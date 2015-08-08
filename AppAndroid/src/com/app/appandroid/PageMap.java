package com.app.appandroid;


import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class PageMap extends Activity {


   private WebView myWebView;
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.page_map);
  myWebView = (WebView) findViewById(R.id.webview);
       
       myWebView.getSettings().setLoadsImagesAutomatically(true);
       myWebView.getSettings().setJavaScriptEnabled(true);
       myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
       myWebView.setWebViewClient(new MyBrowser());
       myWebView.loadUrl("http://victorymonumentmap.com/App_Travel_Korat/map/");
    }

    private class MyBrowser extends WebViewClient {
       @Override
       public boolean shouldOverrideUrlLoading(WebView view, String url) {
          view.loadUrl(url);
          return true;
       }
    }
 }





