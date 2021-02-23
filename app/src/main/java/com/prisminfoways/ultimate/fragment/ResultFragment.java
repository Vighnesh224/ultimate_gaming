package com.prisminfoways.ultimate.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.BuyProductsActivity;
import com.prisminfoways.ultimate.activity.LuckyDrawActivity;
import com.prisminfoways.ultimate.activity.MainActivity;
import com.prisminfoways.ultimate.activity.NotificationActivity;
import com.prisminfoways.ultimate.activity.WatchAndEarnActivity;
import com.prisminfoways.ultimate.adapter.CompletedMatchAdapter;
import com.prisminfoways.ultimate.databinding.FragmentResultBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.wang.avi.CustomLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class ResultFragment extends Fragment {

    private static final String TAG = "ResultFragment";
    FragmentResultBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;
    Activity activity;
    CompletedMatchAdapter adapter;
    ArrayList<Object> completedMatchList;

    public ResultFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
            if (activity != null) {
                Log.d(TAG,"visible");
            } else {
                Log.d(TAG,"not visible");
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false);
        activity = getActivity();

        storeUserData = new StoreUserData(activity);
        customLoader = new CustomLoader(activity, false);

        completedMatchList = new ArrayList<>();
        binding.rvCompletedMatch.setLayoutManager(new LinearLayoutManager(activity));

        binding.swipeRefresh.measure(100, 100);
        binding.swipeRefresh.setRefreshing(true);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.measure(100, 100);
                binding.swipeRefresh.setRefreshing(true);

            }
        });

        binding.crdLuckyDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LuckyDrawActivity.class);
                getActivity().startActivity(intent);
            }
        });

        binding.crdWatchVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), WatchAndEarnActivity.class);
                getActivity().startActivity(intent);

            }
        });

        binding.crdBuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BuyProductsActivity.class);
                getActivity().startActivity(intent);
            }
        });

        binding.crdPlayEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).binding.mainPager.setCurrentItem(2);
            }
        });

        binding.imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return binding.getRoot();
    }

}
