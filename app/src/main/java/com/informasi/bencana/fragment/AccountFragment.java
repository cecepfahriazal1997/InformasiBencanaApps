package com.informasi.bencana.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.informasi.bencana.R;
import com.informasi.bencana.app.DashboardActivity;

public class AccountFragment extends Fragment {
    private DashboardActivity parent;

    public AccountFragment() {
        parent          = (DashboardActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent      = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView       = inflater.inflate(R.layout.fragment_account, container, false);

        return rootView;
    }
}
