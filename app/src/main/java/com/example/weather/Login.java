package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText username ,password;
    Button login;
    private int PERMISSON_CODE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        login = findViewById(R.id.Login);

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSON_CODE );

        }else {
            finish();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String User_name =username.getText().toString();
                String PassWord = password.getText().toString();


                if (TextUtils.isEmpty(User_name))
                {
                    username.setError("enter user name");
                }
                if (TextUtils.isEmpty(PassWord)){
                    password.setError("Enter password");
                }

                if (User_name.equals("admin")& PassWord.equals("admin123")){
                    startActivity(new Intent(Login.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(Login.this,"Check username and password",Toast.LENGTH_SHORT).show();


                }
            }
        });
    }
}