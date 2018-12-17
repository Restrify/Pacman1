package com.example.roman.pacman;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Point
{
    int x;
    int y;
    int point;
}

class Object
{
    Bitmap bitmap;
    Canvas canvas;
    int j;
    int i;
    int width;
    int height;

}

class Ghost
{
    int x;
    int y;
}

public class GameView extends View {

    //pacman
    private Bitmap pacman;

    private Bitmap ghost;

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

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;

    private Ghost ghosts[] = new Ghost[6];

    int pacx;
    int pacy;

    int points = 0;
    String name;

    int speedx = 0;
    int speedy = 0;

    int width;
    int height;

    int start = 0;
    int moves = 7;

    Boolean exist = false;
    Boolean end = true;

    //20*30
    private int level[] = {
            1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
            1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,1,1,1,1,1,0,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,0,1,
            1,4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,1,0,1,0,0,1,1,1,1,0,1,0,0,0,1,1,1,0,0,0,0,0,1,
            1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,
            1,0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,
            1,0,1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,
            1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,
            1,0,1,0,0,1,0,0,0,0,0,1,2,1,0,0,0,0,0,0,1,0,0,0,1,
            1,0,1,0,0,1,0,0,0,0,0,1,2,1,0,0,0,0,0,0,1,0,0,0,1,
            1,0,1,0,0,1,0,0,0,0,0,1,2,1,0,0,0,0,0,0,1,0,0,0,1,
            1,0,1,0,0,1,0,0,0,0,0,1,2,1,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,1,0,0,0,0,0,1,2,1,0,0,0,0,1,0,0,0,0,0,1,
            1,0,1,0,0,0,0,0,0,0,0,1,2,1,0,0,0,0,1,0,0,0,0,0,1,
            1,0,1,0,0,0,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,1,
            1,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,
            1,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,
            1,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,1,1,1,0,0,0,0,0,1,
            1,0,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
    };

    public GameView(Context context) {
        super(context);

        this.setBackgroundColor(Color.BLUE);

        pacman = BitmapFactory.decodeResource(getResources(), R.drawable.pacright);
        score.setColor(Color.GREEN);
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

        ghost = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);
        wall = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        point = BitmapFactory.decodeResource(getResources(), R.drawable.point);

        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(gyroscopeSensor == null)
        {
            Toast.makeText(context, "Zařízení nemá Gyroskop", Toast.LENGTH_SHORT).show();

        }

        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //if(event.values[2])
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 25;
        height = (h-100) / 25;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        ArrayList<Task> list = new ArrayList<>();

        int count = 0;
            for (int i = 0; i < 25; i++) {
                for (int j = 0; j < 25; j++) {
                    Object object = new Object();
                    object.height = height;
                    object.width = width;
                    object.i = i;
                    object.j = j;
                    object.canvas = canvas;

                    if (level[i * 25 + j] == 4) {
                        pacx = i;
                        pacy = j;

                        object.bitmap = pacman;
                    }
                    if (level[i * 25 + j] == 2) {
                        Ghost g = new Ghost();
                        g.x = i;
                        g.y = j;
                        ghosts[count] = g;
                        count++;

                    }
                    if (level[i * 25 + j] == 1) {
                        object.bitmap = wall;
                    }
                    if (level[i * 25 + j] == 0) {
                        exist = true;
                        object.bitmap = point;
                    }

                    if(object.bitmap != null)
                    {
                        Task task = new Task();
                        task.execute(object);
                        list.add(task);
                    }
                }
            }

            MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.pacman_chomp);


            if (level[(pacx + speedx) * 25 + (pacy + speedy)] == 0) {
                points += 10;
                level[pacx * 25 + pacy] = 3;
                level[(pacx + speedx) * 25 + (pacy + speedy)] = 4;
                mp.start();
            }
            if (level[(pacx + speedx) * 25 + (pacy + speedy)] == 2) {
                end = true;
            }
            if (level[(pacx + speedx) * 25 + (pacy + speedy)] == 1) {
                speedx = 0;
                speedy = 0;
            }
            if (level[(pacx + speedx) * 25 + (pacy + speedy)] == 3) {
                level[pacx * 25 + pacy] = 3;
                level[(pacx + speedx) * 25 + (pacy + speedy)] = 4;
            }

            moveGhosts();


            pacx += speedx;
            pacy += speedy;

            canvas.drawText("Skóre: " + points, 20, 60, score);

            canvas.drawText("Obtížnost: 0", canvas.getWidth() / 2, 60, diff);

            canvas.drawBitmap(life[0], 10, canvas.getHeight() - 80, null);
            canvas.drawBitmap(life[1], 110, canvas.getHeight() - 80, null);
            canvas.drawBitmap(life[2], 210, canvas.getHeight() - 80, null);

            if(!exist)
            {
                end = true;
            }

    }
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()) {

            case MotionEvent.ACTION_DOWN: {

                double xDown = event.getX();
                double yDown = event.getY();
                Toast.makeText(getContext(), "xDown " + xDown, Toast.LENGTH_SHORT).show();


                if (xDown >= Resources.getSystem().getDisplayMetrics().widthPixels / 4 * 3) { //doprava

                    if(level[pacx*25 + (pacy + 1)] != 1) {
                        pacman = BitmapFactory.decodeResource(getResources(), R.drawable.pacright);
                        speedx = 0;
                        speedy = 1;
                    }


                } else if (xDown <= Resources.getSystem().getDisplayMetrics().widthPixels / 4) { //doleva

                    if(level[pacx*25 + (pacy - 1)] != 1) {
                        pacman = BitmapFactory.decodeResource(getResources(), R.drawable.pacleft);
                        speedx = 0;
                        speedy = -1;
                    }


                } else if (yDown >= Resources.getSystem().getDisplayMetrics().heightPixels / 4 * 3) { //dolů

                    if(level[(pacx + 1)*25 + pacy] != 1) {
                        pacman = BitmapFactory.decodeResource(getResources(), R.drawable.pacdown);
                        speedx = 1;
                        speedy = 0;
                    }


                } else if (yDown <= Resources.getSystem().getDisplayMetrics().heightPixels / 4) { //nahoru

                    if(level[(pacx - 1)*25 + pacy] != 1) {
                        pacman = BitmapFactory.decodeResource(getResources(), R.drawable.pacup);
                        speedx = -1;
                        speedy = 0;
                    }
                }
            }
        }

        return super.onTouchEvent(event);
    }

    public void moveGhosts()
    {
        List<Point> points = new ArrayList<Point>();
        for(int i=0;i<ghosts.length;i++)
        {
            if(level[(ghosts[i].x+1)*25 + ghosts[i].y] != 1 && level[(ghosts[i].x+1)*25 + ghosts[i].y] != 2)
            {
                Point point = new Point();
                point.x = ghosts[i].x+1;
                point.y = ghosts[i].y;
                point.point = level[(ghosts[i].x+1)*25 + ghosts[i].y];

                points.add(point);

            }
            if(level[(ghosts[i].x-1)*25 + ghosts[i].y] != 1 && level[(ghosts[i].x-1)*25 + ghosts[i].y] != 2)
            {
                Point point = new Point();
                point.x = ghosts[i].x-1;
                point.y = ghosts[i].y;
                point.point = level[(ghosts[i].x-1)*25 + ghosts[i].y];

                points.add(point);

            }
            if(level[ghosts[i].x*25 + ghosts[i].y+1] != 1 && level[ghosts[i].x*25 + ghosts[i].y+1] != 2)
            {
                Point point = new Point();
                point.x = ghosts[i].x;
                point.y = ghosts[i].y+1;
                point.point = level[ghosts[i].x*25 + ghosts[i].y+1];

                points.add(point);

            }
            if(level[ghosts[i].x*25 + ghosts[i].y-1] != 1 && level[ghosts[i].x*25 + ghosts[i].y-1] != 2)
            {
                Point point = new Point();
                point.x = ghosts[i].x;
                point.y = ghosts[i].y-1;
                point.point = level[ghosts[i].x*25 + ghosts[i].y-1];

                points.add(point);

            }
            if(points.size()!=0)
            {
                Random rnd = new Random();
                int rand = rnd.nextInt(points.size());


                level[points.get(rand).x*25 + points.get(rand).y] = 2;
                level[ghosts[i].x*25 + ghosts[i].y] = points.get(rand).point;
            }
        }

    }
}
