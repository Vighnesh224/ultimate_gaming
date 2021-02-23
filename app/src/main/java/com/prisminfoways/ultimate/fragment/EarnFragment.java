package com.prisminfoways.ultimate.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.TransactionCoinActivity;
import com.prisminfoways.ultimate.adapter.InnerPagerAdapter;
import com.prisminfoways.ultimate.databinding.FragmentEarnBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

/**
 * A simple {@link Fragment} subclass.
 */

public class EarnFragment extends Fragment {

    private static final String TAG = "EarnFragment";
    FragmentEarnBinding binding;
    Activity activity;
    StoreUserData storeUserData;

    private final String[] mTitles = {"Add Money", "Redeem", "Transaction"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    InnerPagerAdapter pagerAdapter;
    String totalCoins = "total_coins";
    String totalRupee = "total_rupee";
    String totalAvailRupee = "total_available_rupee";
    String totalWinning = "total_winning";
    public EarnFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getBalance();
            binding.redemTabLayout.setCurrentTab(0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_earn, container, false);
        activity = getActivity();

        storeUserData = new StoreUserData(activity);


        getBalance();

        mFragments.clear();
        mFragments.add(new AddMoneyFragment());
        mFragments.add(new RedeemFragment());
        mFragments.add(new TransactionRsFragment());

        binding.viewPagerRedeem.setOffscreenPageLimit(mFragments.size());
        pagerAdapter = new InnerPagerAdapter(getChildFragmentManager(), mFragments, mTitles);
        binding.viewPagerRedeem.setAdapter(pagerAdapter);
        binding.redemTabLayout.setViewPager(binding.viewPagerRedeem, mTitles);

        binding.viewPagerRedeem.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG,"page scroll");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG,"page select");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG,"page state changed");
            }
        });

        binding.crdBonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TransactionCoinActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    void openFragment(Fragment fragment) {


     String backStateName = fragment.getClass().getName();

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_transaction, fragment);
        fragmentTransaction.addToBackStack(backStateName);
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void onResume() {
        super.onResume();
        getBalance();
    }

    private void getBalance() {
        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject responseObj = jsonObject.getJSONObject("data");


                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_COINS, responseObj.getString(totalCoins));
                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_RUPEE, responseObj.getString(totalRupee));

                        if(responseObj.getString(totalAvailRupee) == null || responseObj.getString(totalAvailRupee).equals("") || responseObj.getString(totalAvailRupee).equals("null")){
                            storeUserData.setString(Constants.TOTAL_AVAILABLE_RUPEE,"0");
                        }else{
                            storeUserData.setString(Constants.TOTAL_AVAILABLE_RUPEE, responseObj.getString(totalAvailRupee));
                        }

                        if(responseObj.getString(totalRupee) == null || responseObj.getString(totalRupee).equals("") || responseObj.getString("total_rupee").equals("null")){
                            binding.txtDeposite.setText("0");
                        }else{
                            binding.txtDeposite.setText(responseObj.getString(totalRupee));
                        }

                        if(responseObj.getString(totalWinning) == null || responseObj.getString(totalWinning).equals("") || responseObj.getString(totalWinning).equals("null")){
                            binding.txtWinning.setText("0");
                        }else{
                            binding.txtWinning.setText(responseObj.getString(totalWinning));
                        }

                        if(responseObj.getString(totalCoins) == null || responseObj.getString(totalCoins).equals("") || responseObj.getString("total_coins").equals("null")){
                            binding.txtBonus.setText("0");
                        }else{
                            binding.txtBonus.setText(responseObj.getString(totalCoins));
                        }


                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object o) {
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }
}
