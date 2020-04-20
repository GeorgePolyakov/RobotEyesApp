package com.example.eyesapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
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

    private int findFrontFacingCameraID() {
        int cameraId = -1;
        // Search for the front facing camera
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
        if (index == -1){
            Toast.makeText(getApplicationContext(), "No front camera", Toast.LENGTH_LONG).show();
        }
        else
        {
            iv_image = (ImageView) findViewById(R.id.image_view);
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

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            Camera.Parameters parameters = mCamera.getParameters();
            mCamera.setParameters(parameters);
            mCamera.startPreview();

            Camera.PictureCallback mCall = new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    Uri uriTarget = getContentResolver().insert//(Media.EXTERNAL_CONTENT_URI, image);
                            (MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());

                    OutputStream imageFileOS;
                    try {
                        imageFileOS = getContentResolver().openOutputStream(uriTarget);
                        imageFileOS.write(data);
                        imageFileOS.flush();
                        imageFileOS.close();
                       // ServerInteraction serverInteraction = new ServerInteraction();


                        /*Toast.makeText(PhotoActivity.this,
                                "Image saved: " + data, Toast.LENGTH_LONG).show();*/
                        Log.d("xyi","aaaaaaaaa " + data.length);
                      //  Log.d("xyi","aaaaaaaaa " +   serverInteraction.connectServer(data));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //mCamera.startPreview();
                    Bitmap bmp;


                    bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                    iv_image.setImageBitmap(bmp);

                }
            };

            mCamera.takePicture(null, null, mCall);
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