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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityMatchResultDetailBinding;
import com.prisminfoways.ultimate.databinding.AdapterResultlistBinding;
import com.prisminfoways.ultimate.databinding.AdapterWinnerslistBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.MatchResultPojo;
import com.wang.avi.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

public class MatchResultDetailActivity extends AppCompatActivity {

    private static final String TAG = "MatchResultDetailActivi";
    ActivityMatchResultDetailBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;
    String matchid;
    String icon;
    String image;
    List<MatchResultPojo.DataBean.WinnerBean> winnerList;
    List<MatchResultPojo.DataBean.ResultBean> resultBeanList;
    WinnerAdapter adapter;
    ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match_result_detail);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        matchid = getIntent().getStringExtra("match_id");
        image = getIntent().getStringExtra("image");
        icon = getIntent().getStringExtra("icon");

        Glide.with(this).load(icon).into(binding.icon);



        winnerList = new ArrayList<>();
        resultBeanList = new ArrayList<>();

        binding.rvWinnerList.setNestedScrollingEnabled(false);
        binding.rvResultList.setNestedScrollingEnabled(false);

        binding.rvWinnerList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvResultList.setLayoutManager(new LinearLayoutManager(this));







        callMatchResultApi(matchid);

    }

    private void callMatchResultApi(String matchId) {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.MATCH_ID, matchId);

        new AddShow().handleCall(MatchResultDetailActivity.this, add.Native.com.admodule.Constants.TAG_MATCH_RESULT, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());
                MatchResultPojo matchResultPojo = new Gson().fromJson(o.toString(), MatchResultPojo.class);

                binding.warType.setText(matchResultPojo.getData().getName());

                binding.winPrice.setText(matchResultPojo.getData().getWin_price() );
                binding.perKill.setText(matchResultPojo.getData().getPer_kill() );
                binding.entryFees.setText(matchResultPojo.getData().getEntry_fee() /* + matchResultPojo.getData().getEntry_fee_coin() + " c"*/);
                binding.matchType.setText(matchResultPojo.getData().getType());
                binding.version.setText(matchResultPojo.getData().getVersion());
                binding.mapType.setText(matchResultPojo.getData().getMap());

                String[] time = matchResultPojo.getData().getMatch_time().split(" ");

                String time12h = Constants.timeFormat(time[1]);

                binding.time.setText("time: " + time[0] + " AT: " + time12h);

                if (!matchResultPojo.getData().getWinner().isEmpty()) {
                    winnerList.clear();
                    winnerList.addAll(matchResultPojo.getData().getWinner());
                    adapter = new WinnerAdapter();
                    binding.rvWinnerList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    winnerList.clear();
                    Log.d(TAG, "onLoaded: No Data Available");
                }

                if (!matchResultPojo.getData().getResult().isEmpty()) {
                    resultBeanList.clear();
                    resultBeanList.addAll(matchResultPojo.getData().getResult());
                    resultAdapter = new ResultAdapter();
                    binding.rvResultList.setAdapter(resultAdapter);
                    adapter.notifyDataSetChanged();
                } else {
                    resultBeanList.clear();
                    Log.d(TAG, "onLoaded: No Data Available");
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }

    public class WinnerAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MatchResultDetailActivity.this).inflate(R.layout.adapter_winnerslist, viewGroup, false);
            return new WinnersHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            if (holder instanceof WinnersHolder) {
                WinnersHolder winnersHolder = (WinnersHolder) holder;
                MatchResultPojo.DataBean.WinnerBean winnerBean = winnerList.get(i);

                winnersHolder.binding.winnerPlayer.setText(winnerBean.getPubg_name());
                winnersHolder.binding.winnerKill.setText(winnerBean.getKill_count());
                winnersHolder.binding.rsWin.setText("₹ " + winnerBean.getWin_amount() + "");
            }
        }

        @Override
        public int getItemCount() {
            return winnerList.size();
        }

        public class WinnersHolder extends RecyclerView.ViewHolder {

            AdapterWinnerslistBinding binding;

            public WinnersHolder(@NonNull View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }
        }
    }

    public class ResultAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MatchResultDetailActivity.this).inflate(R.layout.adapter_resultlist, viewGroup, false);
            return new ResultViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            if (holder instanceof ResultViewHolder) {
                ResultViewHolder viewHolder = (ResultViewHolder) holder;

                MatchResultPojo.DataBean.ResultBean resultBean = resultBeanList.get(i);

                viewHolder.binding.winnerPlayer.setText(resultBean.getPubg_name());
                viewHolder.binding.winnerKill.setText(resultBean.getKill_count());
                viewHolder.binding.rsWin.setText("₹ " + resultBean.getWin_amount() + "");
            }
        }

        @Override
        public int getItemCount() {
            return resultBeanList.size();
        }

        public class ResultViewHolder extends RecyclerView.ViewHolder {

            AdapterResultlistBinding binding;

            public ResultViewHolder(@NonNull View itemView) {
                super(itemView);

                binding = DataBindingUtil.bind(itemView);
            }
        }
    }
}
