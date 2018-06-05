package com.example.ahn.signinwithgoogle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SetDetail extends AppCompatActivity {
    Button saveBtn;
    EditText spot, memo;
    Button map;
    Double x, y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_detail);

        saveBtn=findViewById(R.id.saveBtn);
        spot= findViewById(R.id.spot);
        memo = findViewById(R.id.memo);
        map = findViewById(R.id.map);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                //Intent intent = new Intent();
                if(!spot.getText().toString().equals(null))
                {
                    Log.d("coming?", "yeah");
                    intent.putExtra("spot",spot.getText().toString());
                    intent.putExtra("memo", memo.getText().toString());
                    intent.putExtra("dayposition", intent.getFlags());
                    intent.putExtra("MapX", x);
                    intent.putExtra("MapY", y);

                    setResult(Activity.RESULT_OK,intent);
                }
                finish();
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// 욘똔리더님 여기에 지도(위치 확인)해주세여
                //startActivity(new Intent(this, MapActivity.class));
                Intent goMap = new Intent(getApplicationContext() , MapActivity.class);
                startActivity(goMap);
            }
        });
    }
}