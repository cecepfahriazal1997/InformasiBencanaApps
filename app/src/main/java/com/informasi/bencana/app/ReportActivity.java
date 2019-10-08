package com.informasi.bencana.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.ReportAdapter;
import com.informasi.bencana.model.ReportModel;
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportActivity extends MasterActivity {
    private ListView listView;
    private ReportAdapter adapter;
    private List<ReportModel> listData = new ArrayList<>();
    private List<ReportModel> searchData = new ArrayList<>();
    private Toolbar toolbar;
    private EditText keyword;
    private boolean onPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);
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
        clientApiService.getData(listReport, "object", true,
                new ApiService.hashMapListener() {
                    @Override
                    public String getHashMap(Map<String, String> hashMap) {
                        try {
                            if (hashMap.get("success").equals("1")) {
                                helper.showProgressDialog(pDialog, true);
                                JSONObject result   = new JSONObject(hashMap.get("result"));
                                JSONArray list      = result.getJSONArray("data");
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject detail       = list.getJSONObject(i);
                                    ReportModel model = new ReportModel();
                                    model.setId(detail.getString("id"));
                                    model.setGender(detail.getString("gender"));
                                    model.setAge(detail.getString("age"));
                                    model.setName(detail.getString("name"));
                                    model.setDoctor(detail.getString("doctor"));
                                    model.setNurse(detail.getString("nurse"));
                                    model.setListProgress(detail.getString("progress"));

                                    listData.add(model);
                                }
                                search(null);
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
            adapter         = new ReportAdapter(ReportActivity.this, searchData, helper);
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
