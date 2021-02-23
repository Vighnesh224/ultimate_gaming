package com.prisminfoways.ultimate.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.ads.NativeAd;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.MatchDetailActivity;
import com.prisminfoways.ultimate.databinding.AdapterUpcomingBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.pojo.OnGoingMatchPojo;

import java.util.ArrayList;

public class UpComingMatchAdapter extends RecyclerView.Adapter {

    private Activity context;
    private ArrayList<Object> onGoingMatchList = new ArrayList<>();
    public static final int TYPE_AD_FB = 2;
    public static final int ITEM_VIEW = 1;
    private String gameName;

    public UpComingMatchAdapter(Activity context, ArrayList<Object> onGoingMatchList, String gameName) {
        this.context = context;
        this.onGoingMatchList = onGoingMatchList;
        this.gameName = gameName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_upcoming, viewGroup, false);
        return new OnGoingMatchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof OnGoingMatchHolder) {
            OnGoingMatchHolder viewHolder = (OnGoingMatchHolder) holder;
            final OnGoingMatchPojo.DataBean matchPojo = (OnGoingMatchPojo.DataBean) onGoingMatchList.get(i);

            viewHolder.binding.warType.setText(matchPojo.getName());
            viewHolder.binding.time.setText(matchPojo.getMatch_time());
            viewHolder.binding.winPrice.setText(matchPojo.getWin_price());
            viewHolder.binding.matchType.setText(matchPojo.getType());
            viewHolder.binding.entryFees.setText(matchPojo.getEntry_fee() /* + matchPojo.getEntry_fee_coin() + "c"*/);
            viewHolder.binding.perKill.setText(matchPojo.getPer_kill());
            viewHolder.binding.version.setText(matchPojo.getVersion());
            viewHolder.binding.mapType.setText(matchPojo.getMap());
            viewHolder.binding.txtSpot.setText(matchPojo.getTotal_spot() + "/100");

            String[] time = matchPojo.getMatch_time().split(" ");

            String time12h = Constants.timeFormat(time[1]);



            Glide.with(context).load(matchPojo.getGame_details().getIcon()).into(viewHolder.binding.icon);

            Glide.with(context).load(matchPojo.getGame_details().getImage()).into(viewHolder.binding.imgBanner);

            viewHolder.binding.spotProgress.setProgress(Integer.parseInt(matchPojo.getCount()));

            viewHolder.binding.time.setText("time: " + time[0] + " AT: " + time12h);



            int spotleft = Integer.parseInt(matchPojo.getTotal_spot()) - Integer.parseInt(matchPojo.getCount());

            if (!matchPojo.getTotal_spot().equals(matchPojo.getCount())) {
                viewHolder.binding.txtSpotDetails.setText("Only " + spotleft + " Spot Left");
            } else {
                viewHolder.binding.txtSpotDetails.setText("No Spots Left! Match is Full.");
            }

            viewHolder.binding.txtSpot.setText(matchPojo.getCount()+"/" + matchPojo.getTotal_spot());

            if (matchPojo.getIs_joined() >= 1) {
                viewHolder.binding.isJoin.setText("+ Joined");
            }
            else if (matchPojo.getIs_joined() == 0){
                viewHolder.binding.isJoin.setText("Not Joined");
            }else {
                viewHolder.binding.isJoin.setText("Not Joined");
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, MatchDetailActivity.class)
                            .putExtra("match_id", matchPojo.getId())
                            .putExtra("is_join", matchPojo.getIs_joined())
                            .putExtra("detail", matchPojo.getDetail())
                            .putExtra("btn_join", "UpComing")
                            .putExtra("image", matchPojo.getGame_details().getImage())
                            .putExtra("icon", matchPojo.getGame_details().getIcon())
                            .putExtra("gameName", gameName)
                            .putExtra("matchType", matchPojo.getType())
                            .putExtra("openFrom","UpComing"));
                }
            });

            viewHolder.binding.llPrizePoll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPrizePoolDialog((OnGoingMatchPojo.DataBean) onGoingMatchList.get(i));
                }
            });
        }
    }

    private void showPrizePoolDialog(OnGoingMatchPojo.DataBean dataBean) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View view = context.getLayoutInflater().inflate(R.layout.prizepool_dialog, null);
        dialogBuilder.setView(view);

        TextView txtGameDetails = view.findViewById(R.id.txtGameDetails);
        TextView txtWinnerDetails = view.findViewById(R.id.txtWinnerDetails);
        TextView txtKillDetails = view.findViewById(R.id.txtKillDetails);
        TextView txtTotalPrize = view.findViewById(R.id.txtTotalPrize);

        ImageView imgClose = view.findViewById(R.id.imgClose);

        txtGameDetails.setText(dataBean.getName()+" MATCH # "+dataBean.getId());
        txtKillDetails.setText("Per Kill - "+" ₹"+dataBean.getPer_kill());

        String totalPrize = "Winner - \n";

        for(int i = 0; i<dataBean.getPrice_pool().size();i++){

            totalPrize += "Rank " + dataBean.getPrice_pool().get(i).getRank() + " - " + dataBean.getPrice_pool().get(i).getAmount() + "\n";
        }

        txtWinnerDetails.setText(totalPrize);

        txtTotalPrize.setText("Total Prize - "+" ₹"+dataBean.getWin_price());

        final AlertDialog dialog = dialogBuilder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
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

        AdapterUpcomingBinding binding;

        public OnGoingMatchHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }
}
