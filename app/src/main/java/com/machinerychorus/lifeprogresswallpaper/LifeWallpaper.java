package com.machinerychorus.lifeprogresswallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Locale;

public class LifeWallpaper extends WallpaperService {
	Context context;

	public LifeWallpaper() {
	}

	@Override
	public Engine onCreateEngine() {
		context = this;
		JodaTimeAndroid.init(context);

		return new WallpaperEngine();
	}

	public class WallpaperEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener {
		private SharedPreferences preferences;

		public WallpaperEngine(){
			preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			preferences.registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
			Log.i("DeathWallpaper", "called onSurfaceCreated");
			drawFrame();
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			Log.i("DeathWallpaper", "called onSurfaceDestroyed");
		}

		public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
			Log.i("DeathWallpaper", "called onSurfaceRedrawNeeded");
		}

		void drawFrame()
		{
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
                    LocalDate birthdate = LocalDate.parse(PreferenceManager.getDefaultSharedPreferences(context).getString(getString(R.string.birthdateKey), "1994"));

                    float percentDead = ((float)Days.daysBetween(birthdate, new LocalDate()).getDays()) /
							((float)Days.daysBetween(birthdate, birthdate.plusYears(Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString(getString(R.string.expectancyKey), "85")))).getDays());

					Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);

					paint.setColor(PreferenceManager.getDefaultSharedPreferences(context).getInt(getString(R.string.bgColorKey), Color.BLUE));
					canvas.drawRect(0, 0, getDesiredMinimumWidth(), getDesiredMinimumHeight(), paint);

                    paint.setColor(PreferenceManager.getDefaultSharedPreferences(context).getInt(getString(R.string.fgColorKey), Color.RED));
                    canvas.drawRect(0, getDesiredMinimumHeight()-(int)(getDesiredMinimumHeight()*percentDead), getDesiredMinimumWidth(), getDesiredMinimumHeight(), paint);

					paint.setTextSize(Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(context).getString(getString(R.string.textSizeKey), "256")));
					canvas.drawText(String.format(Locale.US, "%.3f%%",percentDead*100f), 10, getDesiredMinimumHeight()-(int)(getDesiredMinimumHeight()*percentDead)-10, paint);
				}
			} finally {
				if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
			}
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
			Log.i("DeathWallpaper", "called onSharedPreferenceChanged");
			drawFrame();
		}
	}
}
