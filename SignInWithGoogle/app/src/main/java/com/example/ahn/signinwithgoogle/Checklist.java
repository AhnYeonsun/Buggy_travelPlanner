package com.example.ahn.signinwithgoogle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

public class Checklist extends android.support.v4.app.Fragment {
    public static Checklist newInstance() {
        Checklist fragment = new Checklist();
        return fragment;
    }

    SharedPreferences sh_Pref;
    SharedPreferences.Editor toEdit;

    public static String bucketList_FILE = "bucketList";

    ImageButton addbtn; // 항목 추가버튼
    ListView userItemList; //완성된 체크리스트
    EditText addBar; // 추가할 친구들
    AlertDialog.Builder builder; // 꾹 누르면 이벤트 생성(삭제할때 꾹 누르면 알림창떠서 삭제 가능하게, 나중에 추가할 것: 소분류 꾹 누르면 체크리스트에 추가가능하도록)
    ChecklistListViewAdapter adapter;
    Set<String> values;

    private FirebaseAuth mAuth;
    private DatabaseReference getBucketList;
    private DatabaseReference addUserBucketList;
    private DatabaseReference getUserBucketList;
    private DatabaseReference firstCheck;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_checklist, container, false);
        builder = new AlertDialog.Builder(getActivity());

        ProgressDialog di = new ProgressDialog(getActivity());
        Progress_dialog dialog = new Progress_dialog(di, 4);
        dialog.execute();

        final ListView listview;

        addBar = view.findViewById(R.id.addBar);
        addbtn = view.findViewById(R.id.addbtn);

        // Adapter 생성
        adapter = new ChecklistListViewAdapter(getActivity());

        //get list from DB to adapter
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        getBucketList = FirebaseDatabase.getInstance().getReference().child("DefaultBucketList");
        addUserBucketList = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("BucketList");

        //If first data load to User's DB, then initialize bucketList
        firstCheck = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("BucketList");
        ValueEventListener checkNullListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.exists()))
                    UpdateBucketListDB();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }; firstCheck.addListenerForSingleValueEvent(checkNullListener);

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.userItemList);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBar.setText("");

                //입력 후 키보드 감추기
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(addBar.getWindowToken(), 0);
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listview.setAdapter(adapter);
            }
        }, 3000);

        return view;
    }

    private void UpdateBucketListDB(){
        ValueEventListener addUserBucketListListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Checklist_item c = child.getValue(Checklist_item.class);
                    addUserBucketList.child(child.getKey()).setValue(c);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        getBucketList.addListenerForSingleValueEvent(addUserBucketListListener);
    }

    @Override
    public void onResume(){
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Load Users bucketList
                getUserBucketList = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("BucketList");
                ValueEventListener getBucketListListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Checklist_item c = child.getValue(Checklist_item.class);
                            adapter.addItem(c.list, c.check);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                getUserBucketList.addListenerForSingleValueEvent(getBucketListListener);
            }
        }, 1000);

    }
}
