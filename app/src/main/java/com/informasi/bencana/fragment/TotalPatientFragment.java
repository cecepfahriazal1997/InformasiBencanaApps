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

import java.util.ArrayList;

public class TotalPatientFragment extends Fragment {
    private TotalPatientAdapter customGridAdapter;
    private ArrayList<TotalPatientModel> listData = new ArrayList();
    private GridView gridView;
    private DashboardActivity parent;

    private String country[] = {
            "Indonesia",
            "Bouvet Island",
            "Bangladesh",
            "Azerbaijan",
            "Australia",
            "Islandia"
    };

    private String total[] = {
            "33",
            "8",
            "63",
            "21",
            "14",
            "45"
    };

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
        for (int i = 0; i < country.length; i++) {
            TotalPatientModel model = new TotalPatientModel();
            model.setCountry(country[i]);
            model.setTotal(total[i]);

            listData.add(model);
        }

        gridView.setFocusable(false);
        customGridAdapter = new TotalPatientAdapter(getActivity(), R.layout.item_total_patient, listData);
        gridView.setAdapter(customGridAdapter);
        customGridAdapter.notifyDataSetChanged();
    }
}
