package com.informasi.bencana.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.informasi.bencana.R;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class FormPatientActivity extends MasterActivity {
    private Toolbar toolbar;
    private CardView cardLocation, cardGender;
    private TextView location, gender;
    private EditText date, id, name, age, weaknessCondition, threadCondition, remark, nameDoctor,
                    nameNurse, nameSupport;
    private FancyButton btnSubmit;
    private Map<String, String> param = new HashMap<>();
    private String locationId, genderCode;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_patient);

        toolbar         = (Toolbar) findViewById(R.id.toolbar);
        cardLocation    = (CardView) findViewById(R.id.cardLocation);
        cardGender      = (CardView) findViewById(R.id.cardGender);
        location        = (TextView) findViewById(R.id.location);
        gender          = (TextView) findViewById(R.id.gender);
        date            = (EditText) findViewById(R.id.date);
        id              = (EditText) findViewById(R.id.id);
        name            = (EditText) findViewById(R.id.name);
        age             = (EditText) findViewById(R.id.age);
        weaknessCondition   = (EditText) findViewById(R.id.weaknessCondition);
        threadCondition     = (EditText) findViewById(R.id.threadCondition);
        remark              = (EditText) findViewById(R.id.remark);
        nameDoctor          = (EditText) findViewById(R.id.nameDoctor);
        nameNurse           = (EditText) findViewById(R.id.nameNurse);
        nameSupport         = (EditText) findViewById(R.id.nameSupport);
        btnSubmit           = (FancyButton) findViewById(R.id.btnSubmit);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("" + getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
    }

    private void initial() {
        cardLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "location");
                helper.startIntentForResult(DataMasterActivity.class, param, 0);
            }
        });
        cardGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "gender");
                helper.startIntentForResult(DataMasterActivity.class, param, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        helper.hideKeyboard();
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    String id           = data.getStringExtra("id");
                    String title        = data.getStringExtra("title");
                    locationId          = id;

                    location.setText(title);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    String id           = data.getStringExtra("id");
                    String title        = data.getStringExtra("title");
                    genderCode          = id;

                    gender.setText(title);
                }
                break;
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