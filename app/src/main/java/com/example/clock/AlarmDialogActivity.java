package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.HashMap;

public class AlarmDialogActivity extends AppCompatActivity {
    Button snoozeButton,dismissButton;
    TextView textview;
    TimePicker alarmsettime1;
    MediaPlayer mediaPlayer;
    DatabaseHelper dbhelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_dialog);
        snoozeButton=findViewById(R.id.button_snooze);
         dismissButton=findViewById(R.id.button_dismiss);
        alarmsettime1=findViewById(R.id.alarmsettime);
         textview=findViewById(R.id.alarmtime);
         dbhelper =new DatabaseHelper(getApplicationContext());
        int hour = alarmsettime1.getCurrentHour();
        int minute = alarmsettime1.getCurrentMinute();
        String selectedTime1 = String.format("%02d:%02d", hour, minute);
        String amPm;
        if (hour >= 12) {
            amPm = "PM";
            if (hour > 12) {
                hour -= 12;
            }
        } else {
            amPm = "AM";
            if (hour == 0) {
                hour = 12;
            }
        }

        String selectedTime = String.format("%02d:%02d %s", hour, minute, amPm);
        textview.setText(selectedTime);
        String songName = dbhelper.retrieveSongNameFromDatabase(selectedTime1);

        HashMap<String, Integer> songResourceMap = new HashMap<>();
        songResourceMap.put("Senorita",R.raw.senorita);
        songResourceMap.put("Inthandam",R.raw.sitaramam);
        songResourceMap.put("Masteru",R.raw.masteru);
        songResourceMap.put("JinthaakChithaka",R.raw.jinthaakchithaka);
        songResourceMap.put("Saami Saami",R.raw.saamisaami);

        int songResourceID=0;
        if(songResourceID == 0 ) {
            songResourceID = 2131820547;
        }
        else{
            songResourceID = songResourceMap.get(songName);
        }

        System.out.println("resourece id:"+songResourceID);
        mediaPlayer = MediaPlayer.create(this, songResourceID);

        mediaPlayer.start();
        snoozeButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (mediaPlayer != null) {
                     mediaPlayer.stop();
                 }

                 android.os.Handler handler = new android.os.Handler();
                 handler.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         Intent intent = new Intent(AlarmDialogActivity.this, AlarmDialogActivity.class);
                         startActivity(intent);
                     }
                 }, 60 * 1000); // 1 minute in milliseconds

                 finish();
             }
         });
         dismissButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });
          getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

}