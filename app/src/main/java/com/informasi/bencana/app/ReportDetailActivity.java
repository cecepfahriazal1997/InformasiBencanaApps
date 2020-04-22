package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.informasi.bencana.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReportDetailActivity extends MasterActivity {
    private Toolbar toolbar;
    private TextView id, name, gender, age;
    private LinearLayout listProgress, listStatus;
    private BarChart chartProgress, chartStatus;
    private Spinner yearProgress, statusProgress;
    List<String> paramYear = new ArrayList<>();
    List<String> paramStatus = new ArrayList<>();
    private String year = "";
    private String year2 = "";

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
        chartProgress       = (BarChart) findViewById(R.id.chartProgress);
        chartStatus         = (BarChart) findViewById(R.id.chartStatus);
        yearProgress        = (Spinner) findViewById(R.id.yearProgress);
        statusProgress      = (Spinner) findViewById(R.id.statusProgress);

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

            setYear(getIntent().getStringExtra("listYear"));
            setStatus(getIntent().getStringExtra("listYear"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setYear(String list) {
        try {
            paramYear.clear();
            JSONArray data = new JSONArray(list);
            for (int i = 0; i < data.length(); i++) {
                paramYear.add(data.get(i).toString());
            }
            
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, paramYear);
            yearProgress.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            yearProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    year        = paramYear.get(position);
                    loadHistory(0, getIntent().getStringExtra("listProgress"));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            year        = paramYear.get(0);
            loadHistory(0, getIntent().getStringExtra("listProgress"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStatus(String list) {
        try {
            paramStatus.clear();
            JSONArray data = new JSONArray(list);
            for (int i = 0; i < data.length(); i++) {
                paramStatus.add(data.get(i).toString());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, paramStatus);
            statusProgress.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            statusProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    year2        = paramStatus.get(position);
                    loadHistory(1, getIntent().getStringExtra("listProgress"));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            year2        = paramStatus.get(0);
            loadHistory(1, getIntent().getStringExtra("listProgress"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHistory(int type, String list) {
        try {
            ArrayList<BarEntry> values = new ArrayList<>();
            ArrayList<BarEntry> values2 = new ArrayList<>();
            JSONArray data = new JSONArray(list);
            values.clear();
            if (type == 0)
                listProgress.removeAllViews();
            else
                listStatus.removeAllViews();

            for (int i = 0; i < 12; i++) {
                values.add(new BarEntry((i + 1), 0));
                values2.add(new BarEntry((i + 1), 0));
            }
            for (int i = 0; i < data.length(); i++) {
                JSONObject detail   = data.getJSONObject(i);
                View view = helper.inflateView(R.layout.list_row_history_report);
                TextView title = (TextView) view.findViewById(R.id.title);
                TextView date = (TextView) view.findViewById(R.id.date);
                TextView status = (TextView) view.findViewById(R.id.status);

                title.setText(detail.getString("complication"));
                date.setText(detail.getString("date"));

                if (type == 0) {
                    if (detail.getString("year").equals(year)) {
                        if (detail.getString("progress").equalsIgnoreCase("I")) {
                            status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_primary));
                            status.setText("Membaik");
                            values.set(i, new BarEntry((i + 1), 3));
                        } else if (detail.getString("progress").equalsIgnoreCase("S")) {
                            status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_yelow));
                            status.setText("Stabil");
                            values.set(i, new BarEntry((i + 1), 2));
                        } else {
                            status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_red));
                            status.setText("Menurun");
                            values.set(i, new BarEntry((i + 1), 1));
                        }
                        listProgress.addView(view);
                    }
                } else {
                    if (detail.getString("year").equals(year2)) {
                        if (detail.getString("status").equalsIgnoreCase("HE")) {
                            status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_primary));
                            status.setText("Sehat");
                            values2.set(i, new BarEntry((i + 1), 3));
                        } else if (detail.getString("status").equalsIgnoreCase("DA")) {
                            status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_yelow));
                            status.setText("Cacat");
                            values2.set(i, new BarEntry((i + 1), 2));
                        } else {
                            status.setBackground(ContextCompat.getDrawable(ReportDetailActivity.this, R.drawable.border_fill_red));
                            status.setText("Meninggal");
                            values2.set(i, new BarEntry((i + 1), 1));
                        }
                        listStatus.addView(view);
                    }
                }
            }
            if (type == 0) {
                setDataChart(chartProgress, values);
            } else {
                setDataChart(chartStatus, values2);
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

    private void setDataChart(BarChart chart, ArrayList<BarEntry> values) {
        BarDataSet set1;
        set1 = new BarDataSet(values, "Minggu Ke");
        set1.setDrawIcons(false);
                set1.setColors(new int[]{
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorYellow),
                ContextCompat.getColor(this, R.color.colorRed)});
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(0f);
        data.setBarWidth(0.5f);
        chart.setScaleEnabled(false);
        chart.setDescription(null);
        chart.getLegend().setEnabled(false);
        chart.getAxisLeft().setAxisMaxValue(3);
        chart.getAxisLeft().setAxisMinValue(0);
        chart.getAxisLeft().setLabelCount(3);
        chart.getXAxis().setLabelCount(12);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setData(data);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
}