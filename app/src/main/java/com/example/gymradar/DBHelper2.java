package com.example.gymradar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper2 extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "test2.db";

    public DBHelper2(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE program(center_id NUM, pg_id NUM, NAME TEXT, Period TEXT, PerPrice NUM)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS program");
        onCreate(db);
    }

    public void insertProgram(int center_id, String name, String period, int price) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM program", null);
        cursor.moveToFirst();
        int n = cursor.getInt(0);
        int id;
        for(id=0;id<n;id++){
            cursor = db.rawQuery("SELECT EXISTS(SELECT 1 FROM program WHERE (center_id = "+center_id+" & pg_id = "+id+"))", null);
            cursor.moveToFirst();
            if(cursor.getInt(0) == 0){ //id가 id인 행이 없을때
                break;
            }
        }
        db = getWritableDatabase();
        db.execSQL("INSERT INTO program VALUES("+ center_id +","+id+",'" + name + "', '" + period + "', '" + price + "')");
        db.close();
    }


    public void updateProgram(int center_id, int pg_id, int price) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE program SET PerPrice = '" + price + "' " + " WHERE center_id = "+center_id+" AND pg_id = " + pg_id + "");
        db.close();
    }

    public void deleteProgram(int center_id, int pg_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM program WHERE center_id = " + center_id + " AND pg_id = "+pg_id+"");
        db.close();
    }

    public String getPerPrice(int center_id, int pg_id){
        SQLiteDatabase db = getWritableDatabase();
        String id = null;
        String sql = "select * from program where center_id="+center_id+" AND pg_id="+pg_id+"";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            id = cursor.getString(2);
        }

        return id;
    }
}