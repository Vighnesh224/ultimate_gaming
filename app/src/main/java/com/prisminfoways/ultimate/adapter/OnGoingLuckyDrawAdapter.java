package com.prisminfoways.ultimate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.ads.NativeAd;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.LuckyDrawDetailsActivity;
import com.prisminfoways.ultimate.databinding.AdapterOngoingLuckydrawBinding;
import com.prisminfoways.ultimate.pojo.LuckyDrawOngingPojo;

import java.util.ArrayList;

public class OnGoingLuckyDrawAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Object> onGoingMatchList = new ArrayList<>();
    public static final int TYPE_AD_FB = 2;
    public static final int ITEM_VIEW = 1;

    public OnGoingLuckyDrawAdapter(Context context, ArrayList<Object> onGoingMatchList) {
        this.context = context;
        this.onGoingMatchList = onGoingMatchList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_ongoing_luckydraw, viewGroup, false);
        return new OnGoingMatchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if (holder instanceof OnGoingMatchHolder) {
            OnGoingMatchHolder viewHolder = (OnGoingMatchHolder) holder;
            final LuckyDrawOngingPojo.DataBean matchPojo = (LuckyDrawOngingPojo.DataBean) onGoingMatchList.get(i);

            viewHolder.binding.warType.setText(matchPojo.getTitle());

            viewHolder.binding.txtSpot.setText(matchPojo.getJoining_limit());

            viewHolder.binding.spotProgress.setProgress(Integer.parseInt(matchPojo.getTotal()));

            int spotleft = Integer.parseInt(matchPojo.getJoining_limit()) - Integer.parseInt(matchPojo.getTotal());

            if (!matchPojo.getJoining_limit().equals(matchPojo.getTotal())) {
                viewHolder.binding.txtSpotDetails.setText("Only " + spotleft + " Spots Left");
                viewHolder.binding.txtRegsDetails.setText("Registration open");
            } else {
                viewHolder.binding.txtSpotDetails.setText("No Spots Left");
                viewHolder.binding.txtRegsDetails.setText("Registration closed.");
            }

            Glide.with(context).load("https://ultimategaming.prisminfoways.com/assets/images/"+matchPojo.getImage()).into(viewHolder.binding.imgBanner);

            if (matchPojo.getIs_joined() == 1) {
                viewHolder.binding.isJoin.setText("Registered");
            } else {
                viewHolder.binding.isJoin.setText("Register");
            }

            /**
             * id : 2
             * title : Title
             * entry_fee_coin : 100
             * entry_fee_rs : 50
             * winning_price : 500
             * joining_limit : 50
             * start_date : 2020-06-29 13:30:00
             * end_date : 2020-07-10 23:50:00
             * result_date : 2020-08-08 19:05:00
             * image : 216a3f2c0edccdfc1448ae2a1086a894.png
             * status : 0
             * created_date : 2020-06-29 15:44:24
             * total : 0
             * is_joined : 0
             */


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, LuckyDrawDetailsActivity.class)
                            .putExtra("lucky_draw_id", matchPojo.getId())
                            .putExtra("title", matchPojo.getTitle())
                            .putExtra("entry_fee_coin", matchPojo.getEntry_fee_coin())
                            .putExtra("entry_fee_rs", matchPojo.getEntry_fee_rs())
                            .putExtra("winning_price", matchPojo.getWinning_price())
                            .putExtra("joining_limit", matchPojo.getJoining_limit())
                            .putExtra("start_date", matchPojo.getStart_date())
                            .putExtra("end_date", matchPojo.getEnd_date())
                            .putExtra("result_date", matchPojo.getResult_date())
                            .putExtra("image", matchPojo.getImage())
                            .putExtra("status", matchPojo.getStatus())
                            .putExtra("created_date", matchPojo.getCreated_date())
                            .putExtra("total", matchPojo.getTotal())
                            .putExtra("is_join", matchPojo.getIs_joined())
                            .putExtra("openFrom","OnGoingLuckyDraw"));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return onGoingMatchList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (onGoingMatchList.get(position) instanceof NativeAd) {
            return TYPE_AD_FB;
        } else {
            return ITEM_VIEW;
        }
    }

    public static class OnGoingMatchHolder extends RecyclerView.ViewHolder {

        AdapterOngoingLuckydrawBinding binding;

        public OnGoingMatchHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }
}
