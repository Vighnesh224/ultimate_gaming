package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.adapter.InnerPagerAdapter;
import com.prisminfoways.ultimate.fragment.MatchResultFragment;
import com.prisminfoways.ultimate.fragment.OnGoingFragment;
import com.prisminfoways.ultimate.fragment.UpComingMatchFragment;

import java.util.ArrayList;

public class GameDetailActivity extends AppCompatActivity {
    SlidingTabLayout redemTabLayout;
    ViewPager viewPagerRedeem;

    private final String[] mTitles = {"ONGOING", "UPCOMING", "RESULT"};
    private ArrayList<Fragment> mFragmentsarraylist = new ArrayList<>();
    InnerPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        redemTabLayout = findViewById(R.id.redemTabLayout);
        viewPagerRedeem = findViewById(R.id.viewPagerRedeem);
        ImageView imgBack;
        imgBack = findViewById(R.id.imgBack);
        TextView txtmultigame;
        txtmultigame = findViewById(R.id.txtmultigame);
        String gameID;
        gameID = getIntent().getStringExtra("gameID");
        String gameName = getIntent().getStringExtra("gameName");

        txtmultigame.setText(gameName);

        mFragmentsarraylist.clear();
        mFragmentsarraylist.add(new OnGoingFragment(gameID, gameName));
        mFragmentsarraylist.add(new UpComingMatchFragment(gameID, gameName));
        mFragmentsarraylist.add(new MatchResultFragment(gameID, gameName));

        viewPagerRedeem.setOffscreenPageLimit(mFragmentsarraylist.size());
        pagerAdapter = new InnerPagerAdapter(getSupportFragmentManager(), mFragmentsarraylist, mTitles);
        viewPagerRedeem.setAdapter(pagerAdapter);
        redemTabLayout.setViewPager(viewPagerRedeem, mTitles);

        redemTabLayout.setCurrentTab(1);

        viewPagerRedeem.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("page","scrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("page selected","on");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("onpage","scrolledstatechanged");
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
