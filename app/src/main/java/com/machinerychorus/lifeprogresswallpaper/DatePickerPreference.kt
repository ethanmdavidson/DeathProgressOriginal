package com.machinerychorus.lifeprogresswallpaper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.preference.DialogPreference;

import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import org.joda.time.LocalDate;

import java.util.Calendar;

/**
 * Date picker preference. This is implemented poorly because I am lazy; It saves the date as a string
 * Created by Ethan on 8/13/2017.
 * WIP: fixing for androidx https://stackoverflow.com/questions/52754655/ what a pita
 */

public class DatePickerPreference extends DialogPreference {
    private LocalDate lastDate;

    public DatePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //TODO: needs more work for androidx migration



    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);

        if(lastDate != null) {
            //need to -1/+1 the month because the picker uses 0-11 and jodatime uses 1-12
            picker.updateDate(lastDate.getYear(), lastDate.getMonthOfYear()-1, lastDate.getDayOfMonth());
        }
    }



    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        lastDate=(restoreValue ? LocalDate.parse(getPersistedString("1994")) : new LocalDate());
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }
}
