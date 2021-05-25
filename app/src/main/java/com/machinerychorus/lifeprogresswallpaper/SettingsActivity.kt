package com.machinerychorus.lifeprogresswallpaper

import android.preference.PreferenceActivity
import android.os.Bundle
import com.machinerychorus.lifeprogresswallpaper.R
import android.widget.AbsListView
import android.widget.LinearLayout
import android.os.Build
import android.content.Intent
import android.app.WallpaperManager
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragment

class SettingsActivity : PreferenceActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(
            android.R.id.content,
            SettingsFragment()
        ).commit()
        val v = listView
        val setWallpaperButton = Button(this)
        setWallpaperButton.setText(R.string.setWallpaperButtonName)
        setWallpaperButton.layoutParams = AbsListView.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            AbsListView.LayoutParams.MATCH_PARENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setWallpaperButton.layoutDirection = AbsListView.LAYOUT_DIRECTION_RTL
        }
        setWallpaperButton.setOnClickListener {
            val intent = Intent()
            intent.action = WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER
            startActivity(intent)
        }
        v.addHeaderView(setWallpaperButton)

        /*
        The wallpaper service needs to know the height of the notification bar so
        that it can avoid drawing the text underneath the notif bar (looks bad).
        Apparently the best way to do this is by getting the notification bar
        height by measuring the window (which can only be done in the activity),
        then saving it in the preferences so it can be access from the service.
        https://stackoverflow.com/questions/3044552
        https://stackoverflow.com/questions/3355367
        Seems dumb and fragile, but that's never stopped me before!
        This method also assumes that the status bar is always at the top.
        We default to 100 so that it (hopefully) won't be covered by the stats bar,
        even if we don't get the real height.
        */
        var statusBarHeight = 100
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        val pref = PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )
        pref.edit().putInt(getString(R.string.statusBarHeightKey), statusBarHeight).apply()
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean {
        return android.preference.PreferenceFragment::class.java.name == fragmentName
    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }

        override fun onDisplayPreferenceDialog(preference: Preference) {
            if (fragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) != null) {
                return
            }
            if (preference is com.machinerychorus.lifeprogresswallpaper.DatePickerPreference) {
                val f: DialogFragment = CustomDialog.newInstance(preference.key)
                f.setTargetFragment(this, 0)
                f.show(fragmentManager, DIALOG_FRAGMENT_TAG)
            } else {
                super.onDisplayPreferenceDialog(preference)
            }
        }
    }
}