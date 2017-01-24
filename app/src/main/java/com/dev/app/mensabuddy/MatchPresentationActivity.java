package com.dev.app.mensabuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MatchPresentationActivity extends AppCompatActivity {

    String mensaName, name, zeit, telefon;
    TextView matchName, matchMensa, matchZeit, matchTelefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_presentation);

        Bundle extras = getIntent().getExtras();

        mensaName = extras.getString("MensaName");
        name = extras.getString("Name");
        zeit = extras.getString("Zeit");
        telefon = extras.getString("Telefon");

        matchMensa = (TextView) findViewById(R.id.matchMensa);
        matchTelefon = (TextView) findViewById(R.id.matchTelefon);
        matchName = (TextView) findViewById(R.id.matchName);
        matchZeit = (TextView) findViewById(R.id.matchZeit);

        matchName.setText(name);
        matchMensa.setText(mensaName);
        matchZeit.setText(zeit);
        matchTelefon.setText(telefon);
    }
}
