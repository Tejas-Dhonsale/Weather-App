package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout Home;
    private ProgressBar Pb;
    private ImageView Background, Search, weathercon;
    private TextView City_name, Temp, Condition;
    private TextInputEditText TI_city;
    private RecyclerView RecyclerView;
    private ArrayList<Rvmodel> rvmodelArrayList;
    private RvAdapter rvAdapter;
    private LocationManager locationManager;
    private int PERMISSON_CODE = 1;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Home = findViewById(R.id.home);
        Pb = findViewById(R.id.pb);
        Background = findViewById(R.id.background);
        Search = findViewById(R.id.search);
        City_name = findViewById(R.id.city_name);
        Temp = findViewById(R.id.temp);
        Condition = findViewById(R.id.condition);
        weathercon = findViewById(R.id.Weathercon);
        TI_city = findViewById(R.id.editcity);
        RecyclerView = findViewById(R.id.recyclerview);
        rvmodelArrayList = new ArrayList<>();
        rvAdapter = new RvAdapter(this, rvmodelArrayList);
        RecyclerView.setAdapter(rvAdapter);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSON_CODE);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        cityName =getCityname(location.getLongitude(),location.getLatitude());
        getweatherinfo(cityName);


        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = TI_city.getText().toString();
                if (city.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    TI_city.setText(cityName);
                    getweatherinfo(city);
                }
            }
        });


    }

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

    private String getCityname(double longitude, double latitude) {
        String CityName = "Not Found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses =gcd.getFromLocation(latitude,longitude,10);


            for (Address Adr: addresses){
                if (Adr!=null){
                    String city =Adr.getLocality();

                    if (city!=null && !city.equals("")){
                        cityName =city;
                    }else{
                        Toast.makeText(MainActivity.this,"City Not Found",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;

    }

    private void getweatherinfo(String cityName){
        String url = "http://api.weatherapi.com/v1/forecast.json?key=54d567be60f9406193653910212210&q="+ cityName +"&days=1&aqi=yes&alerts=yes";
        City_name.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Pb.setVisibility(View.GONE);
                Home.setVisibility(View.VISIBLE);
                rvmodelArrayList.clear();

                try {
                    String temperature = response.getJSONObject("current").getString("temp_c");
                    Temp.setText(temperature+"°c");
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition =response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon =response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionIcon)).into(weathercon);
                    Condition.setText(condition);

                    if (isDay==1){
                        //morning Image
                        Picasso.get().load("https://images.unsplash.com/photo-1541119638723-c51cbe2262aa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1173&q=80").into(Background);
                    }else {
                        //night Image
                        Picasso.get().load("https://images.unsplash.com/photo-1507502707541-f369a3b18502?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=388&q=80").into(Background);
                    }
                    JSONObject forecasetobj = response.getJSONObject("forecast");
                    JSONObject forecasetO = forecasetobj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray = forecasetO.getJSONArray("hour");

                    for (int i=0; i<hourArray.length();i++){
                        JSONObject hourObj =hourArray.getJSONObject(i);
                        String time =hourObj.getString("time");
                        String temp = hourObj.getString("temp_c");
                        String img =hourObj.getJSONObject("condition").getString("icon");

                        rvmodelArrayList.add(new Rvmodel(time,temp,img));
                    }
                    rvAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Enter valid city name", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


}