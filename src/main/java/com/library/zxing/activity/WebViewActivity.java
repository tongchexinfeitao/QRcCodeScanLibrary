package com.library.zxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.library.zxing.R;

/**
 * 1. 类的用途
 * 2. @author forever
 * 3. @date 2017/4/17 13:18
 */
//http://www.imooc.com/qadetail/93285
public class WebViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebView webView = (WebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }
}
