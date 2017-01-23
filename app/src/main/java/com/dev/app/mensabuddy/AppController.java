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

    private int id;
    private String startzeit1;
    private String startzeit2;
    private String mensa;
    private String firebaseToken;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized AppController getInstance() {
        return instance;
    }

    public void setStartzeit1(String zeit) {
        this.startzeit1 = zeit;
    }

    public String getStartzeit1() {
        return startzeit1;
    }

    public void setStartzeit2(String zeit) {
        this.startzeit2 = zeit;
    }

    public String getStartzeit2() {
        return startzeit2;
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

    public void setFirebaseToken(String Token){
        this.firebaseToken=Token;
    }

    public String getFirebaseToken(){
        return this.firebaseToken;
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

}
