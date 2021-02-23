package com.prisminfoways.ultimate.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.CashoutActivity;
import com.prisminfoways.ultimate.adapter.TransactionCoinAdapter;
import com.prisminfoways.ultimate.databinding.FragmentTransactionCoinBinding;
import com.prisminfoways.ultimate.helper.OnLoadMoreListener;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.TransactionCoinPojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;

/**
 * A simple {@link Fragment} subclass.
 */

public class TransactionCoinFragment extends Fragment {

    private static final String TAG = "TransactionCoinFragment";
    FragmentTransactionCoinBinding binding;
    Activity activity;
    private int page = 0;
    private ArrayList<Object> transactionCoinList;
    TransactionCoinAdapter adapter;
    CustomLoader customLoader;
    private boolean isDataAvilable = true;
    boolean onLoadCalled = false;
    StoreUserData storeUserData;
    private ArrayList tempTransactionHistoryList = new ArrayList<>();

    @SuppressWarnings("deprecation")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            getBalance();
        }
    }

    public TransactionCoinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction_coin, container, false);
        activity = getActivity();

        storeUserData = new StoreUserData(activity);
        customLoader = new CustomLoader(activity, false);

        transactionCoinList = new ArrayList<>();
        binding.rvTransactionCoin.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new TransactionCoinAdapter(activity, tempTransactionHistoryList, binding.rvTransactionCoin);
        binding.rvTransactionCoin.setAdapter(adapter);

        binding.cashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, CashoutActivity.class));
            }
        });

        callTransactionCoinApi(page);
        getBalance();

        return binding.getRoot();
    }
    public void onBackPressed() {
              getChildFragmentManager().popBackStack();
    }
    private void callTransactionCoinApi(int page) {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(com.prisminfoways.ultimate.helper.Constants.PAGE, String.valueOf(page));

        new AddShow().handleCall(activity, Constants.TAG_TRANSACTION_COIN, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());
                TransactionCoinPojo transactionCoinPojo = new Gson().fromJson(o.toString(), TransactionCoinPojo.class);
                tempTransactionHistoryList.clear();

                if (transactionCoinPojo.getStatus().equals("1")) {
                    transactionCoinList.addAll(transactionCoinPojo.getData());
                    tempTransactionHistoryList.addAll(transactionCoinList);
                    adapter.notifyDataSetChanged();

                    if (transactionCoinPojo.getData().isEmpty()) {
                        isDataAvilable = false;
                    } else {
                        isDataAvilable = true;
                    }

                    enableLoadListener();
                    adapter.setLoaded();
                    binding.rvTransactionCoin.setNestedScrollingEnabled(true);
                } else {
                    Log.d(TAG,"0");
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }

    public void enableLoadListener() {

        if (isDataAvilable) {
            adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoad() {
                    if (isDataAvilable) {
                        if (!tempTransactionHistoryList.isEmpty()) {
                            tempTransactionHistoryList.add(null);
                            adapter.notifyItemInserted(tempTransactionHistoryList.size() - 1);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        tempTransactionHistoryList.remove(tempTransactionHistoryList.size() - 1);
                                        adapter.notifyItemRemoved(tempTransactionHistoryList.size());
                                        page++;
                                        callTransactionCoinApi(page);
                                        onLoadCalled = true;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 3000);
                        } else {
                            Log.d(TAG,"size 0");
                        }
                    }
                }
            });
        }
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
                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_AVAILABLE_RUPEE, responseObj.getString("total_available_rupee"));
                        binding.totalCoins.setText(responseObj.getString("total_coins"));


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
