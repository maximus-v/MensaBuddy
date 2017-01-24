package com.dev.app.mensabuddy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dev.app.mensabuddy.Adapter.MensaAdapter;
import com.dev.app.mensabuddy.ListViewModel.MensaModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dev.app.mensabuddy.R.id.button;

public class StartActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    DatabaseHelper myDb;
    AppController appController;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = StartMatchingActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private DistanceCalculator DC = new DistanceCalculator();
    private ArrayList<MensaModel> mensaModels = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FirebaseIDService Service = new FirebaseIDService();
        Service.getToken();


        appController = (AppController) getApplicationContext();

        myDb = new DatabaseHelper(this);

        //Nachfolgender Code leitet User auf Profil, wenn noch nicht angelegt
        int id = myDb.getProfilId();
        if (id == 0) {
            Intent i = new Intent(getApplicationContext(), PersonalActivity.class);
            startActivity(i);
        } else {
            appController.setId(id);
        }

        /*
        //Hier könnte Ihre ArrayList stehen
        ArrayList<String> items = new ArrayList<>();
        items.add("Alte Mensa");
        items.add("GrillCube");
        items.add("Reichenbachstrasse");
        items.add("Siedepunkt");
        items.add("UBoot");
        items.add("Zeltmensa");
        String favortienMensa = "Zeltmensa";
        //Lösche Mensa von übrigen Mensen
        if (items.indexOf(favortienMensa) != 0) {
            //Favoriten-Mensa an zweite Stelle
            int index = items.indexOf(favortienMensa);
            items.remove(index);
            items.add(1, favortienMensa);
        } else {
            //Favorit und nächste Mensa sind die selbe
            items.remove(0);
            items.add(0, favortienMensa);
        }
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) findViewById(R.id.mensa_list);
        listView.setAdapter(itemsAdapter);
        */

        //Google API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        listView = (ListView) findViewById(R.id.mensa_list);



        mensaModels.add(new MensaModel("Alte Mensa"));
        mensaModels.add(new MensaModel("Grillcube"));
        mensaModels.add(new MensaModel("Reichenbachstrasse"));
        mensaModels.add(new MensaModel("Siedepunkt"));
        mensaModels.add(new MensaModel("UBoot"));
        mensaModels.add(new MensaModel("Zeltmensa"));

        MensaAdapter adapter = new MensaAdapter(mensaModels, getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MensaModel model = (MensaModel) parent.getItemAtPosition(position);
                String mensa = model.getName().replaceAll("\\s+","");
                Intent i = new Intent(getApplicationContext(), MensaMenuActivity.class);
                i.putExtra("MensaName", mensa);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume(){
        /*match = new Match(this);
        if (match.findFastMatch(2,"Alte Mensa", 1100)==1)
        {Toast.makeText(this, "it worked", Toast.LENGTH_LONG).show();}
        else Toast.makeText(this, "didn't work", Toast.LENGTH_LONG).show();*/

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.openProfil:
                Intent i = new Intent(getApplicationContext(), PersonalActivity.class);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Liste sortieren
        if (sortCanteens()){
            Toast.makeText(StartActivity.this, "Mensen erfolgreich sortiert!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(StartActivity.this, "Mensen konnten nicht sortiert werden", Toast.LENGTH_LONG).show();
        }
    }

    private boolean sortCanteens (){
        Log.i(TAG, "Location services connected.");
        Location location = null;
        try{
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);}
        catch (SecurityException e) {
            Toast.makeText(StartActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (location == null) {
            Toast.makeText(StartActivity.this, "No Location detected", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            handleNewLocation(location);
            double lat1 = location.getLatitude();
            double lng1 = location.getLongitude();

            String[] sortedCanteens = new String[5];
            sortedCanteens=DC.getSortedCanteens(lat1, lng1);
            mensaModels.clear();
            mensaModels.add(0, new MensaModel(sortedCanteens[0]));
            mensaModels.add(1, new MensaModel(sortedCanteens[1]));
            mensaModels.add(2, new MensaModel(sortedCanteens[2]));
            mensaModels.add(3, new MensaModel(sortedCanteens[3]));
            mensaModels.add(4, new MensaModel(sortedCanteens[4]));
            mensaModels.add(5, new MensaModel(sortedCanteens[5]));

            MensaAdapter adapter = new MensaAdapter(mensaModels, getApplicationContext());

            listView.setAdapter(adapter);

            return true;
        }

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
}