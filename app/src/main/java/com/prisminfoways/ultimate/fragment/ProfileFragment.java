package com.prisminfoways.ultimate.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.CustomerSupportActivity;
import com.prisminfoways.ultimate.activity.LoginActivity;
import com.prisminfoways.ultimate.activity.MyStatisticsActivity;
import com.prisminfoways.ultimate.activity.NotificationActivity;
import com.prisminfoways.ultimate.activity.ProfileActivity;
import com.prisminfoways.ultimate.activity.ReferAndEarnActivity;
import com.prisminfoways.ultimate.activity.SettingActivity;
import com.prisminfoways.ultimate.activity.TopPlayerActivity;
import com.prisminfoways.ultimate.activity.WalletActivity;
import com.prisminfoways.ultimate.databinding.FragmentProfileBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

/**
 * A simple {@link Fragment} subclass.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ProfileFragment";
    FragmentProfileBinding binding;
    Activity activity;
    StoreUserData storeUserData;
    CustomLoader customLoader;
    private String aboutUs = "aboutUs";
    private String joinRoom = "joinRoom";
    private String cusomSupport = "customSupport";

    public ProfileFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        activity = getActivity();

        storeUserData = new StoreUserData(activity);

        customLoader = new CustomLoader(activity, false);

        binding.wallet.setOnClickListener(this);
        binding.referEarn.setOnClickListener(this);
        binding.myProfile.setOnClickListener(this);
        binding.myStatistics.setOnClickListener(this);
        binding.logout.setOnClickListener(this);
        binding.updates.setOnClickListener(this);
        binding.topPlayers.setOnClickListener(this);
        binding.aboutUs.setOnClickListener(this);
        binding.customSupport.setOnClickListener(this);
        binding.howItWorks.setOnClickListener(this);
        binding.joinPubgRoom.setOnClickListener(this);
        binding.settings.setOnClickListener(this);
        binding.customersupport.setOnClickListener(this);
        binding.notification.setOnClickListener(this);

        binding.userName.setText(storeUserData.getString(Constants.USER_NAME));
        binding.userEmail.setText(storeUserData.getString(Constants.USER_EMAIL));



        callgetUserStatus();
        getBalance();

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.wallet:
                startActivity(new Intent(activity, WalletActivity.class));
                break;

            case R.id.refer_earn:
                startActivity(new Intent(activity, ReferAndEarnActivity.class));
                break;

            case R.id.my_profile:
                startActivity(new Intent(activity, ProfileActivity.class));
                break;

            case R.id.my_statistics:
                startActivity(new Intent(activity, MyStatisticsActivity.class));
                break;




            case R.id.updates:
                startActivity(new Intent(activity, NotificationActivity.class));
                break;

            case R.id.top_players:
                startActivity(new Intent(activity, TopPlayerActivity.class));
                break;

            case R.id.about_us:

                openUrl(aboutUs);
                break;

            case R.id.join_pubg_room:

                openUrl(joinRoom);
                break;

            case R.id.how_it_works:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(storeUserData.getString(Constants.HOW_IT_WORK))));
                } catch (Exception e) {
                    Log.d(TAG, "You don't have any app to handle this action");
                    e.printStackTrace();
                }
                break;

            case R.id.custom_support:

                openUrl(cusomSupport);
                break;

            case R.id.logout:
                storeUserData.clearData(activity);
                startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
                break;
            case R.id.settings:
                startActivity(new Intent(activity, SettingActivity.class));
                break;
            case R.id.customersupport:


                Intent intent = new Intent(activity, CustomerSupportActivity.class);

                startActivity(intent);
                break;
            case R.id.notification:
                startActivity(new Intent(activity, NotificationActivity.class));
                break;
            default: break;
        }
    }


    private void openUrl(String url) {

        String packageNAME = "com.android.chrome";

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        builder.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary));

        if (url.equals(aboutUs)){
            customTabsIntent.intent.setData(Uri.parse(storeUserData.getString(Constants.ABOUT_US)));
        } else if (url.equals(cusomSupport)){
            customTabsIntent.intent.setData(Uri.parse(storeUserData.getString(Constants.CUSTOMER_SUPPORT)));
        } else if (url.equals(joinRoom)){
            customTabsIntent.intent.setData(Uri.parse(storeUserData.getString(Constants.HOW_TO_JOIN_ROOM)));
        }

        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(customTabsIntent.intent, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo resolveInfo : resolveInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            if (TextUtils.equals(packageName, packageNAME))
                customTabsIntent.intent.setPackage(packageNAME);
        }

        if (url.equals(aboutUs)){
            customTabsIntent.launchUrl(activity, Uri.parse(storeUserData.getString(Constants.ABOUT_US)));
        } else if (url.equals(cusomSupport)){
            customTabsIntent.launchUrl(activity, Uri.parse(storeUserData.getString(Constants.CUSTOMER_SUPPORT)));
        } else if (url.equals(joinRoom)){
            customTabsIntent.launchUrl(activity, Uri.parse(storeUserData.getString(Constants.HOW_TO_JOIN_ROOM)));
        }
    }

    private void callgetUserStatus() {


        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_USER_STATUS, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject responsObj = jsonObject.getJSONObject("data");

                        binding.totalMatchPlayed.setText(responsObj.getString("match_join"));
                        binding.totalKills.setText(responsObj.getString("kill_count"));
                        binding.totalAmountWon.setText(responsObj.getString("total_win"));
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

    @Override
    public void onResume() {
        super.onResume();
        getBalance();
    }

    private void getBalance() {

        customLoader.showLoader();

        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
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
                        binding.totalRs.setText("₹" + responseObj.getString("total_rupee"));
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
}
