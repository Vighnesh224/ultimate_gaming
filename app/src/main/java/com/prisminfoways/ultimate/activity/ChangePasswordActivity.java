package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityChangePasswordBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

public class ChangePasswordActivity extends AppCompatActivity {

    private static final String TAG = "ChangePasswordActivity";
    ActivityChangePasswordBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edtOldPassword.getText().toString().isEmpty()) {

                    CustomLoader.showErrorDialog(ChangePasswordActivity.this, "Please enter old password");
                } else if (binding.edtNewPassword.getText().toString().isEmpty()) {

                    CustomLoader.showErrorDialog(ChangePasswordActivity.this, "Please enter new password");
                } else if (binding.edtConformPassword.getText().toString().isEmpty()) {

                    CustomLoader.showErrorDialog(ChangePasswordActivity.this, "Please conform new password");
                } else if (!binding.edtConformPassword.getText().toString().equals(binding.edtNewPassword.getText().toString())) {
                    CustomLoader.showErrorDialog(ChangePasswordActivity.this, "Password does not match");
                } else {
                    callForgotPasswordApi();
                }
            }
        });
    }

    private void callForgotPasswordApi() {


        customLoader.showLoader();
        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.OLD_PASSWORD, binding.edtOldPassword.getText().toString());
        map.put(Constants.NEW_PASSWORD, binding.edtConformPassword.getText().toString());

        new AddShow().handleCall(ChangePasswordActivity.this, add.Native.com.admodule.Constants.TAG_CHANGE_PASSWORD, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {

                binding.edtOldPassword.setText("");
                binding.edtNewPassword.setText("");
                binding.edtConformPassword.setText("");

                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());
                    CustomLoader.showErrorDialog(ChangePasswordActivity.this, jsonObject.getString("msg"));
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
