package com.example.gymradar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapPoint;

public class add extends AppCompatActivity implements MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private MapReverseGeoCoder geoCoder;
    private Button btnModify;
    private EditText edtName;
    private String addr;
    private EditText input_1month;
    private EditText machine;
    private EditText machine_num;
    DBHelper2 dbHelper2;
    DBHelper4 dbHelper4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbHelper2 = new DBHelper2(add.this, 1);
        dbHelper4 = new DBHelper4(add.this, 1);
        btnModify = findViewById(R.id.insert_btn);
        edtName = findViewById(R.id.input_name);
        input_1month = findViewById(R.id.input_1month);
        machine = findViewById(R.id.machine);
        machine_num = findViewById(R.id.machine_num);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //public void insertProgram(int center_id, String name, String period, int price)
                //dbHelper2.insertProgram(Integer.parseInt(edtName.getText().toString()), "이용권", "1개월", Integer.parseInt(input_1month.getText().toString()));
                //dbHelper4.insertEquipment(Integer.parseInt(edtName.getText().toString()), machine.getText().toString(),Integer.parseInt(machine_num.getText().toString()));
                startActivity(new Intent(add.this, MainActivity.class));
            }
        });
    }



    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
        addr = result;
    }
}