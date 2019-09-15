package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.other.ApiService;

import org.json.JSONObject;

import java.util.Map;

public class ProfileActivity extends MasterActivity {
    private Toolbar toolbar;
    private TextView name, email, username, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar             = findViewById(R.id.toolbar);
        name                = findViewById(R.id.name);
        email               = findViewById(R.id.email);
        username            = findViewById(R.id.username);
        phone               = findViewById(R.id.phone);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();
    }

    private void loadData() {
        helper.setupProgressDialog(pDialog, "Loading data ...");
        clientApiService.getData(urlProfile + helper.getSession("id"), "object", false,
                new ApiService.hashMapListener() {
                    @Override
                    public String getHashMap(Map<String, String> hashMap) {
                        try {
                            if (hashMap.get("success").equals("1")) {
                                JSONObject result   = new JSONObject(hashMap.get("result"));
                                JSONObject detail   = result.getJSONObject("result");

                                name.setText(detail.getString("nama"));
                                email.setText(detail.getString("email"));
                                phone.setText(detail.getString("telepon"));
                                username.setText(detail.getString("username"));
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