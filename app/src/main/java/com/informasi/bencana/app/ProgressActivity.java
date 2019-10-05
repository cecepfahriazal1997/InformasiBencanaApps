package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.ProgressAdapter;
import com.informasi.bencana.model.PatientProgressModel;
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgressActivity extends MasterActivity {
    private ListView listView;
    private ProgressAdapter adapter;
    private List<PatientProgressModel> listData = new ArrayList<>();
    private Toolbar toolbar;
    private boolean onPaused = false;
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);

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
            getSupportActionBar().setTitle("Progress Detail " + patientId);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            listData.clear();
            clientApiService.getData(listProgressPatientDetail + URLEncoder.encode(patientId, "UTF-8"), "object", true,
                    new ApiService.hashMapListener() {
                        @Override
                        public String getHashMap(Map<String, String> hashMap) {
                            try {
                                helper.showProgressDialog(pDialog, false);
                                if (hashMap.get("success").equals("1")) {
                                    JSONObject result   = new JSONObject(hashMap.get("result"));
                                    JSONArray list      = result.getJSONArray("data");
                                    for (int i = 0; i < list.length(); i++) {
                                        JSONObject detail       = list.getJSONObject(i);
                                        PatientProgressModel model = new PatientProgressModel();
                                        model.setProgressId(detail.getString("id_prog"));
                                        model.setYear(detail.getString("Year"));
                                        model.setMonth(detail.getString("Month"));
                                        model.setWeek(detail.getString("Week"));
                                        model.setDay(detail.getString("Tgl"));
                                        model.setComplication(detail.getString("Complication"));
                                        model.setComplicationLabel(detail.getString("ComplicationLabel"));
                                        model.setComplicationDtl(detail.getString("ComplicationDtl"));
                                        model.setComplicationDtlLabel(detail.getString("ComplicationDtlLabel"));
                                        model.setProgress(detail.getString("Progress"));
                                        model.setStatus(detail.getString("Status"));
                                        model.setRemark(detail.getString("Remark"));
                                        listData.add(model);
                                    }

                                    adapter         = new ProgressAdapter(ProgressActivity.this, listData, helper,
                                            clientApiService, deleteProgressPatient, paramPatient);
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
