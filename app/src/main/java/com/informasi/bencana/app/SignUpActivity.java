package com.informasi.bencana.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.informasi.bencana.R;
import com.informasi.bencana.other.FunctionHelper;

import mehdi.sakout.fancybuttons.FancyButton;

public class SignUpActivity extends Activity {
    private FancyButton btnSubmit, btnBack;
    private FunctionHelper functionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

         btnSubmit          = findViewById(R.id.btnSubmit);
         btnBack            = findViewById(R.id.btnBack);
         functionHelper     = new FunctionHelper(this);

         initial();
    }

    private void initial() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functionHelper.showToast("Register account has successfuly !", 0);
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
