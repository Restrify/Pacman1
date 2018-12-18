package com.example.roman.pacman;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class Task extends AsyncTask<Object,Void , Void> {

    @Override
    protected Void doInBackground(Object... objects) {
        MediaPlayer mp = MediaPlayer.create( objects[0].context, objects[0].sound);
        mp.start();

        return null;
    }
}
