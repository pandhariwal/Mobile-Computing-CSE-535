package com.vinay.assignment_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

public class Page2 extends AppCompatActivity {
    public ContentValues val1 ;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        b=getIntent().getExtras();
        if(b!=null)
            val1=b.getParcelable("Extras");


        VideoView videoView = findViewById(R.id.expertVideoPlayer);
        ImageButton playButton = findViewById(R.id.playButton);
        ImageButton backButton = findViewById(R.id.backButtonPage2);
        ProgressBar pg1 =findViewById(R.id.progressBar);
        Button Practice = findViewById(R.id.practiceButton);
        pg1.setMax(100);
        pg1.setMin(0);

        String videoName= String.valueOf(val1.get("selectedPage2"));
        String path = null;
        if(videoName.equals("0")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.h0;
        }else if(videoName.equals("1")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.h1;
        }else if(videoName.equals("2")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.h2;
        }else if(videoName.equals("3")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.h3;
        }else if(videoName.equals("4")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.h4;
        }else if(videoName.equals("5")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.h5;
        }else if(videoName.equals("6")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.h6;
        }else if(videoName.equals("7")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.h7;
        }else if(videoName.equals("8")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.h8;
        }else if(videoName.equals("9")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.h9;
        }else if(videoName.equals("Turn on Light")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.lighton;
        }else if(videoName.equals("Turn off Light")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.lightoff;
        }else if(videoName.equals("Turn on Fan")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.fanon;
        }else if(videoName.equals("Turn off Fan")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.fanoff;
        }else if(videoName.equals("Increase Fan Speed")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.increasefanspeed;
        }else if(videoName.equals("Decrease Fan Speed")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.decreasefanspeed;
        }else if(videoName.equals("Set Thermostat to specified temperature")){
            path = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.setthermo;
        }
        videoView.setVideoURI(Uri.parse(path));


        pg1.setProgress(33, true);
        videoView.start();

        final int[] flag = {2,2};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Assignment_2")
                .setMessage("Do you want to play the video again?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        videoView.start();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Please Press the Practice Button to Record",Toast.LENGTH_LONG).show();
                    }
                });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if(flag[0]>0){
                        pg1.setProgress(flag[1]*33);
                        videoView.start();
                        flag[1]++;
                        flag[0]--;
                    }else if(flag[0]==0){

                        alertDialog.show();

                    }
                }
            });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1;
                intent1 = new Intent(getApplicationContext(), MainActivity.class);
                b.putParcelable("Extras",val1);
                intent1.putExtras(b);
                startActivity(intent1);
            }
        });

        Practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1;
                intent1 = new Intent(getApplicationContext(), Page3.class);
                String videoName= (String) val1.get("selectedPage2");
                if(val1.containsKey(videoName)){
                    val1.put(videoName,(int)val1.get(videoName)+1);
                }else{
                    val1.put(videoName,1);
                }
                b.putParcelable("Extras",val1);
                intent1.putExtras(b);
                startActivity(intent1);
            }
        });



            }
    }
