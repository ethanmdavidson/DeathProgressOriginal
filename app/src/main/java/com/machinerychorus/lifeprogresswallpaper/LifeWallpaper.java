package com.machinerychorus.lifeprogresswallpaper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

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
		return new WallpaperEngine();
	}

	public class WallpaperEngine extends Engine{

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);

			drawFrame();
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			if (visible) {
				drawFrame();
			}
		}

		//called when surface destroyed
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
		}

		void drawFrame()
		{
			//getting the surface holder
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas canvas = null;  // canvas
			try {
				canvas = holder.lockCanvas();  //get the canvas
				if (canvas != null) {
                    LocalDate birthdate = LocalDate.parse(PreferenceManager.getDefaultSharedPreferences(context).getString(getString(R.string.birthdateKey), ""));

                    float percentDead = ((float)Days.daysBetween(birthdate, new LocalDate()).getDays()) /
							((float)Days.daysBetween(birthdate, birthdate.plusYears(90)).getDays());

					Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);

					paint.setColor(PreferenceManager.getDefaultSharedPreferences(context).getInt(getString(R.string.bgColorKey), Color.BLUE));
					canvas.drawRect(0, 0, getDesiredMinimumWidth(), getDesiredMinimumHeight(), paint);//draw rect to clear the canvas

                    paint.setColor(PreferenceManager.getDefaultSharedPreferences(context).getInt(getString(R.string.fgColorKey), Color.RED));
                    canvas.drawRect(0, getDesiredMinimumHeight()-(int)(getDesiredMinimumHeight()*percentDead), getDesiredMinimumWidth(), getDesiredMinimumHeight(), paint);

					paint.setTextSize(48f);
					canvas.drawText(String.format(Locale.US, "%.2f%%",percentDead*100f), 10, getDesiredMinimumHeight()-(int)(getDesiredMinimumHeight()*percentDead)-10, paint);
				}
			} finally {
				if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
			}
		}
	}


}
