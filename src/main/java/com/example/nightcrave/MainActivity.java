package com.example.nightcrave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
WebView crave;
Button mButton;
ImageView mImage;
private View view1, view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view1 = getLayoutInflater().inflate(R.layout.activity_main, null);
        view2 = getLayoutInflater().inflate(R.layout.no_internet, null);
        setContentView(view1);
        mImage = findViewById(R.id.front_screen1);
        mButton = findViewById(R.id.button);
        crave = (WebView) findViewById(R.id.crave_webView);
        if (haveNetwork()){
            Log.d("CON","internet");
            WebSettings webSettings= crave.getSettings();
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setJavaScriptEnabled(true);

            crave.loadUrl("https://www.nightcrave.club/");

            crave.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                //hide loading image
                mImage.setVisibility(View.GONE);
                //show webview
                crave.setVisibility(View.VISIBLE);
            }


        });
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mImage.setVisibility(View.GONE);
//                    //show webview
//                    crave.setVisibility(View.VISIBLE);
//                    //hide loading imag
//                }
//            },4000);

        }
        else if (!haveNetwork()) {
            Log.d("CON"," NO internet");
            Toast.makeText(MainActivity.this, "Network connection is not available", Toast.LENGTH_SHORT).show();
            setContentView(view2);
            mButton = findViewById(R.id.button);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });

        }


    }

    @Override
    public void onBackPressed(){
        if(crave.canGoBack()){
            crave.goBack();
        }
        else {
            super.onBackPressed();
        }
    }

    private boolean haveNetwork(){
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isWifiConn = false;
        boolean isMobileConn = false;

        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();

            }
        }


        Log.d("DEBUG_TAG", "Wifi connected: " + isWifiConn);
        Log.d("DEBUG_TAG", "Mobile connected: " + isMobileConn);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

       // return isWifiConn|isMobileConn;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
}
