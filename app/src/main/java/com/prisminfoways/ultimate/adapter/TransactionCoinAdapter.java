package com.prisminfoways.ultimate.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.prisminfoways.ultimate.R;

import java.util.ArrayList;

import com.prisminfoways.ultimate.databinding.AdapterTransactionCoinBinding;
import com.prisminfoways.ultimate.helper.OnLoadMoreListener;
import com.prisminfoways.ultimate.pojo.TransactionCoinPojo;

public class TransactionCoinAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Object> transactionCoinList;
    RecyclerView recyclerView;
    private boolean isLoading;
    private int firstvisibleItem;
    private int visibleItemCount;
    private int  totalItemCount;
    int viewItem = 1;
    private int visibleThrsold = 1;
    private OnLoadMoreListener onLoadMoreListener;

    public TransactionCoinAdapter(Context context, final ArrayList<Object> transactionCoinList, RecyclerView recyclerView) {
        this.context = context;
        this.transactionCoinList = transactionCoinList;
        this.recyclerView = recyclerView;

        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstvisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (totalItemCount - visibleItemCount) >= (firstvisibleItem + visibleThrsold)) {
                    if (onLoadMoreListener != null) {
                        if (transactionCoinList.size() >= 30) {
                            onLoadMoreListener.onLoad();
                        } else {
                            Log.d("size","less than 30");
                        }
                    }
                    isLoading = true;
                }
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == viewItem) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_transaction_coin, viewGroup, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.load_more_layout, viewGroup, false);
            return new ProgressBarViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            TransactionCoinPojo.DataBean coinPojo = (TransactionCoinPojo.DataBean) transactionCoinList.get(i);

            viewHolder.binding.title.setText(coinPojo.getTitle());
            viewHolder.binding.time.setText(coinPojo.getCreated_date());
            viewHolder.binding.coins.setText(coinPojo.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return transactionCoinList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewProg = 0;
        return transactionCoinList.get(position) != null ? viewItem : viewProg;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AdapterTransactionCoinBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

        }
    }

    public class ProgressBarViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public ProgressBarViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressbar_load_more);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

}
