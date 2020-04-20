package com.example.eyesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EyesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        String name = arguments.get("angle").toString();
        setContentView(new EyesAnimation(this,name));
    }
}
