package com.dev.app.mensabuddy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dev.app.mensabuddy.R.id.button;

public class StartActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        appController = (AppController) getApplicationContext();

        myDb = new DatabaseHelper(this);
        //Update Vorschläge bei Start der App
        //Herausnehmen, wenn Service steht
        myDb.updateDatum();

        //Nachfolgender Code leitet User auf Profil, wenn noch nicht angelegt
        /*String resultString = "";
        Cursor result = myDb.getUniqueKey();
        while (result.moveToNext()) {
            resultString = Integer.toString(result.getInt(0));
        }
        if (resultString.equals("")) {
            Intent i = new Intent(getApplicationContext(), PersonalActivity.class);
            startActivity(i);
        } else {
            mpa.setId(Integer.parseInt(resultString);
        }*/

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MensaPageAdapter(getSupportFragmentManager(), StartActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        Button button = (Button) findViewById(R.id.mensaBtn);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DateActivity.class);
                startActivity(i);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.openProfil:
                Intent i = new Intent(getApplicationContext(), PersonalActivity.class);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
