package com.example.termtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu1Fragment extends Fragment{
    ImageButton imageButton1, imageButton2, imageButton3;
    Intent intent = new Intent(Intent.ACTION_VIEW);
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String getTime = sdf.format(date);
    private WebView mWebView;  //웹뷰
    private WebSettings mWebSettings;  //웹뷰 세팅

    Uri cgv = Uri.parse("http://m.cgv.co.kr/Schedule/?tc=0001&t=T&ymd="+getTime+"&src=");
    Uri lotte = Uri.parse("http://www.lottecinema.co.kr/LCHS/Contents/ticketing/movie-schedule.aspx");
    Uri mega = Uri.parse("http://m.megabox.co.kr/?menuId=booking&mBookingType=2");

    public Menu1Fragment() {
        // Required empty public constructor

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

    }

    private void setContentView(int activity_main) {
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home, container, false);

        imageButton1 = (ImageButton) v.findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) v.findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) v.findViewById(R.id.imageButton3);
        mWebView = (WebView) v.findViewById(R.id.webview);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mWebView.setWebViewClient(new WebViewClient());
                mWebSettings = mWebView.getSettings();
                mWebSettings.setJavaScriptEnabled(true);
                mWebView.loadUrl(String.valueOf(cgv));

            }

        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mWebView.setWebViewClient(new WebViewClient());
                mWebSettings = mWebView.getSettings();
                mWebSettings.setJavaScriptEnabled(true);
                mWebSettings.setLoadWithOverviewMode(true);
                mWebView.loadUrl(String.valueOf(lotte));
            }

        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mWebView.setWebViewClient(new WebViewClient());
                mWebSettings = mWebView.getSettings();
                mWebSettings.setJavaScriptEnabled(true);
                mWebView.loadUrl(String.valueOf(mega));
            }
        });
        return v;
    }
}
