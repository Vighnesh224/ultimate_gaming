package com.prisminfoways.ultimate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.PaymentActivity;
import com.prisminfoways.ultimate.pojo.AddMoneyListPojo;

import java.util.ArrayList;
import java.util.List;

public class AddMoneyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;
    private List<AddMoneyListPojo.DataBean> data = new ArrayList<>();

    public AddMoneyListAdapter(Activity activity, List<AddMoneyListPojo.DataBean> data) {
        this.context = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View gamelistview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_addmoney_list, viewGroup, false);
        return new MyViewHolder(gamelistview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder holder = (MyViewHolder) viewHolder;

        holder.title.setText(data.get(i).getTitle());
        holder.coins.setText("₹ "+data.get(i).getAmount());

        holder.crdAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, PaymentActivity.class).putExtra("amount", data.get(i).getAmount()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView coins;
        private CardView crdAddMoney;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            coins = (TextView) itemView.findViewById(R.id.coins);
            crdAddMoney = itemView.findViewById(R.id.crdAddMoney);
        }

    }
}
