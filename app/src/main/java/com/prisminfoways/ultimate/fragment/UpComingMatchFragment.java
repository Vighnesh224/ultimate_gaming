package com.prisminfoways.ultimate.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.adapter.UpComingMatchAdapter;
import com.prisminfoways.ultimate.databinding.FragmentUpcomingBinding;
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
public class UpComingMatchFragment extends Fragment {

    private static final String TAG = "OngoingFragment";
    FragmentUpcomingBinding binding;
    Activity activity;
    StoreUserData storeUserData;
    ArrayList<Object> onGoingMatchList;
    UpComingMatchAdapter adapter;
    CustomLoader customLoader;


    private String gameID;
    private String gameName;

    public UpComingMatchFragment(String gameID, String gameName) {
        this.gameID = gameID;
        this.gameName = gameName;
    }

    public UpComingMatchFragment(String gameID) {
        this.gameID = gameID;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming, container, false);
        activity = getActivity();

        storeUserData = new StoreUserData(activity);
        customLoader = new CustomLoader(activity, false);

        onGoingMatchList = new ArrayList<>();
        binding.rvOngoingMatch.setLayoutManager(new LinearLayoutManager(activity));

        binding.shimmerLayout.startShimmerAnimation();


        callOnGoingMatch("main");

        binding.swipeRefresh.setRefreshing(true);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.measure(100, 100);
                binding.swipeRefresh.setRefreshing(true);
                callOnGoingMatch("onRefresh");

            }
        });

        return binding.getRoot();
    }

    private void callOnGoingMatch(final String main) {

        final CustomLoader loader = new CustomLoader(getActivity(), false);

        binding.swipeRefresh.measure(100, 100);
        binding.swipeRefresh.setRefreshing(true);

        HashMap<String, String> map = new HashMap<>();
        map.put(com.prisminfoways.ultimate.helper.Constants.GAME_ID, gameID);

        new AddShow().handleCall(activity, Constants.TAG_TODAY_MATCH, map, new ErrorListner() {
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
                        adapter = new UpComingMatchAdapter(activity, onGoingMatchList,gameName);
                        binding.rvOngoingMatch.setAdapter(adapter);

                        if(main.equals("main")){
                            Log.d(TAG,"main");
                        }
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
