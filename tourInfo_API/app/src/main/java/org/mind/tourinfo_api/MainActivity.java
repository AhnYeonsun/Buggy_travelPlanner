package org.mind.tourinfo_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //StrictMode.enableDefaults(); //ANR 발생 방지

        TextView result = (TextView) findViewById(R.id.tourInfo); //파싱된 결과
        String resultText = null;
        String[][] array = new String[100][3];

        try {
            resultText = new Task().execute().get();
            array = new Task().dataParser(resultText);

            result.setText(array[0][1].toString());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

