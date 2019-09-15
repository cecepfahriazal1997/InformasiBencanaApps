package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.View;

import com.informasi.bencana.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class SignUpActivity extends MasterActivity {
    private FancyButton btnSubmit, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

         btnSubmit          = findViewById(R.id.btnSubmit);
         btnBack            = findViewById(R.id.btnBack);

         initial();
    }

    private void initial() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.showToast("Register account has successfuly !", 0);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
