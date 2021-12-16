package com.example.gymradar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {
    ArrayList<ReviewAdapterData> list = new ArrayList<ReviewAdapterData>();

    @Override
    public int getCount() {
        return list.size(); //그냥 배열의 크기를 반환하면 됨
    }

    @Override
    public Object getItem(int i) {
        return list.get(i); //배열에 아이템을 현재 위치값을 넣어 가져옴
    }

    @Override
    public long getItemId(int i) {
        return i; //그냥 위치값을 반환해도 되지만 원한다면 아이템의 num 을 반환해도 된다.
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.review_listview,viewGroup,false);
        }

        TextView tvUserid = (TextView)view.findViewById(R.id.rv_userid);
        RatingBar rbStarpoint = (RatingBar) view.findViewById(R.id.rv_starpoint);
        TextView tvContent = (TextView)view.findViewById(R.id.rv_content);

        ReviewAdapterData listdata = list.get(i);

        tvUserid.setText(listdata.getUserId());
        rbStarpoint.setRating(listdata.getStarPoint());
        tvContent.setText(listdata.getContent());

        return view;
    }

    public void addItemToList(String userid, float starpoint, String content){
        ReviewAdapterData listdata = new ReviewAdapterData();

        listdata.setUserId(userid);
        listdata.setStarPoint(starpoint);
        listdata.setContent(content);

        list.add(listdata);

    }
}
