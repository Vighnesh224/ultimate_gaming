package com.prisminfoways.ultimate.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragmnet = new ArrayList<>();
    private String[] mTitles;


    public CustomPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragmnet, String[] mTitles) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mFragmnet = mFragmnet;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmnet.get(i);
    }

    @Override
    public int getCount() {
        return mFragmnet.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
