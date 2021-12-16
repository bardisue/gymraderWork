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

    public boolean hasEquip(String equip, int id){
        SQLiteDatabase db = getReadableDatabase();
        boolean result = false; // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM equipment Where center_id =" + id + "", null);//모든 데이터를 가져온다. select 문에는 일반적으로 Cursor을 사용
        while (cursor.moveToNext()) {
            if(cursor.getString(2).equals(equip)){
                result = true;
            }
        }
        return result;
    }
}