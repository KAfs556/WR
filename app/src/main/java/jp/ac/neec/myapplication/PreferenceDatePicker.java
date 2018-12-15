package jp.ac.neec.myapplication;

import java.util.Date;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
public class PreferenceDatePicker extends DialogPreference {
    private DatePicker datePicker;
    public PreferenceDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PreferenceDatePicker(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected View onCreateDialogView() {
        this.datePicker = new DatePicker(this.getContext());
        long dateLong = Long.valueOf(this.getPersistedString("-2209021200000"));
        Date date = new Date(dateLong);
        datePicker.init(date.getYear()+1900, date.getMonth(), date.getDate(), new OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
            }
        });
        return this.datePicker;
    }
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if(positiveResult){
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            Date date = new Date(year-1900,month,day);
            persistString(String.valueOf(date.getTime()));
            this.setSummary(String.format("%d/%d/%d", year,month+1,day));
        }
        super.onDialogClosed(positiveResult);
    }
    public String getValue(){
        long dateLong = Long.valueOf(this.getPersistedString("-2209021200000"));
        Date date = new Date(dateLong);
        return String.format("%04d/%02d/%02d", date.getYear()+1900,date.getMonth()+1,date.getDate());
    }
}