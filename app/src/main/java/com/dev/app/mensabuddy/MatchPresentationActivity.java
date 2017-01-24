package com.dev.app.mensabuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dev.app.mensabuddy.Entities.User;
import com.dev.app.mensabuddy.Entities.Vorschlag;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MatchPresentationActivity extends AppCompatActivity {

    private final String TAG = MatchPresentationActivity.class.getSimpleName();

    String mensaName, name, zeit, telefon;
    int prozent;
    TextView matchName, matchMensa, matchZeit, matchTelefon, matchProzent;
    PieChart pieChart;
    private int[] yData;
    private String[] xData = {"Fit", "Misfit"};
    DatabaseHelper myDb;
    Button confBtn;
    ProgressDialog progressDialog;
    Vorschlag vorschlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_presentation);
        Log.d(TAG, "onCreate: PieChart");

        myDb = new DatabaseHelper(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Anfrage wird bearbeitet ... ");
        progressDialog.setCancelable(false);

        vorschlag = myDb.getVorschlag();

        mensaName = vorschlag.getMensa();
        name = vorschlag.getName();
        zeit = vorschlag.getZeit();
        telefon = vorschlag.getTelefon();
        prozent = vorschlag.getProzent();

        yData = new int[]{prozent, 100 - prozent};

        matchMensa = (TextView) findViewById(R.id.matchMensa);
        matchTelefon = (TextView) findViewById(R.id.matchTelefon);
        matchName = (TextView) findViewById(R.id.matchName);
        matchZeit = (TextView) findViewById(R.id.matchZeit);

        pieChart = (PieChart) findViewById(R.id.matchPieChart);

        confBtn = (Button) findViewById(R.id.confBtn);

        matchName.setText(name);
        matchMensa.setText(mensaName);
        matchZeit.setText(zeit);
        matchTelefon.setText(telefon);

        Description desc = new Description();
        desc.setText("Übereinstimmung");
        desc.setEnabled(false);
        pieChart.setDescription(desc);

        pieChart.setHoleRadius(0);

        addDataSet(yData);

        confBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Confirm clicked");
                showProgressDialog();

                JSONObject json = new JSONObject();
                try {
                    json.put("userId", vorschlag.getEigeneId());
                    json.put("matchId", vorschlag.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = "http://192.168.0.18:8080/MensaBuddyServer/webapi/matching/confirm";

                JsonObjectRequest request = new JsonObjectRequest(url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("conf1") == true) {
                                        vorschlag.setConf1(1);
                                    } else {
                                        vorschlag.setConf1(0);
                                    }
                                    if (response.getBoolean("conf2") == true) {
                                        vorschlag.setConf2(1);
                                    } else {
                                        vorschlag.setConf2(0);
                                    }

                                    Log.d(TAG, "onResponse: " + vorschlag.toString());
                                    myDb.updateVorschlag(vorschlag);
                                    confBtn.setEnabled(false);
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
        });

    }
    
    public void addDataSet(int[] data) {
        Log.d(TAG, "addDataSet: started");
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            yEntries.add(new PieEntry(data[i], i));
        }

        for (int i = 0; i < xData.length; i++) {
            xEntries.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntries, "Übereinstimmung");
        pieDataSet.setSliceSpace(2);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(104,188,36));
        colors.add(Color.rgb(82,128,45));

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
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
