package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivitySignupBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.SettingPojo;
import com.wang.avi.CustomLoader;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;
import add.Native.com.admodule.models.RegisterItem;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignupActivity";
    ActivitySignupBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        binding.btnSignup.setOnClickListener(this);
        binding.openSignin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.btn_signup) {
            onSignbtnClick();
        } else if (id == R.id.open_signin) {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void onSignbtnClick() {

        if (binding.edtName.getText().toString().trim().isEmpty()) {
            CustomLoader.showErrorDialog(this, "Please enter name");
        } else if (binding.edtEmail.getText().toString().trim().isEmpty()) {
            CustomLoader.showErrorDialog(this, "Please enter email");
        } else if (binding.edtPassword.getText().toString().trim().isEmpty()) {
            CustomLoader.showErrorDialog(this, "Please enter password");
        } else if (binding.edtNumber.getText().toString().trim().isEmpty()) {
            CustomLoader.showErrorDialog(this, "Please enter number");
        } else {
            callSignupApi();
        }
    }

    private void callSignupApi() {


        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.NAME, binding.edtName.getText().toString().trim());
        map.put(Constants.EMAIL, binding.edtEmail.getText().toString().trim());
        map.put(Constants.PASSWORD, binding.edtPassword.getText().toString().trim());
        map.put(Constants.PHONE, binding.edtNumber.getText().toString().trim());
        map.put(Constants.CODE, binding.edtSponcerid.getText().toString().trim());

        new AddShow().handleCall(SignupActivity.this, add.Native.com.admodule.Constants.TAG_SIGNUP, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d("Signup", "User Info " + o.toString());

                RegisterItem registerItem = (RegisterItem) o;

                if (registerItem.getStatus().equals("1")) {
                    storeUserData.setString(Constants.USER_NAME, registerItem.getData().getData().getName());
                    storeUserData.setString(Constants.TOKEN, registerItem.getData().getToken());
                    storeUserData.setString(Constants.USER_EMAIL, registerItem.getData().getData().getEmail());
                    storeUserData.setString(Constants.USER_PHONE, registerItem.getData().getData().getPhone());
                    storeUserData.setString(Constants.USER_ID, registerItem.getData().getData().getUser_id());

                    getSettings();

                } else {
                    CustomLoader.showErrorDialog(SignupActivity.this, registerItem.getMsg());
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }

    private void getSettings() {

        customLoader.showLoader();

        new AddShow().handleCall(SignupActivity.this, add.Native.com.admodule.Constants.TAG_SETTING, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());

                SettingPojo settingPojo = new Gson().fromJson(o.toString(), SettingPojo.class);

                if (settingPojo.getStatus().equals("1")) {
                    storeUserData.setString(Constants.TELEGRAM_LINK, settingPojo.getData().getTelegram());
                    storeUserData.setString(Constants.ABOUT_US, settingPojo.getData().getAbout_us());
                    storeUserData.setString(Constants.CUSTOMER_SUPPORT, settingPojo.getData().getCustomer_support());
                    storeUserData.setString(Constants.TERMS_CONDITION, settingPojo.getData().getTerms_and_conditions());
                    storeUserData.setString(Constants.PRIVACY_POLICY, settingPojo.getData().getPrivacy_policy());
                    storeUserData.setString(Constants.FAQ, settingPojo.getData().getFaq());

                    storeUserData.setString(Constants.WEBSITE, settingPojo.getData().getWebsite());
                    storeUserData.setString(Constants.HOW_IT_WORK, settingPojo.getData().getHow_it_work());
                    storeUserData.setString(Constants.DATE, settingPojo.getData().getDate());
                    storeUserData.setString(Constants.INFO_MSG, settingPojo.getData().getInfo_msg());
                    storeUserData.setString(Constants.HOW_TO_JOIN_ROOM, settingPojo.getData().getHow_to_join_room());


                    storeUserData.setString(Constants.UPDATE_MESSAGE, settingPojo.getData().getUpdate().getMessage());
                    storeUserData.setString(Constants.SKIP, settingPojo.getData().getUpdate().getSkip());
                    storeUserData.setString(Constants.APP_LINK, settingPojo.getData().getUpdate().getLink());
                } else {
                    Log.d(TAG,"getstatus0");
                }

                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                finish();

            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onFailed: " + o.toString());
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                finish();
            }
        }, false);
    }
}
