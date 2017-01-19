package com.dev.app.mensabuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MatchingActivity extends AppCompatActivity {

    AppController appController;
    Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        appController = (AppController) getApplicationContext();
        TextView matchText = (TextView) findViewById(R.id.matchText);
        matchText.setText(appController.getMensa());
        getMatch();
        match = new Match(this);
    }

    private void getMatch(){
        try {
        match.findBestMatch(appController.getId(), appController.getMensa(), appController.getTime());
            } catch (NullPointerException e){
            Toast.makeText(this, "Noch kein Match verfügbar. Sorry ;/", Toast.LENGTH_LONG).show();
        }

    }
}
