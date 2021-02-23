package com.prisminfoways.ultimate.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.adapter.OnGoingLuckyDrawAdapter;
import com.prisminfoways.ultimate.databinding.FragmentLuckydrawongoingBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.LuckyDrawOngingPojo;
import com.wang.avi.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;


/**
 * A simple {@link Fragment} subclass.
 */
public class LuckyDrawOnGoingFragment extends Fragment {

    private static final String TAG = "LuckyDrawOnGoing";
    FragmentLuckydrawongoingBinding binding;
    Activity activity;
    StoreUserData storeUserData;
    ArrayList<Object> onGoingMatchList;
    OnGoingLuckyDrawAdapter adapter;
    CustomLoader customLoader;

    public LuckyDrawOnGoingFragment() {
        Log.d(TAG,"ongoing");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_luckydrawongoing, container, false);
        activity = getActivity();

        storeUserData = new StoreUserData(activity);
        customLoader = new CustomLoader(activity, false);

        onGoingMatchList = new ArrayList<>();
        binding.rvOngoingMatch.setLayoutManager(new LinearLayoutManager(activity));

        callOnGoingMatch();

        binding.swipeRefresh.setRefreshing(true);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.measure(100, 100);
                binding.swipeRefresh.setRefreshing(true);
                callOnGoingMatch();

            }
        });

        return binding.getRoot();
    }

    private void callOnGoingMatch() {

        final CustomLoader loader = new CustomLoader(getActivity(), false);

        loader.showLoader();
        binding.swipeRefresh.measure(100, 100);
        binding.swipeRefresh.setRefreshing(true);

        HashMap<String, String> map = new HashMap<>();


        new AddShow().handleCall(activity, Constants.TAG_GET_LUCKYDRAW_ONGOING, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                loader.dismissLoader();
                binding.swipeRefresh.setRefreshing(false);

                Log.d(TAG, "onLoaded: " + o.toString());
                LuckyDrawOngingPojo onGoingMatchPojo = new Gson().fromJson(o.toString(), LuckyDrawOngingPojo.class);

                if (onGoingMatchPojo.getStatus().equals("1")) {
                    binding.txtMessage.setVisibility(View.GONE);
                    if (!onGoingMatchPojo.getData().isEmpty()) {

                        onGoingMatchList.clear();
                        onGoingMatchList.addAll(onGoingMatchPojo.getData());
                        adapter = new OnGoingLuckyDrawAdapter(activity, onGoingMatchList);
                        binding.rvOngoingMatch.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        onGoingMatchList.clear();
                        Log.d(TAG, "onLoaded: No Data Available");
                    }
                } else {
                    binding.txtMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailed(Object o) {
                loader.dismissLoader();
                binding.swipeRefresh.setRefreshing(false);
                Log.d(TAG, "onFailed: " + o.toString());
                binding.txtMessage.setVisibility(View.VISIBLE);
            }
        }, false);
    }
}
