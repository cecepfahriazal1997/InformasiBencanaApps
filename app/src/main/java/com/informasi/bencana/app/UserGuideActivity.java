package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.UserGuideAdapter;
import com.informasi.bencana.model.UserGuideModel;
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserGuideActivity extends MasterActivity {
    private ListView listView;
    private UserGuideAdapter adapter;
    private List<UserGuideModel> listData = new ArrayList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Download User Guide");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
    }

    private void initial() {
        clientApiService.getData(urlUserGuide, "object", false,
                new ApiService.hashMapListener() {
                    @Override
                    public String getHashMap(Map<String, String> hashMap) {
                        try {
                            if (hashMap.get("success").equals("1")) {
                                JSONObject result   = new JSONObject(hashMap.get("result"));
                                JSONArray list      = result.getJSONArray("data");
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject detail       = list.getJSONObject(i);
                                    UserGuideModel model    = new UserGuideModel();

                                    model.setTitle(detail.getString("title"));
                                    model.setDescription(detail.getString("description"));
                                    model.setType(detail.getString("type"));
                                    model.setUrl(urlGoogleDoc + detail.getString("file"));

                                    listData.add(model);
                                }

                                adapter         = new UserGuideAdapter(UserGuideActivity.this, listData);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
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
