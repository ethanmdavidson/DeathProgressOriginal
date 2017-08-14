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

		//Called when the surface is created
		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);

			//call the draw method
			// this is where you must call your draw code
			drawFrame();

		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			if (visible) {

				//call the drawFunction
				drawFrame();

			}
		}

		//called when surface destroyed
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
		}

		public void drawFrame()
		{
			//getting the surface holder
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas canvas = null;  // canvas
			try {
				canvas = holder.lockCanvas();  //get the canvas
				if (canvas != null) {
                    LocalDate birthdate = LocalDate.parse(PreferenceManager.getDefaultSharedPreferences(context).getString("birthdate", ""));
                    LocalDate curdate = new LocalDate();

                    LocalDate deathdate = birthdate.plusYears(90);

                    float percentDead = ((float)Days.daysBetween(birthdate, curdate).getDays()) /
							((float)Days.daysBetween(birthdate, deathdate).getDays());

					Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);

					paint.setColor(Color.parseColor("blue"));
					canvas.drawRect(0, 0, getDesiredMinimumWidth(), getDesiredMinimumHeight(), paint);//draw rect to clear the canvas

                    paint.setColor(Color.parseColor("red"));
                    canvas.drawRect(0, 0, getDesiredMinimumWidth(), (int)(getDesiredMinimumHeight()*percentDead), paint);
				}
			} finally {
				if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
			}
		}
	}


}
