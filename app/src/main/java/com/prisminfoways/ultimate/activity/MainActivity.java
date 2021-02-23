package com.prisminfoways.ultimate.activity;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.onesignal.OneSignal;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.adapter.ViewPagerAdapter;
import com.prisminfoways.ultimate.databinding.ActivityMainBinding;
import com.prisminfoways.ultimate.fragment.EarnFragment;
import com.prisminfoways.ultimate.fragment.GameListFragment;
import com.prisminfoways.ultimate.fragment.GiftFragment;
import com.prisminfoways.ultimate.fragment.ProfileFragment;
import com.prisminfoways.ultimate.fragment.ResultFragment;
import com.prisminfoways.ultimate.fragment.TransactionCoinFragment;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.RewardListPojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public ActivityMainBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;

    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        storeUserData.setBoolean(Constants.IS_LOGIN, true);

        getBalance();
        callGetRewardApi();
        checkInternetConnection();

        OneSignal.sendTag("user_id", storeUserData.getString(Constants.USER_ID));

        OneSignal.setEmail(storeUserData.getString(Constants.USER_EMAIL));

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ResultFragment(), "Percentage");
        adapter.addFrag(new GiftFragment(), "Gift");
        adapter.addFrag(new GameListFragment(), "GameList");
        adapter.addFrag(new EarnFragment(), "Wallet");
        adapter.addFrag(new ProfileFragment(), "Profile");
        binding.mainPager.setAdapter(adapter);

        binding.mainPager.setOffscreenPageLimit(1);
        binding.mainPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.mainPager);

        binding.mainPager.setCurrentItem(2);

        binding.llPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mainPager.setCurrentItem(0);
            }
        });

        binding.llGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mainPager.setCurrentItem(1);
            }
        });

        binding.ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mainPager.setCurrentItem(2);
            }
        });

        binding.llWallate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mainPager.setCurrentItem(3);
            }
        });

        binding.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mainPager.setCurrentItem(4);
            }
        });

        binding.mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG,"page scrolled");
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    binding.ivHome.setImageResource(R.drawable.ic_percentage_select);
                    binding.ivMe.setImageResource(R.drawable.ic_profile);
                    binding.ivSearch.setImageResource(R.drawable.ic_gift);
                    binding.ivChat.setImageResource(R.drawable.ic_wallate);
                    binding.ivUpload.setImageResource(R.drawable.ic_gamelist);
                } else if (position == 1) {
                    binding.ivSearch.setImageResource(R.drawable.ic_gift_select);
                    binding.ivHome.setImageResource(R.drawable.ic_percentage);
                    binding.ivMe.setImageResource(R.drawable.ic_profile);
                    binding.ivChat.setImageResource(R.drawable.ic_wallate);
                    binding.ivUpload.setImageResource(R.drawable.ic_gamelist);
                }
                else if (position == 2) {
                    binding.ivUpload.setImageResource(R.drawable.ic_gamelist);
                    binding.ivSearch.setImageResource(R.drawable.ic_gift);
                    binding.ivHome.setImageResource(R.drawable.ic_percentage);
                    binding.ivMe.setImageResource(R.drawable.ic_profile);
                    binding.ivChat.setImageResource(R.drawable.ic_wallate);

                } else if (position == 3) {
                    binding.ivMe.setImageResource(R.drawable.ic_profile);
                    binding.ivHome.setImageResource(R.drawable.ic_percentage);
                    binding.ivChat.setImageResource(R.drawable.ic_wallate_select);
                    binding.ivSearch.setImageResource(R.drawable.ic_gift);
                    binding.ivUpload.setImageResource(R.drawable.ic_gamelist);
                } else {
                    binding.ivChat.setImageResource(R.drawable.ic_wallate);
                    binding.ivHome.setImageResource(R.drawable.ic_percentage);
                    binding.ivSearch.setImageResource(R.drawable.ic_gift);
                    binding.ivMe.setImageResource(R.drawable.ic_profile_select);
                    binding.ivUpload.setImageResource(R.drawable.ic_gamelist);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG,"page scrolled state change");
            }
        });


    }

    private void getBalance() {

        customLoader.showLoader();

        new AddShow().handleCall(MainActivity.this, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();

                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject responseObj = jsonObject.getJSONObject("data");

                        storeUserData.setString(Constants.TOTAL_COINS, responseObj.getString("total_coins"));
                        storeUserData.setString(Constants.TOTAL_RUPEE, responseObj.getString("total_rupee"));
                        storeUserData.setString(Constants.TOTAL_AVAILABLE_RUPEE, responseObj.getString("total_available_rupee"));
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
        callGetRewardApi();
    }




    private void checkInternetConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                CustomLoader.showErrorDialog(MainActivity.this, "Please check your internet connection");
            }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_transaction);
        if (f instanceof TransactionCoinFragment)
        {
            if(f.getChildFragmentManager().getBackStackEntryCount()>0){
                f.getChildFragmentManager().popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }

    private void callGetRewardApi() {

        HashMap<String, String> map = new HashMap<>();

        new AddShow().handleCall(this, add.Native.com.admodule.Constants.TAG_GET_REWARD_LIST, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                Log.d(TAG, "onLoaded: "+o.toString());
                RewardListPojo rewardListPojo = new Gson().fromJson(o.toString(),RewardListPojo.class);

                if(rewardListPojo.getStatus().equals("1")){


                    for(int i = 0 ; i<rewardListPojo.getData().size();i++){
                        if(rewardListPojo.getData().get(i).getTitle().equalsIgnoreCase("Video reward")){
                            storeUserData.setString(Constants.WATCH_VIDEO_TITLE,rewardListPojo.getData().get(i).getTitle());
                            storeUserData.setString(Constants.WATCH_VIDEO_COIN, rewardListPojo.getData().get(i).getCoins());
                        }
                    }
                }
            }

            @Override
            public void onFailed(Object o) {
                Log.d(TAG, "onFailed: "+o.toString());
            }
        }, false);

    }
}
