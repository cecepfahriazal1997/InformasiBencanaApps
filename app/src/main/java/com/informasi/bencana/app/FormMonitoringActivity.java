package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.other.ApiService;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class FormMonitoringActivity extends MasterActivity {
    private Toolbar toolbar;
    private TextView id, name, gender, age;
    private EditText year, month, week, day, fact, problem;
    private FancyButton btnSubmit;
    private Map<String, String> param = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_monitoring);

        toolbar                 = (Toolbar) findViewById(R.id.toolbar);
        id                      = (TextView) findViewById(R.id.id);
        name                    = (TextView) findViewById(R.id.name);
        gender                  = (TextView) findViewById(R.id.gender);
        age                     = (TextView) findViewById(R.id.age);
        year                    = (EditText) findViewById(R.id.year);
        month                   = (EditText) findViewById(R.id.month);
        week                    = (EditText) findViewById(R.id.week);
        day                     = (EditText) findViewById(R.id.day);
        fact                    = (EditText) findViewById(R.id.fact);
        problem                 = (EditText) findViewById(R.id.problem);
        btnSubmit               = (FancyButton) findViewById(R.id.btnSubmit);

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
            fact.setText("" + getIntent().getStringExtra("fact"));
            problem.setText("" + getIntent().getStringExtra("problem"));
        }
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
                } else if (fact.getText().toString().isEmpty()) {
                    fact.setError("Please enter Fact !");
                } else if (problem.getText().toString().isEmpty()) {
                    problem.setError("Please enter Problem !");
                } else {
                    Map<String, String> param = new HashMap<>();
                    if (getIntent().getStringExtra("type").equals("update"))
                        param.put("id", getIntent().getStringExtra("monitoringId"));
                    param.put("patientId", id.getText().toString());
                    param.put("year", year.getText().toString());
                    param.put("month", month.getText().toString());
                    param.put("week", week.getText().toString());
                    param.put("day", day.getText().toString());
                    param.put("fact", fact.getText().toString());
                    param.put("problem", problem.getText().toString());
                    param.put("userInput", helper.getSession("name"));
                    if (getIntent().getStringExtra("type").equals("add")) {
                        clientApiService.insertPatientMonitoring(insertMonitoring, param, new ApiService.hashMapListener() {
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
                        clientApiService.updatePatientMonitoring(updateMonitoring, param, new ApiService.hashMapListener() {
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