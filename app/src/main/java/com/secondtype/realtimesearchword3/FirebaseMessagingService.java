package com.secondtype.realtimesearchword3;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Moon on 2017-03-26.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    Bitmap bigPicture;
    String imgUrl = null;

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendPushNotification(remoteMessage.getData().get("message"));
        imgUrl = remoteMessage.getData().get("url");
        Log.v("message all " , remoteMessage.getData().toString());

        if(imgUrl == null) {
            Log.v("message test", "NULL");
        }else{
            Log.v("message test", imgUrl);
        }
        try {
            JSONObject obj = new JSONObject(remoteMessage.toString());
            imgUrl = obj.getString("url");
            if(imgUrl == null) {
                Log.v("message test 2", "NULL");
            }else{
                Log.v("message test 2", imgUrl);
            }
        }catch(Exception e){

        }

    }

    private void sendPushNotification(String message) {
        System.out.println("received message : " + message);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        if(imgUrl == null) {
            Log.v("message test 3", "NULL");
        }else{
            Log.v("message test 3", imgUrl);
        }

        if(imgUrl != null){
            try{
                URL url = new URL(imgUrl);
                URLConnection conn = url.openConnection();
                conn.connect();

                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                bigPicture = BitmapFactory.decodeStream(bis);

            }catch(Exception e){

            }
        }

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.refresh).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher) )
                .setContentTitle("Push Title ")
                .setContentText("두 손가락으로 아래로 드래그하세요.")
                .setAutoCancel(true)
                .setSound(defaultSoundUri).setLights(000000255,500,2000)
                .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bigPicture))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire(5000);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
