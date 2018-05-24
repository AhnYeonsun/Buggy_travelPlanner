package com.example.soso.soso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {
    ImageButton addPlanBtn;
    ImageButton checklistBtn;
    ImageButton recommendBtn;
    ImageButton settingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialize the widgets
        addPlanBtn=findViewById(R.id.addplanBtn);
        checklistBtn=findViewById(R.id.checklistBtn);
        recommendBtn=findViewById(R.id.recommendBtn);
        settingBtn=findViewById(R.id.settingBtn);

        addPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPlan.class);
                startActivity(intent);
            }
        });

        checklistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Checklist.class);
                startActivity(intent);
            }
        });

        recommendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), fragment_Recommend.class);
                startActivity(intent);
            }
        });
    }
}
