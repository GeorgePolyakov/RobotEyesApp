package com.example.eyesapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class EyesAnimation extends View {

    Paint p = new Paint();
    private final float CIRCLE_ANIMATION = 0.5f;
    private long mStartTime;
    private float mEyeFirstAngle;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mStartTime = getTime();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = getWidth();
        int y = getHeight();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        rotateFirstEye(canvas);
        rotateSecondEye(canvas);
        invalidate();
        canvas.drawCircle(x * 0.25f, y / 2, 220, p);
        canvas.drawCircle(x * 0.75f, y / 2, 220, p);

    }

    public EyesAnimation(Context context) {
        super(context);
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
