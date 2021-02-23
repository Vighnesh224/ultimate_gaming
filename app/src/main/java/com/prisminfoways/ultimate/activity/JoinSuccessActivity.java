package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityJoinSuccessBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;

public class JoinSuccessActivity extends AppCompatActivity {

    ActivityJoinSuccessBinding binding;
    StoreUserData storeUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_success);

        storeUserData = new StoreUserData(this);

        storeUserData.setBoolean("is_joined", true);

        binding.detail.setText(getIntent().getStringExtra("match_detail"));

        binding.txtDetails.setText(getIntent().getStringExtra("is_match"));



        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(JoinSuccessActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}
