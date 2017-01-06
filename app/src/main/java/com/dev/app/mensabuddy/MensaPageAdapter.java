package com.dev.app.mensabuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
        return MensaFragment.newInstance(position + 1, makeJsonObjectRequest(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }




    //----------------------------------------------------------------------------------------------

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

        if (month < 10) {
            if (day < 10) {
                datum = "datum=" + Integer.toString(year) + "0" + Integer.toString(month) + "0" + Integer.toString(day);
            } else {
                datum = "datum=" + Integer.toString(year) + "0" + Integer.toString(month) + "" + Integer.toString(day);
            }
        } else {
            if (day < 10) {
                datum = "datum=" + Integer.toString(year) + "" + Integer.toString(month) + "0" + Integer.toString(day);
            } else {
                datum = "datum=" + Integer.toString(year) + "" + Integer.toString(month) + "" + Integer.toString(day);
            }
        }

        urlJsonObject = "http://192.168.0.18:8080/BuddyService/mensa?name=" + mensa + "&" + datum + "";
        //urlJsonObject = "http://10.100.7.205:8080/BuddyService/mensa?name=" + mensa + "&" + datum + "";
        //urlJsonObject = "http://10.100.7.205:8080/BuddyService/mensa?name=AlteMensa&datum=20170106";

        return urlJsonObject;
    }
}
