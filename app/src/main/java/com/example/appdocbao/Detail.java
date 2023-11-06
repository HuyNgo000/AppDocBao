package com.example.appdocbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class Detail extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webView = findViewById(R.id.webviewtintuc);
        Intent intent = getIntent();
        String link = intent.getStringExtra("Linktintuc");
        webView.loadUrl(link);
    }
}