package com.machinerychorus.lifeprogresswallpaper;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        ListView v = getListView();
        Button setWallpaperButton = new Button(this);
        setWallpaperButton.setText(R.string.setWallpaperButtonName);
        setWallpaperButton.setLayoutParams(new AbsListView.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.MATCH_PARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setWallpaperButton.setLayoutDirection(AbsListView.LAYOUT_DIRECTION_RTL);
        }
        setWallpaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
                startActivity(intent);
            }
        });
        v.addHeaderView(setWallpaperButton);

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
        int statusBarHeight = 100;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pref.edit().putInt(getString(R.string.statusBarHeightKey), statusBarHeight).apply();
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName);
    }
}
