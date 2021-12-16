package com.example.gymradar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.Math;

public class RecommendResultActivity extends AppCompatActivity {
    DBHelper5 db5;
    DBHelper db;
    DBHelper2 db2;
    DBHelper4 db4;

    private List<Integer> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<Integer> arraylist;
    private int latitude;
    private int longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);
        Intent getIntent = getIntent();
        latitude = Integer.parseInt(getIntent.getStringExtra("latitude"));//로그인한 이메일
        longitude = Integer.parseInt(getIntent.getStringExtra("longitude"));//로그인한 이름
        // 리스트를 생성한다.
        list = new ArrayList<Integer>();

        // 검색에 사용할 데이터을 미리 저장한다.
        list = settingList();

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<Integer>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        //adapter = new SearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);


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
    private List<Integer> settingList(){
        db = new DBHelper(RecommendResultActivity.this, 2);
        List<Integer> result = new ArrayList<Integer>();
        result =db.getIds();
        return result;
    }
    private List<Integer> getRecommendedCenter(List<Integer> centerList){
        List<Integer> list = centerList;
        db = new DBHelper(RecommendResultActivity.this, 2);
        db2 = new DBHelper2(RecommendResultActivity.this, 1);
        db4 = new DBHelper4(RecommendResultActivity.this, 1);
        db5 = new DBHelper5(RecommendResultActivity.this, 1);
        HashMap<Integer, Integer> scoreMap = new HashMap<Integer, Integer>();
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i<list.size(); i++){
            int id = list.get(i);
           // int score = DistanceScore(id,db.getRat(id), db.getLog(id))+ReviewScore(id)+EquipmentScore(id);
           // scoreMap.put(id,score);
        }
        return result;
    }
    private int DistanceScore(int id, double y, double x){
        double lat =y;
        double log = x;
        double meter = distance(latitude, longitude, lat, log, "meter");
        return 1;
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

}