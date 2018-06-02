package com.example.ahn.signinwithgoogle;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class AddPlan extends android.support.v4.app.Fragment {
    public static AddPlan newInstance() {
        AddPlan fragment = new AddPlan();
        return fragment;
    }
    EditText plan_name;
    TextView start_date;
    TextView end_date;
    Button createBtn;
    String startDate = "", endDate="", plan, temp1, temp2;
    String date1="", date2="";
    public String planTitle = "";
    DatePickerDialog datePickerDialog;
    boolean check=false;//마지막날짜가 더 늦은지 확인
    private long calDateDays;
    private AlertDialog.Builder builder;

    public String[] daysOfNewPlan;

    private FirebaseAuth mAuth;
    private DatabaseReference addPlan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_plan,container,false);

        start_date=view.findViewById(R.id.start_date);
        end_date=view.findViewById(R.id.end_date);
        createBtn=view.findViewById(R.id.createBtn);
        plan_name=view.findViewById(R.id.plan_name);


        final ListView planListview;
        final CreatePlanAdapter adapter;

        planListview = view.findViewById(R.id.planListview);
        adapter=new CreatePlanAdapter(getActivity()); //this -> getActivity()
        builder=new AlertDialog.Builder(getActivity());

        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                if (monthOfYear < 10) {
                                    temp1 = String.valueOf(monthOfYear + 1);
                                    temp1 = "0" + temp1;
                                } else
                                    temp1 = String.valueOf(monthOfYear + 1);
                                if (dayOfMonth < 10) {
                                    temp2 = String.valueOf(dayOfMonth);
                                    temp2 = "0" + temp2;
                                } else
                                    temp2 = String.valueOf(dayOfMonth);
                                start_date.setText(year + "-" + temp1 + "-" + temp2);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                startDate = start_date.getText().toString();

                temp1 = null;
                temp2 = null;
            }
        });

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                if (monthOfYear < 10) {
                                    temp1 = String.valueOf(monthOfYear + 1);
                                    temp1 = "0" + temp1;
                                } else
                                    temp1 = String.valueOf(monthOfYear + 1);
                                if (dayOfMonth < 10) {
                                    temp2 = String.valueOf(dayOfMonth);
                                    temp2 = "0" + temp2;
                                } else
                                    temp2 = String.valueOf(dayOfMonth);
                                end_date.setText(year + "-" + temp1 + "-" + temp2);
                                endDate = end_date.getText().toString();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                /*endDate = date_last.getText().toString();
                System.out.println(endDate);*/
                temp1 = null;
                temp2 = null;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
        addPlan = FirebaseDatabase.getInstance().getReference();
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //일정에 추가
                doDifferent();
                if(check){
                    String duration = start_date.getText().toString()+" - " +end_date.getText().toString();
                    adapter.addItem(plan_name.getText().toString(),duration);
                    System.out.println(plan_name.getText().toString()+start_date.getText().toString());
                    planTitle = plan_name.getText().toString();
                    plan_name.setText("");
                    start_date.setText("");
                    end_date.setText("");
                    check=false;

                    //yyyy-MM-dd
                    //여기서부터 이 버튼 끝까지 DB 넣어주기 위한 코드
                    int y1 = Integer.parseInt(date1.substring(0,4));
                    int m1 = Integer.parseInt(date1.substring(5,7));
                    int d1 = Integer.parseInt(date1.substring(8,10));
                    int m2 = Integer.parseInt(date2.substring(5,7));
                    int d2 = Integer.parseInt(date2.substring(8,10));
                    daysOfNewPlan = new String[(int)calDateDays+1];
                    String tempDate = date1;
                    int tempM = m1;
                    int tempD = d1;
                    int tempY = y1;
                    Log.d("NNNN :::", calDateDays+"");
                    if(m1==m2){
                        for (int i = 0; i < (int)calDateDays+1; i++){
                            tempDate = String.valueOf(tempY) + "-" + String.valueOf(tempM) + "-" + String.valueOf(tempD);
                            daysOfNewPlan[i] = tempDate;
                            tempD++;
                            Log.d("AAA", tempDate);
                        }
                    }
                    else if(m1 < m2){//달 넘어가는 경우
                        Log.d("AAA",tempDate);
                        for (int i = 0; i < (int)calDateDays+1; i++){
                            tempDate = String.valueOf(tempY) + "-" + String.valueOf(tempM) + "-" + String.valueOf(tempD);
                            daysOfNewPlan[i] = tempDate;
                            if((tempD==31)&&(tempM==1||tempM==3||tempM==5||tempM==7||tempM==8||tempM==10||tempM==12)){ //31일
                                if(m1==12){ //12월 31일
                                    tempM = 1;
                                    tempD = 1;
                                    tempY++;
                                }
                                else{ //00월 31일
                                    tempM++;
                                    tempD = 1;
                                }
                            }
                            else if((tempD==30)&&(tempM==4||tempM==6||tempM==9||tempM==11)){ //30일
                                tempM++;
                                tempD = 1;
                            }
                            else if((tempD==28)&&(tempM==2)){ //28일
                                tempM++;
                                tempD = 1;
                            }
                            else{
                                tempD++;
                            }
                            Log.d("BBB",tempDate);
                        }
                    }

                    for (int i = 0; i < (int)calDateDays+1; i++) {
                        ObjectForBlank o1 = new ObjectForBlank(i, "tempString", i);
                        //addPlan.child("Users").child(mUser.getUid()).child(planTitle).push().setValue(o1);
                        Log.d("BBB", daysOfNewPlan[i]);
                    }
                    Toasty.success(getActivity().getApplicationContext(), "성공 : 여행이 만들어졌어요!", Toast.LENGTH_LONG, true).show();
                }
                else{
                    //Toast.makeText(getApplicationContext(),"error: check your last date",Toast.LENGTH_LONG).show();
                    Toasty.warning(getActivity().getApplicationContext(), "오류 : 날짜를 확인해주세요!", Toast.LENGTH_LONG, true).show();
                }

                //입력 후 키보드 감추기
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(plan_name.getWindowToken(), 0);

                adapter.notifyDataSetChanged();
                GetDaysForTravel getDaysForTravel = new GetDaysForTravel(daysOfNewPlan);
                GetDaysForTravel getDaysForTravel1 = new GetDaysForTravel(planTitle);
            }
        });


        planListview.setAdapter(adapter);

        planListview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(getActivity().getApplicationContext(), PlanDetail.class);

                intent.putExtra("title", planTitle);
                intent.putExtra("days", calDateDays+"");
                intent.putExtra("startDay", date1);
                intent.putExtra("endDat", date2);
                startActivity(intent);
            }
        });
        planListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CreatePlanItem planItem = (CreatePlanItem)adapter.getItem(position);
                builder.setTitle("여행 삭제")
                        .setMessage(planItem.getName().toString()+"을/를 삭제하시겠습니까?")
                        .setCancelable(false) //뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                adapter.planItemList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        return view;
    }


    public void doDifferent() {

        date1 = start_date.getText().toString();
        date2 = end_date.getText().toString();

        try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
            Date FirstDate = format.parse(date1);
            Date SecondDate = format.parse(date2);

            // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
            // 연산결과 -950400000. long type 으로 return 된다.
            long calDate = FirstDate.getTime() - SecondDate.getTime(); //마지막날이 더 뒤면 당연히 -값

            // Date.getTime() 은 해당날짜를 기준으로 1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            calDateDays = calDate / ( 24*60*60*1000);
            System.out.println(calDate);
            System.out.println(calDateDays);
            if(calDateDays<=0) //마지막날이 더 뒤임
                check=true;
            calDateDays = Math.abs(calDateDays);

            //hi.setText(String.valueOf(calDateDays)); //+1ㅎㅐ줘야지 됨
        }
        catch(ParseException ex)
        {
            // 예외 처리
            ex.printStackTrace();
        }

    }

}