package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.informasi.bencana.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReportDetailActivity extends MasterActivity {
    private Toolbar toolbar;
    private TextView id, name, gender, age;
    private LinearLayout listProgress, listStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        toolbar             = (Toolbar) findViewById(R.id.toolbar);
        id                  = (TextView) findViewById(R.id.id);
        name                = (TextView) findViewById(R.id.name);
        gender              = (TextView) findViewById(R.id.gender);
        age                 = (TextView) findViewById(R.id.age);
        listProgress        = (LinearLayout) findViewById(R.id.listProgress);
        listStatus          = (LinearLayout) findViewById(R.id.listStatus);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Report Patient");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initial();
    }

    private void initial() {
        try {
            id.setText("" + getIntent().getStringExtra("id"));
            name.setText("" + getIntent().getStringExtra("name"));
            gender.setText("" + (getIntent().getStringExtra("gender").equals("0") ? "Laki-laki" : "Perempuan"));
            age.setText("" + getIntent().getStringExtra("age") + " Tahun");

            loadHistory(0, getIntent().getStringExtra("listProgress"));
            loadHistory(1, getIntent().getStringExtra("listProgress"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHistory(int type, String list) {
        try {
            JSONArray data = new JSONArray(list);
            if (type == 0)
                listProgress.removeAllViews();
            else
                listStatus.removeAllViews();

            for (int i = 0; i < data.length(); i++) {
                JSONObject detail   = data.getJSONObject(i);
                View view           = helper.inflateView(R.layout.list_row_history_report);
                TextView title      = (TextView) view.findViewById(R.id.title);
                TextView date       = (TextView) view.findViewById(R.id.date);
                TextView status     = (TextView) view.findViewById(R.id.status);

                title.setText("Tahap " + (i + 1));
                date.setText(detail.getString("date"));

                if (type == 0) {
                    if (detail.getString("progress").equalsIgnoreCase("I")) {
                        status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_primary));
                        status.setText("Meningkat");
                    } else if (detail.getString("progress").equalsIgnoreCase("S")) {
                        status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_yelow));
                        status.setText("Stabil");
                    } else {
                        status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_red));
                        status.setText("Menurun");
                    }
                    listProgress.addView(view, i);
                } else {
                    if (detail.getString("status").equalsIgnoreCase("HE")) {
                        status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_primary));
                        status.setText("Sehat");
                    } else if (detail.getString("status").equalsIgnoreCase("DA")) {
                        status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_red));
                        status.setText("Meninggal");
                    } else {
                        status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_yelow));
                        status.setText("Cacat");
                    }
                    listStatus.addView(view, i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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