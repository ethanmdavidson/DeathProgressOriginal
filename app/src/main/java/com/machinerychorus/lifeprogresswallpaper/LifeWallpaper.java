package com.machinerychorus.lifeprogresswallpaper;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Locale;

public class LifeWallpaper extends WallpaperService {

	public LifeWallpaper() {
	}

	@Override
	public Engine onCreateEngine() {
		JodaTimeAndroid.init(this);

		return new WallpaperEngine();
	}

	private class WallpaperEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener {

		WallpaperEngine(){
            setOffsetNotificationsEnabled(false);
			PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
			drawFrame();
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
		}

		@Override
		public void onSurfaceRedrawNeeded(SurfaceHolder holder){
			drawFrame();
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
			drawFrame();
		}

		void drawFrame()
		{
			Canvas canvas = null;
			try {
				canvas = getSurfaceHolder().lockCanvas();
				if (canvas != null) {
					SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    LocalDate birthdate = LocalDate.parse(pref.getString(getString(R.string.birthdateKey), "1994"));

                    float percentDead = ((float)Days.daysBetween(birthdate, new LocalDate()).getDays()) /
							((float)Days.daysBetween(birthdate, birthdate.plusYears(Integer.parseInt(pref.getString(getString(R.string.expectancyKey), "85")))).getDays());

					Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);

					paint.setColor(pref.getInt(getString(R.string.bgColorKey), Color.BLUE));
					canvas.drawRect(0, 0, getDesiredMinimumWidth(), getDesiredMinimumHeight(), paint);

                    paint.setColor(pref.getInt(getString(R.string.fgColorKey), Color.BLACK));
                    canvas.drawRect(0, getDesiredMinimumHeight()-(int)(getDesiredMinimumHeight()*percentDead), getDesiredMinimumWidth(), getDesiredMinimumHeight(), paint);

					paint.setTextSize(Float.parseFloat(pref.getString(getString(R.string.textSizeKey), "256")));
					canvas.drawText(String.format(Locale.US, "%."+pref.getString(getString(R.string.decimalsKey), "3")+"f%%",percentDead*100f), 10, getDesiredMinimumHeight()-(int)(getDesiredMinimumHeight()*percentDead)-10, paint);
				}
			} finally {
				if (canvas != null) {
					getSurfaceHolder().unlockCanvasAndPost(canvas);
                }
			}
		}
	}
}
