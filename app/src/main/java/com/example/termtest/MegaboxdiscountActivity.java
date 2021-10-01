package com.example.termtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MegaboxdiscountActivity extends AppCompatActivity {
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_megaboxdiscount);
        mWebView = (WebView) findViewById(R.id.megaboxdiscount);//xml 자바코드 연결
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.setInitialScale(35);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.loadUrl("http://www.megabox.co.kr/?menuId=discountcard");
    }
}
