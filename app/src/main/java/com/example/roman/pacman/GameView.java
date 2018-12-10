package com.example.roman.pacman;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameView extends View {

    //pacman
    private Bitmap pacman;

    //stěna
    private Bitmap wall;

    //Bod
    private Bitmap point;

    //skóre
    private Paint score = new Paint();

    //obtížnost
    private Paint diff = new Paint();

    //životy
    private Bitmap life[] = new Bitmap[3];

    float pacx = 200;
    float pacy = 300;

    float speedx = 0;
    float speedy = 0;

    int width;
    int height;

    //20*30
    private int level[] = {
            1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
    };

    public GameView(Context context) {
        super(context);

        this.setBackgroundColor(Color.BLUE);

        pacman = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
        score.setColor(Color.BLACK);
        score.setTextSize(45);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);

        diff.setColor(Color.GREEN);
        diff.setTextSize(45);
        diff.setTypeface(Typeface.DEFAULT_BOLD);
        diff.setTextAlign(Paint.Align.CENTER);
        diff.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
        life[2] = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);

        wall = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        point = BitmapFactory.decodeResource(getResources(), R.drawable.point);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 25;
        height = (h-100) / 25;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        boolean first = true;

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                if(level[i*25 + j]==1)
                {
                    canvas.drawBitmap(wall, null,
                            new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
                }
                if(level[i*25 + j]==0)
                {
                    canvas.drawBitmap(point, null,
                            new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
                }

            }
        }


        pacx += speedx;
        pacy += speedy;
        canvas.drawBitmap(pacman, pacx,pacy,null);

        canvas.drawText("Skóre: 0",20,60, score);

        canvas.drawText("Obtížnost: 0",canvas.getWidth()/2,60, diff);

        canvas.drawBitmap(life[0],10,canvas.getHeight()-80, null);
        canvas.drawBitmap(life[1],10,canvas.getHeight()-180, null);
        canvas.drawBitmap(life[2],10,canvas.getHeight()-280, null);


    }
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()) {

            case MotionEvent.ACTION_DOWN: {

                double xDown = event.getX();
                double yDown = event.getY();
                Toast.makeText(getContext(), "xDown " + xDown, Toast.LENGTH_SHORT).show();


                if (xDown >= Resources.getSystem().getDisplayMetrics().widthPixels / 4 * 3) { //doprava

                    speedx = 15;
                    speedy = 0;

                    invalidate();

                } else if (xDown <= Resources.getSystem().getDisplayMetrics().widthPixels / 4) { //doleva

                    speedx = -15;
                    speedy = 0;

                    invalidate();

                } else if (yDown >= Resources.getSystem().getDisplayMetrics().heightPixels / 4 * 3) { //nahoru

                    speedy = 15;
                    speedx = 0;

                    invalidate();

                } else if (yDown <= Resources.getSystem().getDisplayMetrics().heightPixels / 4) { //dolů

                    speedy = -15;
                    speedx = 0;

                    invalidate();

                }
            }
        }



        return super.onTouchEvent(event);
    }
}
