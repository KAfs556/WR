package jp.ac.neec.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

public class PicAppDateActivity extends AppCompatActivity {

    private static final int RESULT_PICK_IMAGEFILE = 1001;
    final int SELECT_COLOR_MAX=3;

    //登録する画像
    private Bitmap bmp;
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
    final private Integer[] _colors = {Color.parseColor("#8b4513"), Color.RED, Color.BLUE,Color.parseColor("#000080"),Color.GREEN,
            Color.parseColor("#556b2f"),Color.BLACK,Color.DKGRAY,Color.GRAY,Color.LTGRAY,Color.MAGENTA,
            Color.WHITE,Color.YELLOW }; // 色リスト
    final int ID_COLOR_PICKER = 1;
    private int[] app_colors= new int[SELECT_COLOR_MAX];//登録用の変数

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
    }

    public void onClickImageApp(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            if(resultData.getData() != null){
                ParcelFileDescriptor pfDescriptor = null;
                try{
                    Uri uri = resultData.getData();
                    pfDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                    if(pfDescriptor != null){
                        FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                        bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        pfDescriptor.close();
                        imageapp.setImageBitmap(bmp);
                        imageback.setBackgroundColor(Color.BLACK);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try{
                        if(pfDescriptor != null){
                            pfDescriptor.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
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
                ColorListAdapter adapter = new ColorListAdapter(PicAppDateActivity.this, _colors);

                return new AlertDialog.Builder(PicAppDateActivity.this)
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
                                Toast.makeText(PicAppDateActivity.this,
                                        "You picked index=" + which, Toast.LENGTH_LONG).show();
                            }
                        }).create();

            default:
                return super.onCreateDialog(id);
        }
    }

    private TestOpenHelper helper;
    private SQLiteDatabase db;
    //キャンセルボタンを押下した時ｚの動作
    public void onClickCansel(View view){
        finish();
    }
    //登録ボタンを押下した時の動作
    public void onClickApp(View view){
        //説明
        String caption = editText1.getText().toString();
        //ブランド
        String brand = editText2.getText().toString();
        //カラー

        //写真
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bytegazo = bos.toByteArray();

        insertData(db, caption, brand,bytegazo);

        if(caption!=null && brand!=null && app_colors[0]!=-1){

            Toast.makeText(this, caption, Toast.LENGTH_LONG).show();
            finish();
        }
    }


    private void insertData(SQLiteDatabase db, String cap, String bd, byte[] gz){

        ContentValues values = new ContentValues();
        values.put("caption", cap);
        values.put("brand",bd);
        values.put("gazo", gz);

        db.insert("testdb", null, values);
    }
}
