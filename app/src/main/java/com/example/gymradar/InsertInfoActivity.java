package com.example.gymradar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapPoint;

public class InsertInfoActivity extends AppCompatActivity implements MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private MapReverseGeoCoder geoCoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_info);
        Intent intent = getIntent();
        double latitude = intent.getExtras().getDouble("latitude");
        double longitude = intent.getExtras().getDouble("longitude");

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        Log.d("latitude", Double.toString(mapPoint.getMapPointGeoCoord().latitude));
        Log.d("longitude", Double.toString(mapPoint.getMapPointGeoCoord().longitude));

        geoCoder = new MapReverseGeoCoder("c7c87702be619b390ae59d2b201ae0de", mapPoint,
                InsertInfoActivity.this, InsertInfoActivity.this);
        geoCoder.startFindingAddress();

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
        ((TextView) findViewById(R.id.input_address)).setText(result);
    }
}