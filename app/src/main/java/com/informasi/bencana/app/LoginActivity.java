package com.informasi.bencana.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.other.FunctionHelper;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends Activity {
    private FancyButton btnLogin;
    private TextView signUp;
    private FunctionHelper functionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        functionHelper  = new FunctionHelper(this);
        btnLogin        = (FancyButton) findViewById(R.id.btnLogin);
        signUp          = (TextView) findViewById(R.id.signup);

        initial();
    }

    private void initial() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functionHelper.startIntent(DashboardActivity.class, false, false, null);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functionHelper.startIntent(SignUpActivity.class, false, false, null);
            }
        });
    }
}
