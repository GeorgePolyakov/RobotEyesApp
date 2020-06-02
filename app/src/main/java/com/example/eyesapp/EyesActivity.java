package com.example.eyesapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class EyesActivity extends AppCompatActivity {

    private ImageView eyes1;
    private ImageView eyes2;
    float angle = 0f;
    float x1, y1, x2, y2;
    AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyes);


        Bundle arguments = getIntent().getExtras();
        angle = Float.parseFloat(arguments.get("angle").toString());
        Log.d("xyi",angle + "aaaaaaaaaaaaaaaaa");

        eyes1 = findViewById(R.id.eyes1);
        eyes2 = findViewById(R.id.eyes2);
        x1 = eyes1.getTranslationX();
        y1 = eyes1.getTranslationY();
        x2 = eyes1.getTranslationX();
        y2 = eyes1.getTranslationY();

        eyes1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

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

            }
        });

    }
}
