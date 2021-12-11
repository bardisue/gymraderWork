package com.example.gymradar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class DBHelper3 extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "test4.db";

    public DBHelper3(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Account(ID TEXT, Password TEXT, Username TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Account");
        onCreate(db);
    }

    public void insert(String Id, String Password, String Username) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Account VALUES('" + Id + "', '" + Password + "', '" + Username + "')");
        db.close();
    }


    public void updatePrice(String name, String period, String price) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Price SET PerPrice = '" + price + "' " + " WHERE NAME = '" + name + "' AND Period = '" + period + "'");
        db.close();
    }

    public void deletePrice(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Price WHERE NAME = '" + name + "'");
        db.close();
    }

    public List<String> getIds(){
        SQLiteDatabase db = getWritableDatabase();
        List<String> result = new ArrayList<>();
        String sql = "select * from Account";//이름과 기간을 받아서 해당하는 가격을 받아온다
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        return result;
    }

    public boolean validationLogin(String Id, String password){
        SQLiteDatabase db = getWritableDatabase();
        String result = "!@#$!!@!$";
        String sql = "select * from Account where ID='"+Id+"'";//이름과 기간을 받아서 해당하는 가격을 받아온다
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            result = cursor.getString(1);
        }
        return result.equals(password);
    }

    public String getUsername(String Id){
        SQLiteDatabase db = getWritableDatabase();
        String result = null;
        String sql = "select * from Account where ID='"+Id+"'";//이름과 기간을 받아서 해당하는 가격을 받아온다
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            result = cursor.getString(2);
        }
        return result;
    }
}