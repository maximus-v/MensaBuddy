package com.dev.app.mensabuddy;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

public class PersonalActivity extends AppCompatActivity {

    Spinner fakultaetSpinner;
    TextView vornameInp, nachnameInp, int1Inp, int2Inp, int3Inp;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        myDb = new DatabaseHelper(this);

        vornameInp = (TextView) findViewById(R.id.inputVorname);
        nachnameInp = (TextView) findViewById(R.id.inputNachname);
        int1Inp = (TextView) findViewById(R.id.inputInt1);
        int2Inp = (TextView) findViewById(R.id.inputInt2);
        int3Inp = (TextView) findViewById(R.id.inputInt3);

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
        if (0 == 1) {
            Cursor profil = myDb.getProfilesById(3);

            if (profil.moveToNext()) {
                vornameInp.setText(profil.getString(0));
                nachnameInp.setText(profil.getString(1));

                String fakulaet = profil.getString(2);
                if (!fakulaet.equals(null)) {
                    int spinnerPosition = adapter.getPosition(fakulaet);
                    fakultaetSpinner.setSelection(spinnerPosition);
                }
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
                    if (0 == 1) {
                        //myDb.updateProfile(1, vornameInp.getText().toString(),
                        //        nachnameInp.getText().toString(), fakultaetSpinner.getSelectedItem().toString());
                        Toast.makeText(getApplicationContext(), "Geupdated: " +
                                vornameInp.getText().toString() + nachnameInp.getText().toString() +
                                fakultaetSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                    } else {
                        //myDb.insertProfile(vornameInp.getText().toString(),
                        //        nachnameInp.getText().toString(), fakultaetSpinner.getSelectedItem().toString());
                        Toast.makeText(getApplicationContext(), "Eingefügt: " +
                                vornameInp.getText().toString() + nachnameInp.getText().toString() +
                                fakultaetSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                    }
                    Intent i = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(i);
                }
            }

        });

    }


}
