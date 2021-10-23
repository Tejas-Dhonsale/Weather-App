package com.example.weather;

import androidx.annotation.NonNull;
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

import java.security.Permission;

public class Login extends AppCompatActivity {

    EditText username ,password;
    Button login;
   private int PERMISSON_CODE = 1;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSON_CODE){
            if (grantResults.length > 0 && grantResults[0]==getPackageManager().PERMISSION_GRANTED) {
                Toast.makeText(this,"Permisson Granted...",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"Please provide the permisson",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        login = findViewById(R.id.Login);

        


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