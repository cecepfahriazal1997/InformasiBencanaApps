package com.informasi.bencana.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.TotalPatientAdapter;
import com.informasi.bencana.app.DashboardActivity;
import com.informasi.bencana.model.TotalPatientModel;
import com.informasi.bencana.other.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class TotalPatientFragment extends Fragment {
    private TotalPatientAdapter customGridAdapter;
    private ArrayList<TotalPatientModel> listData = new ArrayList();
    private GridView gridView;
    private DashboardActivity parent;
    
    public TotalPatientFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent      = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView       = inflater.inflate(R.layout.fragment_total_patient, container, false);
        gridView            = (GridView) rootView.findViewById(R.id.gridView);

        initial();

        return rootView;
    }

    private void initial() {
        parent.apiService.getData(parent.urlPatientCountries, "object", false,
                new ApiService.hashMapListener() {
            @Override
            public String getHashMap(Map<String, String> hashMap) {
                try {
                    if (hashMap.get("success").equals("1")) {
                        JSONObject result   = new JSONObject(hashMap.get("result"));
                        JSONArray list      = result.getJSONArray("data");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject detail   = list.getJSONObject(i);
                            TotalPatientModel model     = new TotalPatientModel();

                            model.setCountry(detail.getString("name"));
                            model.setTotal(detail.getString("total"));

                            listData.add(model);
                        }

                        gridView.setFocusable(false);
                        customGridAdapter = new TotalPatientAdapter(getActivity(), R.layout.item_total_patient, listData);
                        gridView.setAdapter(customGridAdapter);
                        customGridAdapter.notifyDataSetChanged();
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
