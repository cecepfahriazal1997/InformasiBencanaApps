package com.informasi.bencana.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.informasi.bencana.R;
import com.informasi.bencana.other.ApiService;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class FormProgressActivity extends MasterActivity {
    private Toolbar toolbar;
    private CardView cardComplication, cardComplicationDetail, cardProgress, cardStatus;
    private TextView id, name, gender, age, complication, complicationDetail, progress, status;
    private EditText year, month, week, day, remark;
    private FancyButton btnSubmit;
    private Map<String, String> param = new HashMap<>();
    private String complicationCode, complicationDetailCode = "0", progressCode, statusCode;
    private LinearLayout bodyComplicationDetail;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_progress);

        toolbar                 = (Toolbar) findViewById(R.id.toolbar);
        cardComplication        = (CardView) findViewById(R.id.cardComplication);
        cardComplicationDetail  = (CardView) findViewById(R.id.cardComplicationDetail);
        cardProgress            = (CardView) findViewById(R.id.cardProgress);
        cardStatus              = (CardView) findViewById(R.id.cardStatus);
        id                      = (TextView) findViewById(R.id.id);
        name                    = (TextView) findViewById(R.id.name);
        gender                  = (TextView) findViewById(R.id.gender);
        age                     = (TextView) findViewById(R.id.age);
        complication            = (TextView) findViewById(R.id.complication);
        complicationDetail      = (TextView) findViewById(R.id.complicationDetail);
        progress                = (TextView) findViewById(R.id.progress);
        status                  = (TextView) findViewById(R.id.status);
        year                    = (EditText) findViewById(R.id.year);
        month                   = (EditText) findViewById(R.id.month);
        week                    = (EditText) findViewById(R.id.week);
        day                     = (EditText) findViewById(R.id.day);
        remark                  = (EditText) findViewById(R.id.remark);
        btnSubmit               = (FancyButton) findViewById(R.id.btnSubmit);
        bodyComplicationDetail  = (LinearLayout) findViewById(R.id.contentComplicationDetail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("" + getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
    }

    private void initial() {
        helper.setupProgressDialog(pDialog, "Saving data ...");
        id.setText("" + getIntent().getStringExtra("id"));
        name.setText("" + getIntent().getStringExtra("name"));
        gender.setText("" + (getIntent().getStringExtra("gender").equals("0") ? "Laki-laki" :
                "Perempuan"));
        age.setText("" + getIntent().getStringExtra("age") + " Tahun");

        if (getIntent().getStringExtra("type").equals("update")) {
            year.setText("" + getIntent().getStringExtra("year"));
            month.setText("" + getIntent().getStringExtra("month"));
            week.setText("" + getIntent().getStringExtra("week"));
            day.setText("" + getIntent().getStringExtra("day"));
            complication.setText("" + getIntent().getStringExtra("complicationLabel"));
            complicationDetail.setText("" + getIntent().getStringExtra("complicationDtlLabel"));
            String labelProgress = "";
            if (getIntent().getStringExtra("progress").equals("I"))
                labelProgress   = "Membaik";
            else if (getIntent().getStringExtra("progress").equals("S"))
                labelProgress   = "Stabil";
            else if (getIntent().getStringExtra("progress").equals("D"))
                labelProgress   = "Menurun";

            String labelStatus = "";
            if (getIntent().getStringExtra("status").equals("HE"))
                labelStatus = "Sehat";
            else if (getIntent().getStringExtra("status").equals("DA"))
                labelStatus = "Meninggal";
            else
                labelStatus = "Cacat";

            progress.setText("" + labelProgress);
            status.setText("" + labelStatus);
            remark.setText("" + getIntent().getStringExtra("remark"));

            complicationCode = getIntent().getStringExtra("complication");
            complicationDetailCode = getIntent().getStringExtra("complicationDtl");
            progressCode = getIntent().getStringExtra("progress");
            statusCode = getIntent().getStringExtra("status");
        }

        cardComplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "complication");
                helper.startIntentForResult(DataMasterActivity.class, param, 0);
            }
        });
        cardComplicationDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "complicationDetail");
                helper.startIntentForResult(DataMasterActivity.class, param, 1);
            }
        });
        cardProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "progress");
                helper.startIntentForResult(DataMasterActivity.class, param, 2);
            }
        });
        cardStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "status");
                helper.startIntentForResult(DataMasterActivity.class, param, 3);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year.getText().toString().isEmpty()) {
                    year.setError("Please enter Year !");
                } else if (month.getText().toString().isEmpty()) {
                    month.setError("Please enter Month !");
                } else if (week.getText().toString().isEmpty()) {
                    week.setError("Please enter Week !");
                } else if (day.getText().toString().isEmpty()) {
                    day.setError("Please enter Day !");
                } else if (complicationCode.isEmpty()) {
                    complication.setError("Please choose Complication !");
                } else if (progressCode.isEmpty()) {
                    progress.setError("Please choose Progress !");
                } else if (statusCode.isEmpty()) {
                    status.setError("Please choose Status !");
                } else if (remark.getText().toString().isEmpty()) {
                    remark.setError("Please enter Remark !");
                } else {
                    Map<String, String> param = new HashMap<>();
                    if (getIntent().getStringExtra("type").equals("update"))
                        param.put("id", getIntent().getStringExtra("idProg"));
                    param.put("patientId", id.getText().toString());
                    param.put("year", year.getText().toString());
                    param.put("month", month.getText().toString());
                    param.put("week", week.getText().toString());
                    param.put("day", day.getText().toString());
                    param.put("complication", complicationCode);
                    param.put("complicationDetail", complicationDetailCode);
                    param.put("progress", progressCode);
                    param.put("status", statusCode);
                    param.put("remark", remark.getText().toString());
                    param.put("userInput", helper.getSession("name"));
                    if (getIntent().getStringExtra("type").equals("add")) {
                        clientApiService.insertPatientProgress(insertProgressPatient, param, new ApiService.hashMapListener() {
                            @Override
                            public String getHashMap(Map<String, String> hashMap) {
                                if (hashMap.get("success").equals("1")) {
                                    helper.showToast(hashMap.get("message"), 0);
                                    finish();
                                } else {
                                    helper.showToast(hashMap.get("message"), 1);
                                }
                                return null;
                            }
                        });
                    } else {
                        clientApiService.updatePatientProgress(updateProgressPatient, param, new ApiService.hashMapListener() {
                            @Override
                            public String getHashMap(Map<String, String> hashMap) {
                                if (hashMap.get("success").equals("1")) {
                                    helper.showToast(hashMap.get("message"), 0);
                                    finish();
                                } else {
                                    helper.showToast(hashMap.get("message"), 1);
                                }
                                return null;
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        helper.hideKeyboard();

        if (resultCode == RESULT_OK) {
            String id       = data.getStringExtra("id");
            String title    = data.getStringExtra("title");
            switch (requestCode) {
                case 0:
                    complicationCode = id;
                    complication.setText(title);
                    complication.setTextColor(ContextCompat.getColor(FormProgressActivity.this,
                            R.color.colorBlack));
                    bodyComplicationDetail.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    complicationDetailCode = id;
                    complicationDetail.setText(title);
                    complicationDetail.setTextColor(ContextCompat.getColor(FormProgressActivity.this,
                            R.color.colorBlack));
                    break;
                case 2:
                    progressCode = id;
                    progress.setText(title);
                    progress.setTextColor(ContextCompat.getColor(FormProgressActivity.this,
                            R.color.colorBlack));
                    break;
                case 3:
                    statusCode = id;
                    status.setText(title);
                    status.setTextColor(ContextCompat.getColor(FormProgressActivity.this,
                            R.color.colorBlack));
                    break;
            }
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