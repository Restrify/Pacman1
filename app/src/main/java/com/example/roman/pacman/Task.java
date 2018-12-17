package com.example.roman.pacman;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;

public class Task extends AsyncTask<Object, Void, Integer> {

    @Override
    protected Integer doInBackground(Object... objects) {
        Object obj = objects[0];
        obj.canvas.drawBitmap(objects[0].bitmap, null,
                new Rect( obj.j* obj.width, obj.i * obj.height, (obj.j + 1) * obj.width, (obj.i + 1) * obj.height), null);

        return 1;
    }
}
