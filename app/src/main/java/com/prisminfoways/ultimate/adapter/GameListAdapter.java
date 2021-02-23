package com.prisminfoways.ultimate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.GameDetailActivity;
import com.prisminfoways.ultimate.activity.HTML_GameDetailsActivity;
import com.prisminfoways.ultimate.pojo.GameListPojo;

import java.util.ArrayList;
import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;
    private List<GameListPojo.DataBean> data = new ArrayList<>();

    public GameListAdapter(Activity activity, List<GameListPojo.DataBean> data) {
        this.context = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View gamelistview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_list_layout, viewGroup, false);
        return new MyViewHolder(gamelistview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder holder = (MyViewHolder) viewHolder;

        holder.txtGameName.setText(data.get(i).getTitle());

        Glide.with(context).load(data.get(i).getImage())
                .placeholder(context.getResources().getDrawable(R.drawable.game_placeholder))
                .error(context.getResources().getDrawable(R.drawable.game_placeholder))
                .into(holder.imgGame);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (data.get(i).getType().equals("Banner")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(i).getPlaystoreUrl()));
                    context.startActivity(browserIntent);
                } else if (data.get(i).getType().equals("HTML Games")) {
                    Intent intent = new Intent(context, HTML_GameDetailsActivity.class);
                    intent.putExtra("gameID", data.get(i).getId());
                    intent.putExtra("gameName", data.get(i).getTitle());
                    intent.putExtra("playstore_url", data.get(i).getPlaystoreUrl());
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, GameDetailActivity.class);
                    intent.putExtra("gameID", data.get(i).getId());
                    intent.putExtra("gameName", data.get(i).getTitle());
                    context.startActivity(intent);
                }
            }

        });

        if (data.get(i).getType().equals("Banner")) {
            holder.txtGameName.setVisibility(View.GONE);
        } else {
            holder.txtGameName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgGame;
        private TextView txtGameName;
        private LinearLayout relativeLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGame = (ImageView) itemView.findViewById(R.id.img_gamelist);
            txtGameName = (TextView) itemView.findViewById(R.id.txt_gamelist);
            relativeLayout = (LinearLayout) itemView.findViewById(R.id.layout_gamelist);
        }

    }
}
