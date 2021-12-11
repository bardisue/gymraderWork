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

    public void insertEquipment(int center_id, String name, int count) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM equipment", null);
        cursor.moveToFirst();
        int n = cursor.getInt(0);
        int id;
        for(id=0;id<n;id++){
            cursor = db.rawQuery("SELECT EXISTS(SELECT 1 FROM equipment WHERE (center_id = "+center_id+" & eq_id = "+id+"))", null);
            cursor.moveToFirst();
            if(cursor.getInt(0) == 0){ //id가 id인 행이 없을때
                break;
            }
        }
        db = getWritableDatabase();
        db.execSQL("INSERT INTO equipment VALUES("+ center_id +","+id+",'" + name + "', '" + count + "')");
        db.close();
    }


    public void updateEquipment(int center_id, int eq_id, String name, int count) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE equipment SET count = '" + count + "' " + " WHERE center_id = "+center_id+" AND eq_id = " + eq_id + "");
        db.close();
    }

    public void deleteEquipment(int center_id, int eq_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM equipment WHERE center_id = " + center_id + " AND eq_id = "+eq_id+"");
        db.close();
    }
}