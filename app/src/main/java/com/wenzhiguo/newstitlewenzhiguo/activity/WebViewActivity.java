package com.wenzhiguo.newstitlewenzhiguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wenzhiguo.newstitlewenzhiguo.R;
import com.wenzhiguo.newstitlewenzhiguo.Signal;

public class WebViewActivity extends Signal {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //接受传值
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        //找控件
        WebView web = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = web.getSettings();
        //支持js交互
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        //加载路径
        web.loadUrl(url);
        //设置本地链接加载
        web.setWebViewClient(new WebViewClient());
    }
}
