package com.dev.app.mensabuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity {

    Spinner fakultaetSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

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
    }
}
