package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.MonitoringAdapter;
import com.informasi.bencana.model.PatientMonitoringModel;
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class MonitoringActivity extends MasterActivity {
    private ListView listView;
    private MonitoringAdapter adapter;
    private List<PatientMonitoringModel> listData = new ArrayList<>();
    private Toolbar toolbar;
    private boolean onPaused = false;
    private String patientId;
    private FancyButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);
        btnAdd              = findViewById(R.id.btnAdd);

        initial();
    }
    
    private void initial() {
        try {
            helper.setupProgressDialog(pDialog, "Loading data ...");
            patientId = getIntent().getStringExtra("id");
            Map<String, String> paramPatient = new HashMap<>();
            paramPatient.put("id", patientId);
            paramPatient.put("name", getIntent().getStringExtra("name"));
            paramPatient.put("gender", getIntent().getStringExtra("gender"));
            paramPatient.put("age", getIntent().getStringExtra("age"));
            paramPatient.put("nurse", getIntent().getStringExtra("nurse"));

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Monitoring Detail " + patientId);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> param = new HashMap<>();
                    param.put("title", "Add Monitoring Patient");
                    param.put("type", "add");
                    param.put("id", patientId);
                    param.put("name", getIntent().getStringExtra("name"));
                    param.put("gender", getIntent().getStringExtra("gender"));
                    param.put("age", getIntent().getStringExtra("age"));
                    param.put("nurse", getIntent().getStringExtra("nurse"));
                    helper.startIntent(FormMonitoringActivity.class, false, false, param);
                }
            });

            listData.clear();
            clientApiService.getData(listMonitoringDetail +
                            URLEncoder.encode(patientId, "UTF-8"), "object", true,
                    new ApiService.hashMapListener() {
                        @Override
                        public String getHashMap(Map<String, String> hashMap) {
                            try {
                                helper.showProgressDialog(pDialog, false);
                                if (hashMap.get("success").equals("1")) {
                                    JSONObject result   = new JSONObject(hashMap.get("result"));
                                    if (result.getString("status").equals("1")) {
                                        JSONArray list = result.getJSONArray("data");
                                        for (int i = 0; i < list.length(); i++) {
                                            JSONObject detail = list.getJSONObject(i);
                                            PatientMonitoringModel model = new PatientMonitoringModel();
                                            model.setMonitoringId(detail.getString("id"));
                                            model.setYear(detail.getString("Year"));
                                            model.setMonth(detail.getString("Month"));
                                            model.setWeek(detail.getString("Week"));
                                            model.setDay(detail.getString("Tgl"));
                                            model.setFact(detail.getString("Fact"));
                                            model.setProblem(detail.getString("Problem"));
                                            listData.add(model);
                                        }
                                        adapter         = new MonitoringAdapter(MonitoringActivity.this, listData, helper,
                                                clientApiService, deleteMonitoring, paramPatient);
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        helper.setListViewHeightBasedOnChildren(listView);
                                    } else {
                                        helper.popupDialog("Oops", result.getString("message"), false);
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
