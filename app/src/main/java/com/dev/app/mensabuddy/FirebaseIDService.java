package com.dev.app.mensabuddy;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.dev.app.mensabuddy.StartActivity.TAG;

/**
 * Created by chris on 22.01.2017.
 * gets called on Startup of Application an generates an new FirebaseToken if necessary
 */

public class FirebaseIDService extends FirebaseInstanceIdService{


    //returns current FirebaseToken
    public String getToken(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        return refreshedToken;
    }

    //gets calles when new Token is generated
    @Override
    public void onTokenRefresh(){
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // store new FirebaseToken in AppController
        AppController appController = (AppController) getApplicationContext();
        appController.setFirebaseToken(FirebaseInstanceId.getInstance().getToken());
        //TODO Webservice Post request: Token
    }



}
