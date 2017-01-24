package com.dev.app.mensabuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MatchingActivity extends AppCompatActivity {

    AppController appController;
    TextView match1, match2, match3, match4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        appController = (AppController) getApplicationContext();
        match1 = (TextView) findViewById(R.id.matchText1);
        match2 = (TextView) findViewById(R.id.matchText2);
        match3 = (TextView) findViewById(R.id.matchText3);
        match4 = (TextView) findViewById(R.id.matchText4);
        match1.setText(Integer.toString(appController.getId()));
        match2.setText(appController.getStartzeit1());
        match3.setText(appController.getStartzeit2());
        match4.setText(appController.getMensa());
    }
}
