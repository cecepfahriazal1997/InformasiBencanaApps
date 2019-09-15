package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private Toolbar toolbar;
    private FancyButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);
        btnAdd              = findViewById(R.id.btnAdd);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Download User Guide");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
    }
    
    private void initial() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> param = new HashMap<>();
                param.put("title", "Add Patient");
                helper.startIntent(FormPatientActivity.class, false, false, param);
            }
        });
        clientApiService.getData(listPatient, "object", false,
                new ApiService.hashMapListener() {
                    @Override
                    public String getHashMap(Map<String, String> hashMap) {
                        try {
                            if (hashMap.get("success").equals("1")) {
                                JSONObject result   = new JSONObject(hashMap.get("result"));
                                JSONArray list      = result.getJSONArray("data");
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject detail       = list.getJSONObject(i);
                                    PatientModel model = new PatientModel();
                                    model.setId(detail.getString("PatientId"));
                                    model.setName(detail.getString("PatientNm"));
                                    model.setDoctor(detail.getString("DoctorNm"));
                                    model.setGender(detail.getString("Sex").equals("0") ? "Laki-laki" : "Perempuan");
                                    model.setAge(detail.getString("Age"));
                                    model.setStepOne("0");
                                    model.setStepTwo("0");
                                    model.setStepThree("0");

                                    listData.add(model);
                                }

                                adapter         = new PatientAdapter(PatientActivity.this, listData);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                helper.setListViewHeightBasedOnChildren(listView);
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
}
