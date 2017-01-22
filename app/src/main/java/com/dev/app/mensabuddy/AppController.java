package com.dev.app.mensabuddy;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Max on 08.12.2016.
 */

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue requestQueue;
    private static AppController instance;

    private int time;
    private String mensa;
    private int id;
    private String firebaseToken;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized AppController getInstance() {
        return instance;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String getMensa() {
        return mensa;
    }

    public void setMensa(String mensa) {
        this.mensa = mensa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequest(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    public void setFirebaseToken(String Token){
        this.firebaseToken=Token;
    }

    public String getFirebaseToken(){
        return this.firebaseToken;
    }
}
