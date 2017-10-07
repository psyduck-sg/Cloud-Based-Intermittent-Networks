package mcc.project;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Browser;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static mcc.project.intermittentNetworkService.mWebSocketClient;
import static mcc.project.intermittentNetworkService.sendJSON;

/**
 * Created by SUMIT on 5/5/2017.
 */

public class intermittentNetworkIntentService extends IntentService {
    Handler mHandler;
    intermittentNetworkService service2;

    public intermittentNetworkIntentService() {
        super("intermittentNetworkIntentService");
        mHandler = new Handler();
    }

    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        Log.d("Service","Initialising");
        mHandler.post(new DisplayToast(this, "intent Service Started"));
        boolean isNetworkConnected = extras.getBoolean("isNetworkConnected");

        if(isNetworkConnected){
            mHandler.post(new DisplayToast(this, "Connection Available"));
            Log.d("TAG","You are Connected to Internet");
            startService(new Intent(getBaseContext(), intermittentNetworkService.class));
         }
        else {
            mHandler.post(new DisplayToast(this, "Connect to Internet"));
            Log.d("TAG","You are not Connected to Internet");
        }
    }
}
