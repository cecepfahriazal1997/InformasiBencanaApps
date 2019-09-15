package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;

public class ViewHtmlActivity extends MasterActivity {
    private WebView privacyPolicy;
    private String urlPrivacy;
    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_html);

        privacyPolicy      = (WebView) findViewById(R.id.web_view_privacy);
        toolbar            = (Toolbar) findViewById(R.id.toolbar);

        urlPrivacy         = "file:///android_asset/" + getIntent().getStringExtra("file");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("" + getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        privacyPolicy.getSettings().setJavaScriptEnabled(true);
        privacyPolicy.loadUrl(urlPrivacy);
        privacyPolicy.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(urlPrivacy);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}