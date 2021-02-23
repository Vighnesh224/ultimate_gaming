package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityLoginBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.SettingPojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;
import add.Native.com.admodule.models.RegisterItem;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    ActivityLoginBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;
    String onFailed = "onFailed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        customLoader = new CustomLoader(this, false);
        storeUserData = new StoreUserData(this);

        binding.openSignup.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.forgotPassword.setOnClickListener(this);

        if (storeUserData.getBoolean(Constants.IS_LOGIN)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_signup:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
                break;

            case R.id.btn_login:
                onLoginbtnClick();
                break;

            case R.id.forgot_password:
                showDialog();
                break;
            default: break;
        }
    }

    private void onLoginbtnClick() {
        if (binding.edtEmail.getText().toString().trim().isEmpty()) {
            CustomLoader.showErrorDialog(this, "Please enter name");
        } else if (binding.edtPassword.getText().toString().trim().isEmpty()) {

            CustomLoader.showErrorDialog(this, "Please enter password");
        } else {
            callLoginApi();
        }
    }

    private void callLoginApi() {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.EMAIL, binding.edtEmail.getText().toString().trim());
        map.put(Constants.PASSWORD, binding.edtPassword.getText().toString().trim());

        new AddShow().handleCall(LoginActivity.this, add.Native.com.admodule.Constants.TAG_LOGIN, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: Login " + o.toString());
                RegisterItem registerItem = (RegisterItem) o;

                if (registerItem.getStatus().equals("1")) {
                    storeUserData.setString(Constants.USER_NAME, registerItem.getData().getData().getName());
                    storeUserData.setString(Constants.TOKEN, registerItem.getData().getToken());
                    storeUserData.setString(Constants.USER_EMAIL, registerItem.getData().getData().getEmail());
                    storeUserData.setString(Constants.USER_PHONE, registerItem.getData().getData().getPhone());
                    storeUserData.setString(Constants.USER_ID, registerItem.getData().getData().getUser_id());

                    getSettings();

                } else {
                    CustomLoader.showErrorDialog(LoginActivity.this, registerItem.getMsg());
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, onFailed + o.toString());
            }
        }, false);
    }

    private void showDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        View view = getLayoutInflater().inflate(R.layout.forgot_password_dialog, null);
        dialogBuilder.setView(view);

        final EditText edtemail = view.findViewById(R.id.forgot_password_email);
        TextView txtcancel = view.findViewById(R.id.btn_cancel);
        TextView txtsubmit = view.findViewById(R.id.btn_send);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        txtcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtemail.getText().toString().trim().isEmpty()) {
                    CustomLoader.showErrorDialog(LoginActivity.this, "Please Enter email");
                } else {
                    dialog.dismiss();
                    callForgotPasswordApi(edtemail.getText().toString());
                }
            }
        });

        dialog.show();
    }

    private void callForgotPasswordApi(String email) {


        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.EMAIL, email);

        new AddShow().handleCall(LoginActivity.this, add.Native.com.admodule.Constants.TAG_FORGOT_PASSWORD, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    CustomLoader.showErrorDialog(LoginActivity.this, jsonObject.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, onFailed + o.toString());
            }
        }, false);
    }

    private void getSettings() {

        customLoader.showLoader();

        new AddShow().handleCall(LoginActivity.this, add.Native.com.admodule.Constants.TAG_SETTING, new HashMap<String, String>(), new ErrorListner() {
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
                    Log.d(TAG,onFailed);
                }

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();

            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, onFailed + o.toString());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }, false);
    }

}