package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.informasi.bencana.R;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */

public class MultiFormatActivity extends MasterActivity {
    private Toolbar toolbar;
    private WebView webView;
    private PlayerView video;
    private String typeFile, content, loadType;
    private RelativeLayout contentLayout;
    private FrameLayout notifBar;
    private FrameLayout emptyState;
    private boolean isPause = false, canDestroy = false;

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
            video           = (PlayerView) findViewById(R.id.video);

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
            loadType            = getIntent().getStringExtra("loadType");

            if (typeFile.equalsIgnoreCase("doc") ||
                    typeFile.equalsIgnoreCase("embed")) {
                formatIsEmbed(urlGoogleDoc + content);
            } else if (typeFile.equalsIgnoreCase("video")) {
                formatVideo(content);
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
        helper.formatIsText(pDialog, webView, content, loadType);
    }

    private void formatVideo(String content)
    {
        video.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        helper.formatIsVideo(video, content);
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

//    @Override
//    public void onBackPressed() {
//        if (typeFile.equalsIgnoreCase("video") && video.getPlayer().isPlaying()) {
//            video.getPlayer().release();
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (canDestroy)
            releaseVideoPlayer();
    }

    private void releaseVideoPlayer() {
        if (typeFile != null) {
            if (typeFile.equalsIgnoreCase("video")) {
                if (video.getPlayer() != null && video.getPlayer().getPlaybackState() == Player.STATE_READY
                        && video.getPlayer().getPlayWhenReady())
                    video.getPlayer().release();
            }
        }
    }

    private void playVideo(boolean isPlay) {
        if (typeFile.equalsIgnoreCase("video")) {
            video.getPlayer().setPlayWhenReady(isPlay);
            video.getPlayer().getPlaybackState();
        }
    }

    public void onPause() {
        super.onPause();
        playVideo(false);
        isPause = true;
    }

    public void onResume() {
        super.onResume();
        try {
            if (isPause) {
                playVideo(true);
                canDestroy  = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (canDestroy)
            releaseVideoPlayer();
    }
}