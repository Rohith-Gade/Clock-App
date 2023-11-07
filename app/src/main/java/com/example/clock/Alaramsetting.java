package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class Alaramsetting extends AppCompatActivity {
    TimePicker settime;
    Button repeatBtn, settoneBtn, saveBtn;
    static TextView selecteddays,selectedsong;
    ArrayList<String> selectedWeekdays;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaramsetting);
        selectedWeekdays = new ArrayList<>();
        settime = findViewById(R.id.settime);
        repeatBtn = findViewById(R.id.repeatBtn);
        settoneBtn = findViewById(R.id.settoneBtn);
        saveBtn = findViewById(R.id.saveBtn);
        selecteddays = findViewById(R.id.selecteddays);
        selectedsong=findViewById(R.id.selectedsong);
        repeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new Popup();
                dialogFragment.show(getSupportFragmentManager(), "Alert");
            }
        });
        settoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in =new Intent(getApplicationContext(),SetTuneActivity.class);
                startActivity(in);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = settime.getCurrentHour();
                int minute = settime.getCurrentMinute();
                String selectedTime = String.format("%02d:%02d", hour, minute);

                // Get the selected song and status
                String selectedSong = selectedsong.getText().toString();
                int status = 1; // Default to "On"

                String selectedDays = selecteddays.getText().toString();


                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                long rowId =dbHelper.insertAlarm(selectedTime, selectedDays, selectedSong, status);

                if (rowId != -1) {
                    Intent intent = new Intent(getApplicationContext(), managment.class);
                    startActivity(intent);
                }
            }
        });
    }

    public static void onOkButtonClicked(ArrayList<String> weekdays) {
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < weekdays.size(); i++) {
            String language = weekdays.get(i);
            builder.append(language);
            if (i < weekdays.size() - 1) {
                builder.append(", "); // Add a comma if it's not the last language
            }
            System.out.println("language: " + language);
        }
        String text = builder.toString();
        selecteddays.setText(text);
    }

}