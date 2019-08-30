package com.informasi.bencana.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.informasi.bencana.R;
import com.informasi.bencana.adapter.NewsAdapter;
import com.informasi.bencana.app.DashboardActivity;
import com.informasi.bencana.model.NewsModel;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
    private NewsAdapter adapter;
    private ArrayList<NewsModel> listData = new ArrayList();
    private ListView listView;
    private DashboardActivity parent;

    private String title[] = {
            "PERGURUAN TINGGI MH THAMRIN SERAHKAN BEASISWA",
            "MAHASISWA KEBIDANAN DAN KEPERAWATAN DI KUNJUNGI",
            "LATIHAN DASAR BELA NEGARA",
            "PENANDATANGANAN MOU DENGAN RS PLUIT JAKARTA",
            "MARKETING RADJAK GROUP KOMPAK DAN FOKUS",
            "MAHASISWA THAMRIN GELAR SIMULASI KESIAPSIAGAAN BENCANA"
    };

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
        for (int i = 0; i < title.length; i++) {
            NewsModel model = new NewsModel();
            model.setTitle(title[i]);
            model.setDate("2" + i + " Agustus 2019");

            listData.add(model);
        }

        listView.setFocusable(false);
        adapter = new NewsAdapter(getActivity(), listData);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
