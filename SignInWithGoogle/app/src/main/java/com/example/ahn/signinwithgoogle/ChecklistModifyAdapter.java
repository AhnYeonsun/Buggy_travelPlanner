package com.example.ahn.signinwithgoogle;

/**
 * Created by SOSO on 2018-05-14.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChecklistModifyAdapter extends BaseAdapter{
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Checklist_item_Modify> listViewItemList = new ArrayList<Checklist_item_Modify>() ;
    ImageView deleteBtn;
    TextView itemListView;
    CheckBox checkbox;

    AlertDialog.Builder builder;
    // ListViewAdapter의 생성자
    public ChecklistModifyAdapter(Context c) {
        builder= new AlertDialog.Builder(c);
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checklist_item_modify, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        itemListView = (TextView) convertView.findViewById(R.id.itemList) ;
        deleteBtn=convertView.findViewById(R.id.deletebtn);
        checkbox = convertView.findViewById(R.id.checkbox);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final Checklist_item_Modify listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        itemListView.setText(listViewItem.getText());
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("체크리스트에서 빼기")
                        .setMessage(listViewItem.getText().toString()+"을/를 안가져갈래요?")
                        .setCancelable(false) //뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("뺄래요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                listViewItemList.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("가져가요!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        itemListView.setText(listViewItem.getText());

        if(listViewItem.getCheck()==1){ checkbox.setChecked(true); }
        else{checkbox.setChecked(false);}

        if(checkbox.isChecked()){}//디비에 넣어줌
        else{} //디비에서 뺴줌

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String text) {
        Checklist_item_Modify item = new Checklist_item_Modify();
        item.setText(text);
        listViewItemList.add(item);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String text, int ischk) {
        Checklist_item_Modify item = new Checklist_item_Modify();
        item.setText(text);
        item.setCheck(ischk);
        listViewItemList.add(item);
    }
}
