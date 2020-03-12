package com.informasi.bencana.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.KlasifikasiDisasterAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KlasifikasiDisasterActivity extends MasterActivity {
    private GridView gridView;
    private KlasifikasiDisasterAdapter adapter;
    private Toolbar toolbar;
    private ArrayList<String> listPhotos = new ArrayList<>();
    private TextView reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klasifikasi_disaster);
        toolbar             = findViewById(R.id.toolbar);
        gridView            = findViewById(R.id.gridView);
        reference           = findViewById(R.id.reference);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Klasifikasi Disaster");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
    }

    private void initial() {
        String url                  = reference.getText().toString();
        SpannableString content     = new SpannableString(reference.getText().toString() + ", 2019-12-14, 21.45");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        reference.setText(content);
        reference.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        reference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

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
