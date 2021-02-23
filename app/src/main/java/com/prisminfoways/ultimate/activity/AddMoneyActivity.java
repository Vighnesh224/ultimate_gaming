package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.adapter.AddMoneyListAdapter;
import com.prisminfoways.ultimate.databinding.ActivityAddMoneyBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.AddMoneyListPojo;
import com.wang.avi.CustomLoader;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;

public class AddMoneyActivity extends AppCompatActivity {

    private static final String TAG = "AddMoneyActivity";
    ActivityAddMoneyBinding binding;
    CustomLoader customLoader;
    StoreUserData storeUserData;


    AddMoneyListAdapter addMoneyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_money);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        binding.addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edtAmount.getText().toString().trim().isEmpty()) {
                    CustomLoader.showErrorDialog(AddMoneyActivity.this, "Enter amount to add into wallet");
                } else {

                    startActivity(new Intent(AddMoneyActivity.this, PaymentActivity.class).putExtra("amount",
                            binding.edtAmount.getText().toString()));
                    finish();
                }
            }
        });

        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(this);
        binding.recycleAddmoneyList.setLayoutManager(layoutmanager);

        getAddMoneyList();
    }

    private void getAddMoneyList() {
        new AddShow().handleCall(this, Constants.TAG_GET_ADDMONEY_LIST, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {

                Log.d(TAG, "onLoaded: " + o.toString());

                AddMoneyListPojo addMoneyListPojo = new Gson().fromJson(o.toString(),AddMoneyListPojo.class);
                if(addMoneyListPojo.getStatus().equals("1")){
                    addMoneyListAdapter = new AddMoneyListAdapter(AddMoneyActivity.this,addMoneyListPojo.getData());
                    binding.recycleAddmoneyList.setAdapter(addMoneyListAdapter);
                }

            }

            @Override
            public void onFailed(Object o) {

                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }
}
