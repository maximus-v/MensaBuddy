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

public class StartMatchingActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemSelectedListener {

    //private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = StartMatchingActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private TextView t;
    private DistanceCalculator DC = new DistanceCalculator();

    //Orte der Mensen
    private HashMap<String, Double> Canteens = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_matching);


        //Label erstellen
        t=new TextView(this);
        t=(TextView)findViewById(R.id.lblLocation);

        //Dropdown erstellen
        Spinner spinner = (Spinner) findViewById(R.id.mensaSpin);
        spinner.setOnItemSelectedListener(this);
        //Elemente einfügen
        List<String> mensen = new ArrayList<String>();
        mensen.add("Alte Mensa");
        mensen.add("Zeltschlösschen");
        mensen.add("Siedepunkt");
        mensen.add("UBoot");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mensen);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        //Google API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onResume(){
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
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            // Blank for a moment...
        }
        else {
            handleNewLocation(location);
            double lat1 = location.getLatitude();
            double lng1 = location.getLongitude();

            //Orte der Mensen zufügen
            Canteens.put("Alte MensaLat", 51.027199);
            Canteens.put("Alte MensaLng", 13.726550);
            Canteens.put("ZeltschlösschenLat", 51.031318);
            Canteens.put("ZeltschlösschenLng", 13.728419);
            Canteens.put("SiedepunktLat", 51.029140);
            Canteens.put("SiedepunktLng", 13.738530);
            Canteens.put("UBootLat", 51.030100);
            Canteens.put("UBootLng", 13.729319);

            double lat2 = Canteens.get(Mensa+"Lat");
            double lng2 = Canteens.get(Mensa+"Lng");

            String i = DC.calculateDistance(lat1, lat2, lng1, lng2);
            t.setText(i);
        };
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


}
