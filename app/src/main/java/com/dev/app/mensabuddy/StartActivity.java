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

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private String urlJsonObject = "http://192.168.0.18:8080/BuddyService/mensa?name=AlteMensa";

    private static String TAG = StartActivity.class.getSimpleName();
    private Button btnMakeObjectRequest;

    private ProgressDialog progressDialog;

    private TextView textView;

    private List<String> mealList;

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
                textView.setText(makeJsonObjectRequest().get(0));
            }
        });
    }

    private List<String> makeJsonObjectRequest() {
        showProgessDialog();

        mealList = new ArrayList<String>();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJsonObject, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    String erstesMenue = response.getString("erstesMenue");
                    String zweitesMenue = response.getString("zweitesMenue");
                    String drittesMenue = response.getString("drittesMenue");

                    mealList.add(erstesMenue);
                    mealList.add(zweitesMenue);
                    mealList.add(drittesMenue);
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

        return mealList;
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
