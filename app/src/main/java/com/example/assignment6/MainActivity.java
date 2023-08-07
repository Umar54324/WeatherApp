package com.example.assignment6;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    final String apiKey = "d286ec4c165287bf0a038b562531e644";
    final String url = "https://api.openweathermap.org/data/2.5/onecall?lat=30.267153&lon=-97.743057&exclude=minutely,alerts&units=imperial&appid=d286ec4c165287bf0a038b562531e644";
    TextView curTempText;
    TextView curHumidityText;
    TextView curWindSpeed;
    TextView curPressure;
    TextView h1;
    TextView h2;
    TextView h3;
    TextView h4;
    TextView h5;
    TextView h1Display;
    TextView h2Display;
    TextView h3Display;
    TextView h4Display;
    TextView h5Display;
    TextView high;
    TextView low;
    TextView days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curTempText = findViewById(R.id.Temperature);
        curHumidityText = findViewById(R.id.humidityDisplay);
        curWindSpeed = findViewById(R.id.windDisplay);
        curPressure = findViewById(R.id.pressureDisplay);
        h1 = findViewById(R.id.t1);
        h2 = findViewById(R.id.t2);
        h3 = findViewById(R.id.t3);
        h4 = findViewById(R.id.t4);
        h5 = findViewById(R.id.t5);
        h1Display = findViewById(R.id.t1display);
        h2Display = findViewById(R.id.t2display);
        h3Display = findViewById(R.id.t3display);
        h4Display = findViewById(R.id.t4display);
        h5Display = findViewById(R.id.t5display);
        high = findViewById(R.id.Highs);
        low = findViewById(R.id.Lows);
        days = findViewById(R.id.Days);

            updateWeather(curTempText);



    }
    public void updateWeather(View view){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                //String out = "";
                try {
                    int[] hourtemps = new int[5];
                    //int[] times = new int[5];
                    String[] daysArray = {"Sunday", "Monday", "Tuesday", "Wendesday", "Thursday", "Friday", "Saturday"};
                    Date now = new Date();
                    int currentHour = now.getHours()+1;
                    int origHour = currentHour;
                    if(currentHour>12){
                        currentHour-=12;
                    }
                    int currentDay = now.getDay();
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject jsonObjectCurrent = jsonResponse.getJSONObject("current");
                   // JSONObject jsonObjectHourly = jsonResponse.getJSONObject("hourly");
                    JSONArray jsonHourlyArray = jsonResponse.getJSONArray("hourly");
                    JSONArray jsonDailyArray = jsonResponse.getJSONArray("daily");
                    String highs ="";
                    String lows="";
                    for(int i=1; i<6; i++){
                        JSONObject jsonHourI = jsonHourlyArray.getJSONObject(i);
                        hourtemps[i-1] = (int)(Math.round(jsonHourI.getDouble("temp")));
                       // times[i] = jsonHourI.getInt("dt");
                    }
                    for(int i=1; i<8; i++){
                        JSONObject jsonDay = jsonDailyArray.getJSONObject(i);
                        JSONObject jsonThisTemp = jsonDay.getJSONObject("temp");
                        highs += (int)(Math.round(jsonThisTemp.getDouble("max"))) + "°F\n\n";
                        lows += (int)(Math.round(jsonThisTemp.getDouble("min"))) + "°F\n\n";
                        //highDaily.add(jsonThisTemp.getDouble(2));
                        //lowDaily.add(jsonThisTemp.getDouble(1));
                    }
//                   // JSONObject jsonObjectCurrent = jsonArray.getJSONObject(0);
                    int curTemp = (int)(Math.round(jsonObjectCurrent.getDouble("temp")));
                    int humidity = (int)(Math.round(jsonObjectCurrent.getInt("humidity")));
                    int windspeed = (int)(Math.round(jsonObjectCurrent.getInt("wind_speed")));
                    int pres = (int)(Math.round(jsonObjectCurrent.getInt("pressure")));
                    String humid = humidity + "%";
                    String ct = "" +curTemp + "°F";
                    String wind = windspeed + "mph";
                    String pressure = pres + " millibars";
                    //Log.d("temp", ct);
                    curTempText.setText(ct);
                    curHumidityText.setText(humid);
                    curWindSpeed.setText(wind);
                    curPressure.setText(pressure);
                    h1.setText(currentHour + AmorPm(origHour));
                    h1Display.setText(hourtemps[0] + "°F");
                    currentHour++;
                    if(currentHour>12){
                        currentHour-=12;
                    }
                    origHour++;
                    if(origHour==24){
                        origHour=0;
                    }
                    h2.setText(currentHour + AmorPm(origHour));
                    h2Display.setText(hourtemps[1] + "°F");
                    currentHour++;
                    if(currentHour>12){
                        currentHour-=12;
                    }
                    origHour++;
                    if(origHour==24){
                        origHour=0;
                    }
                    h3.setText(currentHour + AmorPm(origHour));
                    h3Display.setText(hourtemps[2] + "°F");
                    currentHour++;
                    if(currentHour>12){
                        currentHour-=12;
                    }
                    origHour++;
                    if(origHour==24){
                        origHour=0;
                    }
                    h4.setText(currentHour + AmorPm(origHour));
                    h4Display.setText(hourtemps[3] + "°F");
                    currentHour++;
                    if(currentHour>12){
                        currentHour-=12;
                    }
                    origHour++;
                    if(origHour==24){
                        origHour=0;
                    }
                    h5.setText(currentHour + AmorPm(origHour));
                    h5Display.setText(hourtemps[4] + "°F");
                    high.setText(highs);
                    low.setText(lows);
                    days.setText(printDays(currentDay, daysArray));
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
    public String printDays(int currentDay, String[] daysArray){
        String result = "";
        currentDay+=1;
        for(int i=0; i<7; i++){
            result +=daysArray[currentDay] + "\n\n";
            currentDay++;
            if(currentDay==7){
                currentDay=0;
            }
        }
        return result;
    }
    public String AmorPm(int origHur){
        if(origHur<12) {
            return " A.M.";
        }
        return " P.M.";
    }
}