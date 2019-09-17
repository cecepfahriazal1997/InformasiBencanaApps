package com.informasi.bencana.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.informasi.bencana.R;
import com.informasi.bencana.other.ApiService;
import com.mikepenz.iconics.view.IconicsImageView;

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
    private String locationId, genderCode = "0";
    private IconicsImageView datePicker;
    private String type;
    
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
        datePicker          = (IconicsImageView) findViewById(R.id.datePicker);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("" + getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
    }

    private void initial() {
        type    = getIntent().getStringExtra("type");

        if (type.equals("add")) {
            id.setText("" + getIntent().getStringExtra("number"));
            location.setTextColor(ContextCompat.getColor(FormPatientActivity.this, R.color.colorGrayDark));
            gender.setTextColor(ContextCompat.getColor(FormPatientActivity.this, R.color.colorGrayDark));
        } else {
            id.setText("" + getIntent().getStringExtra("id"));
            name.setText("" + getIntent().getStringExtra("name"));
            gender.setText("" + (getIntent().getStringExtra("gender").equals("0") ?
                                    "Laki-laki" : "Perempuan"));
            age.setText("" + getIntent().getStringExtra("age"));
            location.setText("" + getIntent().getStringExtra("location"));
            date.setText("" + getIntent().getStringExtra("date"));
            weaknessCondition.setText("" + getIntent().getStringExtra("weaknessCondition"));
            threadCondition.setText("" + getIntent().getStringExtra("threadCondition"));
            nameDoctor.setText("" + getIntent().getStringExtra("doctorName"));
            nameNurse.setText("" + getIntent().getStringExtra("nurseName"));
            nameSupport.setText("" + getIntent().getStringExtra("supportName"));
            remark.setText("" + getIntent().getStringExtra("remark"));

            locationId          = getIntent().getStringExtra("location");
            genderCode          = getIntent().getStringExtra("gender");
            location.setTextColor(ContextCompat.getColor(FormPatientActivity.this, R.color.colorBlack));
            gender.setTextColor(ContextCompat.getColor(FormPatientActivity.this, R.color.colorBlack));
        }

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
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.showDatePicker(date);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location.getText().toString().isEmpty()) {
                    location.setError("Please choose location !");
                } else if (date.getText().toString().isEmpty()) {
                    date.setError("Please choose date !");
                } else if (id.getText().toString().isEmpty()) {
                    id.setError("Please enter patient id !");
                } else if (name.getText().toString().isEmpty()) {
                    name.setError("Please enter name !");
                } else if (gender.getText().toString().isEmpty()) {
                    gender.setError("Please choose gender !");
                } else if (age.getText().toString().isEmpty()) {
                    age.setError("Please enter age !");
                } else if (weaknessCondition.getText().toString().isEmpty()) {
                    weaknessCondition.setError("Please enter weakness condition !");
                } else if (threadCondition.getText().toString().isEmpty()) {
                    threadCondition.setError("Please enter thread condition !");
                } else if (remark.getText().toString().isEmpty()) {
                    remark.setError("Please enter remark !");
                } else if (nameDoctor.getText().toString().isEmpty()) {
                    nameDoctor.setError("Please enter doctor name !");
                } else if (nameNurse.getText().toString().isEmpty()) {
                    nameNurse.setError("Please enter nurse name !");
                } else if (nameSupport.getText().toString().isEmpty()) {
                    nameSupport.setError("Please enter support name !");
                } else {
                    if (type.equals("add")) {
                        Map<String, String> param = new HashMap<>();
                        param.put("location", locationId);
                        param.put("date", date.getText().toString());
                        param.put("number", id.getText().toString());
                        param.put("name", name.getText().toString());
                        param.put("gender", genderCode);
                        param.put("age", age.getText().toString());
                        param.put("weaknessCondition", weaknessCondition.getText().toString());
                        param.put("threadCondition", threadCondition.getText().toString());
                        param.put("doctorName", nameDoctor.getText().toString());
                        param.put("nurseName", nameNurse.getText().toString());
                        param.put("supportName", nameSupport.getText().toString());
                        param.put("remark", remark.getText().toString());
                        param.put("userInput", helper.getSession("name"));
                        clientApiService.insertPatient(insertPatient, param, new ApiService.hashMapListener() {
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
                        Map<String, String> param = new HashMap<>();
                        param.put("patientId", id.getText().toString());
                        param.put("location", locationId);
                        param.put("date", date.getText().toString());
                        param.put("name", name.getText().toString());
                        param.put("gender", genderCode);
                        param.put("age", age.getText().toString());
                        param.put("weaknessCondition", weaknessCondition.getText().toString());
                        param.put("threadCondition", threadCondition.getText().toString());
                        param.put("doctorName", nameDoctor.getText().toString());
                        param.put("nurseName", nameNurse.getText().toString());
                        param.put("supportName", nameSupport.getText().toString());
                        param.put("remark", remark.getText().toString());
                        param.put("userInput", helper.getSession("name"));
                        clientApiService.updatePatient(updatePatient, param, new ApiService.hashMapListener() {
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
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    String id           = data.getStringExtra("id");
                    String title        = data.getStringExtra("title");
                    locationId          = id;

                    location.setText(title);
                    location.setTextColor(ContextCompat.getColor(FormPatientActivity.this, R.color.colorBlack));
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    String id           = data.getStringExtra("id");
                    String title        = data.getStringExtra("title");
                    genderCode          = id;

                    gender.setText(title);
                    gender.setTextColor(ContextCompat.getColor(FormPatientActivity.this, R.color.colorBlack));
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