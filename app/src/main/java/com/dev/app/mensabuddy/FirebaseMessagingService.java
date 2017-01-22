package com.dev.app.mensabuddy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;

import static com.dev.app.mensabuddy.StartActivity.TAG;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public FirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage Message){
        //gets called when message is coming in while App is in foreground

        Log.d(TAG, "From: " + Message.getFrom());
        Log.d(TAG, "Notification Message Body: " + Message.getNotification().getBody());
    }


}
