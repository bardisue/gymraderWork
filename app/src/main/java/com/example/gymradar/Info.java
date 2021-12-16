package com.example.gymradar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Info extends AppCompatActivity {
    SQLiteDatabase sqlDB;
    SQLiteDatabase programDB;
    SQLiteDatabase equipmentDB;
    SQLiteDatabase reviewDB;
    DBHelper centerDBHelper;
    DBHelper2 programDBHelper;
    DBHelper4 equipmentDBHelper;
    DBHelper5 reviewDBHelper;
    TextView center_name;
    ListView program_list;
    ListView equipment_list;
    ListView review_list;
    Button editProgram;
    Button editEquipment;
    Button writeReview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        center_name = findViewById(R.id.center_name);
        program_list = findViewById(R.id.program_list);
        equipment_list = findViewById(R.id.equipment_list);
        review_list = findViewById(R.id.review_list);
        editProgram = findViewById(R.id.editProgram);
        editEquipment = findViewById(R.id.editEquipment);
        writeReview = findViewById(R.id.review);

        Intent intent = getIntent();
        int center_id = intent.getExtras().getInt("center_id");

        //DB에서 정보 받아오기
        centerDBHelper = new DBHelper(this, 2);
        sqlDB = centerDBHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM TrainingCenter WHERE id = " + center_id +"", null);
        cursor.moveToFirst();
        String center_name_str = cursor.getString(1);
        center_name.setText(center_name_str);

        programDBHelper = new DBHelper2(this, 1);
        programDB = programDBHelper.getReadableDatabase();
        cursor = programDB.rawQuery("SELECT * FROM program WHERE center_id = "+center_id+"", null);
        ProgramListViewAdapter program_adapter = new ProgramListViewAdapter();
        while(cursor.moveToNext()){
            program_adapter.addItemToList(cursor.getString(2),cursor.getString(3), cursor.getInt(4));
        }
        program_list.setAdapter(program_adapter);

        equipmentDBHelper = new DBHelper4(this, 1);
        equipmentDB = equipmentDBHelper.getReadableDatabase();
        cursor = equipmentDB.rawQuery("SELECT * FROM equipment WHERE center_id = " + center_id +"", null);
        EquipmentListViewAdapter equipment_adapter = new EquipmentListViewAdapter();
        while(cursor.moveToNext()){
            equipment_adapter.addItemToList(cursor.getString(2), cursor.getInt(3));
        }
        equipment_list.setAdapter(equipment_adapter);

       reviewDBHelper = new DBHelper5(this, 1);
       reviewDB = reviewDBHelper.getReadableDatabase();
       cursor = reviewDB.rawQuery("SELECT * FROM review WHERE center_id = " + center_id +"", null);
        ReviewAdapter review_adapter = new ReviewAdapter();
        while(cursor.moveToNext()){
            review_adapter.addItemToList(cursor.getString(2), cursor.getFloat(3), cursor.getString(4));
        }
        review_list.setAdapter(review_adapter);


        editProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProgram.class);
                intent.putExtra("center_id",center_id);
                startActivity(intent);
            }
        });

        editEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditEquipment.class);
                intent.putExtra("center_id",center_id);
                startActivity(intent);
            }
        });

        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                intent.putExtra("center_id",center_id);
                startActivity(intent);
            }
        });
    }
}