package com.dev.app.mensabuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dev.app.mensabuddy.Entities.Vorschlag;

import org.json.JSONException;
import org.json.JSONObject;

public class MatchingActivity extends AppCompatActivity {
    
    final String TAG = MatchingActivity.class.getSimpleName();

    AppController appController;
    DatabaseHelper myDb;
    TextView message;
    Vorschlag vorschlag;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        appController = (AppController) getApplicationContext();
        myDb = new DatabaseHelper(this);

        message = (TextView) findViewById(R.id.matchMessage);
        message.setVisibility(View.INVISIBLE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Anfrage wird bearbeitet ... ");
        progressDialog.setCancelable(false);

        if (myDb.getVorschlag().getName() == null) {
            Log.d(TAG, "onCreate: Vorschlag = null");
            makeMatchingRequest();
        } else {
            Log.d(TAG, "onCreate: Vorschlag vorhanden");
            Intent i = new Intent(getApplicationContext(), MatchPresentationActivity.class);
            startActivity(i);
        }
    }

    public void makeMatchingRequest() {

        showProgressDialog();

        String url = "http://192.168.0.18:8080/MensaBuddyServer/webapi/matching/request";

        JSONObject json = new JSONObject();

        try {
            json.put("userId", appController.getId());
            json.put("mensa", appController.getMensa());
            json.put("zeit1", appController.getStartzeit1());
            json.put("zeit2", appController.getStartzeit2());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        vorschlag = new Vorschlag();

        JsonObjectRequest request = new JsonObjectRequest(url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            vorschlag.setId(response.getInt("matchId"));
                            vorschlag.setName(response.getString("name"));
                            vorschlag.setAndereId(response.getInt("matchedUserId"));
                            vorschlag.setProzent(response.getInt("percentage"));
                            vorschlag.setTelefon(response.getString("telefon"));
                            Log.d(TAG, "onResponse: " + response.getString("zeit"));
                            vorschlag.setZeit(response.getString("zeit"));
                            vorschlag.setMensa(appController.getMensa());
                            vorschlag.setEigeneId(appController.getId());
                            vorschlag.setStartzeit1(appController.getStartzeit1());
                            vorschlag.setStartzeit2(appController.getStartzeit2());
                            vorschlag.setDatum("2017-01-24");
                            vorschlag.setConf1(0);
                            vorschlag.setConf2(0);

                            if (vorschlag.getId() != 0) {
                                //Schreibe in Datenbank
                                Log.d(TAG, "onResponse: Schreibe in Datenbank");
                                myDb.insertVorschlag(vorschlag);
                                //Starte neue Aktivität
                                Intent i = new Intent(getApplicationContext(), MatchPresentationActivity.class);
                                startActivity(i);
                            } else {
                                message.setVisibility(View.VISIBLE);
                            }

                            Log.d(TAG, "onResponse: " + vorschlag.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                hideProgressDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(request);

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
