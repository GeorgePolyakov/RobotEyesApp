package com.example.eyesapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EyesActivity extends AppCompatActivity {

    private ImageView eyes1, eyes2;
    private ImageView eyes;
    //float angle = 0f;
    String angle="";
    float x1, y1, x2, y2;
    AnimatorSet animatorSet;
    TextView warningText;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyes);
        warningText = findViewById(R.id.eyesView);
        eyes1 = findViewById(R.id.eyes1);
        eyes2 = findViewById(R.id.eyes2);


        Bundle arguments = getIntent().getExtras();
       // angle = arguments.get("angle").toString();
        angle = "false";

        if(angle.equals("true")){
            warningText.setTextColor(getResources().getColor(R.color.green_color));
            warningText.setText("Доступ разрешен!!!");
            eyes1.setImageDrawable(getResources().getDrawable(R.drawable.ic_1));
            eyes1.setImageDrawable(getResources().getDrawable(R.drawable.ic_1));
        } else{
            warningText.setTextColor(getResources().getColor(R.color.red_color));
            warningText.setText("Доступ запрещен!!!");
            eyes1.setImageDrawable(getResources().getDrawable(R.drawable.i2));
            eyes2.setImageDrawable(getResources().getDrawable(R.drawable.i2));
        }

       // angle = Float.parseFloat(arguments.get("angle").toString());
       /* eyes1 = findViewById(R.id.eyes1);
        eyes2 = findViewById(R.id.eyes2);
        x1 = eyes1.getTranslationX();
        y1 = eyes1.getTranslationY();
        x2 = eyes1.getTranslationX();
        y2 = eyes1.getTranslationY();



        float sumX = (angle == 0) ? 0 : (angle == 45) ? 50 : (angle == 90) ? 50 : (angle == 135) ? 50 : (angle == 180) ? 0 : (angle == 225) ? -50 : (angle == 270) ? -50 : 0;
        float sumY = (angle == 0) ? 50 : (angle == 45) ? 50 : (angle == 90) ? 0 : (angle == 135) ? -50 : (angle == 180) ? -50 : (angle == 225) ? -50 : (angle == 270) ? 0 : 0;

        animatorSet = new AnimatorSet();
        ObjectAnimator firstEyeX = ObjectAnimator.ofFloat(eyes1, "rotationX", x1 + sumX);
        ObjectAnimator firstEyeY = ObjectAnimator.ofFloat(eyes1, "rotationY", y1 + sumY);
        ObjectAnimator secondEyeX = ObjectAnimator.ofFloat(eyes2, "rotationX", x2 + sumX);
        ObjectAnimator secondEyeY = ObjectAnimator.ofFloat(eyes2, "rotationY", y2 + sumY);


        animatorSet.play(firstEyeX).with(firstEyeY).with(secondEyeX).with(secondEyeY);
        animatorSet.setDuration(1500);
        animatorSet.start();
        eyes1.setImageDrawable(getResources().getDrawable(R.drawable.i2));
        eyes2.setImageDrawable(getResources().getDrawable(R.drawable.i2));*/


    }
}
