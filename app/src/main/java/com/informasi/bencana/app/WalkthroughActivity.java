package com.informasi.bencana.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.informasi.bencana.R;
import com.informasi.bencana.fragment.WalkthroughOneFragment;
import com.informasi.bencana.fragment.WalkthroughThreeFragment;
import com.informasi.bencana.fragment.WalkthroughTwoFragment;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */

public class WalkthroughActivity extends MasterActivity {

    private ViewPager mViewPager;
    private PageIndicatorView pageIndicatorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        mViewPager          = (ViewPager)findViewById(R.id.viewPager);
        pageIndicatorView   = (PageIndicatorView)findViewById(R.id.pageIndicatorView);

        setupViewPager(mViewPager);
        pageIndicatorView.setViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        WalkthroughFragmentAdapter adapter = new WalkthroughFragmentAdapter(getSupportFragmentManager());
        adapter.addFrag(new WalkthroughOneFragment());
        adapter.addFrag(new WalkthroughTwoFragment());
        adapter.addFrag(new WalkthroughThreeFragment());

        viewPager.setAdapter(adapter);
    }

    class WalkthroughFragmentAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        public WalkthroughFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
