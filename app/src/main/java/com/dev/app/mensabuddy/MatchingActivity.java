package com.dev.app.mensabuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MatchingActivity extends AppCompatActivity {

    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        appController = (AppController) getApplicationContext();
        TextView matchText = (TextView) findViewById(R.id.matchText);
        matchText.setText(appController.getMensa());
    }
}
