package jp.ac.neec.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OtherpageActivity extends AppCompatActivity{

    private static final int SELECT_PICTURE = 1;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherpage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //ツールバーに戻るボタンの実装
        toolbar.setNavigationIcon(R.drawable.ic_icon_left_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // インテントのデータを取得
        Intent i = getIntent();

        // 画像のIDを選択
        position = i.getExtras().getInt("id");
        HueAdapter_TimeLine imageAdapter = new HueAdapter_TimeLine(OtherpageActivity.this);

        //ヘッダーにアイコンをセット
        ImageView hueIconView1 = (ImageView)findViewById(R.id.circle_imageview);
        hueIconView1.setImageResource(imageAdapter.mHueIcArray[position]);

        //ツールバーに名前をセット
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(imageAdapter.mHueArray[position]);

        //ツールバーに名前と身長をセット
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText(imageAdapter.mHueArray[position]);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(imageAdapter.mHueSinArray[position]);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_hanger));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_heart));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_other);
        final PagerAdapter_OtherPage adapter = new PagerAdapter_OtherPage(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        final ImageView tapView = (ImageView)findViewById(R.id.circle_imageview);

        //アイコンをタップで拡大表示
        tapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = new ImageView(OtherpageActivity.this);
                image.setImageResource(R.drawable.icon_1);
                image.setAdjustViewBounds(true);
                new AlertDialog.Builder(OtherpageActivity.this)
                        .setView(image)
                        .show();
            }
        });
    }
}
