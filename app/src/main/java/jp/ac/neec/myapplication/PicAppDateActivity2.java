package jp.ac.neec.myapplication;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PicAppDateActivity2 extends AppCompatActivity {

    private static final int RESULT_PICK_IMAGEFILE = 1001;
    final int SELECT_COLOR_MAX=3;

    //登録する画像
    Bitmap bmpapp;
    private ImageView imageapp;
    private ImageView imageback;

    //入力したテキスト
    private EditText editText1;
    private EditText editText2;

    //カラー選択
    private  ImageView color1;
    private  ImageView color2;
    private  ImageView color3;
    private int flag=1;
    final private Integer[] _colors = { Color.RED, Color.BLUE, Color.GREEN,
            Color.parseColor("#556b2f"),Color.DKGRAY,Color.GRAY,Color.LTGRAY,Color.MAGENTA,
            Color.WHITE,Color.YELLOW }; // 色リスト
    final int ID_COLOR_PICKER = 1;
    private int[] app_colors= new int[SELECT_COLOR_MAX];//登録用の変数

    public final static int REQUEST_CODE_CHOOSER = 101;
    public final static int REQUEST_CODE_EXTERNAL_STORAGE = 102;
    public final static int REQUEST_CODE_CROP = 103;
    public static final List<String> types = Collections
            .unmodifiableList(new LinkedList<String>() {
                {
                    add("image/jpeg");
                    add("image/jpg");
                    add("image/png");
                }
            });
    private static String[] PERMISSION_EXTERNAL_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_app_date);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("コーデを登録");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_icon_left_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //アップロードする画像
        imageapp = (ImageView) findViewById(R.id.app);
        imageback = (ImageView)findViewById(R.id.appback);

        //入力したテキスト
        editText1 = (EditText) findViewById(R.id.edittext1);
        editText2 = (EditText) findViewById(R.id.edittext2);

        //選択カラーの枠
        color1=(ImageView)findViewById(R.id.color1);
        color2=(ImageView)findViewById(R.id.color2);
        color3=(ImageView)findViewById(R.id.color3);

        Button btnSelectImage = (Button) findViewById(R.id.button);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });

    }

    public void checkPermission() {
        for (String permission : PERMISSION_EXTERNAL_STORAGE) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // EXTERNAL_STORAGE permission has not been granted.
                requestExternalStoragePermission();
                return;
            }
        }
        onClickImageApp(null);
    }

    public void onClickImageApp(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, types.toArray());
        }
        startActivityForResult(Intent.createChooser(intent, null), PicAppDateActivity2.REQUEST_CODE_CHOOSER);
    }

    private void requestExternalStoragePermission() {
        // Contact permissions have not been granted yet. Request them directly.
        ActivityCompat.requestPermissions(this, PERMISSION_EXTERNAL_STORAGE, PicAppDateActivity2.REQUEST_CODE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case (PicAppDateActivity2.REQUEST_CODE_EXTERNAL_STORAGE):
                if (verifyPermissions(grantResults)) {
                    onClickImageApp(null);
                } else {
                    Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }
        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PicAppDateActivity2.REQUEST_CODE_CHOOSER):
                if (resultCode != RESULT_OK) {
                    Toast.makeText(this,"test", Toast.LENGTH_LONG).show();
                    return;
                }
                startCrop(data.getData());
                break;
            case (REQUEST_CODE_CROP):
                if (resultCode != RESULT_OK) {
                    Toast.makeText(this,"tset2", Toast.LENGTH_LONG).show();
                    return;
                }
                imageapp.setImageURI(data.getData());
                //imageback.setBackgroundColor(Color.BLACK);
                break;
            default:
                break;
        }
    }

    private void startCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 16);
        intent.putExtra("aspectY", 9);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("scale", "true");
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getExternalStorageTempStoreFilePath()));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, PicAppDateActivity2.REQUEST_CODE_CROP);
    }
    private File getExternalStorageTempStoreFilePath() {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File file = new File(path, "selected_temp_image.jpg");
        return file;
    }
    private void deleteExternalStoragePublicPicture() {
        // Create a path where we will place our picture in the user's
        // public pictures directory and delete the file.  If external
        // storage is not currently mounted this will fail.
        File file = getExternalStorageTempStoreFilePath();
        if (file != null) {
            // Log.d("ImageSelectionCropDemo", file.getAbsolutePath() + " is " + file.exists());
            if (!file.delete()) {
                Log.e("ImageSelectionCropDemo", "File deletion failed.");
            }
        }
    }

    // 背景に色リストを適用する ListAdapter
    static class ColorListAdapter extends ArrayAdapter<Integer> {
        public ColorListAdapter(Context context, Integer[] colors) {
            super(context, android.R.layout.simple_list_item_1, colors);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView)super.getView(position, convertView, parent);
            view.setBackgroundColor(getItem(position));
            view.setTextColor(Color.TRANSPARENT); // 色値が表示されないように隠す
            return view;
        }
    }
    //着色カラーを選択を押下した時の動作
    public void onClickColor(View view){
        showDialog(ID_COLOR_PICKER);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ID_COLOR_PICKER:
                ColorListAdapter adapter = new ColorListAdapter(PicAppDateActivity2.this, _colors);

                return new AlertDialog.Builder(PicAppDateActivity2.this)
                        .setTitle("色を選択")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //選択カラーをセットする場所のフラグ
                                if(flag==1) {
                                    color1.setBackgroundColor(_colors[which]);
                                    app_colors[0]=_colors[which];
                                    flag++;
                                }else if(flag==2){
                                    color2.setBackgroundColor(_colors[which]);
                                    app_colors[1]=_colors[which];
                                    flag++;
                                }else if(flag==3) {
                                    color3.setBackgroundColor(_colors[which]);
                                    app_colors[2] = _colors[which];
                                    flag=1;
                                }
                                //色を選択したときに反映
                                Toast.makeText(PicAppDateActivity2.this,
                                        "You picked index=" + which, Toast.LENGTH_LONG).show();
                            }
                        }).create();

            default:
                return super.onCreateDialog(id);
        }
    }

    //キャンセルボタンを押下した時の動作
    public void onClickCansel(View view){
        Intent intent = new Intent(getApplication(), TimeLineActivity.class);
        startActivity(intent);
    }

    private TestOpenHelper helper;
    private SQLiteDatabase db;
    //登録ボタンを押下した時の動作
    public void onClickApp(View view){
        //CharSequence textcap = editText1.getText().toString();
        //CharSequence textbr = editText2.getText().toString();
        String caption = editText1.getText().toString();
        String brand = editText2.getText().toString();
        Toast.makeText(this, caption, Toast.LENGTH_LONG).show();

        if(helper == null){
            helper = new TestOpenHelper(getApplicationContext());
        }

        if(db == null){
            db = helper.getWritableDatabase();
        }
        insertData(db, caption, brand,Integer.valueOf(brand));

    }
    private void insertData(SQLiteDatabase db, String cap, String bd,int price){

        ContentValues values = new ContentValues();
        values.put("company", cap);
        values.put("brand",bd);
        values.put("stockprice", price);

        db.insert("testdb", null, values);
    }
}
