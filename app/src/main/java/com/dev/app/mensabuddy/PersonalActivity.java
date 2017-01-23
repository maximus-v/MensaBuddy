package com.dev.app.mensabuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.dev.app.mensabuddy.Entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalActivity extends AppCompatActivity {

    Spinner fakultaetSpinner;
    EditText vornameInp, nachnameInp, int1Inp, int2Inp, int3Inp, studiumInp, telefonInp;
    DatabaseHelper myDb;
    User user;
    AppController appController;
    ProgressDialog progressDialog;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        myDb = new DatabaseHelper(this);
        appController = (AppController) getApplicationContext();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Anfrage wird bearbeitet ... ");
        progressDialog.setCancelable(false);

        vornameInp = (EditText) findViewById(R.id.inputVorname);
        nachnameInp = (EditText) findViewById(R.id.inputNachname);
        int1Inp = (EditText) findViewById(R.id.inputInt1);
        int2Inp = (EditText) findViewById(R.id.inputInt2);
        int3Inp = (EditText) findViewById(R.id.inputInt3);
        studiumInp = (EditText) findViewById(R.id.inputStudium);
        telefonInp = (EditText) findViewById(R.id.inputTelefon);

        fakultaetSpinner = (Spinner) findViewById(R.id.fakultaetSpinner);

        List<String> fakulaetArray = new ArrayList<>();
        fakulaetArray.add("Architektur");
        fakulaetArray.add("Bauingenieurwesen");
        fakulaetArray.add("Elektrotechnik und Informationstechnik");
        fakulaetArray.add("Erziehungswissenschaften");
        fakulaetArray.add("Informatik");
        fakulaetArray.add("Maschinenwesen");
        fakulaetArray.add("Mathematik und Naturwissenschaften");
        fakulaetArray.add("Sprach, Literatur und Kulturwissenschaften");
        fakulaetArray.add("Umweltwissenschaften");
        fakulaetArray.add("Verkehrswissenschaften");
        fakulaetArray.add("Wirtschaftswissenschaften");
        fakulaetArray.add("Juristische Fakultaet");
        fakulaetArray.add("Medizinische Fakultaet");
        fakulaetArray.add("Philosophische Fakultaet");
        fakulaetArray.add("Internationale Studien");
        fakulaetArray.add("Lehrerbildung, Schul und Berufsbildungsforschung");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, fakulaetArray);

        fakultaetSpinner.setAdapter(adapter);

        //Überprüfe, ob Profildaten vorliegen anhand der ID.
        //Wenn ja, dann fülle Formular aus
        if (myDb.getProfilId() > 0) {
            user = myDb.getProfil();
            vornameInp.setText(user.getVorname());
            nachnameInp.setText(user.getNachname());

            String fakulaet = user.getFakultaet();
            if (!fakulaet.equals(null)) {
                int spinnerPosition = adapter.getPosition(fakulaet);
                fakultaetSpinner.setSelection(spinnerPosition);
            }
        }

        Button button = (Button) findViewById(R.id.saveProfilBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                if (vornameInp.getText().toString().trim().equals("")) {
                    vornameInp.setError("Benötigt");
                } else if (nachnameInp.getText().toString().trim().equals("")) {
                    nachnameInp.setError(("Benötigt"));
                } else {
                    //Überprüfe ob angelegt oder aktualisiert wird, anhand der ID
                    if (myDb.getProfilId() > 0) {
                        user = new User();
                        //TODO Hier müssen wir noch die Datenbank mit den Eingaben abgleichen
                        user.setId(appController.getId());
                        user.setVorname(/*vornameInp.getText().toString()*/"Hallo");
                        user.setNachname(nachnameInp.getText().toString());
                        user.setFakultaet(fakultaetSpinner.getSelectedItem().toString());
                        user.setStudiengang(studiumInp.getText().toString());
                        user.setInteresse1(int1Inp.getText().toString());
                        user.setInteresse2(int3Inp.getText().toString());
                        user.setInteresse3(int3Inp.getText().toString());
                        user.setTelefon(telefonInp.getText().toString());
                        user.setToken(appController.getFirebaseToken());
                        //TODO Service aufrufen
                        //myDb.updateProfile(user);
                        Toast.makeText(getApplicationContext(), "Erfolgreich aktualisiert!", Toast.LENGTH_SHORT).show();
                    } else {
                        user = new User();
                        user.setVorname(vornameInp.getText().toString());
                        user.setNachname(nachnameInp.getText().toString());
                        user.setFakultaet(fakultaetSpinner.getSelectedItem().toString());
                        user.setStudiengang(studiumInp.getText().toString());
                        user.setInteresse1(int1Inp.getText().toString());
                        user.setInteresse2(int2Inp.getText().toString());
                        user.setInteresse3(int3Inp.getText().toString());
                        user.setTelefon(telefonInp.getText().toString());
                        user.setToken(appController.getFirebaseToken());


                        JSONObject json = new JSONObject();

                        try {
                            json.put("nachname", user.getNachname());
                            json.put("vorname", user.getVorname());
                            json.put("fakultaet", user.getFakultaet());
                            json.put("studiengang", user.getStudiengang());
                            json.put("interesse1", user.getInteresse1());
                            json.put("interesse2", user.getInteresse2());
                            json.put("interesse3", user.getInteresse3());
                            json.put("telefon", user.getTelefon());
                            json.put("token", user.getToken());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String url = "http://192.168.0.18:8080/MensaBuddyServer/webapi/user";

                        JsonObjectRequest request = new JsonObjectRequest(url, json,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            id = response.getInt("value");
                                            setUserId(user, id);
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

                        Toast.makeText(getApplicationContext(), "Erfolgreich gespeichert!" + " " + user.getId(), Toast.LENGTH_LONG).show();
                    }
                }
            }

        });

    }

    public void setUserId(User user, int id) {
        user.setId(id);
        myDb.insertProfile(user);
        appController.setId(id);
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
