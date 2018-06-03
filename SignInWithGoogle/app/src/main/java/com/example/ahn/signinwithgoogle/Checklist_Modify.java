package com.example.ahn.signinwithgoogle;

//중분류, 소분류는 나중에 시간이 되면 마무리 짓기
//지금까지의 체크리스트 기능은 사용자 마음대로 추가 삭제 - update 2018.05.13

//디비에 버킷리스트 다 넣어주고, 리스트와 함께 0(체크안됨) 또는 1(체크됨)의 값을 넣어줌
//체크 될때랑 안될때, 실시간으로 디비에서 빼주고 더해주는식으로.
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class Checklist_Modify extends android.support.v4.app.Fragment {
    public static Checklist_Modify newInstance() {
        Checklist_Modify fragment = new Checklist_Modify();
        return fragment;
    }
    ListView userItemList; //완성된 체크리스트
    AlertDialog.Builder builder; // 꾹 누르면 이벤트 생성(삭제할때 꾹 누르면 알림창떠서 삭제 가능하게, 나중에 추가할 것: 소분류 꾹 누르면 체크리스트에 추가가능하도록)
    Button modifyEnd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_checklist_modify, container, false);

        builder = new AlertDialog.Builder(getActivity());

        final ListView listview;
        final ChecklistModifyAdapter adapter;
        //aboutLuggageType(view);

        String bucketlist[] = getResources().getStringArray(R.array.bucketList);
        // Adapter 생성
        adapter = new ChecklistModifyAdapter(getActivity());

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.userItemList);
        listview.setAdapter(adapter);

        for(int i=0;i<bucketlist.length;i++){
            adapter.addItem(bucketlist[i]);
            //adapter.addItem(버킷리스트, 체크여부)
        }

        modifyEnd = view.findViewById(R.id.modifyEnd);
        modifyEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().remove(Checklist_Modify.this).commit();
                fm.popBackStack();
            }
        });
        return view;
    }
}
