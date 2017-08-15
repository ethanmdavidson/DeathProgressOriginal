package com.machinerychorus.lifeprogresswallpaper;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.widget.Toast;

/**
 * A custom preference object. Works the same as a normal EditTextPreference, but only allows
 * integer input
 * Created by Ethan on 3/2/2017.
 */

public class IntegerPreference extends EditTextPreference {
    private Context context;

    public IntegerPreference(Context context) {
        super(context);
        this.context = context;
    }

    public IntegerPreference(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        this.context = context;
    }

    @Override
    public void setText(String text){
        boolean isValid = false;
        try {
            int value = Integer.parseInt(text);
            if(value >= 0){
                isValid = true;
            }
        } catch (NumberFormatException e){}

        if(isValid){
            super.setText(text);
        } else {
            //alert user that it wasn't changed
            Toast toast = Toast.makeText(context, "Only positive numbers are allowed in this field", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
