package com.prisminfoways.ultimate.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.NotificationActivity;
import com.prisminfoways.ultimate.adapter.OnGoingMatchAdapter;
import com.prisminfoways.ultimate.databinding.FragmentGiftBinding;
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

public class GiftFragment extends Fragment {

    private static final String TAG = "OngoingFragment";
    FragmentGiftBinding binding;
    Activity activity;
    StoreUserData storeUserData;
    ArrayList<Object> onGoingMatchList;
    OnGoingMatchAdapter adapter;

    private String shareMsg;


    public GiftFragment() {
        Log.d(TAG,"gift");
    }

@SuppressWarnings("deprecation")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
            Log.d(TAG,"visible");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gift, container, false);
        activity = getActivity();

        storeUserData = new StoreUserData(activity);

        binding.txtPromoCode.setText(storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.USER_ID));


        final Uri longurl = Uri.parse("https://ultimategaming.prisminfoways.com");
        shareMsg = "Addicted to PUBG/FREE FIRE/PUBG LITE/LUDO KING/COD MOBILE? Want to make some cash out of it?? Try out Ultimate Gaming, an eSports Platform. Join Daily PUBG Matches & Get Rewards. Just Download the Ultimate Gaming App & Register using the Promo Code given below. \n\n" + "app link : " + longurl
                + "\n\nMy Refer Code : " + storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.USER_ID);


        binding.shareCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareMsg);
                startActivity(intent);
            }
        });

        binding.imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    void getBalance() {

        binding.swipeRefresh.setRefreshing(true);

        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                binding.swipeRefresh.setRefreshing(false);

                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        Log.d(TAG,""+jsonObject.get("data"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object o) {
                binding.swipeRefresh.setRefreshing(false);
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }

}
