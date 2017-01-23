package com.dev.app.mensabuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dev.app.mensabuddy.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity {

    Spinner fakultaetSpinner;
    EditText vornameInp, nachnameInp, int1Inp, int2Inp, int3Inp, studiumInp, telefonInp;
    DatabaseHelper myDb;
    User user;
    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        myDb = new DatabaseHelper(this);
        appController = (AppController) getApplicationContext();

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
                if (vornameInp.getText().toString().trim().equals("")) {
                    vornameInp.setError("Benötigt");
                } else if (nachnameInp.getText().toString().trim().equals("")) {
                    nachnameInp.setError(("Benötigt"));
                } else {
                    //Überprüfe ob angelegt oder aktualisiert wird, anhand der ID
                    if (myDb.getProfilId() > 0) {
                        //TODO Hier müssen wir noch die Datenbank mit den Eingaben abgleichen
                        user.setVorname(vornameInp.getText().toString());
                        user.setNachname(nachnameInp.getText().toString());
                        user.setFakultaet(fakultaetSpinner.getSelectedItem().toString());
                        user.setStudiengang(studiumInp.getText().toString());
                        user.setInteresse1(int1Inp.getText().toString());
                        user.setInteresse2(int3Inp.getText().toString());
                        user.setInteresse3(int3Inp.getText().toString());
                        user.setTelefon(telefonInp.getText().toString());
                        user.setToken(appController.getFirebaseToken());
                        myDb.updateProfile(user);
                        Toast.makeText(getApplicationContext(), "Erfolgreich aktualisiert!", Toast.LENGTH_SHORT).show();
                    } else {
                        user.setVorname(vornameInp.getText().toString());
                        user.setNachname(nachnameInp.getText().toString());
                        user.setFakultaet(fakultaetSpinner.getSelectedItem().toString());
                        user.setStudiengang(studiumInp.getText().toString());
                        user.setInteresse1(int1Inp.getText().toString());
                        user.setInteresse2(int3Inp.getText().toString());
                        user.setInteresse3(int3Inp.getText().toString());
                        user.setTelefon(telefonInp.getText().toString());
                        user.setToken(appController.getFirebaseToken());
                        Toast.makeText(getApplicationContext(), "Erfolgreich gespeichert!", Toast.LENGTH_LONG).show();
                    }
                    Intent i = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(i);
                }
            }

        });

    }


}
