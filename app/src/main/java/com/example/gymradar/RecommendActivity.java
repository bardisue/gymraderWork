package com.example.gymradar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecommendActivity extends AppCompatActivity {
    EditText et1;
    EditText et2;
    EditText et3;
    Button button_R;
    private String latitude;
    private String longitude;

    String[] items = {"1순위", "2순위", "3순위"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Intent getIntent = getIntent();
        latitude = getIntent.getStringExtra("latitude");
        longitude = getIntent.getStringExtra("longitude");

        button_R = (Button) findViewById(R.id.btn_recommend);

        et1 = (EditText) findViewById(R.id.equip_1);
        et2 = (EditText) findViewById(R.id.equip_2);
        et3 = (EditText) findViewById(R.id.equip_3);

        List<String> equipList= new ArrayList<String>();
        equipList.add(et1.getText().toString());
        equipList.add(et2.getText().toString());
        equipList.add(et3.getText().toString());

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_2);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner_3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items
        );

        // 드롭다운 클릭 시 선택 창
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 스피너에 어댑터 설정
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);

        // 스피너에서 선택 했을 경우 이벤트 처리
        button_R.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendActivity.this, RecommendResultActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("equip_1", et1.getText().toString());
                intent.putExtra("equip_2", et1.getText().toString());
                intent.putExtra("equip_3", et1.getText().toString());
                intent.putExtra("distance", spinner1.getSelectedItem().toString());
                intent.putExtra("rating", spinner2.getSelectedItem().toString());
                intent.putExtra("equip", spinner3.getSelectedItem().toString());
                startActivity(intent);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cancel:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}