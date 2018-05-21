package com.example.soso.soso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    String TAG = "logging";

    public DatabaseReference userDatabase;
    //public DatabaseReference infoDatabase;
    //public UserInfo mem;

    private Button joinBtn;
    private Button loginBtn;
    private EditText inputEmail;
    private EditText inputPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        userDatabase = FirebaseDatabase.getInstance().getReference();
        //infoDatabase = userDatabase.child("Users").child("Info");

        inputEmail = (EditText)findViewById(R.id.inputEmail);
        inputPw = (EditText)findViewById(R.id.inputPw);

        //login button
        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputEmail==null){
                    Toast.makeText(LoginActivity.this,"Enter your email address again",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputPw==null){
                    Toast.makeText(LoginActivity.this,"Enter your password again",Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = inputEmail.getText().toString();
                String pw = inputPw.getText().toString();

                final String checkUID = email.substring(0,2).concat(pw.substring(0,2));

                userDatabase.child("Users").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.d(TAG,dataSnapshot.getKey().toString());
                        Log.d(TAG,dataSnapshot.getValue().toString());
                        if(dataSnapshot.getKey().equals(checkUID)){
                            Intent loginIntent = new Intent(getApplicationContext(),MainActivity.class);
                            Bundle loginBundle = new Bundle();
                            loginBundle.putString("UID", checkUID);
                            loginIntent.putExtras(loginBundle);
                            startActivity(loginIntent);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        //join button
        joinBtn = (Button)findViewById(R.id.joinBtn);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent joinIntent = new Intent(getApplicationContext(),joinActivity.class);
                startActivity(joinIntent);
            }
        });
    }
}
