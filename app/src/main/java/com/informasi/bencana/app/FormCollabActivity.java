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

public class FormCollabActivity extends MasterActivity {
    private Toolbar toolbar;
    private TextView id, name, gender, age;
    private EditText year, month, week, day, problem, recommend, collaborativeC, collaborativeD, feedback;
    private FancyButton btnSubmit;
    private Map<String, String> param = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_collab);

        toolbar                 = (Toolbar) findViewById(R.id.toolbar);
        id                      = (TextView) findViewById(R.id.id);
        name                    = (TextView) findViewById(R.id.name);
        gender                  = (TextView) findViewById(R.id.gender);
        age                     = (TextView) findViewById(R.id.age);
        year                    = (EditText) findViewById(R.id.year);
        month                   = (EditText) findViewById(R.id.month);
        week                    = (EditText) findViewById(R.id.week);
        day                     = (EditText) findViewById(R.id.day);
        problem                 = (EditText) findViewById(R.id.problem);
        recommend               = (EditText) findViewById(R.id.recommend);
        collaborativeC          = (EditText) findViewById(R.id.collaborativeC);
        collaborativeD          = (EditText) findViewById(R.id.collaborativeD);
        feedback                = (EditText) findViewById(R.id.feedback);
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
            problem.setText("" + getIntent().getStringExtra("problem"));
            recommend.setText("" + getIntent().getStringExtra("recommend"));
            collaborativeC.setText("" + getIntent().getStringExtra("collaborativeC"));
            collaborativeD.setText("" + getIntent().getStringExtra("collaborativeD"));
            feedback.setText("" + getIntent().getStringExtra("feedback"));
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
                } else if (problem.getText().toString().isEmpty()) {
                    problem.setError("Please enter Problem !");
                } else if (recommend.getText().toString().isEmpty()) {
                    recommend.setError("Please enter Recommend !");
                } else if (collaborativeC.getText().toString().isEmpty()) {
                    collaborativeC.setError("Please Collaborative C !");
                } else if (collaborativeD.getText().toString().isEmpty()) {
                    collaborativeD.setError("Please enter Collaborative D !");
                } else if (feedback.getText().toString().isEmpty()) {
                    feedback.setError("Please enter Feedback !");
                } else {
                    Map<String, String> param = new HashMap<>();
                    if (getIntent().getStringExtra("type").equals("update"))
                        param.put("id", getIntent().getStringExtra("collabId"));
                        param.put("detailId", getIntent().getStringExtra("detailId"));
                    param.put("patientId", id.getText().toString());
                    param.put("year", year.getText().toString());
                    param.put("month", month.getText().toString());
                    param.put("week", week.getText().toString());
                    param.put("day", day.getText().toString());
                    param.put("problem", problem.getText().toString());
                    param.put("recommend", recommend.getText().toString());
                    param.put("collaborativeC", collaborativeC.getText().toString());
                    param.put("collaborativeD", collaborativeD.getText().toString());
                    param.put("feedback", feedback.getText().toString());
                    param.put("userInput", helper.getSession("name"));
                    if (getIntent().getStringExtra("type").equals("add")) {
                        clientApiService.insertPatientCollab(insertCollaboratationPatient, param, new ApiService.hashMapListener() {
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
                        clientApiService.updatePatientCollab(updateCollaborationPatient, param, new ApiService.hashMapListener() {
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