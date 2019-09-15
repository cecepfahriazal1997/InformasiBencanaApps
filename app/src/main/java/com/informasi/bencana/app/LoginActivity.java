package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.informasi.bencana.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends MasterActivity {
    private FancyButton btnLogin;
    private TextView signUp;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin        = (FancyButton) findViewById(R.id.btnLogin);
        signUp          = (TextView) findViewById(R.id.signup);
        username        = (EditText) findViewById(R.id.username);
        password        = (EditText) findViewById(R.id.password);

        initial();
    }

    private void initial() {
        helper.setupProgressDialog(pDialog, "Please wait ...");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientApiService.login(urlLogin, username.getText().toString(), password.getText().toString());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.startIntent(SignUpActivity.class, false, false, null);
            }
        });
    }
}
