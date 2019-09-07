package com.informasi.bencana.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.GridViewAdapter;
import com.informasi.bencana.app.DashboardActivity;
import com.informasi.bencana.app.PatientActivity;
import com.informasi.bencana.model.GridViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private GridViewAdapter customGridAdapter;
    private ArrayList<GridViewModel> listData = new ArrayList();
    private GridView gridView;
    private DashboardActivity parent;
    private int icon[] = {
            R.drawable.patient,
            R.drawable.progress,
            R.drawable.monitor,
            R.drawable.collaboration,
            R.drawable.report,
            R.drawable.grafic,
            R.drawable.accessment
    };

    private String title[] = {
            "Pasien",
            "Perkembangan",
            "Monitor",
            "Kolaborasi",
            "Laporan",
            "Grafik",
            "Assesment Kebutuhan"
    };

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent      = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView       = inflater.inflate(R.layout.fragment_home, container, false);
        gridView            = (GridView) rootView.findViewById(R.id.gridView);

        initial();

        return rootView;
    }

    private void initial() {
        for (int i = 0; i < title.length; i++) {
            GridViewModel model = new GridViewModel();
            model.setImage(icon[i]);
            model.setTitle(title[i]);

            listData.add(model);
        }

        gridView.setFocusable(false);
        customGridAdapter = new GridViewAdapter(getActivity(), R.layout.item_menu_home, listData);
        gridView.setAdapter(customGridAdapter);
        customGridAdapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parents, View view, int position, long id) {
                switch (position) {
                    case 0:
                        parent.functionHelper.startIntent(PatientActivity.class, false, false, null);
                        break;
                    default: break;
                }
            }
        });
    }
}
