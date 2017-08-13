package com.machinerychorus.lifeprogresswallpaper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

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
					Paint paint = new Paint();
					paint.setColor(Color.parseColor("blue"));

					//paint.setAlpha(250);
					paint.setStyle(Paint.Style.FILL);
					canvas.drawRect(0, 0, getDesiredMinimumWidth(), getDesiredMinimumHeight(), paint);//draw rect to clear the canvas
				}
			} finally {
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}
		}
	}


}
