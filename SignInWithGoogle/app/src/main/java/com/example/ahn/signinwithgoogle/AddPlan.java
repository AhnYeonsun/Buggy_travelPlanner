package com.example.ahn.signinwithgoogle;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPlan extends AppCompatActivity {
    EditText plan_name;
    TextView start_date;
    TextView end_date;
    Button createBtn;
    String startDate = "", endDate="", plan, temp1, temp2;
    String date1="", date2="";
    DatePickerDialog datePickerDialog;
    boolean check=false;//마지막날짜가 더 늦은지 확인
    private long calDateDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        start_date=findViewById(R.id.start_date);
        end_date=findViewById(R.id.end_date);
        createBtn=findViewById(R.id.createBtn);
        plan_name=findViewById(R.id.plan_name);


        final ListView planListview;
        final CreatePlanAdapter adapter;

        planListview = findViewById(R.id.planListview);
        adapter=new CreatePlanAdapter(this);


        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddPlan.this,
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
                datePickerDialog = new DatePickerDialog(AddPlan.this,
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


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //일정에 추가
                doDifferent();
                if(check){
                    String duration = start_date.getText().toString()+" - " +end_date.getText().toString();
                    adapter.addItem(plan_name.getText().toString(),duration);
                    System.out.println(plan_name.getText().toString()+start_date.getText().toString());
                    plan_name.setText("");
                    start_date.setText("Start Date");
                    end_date.setText("End Date");
                    check=false;
                }
                else
                    Toast.makeText(getApplicationContext(),"error: check your last date",Toast.LENGTH_LONG).show();
                //입력 후 키보드 감추기
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(plan_name.getWindowToken(), 0);

                adapter.notifyDataSetChanged();
            }
        });

        planListview.setAdapter(adapter);

        planListview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(getApplicationContext(), PlanDetail.class);
                intent.putExtra("title", plan_name.getText().toString());
                intent.putExtra("days", calDateDays+"");
                intent.putExtra("startDay", date1);
                intent.putExtra("endDat", date2);
                startActivity(intent);
            }
        });
    }

    public void doDifferent() {
        /*String s=date_first.getText().toString();
        String e=date_last.getText().toString();*/

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