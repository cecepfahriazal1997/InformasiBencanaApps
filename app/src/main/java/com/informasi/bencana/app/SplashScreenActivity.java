package com.informasi.bencana.app;

import android.os.Bundle;
import android.os.Handler;

import com.informasi.bencana.R;

public class SplashScreenActivity extends MasterActivity {
    private static int SPLASH_TIME_OUT  = 3000;
    private boolean isFirstInstall      = true;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashcreen);
    }

    public void onStart()
    {
        super.onStart();
        handler         = new Handler();

        if (helper.getSession("FIRST_INSTALL") != null)
            isFirstInstall = Boolean.parseBoolean(helper.getSession("FIRST_INSTALL"));

        if(isFirstInstall){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    helper.saveSession("FIRST_INSTALL", String.valueOf(false));
                    moveActivity();
                }
            }, SPLASH_TIME_OUT);
        } else {
            moveActivity();
        }
    }

    private void moveActivity() {
        if (helper.getSession("id") != null)
            helper.startIntent(DashboardActivity.class, true, true, null);
        else
            helper.startIntent(LoginActivity.class, true, true, null);
    }

    public void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
