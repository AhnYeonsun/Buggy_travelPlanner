package com.example.soso.soso;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPlan extends AppCompatActivity {
    TextView date_first;
    TextView date_last;
    EditText plan_name;
    Button cal_first;
    Button cal_last;
    Button next;
    String startDate = "", endDate="", plan, temp1, temp2;
    DatePickerDialog datePickerDialog;

    private long calDateDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        //initiate buttons, and textview
        date_first = findViewById(R.id.date_first);
        date_last = findViewById(R.id.date_last);
        cal_first = findViewById(R.id.cal_first);
        cal_last = findViewById(R.id.cal_last);
        plan_name = findViewById(R.id.plan_name);
        next = findViewById(R.id.next);

        plan = plan_name.getText().toString();

        cal_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                date_first.setText(year + "-" + temp1 + "-" + temp2);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                startDate = date_first.getText().toString();

                temp1 = null;
                temp2 = null;
            }
        });

        cal_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                date_last.setText(year + "-" + temp1 + "-" + temp2);
                                endDate = date_last.getText().toString();
                                System.out.println(date_last.getText().toString());
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                /*endDate = date_last.getText().toString();
                System.out.println(endDate);*/
                temp1 = null;
                temp2 = null;
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doDifferent();
                Intent intent = new Intent(getApplicationContext(), PlanDetail.class);
                intent.putExtra("date",String.valueOf(calDateDays));
                startActivity(intent);
            }
        });


    }


    public void doDifferent() {
        /*String s=date_first.getText().toString();
        String e=date_last.getText().toString();*/

        String date1 = date_first.getText().toString();
        String date2 = date_last.getText().toString();

        try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
            Date FirstDate = format.parse(date1);
            Date SecondDate = format.parse(date2);


            // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
            // 연산결과 -950400000. long type 으로 return 된다.
            long calDate = FirstDate.getTime() - SecondDate.getTime();

            // Date.getTime() 은 해당날짜를 기준으로 1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            calDateDays = calDate / ( 24*60*60*1000);
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