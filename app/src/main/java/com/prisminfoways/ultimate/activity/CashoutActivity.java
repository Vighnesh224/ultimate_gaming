package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.TabEntity;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityCashoutBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

public class CashoutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final String[] mTitles = {"", ""};
    private final Integer[] mSelectedTabIcon = {R.drawable.paytm, R.drawable.paypal};
    private static final String TAG = "CashoutActivity";
    ActivityCashoutBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cashout);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.amountSpinner.setOnItemSelectedListener(this);
        List<String> amountList;
        amountList = new ArrayList<>();

        amountList.add("20");
        amountList.add("50");
        amountList.add("100");
        amountList.add("200");
        amountList.add("500");
        amountList.add("1000");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CashoutActivity.this, android.R.layout.simple_list_item_1, amountList);

        binding.amountSpinner.setSelection(0);
        binding.amountSpinner.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mTabEntities.add(new TabEntity(mTitles[0], mSelectedTabIcon[0], mSelectedTabIcon[0]));
        mTabEntities.add(new TabEntity(mTitles[1], mSelectedTabIcon[1], mSelectedTabIcon[1]));

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
                Log.d(TAG,""+position);
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.number.getText().toString().trim().isEmpty()) {
                    if (binding.redeemTab.getCurrentTab() == 0) {

                        CustomLoader.showErrorDialog(CashoutActivity.this, "Please enter paytm mobile number");
                    } else if (binding.redeemTab.getCurrentTab() == 1) {

                        CustomLoader.showErrorDialog(CashoutActivity.this, "Please enter paypal address");
                    }
                }
                else {

                    callRedeemApi(binding.number.getText().toString().trim(), amount);
                }
            }
        });
    }

    private void callRedeemApi(String paytmno, String amount) {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.REDEEM_DETAIL, paytmno);
        map.put(Constants.REDEEM_AMOUNT, amount);

        new AddShow().handleCall(CashoutActivity.this, add.Native.com.admodule.Constants.TAG_MAKE_REQUEST, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {

                        CustomLoader.showErrorDialog(CashoutActivity.this, jsonObject.getString("msg"));
                        getBalance();
                    } else {

                        CustomLoader.showErrorDialog(CashoutActivity.this, jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        amount = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(TAG,"nothingselect");
    }

    private void getBalance() {

        customLoader.showLoader();


        new AddShow().handleCall(CashoutActivity.this, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();


                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject responseObj = jsonObject.getJSONObject("data");

                        storeUserData.setBoolean("add_balance", false);

                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_COINS, responseObj.getString("total_coins"));
                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_RUPEE, responseObj.getString("total_rupee"));
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

}
