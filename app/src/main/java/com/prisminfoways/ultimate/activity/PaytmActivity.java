package com.prisminfoways.ultimate.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.prisminfoways.ultimate.R;

import java.util.HashMap;

public class PaytmActivity extends AppCompatActivity {

    private static final String TAG = "PaytmActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);

        PaytmPGService service = PaytmPGService.getStagingService();

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put( "MID" , "keeZe451975");
// Key in your staging and production MID available in your dashboard
        paramMap.put( "ORDER_ID" , "order1");
        paramMap.put( "CUST_ID" , "cust123");
        paramMap.put( "MOBILE_NO" , "");
        paramMap.put( "EMAIL" , "info@prisminfoways.com");
        paramMap.put( "CHANNEL_ID" , "WAP");
        paramMap.put( "TXN_AMOUNT" , "100.12");
        paramMap.put( "WEBSITE" , "ultimategaming.prisminfoways.com");
// This is the staging value. Production value is available in your dashboard
        paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
// This is the staging value. Production value is available in your dashboard
        paramMap.put( "CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=order1");
        paramMap.put( "CHECKSUMHASH" , "w2QDRMgp1234567JEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=");
        PaytmOrder order = new PaytmOrder(paramMap);


        service.initialize(order, null);

        service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle inResponse) {
                Log.d(TAG, "onTransactionResponse: ");
            }

            @Override
            public void networkNotAvailable() {
                Log.d(TAG, "network not available");
            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {
                Log.d(TAG, "authentication failed");
            }

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {
                Log.d(TAG, "UI error");
            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                Log.d(TAG, "webpage error");
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.d(TAG, "backpressed cantel");
            }

            @Override
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Log.d(TAG, "transaction cancel");
            }
        });

    }
}
