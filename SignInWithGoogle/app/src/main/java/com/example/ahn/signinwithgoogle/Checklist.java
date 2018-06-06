package com.example.ahn.signinwithgoogle;
//중분류, 소분류는 나중에 시간이 되면 마무리 짓기
//지금까지의 체크리스트 기능은 사용자 마음대로 추가 삭제 - update 2018.05.13
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

public class Checklist extends android.support.v4.app.Fragment {
    public static Checklist newInstance() {
        Checklist fragment = new Checklist();
        return fragment;
    }

    //Log.d(TAG, variable);
    SharedPreferences sh_Pref;
    SharedPreferences.Editor toEdit;

    public static String bucketList_FILE = "bucketList";

    ImageButton addbtn; // 항목 추가버튼
    ListView userItemList; //완성된 체크리스트
    EditText addBar; // 추가할 친구들
    AlertDialog.Builder builder; // 꾹 누르면 이벤트 생성(삭제할때 꾹 누르면 알림창떠서 삭제 가능하게, 나중에 추가할 것: 소분류 꾹 누르면 체크리스트에 추가가능하도록)
    ChecklistListViewAdapter adapter;
    Set<String> values;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view= super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_checklist, container, false);
        builder=new AlertDialog.Builder(getActivity());

        final ListView listview ;

        addBar=view.findViewById(R.id.addBar);
        addbtn=view.findViewById(R.id.addbtn);

        // Adapter 생성
        adapter = new ChecklistListViewAdapter(getActivity());

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.userItemList);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefernces();
                addBar.setText("");

                //입력 후 키보드 감추기
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(addBar.getWindowToken(), 0);
            }
        });

        listview.setAdapter(adapter);
        return view;
    }

    public void sharedPrefernces(){
        sh_Pref = getActivity().getSharedPreferences(bucketList_FILE,Context.MODE_PRIVATE);
        toEdit = sh_Pref.edit();
        values = new HashSet<>();
        values.add("AAAA");
        values.add("0");
        toEdit.putStringSet("A1", values);
        toEdit.commit();
    }

    public void applySharedPreference(){
        sh_Pref = getActivity().getSharedPreferences(bucketList_FILE,Context.MODE_PRIVATE);

//        if(sh_Pref!=null && sh_Pref.contains("A1")){
//            Set<String> ret = sh_Pref.getStringSet(values, new HashSet<String>());
//
//            adapter.addItem(name, isChecked);
//        }
    }
}