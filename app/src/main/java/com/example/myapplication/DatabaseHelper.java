package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "billItemsDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE billItems (id INTEGER PRIMARY KEY, date TEXT, name TEXT, cost1 TEXT, cost2 TEXT, cost3 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS billItems");
    }

    public void insertData (String date, String name, String cost1, String cost2, String cost3) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("name", name);
        contentValues.put("cost1", cost1);
        contentValues.put("cost2", cost2);
        contentValues.put("cost3", cost3);
        sqLiteDatabase.insert("billItems", null, contentValues);
    }

    public void deleteData (int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String[] arguments = new String[] {String.valueOf(id)};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM billItems WHERE id = ?", arguments);
        if (cursor.getCount() > 0) {
            sqLiteDatabase.delete("billItems", "id = ?", arguments);
        }
    }

    public Cursor getData () {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM billItems", null);
    }
}
