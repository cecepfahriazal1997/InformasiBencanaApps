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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class FormHistoryActivity extends MasterActivity {
    private Toolbar toolbar;
    private CardView cardTypeRecord, cardSymptom, cardSpesific, cardRelationship,
            cardRadiologi, cardDiagnostic, cardTherapic, cardRehability;
    private TextView id, name, gender, age, typeRecord, sympton, specific, relationship, radiologi,
            diagnostic, therapy, rehability;
    private String typeRecordCode, symptonCode, specificCode, relationshipCode, radiologiCode,
            diagnosticCode, therapyCode, rehabilityCode;
    private EditText condition, specificCondition, lab, remark;
    private FancyButton btnSubmit;
    private Map<String, String> param = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_history);

        toolbar         = (Toolbar) findViewById(R.id.toolbar);
        cardTypeRecord  = (CardView) findViewById(R.id.cardTypeRecord);
        cardSymptom     = (CardView) findViewById(R.id.cardSymptom);
        cardSpesific    = (CardView) findViewById(R.id.cardSpesific);
        cardRelationship= (CardView) findViewById(R.id.cardRelationship);
        cardRadiologi   = (CardView) findViewById(R.id.cardRadiologi);
        cardDiagnostic  = (CardView) findViewById(R.id.cardDiagnostic);
        cardTherapic    = (CardView) findViewById(R.id.cardTherapic);
        cardRehability  = (CardView) findViewById(R.id.cardRehability);
        id              = (TextView) findViewById(R.id.id);
        name            = (TextView) findViewById(R.id.name);
        gender          = (TextView) findViewById(R.id.gender);
        age             = (TextView) findViewById(R.id.age);
        typeRecord      = (TextView) findViewById(R.id.typeRecord);
        sympton         = (TextView) findViewById(R.id.symptom);
        specific        = (TextView) findViewById(R.id.spesific);
        relationship    = (TextView) findViewById(R.id.relationship);
        diagnostic      = (TextView) findViewById(R.id.diagnostic);
        radiologi       = (TextView) findViewById(R.id.radiologi);
        therapy         = (TextView) findViewById(R.id.therapic);
        rehability      = (TextView) findViewById(R.id.rehability);
        condition       = (EditText) findViewById(R.id.condition);
        specificCondition   = (EditText) findViewById(R.id.spesificCondition);
        lab                 = (EditText) findViewById(R.id.lab);
        remark              = (EditText) findViewById(R.id.remark);
        btnSubmit           = (FancyButton) findViewById(R.id.btnSubmit);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("" + getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
    }

    private void initial() {
        helper.setupProgressDialog(pDialog, "Loading data ...");
        id.setText("" + getIntent().getStringExtra("id"));
        name.setText("" + getIntent().getStringExtra("name"));
        gender.setText("" + (getIntent().getStringExtra("gender").equals("0") ?
                "Laki-laki" : "Perempuan"));
        age.setText("" + getIntent().getStringExtra("age") + " Tahun");
        clientApiService.getData(historyPatient + id.getText().toString(), "object", true, new ApiService.hashMapListener() {
            @Override
            public String getHashMap(Map<String, String> hashMap) {
                try {
                    if (hashMap.get("success").equals("1")) {
                        JSONObject result   = new JSONObject(hashMap.get("result"));
                        JSONObject data     = result.getJSONObject("data");
                        typeRecord.setText("" + data.getString("HistoryTypeName"));
                        sympton.setText("" + data.getString("SymptomName"));
                        specific.setText("" + data.getString("SpecSymptomName"));
                        relationship.setText("" + data.getString("FamilyRelated"));
                        condition.setText("" + data.getString("GenCon"));
                        specificCondition.setText("" + data.getString("SpecCon"));
                        lab.setText("" + data.getString("Laboratory"));
                        radiologi.setText("" + data.getString("RadiologyName"));
                        diagnostic.setText("" + data.getString("DiagnosisName"));
                        therapy.setText("" + data.getString("TherapyName"));
                        rehability.setText("" + data.getString("RehabilitationName"));
                        remark.setText("" + data.getString("Remark"));

                        typeRecordCode  = data.getString("HistoryType");
                        symptonCode     = data.getString("Symptom");
                        specificCode    = data.getString("SpecSymptom");
                        relationshipCode= data.getString("FamilyRelated");
                        radiologiCode   = data.getString("Radiology");
                        diagnosticCode  = data.getString("Diagnosis");
                        therapyCode     = data.getString("Therapy");
                        rehabilityCode  = data.getString("Rehabilitation");

                        typeRecord.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        sympton.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        specific.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        relationship.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        radiologi.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        diagnostic.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        therapy.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        rehability.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                    } else {
                        typeRecord.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        sympton.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        specific.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        relationship.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        radiologi.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        diagnostic.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        therapy.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                        rehability.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        cardTypeRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "typeRecord");
                helper.startIntentForResult(DataMasterActivity.class, param, 0);
            }
        });

        cardSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "symptom");
                helper.startIntentForResult(DataMasterActivity.class, param, 1);
            }
        });

        cardSpesific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "specific");
                helper.startIntentForResult(DataMasterActivity.class, param, 2);
            }
        });

        cardRelationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "relationship");
                helper.startIntentForResult(DataMasterActivity.class, param, 3);
            }
        });

        cardRadiologi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "radiologi");
                helper.startIntentForResult(DataMasterActivity.class, param, 4);
            }
        });

        cardDiagnostic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "diagnostic");
                helper.startIntentForResult(DataMasterActivity.class, param, 5);
            }
        });

        cardTherapic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "therapy");
                helper.startIntentForResult(DataMasterActivity.class, param, 6);
            }
        });

        cardRehability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param.clear();
                param.put("type", "rehability");
                helper.startIntentForResult(DataMasterActivity.class, param, 7);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeRecord.getText().toString().isEmpty()) {
                    typeRecord.setError("Please choose Type History !");
                } else if (sympton.getText().toString().isEmpty()) {
                    sympton.setError("Please choose Symptom !");
                } else if (specific.getText().toString().isEmpty()) {
                    specific.setError("Please enter Symptom Specific!");
                } else if (relationship.getText().toString().isEmpty()) {
                    relationship.setError("Please enter Family Related !");
                } else if (condition.getText().toString().isEmpty()) {
                    condition.setError("Please enter Condition !");
                } else if (specificCondition.getText().toString().isEmpty()) {
                    specificCondition.setError("Please enter Specific Condition !");
                } else if (lab.getText().toString().isEmpty()) {
                    lab.setError("Please enter Laboratory !");
                } else if (radiologi.getText().toString().isEmpty()) {
                    radiologi.setError("Please choose thread Radiology !");
                } else if (diagnostic.getText().toString().isEmpty()) {
                    diagnostic.setError("Please choose Diagnostic !");
                } else if (therapy.getText().toString().isEmpty()) {
                    therapy.setError("Please choose Therapic !");
                } else if (rehability.getText().toString().isEmpty()) {
                    rehability.setError("Please choose Rehability !");
                } else if (remark.getText().toString().isEmpty()) {
                    remark.setError("Please enter Remark !");
                } else {
                    Map<String, String> param = new HashMap<>();
                    param.put("patientId", id.getText().toString());
                    param.put("typeRecord", typeRecordCode);
                    param.put("symptom", symptonCode);
                    param.put("specific", specificCode);
                    param.put("relationship", relationshipCode);
                    param.put("condition", condition.getText().toString());
                    param.put("specificCondition", specificCondition.getText().toString());
                    param.put("lab", lab.getText().toString());
                    param.put("radiologi", radiologiCode);
                    param.put("diagnostic", diagnosticCode);
                    param.put("therapy", therapyCode);
                    param.put("rehability", rehabilityCode);
                    param.put("remark", remark.getText().toString());
                    param.put("userInput", helper.getSession("name"));
                    clientApiService.savePatientHistory(saveHistoryPatient, param, new ApiService.hashMapListener() {
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
        if (resultCode == RESULT_OK) {
            String id       = data.getStringExtra("id");
            String title    = data.getStringExtra("title");
            switch (requestCode) {
                case 0:
                    typeRecordCode = id;
                    typeRecord.setText(title);
                    typeRecord.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                    break;
                case 1:
                    symptonCode = id;
                    sympton.setText(title);
                    sympton.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                    break;
                case 2:
                    specificCode = id;
                    specific.setText(title);
                    specific.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                    break;
                case 3:
                    relationshipCode = title;
                    relationship.setText(title);
                    relationship.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                    break;
                case 4:
                    radiologiCode = id;
                    radiologi.setText(title);
                    radiologi.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                    break;
                case 5:
                    diagnosticCode = id;
                    diagnostic.setText(title);
                    diagnostic.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                    break;
                case 6:
                    therapyCode = id;
                    therapy.setText(title);
                    therapy.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
                    break;
                case 7:
                    rehabilityCode = id;
                    rehability.setText(title);
                    rehability.setTextColor(ContextCompat.getColor(FormHistoryActivity.this, R.color.colorBlack));
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