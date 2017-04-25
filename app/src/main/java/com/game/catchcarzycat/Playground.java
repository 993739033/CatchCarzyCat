package com.game.catchcarzycat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by 知らないのセカイ on 2017/4/25.
 */

public class Playground extends SurfaceView implements View.OnTouchListener {
    SurfaceHolder holder;
    private static final  int RAW=10;
    private static final int COL=10;
    private static final int BLOCKS=10;
    private static int Width=90;

    private  int Meargin=50;

    private Dot[][] Matrix ;
    private Dot cat;

    public Playground(Context context) {
        super(context);
        getHolder().addCallback(callback);
        Matrix = new Dot[RAW][COL];
        for (int i=0;i<RAW;i++) {
            for (int j=0;j<COL;j++) {
                Matrix[i][j] = new Dot(j, i);
            }
        }
        setOnTouchListener(this);
        initGame();
    }
    private void initGame(){
        for (int i=0;i<RAW;i++) {
            for (int j=0;j<COL;j++) {
                Matrix[i][j].setStatus(Dot.STATUS_OFF);

            }
        }
        Matrix[5][6].setStatus(Dot.STATUS_IN);
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
        canvas.drawColor(Color.LTGRAY);
        for (int i=0;i<RAW;i++) {
            int offsetX=0;
            if (i%2==0){
                offsetX=Width/2;
            }

            for (int j=0;j<COL;j++) {
                Dot dot=Matrix[i][j];
                Paint paint = new Paint();
                paint.setFlags(Paint.ANTI_ALIAS_FLAG);

                switch(dot.getStatus()){
                    case Dot.STATUS_IN:
                       paint.setColor(ContextCompat.getColor(getContext(),R.color.colorin));
                        Log.d("ok1", "ok1");
                        break;
                    case Dot.STATUS_OFF:
                        paint.setColor(ContextCompat.getColor(getContext(),R.color.coloroff));
                        Log.d("ok", "ok2");
                        break;
                    case Dot.STATUS_ON:
                        paint.setColor(ContextCompat.getColor(getContext(),R.color.coloron));
                        Log.d("ok", "ok3");
                        break;
                    default:
                        break;
                }
//                Log.d("dot", dot.getY()+"");

                int xl=dot.getX()*Width-offsetX+Meargin;
                int yl=(dot.getY())*Width+Meargin;
                int xr=(dot.getX()+1)*Width-offsetX+Meargin;
                int yr =(dot.getY()+1)*Width+Meargin;
                canvas.drawOval(new RectF(xl,yl,
                        xr,yr),paint);
//                Log.d("draw", "draw");
//                canvas.drawOval(new RectF(0,0,200,200),paint);
            }
        }


        getHolder().unlockCanvasAndPost(canvas);

     }
     SurfaceHolder.Callback callback=new SurfaceHolder.Callback() {
         @Override
         public void surfaceCreated(SurfaceHolder holder) {
             redraw();
         }

         @Override
         public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
               Width=(width)/(COL+1);
             Meargin=Width*4/5;
               redraw();
         }

         @Override
         public void surfaceDestroyed(SurfaceHolder holder) {

         }
     };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x;
            int y = (int) ((event.getY() - Meargin) / Width);
            if (y%2==0){
                x = (int) ((event.getX() - Meargin ) / Width);
            }else {
                x = (int) ((event.getX() - Meargin - Width / 2) / Width);
            }
            if (x+1>COL||y+1>RAW){
                //Todo 这里实现点击边界外的方法
            }
            else{
                Matrix[y][x].setStatus(Dot.STATUS_ON);
                redraw();
            }

        }
        return true;
    }
}
