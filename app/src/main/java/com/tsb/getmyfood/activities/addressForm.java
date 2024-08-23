package com.tsb.getmyfood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.tsb.getmyfood.R;
import com.tsb.getmyfood.models.UserModel;

public class addressForm extends AppCompatActivity {

    private String id, userName, userMail, userPass, getaddLine1, getaddLine2, getcity, getstate;
    private long getpin=0;
    private EditText addLine1, addLine2, pincode, city, state;
    private Button continueButton;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_form);

        addLine1=findViewById(R.id.addLine1);
        addLine2=findViewById(R.id.addLine2);
        pincode=findViewById(R.id.pincode);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        continueButton=findViewById(R.id.continueButton);
        database=FirebaseDatabase.getInstance();

        userName=getIntent().getStringExtra("userName");
        userMail=getIntent().getStringExtra("userMail");
        userPass=getIntent().getStringExtra("userPass");

        id=FirebaseAuth.getInstance().getCurrentUser().getUid();

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

    }

    private void createUser()
    {
        getaddLine1=addLine1.getText().toString();
        getaddLine2=addLine2.getText().toString();
        getpin=Long.parseLong(pincode.getText().toString());
        getcity=city.getText().toString();
        getstate=state.getText().toString();


        if(getaddLine1.equals("")) {
            Toast.makeText(addressForm.this, "Enter address line 1", Toast.LENGTH_SHORT).show();
            return;
        }

        if(getaddLine2.equals("")) {
            Toast.makeText(addressForm.this, "Enter address line 2", Toast.LENGTH_SHORT).show();
            return;
        }

        if(getpin==0) {
            Toast.makeText(this, "Enter pincode", Toast.LENGTH_SHORT).show();
            return;
        }

        if(getcity.equals("")) {
            Toast.makeText(addressForm.this, "Enter city", Toast.LENGTH_SHORT).show();
            return;
        }

        if(getstate.equals("")) {
            Toast.makeText(addressForm.this, "Enter state", Toast.LENGTH_SHORT).show();
            return;
        }

        UserModel userModel=new UserModel(userName, userMail, userPass, getaddLine1, getaddLine2, getpin, getcity, getstate);
        database.getReference().child("Users").child(id).setValue(userModel);

//        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(addressForm.this, HomeActivity.class));
        finish();

    }
}