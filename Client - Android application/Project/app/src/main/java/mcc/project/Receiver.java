package mcc.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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

/**
 * Created by SUMIT on 5/5/2017.
 */
public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Receiver", "Broadcast Received");
        Intent service = new Intent(context, intermittentNetworkIntentService.class);
        service.putExtra("isNetworkConnected",isConnected(context));
        Log.d("Status", String.valueOf(isConnected(context)));
        context.startService(service);
    }

    public  boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
