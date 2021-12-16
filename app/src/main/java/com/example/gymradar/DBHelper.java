package com.example.gymradar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "test.db"; // DBHelper 생성자. table마다 이름을 다르게 해야한다.

    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TrainingCenter(ID NUM, NAME TEXT, COORDINATEY REAL, COORDINATEX REAL, ADDR TEXT)");
    } // 생성과 동시에 진행되어야 하는 내용

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TrainingCenter");
        onCreate(db);
    }//변경된 부분이 있으면 작동

    public void insertTC(String name, double coordinateY, double coordinateX, String addr) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM TrainingCenter", null);
        cursor.moveToFirst();
        int n = cursor.getInt(0);
        int id;
        for(id=0;id<n;id++){
            cursor = db.rawQuery("SELECT EXISTS(SELECT 1 FROM TrainingCenter WHERE id = "+id+")", null);
            cursor.moveToFirst();
            if(cursor.getInt(0) == 0){ //id가 id인 행이 없을때
                break;
            }
        }
        db = getWritableDatabase();
        db.execSQL("INSERT INTO TrainingCenter VALUES("+id+",'" + name + "', " + coordinateY + ", " + coordinateX + " , '" +addr+ "')");
        db.close();
    }//Insert문 해당 table에 넣는다. NUM타입의 경우 " + 변수 + " 형태로, 문자열의 경우 '" + 변수 "' 형태로 넣을것


    public void updateTC(Integer id, String name, double coordinateY, double coordinateX, String addr) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TrainingCenter SET NAME = '"+name+"',ADDR = '" +addr+ "' ,COORDINATEY = " + coordinateX + ", COORDINATEY = " + coordinateX + "" + " WHERE ID = '" + id + "'");
        db.close();
    }

    public void deleteTC(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TrainingCenter WHERE ID = " + id + "");
        db.close();
    }

    public List<String> getResultTC() { // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        List<String> result = new ArrayList<>(); // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM TrainingCenter", null);//모든 데이터를 가져온다. select 문에는 일반적으로 Cursor을 사용
        while (cursor.moveToNext()) {
            result.add(cursor.getString(1) + "#" + cursor.getString(4) + " id:" +cursor.getString(0));
        }
        return result;
    }

    public String getById(int id) { // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "" ;// DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM TrainingCenter Where ID =  " + id + "", null);//모든 데이터를 가져온다. select 문에는 일반적으로 Cursor을 사용
        while (cursor.moveToNext()) {
            result = (cursor.getString(1) + "#" + cursor.getString(4) + " id:" +cursor.getString(0));
        }
        return result;
    }

    public Cursor getTC() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TrainingCenter", null);
        return cursor;
    }
    public List<Integer> getIds() { // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        List<Integer> result = new ArrayList<>(); // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM TrainingCenter", null);//모든 데이터를 가져온다. select 문에는 일반적으로 Cursor을 사용
        while (cursor.moveToNext()) {
            result.add(cursor.getInt(0));
        }
        return result;
    }

    public double getRat(int id) { // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        double result = 0; // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM TrainingCenter Where ID =" + id + "", null);//모든 데이터를 가져온다. select 문에는 일반적으로 Cursor을 사용
        while (cursor.moveToNext()) {
            result = cursor.getDouble(2);
        }
        return result;
    }
    public double getLog(int id) { // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        double result = 0; // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM TrainingCenter Where ID =" + id + "", null);//모든 데이터를 가져온다. select 문에는 일반적으로 Cursor을 사용
        while (cursor.moveToNext()) {
            result = cursor.getDouble(3);
        }
        return result;
    }
}