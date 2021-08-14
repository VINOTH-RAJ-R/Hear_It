package com.example.hearit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.hearit.ApplicationClass.ACTION_NEXT;
import static com.example.hearit.ApplicationClass.ACTION_PLAY;
import static com.example.hearit.ApplicationClass.ACTION_PREVIOUS;
import static com.example.hearit.ApplicationClass.PLAYER;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive (Context context, Intent intent) {
        String actionName = intent.getAction ();
        Intent serviceIntent = new Intent (context,MusicService.class);

        if (actionName !=null){
            switch (actionName){
                case ACTION_PLAY:
                    serviceIntent.putExtra ("ActionName","playPause");
                    context.startService (serviceIntent);
                    break;
                case ACTION_NEXT:
                    serviceIntent.putExtra ("ActionName","Next");
                    context.startService (serviceIntent);
                    break;
                case ACTION_PREVIOUS:
                    serviceIntent.putExtra ("ActionName","Previous");
                    context.startService (serviceIntent);
                    break;
                case PLAYER:
                    serviceIntent.putExtra ("ActionName","Player");
                    context.startService (serviceIntent);
                    break;
            }
        }

    }
}
