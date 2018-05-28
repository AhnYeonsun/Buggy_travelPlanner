package com.example.ahn.signinwithgoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SetDetail extends AppCompatActivity {
    Button btn;
    EditText spot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_detail);

        btn=findViewById(R.id.button);
        spot= findViewById(R.id.spot);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Test.this, MainActivity.class);
                Intent intent = getIntent();
                intent.putExtra("spot",spot.getText().toString());
                intent.putExtra("dayposition", intent.getFlags());
                setResult(RESULT_OK,intent);
                Toast.makeText(getApplicationContext(),"어디갈꺼야? "+spot.getText().toString(),Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
