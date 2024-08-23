package com.tsb.getmyfood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tsb.getmyfood.MainActivity2;
import com.tsb.getmyfood.R;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)
        {
            startActivity(new Intent(HomeActivity.this, MainActivity2.class));
//            Toast.makeText(this, "please wait you are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            startActivity(new Intent(HomeActivity.this, login.class));
            finish();
        }
    }

    public void login(View view)
    {
        startActivity(new Intent(HomeActivity.this, login.class));
    }

    public void signUp(View view)
    {
        startActivity(new Intent(HomeActivity.this, signUp.class));
    }
}