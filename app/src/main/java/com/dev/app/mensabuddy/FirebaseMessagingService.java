package com.dev.app.mensabuddy;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;

import static com.dev.app.mensabuddy.StartActivity.TAG;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    public FirebaseMessagingService() {
    }

    //gets called when message is coming in while App is running
    //creates PushNotification with FirebaseMessageContent
    @Override
    public void onMessageReceived(RemoteMessage Message){


        Log.d(TAG, "Firebase Massage received from: " + Message.getFrom() + "Please check Matches!");
        try { Log.d(TAG, "Message:"+Message.getTtl());

        } catch (Exception e){
            Log.d(TAG, "Didn't get notification because: "+e.getMessage());
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("Mensa Buddy")
                        .setContentText("You've got a new Match - Check it out now!");

        Intent resultIntent = new Intent(this, MatchingActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MatchingActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int mId = 0;
        mNotificationManager.notify(mId, mBuilder.build());


    }


}
