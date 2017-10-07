package mcc.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static mcc.project.intermittentNetworkService.sendJSON;

/**
 * Created by SUMIT on 5/12/2017.
 */
public class ManualMode extends AppCompatActivity {

    EditText url1;
    EditText url2;
    EditText url3;
    ArrayList<JSONObject> json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_mode_layout);
        url1 = (EditText)findViewById(R.id.editText1);
        url2 = (EditText)findViewById(R.id.editText2);
        url3 = (EditText)findViewById(R.id.editText3);
    }

    public void fetch(View v){
        json = new ArrayList<>();
        try {
            JSONObject current = new JSONObject();
            current.put("title", "Facebook");
            current.put("url", "http://" + url1.getText() + "/");
            //current.put("url", "http://trainstatus.info/running-status/12431-rajdhani-exp-today/");
            current.put("datetime", DateFormat.getDateTimeInstance().format(new Date()));
            current.put("visits", 1 + "");
            json.add(current);
            Log.d("Size", json.size()+"");
            sendJSON(json);
            current = new JSONObject();
            current.put("title", "Engadget");
            current.put("url", "http://" + url2.getText() + "/");
            current.put("datetime", DateFormat.getDateTimeInstance().format(new Date()));
            current.put("visits", 1 + "");
            json.add(current);
            Log.d("Size", json.size()+"");
            sendJSON(json);
            current = new JSONObject();
            current.put("title", "Android Police");
            current.put("url", "http://" + url3.getText() + "/");
            current.put("datetime", DateFormat.getDateTimeInstance().format(new Date()));
            current.put("visits", 1 + "");
            json.add(current);
            Log.d("Size", json.size()+"");
            sendJSON(json);
            finish();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
