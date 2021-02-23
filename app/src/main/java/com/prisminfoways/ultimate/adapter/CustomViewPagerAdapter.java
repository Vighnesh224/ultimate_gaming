package com.prisminfoways.ultimate.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    private List<Fragment> fragmentList = new ArrayList<>();


    public CustomViewPagerAdapter(Context context, List<Fragment> fragmentList,FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.fragmentList= fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        fragment = fragmentList.get(i);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        if (position == 0) {
            title = "ONGOING";
        } else if (position == 1) {
            title = "UPCOMING";
        } else if (position == 2) {
            title = "RESULT";
        }
        return title;
    }

}
