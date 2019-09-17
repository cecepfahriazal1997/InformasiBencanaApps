package com.informasi.bencana.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.NewsAdapter;
import com.informasi.bencana.app.DashboardActivity;
import com.informasi.bencana.app.DetailNewsActivity;
import com.informasi.bencana.model.NewsModel;
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsFragment extends Fragment {
    private NewsAdapter adapter;
    private ArrayList<NewsModel> listData = new ArrayList();
    private ListView listView;
    private DashboardActivity parent;

    public NewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent      = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView       = inflater.inflate(R.layout.fragment_news, container, false);
        listView            = (ListView) rootView.findViewById(R.id.listView);

        initial();

        return rootView;
    }

    private void initial() {
        listData.clear();
        parent.apiService.getData(parent.urlNews, "object", false, new ApiService.hashMapListener() {
            @Override
            public String getHashMap(Map<String, String> hashMap) {
                try {
                    if (hashMap.get("success").equals("1")) {
                        JSONObject result   = new JSONObject(hashMap.get("result"));
                        JSONArray list      = result.getJSONArray("data");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject detail   = list.getJSONObject(i);
                            NewsModel model     = new NewsModel();

                            model.setTitle(detail.getString("judul"));
                            model.setDate(detail.getString("tgl"));
                            model.setDescription(detail.getString("isi"));

                            listData.add(model);
                        }

                        listView.setFocusable(false);
                        adapter = new NewsAdapter(getActivity(), listData);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parents, View view, int position, long id) {
                                Map<String, String> param = new HashMap<>();
                                param.put("title", listData.get(position).getTitle());
                                param.put("date", listData.get(position).getDate());
                                param.put("content", listData.get(position).getDescription());
                                parent.functionHelper.startIntent(DetailNewsActivity.class, false, false, param);
                            }
                        });
                    } else {
                        parent.functionHelper.popupDialog("Oops", hashMap.get("message"), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
