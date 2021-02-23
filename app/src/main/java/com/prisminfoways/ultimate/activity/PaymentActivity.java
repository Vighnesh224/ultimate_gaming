package com.prisminfoways.ultimate.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityPaymentBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.GenerateCheckSumPojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;
import timber.log.Timber;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = "PaymentActivity";
    ActivityPaymentBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;
    private String amount;

    add.Native.com.admodule.StoreUserData storeUserData1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        storeUserData1 = new add.Native.com.admodule.StoreUserData(this);

        amount = getIntent().getStringExtra("amount");

        callGenerateChecksumApi(amount);

    }

    private void callGenerateChecksumApi(String amount) {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(com.prisminfoways.ultimate.helper.Constants.AMOUNT, amount);
        map.put(com.prisminfoways.ultimate.helper.Constants.CUSTOMER_ID, storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.USER_ID));

        new AddShow().handleCall(PaymentActivity.this, Constants.TAG_GENRATECHECKSUM, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Timber.d("%s", o.toString());

                GenerateCheckSumPojo checkSumPojo = new Gson().fromJson(o.toString(), GenerateCheckSumPojo.class);

                if (checkSumPojo.getStatus().equals("1")) {
                    storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.CHECKSUMHASH, checkSumPojo.getData().getCHECKSUMHASH());
                    storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.ORDER_ID, checkSumPojo.getData().getORDER_ID());
                    storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.PAYT_STATUS, checkSumPojo.getData().getPayt_STATUS());
                    storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.MID, checkSumPojo.getData().getMID());
                    storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.INDUSTRY_TYPE_ID, checkSumPojo.getData().getINDUSTRY_TYPE_ID());
                    storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.PAYTM_WEBSITE, checkSumPojo.getData().getWEBSITE());
                    storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.CALLBACK_URL, checkSumPojo.getData().getCALLBACK_URL());
                    storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.CHANNEL_ID, checkSumPojo.getData().getCHANNEL_ID());
                    preparePaytmParms(checkSumPojo);
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Timber.d("%s", o.toString());
            }
        }, false);
    }

    public void preparePaytmParms(GenerateCheckSumPojo checkSumPojo) {

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", checkSumPojo.getData().getMID());
        paramMap.put("ORDER_ID", checkSumPojo.getData().getORDER_ID());
        paramMap.put("CUST_ID", storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.USER_ID));
        paramMap.put("CHANNEL_ID", checkSumPojo.getData().getCHANNEL_ID());
        paramMap.put("TXN_AMOUNT", amount);
        paramMap.put("WEBSITE", checkSumPojo.getData().getWEBSITE());
        paramMap.put("INDUSTRY_TYPE_ID", checkSumPojo.getData().getINDUSTRY_TYPE_ID());
        paramMap.put("CALLBACK_URL", checkSumPojo.getData().getCALLBACK_URL());
        paramMap.put("CHECKSUMHASH", checkSumPojo.getData().getCHECKSUMHASH());





        PaytmOrder order = new PaytmOrder(paramMap);




        PaytmPGService service = PaytmPGService.getProductionService();

        service.initialize(order, null);

        service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle inResponse) {

                Timber.d("PayTM Transaction Response: %s", inResponse.toString());
                storeUserData1.setString("status", inResponse.getString("STATUS"));
                storeUserData1.setString("checksumhash", inResponse.getString("CHECKSUMHASH"));
                storeUserData1.setString("bankname", inResponse.getString("BANKNAME"));
                storeUserData1.setString("orderid", inResponse.getString("ORDERID"));
                storeUserData1.setString("txnamount", inResponse.getString("TXNAMOUNT"));
                storeUserData1.setString("txndate", inResponse.getString("TXNDATE"));
                storeUserData1.setString("mid", inResponse.getString("MID"));
                storeUserData1.setString("txnid", inResponse.getString("TXNID"));
                storeUserData1.setString("respcode", inResponse.getString("RESPCODE"));
                storeUserData1.setString("paymentmode", inResponse.getString("PAYMENTMODE"));
                storeUserData1.setString("banktxnid", inResponse.getString("BANKTXNID"));
                storeUserData1.setString("currency", inResponse.getString("CURRENCY"));
                storeUserData1.setString("gatewayname", inResponse.getString("GATEWAYNAME"));
                storeUserData1.setString("respmsg", inResponse.getString("RESPMSG"));



                callVarificationApi(inResponse);
            }

            @Override
            public void networkNotAvailable() {
                Timber.d("Network not available");
                finish();
            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {
                Timber.e("clientAuthenticationFailed: %s", inErrorMessage);
                CustomLoader.showErrorDialog(PaymentActivity.this, inErrorMessage);
                finish();
            }

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {
                Timber.d("someUIErrorOccurred : %s", inErrorMessage);
                CustomLoader.showErrorDialog(PaymentActivity.this, inErrorMessage);

                finish();
            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                Timber.e("onErrorLoadingWebPage: %d | %s | %s", iniErrorCode, inErrorMessage, inFailingUrl);
                CustomLoader.showErrorDialog(PaymentActivity.this, inErrorMessage);
                finish();
            }

            @Override
            public void onBackPressedCancelTransaction() {

                finish();
            }

            @Override
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Timber.e("onTransactionCancel: %s | %s", inErrorMessage, inResponse);
                CustomLoader.showErrorDialog(PaymentActivity.this, inErrorMessage);
                finish();
            }
        });
    }

    private void callVarificationApi(Bundle response) {


        new AddShow().handleCall(PaymentActivity.this, Constants.TAG_VERIFYCHECKSUM, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                Timber.d("%s", o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if(jsonObject.getString("status").equals("1")) {
                        showStatus(true, jsonObject.getString("msg"));
                    } else {
                        showStatus(false, jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailed(Object o) {
                Timber.d("%s", o.toString());
                finish();
            }
        }, false);
    }

    @SuppressWarnings("deprecation")
    private void showStatus(boolean isSuccess, String message) {
        binding.indicator.setVisibility(View.GONE);
        binding.lblStauts.setVisibility(View.GONE);
        binding.llAddBalance.setVisibility(View.VISIBLE);
        if (isSuccess) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //>= API 21
                binding.icon.setImageDrawable(getResources().getDrawable(R.drawable.success, getApplicationContext().getTheme()));
            } else {
                binding.icon.setImageDrawable(getResources().getDrawable(R.drawable.success));
            }
            binding.icon.setImageDrawable(getResources().getDrawable(R.drawable.success));
            binding.statusMessage.setText(message);
            binding.titleStatus.setText("Thank you");
            getBalance();
            storeUserData.setBoolean("add_balance", true);
        } else {
            binding.icon.setImageDrawable(getResources().getDrawable(R.drawable.error));
            binding.statusMessage.setText(message);
            binding.titleStatus.setText("Error!!");
        }
    }

    private void getBalance() {


        new AddShow().handleCall(PaymentActivity.this, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {

                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject responseObj = jsonObject.getJSONObject("data");

                        storeUserData.setBoolean("add_balance", false);

                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_COINS, responseObj.getString("total_coins"));
                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_RUPEE, responseObj.getString("total_rupee"));
                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_AVAILABLE_RUPEE, responseObj.getString("total_available_rupee"));



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


}
