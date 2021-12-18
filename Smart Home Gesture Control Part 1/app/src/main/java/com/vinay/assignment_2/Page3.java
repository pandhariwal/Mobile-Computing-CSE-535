package com.vinay.assignment_2;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@SuppressWarnings("ALL")
public class Page3 extends Activity implements SurfaceHolder.Callback{
    private String VIDEO_PATH_NAME = Environment.getExternalStorageDirectory().getAbsolutePath()+"/video/";
    private String VIDEO_NAME = "";
    private MediaRecorder mMediaRecorder;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private View mRecordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        Toast.makeText(getApplicationContext(), "Press the record button to start recording.", Toast.LENGTH_SHORT).show();
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Button uploadButton = findViewById(R.id.upload);
        uploadButton.setEnabled(false);

        mRecordButton =(Button) findViewById(R.id.recordButton);

        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Thread th1=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(mMediaRecorder!=null) mMediaRecorder.reset();
                            mMediaRecorder=null;
                            Bundle b=getIntent().getExtras();
                            try {
                                initRecorder(b);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRecordButton.setEnabled(false);
                                    Toast.makeText(getApplicationContext(), "Recording Will Start in 2 Seconds", Toast.LENGTH_SHORT).show();
                                }
                            });
                            try{Thread.sleep(2000);}catch (Exception e){}
                            mMediaRecorder.start();
                            try {
                                Thread.sleep(6 * 1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mMediaRecorder.stop();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Storing File: "+VIDEO_NAME+"in : " +Environment.getExternalStorageDirectory().getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                    uploadButton.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), "Recording Completed. Press the upload button to upload.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                th1.start();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            uploadVideoOk();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redirect();
            }
        });
    }

   public void redirect(){

       Toast.makeText(getApplicationContext(), "Redirecting", Toast.LENGTH_SHORT).show();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent1;
        intent1 = new Intent(getApplicationContext(), MainActivity.class);
        Bundle b=getIntent().getExtras();
        intent1.putExtras(b);
        startActivity(intent1);
    }

    private void initRecorder(Bundle b) throws IOException {
        String videoName = null,vforname=null;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecordButton.setEnabled(false);
            }
        });

        if(mCamera == null) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            mCamera.setDisplayOrientation(90);
            mCamera.unlock();
        }

        if(mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
            // mMediaRecorder.setPreviewDisplay(surface);
            mMediaRecorder.setOrientationHint(270);
            mMediaRecorder.setCamera(mCamera);


            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
            CamcorderProfile cphigh=CamcorderProfile.get(CamcorderProfile.QUALITY_720P);

            mMediaRecorder.setOutputFormat(cphigh.fileFormat);
            mMediaRecorder.setVideoFrameRate(cphigh.videoFrameRate);
            mMediaRecorder.setVideoSize(cphigh.videoFrameWidth,cphigh.videoFrameHeight);
            mMediaRecorder.setVideoEncodingBitRate(cphigh.videoBitRate);
            mMediaRecorder.setVideoEncoder(cphigh.videoCodec);



            File folder = new File(Environment.getExternalStorageDirectory() +
                    "/video");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            ContentValues val1=b.getParcelable("Extras");
            String Name= (String) val1.get("selectedPage2");
            String gestureName = null;
            int number;
            number= (int) val1.get(Name);

            if(Name.equals("0")){
                gestureName="Num0";
            }else if(Name.equals("1")){
                gestureName="Num1";
            }else if(Name.equals("2")){
                gestureName="Num2";
            }else if(Name.equals("3")){
                gestureName="Num3";
            }else if(Name.equals("4")){
                gestureName="Num4";
            }else if(Name.equals("5")){
                gestureName="Num5";
            }else if(Name.equals("6")){
                gestureName="Num6";
            }else if(Name.equals("7")){
                gestureName="Num7";
            }else if(Name.equals("8")){
                gestureName="Num8";
            }else if(Name.equals("9")){
                gestureName="Num9";
            }else if(Name.equals("Turn on Light")){
                gestureName="LightOn";
            }else if(Name.equals("Turn off Light")){
                gestureName="LightOff";
            }else if(Name.equals("Turn on Fan")){
                gestureName="FanOn";
            }else if(Name.equals("Turn off Fan")){
                gestureName="FanOff";
            }else if(Name.equals("Increase Fan Speed")){
                gestureName="FanUp";
            }else if(Name.equals("Decrease Fan Speed")){
                gestureName="FanDown";
            }else if(Name.equals("Set Thermostat to specified temperature")){
                gestureName="SetThermo";
            }
            vforname=gestureName+"_PRACTICE_"+number+"_PANDHARIWAL.mp4";
            videoName=Environment.getExternalStorageDirectory().getAbsolutePath()+ "/video/"+gestureName+"_PRACTICE_"+number+"_PANDHARIWAL.mp4";
            mMediaRecorder.setOutputFile(videoName);


            try {
                mMediaRecorder.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        String finalVforname = vforname;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                VIDEO_NAME= finalVforname;
            }
        });

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        mCamera=Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        mCamera.unlock();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera=null;
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }
    private Response uploadVideoOk() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",VIDEO_NAME,
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(VIDEO_PATH_NAME+VIDEO_NAME)))
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.0.5:5000/uploadVideo")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.code());
        return response;
    }
}

