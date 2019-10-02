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
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataMasterActivity extends MasterActivity {
    private ListView listView;
    private DataMasterAdapter adapter;
    private List<DataMasterModel> listData = new ArrayList<>();
    private Toolbar toolbar;
    private String type;

    private String title[] = {
            "Laki-laki",
            "Perempuan"
    };

    private String typeRecord[] = {
            "Gagal",
            "Bingung"
    };

    private String symptom[] = {
            "Sakit",
            "Mengantuk"
    };

    private String progresId[] = {
            "I",
            "S",
            "D"
    };

    private String progress[] = {
            "Membaik",
            "Stabil",
            "Menurun"
    };

    private String statusProgressId[] = {
            "HE",
            "DA",
            "DE"
    };

    private String statusProgress[] = {
            "Sehat",
            "Cacat",
            "Meninggal"
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

        initial();
    }

    private void initial() {
        type        = getIntent().getStringExtra("type");
        if (type.equals("location")) {
            loadFromApi(listCountries);
        } else if (type.equals("specific")) {
            loadFromApi(dataMaster + "gejalaSpesifik");
        } else if (type.equals("relationship")) {
            loadFromApi(dataMaster + "hubunganKeluarga");
        } else if (type.equals("radiologi")) {
            loadFromApi(dataMaster + "radiologi");
        } else if (type.equals("diagnostic")) {
            loadFromApi(dataMaster + "diagnosis");
        } else if (type.equals("therapy")) {
            loadFromApi(dataMaster + "terapi");
        } else if (type.equals("rehability")) {
            loadFromApi(dataMaster + "rehabilitas");
        } else if (type.equals("complication")) {
            loadFromApi(dataMaster + "komplikasi");
        } else if (type.equals("complicationDetail")) {
            loadFromApi(dataMaster + "detailKomplikasi");
        } else {
            loadStatic(type);
        }
    }

    private void loadStatic(String type) {
        listData.clear();
        if (type.equals("gender")) {
            for (int i = 0; i < title.length; i++) {
                DataMasterModel model = new DataMasterModel();
                model.setId("" + i);
                model.setTitle(title[i]);

                listData.add(model);
            }
        } else if (type.equals("typeRecord")) {
            for (int i = 0; i < typeRecord.length; i++) {
                DataMasterModel model = new DataMasterModel();
                model.setId("" + (i + 1));
                model.setTitle(typeRecord[i]);

                listData.add(model);
            }
        } else if (type.equals("symptom")) {
            for (int i = 0; i < symptom.length; i++) {
                DataMasterModel model = new DataMasterModel();
                model.setId("" + (i + 1));
                model.setTitle(symptom[i]);

                listData.add(model);
            }
        } else if (type.equals("progress")) {
            for (int i = 0; i < progress.length; i++) {
                DataMasterModel model = new DataMasterModel();
                model.setId("" + progresId[i]);
                model.setTitle(progress[i]);

                listData.add(model);
            }
        } else if (type.equals("status")) {
            for (int i = 0; i < statusProgress.length; i++) {
                DataMasterModel model = new DataMasterModel();
                model.setId("" + statusProgressId[i]);
                model.setTitle(statusProgress[i]);

                listData.add(model);
            }
        }

        adapter         = new DataMasterAdapter(DataMasterActivity.this, listData);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        helper.setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("id", listData.get(position).getId());
                resultIntent.putExtra("title", listData.get(position).getTitle());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
    
    private void loadFromApi(final String url) {
        clientApiService.getData(url, "object", false,
                new ApiService.hashMapListener() {
            @Override
            public String getHashMap(Map<String, String> hashMap) {
                try {
                    if (hashMap.get("success").equals("1")) {
                        JSONObject result   = new JSONObject(hashMap.get("result"));
                        JSONArray list      = result.getJSONArray("data");
                        listData.clear();
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject detail   = list.getJSONObject(i);
                            DataMasterModel model     = new DataMasterModel();

                            model.setId(detail.getString("id"));
                            model.setTitle(detail.getString("title"));

                            listData.add(model);
                        }

                        adapter         = new DataMasterAdapter(DataMasterActivity.this, listData);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        helper.setListViewHeightBasedOnChildren(listView);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("id", listData.get(position).getId());
                                resultIntent.putExtra("title", listData.get(position).getTitle());
                                setResult(RESULT_OK, resultIntent);
                                finish();
                            }
                        });
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
