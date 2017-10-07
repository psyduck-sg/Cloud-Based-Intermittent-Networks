package mcc.project;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static android.R.attr.value;
import static mcc.project.intermittentNetworkService.firstTimeSent;
import static mcc.project.intermittentNetworkService.mWebSocketClient;
import static mcc.project.intermittentNetworkService.sendJSON;
import static mcc.project.intermittentNetworkService.url;

public class MainActivity extends AppCompatActivity {
    TextView editText;
    WebView webView;
    Button url1;
    Button url2;
    Button url3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!serviceRunning()) {
            Toast.makeText(this, "App Started", Toast.LENGTH_LONG).show();
            startService(new Intent(getBaseContext(), intermittentNetworkService.class));
            editText = (TextView) findViewById(R.id.textBox);
            editText.setText("Registration Successful");
            Log.d("tag3", "Service Started");
        }
        else Log.d("tag3", "Service Already Started");

        url1 = (Button)findViewById(R.id.url1);
        url1.setText(url[0]);
        url2 = (Button)findViewById(R.id.url2);
        url2.setText(url[1]);
        url3 = (Button)findViewById(R.id.url3);
        url3.setText(url[2]);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
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

    public void update(View v){
        FetchHistory fH = new FetchHistory();
        sendJSON(fH.fetchHistory(getBaseContext()));
    }

    public void url1(View v){
        File f = new File(getFilesDir() + "/userFolder/");
        Log.d("path",f.getAbsolutePath());
        String url = "file:///" + f.getAbsolutePath() + "/url_1.html";
        Intent myIntent = new Intent(MainActivity.this, Webviews.class);
        myIntent.putExtra("url", url); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }

    public void url2(View v){
        File f = new File(getFilesDir() + "/userFolder/");
        Log.d("path",f.getAbsolutePath());
        String url = "file:///" + f.getAbsolutePath() + "/url_2.html";
        Intent myIntent = new Intent(MainActivity.this, Webviews.class);
        myIntent.putExtra("url", url); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }

    public void url3(View v){
        File f = new File(getFilesDir() + "/userFolder/");
        Log.d("path",f.getAbsolutePath());
        String url = "file:///" + f.getAbsolutePath() + "/url_3.html";
        Intent myIntent = new Intent(MainActivity.this, Webviews.class);
        myIntent.putExtra("url", url);
        MainActivity.this.startActivity(myIntent);
    }

    public void manualMode(View v){
        Intent myIntent = new Intent(this, ManualMode.class);
        startActivity(myIntent);
    }
}
