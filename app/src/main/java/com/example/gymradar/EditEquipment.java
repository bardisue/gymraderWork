package com.example.gymradar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class EditEquipment extends AppCompatActivity {
    SQLiteDatabase DB;
    DBHelper4 DBHelper;
    ListView eqmodi_list;
    Button addButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editequipment);

        eqmodi_list = findViewById(R.id.eqmodi_listview);
        addButton = findViewById(R.id.addList);

        Intent intent = getIntent();
        int center_id = intent.getExtras().getInt("center_id");

        DBHelper = new DBHelper4(this, 1);
        DB = DBHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM equipment WHERE center_id = " + center_id +"", null);
        //
        EqModiAdapter adapter = new EqModiAdapter();
        while(cursor.moveToNext()){
            adapter.addItemToList(cursor.getString(2), cursor.getInt(3));
        }
        eqmodi_list.setAdapter(adapter);

        addButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                adapter.addItemToList("",0);
                adapter.notifyDataSetChanged();
            }
        });
    }
}