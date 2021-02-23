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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.PaymentActivity;
import com.prisminfoways.ultimate.adapter.AddMoneyListAdapter;
import com.prisminfoways.ultimate.adapter.OnGoingMatchAdapter;
import com.prisminfoways.ultimate.databinding.FragmentAddmoneyBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.AddMoneyListPojo;
import com.wang.avi.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMoneyFragment extends Fragment {


    private static final String TAG = "AddMoneyFragment";
    FragmentAddmoneyBinding binding;
    Activity activity;
    StoreUserData storeUserData;
    ArrayList<Object> onGoingMatchList;
    OnGoingMatchAdapter adapter;
    CustomLoader customLoader;
    String gameID;

    AddMoneyListAdapter addMoneyListAdapter;

    public AddMoneyFragment() {
    }

    public AddMoneyFragment(String gameID) {
        this.gameID = gameID;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_addmoney, container, false);
        activity = getActivity();

        storeUserData = new StoreUserData(activity);
        customLoader = new CustomLoader(activity, false);


        binding.addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edtAmount.getText().toString().trim().isEmpty()) {
                    CustomLoader.showErrorDialog(getActivity(), "Enter amount to add into wallet");
                } else {

                    startActivity(new Intent(getActivity(), PaymentActivity.class).putExtra("amount",
                            binding.edtAmount.getText().toString()));
                    getActivity().finish();
                }
            }
        });

        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        binding.recycleAddmoneyList.setLayoutManager(layoutmanager);

        getAddMoneyList();

        return binding.getRoot();
    }

    private void getAddMoneyList() {
        new AddShow().handleCall(activity, Constants.TAG_GET_ADDMONEY_LIST, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {

                Log.d(TAG, "onLoaded: " + o.toString());

                AddMoneyListPojo addMoneyListPojo = new Gson().fromJson(o.toString(),AddMoneyListPojo.class);
                if(addMoneyListPojo.getStatus().equals("1")){
                    addMoneyListAdapter = new AddMoneyListAdapter(getActivity(),addMoneyListPojo.getData());
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
