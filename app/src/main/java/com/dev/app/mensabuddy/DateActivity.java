package com.dev.app.mensabuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class DateActivity extends AppCompatActivity {

    TimePicker timePicker;
    String hour, minutes;
    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        appController = (AppController) getApplicationContext();

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        timePicker.setOnTimeChangedListener(
                new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        hour = Integer.toString(hourOfDay);
                        minutes = Integer.toString(minute);
                    }
                }
        );

        Button button = (Button) findViewById(R.id.timeBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changed something
                appController.setTime(0);
                Intent i = new Intent(getApplicationContext(), MatchingActivity.class);
                startActivity(i);
            }

        });
    }
}
