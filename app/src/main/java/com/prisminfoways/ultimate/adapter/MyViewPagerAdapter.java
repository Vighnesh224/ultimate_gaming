package com.prisminfoways.ultimate.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.prisminfoways.ultimate.fragment.EarnFragment;
import com.prisminfoways.ultimate.fragment.GameListFragment;
import com.prisminfoways.ultimate.fragment.GiftFragment;
import com.prisminfoways.ultimate.fragment.ProfileFragment;
import com.prisminfoways.ultimate.fragment.ResultFragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    @SuppressWarnings("deprecation")
    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = null;

        if (i == 0) {
            fragment = new ResultFragment();
        } else if (i == 1) {
            fragment = new GiftFragment();
        } else if (i == 2) {
            fragment = new GameListFragment();
        } else if (i == 3) {
            fragment = new EarnFragment();
        } else if (i == 4) {
            fragment = new ProfileFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;

        if (position == 0) {
            title = "Home";
        } else if (position == 1) {
            title = "Ongoing";
        } else if (position == 2) {
            title = "Result";
        } else if (position == 3) {
            title = "Earn";
        } else if (position == 4) {
            title = "Profile";
        }

        return title;
    }
}
