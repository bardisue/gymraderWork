package com.example.gymradar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class EditProgram extends AppCompatActivity {
    SQLiteDatabase DB;
    DBHelper2 DBHelper;
    ListView pgmodi_list;
    Button addButton;
    Button saveButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprogram);

        pgmodi_list = findViewById(R.id.pgmodi_listview);
        addButton = findViewById(R.id.addPgList);
        saveButton = findViewById(R.id.savePg);

        Intent intent = getIntent();
        int center_id = intent.getExtras().getInt("center_id");

        DBHelper = new DBHelper2(this, 1);
        DB = DBHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM program WHERE center_id = " + center_id +"", null);

        PgModiAdapter adapter = new PgModiAdapter();
        while(cursor.moveToNext()){
            adapter.addItemToList(cursor.getString(2),cursor.getString(3), cursor.getInt(4));
        }
        pgmodi_list.setAdapter(adapter);

        addButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                adapter.addItemToList("pgname", "pgperiod",0);
                adapter.notifyDataSetChanged();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper.deleteProgram(center_id);
                for(int i=0;i<adapter.list.size();i++){
                    ProgramListViewAdapterData item = (ProgramListViewAdapterData)adapter.getItem(i);
                    DBHelper.insertProgram(center_id, i, item.getName(), item.getPeriod(), item.getCost());
                }
                Intent intent = new Intent(getApplicationContext(), Info.class);
                intent.putExtra("center_id",center_id);
                startActivity(intent);
            }
        });
    }
}
