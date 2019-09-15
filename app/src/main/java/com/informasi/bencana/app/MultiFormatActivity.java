package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */

public class MultiFormatActivity extends MasterActivity {
    private Toolbar toolbar;
    private WebView webView;
    private String typeFile, content;
    private RelativeLayout contentLayout;
    private FrameLayout notifBar;
    private FrameLayout emptyState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_format);
        try {
            toolbar         = (Toolbar) findViewById(R.id.toolbar);
            webView         = (WebView) findViewById(R.id.webView);
            contentLayout   = (RelativeLayout) findViewById(R.id.layoutData);
            notifBar        = (FrameLayout) findViewById(R.id.notifBar);
            emptyState      = (FrameLayout) findViewById(R.id.emptyState);

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("" + getIntent().getStringExtra("title"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            initialData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialData()
    {
        try {
            helper.setupProgressDialog(pDialog, "Loading ...");
            typeFile            = getIntent().getStringExtra("typeFile");
            content             = getIntent().getStringExtra("content");

            if (typeFile.equalsIgnoreCase("doc") ||
                    typeFile.equalsIgnoreCase("embed")) {
                formatIsEmbed(content);
            } else {
                formatText(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void formatIsEmbed(final String content) {
        contentLayout.setPadding(0, 0, 0, 0);
        webView.setVisibility(View.VISIBLE);
        helper.formatIsEmbed(pDialog, webView, content);
    }

    private void formatText(String content) {
        contentLayout.setPadding(32, 32, 32, 32);
        webView.setVisibility(View.VISIBLE);
        helper.formatIsText(webView, content, "#000");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}