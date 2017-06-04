package com.example.android.restful.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.android.restful.utils.HttpHelper;

import java.io.IOException;

public class MyService extends IntentService {

    public static final String TAG = "MyService";
    public static final String INTENT_SERVICE_MESSAGE = "intent service message";
    public static final  String INTENT_SERVICE_PAYLOAD = "intent service payload";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // method ini dipanggil otomatis

        Uri uri = intent.getData();
        Log.i(TAG, " uri "+uri);

        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        String response;
        try {
            response = HttpHelper.downloadUrl(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        Intent messageIntent = new Intent(INTENT_SERVICE_MESSAGE);
        messageIntent.putExtra(INTENT_SERVICE_PAYLOAD, response);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);


    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "OnCreated");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "OnDestroy");
    }
}
