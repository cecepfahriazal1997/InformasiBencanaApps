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
import com.informasi.bencana.adapter.NeedAssesmentAdapter;
import com.informasi.bencana.model.ModelNeedAssesment;
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class NeedAssesmentActivity extends MasterActivity {
    private ListView listView;
    private NeedAssesmentAdapter adapter;
    private List<ModelNeedAssesment> listData = new ArrayList<>();
    private List<ModelNeedAssesment> searchData = new ArrayList<>();
    private Toolbar toolbar;
    private FancyButton btnAdd;
    private EditText keyword;
    private boolean onPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_assesment);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);
        btnAdd              = findViewById(R.id.btnAdd);
        keyword             = findViewById(R.id.keyword);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Need Assesment");
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
        clientApiService.getData(listNeedAssesmen, "object", true,
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
                                        ModelNeedAssesment model = new ModelNeedAssesment();
                                        model.setId(detail.getString("id"));
                                        model.setLocation(detail.getString("location"));
                                        model.setDate(detail.getString("waktu"));
                                        model.setCases(detail.getString("cases"));
                                        model.setDiseases(detail.getString("diseases"));
                                        model.setService(detail.getString("service"));
                                        model.setPlace(detail.getString("place"));
                                        model.setMedicalE(detail.getString("medicalE"));
                                        model.setSupportingE(detail.getString("supportingE"));
                                        model.setDrug(detail.getString("drug"));
                                        model.setMedicalS(detail.getString("medicalS"));
                                        model.setNmedicalS(detail.getString("nmedicalS"));
                                        model.setGeneral(detail.getString("general"));
                                        model.setSpecialistD(detail.getString("specialistD"));
                                        model.setNurse(detail.getString("nurse"));
                                        model.setNmedical(detail.getString("nmedical"));
                                        model.setAmbulance(detail.getString("ambulance"));
                                        model.setRelateDtr(detail.getString("relatedtr"));
                                        model.setCom(detail.getString("com"));
                                        model.setLocationLabel(detail.getString("nameLocation"));

                                        listData.add(model);
                                    }
                                    search(null);
                                }
                                helper.showProgressDialog(pDialog, false);
                                btnAdd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Map<String, String> param = new HashMap<>();
                                        param.put("type", "add");
                                        param.put("title", "Add Data");
                                        helper.startIntent(FormNeedAssesmentActivity.class, false, false, param);
                                    }
                                });
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
                    if (listData.get(i).getService().toLowerCase().contains(keyword.toLowerCase()) ||
                        listData.get(i).getPlace().toLowerCase().contains(keyword.toLowerCase())) {
                        searchData.add(listData.get(i));
                    }
                } else {
                    searchData.add(listData.get(i));
                }
            }
            adapter         = new NeedAssesmentAdapter(NeedAssesmentActivity.this, searchData, helper,
                                clientApiService, deleteNeedAssesmen);
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
