package jp.ac.neec.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget. NumberPicker;

public class PreferenceNumberPicker extends DialogPreference {
    private NumberPicker mPicker;
    private Integer mNumber = 172;
    private Integer mPickerValue = 0; // keeps picker value before dialog closes

    public PreferenceNumberPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreferenceNumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setDialogLayoutResource(R.layout.preference_picker);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
    }

    //身長をダイアログビューでセットするメソッド
    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        mPicker = (NumberPicker) view.findViewById(R.id.picker);
        mPicker.setValue(mNumber);
        mPicker.setMinValue(120);
        mPicker.setMaxValue(200);
    }

    //選択した値をサマリーにセット
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            mPickerValue = mPicker.getValue();
            this.setSummary(String.format("%dcm",mPickerValue));
        }
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setValue(restoreValue ? getPersistedInt(mNumber) : (Integer) defaultValue);
    }

    public void setValue(int value) {
        if (shouldPersist()) {
            persistInt(value);
        }

        if (value != mNumber) {
            mNumber = value;
            notifyChanged();
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }
}