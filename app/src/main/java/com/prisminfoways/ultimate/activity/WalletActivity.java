package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityWalletBinding;
import com.prisminfoways.ultimate.fragment.TransactionCoinFragment;
import com.prisminfoways.ultimate.fragment.TransactionRsFragment;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

public class WalletActivity extends AppCompatActivity {

    private static final String TAG = "WalletActivity";
    ActivityWalletBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getBalance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, new TransactionRsFragment())
                .commitAllowingStateLoss();


        binding.transactionCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.transactionRs.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_white));
                binding.transactionRsTxt.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                binding.totalRupees.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                binding.transactionCoin.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                binding.transactionCoinTxt.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.color_white));
                binding.totalCoins.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.color_white));

                openFragment(new TransactionCoinFragment());


            }
        });

        binding.transactionRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.transactionRs.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                binding.transactionRsTxt.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.color_white));
                binding.totalRupees.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.color_white));

                binding.transactionCoin.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_white));
                binding.transactionCoinTxt.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                binding.totalCoins.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                openFragment(new TransactionRsFragment());


            }
        });

    }

    private void openFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commitAllowingStateLoss();
    }

    private void getBalance() {

        customLoader.showLoader();

        new AddShow().handleCall(WalletActivity.this, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();


                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject responseObj = jsonObject.getJSONObject("data");



                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_COINS, responseObj.getString("total_coins"));
                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_RUPEE, responseObj.getString("total_rupee"));
                        storeUserData.setString(Constants.TOTAL_AVAILABLE_RUPEE, responseObj.getString("total_available_rupee"));

                        binding.totalCoins.setText(responseObj.getString("total_coins"));
                        binding.totalRupees.setText("₹" + responseObj.getString("total_rupee"));

                    }

                } catch (JSONException e) {
                    customLoader.dismissLoader();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getBalance();
    }
}
