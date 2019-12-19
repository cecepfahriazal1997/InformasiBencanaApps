package com.informasi.bencana.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.GridViewAdapter;
import com.informasi.bencana.app.DashboardActivity;
import com.informasi.bencana.app.NeedAssesmentActivity;
import com.informasi.bencana.app.PatientActivity;
import com.informasi.bencana.app.PatientCollabActivity;
import com.informasi.bencana.app.PatientMonitoringActivity;
import com.informasi.bencana.app.PatientProgressActivity;
import com.informasi.bencana.app.ReportActivity;
import com.informasi.bencana.model.GridViewModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    private GridViewAdapter customGridAdapter;
    private ArrayList<GridViewModel> listData = new ArrayList();
    private GridView gridView;
    private DashboardActivity parent;
    private TextView name, email;
    private CircleImageView imageProfile;
    private int icon[] = {
            R.drawable.patient,
            R.drawable.progress,
            R.drawable.monitor,
            R.drawable.collaboration,
            R.drawable.report,
//            R.drawable.grafic,
            R.drawable.accessment
    };

    private String title[] = {
            "Pasien",
            "Perkembangan",
            "Monitor",
            "Kolaborasi",
            "Laporan",
//            "Grafik",
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
        name                = (TextView) rootView.findViewById(R.id.name);
        email               = (TextView) rootView.findViewById(R.id.email);
        imageProfile        = (CircleImageView) rootView.findViewById(R.id.avatar);

        initial();

        return rootView;
    }

    private void initial() {
        name.setText("" + parent.functionHelper.getSession("name"));
        email.setText("" + parent.functionHelper.getSession("email"));
        parent.apiService.getImageOnlineImageViewCircle(parent.functionHelper.getSession("image"), imageProfile);

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
                    case 1:
                        parent.functionHelper.startIntent(PatientProgressActivity.class, false, false, null);
                        break;
                    case 2:
                        parent.functionHelper.startIntent(PatientMonitoringActivity.class, false, false, null);
                        break;
                    case 3:
                        parent.functionHelper.startIntent(PatientCollabActivity.class, false, false, null);
                        break;
                    case 4:
                        parent.functionHelper.startIntent(ReportActivity.class, false, false, null);
                        break;
                    case 5:
                        parent.functionHelper.startIntent(NeedAssesmentActivity.class, false, false, null);
                        break;
                    default: break;
                }
            }
        });
    }
}
