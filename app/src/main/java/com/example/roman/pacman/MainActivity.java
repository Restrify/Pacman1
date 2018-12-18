package com.example.roman.pacman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GameView gameView;
    private Handler handler = new Handler();
    private final static long TIMER_INTERVAL = 100;
    SharedPreferences sharedPref;


    SQLite myDB;
    TextView name;
    Spinner type;
    Button start;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        myDB = new SQLite(this);
        name = (TextView) findViewById(R.id.editTextName);
        type = (Spinner) findViewById(R.id.spinnerType);
        start = (Button) findViewById(R.id.button);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Lehká");
        arrayList.add("Střední");
        arrayList.add("Těžká");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, arrayList);

        type.setAdapter(spinnerAdapter);

        start.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("diff", Integer.toString(type.getSelectedItemPosition()));
        editor.commit();
        gameView = new GameView(this);
        setContentView(gameView);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!gameView.victory && !gameView.loss)
                {
                    gameView.invalidate();
                }
                else
                {
                    cancel();
                    Intent intent = new Intent(MainActivity.this, Image.class);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString("name", name.getText().toString());
                    editor.putString("points", Integer.toString(gameView.points));
                    editor.commit();

                    if(gameView.victory)
                    {
                        intent.putExtra("picture", 1);
                    }
                    else
                    {
                        intent.putExtra("picture", 2);
                    }
                    startActivity(intent);
                }
            };
        }, 0, TIMER_INTERVAL);
    }
}
