package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.KlasifikasiDisasterAdapter;
import com.informasi.bencana.model.DisasterModel;
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KlasifikasiDisasterActivity extends MasterActivity {
    private GridView gridView;
    private KlasifikasiDisasterAdapter adapter;
    private Toolbar toolbar;
    private ArrayList<String> listPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klasifikasi_disaster);
        toolbar             = findViewById(R.id.toolbar);
        gridView            = findViewById(R.id.gridView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Klasifikasi Disaster");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
    }

    private void initial() {
        listPhotos.clear();
        listPhotos.add("http://atp-sb-medis.com/image/menu-1.png");
        listPhotos.add("http://atp-sb-medis.com/image/menu-2.png");
        listPhotos.add("http://atp-sb-medis.com/image/menu-3.png");
        adapter         = new KlasifikasiDisasterAdapter(KlasifikasiDisasterActivity.this,
                R.layout.item_image, listPhotos, clientApiService);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> hashMap = new HashMap<>();
                switch (position) {
                    case 0 :
                        hashMap.put("title", "Natural Disaster");
                        hashMap.put("typeFile", "text");
                        hashMap.put("content", urlNaturalDisaster);
                        hashMap.put("loadType", "url");
                        helper.startIntent(MultiFormatActivity.class, false, false, hashMap);
                        break;
                    case 1 :
                        hashMap.put("title", "Technological Disaster");
                        hashMap.put("typeFile", "text");
                        hashMap.put("content", urlTechDisaster);
                        hashMap.put("loadType", "url");
                        helper.startIntent(MultiFormatActivity.class, false, false, hashMap);
                        break;
                    case 2 :
                        hashMap.put("title", "Complex Disaster");
                        hashMap.put("typeFile", "text");
                        hashMap.put("content", urlComplexDisaster);
                        hashMap.put("loadType", "url");
                        helper.startIntent(MultiFormatActivity.class, false, false, hashMap);
                        break;
                    default: break;
                }
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
