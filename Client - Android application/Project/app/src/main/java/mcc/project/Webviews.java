package mcc.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Webviews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviews);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebView webView = (WebView)findViewById(R.id.web_view);
        webView.loadUrl(url);
    }
}
