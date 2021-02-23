package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityMyStatisticsBinding;
import com.prisminfoways.ultimate.databinding.AdapterMyStatisticsBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.MyStatisticsPojo;
import com.wang.avi.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;

public class MyStatisticsActivity extends AppCompatActivity {

    private static final String TAG = "MyStatisticsActivity";
    ActivityMyStatisticsBinding binding;
    private List<Object> statisticsList;
    MyStatisticsAdapter adapter;
    StoreUserData storeUserData;
    CustomLoader customLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_statistics);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        statisticsList = new ArrayList<>();
        binding.rvStatistics.setLayoutManager(new LinearLayoutManager(this));

        callMyStatisticsApi();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(true);
                callMyStatisticsApi();
            }
        });

    }

    private void callMyStatisticsApi() {
        customLoader.showLoader();
        binding.swipeRefresh.setRefreshing(true);

        new AddShow().handleCall(MyStatisticsActivity.this, Constants.TAG_MY_STATISTICS, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                binding.swipeRefresh.setRefreshing(false);
                Log.d(TAG, "onLoaded: " + o.toString());
                MyStatisticsPojo myStatisticsPojo = new Gson().fromJson(o.toString(), MyStatisticsPojo.class);

                if (myStatisticsPojo.getStatus().equals("1")) {
                    statisticsList.clear();
                    statisticsList.addAll(myStatisticsPojo.getData());
                    adapter = new MyStatisticsAdapter();
                    binding.rvStatistics.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(Object o) {
                binding.swipeRefresh.setRefreshing(false);
                customLoader.dismissLoader();
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }

    public class MyStatisticsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MyStatisticsActivity.this).inflate(R.layout.adapter_my_statistics, viewGroup, false);
            return new StatisticsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            if (holder instanceof StatisticsViewHolder) {
                StatisticsViewHolder viewHolder = (StatisticsViewHolder) holder;
                MyStatisticsPojo.DataBean dataBean = (MyStatisticsPojo.DataBean) statisticsList.get(i);

                viewHolder.binding.matchInfo.setText(dataBean.getName());
                viewHolder.binding.totalWon.setText(dataBean.getWin_amount() + "");
                viewHolder.binding.totalPaid.setText(dataBean.getEntry_fee());
            }
        }

        @Override
        public int getItemCount() {
            return statisticsList.size();
        }

        public class StatisticsViewHolder extends RecyclerView.ViewHolder {

            AdapterMyStatisticsBinding binding;

            public StatisticsViewHolder(@NonNull View itemView) {
                super(itemView);

                binding = DataBindingUtil.bind(itemView);

            }
        }
    }
}
