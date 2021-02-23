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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.PaginationListener;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.AddMoneyActivity;
import com.prisminfoways.ultimate.activity.CashoutActivity;
import com.prisminfoways.ultimate.adapter.TransactionRsAdapter;
import com.prisminfoways.ultimate.databinding.FragmentTransactionRsBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.OnLoadMoreListener;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.TransactionRsPojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

import static com.prisminfoways.ultimate.PaginationListener.PAGE_START;

/**
 * A simple {@link Fragment} subclass.
 */

public class TransactionRsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "TransactionRsFragment";
    FragmentTransactionRsBinding binding;
    Activity activity;
    TransactionRsAdapter adapter;
    ArrayList<Object> transactionList;
    CustomLoader customLoader;
    StoreUserData storeUserData;
    private boolean isDataAvilable = true;
    boolean onLoadCalled = false;
    private ArrayList tempTransactionHistoryList = new ArrayList<>();


    int itemCount = 0;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;


    public TransactionRsFragment() {
        Log.d(TAG,"transrs");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            getBalance();
            calltransactionRs();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction_rs, container, false);
        activity = getActivity();

        transactionList = new ArrayList<>();

        storeUserData = new StoreUserData(activity);
        customLoader = new CustomLoader(activity, false);


        binding.swipeRefresh.setOnRefreshListener(this);
        binding.rvTransactionCoin.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        binding.rvTransactionCoin.setLayoutManager(layoutManager);
        adapter = new TransactionRsAdapter(activity, new ArrayList<TransactionRsPojo.DataBean>(), binding.rvTransactionCoin);
        binding.rvTransactionCoin.setAdapter(adapter);

        binding.btnCashout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, CashoutActivity.class));
            }
        });

        binding.btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, AddMoneyActivity.class));
            }
        });

        calltransactionRs();
        getBalance();

        binding.rvTransactionCoin.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                calltransactionRs();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        return binding.getRoot();
    }

    private void calltransactionRs() {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.PAGE, String.valueOf(currentPage));

        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_TRANSACTION_RS, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();

                TransactionRsPojo transactionPojo = new Gson().fromJson(o.toString(), TransactionRsPojo.class);


                if (transactionPojo.getStatus().equals("1")) {
                    if(transactionPojo.getData().isEmpty() || transactionPojo.getData() == null){
                        Log.d(TAG,"size 0");
                    }else{
                        if(currentPage != 0){
                            if (currentPage != PAGE_START) adapter.removeLoading();
                            else {
                                Log.d(TAG,"page 0");
                            }
                        }

                        if(currentPage == 0){
                            adapter.clear();
                        }

                        adapter.addItems(transactionPojo.getData());
                        binding.swipeRefresh.setRefreshing(false);
                        // check weather is last page or not


                        if(currentPage !=0){
                            int scrollPosition = transactionPojo.getData().size();


                            if (transactionPojo.getData().size() <= scrollPosition) {
                                adapter.addLoading();
                            } else {
                                isLastPage = true;
                            }
                            isLoading = false;
                        }
                    }

                }else{
                    binding.swipeRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
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

                                        calltransactionRs();
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
    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        adapter.clear();
        calltransactionRs();
    }

}
