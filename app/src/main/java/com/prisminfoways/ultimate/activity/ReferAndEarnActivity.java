package com.prisminfoways.ultimate.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityReferAndEarnBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;

public class ReferAndEarnActivity extends AppCompatActivity {

    private static final String TAG = "ReferAndEarnActivity";
    ActivityReferAndEarnBinding binding;
    StoreUserData storeUserData;
    private String shareMsg;
    String textPlain = "text/plain";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refer_and_earn);

        storeUserData = new StoreUserData(this);

        binding.userCode.setText(storeUserData.getString(Constants.USER_ID));

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        final Uri longurl = Uri.parse("https://ultimategaming.prisminfoways.com");
        shareMsg = "Addicted to PUBG/FREE FIRE/PUBG LITE/LUDO KING/COD MOBILE? Want to make some cash out of it??" +
                " Try out Ultimate Gaming, an eSports Platform. Join Daily PUBG Matches & Get Rewards. " +
                "Just Download the Ultimate Gaming App & Register using the Promo Code given below. \n\n" + "app link : " + longurl
                + "\n\nMy Refer Code : " + storeUserData.getString(Constants.USER_ID);

        binding.shareWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setType(textPlain);
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setPackage("com.whatsapp");
                    intent.putExtra(Intent.EXTRA_TEXT, shareMsg);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        binding.shareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setType(textPlain);
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setPackage("com.facebook.katana");
                    intent.putExtra(Intent.EXTRA_TEXT, shareMsg);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        binding.shareGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setType(textPlain);
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setPackage("com.google.android.apps.plus");
                    intent.putExtra(Intent.EXTRA_TEXT, shareMsg);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onClick: App not found");
                }
            }
        });

        binding.shareCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType(textPlain);
                intent.putExtra(Intent.EXTRA_TEXT, shareMsg);
                startActivity(intent);
            }
        });
    }
}
