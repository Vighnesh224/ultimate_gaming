package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivitySettingBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    StoreUserData storeUserData;
    private String shareMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        storeUserData = new StoreUserData(this);
        final Uri longurl = Uri.parse("https://ultimategaming.prisminfoways.com");

        shareMsg = "Addicted to PUBG/FREE FIRE/PUBG LITE/LUDO KING/COD MOBILE? Want to make some cash out of it?? Try out Ultimate Gaming, an eSports Platform. Join Daily PUBG Matches & Get Rewards. Just Download the Ultimate Gaming App & Register using the Promo Code given below. \n\n" + "app link : " + longurl
                + "\n\nReferral Code : " + storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.USER_ID);

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingActivity.this,WebActivity.class);
                intent.putExtra("type","Privacy Policy");
                intent.putExtra("url",storeUserData.getString(Constants.PRIVACY_POLICY));
                startActivity(intent);

            }
        });

        binding.faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingActivity.this,WebActivity.class);
                intent.putExtra("type","Faq");
                intent.putExtra("url",storeUserData.getString(Constants.FAQ));
                startActivity(intent);

            }
        });

        binding.aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(SettingActivity.this,WebActivity.class);
                intent.putExtra("type","About Us");
                intent.putExtra("url",storeUserData.getString(Constants.ABOUT_US));
                startActivity(intent);

            }
        });


        binding.termsandcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(SettingActivity.this,WebActivity.class);
                intent.putExtra("type","Terms & Condition");
                intent.putExtra("url",storeUserData.getString(Constants.TERMS_CONDITION));
                startActivity(intent);
            }
        });

        binding.rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ultimategaming.prisminfoways.com")));

            }
        });

        binding.shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareMsg);
                startActivity(intent);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeUserData.clearData(SettingActivity.this);
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
            }
        });

        binding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, NotificationActivity.class));

            }
        });

    }
}