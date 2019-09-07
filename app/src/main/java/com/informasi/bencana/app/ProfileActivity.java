package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.other.FunctionHelper;

public class ProfileActivity extends AppCompatActivity {
    private FunctionHelper functionHelper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar            = findViewById(R.id.toolbar);
        functionHelper     = new FunctionHelper(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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