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
    long diff, diffDays;
    Button temp;
    TextView hi;

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

        temp=findViewById(R.id.temp);
        hi=findViewById(R.id.hi);

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
                //Intent intent = new Intent(getApplicationContext(), checklist.class);
                //startActivity(intent);
            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), Recommendation.class);
                //startActivity(intent);
                //doDifferent();
                //String t = Long.toString(diffDays);
                //hi.setText(t);
            }
        });

    }


    public void doDifferent() {
        String s=startDate;
        String e=endDate;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

            Date beginDate = formatter.parse(s);
            Date finishDate = formatter.parse(e);

            diff = finishDate.getTime() - beginDate.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}