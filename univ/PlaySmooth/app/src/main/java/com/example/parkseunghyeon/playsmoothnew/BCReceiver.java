package com.example.parkseunghyeon.playsmoothnew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.parkseunghyeon.playsmoothnew.Activity.StartActivity;

/**
 * Created by parkseunghyeon on 2017. 11. 17..
 */

public class BCReceiver extends BroadcastReceiver {
    static final String TAG = "BCReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals("kt.action.voicecommand.asr")) {
            String cmd = intent.getStringExtra("kwsText");
            cmd = cmd.replace(" ", "");
            if (cmd.equals("가자")) {
                startApp(context);
            }
        }
    }

    private void startApp(Context context) {
        Intent intent = new Intent(context, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.startActivity(intent);
    }
}