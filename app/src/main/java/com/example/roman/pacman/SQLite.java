package com.example.roman.pacman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class SQLite extends SQLiteOpenHelper{

        public static final String DATABASE_NAME = "Highscore.db";
        public static final String CONTACTS_TABLE_NAME = "scores";
        public static final String CONTACTS_COLUMN_ID = "id";
        public static final String CONTACTS_COLUMN_NAME = "name";
        public static final String CONTACTS_COLUMN_SCORE = "score";
        public static final String CONTACTS_COLUMN_DATE = "date";

        public static ArrayList<String> arrayList = new ArrayList<String>();

        public SQLite(Context context)
        {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL("create table contacts " + "(id integer primary key, name text)");
            db.execSQL("CREATE TABLE " + CONTACTS_TABLE_NAME + " (id INTEGER PRIMARY KEY, name TEXT, score INTEGER, date DATETIME)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
            onCreate(db);
        }

        public boolean insertContact(String name, String score, Date date) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CONTACTS_COLUMN_NAME, name);
            contentValues.put(CONTACTS_COLUMN_SCORE, score);
            contentValues.put(CONTACTS_COLUMN_DATE, date.getTime());
            db.insert(CONTACTS_TABLE_NAME, null, contentValues);
            return true;
        }

        //Cursor representuje vracena data
        public Cursor getData(int id){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery("select * from " + CONTACTS_TABLE_NAME + " where id=" + id + "", null);
            //Cursor res =  db.rawQuery( "select * from contacts LIMIT 1 OFFSET "+id+"", null );
            return res;
        }

        public void setAllContacs() {
            arrayList.clear();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( "select * from contacts", null );
            res.moveToFirst();

            while(res.isAfterLast() == false){
                arrayList.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
                arrayList.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_SCORE)));
                res.moveToNext();
                arrayList.get(0);
            }
        }

        public ArrayList<String> getAllContacsName() {
            return arrayList;
        }

        public void removeAll() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(CONTACTS_TABLE_NAME, "1", null);
        }

        public void removeContact(Integer id) {
            SQLiteDatabase db = this.getWritableDatabase();
//        String whereClause = "id=?";
//        String[] whereArgs = new String[] { String.valueOf(id) };
//        db.delete(CONTACTS_TABLE_NAME, whereClause, whereArgs);
//        String query =  "DELETE FROM " + CONTACTS_TABLE_NAME + " WHERE "
//                + CONTACTS_COLUMN_ID + " = '" + id + "'";
//        db.execSQL(query);
            db.delete(CONTACTS_TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
            db.execSQL("UPDATE " + CONTACTS_TABLE_NAME + " SET id = id - 1 WHERE id > " + id + ";");
            Log.d("del", "Item of id " + id + " successfully deleted" );

        }
    }
