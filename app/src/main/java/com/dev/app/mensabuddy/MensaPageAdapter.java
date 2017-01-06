package com.dev.app.mensabuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Max on 08.12.2016.
 */

public class MensaPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] {"Alte Mensa", "Zeltmensa", "Siedepunkt" };
    private String fragmentText = "Text";
    private Context context;

    private ProgressDialog progressDialog;
    private String jsonResponse;
    private static String TAG = StartActivity.class.getSimpleName();
    private String urlJsonObject;

    private String mensa;

    public MensaPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        fragmentText = makeJsonObjectRequest(position - 1);
        return MensaFragment.newInstance(position + 1, fragmentText);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    private String makeJsonObjectRequest(int position) {

        switch (position) {
            case 0: mensa = "AlteMensa";
                break;
            case 1: mensa = "Zelt";
                break;
            case 2: mensa = "Siedepunkt";
                break;
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String datum = "";

        if (day < 10) {
            datum = "datum=" + year + month + "0" + day;
        } else {
            datum = "datum=" + year + month + day;
        }

        //urlJsonObject = "http://192.168.0.18:8080/BuddyService/mensa?name=" + mensa + "&" + datum + "";
        //urlJsonObject = "http://10.100.7.205:8080/BuddyService/mensa?name=" + mensa + "&" + datum + "";
        urlJsonObject = "http://10.100.7.205:8080/BuddyService/mensa?name=AlteMensa&datum=20170106";

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Laden ... ");
        progressDialog.setCancelable(false);

        showProgessDialog();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJsonObject, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    String erstesMenue = response.getString("erstesMenue");
                    String zweitesMenue = response.getString("zweitesMenue");
                    String drittesMenue = response.getString("drittesMenue");


                    jsonResponse = erstesMenue + "\n\n";
                    jsonResponse += zweitesMenue + "\n\n";
                    jsonResponse += drittesMenue + "\n\n";
                } catch (JSONException e) {
                    e.printStackTrace();
                    jsonResponse = e.getMessage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                jsonResponse = error.getMessage();
            }
        });
        hideProgressDialog();
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        return jsonResponse;
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
