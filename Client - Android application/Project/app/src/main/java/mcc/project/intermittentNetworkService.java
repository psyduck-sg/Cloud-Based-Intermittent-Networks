package mcc.project;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by SUMIT on 5/5/2017.
 */

public class intermittentNetworkService extends Service {
    static WebSocketClient mWebSocketClient;
    static boolean firstTimeSent = false;
    static String[] url = {"URL1", "URL2", "URL3"};

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getConnected();
        if(firstTimeSent == false){
            FetchHistory fH = new FetchHistory();
            sendJSON(fH.fetchHistory(getBaseContext()));
            firstTimeSent = true;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Destroy", "Destroyed");
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    public void connectWebSocket() {
        URI uri;

        try {
            uri = new URI("ws://52.11.96.244:80");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.d("Websocket", "Opened");
                firstTimeSent = true;
            }

            @Override
            public void onMessage(String s) {
                Log.d("Websocket", "Message Received is " + s);
                url = s.split("___");
            }

            @Override
            public void onMessage( ByteBuffer data ){
                Log.d("Websocket", "Received");
                Saving saving = new Saving();
                saving.makeZip(data,getBaseContext());
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.d("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    public static void sendJSON(ArrayList<JSONObject> json){
        while("NOT_YET_CONNECTED".equals(mWebSocketClient.getReadyState().toString()));
        Log.d("Log", mWebSocketClient.getReadyState().toString());
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<json.size();i++){
            sb.append(json.get(i) + "\n");
            Log.d("JSON", json.get(i).toString());
        }
        mWebSocketClient.send(sb.toString());
    }

    private void getConnected(){
        connectWebSocket();
    }

    public boolean serviceRunning(){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if ("mcc.project.intermittentNetworkService".equals(service.service.getClassName()))
            {
                Log.d("tag4", "Not starting");
                return true;
            }
        }
        return false;
    }
}