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

public class EqModiAdapter extends BaseAdapter {
    ArrayList<EquipmentListViewAdapterData> list = new ArrayList<EquipmentListViewAdapterData>();

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
            view = inflater.inflate(R.layout.eqmodi_listview,viewGroup,false);
        }

        //이제 아이템에 존재하는 텍스트뷰 객체들을 view객체에서 찾아 가져온다
        EditText etName = (EditText) view.findViewById(R.id.editeq_name);
        EditText etCount = (EditText) view.findViewById(R.id.editeq_count);

        //현재 포지션에 해당하는 아이템에 글자를 적용하기 위해 list배열에서 객체를 가져온다.
        EquipmentListViewAdapterData listdata = list.get(i);

        //가져온 객체안에 있는 글자들을 각 뷰에 적용한다
        etName.setText(listdata.getName());
        etCount.setText(Integer.toString(listdata.getCount())); //원래 int형이라 String으로 형 변환

        Button delButton = (Button) view.findViewById(R.id.editeq_delete);
        delButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                deleteItem(i);
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listdata.setName(s.toString());
            }
        });

        etCount.addTextChangedListener(new TextWatcher() {
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
                    listdata.setCount(Integer.parseInt(str));
                }
            }
        });

        return view;
    }

    //ArrayList로 선언된 list 변수에 목록을 채워주기 위함 다른방시으로 구현해도 됨
    public void addItemToList(String name, int count){
        EquipmentListViewAdapterData listdata = new EquipmentListViewAdapterData();
        //값들의 조립이 완성된 listdata객체 한개를 list배열에 추가
        listdata.setName(name);
        listdata.setCount(count);
        list.add(listdata);
    }

    public void deleteItem(int i){
        if(list.size() > i){
            list.remove(i);
            notifyDataSetChanged();
        }
    }


}
