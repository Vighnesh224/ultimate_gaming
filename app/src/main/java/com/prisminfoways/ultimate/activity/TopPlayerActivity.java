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
import com.prisminfoways.ultimate.databinding.ActivityTopPlayerBinding;
import com.prisminfoways.ultimate.databinding.AdapterTopPlayersBinding;
import com.prisminfoways.ultimate.pojo.TopPlayerPojo;
import com.wang.avi.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

public class TopPlayerActivity extends AppCompatActivity {

    private static final String TAG = "TopPlayerActivity";
    ActivityTopPlayerBinding binding;
    CustomLoader customLoader;
    private List<Object> topPlayerList;
    TopPlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_player);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        customLoader = new CustomLoader(this, false);

        topPlayerList = new ArrayList<>();

        binding.rvTopPlayers.setLayoutManager(new LinearLayoutManager(this));

        callTopPlayerApi();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(true);
                callTopPlayerApi();
            }
        });
    }

    private void callTopPlayerApi() {

        customLoader.showLoader();
        binding.swipeRefresh.setRefreshing(true);

        new AddShow().handleCall(TopPlayerActivity.this, add.Native.com.admodule.Constants.TAG_TOP_PLAYER, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                binding.swipeRefresh.setRefreshing(false);
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());

                TopPlayerPojo topPlayerPojo = new Gson().fromJson(o.toString(), TopPlayerPojo.class);

                if (topPlayerPojo.getStatus().equals("1")) {
                    if (!topPlayerPojo.getData().isEmpty()) {
                        topPlayerList.clear();
                        topPlayerList.addAll(topPlayerPojo.getData());
                        adapter = new TopPlayerAdapter();
                        binding.rvTopPlayers.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        topPlayerList.clear();
                        Log.d(TAG, "onLoaded: No Data Available");
                    }
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                binding.swipeRefresh.setRefreshing(false);
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }

    public class TopPlayerAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(TopPlayerActivity.this).inflate(R.layout.adapter_top_players, viewGroup, false);
            return new TopViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

            if (holder instanceof TopViewHolder) {
                TopViewHolder viewHolder = (TopViewHolder) holder;
                TopPlayerPojo.DataBean dataBean = (TopPlayerPojo.DataBean) topPlayerList.get(i);

                viewHolder.binding.playerName.setText(dataBean.getName());
                viewHolder.binding.amountWon.setText(dataBean.getEarning());
            }
        }

        @Override
        public int getItemCount() {
            return topPlayerList.size();
        }

        public class TopViewHolder extends RecyclerView.ViewHolder {

            AdapterTopPlayersBinding binding;

            public TopViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }
        }
    }
}
