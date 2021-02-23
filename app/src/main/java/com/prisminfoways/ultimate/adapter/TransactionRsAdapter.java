package com.prisminfoways.ultimate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.AdapterTransactionCoinBinding;
import com.prisminfoways.ultimate.helper.OnLoadMoreListener;
import com.prisminfoways.ultimate.pojo.TransactionRsPojo;

import java.util.ArrayList;
import java.util.List;

public class TransactionRsAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<TransactionRsPojo.DataBean> requestList;

    boolean isLoading;
    int firstvisibleItem;
    int visibleItemCount;
    int totalItemCount;
    int visibleThrsold = 1;
    OnLoadMoreListener onLoadMoreListener;

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    public TransactionRsAdapter(Context context, final ArrayList<TransactionRsPojo.DataBean> requestList, RecyclerView recyclerView) {
        this.context = context;
        this.requestList = requestList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_transaction_coin, viewGroup, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.load_more_layout, viewGroup, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            TransactionRsPojo.DataBean transactionPojo = (TransactionRsPojo.DataBean) requestList.get(i);

            viewHolder.binding.title.setText(transactionPojo.getTitle());
            viewHolder.binding.time.setText(transactionPojo.getCreated_date());
            viewHolder.binding.coins.setText("₹ "+transactionPojo.getAmount());

            viewHolder.binding.imgCoin.setVisibility(View.GONE);

            if (transactionPojo.getType() == 1) {
                viewHolder.binding.transactionStatus.setVisibility(View.VISIBLE);
                if (transactionPojo.getStatus() == 1) {
                    viewHolder.binding.transactionStatus.setText("Success");
                } else {
                    viewHolder.binding.transactionStatus.setText("Pending");
                }
            } else {
                viewHolder.binding.transactionStatus.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == requestList.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }
    @Override
    public int getItemCount() {
        return requestList == null ? 0 : requestList.size();
    }

    public void addItems(List<TransactionRsPojo.DataBean> postItems) {
        requestList.addAll(postItems);
        notifyDataSetChanged();
    }
    public void addLoading() {
        isLoaderVisible = true;
        requestList.add(new TransactionRsPojo.DataBean());
        notifyItemInserted(requestList.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = requestList.size() - 1;

        if(requestList.isEmpty()){
            notifyItemRemoved(0);
        }else{
            TransactionRsPojo.DataBean item = getItem(position);
            if (item != null) {
                requestList.remove(position);
                notifyItemRemoved(position);
            }
        }

    }

    public void clear() {
        requestList.clear();
        notifyDataSetChanged();
    }

    TransactionRsPojo.DataBean getItem(int position) {
        return requestList.get(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        AdapterTransactionCoinBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public ProgressViewHolder(@NonNull View itemView) {
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
