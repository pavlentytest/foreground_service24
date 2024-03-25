package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private MediaPlayer media;
    public static final String CHANNEL_ID = "Channel";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        media = MediaPlayer.create(this, R.raw.betkhoven);
        media.setLooping(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        media.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // уведомление юзера
        CharSequence name = getString(R.string.app_name);
        String description = getString(R.string.description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Media player ")
                .setContentText("is starting!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        // старт медиаплеера
        startForeground(1,builder.build());
        // > 5 ui
        media.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
