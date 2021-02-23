package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityProfileBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    StoreUserData storeUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        storeUserData = new StoreUserData(this);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.userName.setText(storeUserData.getString(Constants.USER_NAME));
        binding.userEmail.setText(storeUserData.getString(Constants.USER_EMAIL));
        binding.userPhone.setText(storeUserData.getString(Constants.USER_PHONE));



        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
            }
        });

    }
}
