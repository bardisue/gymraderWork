package com.example.gymradar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class PgModiAdapter extends BaseAdapter {
    ArrayList<ProgramListViewAdapterData> list = new ArrayList<ProgramListViewAdapterData>();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();
        final ViewHolder holder;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.pgmodi_listview,viewGroup,false);
            holder = new ViewHolder();
            holder.editText1 = (EditText) view.findViewById(R.id.editpg_name);
            holder.editText2 = (EditText) view.findViewById(R.id.editpg_period);
            holder.editText3 = (EditText) view.findViewById(R.id.editpg_cost);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        holder.ref = i;

        //이제 아이템에 존재하는 텍스트뷰 객체들을 view객체에서 찾아 가져온다
        EditText etName = (EditText) view.findViewById(R.id.editpg_name);
        EditText etPeriod = (EditText) view.findViewById(R.id.editpg_period);
        EditText etCost = (EditText) view.findViewById(R.id.editpg_cost);

        //현재 포지션에 해당하는 아이템에 글자를 적용하기 위해 list배열에서 객체를 가져온다.
        ProgramListViewAdapterData listdata = list.get(i);

        //가져온 객체안에 있는 글자들을 각 뷰에 적용한다
        holder.editText1.setText(listdata.getName());
        holder.editText2.setText(listdata.getPeriod());
        holder.editText3.setText(Integer.toString(listdata.getCost())); //원래 int형이라 String으로 형 변환

        Button delButton = (Button) view.findViewById(R.id.editpg_delete);
        delButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                deleteItem(i);
            }
        });

        holder.editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.get(holder.ref).setName(s.toString());
            }
        });

        etPeriod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.get(holder.ref).setPeriod(s.toString());
            }
        });

        etCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(!str.isEmpty()){
                    list.get(holder.ref).setCost(Integer.parseInt(str));
                }
            }
        });

        return view;
    }

    //ArrayList로 선언된 list 변수에 목록을 채워주기 위함 다른방시으로 구현해도 됨
    public void addItemToList(String name, String period, int cost){
        ProgramListViewAdapterData listdata = new ProgramListViewAdapterData();
        //값들의 조립이 완성된 listdata객체 한개를 list배열에 추가
        listdata.setName(name);
        listdata.setPeriod(period);
        listdata.setCost(cost);
        list.add(listdata);
    }

    public void deleteItem(int i){
        if(list.size() > i){
            list.remove(i);
            notifyDataSetChanged();
        }
    }

}
