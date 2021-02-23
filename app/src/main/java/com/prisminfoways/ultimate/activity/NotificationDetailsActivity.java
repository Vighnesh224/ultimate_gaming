package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityNotificationDetailsBinding;

public class NotificationDetailsActivity extends AppCompatActivity {

    ActivityNotificationDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_details);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Glide.with(this).load(getIntent().getStringExtra("image")).into(binding.imgBanner);

        binding.txtNotification.setText(getIntent().getStringExtra("title"));
        binding.txtNotificationDes.setText(getIntent().getStringExtra("text"));
    }
}