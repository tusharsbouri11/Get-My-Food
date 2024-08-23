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

public class signUp extends AppCompatActivity {

    private Button login, signup;
    private EditText name, email, password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        name=findViewById(R.id.name);
        email=findViewById(R.id.emailId);
        password=findViewById(R.id.password);
        auth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signUp.this, com.tsb.getmyfood.activities.login.class));
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser()
    {
        String userName = name.getText().toString();
        String userMail=email.getText().toString();
        String userPass=password.getText().toString();

        if(userName.equals("")) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(userMail.equals("")) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPass.equals("")) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPass.length() < 6) {
            Toast.makeText(this, "Password length should greater than 6 letters", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userMail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(signUp.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(signUp.this, addressForm.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("userMail", userMail);
                    intent.putExtra("userPass", userPass);
                    Toast.makeText(signUp.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(signUp.this, "Error "+task.getException(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}