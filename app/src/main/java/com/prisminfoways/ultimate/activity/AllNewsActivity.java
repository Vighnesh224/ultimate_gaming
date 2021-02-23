package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityAllNewsBinding;
import com.prisminfoways.ultimate.fragment.news.BusinessFragment;
import com.prisminfoways.ultimate.fragment.news.PoliticsFragment;
import com.prisminfoways.ultimate.fragment.news.TechnologyFragment;

import java.util.ArrayList;

public class AllNewsActivity extends AppCompatActivity {

    ActivityAllNewsBinding binding;
    private ArrayList<Fragment> mFragmentsList;
    private String[] mTitle = {"Technology", "Business", "Politics"};
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_news);


        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mFragmentsList = new ArrayList<>();
        mFragmentsList.clear();

        mFragmentsList.add(new TechnologyFragment());
        mFragmentsList.add(new BusinessFragment());
        mFragmentsList.add(new PoliticsFragment());

        binding.mainPager.setAdapter(adapter);
        binding.tabNews.setupWithViewPager(binding.mainPager);

        binding.mainPager.setOffscreenPageLimit(1);
        binding.mainPager.setCurrentItem(0);

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        @SuppressWarnings("deprecation")
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentsList.get(i);
        }

        @Override
        public int getCount() {
            return mFragmentsList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }

}
