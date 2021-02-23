package com.prisminfoways.ultimate.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.ads.NativeAd;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.MatchResultDetailActivity;
import com.prisminfoways.ultimate.databinding.AdapterCompetedMatchBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.pojo.CompletedMatchPojo;

import java.util.ArrayList;

public class CompletedMatchAdapter extends RecyclerView.Adapter {

    private static final String TAG = "CompletedMatchAdapter";
    private Context context;
    private ArrayList<Object> completedMatch = new ArrayList<>();
    private static final int TYPE_AD_FB = 2;
    private static final int ITEM_VIEW = 1;
    private String gameName;

    public CompletedMatchAdapter(Context context, ArrayList<Object> completedMatch, String gameName) {
        this.context = context;
        this.completedMatch = completedMatch;
        this.gameName = gameName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_competed_match, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder viewHolder = (ItemViewHolder) holder;
            final CompletedMatchPojo.DataBean completedMatchPojo = (CompletedMatchPojo.DataBean) completedMatch.get(i);

            viewHolder.binding.warType.setText(completedMatchPojo.getName());
            viewHolder.binding.time.setText(completedMatchPojo.getMatch_time());
            viewHolder.binding.winPrice.setText(completedMatchPojo.getWin_price() );
            viewHolder.binding.perKill.setText(completedMatchPojo.getPer_kill() );
            viewHolder.binding.entryFees.setText(completedMatchPojo.getEntry_fee() /* + completedMatchPojo.getEntry_fee_coin() + " c"*/);
            viewHolder.binding.matchType.setText(completedMatchPojo.getType());
            viewHolder.binding.version.setText(completedMatchPojo.getVersion());
            viewHolder.binding.mapType.setText(completedMatchPojo.getMap());

            if (completedMatchPojo.getStatus().equals("3"))
                viewHolder.binding.myStatistics.setText("CANCELED");
            else
                viewHolder.binding.myStatistics.setText("WATCH MATCH");




            Glide.with(context).load(completedMatchPojo.getGame_details().getIcon()).into(viewHolder.binding.icon);



            viewHolder.binding.myStatistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String url = "https://www.youtube.com/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });

            viewHolder.binding.notJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (completedMatchPojo.getStatus().equals("2")) {
                        context.startActivity(new Intent(context, MatchResultDetailActivity.class)
                                .putExtra("image", completedMatchPojo.getGame_details().getImage())
                                .putExtra("icon", completedMatchPojo.getGame_details().getIcon())
                                .putExtra("gameName", gameName)
                                .putExtra("match_id", completedMatchPojo.getId()));
                    } else {
                        Log.d(TAG, "onBindViewHolder: Do Nothing");
                    }
                }
            });


            String[] time = completedMatchPojo.getMatch_time().split(" ");

            String time12h = Constants.timeFormat(time[1]);

            viewHolder.binding.time.setText("time: " + time[0] + " AT: " + time12h);

            viewHolder.binding.notJoin.setText("SEE RESULT");
        }
    }

    @Override
    public int getItemCount() {
        return completedMatch.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (completedMatch.get(position) instanceof NativeAd) {
            return TYPE_AD_FB;
        } else {
            return ITEM_VIEW;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        AdapterCompetedMatchBinding binding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
