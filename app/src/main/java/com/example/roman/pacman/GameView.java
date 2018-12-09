package com.example.roman.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

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

        wall = BitmapFactory.decodeResource(getResources(), R.drawable.Block);
        point = BitmapFactory.decodeResource(getResources(), R.drawable.Point);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(pacman, 400,500,null);

        canvas.drawText("Skóre: 0",20,60, score);

        canvas.drawText("Obtížnost: 0",canvas.getWidth()/2,60, diff);

        canvas.drawBitmap(life[0],10,canvas.getHeight()-80, null);
        canvas.drawBitmap(life[1],10,canvas.getHeight()-180, null);
        canvas.drawBitmap(life[2],10,canvas.getHeight()-280, null);

        
    }
}
