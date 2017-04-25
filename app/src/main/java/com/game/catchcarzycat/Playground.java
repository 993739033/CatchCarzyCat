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
    private static final  int RAW=10;
    private static final int COL=10;
    private static final int BLOCKS=10;

    private Dot[][] Matrix ;
    private Dot cat;

    public Playground(Context context) {
        super(context);
        getHolder().addCallback(callback);
        Matrix = new Dot[RAW][COL];
        for (int i=0;i<RAW;i++) {
            for (int j=0;j<COL;j++) {
                Matrix[i][j] = new Dot(i, j);
            }
        }
        initGame();
    }
    private void initGame(){
        for (int i=0;i<RAW;i++) {
            for (int j=0;j<COL;j++) {
                Matrix[i][j].setStatus(Dot.STATUS_OFF);

            }
        }
        cat = Matrix[5][6];
        for (int n=0;n<BLOCKS;){
            int raw = ((int) (Math.random() * (RAW-1)));
            int col = ((int) (Math.random() * (COL-1)));
            if (Matrix[raw][col].getStatus()==Dot.STATUS_OFF){
                n++;
                Matrix[raw][col].setStatus(Dot.STATUS_ON);
            }

        }
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
