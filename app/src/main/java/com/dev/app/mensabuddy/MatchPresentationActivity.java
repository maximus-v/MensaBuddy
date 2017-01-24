package com.dev.app.mensabuddy;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dev.app.mensabuddy.Entities.Vorschlag;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_presentation);
        Log.d(TAG, "onCreate: PieChart");

        myDb = new DatabaseHelper(this);

        Vorschlag vorschlag = myDb.getVorschlag();

        Log.d(TAG, "onCreate: " + vorschlag.toString());

        Log.d(TAG, "onCreate: " + vorschlag.getMensa());

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
}
