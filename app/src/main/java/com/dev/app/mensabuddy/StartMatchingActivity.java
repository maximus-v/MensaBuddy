package com.dev.app.mensabuddy;

import android.content.IntentSender;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StartMatchingActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemSelectedListener {

    //private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = StartMatchingActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private TextView t;
    private DistanceCalculator DC = new DistanceCalculator();
    final List<String> mensen = new ArrayList<>();
    ArrayAdapter<String> dataAdapter;
    Match match;



    //Orte der Mensen
    private HashMap<String, Double> Canteens = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_matching);
        Button button = (Button) findViewById(R.id.goBtn);
        final Spinner spinner = (Spinner) findViewById(R.id.mensaSpin);

        //Label erstellen
        t=new TextView(this);
        t=(TextView)findViewById(R.id.lblLocation);

        //Dropdown erstellen
        spinner.setOnItemSelectedListener(this);
        //Elemente einfügen


        mensen.add("Alte Mensa");
        mensen.add("Zeltschlösschen");
        mensen.add("Siedepunkt");
        mensen.add("UBoot");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mensen);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //Button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mensen.add(mensen.size(),"TestDummy");
                //dataAdapter.notifyDataSetChanged();
                sortSpinner();
                Toast.makeText(StartMatchingActivity.this, "Button Clicked and list sorted", Toast.LENGTH_SHORT).show();
            }

        });

        //Google API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();



    }

    @Override
    protected void onResume(){
        match = new Match(this);

        if (match.findFastMatch(2,"Alte Mensa", 1100)==1)
            {Toast.makeText(this, "it worked", Toast.LENGTH_LONG).show();}
        else Toast.makeText(this, "didn't work", Toast.LENGTH_LONG).show();

        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        this.calcDistance("Alte Mensa");
    }

    private void calcDistance (String Mensa){
        Log.i(TAG, "Location services connected.");
        Location location = null;
        try{
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);}
        catch (SecurityException e) {
            Toast.makeText(StartMatchingActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        if (location == null) {
            Toast.makeText(StartMatchingActivity.this, "No Location detected", Toast.LENGTH_SHORT).show();

        }
        else {
            handleNewLocation(location);
            double lat1 = location.getLatitude();
            double lng1 = location.getLongitude();

            //Orte der Mensen zufügen
            /*Canteens.put("Alte MensaLat", 51.027199);
            Canteens.put("Alte MensaLng", 13.726550);
            Canteens.put("ZeltschlösschenLat", 51.031318);
            Canteens.put("ZeltschlösschenLng", 13.728419);
            Canteens.put("SiedepunktLat", 51.029140);
            Canteens.put("SiedepunktLng", 13.738530);
            Canteens.put("UBootLat", 51.030100);
            Canteens.put("UBootLng", 13.729319);

            double lat2 = Canteens.get(Mensa+"Lat");
            double lng2 = Canteens.get(Mensa+"Lng");

            double dis = DC.calculateDistance(lat1, lat2, lng1, lng2);
            String i = String.valueOf(dis*1000);
            t.setText(i + " m");*/
            //return dis;

            String[] sortedCanteens = new String[3];
            sortedCanteens=DC.getSortedCanteens(lat1, lng1);
            mensen.clear();
            mensen.add(sortedCanteens[0]);
            mensen.add(sortedCanteens[1]);
            mensen.add(sortedCanteens[2]);
        }
        //return 0;
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String item = parent.getItemAtPosition(position).toString();
        this.calcDistance(item);
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0){

    }

    private void sortSpinner() {
        /*Map<Double, String> sortedMap = new TreeMap<>();
        double[] indizes = new double[4];
        indizes[0]=this.calcDistance("Alte Mensa");
        sortedMap.put(indizes[0], "Alte Mensa");
        indizes[1]=this.calcDistance("Zeltschlösschen");
        sortedMap.put(indizes[1], "Zeltschlösschen");
        indizes[2]=this.calcDistance("Siedepunkt");
        sortedMap.put(indizes[2], "Siedepunkt");
        indizes[3]=this.calcDistance("UBoot");
        sortedMap.put(indizes[3], "UBoot");

        mensen.clear();
        int i=0;
        for(Map.Entry<Double,String> entry : sortedMap.entrySet()) {

            mensen.add(i, entry.getValue());
            i++;
        }*/


        //mensen
        this.calcDistance("");
        final Spinner spinner = (Spinner) findViewById(R.id.mensaSpin);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mensen);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

}
