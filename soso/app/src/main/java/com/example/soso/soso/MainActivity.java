package com.example.soso.soso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    String TAG = "logging";

    public DatabaseReference userDatabaseInMain;
    public DatabaseReference infoDatabase;
    TextView userID;
    Button addTravelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = (TextView)findViewById(R.id.userID);
        addTravelBtn = (Button)findViewById(R.id.addTravel);

        Intent receiveUserIdIntent = getIntent();
        Bundle receiveUserIdBundle = receiveUserIdIntent.getExtras();
        final String UID = receiveUserIdBundle.getString("UID");
        Log.d(TAG,UID);

        userDatabaseInMain = FirebaseDatabase.getInstance().getReference();
        infoDatabase = userDatabaseInMain.child("Users").child(UID).child("Info");
        

        String userName="";
        userID.setText(userName);

        addTravelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);

            }
        });
    }
}
