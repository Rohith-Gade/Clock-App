package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class managment extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Alarm> alarmDataList = new ArrayList<>();
    private AlarmAdapter alarmAdapter;
    private DatabaseHelper dbHelper;
    private AlarmManager alarmManager;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managment);
        dbHelper = new DatabaseHelper(this);
        dialog =new Dialog(this);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        recyclerView = findViewById(R.id.recyclerview);
        alarmAdapter = new AlarmAdapter(getApplicationContext(), alarmDataList, dbHelper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(alarmAdapter);

        alarmDataList = dbHelper.getAllAlarms();
        alarmAdapter.setData(alarmDataList);
        alarmAdapter.notifyDataSetChanged();

        ArrayList<Alarm> alarms= dbHelper.getSelectedAlarms();
        checkAndSetAlarms(alarms);

    }
    private void checkAndSetAlarms(ArrayList<Alarm> selectedAlarms) {
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedTime = sdf.format(currentTime.getTime());

        for (Alarm alarm : selectedAlarms) {
            if (formattedTime.equals(alarm.getTime())) {
                scheduleAlarm(alarm);
            }
        }
    }

    private void scheduleAlarm(Alarm alarm) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("selected_song", alarm.getSong());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarm.getId(),
                intent, PendingIntent.FLAG_MUTABLE);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date date = sdf.parse(alarm.getTime());
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }
}


