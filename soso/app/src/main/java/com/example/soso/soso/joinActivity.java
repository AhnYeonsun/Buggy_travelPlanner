package com.example.soso.soso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class joinActivity extends AppCompatActivity {

    public DatabaseReference userDatabase;
    public UserInfo mem;

    private EditText Name;
    private EditText newEmail;
    private EditText newPw;
    private EditText newPwAgain;
    private Button joinedBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Name = (EditText) findViewById(R.id.name);
        newEmail = (EditText) findViewById(R.id.newEmail);
        newPw = (EditText) findViewById(R.id.newPw);
        newPwAgain = (EditText) findViewById(R.id.newPwAgain);
        joinedBtn = (Button) findViewById(R.id.joinedBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        userDatabase = FirebaseDatabase.getInstance().getReference();

        joinedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString();
                String email = newEmail.getText().toString();
                String pw = newPw.getText().toString();
                String pwAgain = newPwAgain.getText().toString();

                if (newEmail == null) {
                    Toast.makeText(joinActivity.this, "Enter your email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPw == null) {
                    Toast.makeText(joinActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPwAgain == null) {
                    Toast.makeText(joinActivity.this, "Enter your password again", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(pw.equals(pwAgain))) {
                    Toast.makeText(joinActivity.this, "Not same passwords", Toast.LENGTH_SHORT).show();
                    return;
                }

                mem = new UserInfo(name, email, pw);

                String UID = email.substring(0, 2).concat(pw.substring(0, 2));
                userDatabase.child("Users").child(UID).child("Info").setValue(mem);
                Toast.makeText(joinActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
