package com.example.roman.pacman;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import java.util.Timer;

public class Image extends Activity {

    Timer timer = new Timer();
    SQLite myDB;
    ImageView imageView;
    SharedPreferences sharedPref;
    String name;
    String points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = (ImageView) findViewById(R.id.imageView);
        myDB = new SQLite(this);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        name = sharedPref.getString("name", "errorn");
        points = sharedPref.getString("points", "errorp");

        Intent intent = getIntent();
        Integer image = intent.getIntExtra("picture", 0);
        if(image==1)
        {
            Drawable res = getResources().getDrawable(R.drawable.pacmanvictory);
            imageView.setImageDrawable(res);
        }
        else
        {
            Drawable res = getResources().getDrawable(R.drawable.pacmanloss);
            imageView.setImageDrawable(res);
        }

        myTimerTask task = new myTimerTask(myDB, name, points,this);
        timer.schedule(task, 5000);


    }
}
