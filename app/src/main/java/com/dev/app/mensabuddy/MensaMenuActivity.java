package com.dev.app.mensabuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MensaMenuActivity extends AppCompatActivity {

    private String urlJsonObject;
    private ArrayList<String> itemList = new ArrayList<>();
    private ListView listView;
    private ProgressDialog progressDialog;
    private AppController appController;

    public static final String TAG = MensaMenuActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensa_menu);

        appController = (AppController) getApplicationContext();

        Bundle extras = getIntent().getExtras();

        final String mensaName;

        mensaName = extras.getString("MensaName");

        listView = (ListView) findViewById(R.id.menu_list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Bitte warten ... ");
        progressDialog.setCancelable(false);

        makeJsonObjectRequest(mensaName);

        Button button = (Button) findViewById(R.id.mensaBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appController.setMensa(mensaName);
                Intent i = new Intent(getApplicationContext(), DateActivity.class);
                startActivity(i);
            }
        });
    }

    private String createServiceUrl(String mensa) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String datum = "";

        if (month < 10) {
            if (day < 10) {
                datum = "datum=" + Integer.toString(year) + "0" + Integer.toString(month) + "0" + Integer.toString(day);
            } else {
                datum = "datum=" + Integer.toString(year) + "0" + Integer.toString(month) + "" + Integer.toString(day);
            }
        } else {
            if (day < 10) {
                datum = "datum=" + Integer.toString(year) + "" + Integer.toString(month) + "0" + Integer.toString(day);
            } else {
                datum = "datum=" + Integer.toString(year) + "" + Integer.toString(month) + "" + Integer.toString(day);
            }
        }

        urlJsonObject = "http://192.168.0.18:8080/BuddyService/mensa?name=" + mensa + "&" + datum + "";
        //urlJsonObject = "http://10.100.7.205:8080/BuddyService/mensa?name=" + mensa + "&" + datum + "";
        return urlJsonObject;
    }

    private void makeJsonObjectRequest(String mensa) {

        showProgressDialog();

        urlJsonObject = createServiceUrl(mensa);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJsonObject, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String erstesMenue = response.getString("erstesMenue");
                    String zweitesMenue = response.getString("zweitesMenue");
                    String drittesMenue = response.getString("drittesMenue");

                    itemList.add(erstesMenue);
                    itemList.add(zweitesMenue);
                    itemList.add(drittesMenue);

                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, itemList);
                    listView.setAdapter(itemsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Fehler: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Fehler: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
