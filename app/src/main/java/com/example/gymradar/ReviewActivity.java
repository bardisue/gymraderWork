package com.example.gymradar;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int centerID = getIntent().getExtras().getInt("center_id");

        db = new DBHelper(this, 1);

        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT NAME FROM TrainingCenter WHERE ID = " + centerID, null);
        String center_name = cursor.getString(0);

        ((TextView) findViewById(R.id.review_centerName)).setText(center_name);
    }
}