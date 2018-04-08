package com.example.seunghyeonpark.dailycondition;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.seunghyeonpark.dailycondition.CONDITION.DailycheckActivity;

/**
 * Created by SeungHyeonPark on 2016. 11. 13..
 */
public class Notification_receiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, DailycheckActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("오늘의 하루를 체크해보세요!!")
                .setContentText("건강검진하러가기!!")
                .setAutoCancel(true)
                .setVibrate(new long[]{200,200,500,300});

        notificationManager.notify(100,builder.build());

    }
}
