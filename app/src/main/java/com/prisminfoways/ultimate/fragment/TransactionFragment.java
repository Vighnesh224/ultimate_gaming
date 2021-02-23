package com.prisminfoways.ultimate.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.FragmentTransactionBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {


    private static final String TAG = "OngoingFragment";
    FragmentTransactionBinding binding;
    Activity activity;
    StoreUserData storeUserData;


    public TransactionFragment() {
        Log.d(TAG,"transaction");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false);
        activity = getActivity();

        storeUserData = new StoreUserData(activity);


        return binding.getRoot();
    }




}
