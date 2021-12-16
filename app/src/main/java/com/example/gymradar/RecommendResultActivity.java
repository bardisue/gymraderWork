package com.example.gymradar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;
import java.util.Map;

public class RecommendResultActivity extends AppCompatActivity {
    DBHelper5 db5;
    DBHelper db;
    DBHelper2 db2;
    DBHelper4 db4;

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private RecommendListViewAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;
    private double latitude;
    private double longitude;
    private String equip_1;
    private String equip_2;
    private String equip_3;
    private String distance;
    private String rating;
    private String equip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendresult);

        Intent getIntent = getIntent();
        latitude = Double.parseDouble(getIntent.getStringExtra("latitude"));
        longitude = Double.parseDouble(getIntent.getStringExtra("longitude"));
        equip_1 = getIntent.getStringExtra("equip_1");
        equip_2 = getIntent.getStringExtra("equip_2");
        equip_3 = getIntent.getStringExtra("equip_3");
        distance = getIntent.getStringExtra("distance");
        rating = getIntent.getStringExtra("rating");
        equip = getIntent.getStringExtra("equip");

        listView = (ListView) findViewById(R.id.listView);
        // 리스트를 생성한다.
        list = new ArrayList<String>();

        // 검색에 사용할 데이터을 미리 저장한다.
        list = getRecommendedCenter();
        Log.i("개수는", Integer.toString(list.size()));

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new RecommendListViewAdapter(arraylist, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);
        search();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SearchActivity.this ,list.get(position).toString(),Toast.LENGTH_LONG).show();

                Intent intent = new Intent(RecommendResultActivity.this, Info.class);
                int center_id = Integer.parseInt(list.get(position).toString().split("id:")[1]);
                intent.putExtra("center_id", center_id);
                Toast.makeText(RecommendResultActivity.this ,Integer.toString(center_id),Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

    private List<String> getRecommendedCenter(){
        db = new DBHelper(RecommendResultActivity.this, 2);
        db2 = new DBHelper2(RecommendResultActivity.this, 1);
        db4 = new DBHelper4(RecommendResultActivity.this, 1);
        db5 = new DBHelper5(RecommendResultActivity.this, 1);
        List<Integer> listID = db.getIds();
        HashMap<Integer, Integer> scoreMap = new HashMap<Integer, Integer>();
        List<String> result = new ArrayList<String>();
        for(int i = 0; i<listID.size(); i++){
            int id = listID.get(i);
            int score = DistanceScore(db.getRat(id), db.getLog(id))+ReviewScore(db5.getRatingEv(id))+EquipmentScore(id);//+EquipmentScore(id);
            scoreMap.put(id,score);
        }
        List<Integer> listKeySet = new ArrayList<>(scoreMap.keySet());
        Collections.sort(listKeySet, (value1, value2) -> (scoreMap.get(value2).compareTo(scoreMap.get(value1))));
        for(Integer key : listKeySet) { result.add(db.getById(key)); }


        List<String> final_result = new ArrayList<String>();
        for(int i = 0; i<5; i++){
            final_result.add(result.get(i));
        }
        return final_result;
    }
    private int DistanceScore( double y, double x){
        int score = 0;
        double lat =y;
        double log = x;
        double meter = distance(latitude, longitude, lat, log, "meter");
        if(meter <=1000.0){
            score = 100;
        }
        else if(meter <= 3000.0){
            score = 60;
        }
        else{
            score = 20;
        }
        if(distance.equals("1순위")){
            score = score * 3;
        }
        else if(distance.equals("2순위")){
            score = score * 2;
        }
        return score;
    }
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    private int ReviewScore(float star){
        int score;
        if(4<=star){
            score = 100;
        }
        else if(3<=star){
            score = 80;
        }
        else if(2<=star){
            score = 60;
        }
        else if(1<=star) {
            score = 40;
        }
        else {
            score = 20;
        }
        if(rating.equals("1순위")){
            score = score * 3;
        }
        else if(rating.equals("2순위")){
            score = score * 2;
        }
        return score;
    }
    private  int EquipmentScore(int id){
        db4 = new DBHelper4(RecommendResultActivity.this, 1);
        int score = 10;
        if(db4.hasEquip(equip_1, id)){
            score += 30;
        }
        if(db4.hasEquip(equip_2, id)){
            score += 30;
        }
        if(db4.hasEquip(equip_3, id)){
            score += 30;
        }
        if(equip.equals("1순위")){
            score = score * 3;
        }
        else if(equip.equals("2순위")){
            score = score * 2;
        }
        return score;
    }

    public static LinkedHashMap<String, String> sortMapByValue(Map<String, String> map) {
        List<Map.Entry<String, String>> entries = new LinkedList<>(map.entrySet());
        Collections.sort(entries, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    public void search() {
        for(int i = 0;i < arraylist.size(); i++)

            list.add(arraylist.get(i));

        adapter.notifyDataSetChanged();
    }

}