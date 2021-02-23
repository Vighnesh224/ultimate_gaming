package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.adapter.InnerPagerAdapter;
import com.prisminfoways.ultimate.fragment.LuckyDrawOnGoingFragment;
import com.prisminfoways.ultimate.fragment.LuckyDrawResultFragment;

import java.util.ArrayList;

public class LuckyDrawActivity extends AppCompatActivity {

    SlidingTabLayout redemTabLayout;
    ViewPager viewPagerRedeem;

    private final String[] mTitles = {"ONGOING", "RESULT"};
    private ArrayList<Fragment> mFragmentslist = new ArrayList<>();
    InnerPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_draw);

        redemTabLayout = findViewById(R.id.redemTabLayout);
        viewPagerRedeem = findViewById(R.id.viewPagerRedeem);
        ImageView imgBack;
        imgBack = findViewById(R.id.imgBack);

        mFragmentslist.clear();
        mFragmentslist.add(new LuckyDrawOnGoingFragment());
        mFragmentslist.add(new LuckyDrawResultFragment());

        viewPagerRedeem.setOffscreenPageLimit(mFragmentslist.size());
        pagerAdapter = new InnerPagerAdapter(getSupportFragmentManager(), mFragmentslist, mTitles);
        viewPagerRedeem.setAdapter(pagerAdapter);
        redemTabLayout.setViewPager(viewPagerRedeem, mTitles);

        viewPagerRedeem.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("on","pagescrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("on","pageselected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("on","pagescrollchange");
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}