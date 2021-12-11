package com.example.gymradar;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class AddTrainingCenterActivity extends AppCompatActivity implements MapView.MapViewEventListener{

    private MapView mapView;
    private ViewGroup mapViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training_center);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.add_map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapViewContainer.removeAllViews();
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        double latitude = mapPoint.getMapPointGeoCoord().latitude;
        double longitude = mapPoint.getMapPointGeoCoord().longitude;
        Intent intent = new Intent(AddTrainingCenterActivity.this, InsertInfoActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        mapViewContainer.removeView(mapView);
        startActivity(intent);
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cancel:
                startActivity(new Intent(AddTrainingCenterActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}