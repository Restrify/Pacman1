package com.example.roman.pacman;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Highscore extends Activity {

    SQLite mydb;
    private ListView obj;
    public static ArrayList<Long> arrayListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        mydb = new SQLite(this);
        //naplnim pole polozek
        mydb.setAllContacs();
        //ziskam do jedno listu vsechny polozky
        ArrayList arrayList = mydb.getAllContacsName();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayList);

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);



    }
}
