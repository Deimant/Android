package com.example.egle.notetaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextClock;

public class
MainCalendar extends AppCompatActivity  {


    public static final String TAG ="MainCalendar";

    private CalendarView mCallendarView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen


        setContentView(R.layout.activity_main_calendar);

        button =(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeClockActivity();

            }
        });

        TextClock clock = (TextClock) findViewById(R.id.clock1);

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
//        theDate.setText(date);
        mCallendarView = (CalendarView) findViewById(R.id.calendarView);
        mCallendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (year) + "/" + month + dayOfMonth;
                Log.d(TAG,"oneSelectedDayChange: mm/dd/yyyy" + date);
                Intent intent = new Intent(MainCalendar.this, NoteActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }

    public void openTimeClockActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}
