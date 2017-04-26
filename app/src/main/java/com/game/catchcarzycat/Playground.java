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
import android.widget.Toast;

import java.util.Vector;

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
    private static  int  k =1;

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
     private Boolean isAtEdge(Dot dot){
         if (dot.getX()*dot.getY()==0||dot.getX()+1==COL||dot.getY()+1==RAW){
             return true;
         }
         return false;
     }


    private void MoveTo(Dot one) {
        one.setStatus(Dot.STATUS_IN);
        Matrix[cat.getX()][one.getY()].setStatus(Dot.STATUS_OFF);
        cat.setXY(one.getY(), one.getX());
    }
    private void move(){
        if (isAtEdge(cat)) {
            lose();return;
        }
        Vector<Dot> vector = new Vector<>();
        for (int i=1;i<7;i++) {
            Dot dot = getNears(Matrix[cat.getY()][cat.getX()], i);
            if (dot.getStatus()==Dot.STATUS_OFF) {
                vector.add(dot);
            }
        }
        if (vector.size()  == 0) {
            win();
            return;
        }else {
            MoveTo(vector.get(0));
        }

    }

    private void win() {
        Toast.makeText(getContext(), "you are win", Toast.LENGTH_SHORT).show();

    }

    private void lose() {
        Toast.makeText(getContext(), "you are lose", Toast.LENGTH_SHORT).show();

    }

    private int getDistance(Dot one ,int dir){
         Dot next;
         next=one;
         int distance=0;
         while (true) {

             next = getNears(next, dir);
             if (next.getStatus() == Dot.STATUS_ON) {
                 return distance*-1;
             }
             if (isAtEdge(next)) {
                 distance++;
                 return distance;
             }
              distance++;

         }


     }

    private Dot getNears(Dot one, int dir) {
        Dot dot=null;
        switch (dir){
            case 1:

                dot = Matrix[one.getY()][one.getX()-1];

               break;
            case 2:
                 if (one.getY()%2==0){

                     dot = Matrix[one.getY()-1][one.getX() -1];
                 }else{

                     dot = Matrix[one.getY()-1][one.getX()];
                 }

                break;
            case 3:
                if (one.getY()%2==0){

                    dot = Matrix[one.getY()-1][one.getX() ];
                }else{
                    dot = Matrix[one.getY()-1][one.getX() +1];
                }
                break;
            case 4:
                dot = Matrix[one.getY()][one.getX() +1];
                break;
            case 5:
                if (one.getY()%2==0){
                    dot = Matrix[one.getY()+1][one.getX() ];
                }else{
                    dot = Matrix[one.getY()+1][one.getX()+1];
                }

                break;
            case 6:
                if (one.getY()%2==0){

                    dot = Matrix[one.getY()+1][one.getX()-1];
                }else{
                    dot = Matrix[one.getY()+1][one.getX()];

                }

                break;
            default:
                break;

        }
        return dot;
    }

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

            if (x+1>COL||y+1>RAW||x<0||y<0){

            }
            else{
                if (Matrix[y][x].getStatus()==Dot.STATUS_OFF) {
                    Matrix[y][x].setStatus(Dot.STATUS_ON);
                    move();
                }
                redraw();
            }

        }
        return true;
    }
}
