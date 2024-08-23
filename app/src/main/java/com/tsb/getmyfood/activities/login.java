package com.tsb.getmyfood.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tsb.getmyfood.R;

public class login extends AppCompatActivity {

    private EditText email, password;
    private Button signup, login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);
        email =findViewById(R.id.emailId);
        password =findViewById(R.id.password);
        auth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,  signUp.class));
                finish();
            }
        });
        
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    private void loginUser()
    {
        String userMail=email.getText().toString();
        String userPass=password.getText().toString();

        if(userMail.equals(""))
        {
            Toast.makeText(this, "Enter email id", Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPass.equals(""))
        {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userMail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(login.this, HomeActivity.class));
                            finish();
                        }
                        else
                            Toast.makeText(login.this, "Error "+task.isSuccessful(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}