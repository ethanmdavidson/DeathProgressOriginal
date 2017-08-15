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

    public IntegerPreference(Context context) {
        super(context);
    }

    public IntegerPreference(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    @Override
    public void setText(String text){
        boolean isValid = false;
        try {
            if(Integer.parseInt(text) >= 0){
                isValid = true;
            }
        } catch (NumberFormatException e){}

        if(isValid){
            super.setText(text);
        } else {
            //alert user that it wasn't changed
            Toast toast = Toast.makeText(getContext(), "Only positive numbers are allowed in this field", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
