package jp.ac.neec.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class FavEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("お気に入りを編集");

        //ツールバーに戻るボタンをセット
        toolbar.setNavigationIcon(R.drawable.ic_icon_left_write_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new HueAdapter_FavEdit(getApplication()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                // Send intent to SingleViewActivity_Timeline
                GridView gridview =(GridView)parent;
                Bitmap image =(Bitmap) gridview.getItemAtPosition(position);
                ArrayAdapter<Bitmap> adapter = (ArrayAdapter<Bitmap>)gridview.getAdapter();
                adapter.remove(image);
            }
        });

        //検索アイコン押下時
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

}
