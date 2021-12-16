package com.example.gymradar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditEquipment extends AppCompatActivity {
    SQLiteDatabase DB;
    DBHelper4 DBHelper;
    ListView eqmodi_list;
    Button addButton;
    Button saveButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editequipment);

        eqmodi_list = findViewById(R.id.eqmodi_listview);
        addButton = findViewById(R.id.addList);
        saveButton = findViewById(R.id.saveEq);

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
                adapter.addItemToList("eqname",0);
                adapter.notifyDataSetChanged();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper.deleteEquipment(center_id);
                for(int i=0;i<adapter.list.size();i++){
                    EquipmentListViewAdapterData item = (EquipmentListViewAdapterData)adapter.getItem(i);
                    DBHelper.insertEquipment(center_id, i, item.getName(), item.getCount());
                }
                Intent intent = new Intent(getApplicationContext(), Info.class);
                intent.putExtra("center_id",center_id);
                startActivity(intent);
            }
        });
    }
}
