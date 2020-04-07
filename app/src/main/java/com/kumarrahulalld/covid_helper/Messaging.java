package com.kumarrahulalld.covid_helper;
import android.app.Notification;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Messaging extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification()!=null){
            String Title=remoteMessage.getNotification().getTitle();
            String Txt=remoteMessage.getNotification().getBody();
            Context context;
            NotificationCompat.Builder m = new NotificationCompat.Builder(this,"Help Note." ).setSmallIcon(R.mipmap.ici_launcher_round).setContentTitle(Title).setContentText(Txt);
            NotificationManagerCompat nm = NotificationManagerCompat.from(this);
            nm.notify(1,m.build());
        }
    }
}
