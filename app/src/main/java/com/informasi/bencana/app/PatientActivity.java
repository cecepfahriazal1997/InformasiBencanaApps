package com.informasi.bencana.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.PatientAdapter;
import com.informasi.bencana.model.PatientModel;
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class PatientActivity extends MasterActivity {
    private ListView listView;
    private PatientAdapter adapter;
    private List<PatientModel> listData = new ArrayList<>();
    private List<PatientModel> searchData = new ArrayList<>();
    private Toolbar toolbar;
    private FancyButton btnAdd;
    private EditText keyword;
    private boolean onPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);
        btnAdd              = findViewById(R.id.btnAdd);
        keyword             = findViewById(R.id.keyword);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
    }
    
    private void initial() {
        helper.setupProgressDialog(pDialog, "Loading data ...");
        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(keyword.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listData.clear();
        clientApiService.getData(listPatient, "object", true,
                new ApiService.hashMapListener() {
                    @Override
                    public String getHashMap(Map<String, String> hashMap) {
                        try {
                            if (hashMap.get("success").equals("1")) {
                                helper.showProgressDialog(pDialog, true);
                                JSONObject result   = new JSONObject(hashMap.get("result"));
                                if (result.getString("status").equals("1")) {
                                    JSONArray list      = result.getJSONArray("data");
                                    for (int i = 0; i < list.length(); i++) {
                                        JSONObject detail       = list.getJSONObject(i);
                                        PatientModel model = new PatientModel();
                                        model.setId(detail.getString("PatientId"));
                                        model.setName(detail.getString("PatientNm"));
                                        model.setGender(detail.getString("Sex"));
                                        model.setAge(detail.getString("Age"));
                                        model.setLocation(detail.getString("Location"));
                                        model.setLocationLabel(detail.getString("nameCountries"));
                                        model.setDate(detail.getString("Time"));
                                        model.setWeaknessCondition(detail.getString("WaknessCon"));
                                        model.setThreadCondition(detail.getString("ThreadCon"));
                                        model.setDoctor(detail.getString("DoctorNm"));
                                        model.setNurse(detail.getString("NurseNm"));
                                        model.setSupport(detail.getString("SupportNm"));
                                        model.setRemark(detail.getString("Remark"));
                                        model.setStepOne("0");
                                        model.setStepTwo("0");
                                        model.setStepThree("0");

                                        listData.add(model);
                                    }
                                    search(null);
                                }
                                btnAdd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Map<String, String> param = new HashMap<>();
                                        param.put("type", "add");
                                        param.put("title", "Add Patient");
                                        param.put("number", String.valueOf(listData.size() + 1));
                                        helper.startIntent(FormPatientActivity.class, false, false, param);
                                    }
                                });
                                helper.showProgressDialog(pDialog, false);
                            } else {
                                helper.popupDialog("Oops", hashMap.get("message"), false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }

    private void search(String keyword) {
        try {
            searchData.clear();
            for (int i = 0; i < listData.size(); i++) {
                if (keyword != null) {
                    if (listData.get(i).getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        listData.get(i).getDoctor().toLowerCase().contains(keyword.toLowerCase())) {
                        searchData.add(listData.get(i));
                    }
                } else {
                    searchData.add(listData.get(i));
                }
            }
            adapter         = new PatientAdapter(PatientActivity.this, searchData, helper,
                                clientApiService, deletePatient);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            helper.setListViewHeightBasedOnChildren(listView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        onPaused    = true;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (onPaused)
            initial();
    }
}
