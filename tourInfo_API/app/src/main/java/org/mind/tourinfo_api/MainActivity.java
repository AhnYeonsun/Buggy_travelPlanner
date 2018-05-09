package org.mind.tourinfo_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.StrictMode;

public class MainActivity extends AppCompatActivity {
    //HashMap<String, String> regionCodeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.enableDefaults(); //ANR 발생 방지

        TextView result = (TextView) findViewById(R.id.tourInfo); //파싱된 결과
        //String regionText = new regionCodeHashMap().main();
        String sigunguText = new codeHashMap().main();
        //String sigunguFinal = new String();

        result.setText(sigunguText);
    }
}

