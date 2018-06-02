package com.example.ahn.signinwithgoogle;

//중분류, 소분류는 나중에 시간이 되면 마무리 짓기
//지금까지의 체크리스트 기능은 사용자 마음대로 추가 삭제 - update 2018.05.13

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class Checklist extends android.support.v4.app.Fragment {
    public static Checklist newInstance() {
        Checklist fragment = new Checklist();
        return fragment;
    }

    String TAG = "logging";

    //Log.d(TAG, variable);

    ImageButton addbtn; // 항목 추가버튼
    ListView category; //대분류
    ListView detailedList; //소분류
    ListView userItemList; //완성된 체크리스트
    EditText addBar; // 추가할 친구들
    AlertDialog.Builder builder; // 꾹 누르면 이벤트 생성(삭제할때 꾹 누르면 알림창떠서 삭제 가능하게, 나중에 추가할 것: 소분류 꾹 누르면 체크리스트에 추가가능하도록)

    String luggageTypeList[] = {"여행소지품", "미용용품", "샤워용품", "전자용품", "필수용품", "일상용품", "의약품", "기타"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_checklist, container, false);

        builder = new AlertDialog.Builder(getActivity());

        final ListView listview;
        final ChecklistListViewAdapter adapter;
        aboutLuggageType(view);
        addBar = view.findViewById(R.id.addBar);
        addbtn = view.findViewById(R.id.addbtn);

        // Adapter 생성
        adapter = new ChecklistListViewAdapter(getActivity());

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.userItemList);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addItem(addBar.getText().toString());
                addBar.setText("");

                //입력 후 키보드 감추기
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(addBar.getWindowToken(), 0);
            }
        });

        listview.setAdapter(adapter);
        return view;
    }

    //대분류
    public void aboutLuggageType(View view)
    {
        category=view.findViewById(R.id.luggageType);
        ArrayAdapter<String> LugguageTypeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, luggageTypeList);
        category.setAdapter(LugguageTypeAdapter);
    }
}
