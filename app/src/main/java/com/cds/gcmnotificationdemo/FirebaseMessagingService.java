package com.cds.gcmnotificationdemo;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by fazal on 2/6/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getData().get("message"),remoteMessage.getData().get("tickerText"));
        Log.d("msg",remoteMessage.getData().get("message"));
        /*Intent intent = new Intent();
        intent.setAction("Message_show");
        intent.putExtra("message",remoteMessage.getData().get("message"));
        sendBroadcast(intent);*/
    }

    private void sendNotification(String msg,String ticker) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent myintent = new Intent(this, MainActivity.class);
        myintent.putExtra("message", msg);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                myintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setContentTitle("CrazyApp Notification")
                        .setSubText(ticker)
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setContentText(msg);
        Log.d("ticker",ticker);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
