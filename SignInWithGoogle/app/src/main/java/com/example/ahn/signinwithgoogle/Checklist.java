package com.example.ahn.signinwithgoogle;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class checklist extends android.support.v4.app.Fragment {
    public static checklist newInstance() {
        checklist fragment = new checklist();
        return fragment;
    }

    ListView userItemList; //완성된 체크리스트
    Button modifyBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_checklist, container, false);

        final ListView listview;
        final ChecklistAdapter adapter;

        String bucketlist[] = getResources().getStringArray(R.array.bucketList);
        // Adapter 생성
        adapter = new ChecklistAdapter(getActivity());

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.userBucket);
        listview.setAdapter(adapter);

        for(int i=0;i<bucketlist.length;i++){
            adapter.addItem(bucketlist[i]);
            //adapter.addItem(버킷리스트, 체크여부)
        }

        modifyBtn = (Button) view.findViewById(R.id.modifyBtn);
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = Checklist_Modify.newInstance();
                ft.replace(R.id.bucketFragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }
}