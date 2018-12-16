package com.example.roman.pacman;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GameView gameView;
    private Handler handler = new Handler();
    private final static long TIMER_INTERVAL = 100;

    SQLite myDB;
    TextView name;
    Spinner type;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        myDB = new SQLite(this);
        name = (TextView) findViewById(R.id.editTextName);
        type = (Spinner) findViewById(R.id.spinnerType);
        start = (Button) findViewById(R.id.button);

        start.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        gameView = new GameView(this);
        setContentView(gameView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!gameView.end)
                {
                    gameView.invalidate();
                }
                else
                {
                    cancel();
                    Intent intent = new Intent(MainActivity.this, Highscore.class);
                    startActivity(intent);
                    myDB.insertScore((String)name.getText(), Integer.toString(gameView.points));
                }
            };
        }, 0, TIMER_INTERVAL);
    }
}
