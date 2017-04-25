package com.game.catchcarzycat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 知らないのセカイ on 2017/4/25.
 */

public class Playground extends SurfaceView {
    SurfaceHolder holder;
    public Playground(Context context) {
        super(context);
        getHolder().addCallback(callback);

    }
    public void redraw(){
        Canvas canvas=getHolder().lockCanvas();
        canvas.drawColor(Color.CYAN);
        getHolder().unlockCanvasAndPost(canvas);

     }
     SurfaceHolder.Callback callback=new SurfaceHolder.Callback() {
         @Override
         public void surfaceCreated(SurfaceHolder holder) {
             redraw();
         }

         @Override
         public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

         }

         @Override
         public void surfaceDestroyed(SurfaceHolder holder) {

         }
     };

}
