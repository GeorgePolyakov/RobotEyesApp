package com.example.eyesapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class EyesAnimation extends View {

    Paint p = new Paint();
    private float CIRCLE_ANIMATION;
    private long mStartTime;
    private float mEyeFirstAngle;
    private Timer myTimer;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mStartTime = getTime();
    }
    boolean flag;
    private void refreshAnim() {
        flag=true;
        if(flag==true) {
            myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    invalidate();
                }

            }, 1000);
            flag=false;
            timer.cancel();
        }
        else {

            return;
        }
    }

    Timer timer = new Timer();



    @Override
    protected void onDraw(Canvas canvas) {
        int x = getWidth();
        int y = getHeight();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        rotateFirstEye(canvas);
        rotateSecondEye(canvas);
        canvas.drawCircle(x * 0.25f, y / 2, 220, p);
        canvas.drawCircle(x * 0.75f, y / 2, 220, p);
    }

    public EyesAnimation(Context context, String angle) {
        super(context);
        Log.d("myTag", angle + "");
        CIRCLE_ANIMATION = (float) (Integer.parseInt(angle) * Math.PI / 180);
        Log.d("tag",CIRCLE_ANIMATION+"");
        refreshAnim();
    }

    private long getTime() { //возвращает текущее время в миллисекундах
        return System.nanoTime() / 1_000_000;
    }

    private void rotateFirstEye(Canvas canvas) {
        canvas.save();
        int centerX = getWidth() / 4;
        int centerY = getHeight() / 2;
        canvas.translate(centerX, centerY);
        mEyeFirstAngle += CIRCLE_ANIMATION;
        canvas.rotate(mEyeFirstAngle);
        float rad = Math.min(getWidth(), getHeight()) / 12;
        p.setStyle(Paint.Style.FILL);
        canvas.drawCircle(rad, rad, rad, p);
        p.setStyle(Paint.Style.STROKE);
        canvas.restore();
    }

    private void rotateSecondEye(Canvas canvas) {
        canvas.save();
        float centerX = getWidth() * 0.75f;
        int centerY = getHeight() / 2;
        canvas.translate(centerX, centerY);
        mEyeFirstAngle += CIRCLE_ANIMATION;
        canvas.rotate(mEyeFirstAngle);
        int rad = Math.min(getWidth(), getHeight()) / 12;
        p.setStyle(Paint.Style.FILL);
        canvas.drawCircle((float) rad, (float) rad, rad, p);
        p.setStyle(Paint.Style.STROKE);
        canvas.restore();
    }
}
