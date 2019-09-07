package com.informasi.bencana.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.PatientAdapter;
import com.informasi.bencana.model.PatientModel;
import com.informasi.bencana.other.FunctionHelper;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class PatientActivity extends AppCompatActivity {
    private FunctionHelper functionHelper;
    private ListView listView;
    private PatientAdapter adapter;
    private List<PatientModel> list = new ArrayList<>();
    private Toolbar toolbar;
    private FancyButton btnAdd;

    private String id[] = {
            "9/Aug/2011",
            "11/Aug/2011",
            "14/Aug/2011",
            "15/Aug/2011"
    };

    private String name[] = {
            "Rinto Rivanto",
            "Supriyanto",
            "Hani Nurhaini",
            "Susi Nuralisah"
    };

    private String doctor[] = {
            "Putri Agustin",
            "Handoyo",
            "Boyke",
            "Putri Agustin"
    };

    private String gender[] = {
            "Laki-laki",
            "Laki-laki",
            "Perempuan",
            "Perempuan"
    };

    private String age[] = {
            "21",
            "31",
            "21",
            "24"
    };

    private String stepOne[] = {
            "1",
            "1",
            "1",
            "1"
    };

    private String stepTwo[] = {
            "0",
            "1",
            "0",
            "1"
    };

    private String stepThree[] = {
            "0",
            "0",
            "1",
            "1"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        functionHelper      = new FunctionHelper(this);
        toolbar             = findViewById(R.id.toolbar);
        listView            = findViewById(R.id.listView);
        btnAdd              = findViewById(R.id.btnAdd);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Download User Guide");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialData();
    }

    private void initialData() {
        adapter         = new PatientAdapter(this, list);
        listView.setAdapter(adapter);

        for (int i = 0; i < id.length; i++) {
            PatientModel model = new PatientModel();
            model.setId(id[i]);
            model.setName(name[i]);
            model.setDoctor(doctor[i]);
            model.setGender(gender[i]);
            model.setAge(age[i]);
            model.setStepOne(stepOne[i]);
            model.setStepTwo(stepTwo[i]);
            model.setStepThree(stepThree[i]);

            list.add(model);
        }

        adapter.notifyDataSetChanged();
        functionHelper.setListViewHeightBasedOnChildren(listView);
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
