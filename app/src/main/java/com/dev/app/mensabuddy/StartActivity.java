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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dev.app.mensabuddy.Adapter.MensaAdapter;
import com.dev.app.mensabuddy.ListViewModel.MensaModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
            appController.setId(Integer.parseInt(resultString);
        }*/

        /*
        //Hier könnte Ihre ArrayList stehen
        ArrayList<String> items = new ArrayList<>();
        items.add("Alte Mensa");
        items.add("GrillCube");
        items.add("Reichenbachstrasse");
        items.add("Siedepunkt");
        items.add("UBoot");
        items.add("Zeltmensa");

        String favortienMensa = "Zeltmensa";

        //Lösche Mensa von übrigen Mensen
        if (items.indexOf(favortienMensa) != 0) {
            //Favoriten-Mensa an zweite Stelle
            int index = items.indexOf(favortienMensa);
            items.remove(index);
            items.add(1, favortienMensa);
        } else {
            //Favorit und nächste Mensa sind die selbe
            items.remove(0);
            items.add(0, favortienMensa);
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        ListView listView = (ListView) findViewById(R.id.mensa_list);
        listView.setAdapter(itemsAdapter);
        */

        ListView listView = (ListView) findViewById(R.id.mensa_list);

        ArrayList<MensaModel> mensaModels = new ArrayList<>();

        mensaModels.add(new MensaModel("Alte Mensa"));
        mensaModels.add(new MensaModel("Grillcube"));
        mensaModels.add(new MensaModel("Reichenbachstrasse"));
        mensaModels.add(new MensaModel("Siedepunkt"));
        mensaModels.add(new MensaModel("UBoot"));
        mensaModels.add(new MensaModel("Zeltmensa"));

        MensaAdapter adapter = new MensaAdapter(mensaModels, getApplicationContext());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MensaModel model = (MensaModel) parent.getItemAtPosition(position);
                String mensa = model.getName().replaceAll("\\s+","");
                Intent i = new Intent(getApplicationContext(), MensaMenuActivity.class);
                i.putExtra("MensaName", mensa);
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
