package com.example.gymradar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper5 extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "test5.db"; // DBHelper 생성자. table마다 이름을 다르게 해야한다.

    public DBHelper5(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE review(review_id NUM, center_id NUM, user_id TEXT, star_point NUM , content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS review");
        onCreate(db);
    }

    public void addReview(int center_id, String user_id, float star_point, String content) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM review", null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        id++;
        db.execSQL("INSERT INTO review VALUES("+id+",'" + center_id + "', " + user_id + ", " + star_point+ " , '" +content+ "')");
        db.close();
    }

}
