package com.informasi.bencana.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.DataMasterAdapter;
import com.informasi.bencana.model.DataMasterModel;

import java.util.ArrayList;
import java.util.List;

public class DataMasterActivity extends MasterActivity {
    private ListView listView;
    private DataMasterAdapter adapter;
    private List<DataMasterModel> list = new ArrayList<>();
    private Toolbar toolbar;

    private String title[] = {
            "Indonesia",
            "Australia",
            "Bangladesh",
            "Italia",
            "Inggris",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_master);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Choose Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialData();
    }

    private void initialData() {
        adapter         = new DataMasterAdapter(this, list);
        listView.setAdapter(adapter);

        for (int i = 0; i < title.length; i++) {
            DataMasterModel model = new DataMasterModel();
            model.setId(i + "");
            model.setTitle(title[i]);

            list.add(model);
        }

        adapter.notifyDataSetChanged();
        helper.setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("id", list.get(position).getId());
                resultIntent.putExtra("title", list.get(position).getTitle());
                setResult(RESULT_OK, resultIntent);
                finish();
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
