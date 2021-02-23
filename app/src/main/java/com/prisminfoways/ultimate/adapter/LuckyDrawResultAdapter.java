package com.prisminfoways.ultimate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.ads.NativeAd;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.AdapterResultLuckydrawBinding;
import com.prisminfoways.ultimate.pojo.CompletedLuckyDrawPojo;

import java.util.ArrayList;

public class LuckyDrawResultAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Object> onGoingMatchList = new ArrayList<>();
    public static final int TYPE_AD_FB = 2;
    public static final int ITEM_VIEW = 1;

    public LuckyDrawResultAdapter(Context context, ArrayList<Object> onGoingMatchList) {
        this.context = context;
        this.onGoingMatchList = onGoingMatchList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_result_luckydraw, viewGroup, false);
        return new OngoingmatchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if (holder instanceof OngoingmatchHolder) {
            OngoingmatchHolder viewHolder = (OngoingmatchHolder) holder;
            final CompletedLuckyDrawPojo.DataBean matchPojo = (CompletedLuckyDrawPojo.DataBean) onGoingMatchList.get(i);

            viewHolder.binding.warType.setText(matchPojo.getTitle());
            viewHolder.binding.txtDrawOn.setText("Draw on : "+matchPojo.getResult_date());

            if(matchPojo.getWinning_price() == null){
                viewHolder.binding.txtWonPrice.setText("Won Price : ");
            }else{
                viewHolder.binding.txtWonPrice.setText("Won Price : "+matchPojo.getWinning_price());
            }

            if(matchPojo.getWinner_name() == null){
                viewHolder.binding.txtWonUser.setText("Won By : ");
            }else{
                viewHolder.binding.txtWonUser.setText("Won By : "+matchPojo.getWinner_name());
            }

            Glide.with(context).load("https://ultimategaming.prisminfoways.com/assets/images/"+matchPojo.getImage()).into(viewHolder.binding.imgBanner);

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

    public class OngoingmatchHolder extends RecyclerView.ViewHolder {

        AdapterResultLuckydrawBinding binding;

        public OngoingmatchHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }
}
