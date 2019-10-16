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

public class FormNeedAssesmentActivity extends MasterActivity {
    private Toolbar toolbar;
    private CardView cardLocation;
    private TextView location;
    private EditText date, cases, diseases, serviceNeed, places, medicalE, supportE, drug, medicalS,
            nonMedicalS, general, specialistDoctor, nurse, nonMedical, ambulance, transport, communication;
    private FancyButton btnSubmit;
    private Map<String, String> param = new HashMap<>();
    private String locationId, id = "";
    private IconicsImageView datePicker;
    private String type;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_need);

        toolbar             = (Toolbar) findViewById(R.id.toolbar);
        cardLocation        = (CardView) findViewById(R.id.cardLocation);
        location            = (TextView) findViewById(R.id.location);
        date                = (EditText) findViewById(R.id.date);
        cases               = (EditText) findViewById(R.id.cases);
        diseases            = (EditText) findViewById(R.id.diseases);
        serviceNeed         = (EditText) findViewById(R.id.serviceNeed);
        places              = (EditText) findViewById(R.id.places);
        medicalE            = (EditText) findViewById(R.id.medical);
        supportE            = (EditText) findViewById(R.id.supporting);
        drug                = (EditText) findViewById(R.id.drug);
        medicalS            = (EditText) findViewById(R.id.medicalSupply);
        nonMedicalS         = (EditText) findViewById(R.id.nonMedicalSupply);
        general             = (EditText) findViewById(R.id.general);
        specialistDoctor    = (EditText) findViewById(R.id.specialistDoctor);
        nurse               = (EditText) findViewById(R.id.nurse);
        nonMedical          = (EditText) findViewById(R.id.nonMedical);
        ambulance           = (EditText) findViewById(R.id.ambulance);
        transport           = (EditText) findViewById(R.id.related);
        communication       = (EditText) findViewById(R.id.communication);
        btnSubmit           = (FancyButton) findViewById(R.id.btnSubmit);
        datePicker          = (IconicsImageView) findViewById(R.id.datePicker);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("" + getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
    }

    private void initial() {
        type    = getIntent().getStringExtra("type");
        helper.setupProgressDialog(pDialog, "Saving data ...");

        if (type.equals("add")) {
            location.setTextColor(ContextCompat.getColor(FormNeedAssesmentActivity.this, R.color.colorGrayDark));
        } else {
            location.setText("" + getIntent().getStringExtra("location"));
            date.setText("" + getIntent().getStringExtra("date"));
            cases.setText("" + getIntent().getStringExtra("cases"));
            diseases.setText("" + getIntent().getStringExtra("diseases"));
            serviceNeed.setText("" + getIntent().getStringExtra("service"));
            places.setText("" + getIntent().getStringExtra("place"));
            medicalE.setText("" + getIntent().getStringExtra("medicalE"));
            supportE.setText("" + getIntent().getStringExtra("supportingE"));
            drug.setText("" + getIntent().getStringExtra("drug"));
            medicalS.setText("" + getIntent().getStringExtra("medicalS"));
            nonMedicalS.setText("" + getIntent().getStringExtra("nmedicalS"));
            general.setText("" + getIntent().getStringExtra("general"));
            specialistDoctor.setText("" + getIntent().getStringExtra("specialistD"));
            nurse.setText("" + getIntent().getStringExtra("nurse"));
            nonMedical.setText("" + getIntent().getStringExtra("nmedical"));
            ambulance.setText("" + getIntent().getStringExtra("ambulance"));
            transport.setText("" + getIntent().getStringExtra("relateDtr"));
            communication.setText("" + getIntent().getStringExtra("com"));

            locationId          = getIntent().getStringExtra("location");
            id                  = getIntent().getStringExtra("id");
            location.setTextColor(ContextCompat.getColor(FormNeedAssesmentActivity.this, R.color.colorBlack));
            location.setText(getIntent().getStringExtra("nameLocation"));
        }

        cardLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "location");
                helper.startIntentForResult(DataMasterActivity.class, param, 0);
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
                } else if (cases.getText().toString().isEmpty()) {
                    cases.setError("Please enter cases !");
                } else if (diseases.getText().toString().isEmpty()) {
                    diseases.setError("Please choose diseases !");
                } else if (serviceNeed.getText().toString().isEmpty()) {
                    serviceNeed.setError("Please enter service !");
                } else if (places.getText().toString().isEmpty()) {
                    places.setError("Please enter places !");
                } else if (medicalE.getText().toString().isEmpty()) {
                    medicalE.setError("Please enter medical !");
                } else if (supportE.getText().toString().isEmpty()) {
                    supportE.setError("Please enter support !");
                } else if (drug.getText().toString().isEmpty()) {
                    drug.setError("Please enter drug !");
                } else if (medicalS.getText().toString().isEmpty()) {
                    medicalS.setError("Please enter medical supply!");
                } else if (nonMedicalS.getText().toString().isEmpty()) {
                    nonMedicalS.setError("Please enter non medical supply !");
                } else if (general.getText().toString().isEmpty()) {
                    general.setError("Please enter general !");
                } else if (specialistDoctor.getText().toString().isEmpty()) {
                    specialistDoctor.setError("Please enter doctor !");
                } else if (nurse.getText().toString().isEmpty()) {
                    nurse.setError("Please enter nurse !");
                } else if (nonMedical.getText().toString().isEmpty()) {
                    nonMedical.setError("Please enter non medical !");
                } else if (ambulance.getText().toString().isEmpty()) {
                    ambulance.setError("Please enter ambulance !");
                } else if (transport.getText().toString().isEmpty()) {
                    transport.setError("Please enter transportation !");
                } else if (communication.getText().toString().isEmpty()) {
                    communication.setError("Please enter communication !");
                } else {
                    Map<String, String> param = new HashMap<>();
                    param.put("location", locationId);
                    param.put("date", date.getText().toString());
                    param.put("id", id);
                    param.put("cases", cases.getText().toString());
                    param.put("diseases", diseases.getText().toString());
                    param.put("service", serviceNeed.getText().toString());
                    param.put("place", places.getText().toString());
                    param.put("medicalE", medicalE.getText().toString());
                    param.put("supportingE", supportE.getText().toString());
                    param.put("drug", drug.getText().toString());
                    param.put("medicalS", medicalS.getText().toString());
                    param.put("nonMedicalS", nonMedicalS.getText().toString());
                    param.put("general", general.getText().toString());
                    param.put("specialistD", specialistDoctor.getText().toString());
                    param.put("nurse", nurse.getText().toString());
                    param.put("nonMedical", nonMedical.getText().toString());
                    param.put("ambulance", ambulance.getText().toString());
                    param.put("relatedTransportation", transport.getText().toString());
                    param.put("communication", communication.getText().toString());
                    clientApiService.saveNeedAsses(saveNeedAssesmen, param, new ApiService.hashMapListener() {
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
                    location.setTextColor(ContextCompat.getColor(FormNeedAssesmentActivity.this, R.color.colorBlack));
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