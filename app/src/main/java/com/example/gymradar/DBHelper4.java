package com.example.gymradar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper4 extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "test4.db";

    public DBHelper4(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE equipment(center_id NUM, eq_id NUM, NAME TEXT, COUNT NUM)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS equipment");
        onCreate(db);
    }

    public void insertEquipment(int center_id, int eq_id, String name, int count) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO equipment VALUES("+ center_id +","+eq_id+",'" + name + "', '" + count + "')");
        db.close();
    }


    public void updateEquipment(int center_id, int eq_id, String name, int count) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE equipment SET count = '" + count + "' " + " WHERE center_id = "+center_id+" AND eq_id = " + eq_id + "");
        db.close();
    }

    public void deleteEquipment(int center_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM equipment WHERE center_id = " + center_id +"");
        db.close();
    }
}