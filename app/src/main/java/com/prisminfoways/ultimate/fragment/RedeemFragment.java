package com.prisminfoways.ultimate.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.TabEntity;
import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.adapter.RedeemOptionListAdapter;
import com.prisminfoways.ultimate.databinding.FragmentRedeemBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.RedeemOptionPojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

/**
 * A simple {@link Fragment} subclass.
 */
public class RedeemFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "OngoingFragment";
    FragmentRedeemBinding binding;
    Activity activity;
    StoreUserData storeUserData;
    ArrayList<Object> onGoingMatchList;
    CustomLoader customLoader;
    String gameID;

    private final String[] mTitles = {""};
    private final Integer[] mSelectedTabIcon = {R.drawable.paytm};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    private String amount;
    RedeemOptionListAdapter addMoneyListAdapter;

    public RedeemFragment() {
    }

    public RedeemFragment(String gameID) {
        this.gameID = gameID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_redeem, container, false);
        activity = getActivity();
        List<String> amountList;
        storeUserData = new StoreUserData(activity);
        customLoader = new CustomLoader(activity, false);

        binding.amountSpinner.setOnItemSelectedListener(this);

        amountList = new ArrayList<>();

        amountList.add("100");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, amountList);

        binding.amountSpinner.setSelection(0);
        binding.amountSpinner.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mTabEntities.add(new TabEntity(mTitles[0], mSelectedTabIcon[0], mSelectedTabIcon[0]));

        binding.redeemTab.setTabData(mTabEntities);

        binding.redeemTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                binding.number.setText("");
                if (position == 0) {
                    binding.msg.setText("Send to paytm");
                    binding.number.setInputType(InputType.TYPE_CLASS_PHONE);
                    binding.number.setHint("Enter Paytm Number");
                } else if (position == 1) {
                    binding.msg.setText("Send to paypal");
                    binding.number.setInputType(InputType.TYPE_CLASS_TEXT);
                    binding.number.setHint("Enter Paypal Address");
                }
            }

            @Override
            public void onTabReselect(int position) {
                Log.d(TAG,"reselect");
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.number.getText().toString().trim().isEmpty()) {
                    if (binding.redeemTab.getCurrentTab() == 0) {
                        CustomLoader.showErrorDialog(activity, "Please enter paytm mobile number");
                    } else if (binding.redeemTab.getCurrentTab() == 1) {
                        CustomLoader.showErrorDialog(activity, "Please enter paypal address");
                    }
                }



                else {
                    callRedeemApi(binding.number.getText().toString().trim(), amount);
                }
            }
        });

        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        binding.recycleAddmoneyList.setLayoutManager(layoutmanager);

        getAddMoneyList();

        return binding.getRoot();
    }

    private void getAddMoneyList() {
        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_GET_REDEEM_OPTION, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {

                Log.d(TAG, "onLoaded1: " + o.toString());

                RedeemOptionPojo addMoneyListPojo = new Gson().fromJson(o.toString(),RedeemOptionPojo.class);
                if(addMoneyListPojo.getStatus().equals("1")){
                    addMoneyListAdapter = new RedeemOptionListAdapter(getActivity(),addMoneyListPojo.getData());
                    binding.recycleAddmoneyList.setAdapter(addMoneyListAdapter);
                }

            }

            @Override
            public void onFailed(Object o) {

                Log.d(TAG, "onFailed1: " + o.toString());
            }
        }, false);
    }

    private void callRedeemApi(String paytmno, String amount) {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.REDEEM_DETAIL, paytmno);
        map.put(Constants.REDEEM_AMOUNT, amount);

        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_MAKE_REQUEST, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded2: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        CustomLoader.showErrorDialog(activity, jsonObject.getString("msg"));
                        getBalance();
                    } else {
                        CustomLoader.showErrorDialog(activity, jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onFailed2: " + o.toString());
            }
        }, false);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        amount = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(TAG,"not selected");
    }

    private void getBalance() {

        customLoader.showLoader();

        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();


                Log.d(TAG, "onLoaded3: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject responseObj = jsonObject.getJSONObject("data");

                        storeUserData.setBoolean("add_balance", false);

                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_COINS, responseObj.getString("total_coins"));
                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_RUPEE, responseObj.getString("total_rupee"));


                    }

                } catch (JSONException e) {
                    customLoader.dismissLoader();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onFailed3: " + o.toString());
            }
        }, false);
    }
}
