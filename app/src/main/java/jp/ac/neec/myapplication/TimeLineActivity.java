package jp.ac.neec.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

public class TimeLineActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Button button2;
    public HueAdapter_TimeLine imageAdapter;
    private boolean flag=false;
    public int position2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bk);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new HueAdapter_TimeLine(this));
        //グリッドビューの画像をタップしたら詳細へ画面遷移
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Send intent to SingleViewActivity_Timeline
                Intent i = new Intent(getApplicationContext(), SingleViewActivity_Timeline.class);
                // Pass image index
                i.putExtra("id", position);
                startActivity(i);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_search) {
                    Intent intent = new Intent(getApplication(), SearchActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        Spinner spinner = (Spinner) toolbar.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 処理
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.list, android.R.layout.simple_spinner_dropdown_item));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void onClickfav(View view){
        imageAdapter = new HueAdapter_TimeLine(this);

        button2 = (Button) findViewById(R.id.button2);
        if(flag==false) {
            imageAdapter.mHueFavArray[position2]++;
            button2.setText(String.valueOf(imageAdapter.mHueFavArray[position2]));
            flag=true;
        }else{
            button2.setText(String.valueOf(imageAdapter.mHueFavArray[position2]));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {//カメラ

            Intent intent = new Intent(getApplication(), PicAppDateActivity2.class);
            startActivity(intent);
            /*Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);*/
        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(getApplication(), MypageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_timeline) {
            //何もしない
        } else if (id == R.id.nav_manage) {//
            Intent intent = new Intent(getApplication(), SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_fav) {
            Intent intent = new Intent(getApplication(), MypageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(getApplication(), SearchActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_proedit) {
            Intent intent = new Intent(getApplication(), SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {//ログアウトボタンを押したときの動作

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("本当にログアウトしますか？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // ボタンをクリックしたときの動作
                            //ログアウト画面へ遷移
                            Intent intent = new Intent(getApplication(), LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("キャンセル", null);
            builder.show();
        } else if(id == R.id.nav_dress){
            Intent intent = new Intent(getApplication(), DressCodeActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
