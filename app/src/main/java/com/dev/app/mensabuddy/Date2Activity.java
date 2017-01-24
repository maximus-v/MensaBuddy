package com.dev.app.mensabuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class Date2Activity extends AppCompatActivity {

    TimePicker timePicker;
    String hour, minutes;
    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date2);

        appController = (AppController) getApplicationContext();

        timePicker = (TimePicker) findViewById(R.id.timePicker2);
        timePicker.setIs24HourView(true);

        timePicker.setOnTimeChangedListener(
                new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay < 10) {
                            hour = "0" + Integer.toString(hourOfDay);
                        } else {
                            hour = Integer.toString(hourOfDay);
                        }

                        if (minute < 10) {
                            minutes = "0" + Integer.toString(minute);
                        } else {
                            minutes = Integer.toString(minute);
                        }
                    }
                }
        );

        Button button = (Button) findViewById(R.id.timeBtn2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appController.setStartzeit2(hour + ":" + minutes + ":00");
                Intent i = new Intent(getApplicationContext(), MatchingActivity.class);
                startActivity(i);
            }

        });
    }
}
