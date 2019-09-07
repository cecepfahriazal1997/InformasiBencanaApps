package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;

public class DetailNewsActivity extends AppCompatActivity {
    private WebView Content;
    private Toolbar toolbar;
    private TextView Title, Dates;
    private String title, content, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        Content             = (WebView) findViewById(R.id.content);
        toolbar             = (Toolbar) findViewById(R.id.toolbar);
        Title               = (TextView) findViewById(R.id.title);
        Dates               = (TextView) findViewById(R.id.date);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setData();
    }

    private void setData() {
        try {
            title       = getIntent().getStringExtra("title");
            content     = "<body style='margin: 0; padding: 0; color: #C7C4C4; text-align: justify;'>";
            content     += getIntent().getStringExtra("content");
            content     += "</body>";
            date        = getIntent().getStringExtra("date");

            Title.setText(title);
            Dates.setText("Published : " + date);

            Content.getSettings().setJavaScriptEnabled(true);
            Content.loadData(content, "text/html; charset=utf-8", "UTF-8");
            Content.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadData(content, "text/html; charset=utf-8", "UTF-8");
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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