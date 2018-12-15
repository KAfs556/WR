package jp.ac.neec.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LoginNewActivity extends AppCompatActivity {

    //入力したテキスト
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private TextView editText5;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        //ツールバーを設定
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //ツールバーにタイトルを追加
        toolbar.setTitle("新規登録");
        setSupportActionBar(toolbar);
        //ツールバーに戻るボタンを実装
        toolbar.setNavigationIcon(R.drawable.ic_icon_left_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //入力したテキスト
        editText1 = (EditText) findViewById(R.id.editlogin1);
        editText2 = (EditText) findViewById(R.id.editlogin2);
        editText3 = (EditText) findViewById(R.id.editlogin3);
        editText4 = (EditText) findViewById(R.id.editlogin4);
        editText5 = (TextView) findViewById(R.id.editlogin5);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
    }

    public void onClickTall(View view){
        NumberPicker myNumberPicker = new NumberPicker(this);
        myNumberPicker.setMaxValue(200);
        myNumberPicker.setMinValue(100);
        NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                editText5.setText("" + newVal);
            }
        };
        myNumberPicker.setOnValueChangedListener(myValChangedListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(myNumberPicker);
        builder.setTitle("身長を入力");
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){

            }
        });
        builder.show();
    }

    public void onClickNewAccount(View view){
        CharSequence loginId = editText1.getText().toString();
        CharSequence password = editText2.getText().toString();
        CharSequence name = editText3.getText().toString();
        CharSequence email = editText4.getText().toString();
        CharSequence tall = editText5.getText().toString();

        int checkedId = radioGroup.getCheckedRadioButtonId();

        if(loginId.length() != 0 && password.length() != 0 && name.length() != 0 && email.length() != 0 && checkedId!=-1 && tall.length() != 0){
            // 選択されているラジオボタンの取得
            RadioButton radioButton = (RadioButton) findViewById(checkedId);// (Fragmentの場合は「getActivity().findViewById」にする)
            // ラジオボタンのテキストを取得
            String text = radioButton.getText().toString();
            //Log.v("checked", text);

            finish();
            Toast.makeText(this,"登録しました。ログインしてください",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"入力または選択されていない箇所があります",Toast.LENGTH_LONG).show();
        }
    }
}
