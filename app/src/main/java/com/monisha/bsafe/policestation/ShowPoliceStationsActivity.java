package com.monisha.bsafe.policestation;

import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.monisha.bsafe.R;

import java.util.Objects;

public class ShowPoliceStationsActivity extends AppCompatActivity {

    // Intent -> It is a messaging object we can request an action from another app component.
    // 1. Start an activity: Activity is a single screen activity we can start new instance activity by passing
    // an Intent to startActivity()
    // Ex: from call police Intent activity grid to actually calling police no(100)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_police_stations);
        WebView webView = findViewById(R.id.webViewId);
        String lat = getIntent().getStringExtra("lat");
        String lon = getIntent().getStringExtra("lon");
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });
        if (Objects.equals(lat, "0") || Objects.equals(lon, "0")) {
            lat = "12.9565742";  // giving latitude
            lon = "77.6573954";  // giving longitude
        }
        String loc = "@" + lat + "," + lon;
        Toast.makeText(this, "Location " + loc, Toast.LENGTH_SHORT).show();
        webView.loadUrl("https://www.google.com/maps/search/nearby+police+station/" + loc);
    }
}