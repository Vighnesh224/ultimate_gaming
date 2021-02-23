package com.prisminfoways.ultimate.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.prisminfoways.ultimate.AppConstant;
import com.prisminfoways.ultimate.MyGameCountDownTimer;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityLuckyDrawDetailsBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.LuckyDrawParticipatePojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;

public class LuckyDrawDetailsActivity extends AppCompatActivity {

    private static final String TAG = "LuckyDrawDetails";

    private ActivityLuckyDrawDetailsBinding binding;
    private StoreUserData storeUserData;
    CustomLoader customLoader;
    private String amount;
    String entryfeers;
    String luckydrawid;
    String status;
    String openFrom;
    String createddate;
    private int isjoin;
    ArrayList<LuckyDrawParticipatePojo.DataBean> usersArrayList;

    ParticipantsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_lucky_draw_details);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        String title;
        String winningprice;
        String entryfeecoin;
        String startdate;
        String joininglimit;
        String enddate;
        String resultdate;
        String image;
        String total;
        luckydrawid = getIntent().getStringExtra("lucky_draw_id");
        title = getIntent().getStringExtra("title");
        entryfeecoin = getIntent().getStringExtra("entry_fee_coin");
        entryfeers = getIntent().getStringExtra("entry_fee_rs");
        winningprice =getIntent().getStringExtra("winning_price");
        joininglimit = getIntent().getStringExtra("joining_limit");
        startdate =getIntent().getStringExtra("start_date");
        enddate = getIntent().getStringExtra("end_date");
        resultdate = getIntent().getStringExtra("result_date");
        image = getIntent().getStringExtra("image");
        status = getIntent().getStringExtra("status");
        createddate = getIntent().getStringExtra("created_date");
        total = getIntent().getStringExtra("total");
        isjoin = getIntent().getIntExtra("is_join",0);
        openFrom =getIntent().getStringExtra("openFrom");

        binding.txtWinner.setText(winningprice);
        binding.txtJoinCoin.setText(entryfeecoin);
        binding.txtResultDate.setText(resultdate);

        Glide.with(this).load("https://ultimategaming.prisminfoways.com/assets/images/"+image).into(binding.imgBanner);

        binding.warType.setText(title);

        binding.txtSpot.setText(joininglimit);

        binding.spotProgress.setProgress(Integer.parseInt(total));

        int spotleft = Integer.parseInt(joininglimit) - Integer.parseInt(total);

        binding.txtAvailableSpot.setText("Spots : "+spotleft);

        if (!joininglimit.equals(total)) {
            binding.txtSpotDetails.setText("Only " + spotleft + " Spots Left");
            binding.txtRegsDetails.setText("Registration open");
        } else {
            binding.txtSpotDetails.setText("No Spots Left");
            binding.txtRegsDetails.setText("Registration closed.");
        }

        binding.isJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(luckydrawid);
            }
        });

        if (isjoin == 1) {
            binding.isJoin.setText("REGISTERED");
        } else {
            binding.isJoin.setText("REGISTER NOW");
        }

        long time = AppConstant.getTimerDifference(this, startdate, enddate);
        MyGameCountDownTimer timer = new MyGameCountDownTimer(time,binding.txtTimer);
        timer.startCountDownTimer();


        binding.rvPlayer.setNestedScrollingEnabled(false);

        binding.rvPlayer.setLayoutManager(new LinearLayoutManager(this));

        usersArrayList = new ArrayList<>();

        callDetail();

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callDetail() {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put("lucky_draw_id", luckydrawid);

        new AddShow().handleCall(LuckyDrawDetailsActivity.this, Constants.TAG_GET_LUCKYDRAW_PARTICIPATE, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();

                Log.d(TAG, "onLoaded: " + o.toString());
                LuckyDrawParticipatePojo matchDetailPojo = new Gson().fromJson(o.toString(), LuckyDrawParticipatePojo.class);

                if(matchDetailPojo.getStatus().equals("1")){
                    if (!matchDetailPojo.getData().isEmpty()) {
                        usersArrayList.clear();
                        usersArrayList.addAll(matchDetailPojo.getData());
                        adapter = new ParticipantsAdapter();
                        binding.rvPlayer.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG,"0");
                    }
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }

    private void showDialog(final String match_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LuckyDrawDetailsActivity.this);
        View view = getLayoutInflater().inflate(R.layout.join_dialog, null);
        builder.setView(view);

        TextView txtJoinDetails = view.findViewById(R.id.txtJoinDetails);
        TextView cancelBtn = view.findViewById(R.id.btn_cancel);
        TextView joinBtn = view.findViewById(R.id.btn_join);
        final EditText paubgname = view.findViewById(R.id.edt_pubg_name1);

        TextView totalCoins = view.findViewById(R.id.totalcoins);
        TextView totalRs = view.findViewById(R.id.totalRs);

        txtJoinDetails.setText("Select amount type to join Lucky Draw");

        RadioGroup rgType = view.findViewById(R.id.rg_type);
        final RadioButton rbCoin = view.findViewById(R.id.rb_coin);
        final RadioButton rbRupee = view.findViewById(R.id.rb_rs);

        paubgname.setVisibility(View.GONE);

        totalCoins.setText("Coins (" + storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.TOTAL_COINS) + ")");
        totalRs.setText("Rupees (" + storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.TOTAL_AVAILABLE_RUPEE) + ")");

        final AlertDialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        amount = "coin";

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                if (null != radioButton) {
                    if (rbCoin.isChecked()) {
                        amount = "coin";
                    } else if (rbRupee.isChecked()) {
                        amount = "rupees";
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                callJoinMatchApi(match_id, amount);

            }
        });

        if (isjoin == 1) {
            CustomLoader.showErrorDialog(LuckyDrawDetailsActivity.this, "You have already join this lucky draw");
        } else {
            dialog.show();
        }
    }

    private void callJoinMatchApi(String matchid, String amount) {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_LUCKYDRAW_ID, matchid);
        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_AMOUNT, amount);

        new AddShow().handleCall(LuckyDrawDetailsActivity.this, Constants.TAG_GET_JOIN_LUCKYDRAW, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                storeUserData.setBoolean("is_luckydraw_joined", true);

                                startActivity(new Intent(LuckyDrawDetailsActivity.this, JoinSuccessActivity.class).putExtra("match_detail", "SuccessFully Registred Lucky Draw")
                                        .putExtra("is_match", "Lucky Draw Joined Successfully!"));
                            }
                        }, 1000);

                    } else if (jsonObject.getString("status").equals("3")) {

                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LuckyDrawDetailsActivity.this);
                        dialogBuilder.setTitle("Luck Draw");
                        dialogBuilder.setMessage(jsonObject.getString("msg"));

                        dialogBuilder.setPositiveButton("Add Money", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                startActivity(new Intent(LuckyDrawDetailsActivity.this, AddMoneyActivity.class));
                            }
                        });

                        dialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        dialogBuilder.show();

                    } else {
                        CustomLoader.showErrorDialog(LuckyDrawDetailsActivity.this, jsonObject.getString("msg"));
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
    protected void onResume() {
        super.onResume();
        if (storeUserData.getBoolean("is_luckydraw_joined")) {
            binding.isJoin.setText("REGISTERED");
            storeUserData.setBoolean("is_luckydraw_joined", false);
        }

        callDetail();
    }

    public class ParticipantsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(LuckyDrawDetailsActivity.this).inflate(R.layout.adapter_participants, viewGroup, false);
            return new ParticipantsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            LuckyDrawParticipatePojo.DataBean usersBean = usersArrayList.get(i);

            ParticipantsAdapter.ViewHolder viewHolder = (ParticipantsAdapter.ViewHolder) holder;
            viewHolder.participantsname.setText(usersBean.getName());

        }

        @Override
        public int getItemCount() {
            return usersArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView participantsname;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                participantsname = itemView.findViewById(R.id.participants_name);
            }
        }
    }
}