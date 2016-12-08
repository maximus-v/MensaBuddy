package com.dev.app.mensabuddy;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class StartActivity extends AppCompatActivity {

    //private String urlJsonObject = "http://10.100.6.211:8080/BuddyService/mensa?name=AlteMensa&datum=20161208";
    private String urlJsonObject = "http://openmensa.org/api/v2/canteens/79/days/20161208/meals/";

    private static String TAG = StartActivity.class.getSimpleName();
    private Button btnMakeObjectRequest;

    private ProgressDialog progressDialog;

    private TextView textView;

    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnMakeObjectRequest = (Button) findViewById(R.id.btnObjRequest);
        textView = (TextView) findViewById(R.id.txtResponse);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Bitte warten ... ");
        progressDialog.setCancelable(false);

        btnMakeObjectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonObjectRequest();
            }
        });
    }

    private void makeJsonObjectRequest() {
        showProgessDialog();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJsonObject, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    String erstesMenue = response.getString("erstesMenue");
                    String zweitesMenue = response.getString("zweitesMenue");
                    String drittesMenue = response.getString("drittesMenue");


                    jsonResponse += erstesMenue + "\n";
                    jsonResponse += zweitesMenue + "\n";
                    jsonResponse += drittesMenue + "\n";

                    textView.setText(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private void showProgessDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
