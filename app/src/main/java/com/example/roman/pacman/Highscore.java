package com.example.roman.pacman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Highscore extends Activity implements View.OnClickListener {

    SQLite mydb;
    private ListView obj;
    Button button;
    public static ArrayList<Long> arrayListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        mydb = new SQLite(this);
        //naplnim pole polozek
        mydb.setAllContacs();
        //ziskam do jedno listu vsechny polozky
        ArrayList arrayList = mydb.getAllScoresName();

        ListAdapter listAdapter = new ListAdapter(this, R.layout.listviewitems, arrayList);

        obj = (ListView)findViewById(R.id.listView1);
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(this);
        obj.setAdapter(listAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Highscore.this, MainActivity.class);
        startActivity(intent);
    }
}
