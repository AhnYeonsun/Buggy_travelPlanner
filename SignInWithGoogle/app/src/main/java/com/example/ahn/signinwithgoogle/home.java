package com.example.ahn.signinwithgoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    Button addPlanBtn;
    Button checklistBtn;
    Button recommendBtn;
    Button settingBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        Toast.makeText(getApplicationContext(), "Welcome!!!! "+mUser.getUid(), Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(getApplicationContext(), Recommend.class);
                startActivity(intent);
            }
        });
    }
}
