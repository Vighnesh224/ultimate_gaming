package com.prisminfoways.ultimate.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.ads.NativeAd;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.MatchDetailActivity;
import com.prisminfoways.ultimate.databinding.AdapterOngoingBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.pojo.OnGoingMatchPojo;

import java.util.ArrayList;

public class OnGoingMatchAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Object> onGoingMatchList = new ArrayList<>();
    public static final int TYPE_AD_FB = 2;
    public static final int ITEM_VIEW = 1;
    private String gameName;

    public OnGoingMatchAdapter(Context context, ArrayList<Object> onGoingMatchList, String gameName) {
        this.context = context;
        this.onGoingMatchList = onGoingMatchList;
        this.gameName = gameName;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_ongoing, viewGroup, false);
        return new OnGoingMatchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if (holder instanceof OnGoingMatchHolder) {
            OnGoingMatchHolder viewHolder = (OnGoingMatchHolder) holder;
            final OnGoingMatchPojo.DataBean matchPojo = (OnGoingMatchPojo.DataBean) onGoingMatchList.get(i);

            viewHolder.binding.warType.setText(matchPojo.getName());
            viewHolder.binding.time.setText(matchPojo.getMatch_time());
            viewHolder.binding.winPrice.setText(matchPojo.getWin_price());
            viewHolder.binding.matchType.setText(matchPojo.getType());
            viewHolder.binding.entryFees.setText(matchPojo.getEntry_fee()/* + matchPojo.getEntry_fee_coin() + "c"*/);
            viewHolder.binding.perKill.setText(matchPojo.getPer_kill());
            viewHolder.binding.version.setText(matchPojo.getVersion());
            viewHolder.binding.mapType.setText(matchPojo.getMap());




            Glide.with(context).load(matchPojo.getGame_details().getIcon()).into(viewHolder.binding.icon);


            String[] time = matchPojo.getMatch_time().split(" ");

            String time12h = Constants.timeFormat(time[1]);



            viewHolder.binding.time.setText("time: " + time[0] + " AT: " + time12h);




            viewHolder.binding.rrDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, MatchDetailActivity.class)
                            .putExtra("match_id", matchPojo.getId())
                            .putExtra("is_join", matchPojo.getIs_joined())
                            .putExtra("detail", matchPojo.getDetail())
                            .putExtra("btn_join", "ongoing")
                            .putExtra("image", matchPojo.getGame_details().getImage())
                            .putExtra("icon", matchPojo.getGame_details().getIcon())
                            .putExtra("gameName", gameName)
                            .putExtra("openFrom","onGoing"));
                }
            });

            viewHolder.binding.isJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String url = "https://www.youtube.com/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
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

    public class OnGoingMatchHolder extends RecyclerView.ViewHolder {

        AdapterOngoingBinding binding;

        public OnGoingMatchHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }
}
