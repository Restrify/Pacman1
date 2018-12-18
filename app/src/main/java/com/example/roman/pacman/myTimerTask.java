package com.example.roman.pacman;

import android.content.Context;
import android.content.Intent;

import java.util.TimerTask;

public class myTimerTask extends TimerTask {
    SQLite myDb;
    String name;
    String points;
    Context context;

    public myTimerTask(SQLite _myDb, String _name, String _points, Context _context)
    {
        myDb = _myDb;
        name = _name;
        points = _points;
        context = _context;

    }
    @Override
    public void run() {
        myDb.insertScore(name, points);
        Intent intent = new Intent(context, Highscore.class);
        context.startActivity(intent);
    }
}
