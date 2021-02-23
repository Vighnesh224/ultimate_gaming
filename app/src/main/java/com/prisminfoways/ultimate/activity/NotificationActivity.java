package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityNotificationBinding;
import com.prisminfoways.ultimate.databinding.AdapterNotificationBinding;
import com.prisminfoways.ultimate.pojo.NotificationPojo;
import com.wang.avi.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;

public class NotificationActivity extends AppCompatActivity {

    private static final String TAG = "NotificationActivity";
    ActivityNotificationBinding binding;
    CustomLoader customLoader;
    private List<Object> notificationList;
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        customLoader = new CustomLoader(this, false);
        notificationList = new ArrayList<>();

        binding.rvNotification.setLayoutManager(new LinearLayoutManager(this));

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                binding.swipeRefresh.setRefreshing(true);
                callNotificationApi();
            }
        });

        callNotificationApi();

    }

    private void callNotificationApi() {

        customLoader.showLoader();

        binding.swipeRefresh.setRefreshing(true);

        new AddShow().handleCall(NotificationActivity.this, Constants.TAG_NOTIFICATION, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                binding.swipeRefresh.setRefreshing(false);
                Log.d(TAG, "onLoaded: " + o.toString());

                NotificationPojo notificationPojo = new Gson().fromJson(o.toString(), NotificationPojo.class);

                if (notificationPojo.getStatus().equals("1")) {
                    if (!notificationPojo.getData().isEmpty()) {
                        notificationList.clear();
                        notificationList.addAll(notificationPojo.getData());
                        adapter = new NotificationAdapter();
                        binding.rvNotification.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        notificationList.clear();
                        Log.d(TAG, "onLoaded: No Data Available");
                    }
                } else {
                    Log.d(TAG, "0");
                }
            }

            @Override
            public void onFailed(Object o) {
                binding.swipeRefresh.setRefreshing(false);
                customLoader.dismissLoader();
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }

    public class NotificationAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(NotificationActivity.this).inflate(R.layout.adapter_notification, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            if (holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder) holder;
                final NotificationPojo.DataBean notificationPojo = (NotificationPojo.DataBean) notificationList.get(i);

                viewHolder.binding.txtNotification.setText(notificationPojo.getTitle());
                viewHolder.binding.createdDate.setText(notificationPojo.getCreated_date());

                viewHolder.binding.txtNotification.setMovementMethod(LinkMovementMethod.getInstance());

                viewHolder.binding.crdNotification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NotificationActivity.this,NotificationDetailsActivity.class);
                        intent.putExtra("title",notificationPojo.getTitle());
                        intent.putExtra("text",notificationPojo.getText());
                        intent.putExtra("image",notificationPojo.getImage());
                        intent.putExtra("external_link",notificationPojo.getExternal_link());
                        startActivity(intent);
                    }
                });

            }
        }

        @Override
        public int getItemCount() {
            return notificationList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AdapterNotificationBinding binding;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }
        }
    }
}
