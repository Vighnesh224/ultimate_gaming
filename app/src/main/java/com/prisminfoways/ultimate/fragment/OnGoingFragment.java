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
import com.prisminfoways.ultimate.adapter.OnGoingMatchAdapter;
import com.prisminfoways.ultimate.databinding.FragmentOngoingBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.OnGoingMatchPojo;
import com.wang.avi.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnGoingFragment extends Fragment {

    private static final String TAG = "OngoingFragment";
    FragmentOngoingBinding binding;
    Activity activity;
    StoreUserData storeUserData;
    ArrayList<Object> onGoingMatchList;
    OnGoingMatchAdapter adapter;
    CustomLoader customLoader;
    private String gameID;
    private String gameName;

    public OnGoingFragment(String gameID, String gameName) {
        this.gameID = gameID;
        this.gameName = gameName;
    }

    public OnGoingFragment(String gameID) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ongoing, container, false);
        activity = getActivity();

        storeUserData = new StoreUserData(activity);
        customLoader = new CustomLoader(activity, false);

        onGoingMatchList = new ArrayList<>();
        binding.rvOngoingMatch.setLayoutManager(new LinearLayoutManager(activity));

        binding.shimmerLayout.startShimmerAnimation();

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

        binding.swipeRefresh.measure(100, 100);
        binding.swipeRefresh.setRefreshing(true);

        HashMap<String, String> map = new HashMap<>();
        map.put(com.prisminfoways.ultimate.helper.Constants.GAME_ID, gameID);

        new AddShow().handleCall(activity, Constants.TAG_ONGOING_MATCH, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                loader.dismissLoader();
                binding.swipeRefresh.setRefreshing(false);

                Log.d(TAG, "onLoaded: " + o.toString());
                OnGoingMatchPojo onGoingMatchPojo = new Gson().fromJson(o.toString(), OnGoingMatchPojo.class);

                if (onGoingMatchPojo.getStatus().equals("1")) {

                    if (!onGoingMatchPojo.getData().isEmpty()) {

                        binding.shimmerLayout.stopShimmerAnimation();
                        binding.swipeRefresh.setVisibility(View.VISIBLE);
                        binding.shimmerLayout.setVisibility(View.GONE);

                        binding.txtMessage.setVisibility(View.GONE);
                        onGoingMatchList.clear();
                        onGoingMatchList.addAll(onGoingMatchPojo.getData());
                        adapter = new OnGoingMatchAdapter(activity, onGoingMatchList,gameName);
                        binding.rvOngoingMatch.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        onGoingMatchList.clear();
                        Log.d(TAG, "onLoaded: No Data Available");
                        binding.txtMessage.setVisibility(View.VISIBLE);
                        binding.shimmerLayout.setVisibility(View.GONE);
                    }
                } else {
                    binding.txtMessage.setVisibility(View.VISIBLE);
                    binding.shimmerLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailed(Object o) {
                loader.dismissLoader();
                binding.swipeRefresh.setRefreshing(false);
                Log.d(TAG, "onFailed: " + o.toString());
                binding.txtMessage.setVisibility(View.VISIBLE);
                binding.shimmerLayout.setVisibility(View.GONE);
            }
        }, false);
    }
}
