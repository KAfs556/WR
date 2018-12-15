package jp.ac.neec.myapplication;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleViewActivity_Timeline extends AppCompatActivity {

    private RenderScript rs;
    private ImageView image;
    private Bitmap bmp;
    private int position;
    public HueAdapter_TimeLine imageAdapter;
    private Button button2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_view);

        // インテントのデータを取得
        Intent i = getIntent();

        // 画像のIDを選択
        position = i.getExtras().getInt("id");
        imageAdapter = new HueAdapter_TimeLine(this);

        //ツールバーにアイコンをセット
        ImageView hueIconView1 = (ImageView)findViewById(R.id.hue_circle_imageview1);
        hueIconView1.setImageResource(imageAdapter.mHueIcArray[position]);
        //コンテンツにアイコンにセット
        ImageView hueIconView2 = (ImageView)findViewById(R.id.hue_circle_imageview2);
        hueIconView2.setImageResource(imageAdapter.mHueIcArray[position]);

        //ツールバーに名前と身長をセット
        TextView textView1 = (TextView) findViewById(R.id.textview1);
        textView1.setText(imageAdapter.mHueArray[position]);
        TextView textView2 = (TextView) findViewById(R.id.textview2);
        textView2.setText(imageAdapter.mHueSinArray[position]);
        //コンテンツに名前と身長をセット
        TextView textView3 = (TextView) findViewById(R.id.textview3);
        textView3.setText(imageAdapter.mHueArray[position]);
        TextView textView4 = (TextView) findViewById(R.id.textview4);
        textView4.setText(imageAdapter.mHueSinArray[position]);

        //ツールバーにメニューをセット
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.single_view_menu);

        //ツールバーに戻るボタンをセット
        toolbar.setNavigationIcon(R.drawable.ic_icon_left_write_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //ツールバー透過
        Drawable d = toolbar.getBackground();
        d.setAlpha(10);

        //ボタンにいいね数をセット
        Button button = (Button) findViewById(R.id.button2) ;
        button.setText(String.valueOf(imageAdapter.mHueFavArray[position]));

        //ボタンにコメント数をセット
        button = (Button) findViewById(R.id.button3) ;
        button.setText(imageAdapter.mHueComeArray[position]);

        //メインに画像をセット
        ImageView imageView = (ImageView) findViewById(R.id.SingleView);
        imageView.setImageResource(imageAdapter.mHueIdArray[position]);

        hueIconView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), OtherpageActivity.class);
                // Pass image index
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });
       textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), OtherpageActivity.class);
                // Pass image index
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), OtherpageActivity.class);
                // Pass image index
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });


        try{
            rs = RenderScript.create(this);
            image = (ImageView)findViewById(R.id.imageview);
            bmp = BitmapFactory.decodeResource(getResources(), imageAdapter.mHueIdArray[position]);

            // 第二引数は加工する元のBitmap
            blurProcess(image, bmp);
        }catch (Exception e){
            //nop
        }
    }

    private boolean flag=false;
    public void onClickfav(View view){
        imageAdapter = new HueAdapter_TimeLine(this);

        button2 = (Button) findViewById(R.id.button2);
        if(flag==false) {
            imageAdapter.mHueFavArray[position]++;
            button2.setText(String.valueOf(imageAdapter.mHueFavArray[position]));
            flag=true;
        }else{
            button2.setText(String.valueOf(imageAdapter.mHueFavArray[position]));
            flag=false;
        }
        startScaling();
    }
    private void startScaling(){
        // ScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f,1.2f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // animation時間 msec
        scaleAnimation.setDuration(10);
        // 繰り返し回数
        scaleAnimation.setRepeatCount(0);
        // animationが終わったそのまま表示にする
        scaleAnimation.setFillAfter(false);
        //アニメーションの開始
        button2.startAnimation(scaleAnimation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void blurProcess(final ImageView img, final Bitmap bmp) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Allocation alloc = Allocation.createFromBitmap(rs, bmp);
                    ScriptIntrinsicBlur blur =
                            ScriptIntrinsicBlur.create(rs, alloc.getElement());
                    blur.setRadius(25f); // ブラーする度合い
                    blur.setInput(alloc);
                    blur.forEach(alloc);
                    alloc.copyTo(bmp); // 加工した画像をbmpに移す
                } catch (Exception e) {
                    // nop
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void v) {
                //blur画像に灰色のフィルターをかける
                img.setColorFilter(0xaa424242);
                // blur画像を背景にセット
                img.setImageBitmap(bmp);
            }
        }.execute(null, null, null);
    }


}