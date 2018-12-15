package jp.ac.neec.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ツールバーを設定
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //ツールバーにタイトルを追加
        toolbar.setTitle("ログイン画面");
        setSupportActionBar(toolbar);
        //ツールバーに戻るボタンを実装
        toolbar.setNavigationIcon(R.drawable.ic_icon_left_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText1 = (EditText) findViewById(R.id.editlogin1);
        editText2 = (EditText) findViewById(R.id.editlogin2);
    }
    public void onClickLoginNew(View view){
        Intent intent = new Intent(getApplication(), LoginNewActivity.class);
        startActivity(intent);
    }
    public void onClickLogin(View view){
        CharSequence loginId = editText1.getText().toString();
        CharSequence password = editText2.getText().toString();
        if(loginId.length() != 0 && password.length() != 0){

            //ここでデータベースと照合する

        }else {
            Toast.makeText(this, "入力されていない箇所があります", Toast.LENGTH_LONG).show();
        }
    }
}
