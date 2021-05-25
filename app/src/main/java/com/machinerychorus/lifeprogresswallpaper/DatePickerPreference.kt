package com.machinerychorus.lifeprogresswallpaper

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.preference.DialogPreference
import org.joda.time.LocalDate
import java.util.*

/**
 * Date picker preference. This is implemented poorly because I am lazy; It saves the date as a string
 * Created by Ethan on 8/13/2017.
 * WIP: fixing for androidx https://stackoverflow.com/questions/52754655/ what a pita
 */
class DatePickerPreference(context: Context?, attrs: AttributeSet?) :
    DialogPreference(context, attrs) {
    private var lastDate: LocalDate? = null

    //TODO: needs more work for androidx migration
    protected fun onBindDialogView(v: View?) {
        super.onBindDialogView(v)
        if (lastDate != null) {
            //need to -1/+1 the month because the picker uses 0-11 and jodatime uses 1-12
            picker.updateDate(lastDate!!.year, lastDate!!.monthOfYear - 1, lastDate!!.dayOfMonth)
        }
    }

    override fun onSetInitialValue(restoreValue: Boolean, defaultValue: Any) {
        lastDate = if (restoreValue) LocalDate.parse(getPersistedString("1994")) else LocalDate()
    }

    class DatePickerFragment : DialogFragment(), OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c[Calendar.YEAR]
            val month = c[Calendar.MONTH]
            val day = c[Calendar.DAY_OF_MONTH]

            // Create a new instance of DatePickerDialog and return it
            return DatePickerDialog(activity!!, this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            // Do something with the date chosen by the user
        }
    }
}