package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView date_time_view , display;
    Button createalaram,savedalarms;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date_time_view=findViewById(R.id.date_time_view);
        display=findViewById(R.id.dispaly);
        createalaram=findViewById(R.id.createalaram);
        savedalarms=findViewById(R.id.savedalaram);
        SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String FormatedDate = dateFormat.format( new Date());
        System.out.println("FormatedDate : " + FormatedDate);
        display.setText(FormatedDate);
        createalaram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),Alaramsetting.class) ;
                startActivity(in);
            }
        });
        savedalarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in= new Intent(getApplicationContext(),managment.class);
                startActivity(in);
            }
        });
    }

    }