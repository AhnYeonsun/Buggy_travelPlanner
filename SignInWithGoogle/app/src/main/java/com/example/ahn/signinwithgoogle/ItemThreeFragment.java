package com.example.ahn.signinwithgoogle;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemThreeFragment extends Fragment {
    public static ItemThreeFragment newInstance() {
        ItemThreeFragment fragment = new ItemThreeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_item_three, container, false);

        ArrayList<Member>list = new ArrayList<Member>();
        list.add(new Member("사용자 KEY 정보","사용자 키값 받아서 넣어주기~"));
        list.add(new Member("이메일","어떤 문의사항도 죠아여"));
        list.add(new Member("정보","term of application"));

        ArrayList<HashMap<String,String>> mapList = new ArrayList<HashMap<String,String>>();

        for(Member m : list){
            HashMap map = new HashMap();
            map.put("id",m.id);
            map.put("name",m.name);
            mapList.add(map);
        }
        ListView listView=view.findViewById(R.id.infoListview);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),mapList,android.R.layout.simple_list_item_2,
                new String[]{"id","name"},new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView); //리스트뷰 높이 조절


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(position==1) //이메일 보내주기
               {
                   Uri uri = Uri.parse("mailto:youn_306@naver.com");
                   Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                   startActivity(intent);
               }
               else if(position==2)
               {
                   Uri uri = Uri.parse("https://buggy.imweb.me/");
                   Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                   startActivity(intent);
               }
            }
        });
       return view;
    }
    class Member{
        String id;
        String name;
        public Member(String id, String name){
            this.id=id;
            this.name=name;
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }

}

