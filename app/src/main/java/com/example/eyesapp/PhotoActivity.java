package com.example.eyesapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class PhotoActivity extends Activity {

    SurfaceView sv;
    ImageView iv_image;
    SurfaceHolder sHolder;
    Camera mCamera;
    private HolderCallback holderCallback;
    Camera.PictureCallback mCall;

    private int findFrontFacingCameraID() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_photo);

        int index = findFrontFacingCameraID();
        if (index == -1) {
            Toast.makeText(getApplicationContext(), "No front camera", Toast.LENGTH_LONG).show();
        } else {
            // iv_image = (ImageView) findViewById(R.id.image_view);
            sv = (SurfaceView) findViewById(R.id.surfaceView);
            sHolder = sv.getHolder();
            holderCallback = new HolderCallback();
            sHolder.addCallback(holderCallback);
            sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

    }

    public void goToInteraction(View view) {
        Intent intent1 = new Intent(PhotoActivity.this, ServerInteraction.class);
        startActivity(intent1);
    }


    class HolderCallback implements SurfaceHolder.Callback {
        int angle;


        public int getRoatationAngle(Activity mContext, int cameraId) {
            android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
            android.hardware.Camera.getCameraInfo(cameraId, info);
            int rotation = mContext.getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }
            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360; // compensate the mirror
            } else { // back-facing
                result = (info.orientation - degrees + 360) % 360;
            }
            return result;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            Camera.Parameters parameters = mCamera.getParameters();

            Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();


            switch (display.getRotation()) {
                case Surface.ROTATION_0: // This is display orientation
                    angle = 90; // This is camera orientation
                    break;
                case Surface.ROTATION_90:
                    angle = 0;
                    break;
                case Surface.ROTATION_180:
                    angle = 270;
                    break;
                case Surface.ROTATION_270:
                    angle = 180;
                    break;
                default:
                    angle = 90;
                    break;
            }
            Log.v("LOG_TAG", "angle: " + angle);
            mCamera.setDisplayOrientation(angle);


            mCamera.setParameters(parameters);
            mCamera.startPreview();
            //setRotateOrientation();

            mCall = new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                  /*  Uri uriTarget = getContentResolver().insert//(Media.EXTERNAL_CONTENT_URI, image);
                            (MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());

                    OutputStream imageFileOS;
                    try {
                        imageFileOS = getContentResolver().openOutputStream(uriTarget);
                        imageFileOS.write(data);
                        imageFileOS.flush();
                        imageFileOS.close();

                        Toast.makeText(MainActivity.this,
                                "Image saved: " + uriTarget.toString(), Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    //mCamera.startPreview();
                    Bitmap bmp;

                   /* bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    iv_image.setImageBitmap(bmp);*/
                    int angleToRotate = getRoatationAngle(PhotoActivity.this, Camera.CameraInfo.CAMERA_FACING_FRONT);
                    // Solve image inverting problem
                    angleToRotate = angleToRotate + 180;
                    Bitmap orignalImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Bitmap bitmapImage = rotate(orignalImage, angleToRotate);
                    String path = Environment.getExternalStorageDirectory().toString();
                    OutputStream fOut = null;
                    Integer counter = 0;
                    File file = new File(path, "FitnessGirl"+counter+".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
                    try {
                        fOut = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    // Bitmap pictureBitmap = getImageBitmap(myurl); // obtaining the Bitmap
                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                    try {
                        fOut.flush();
                        fOut.close();// Not really required
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // do not forget to close the stream



                }

                public  Bitmap rotate(Bitmap bitmap, int degree) {
                    int w = bitmap.getWidth();
                    int h = bitmap.getHeight();

                    Matrix mtx = new Matrix();
                    mtx.postRotate(degree);

                    return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
                }
            };


        }

        int getFrontCameraId() {
            Camera.CameraInfo ci = new Camera.CameraInfo();
            for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
                Camera.getCameraInfo(i, ci);
                if (ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) return i;
            }
            return -1; // No front-facing camera found
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            int index = getFrontCameraId();
            if (index == -1) {
                Toast.makeText(getApplicationContext(), "No front camera", Toast.LENGTH_LONG).show();
            } else {
                mCamera = Camera.open(index);
                Toast.makeText(getApplicationContext(), "With front camera", Toast.LENGTH_LONG).show();
            }
            mCamera = Camera.open(index);
            try {
                mCamera.setPreviewDisplay(holder);

            } catch (IOException exception) {
                mCamera.release();
                mCamera = null;
            }

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }


    }

}