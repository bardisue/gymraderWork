package com.example.gymradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewActivity extends AppCompatActivity {

    private DBHelper db;
    private DBHelper5 db5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        int center_id = intent.getExtras().getInt("center_id");


        db = new DBHelper(this, 2);
        db5 = new DBHelper5(this, 1);

        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT NAME FROM TrainingCenter WHERE ID = " + center_id, null);
        cursor.moveToFirst();
        String center_name = cursor.getString(0);

        ((TextView) findViewById(R.id.review_centerName)).setText(center_name);

        ((Button) findViewById(R.id.review_submitBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.isLogined == false) {
                    Toast.makeText(ReviewActivity.this,"로그인 후 작성가능합니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                float star_point = ((RatingBar) findViewById(R.id.review_starPoint)).getRating();
                String content = ((EditText) findViewById(R.id.review_content)).getText().toString();
                db5.addReview(center_id, MainActivity.loginedId, star_point, content);
                Toast.makeText(ReviewActivity.this,"리뷰를 등록하셨습니다", Toast.LENGTH_SHORT).show();
                //finish();
                Intent intent = new Intent(getApplicationContext(), Info.class);
                intent.putExtra("center_id",center_id);
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