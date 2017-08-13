package com.machinerychorus.lifeprogresswallpaper;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import org.joda.time.LocalDate;

/**
 * Date picker preference. This is implemented poorly because I am lazy; It saves the date as a string
 * Created by Ethan on 8/13/2017.
 */

public class DatePickerPreference extends DialogPreference {
    private DatePicker picker;
    private LocalDate lastDate;

    public DatePickerPreference(Context context) {
        super(context);
    }

    public DatePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePickerPreference(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    protected View onCreateDialogView() {
        picker=new DatePicker(getContext());

        return(picker);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);

        if(lastDate != null) {
            picker.updateDate(lastDate.getYear(), lastDate.getMonthOfYear(), lastDate.getDayOfMonth());
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            lastDate = new LocalDate(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
            persistString(lastDate.toString());
        }
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        lastDate=(restoreValue ? LocalDate.parse(getPersistedString("")) : new LocalDate());
    }
}
