package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.UserGuideAdapter;
import com.informasi.bencana.model.UserGuideModel;
import com.informasi.bencana.other.FunctionHelper;

import java.util.ArrayList;
import java.util.List;

public class UserGuideActivity extends AppCompatActivity {
    private FunctionHelper functionHelper;
    private ListView listView;
    private UserGuideAdapter adapter;
    private List<UserGuideModel> list = new ArrayList<>();
    private Toolbar toolbar;

    private String title[] = {
            "Petunjuk Pemakaian / User Guide",
            "Diagram Alir / Flowchart"
    };

    private String description[] = {
            "Petunjuk pemakaian dalam Sistem Informasi Medis Bencana, mencakup dari memasukkan data, mengedit data, serta menghapus data.",
            "Bagan atau diagram alir dari Sistem Informasi Medis Bencana"
    };

    private String type[] = {
            "doc",
            "pdf"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        functionHelper      = new FunctionHelper(this);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Download User Guide");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialData();
    }

    private void initialData() {
        adapter         = new UserGuideAdapter(this, list);
        listView.setAdapter(adapter);

        for (int i = 0; i < title.length; i++) {
            UserGuideModel model = new UserGuideModel();
            model.setTitle(title[i]);
            model.setDescription(description[i]);
            model.setType(type[i]);

            list.add(model);
        }

        adapter.notifyDataSetChanged();
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
