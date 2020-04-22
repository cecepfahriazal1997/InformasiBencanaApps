package com.informasi.bencana.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.ReportAdapter;
import com.informasi.bencana.model.ReportModel;
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Spinner year;
    private List<String> years = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);
        keyword             = findViewById(R.id.keyword);
        year                = findViewById(R.id.year);

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
                                JSONObject result   = new JSONObject(hashMap.get("result"));
                                if (result.getString("status").equals("1")) {
                                    JSONArray list      = result.getJSONArray("data");
                                    JSONArray listYear  = result.getJSONArray("years");

                                    initYear(listYear);

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
                                        model.setListYear(detail.getString("year"));

                                        listData.add(model);
                                    }
                                    search(null);
                                }
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
            String tempYear = years.get(year.getSelectedItemPosition());
            searchData.clear();
            for (int i = 0; i < listData.size(); i++) {
                if (tempYear != null && !tempYear.equals("Lihat Semua")) {
                    if (listData.get(i).getListYear().contains(tempYear)) {
                        if (keyword != null) {
                            if (listData.get(i).getName().toLowerCase().contains(keyword.toLowerCase()) ||
                                    listData.get(i).getDoctor().toLowerCase().contains(keyword.toLowerCase())) {
                                searchData.add(listData.get(i));
                            }
                        } else {
                            searchData.add(listData.get(i));
                        }
                    }
                } else {
                    if (keyword != null) {
                        if (listData.get(i).getName().toLowerCase().contains(keyword.toLowerCase()) ||
                                listData.get(i).getDoctor().toLowerCase().contains(keyword.toLowerCase())) {
                            searchData.add(listData.get(i));
                        }
                    } else {
                        searchData.add(listData.get(i));
                    }
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

    private void initYear(JSONArray data) {
        years.clear();
        years.add("Lihat Semua");

        for (int i = 0; i < data.length(); i++) {
            try {
                years.add(data.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, years);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(dataAdapter);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                search(keyword.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
