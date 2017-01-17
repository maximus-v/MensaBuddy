package com.dev.app.mensabuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MensaMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensa_menu);

        Bundle extras = getIntent().getExtras();

        String mensaName;

        mensaName = extras.getString("MensaName");

        TextView text = (TextView) findViewById(R.id.testFenster);
        text.setText(mensaName);
    }
}
