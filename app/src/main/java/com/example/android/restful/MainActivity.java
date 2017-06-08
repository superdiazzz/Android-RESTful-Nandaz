package com.example.android.restful;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.restful.model.DataItem;
import com.example.android.restful.services.MyService;
import com.example.android.restful.utils.NetworkHelper;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    TextView output;
    private boolean networkOk;

    public static final String JSON_URL = "http://560057.youcanlearnit.net/services/json/itemsfeed.php";

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //String message = intent.getExtras().getString(MyService.INTENT_SERVICE_PAYLOAD);
            //output.append(message + "\n");

            DataItem[] dataItems = (DataItem[]) intent.getParcelableArrayExtra(MyService.INTENT_SERVICE_PAYLOAD);
            for (DataItem dataItem: dataItems) {
                output.append(dataItem.getItemName() + "\n");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.output);

        // broadcast diatas mesti didaftarkan disini
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        localBroadcastManager.registerReceiver(mBroadcastReceiver,
                new IntentFilter(MyService.INTENT_SERVICE_MESSAGE));

        networkOk = NetworkHelper.hasNetworkAccess(this);
        output.setText("networkOk : "+networkOk);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        localBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

    public void runClickHandler(View view) {

        // ----- TASK FOR ASYCTASK ---- //
        /*output.append("Button clicked\n");
        MyAsycTask asycTask = new MyAsycTask();
        asycTask.execute("String 1", "String 2", "String 3");*/
        // -----------------------------//


        if(networkOk){
            // ------ TASK FOR INTENTSERVICE ---//
            Intent i = new Intent(view.getContext(), MyService.class);
            i.setData(Uri.parse(JSON_URL));
            startService(i);
//            startService(i);

        }else {
            Toast.makeText(this, "Network is not available", Toast.LENGTH_SHORT).show();
        }

    }

    public void clearClickHandler(View view) {
        output.setText("");
    }


    private class MyAsycTask extends AsyncTask<String, String, Void>{

        @Override
        protected Void doInBackground(String... params) {

            for (String str: params) {
                publishProgress(str);       // -- > ini adalah method yang akan mempublish seluruh parameter ke public,
                                                    // method berikutnya yg handling ini bernama onProgressUpdate
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            // disini akan dilakukan sesuatu setelah proses publishProgress
            output.append(values[0]+"\n");

        }
    }
}
