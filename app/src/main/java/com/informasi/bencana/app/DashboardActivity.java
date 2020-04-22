package com.informasi.bencana.app;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.informasi.bencana.R;
import com.informasi.bencana.adapter.ViewPagerAdapter;
import com.informasi.bencana.fragment.AccountFragment;
import com.informasi.bencana.fragment.HomeFragment;
import com.informasi.bencana.fragment.NewsFragment;
import com.informasi.bencana.fragment.TotalPatientFragment;
import com.informasi.bencana.other.ApiService;
import com.informasi.bencana.other.FunctionHelper;

public class DashboardActivity extends MasterActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean doubleBackToExitPressedOnce = false;
    private Handler handler;
    private boolean isPaused = false;
    public int tabIconColor ;
    public int tabIconColor2;
    public int position = 0;
    private int[] tabIcons = {
            R.drawable.dashboard,
            R.drawable.pasien,
            R.drawable.news,
            R.drawable.users
    };

    private String[] title = {
            "Dashboard",
            "Data Patient",
            "News",
            "Account"
    };

    private ViewPagerAdapter adapter;
    public FunctionHelper functionHelper;
    public ApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        functionHelper          = helper;
        apiService              = clientApiService;
        viewPager               = (ViewPager) findViewById(R.id.viewpager);
        tabLayout               = (TabLayout) findViewById(R.id.tabs);

        tabIconColor            = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        tabIconColor2           = ContextCompat.getColor(getApplicationContext(), R.color.colorGray);

        functionHelper.setupProgressDialog(pDialog, "Please wait ...");

        setupViewPager(viewPager);
        functionHelper.hideKeyboard();

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        changeColorIcon();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), title[0]);
        adapter.addFragment(new TotalPatientFragment(), title[1]);
        adapter.addFragment(new NewsFragment(), title[2]);
        adapter.addFragment(new AccountFragment(), title[3]);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabIcons.length; i++) {
            if (tabLayout.getTabAt(0).isSelected()) {
                setTabIcon(0, tabIcons[0], tabIconColor);
            }
            setTabIcon(i, tabIcons[i], tabIconColor2);
        }
    }

    private void changeColorIcon() {
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
               @Override
               public void onTabSelected(TabLayout.Tab tab) {
                   super.onTabSelected(tab);
                   tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                   position = tab.getPosition();
               }

               @Override
               public void onTabUnselected(TabLayout.Tab tab) {
                   super.onTabUnselected(tab);
                   tab.getIcon().setColorFilter(tabIconColor2, PorterDuff.Mode.SRC_IN);
               }

               @Override
               public void onTabReselected(TabLayout.Tab tab) {
               }
           }
        );
    }

    public void setTabIcon(int pos, int icon, int color) {
        tabLayout.getTabAt(pos).setIcon(icon);
        if (color != 0)
            tabLayout.getTabAt(pos).getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        functionHelper.showToast("Please click button back again to exit the Application !", 0);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                handler.removeCallbacksAndMessages(null);
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        functionHelper.deleteCache(null);
    }
}
