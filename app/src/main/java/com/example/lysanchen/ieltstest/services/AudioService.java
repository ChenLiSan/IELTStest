package com.example.lysanchen.ieltstest.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class AudioService extends Service {
    public AudioService() {
    }

    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    public void onCreate()
    {
        mp = new MediaPlayer();
        mp.setLooping(false);

    }
    public void onDestroy()
    {
        mp.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {

            String url =(String) intent.getExtras().get("url");
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(url);
            mp.prepare();
            mp.start();

        }catch (Exception ex){}

        return Service.START_STICKY;
    }
}
