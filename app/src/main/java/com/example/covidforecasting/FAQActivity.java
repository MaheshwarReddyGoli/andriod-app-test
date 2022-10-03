package com.example.covidforecasting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.webkit.WebView;
import android.widget.TextView;

public class FAQActivity extends AppCompatActivity {

WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqactivity);

        webview=(WebView) findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/faq.html");


    }
}