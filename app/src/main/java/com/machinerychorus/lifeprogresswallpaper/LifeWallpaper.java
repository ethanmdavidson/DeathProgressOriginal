package com.machinerychorus.lifeprogresswallpaper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.IBinder;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class LifeWallpaper extends WallpaperService {
    Context context;
    private boolean visible;
    Canvas canvas;

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

        //called when varaible changed
        @Override
        public void onVisibilityChanged(boolean visible) {
            visible = visible;
            if (visible) {

                //call the drawFunction
                drawFrame();

            }
        }

        //called when surface destroyed
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible = false;
        }

        public void drawFrame()
        {
            //getting the surface holder
            final SurfaceHolder holder = getSurfaceHolder();

            canvas = null;  // canvas
            try {
                canvas = holder.lockCanvas();  //get the canvas
                if (canvas != null) {
                    // draw something
                    // my draw code

                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
        }
    }


}
