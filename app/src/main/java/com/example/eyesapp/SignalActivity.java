package com.example.eyesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SignalActivity extends AppCompatActivity {
    String ip = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);
        startSignal();
        Intent intent = getIntent();
        ip = intent.getStringExtra("ipKey");
        Log.d("ipTag",ip + "AAAAAAAAAAAAAAAAAAAAaaa");

    }

    public void startSignal() {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int i = 0;

            @Override
            public void run() {

                String postUrl = "http://" + ip + ":" + "1000" + "/";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("message", "d");

                HttpUtility.newRequest(postUrl, HttpUtility.METHOD_GET, params, new HttpUtility.Callback() {
                    @Override
                    public void OnSuccess(String response) {
                        int responseIndex = 0;
                        Log.d("ServerOnSuccess", response);
                        Log.d("ServerOnSuccess", Thread.currentThread().getName());
                        responseIndex = response.indexOf("true");
                        if (responseIndex != -1) {
                            timer.cancel();
                            Intent intent = new Intent(SignalActivity.this, PhotoActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void OnError(int status_code, String message) {

                    }
                });

            }
        };
        timer.schedule(timerTask, 0, 3000);
    }
}
