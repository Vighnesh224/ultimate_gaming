package com.prisminfoways.ultimate.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityMatchdetailBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.MatchDetailPojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

public class MatchDetailActivity extends AppCompatActivity {

    private static final String TAG = "MatchDetailActivity";
    ActivityMatchdetailBinding binding;
    StoreUserData storeUserData;
    CustomLoader customLoader;
    String matchId;
    ArrayList<MatchDetailPojo.DataBean.UsersBean> usersArrayList;
    ParticipantsAdapter adapter;
    private String amount;
    private int isjoin;
    String matchdetail;
    String btnjoin;
    String onCreatestr = "oncreate";
    String openFrom;
    String icon;
    String image;
    String matchRule;
    String matchType;
    String editmatchid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_matchdetail);

        storeUserData = new StoreUserData(this);
        customLoader = new CustomLoader(this, false);

        binding.warType.setText(getIntent().getStringExtra("war_name"));
        binding.time.setText(getIntent().getStringExtra("match_time"));
        binding.winPrice.setText(getIntent().getStringExtra("win_prize"));
        binding.matchType.setText(getIntent().getStringExtra("match_type"));
        binding.perKill.setText(getIntent().getStringExtra("kill"));
        binding.version.setText(getIntent().getStringExtra("match_version"));
        binding.entryFees.setText(getIntent().getStringExtra("entry_fee"));
        binding.mapType.setText(getIntent().getStringExtra("map_type"));

        matchType = getIntent().getStringExtra("matchType");

        image = getIntent().getStringExtra("image");
        icon = getIntent().getStringExtra("icon");




        Glide.with(this).load(icon).into(binding.icon);



        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        openFrom = getIntent().getStringExtra("openFrom");

        matchdetail = getIntent().getStringExtra("detail");

        btnjoin = getIntent().getStringExtra("btn_join");

        binding.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MatchDetailActivity.this, MatchInfoActivity.class).putExtra("matchRule",matchRule));
            }
        });

        binding.rvPlayer.setNestedScrollingEnabled(false);

        binding.rvPlayer.setLayoutManager(new LinearLayoutManager(this));

        usersArrayList = new ArrayList<>();

        matchId = getIntent().getStringExtra("match_id");
        isjoin = getIntent().getIntExtra("is_join", 0);

        if (isjoin == 1) {

            if (btnjoin.equals("ongoing")) {
                binding.btnJoin.setVisibility(View.GONE);

            } else {
                binding.btnJoin.setVisibility(View.VISIBLE);
                binding.userId.setVisibility(View.VISIBLE);
            }


            binding.btnJoin.setText("Join +");
        } else {

            if (btnjoin.equals("ongoing")) {
                binding.btnJoin.setVisibility(View.GONE);
                binding.userId.setVisibility(View.GONE);
            } else {
                binding.btnJoin.setVisibility(View.VISIBLE);
            }

            binding.userId.setVisibility(View.GONE);
            binding.btnJoin.setText("JOIN");
        }

        binding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(matchId);
            }
        });

        callMatchDetail(onCreatestr);
    }

    private void showIdPasswordDialog(String detail) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MatchDetailActivity.this);
        View view = getLayoutInflater().inflate(R.layout.idpassword_dialog, null);
        dialogBuilder.setView(view);

        TextView matchIdTV = view.findViewById(R.id.match_id);
        TextView btnCancel = view.findViewById(R.id.btn_cancel);
        TextView btnPlay = view.findViewById(R.id.btn_play);

        final AlertDialog dialog = dialogBuilder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        matchIdTV.setText(detail);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openPubgApp("com.tencent.ig");
            }
        });

        dialog.show();
    }

    private void callMatchDetail(String type) {
        if(type.equals(onCreatestr)){
            customLoader.showLoader();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.MATCH_ID, matchId);
        new AddShow().handleCall(MatchDetailActivity.this, add.Native.com.admodule.Constants.TAG_MATCH_DETAIL, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                MatchDetailPojo matchDetailPojo = new Gson().fromJson(o.toString(), MatchDetailPojo.class);

                if (matchDetailPojo.getStatus().equals("1")) {
                    binding.warType.setText(matchDetailPojo.getData().getName());
                    binding.winPrice.setText(matchDetailPojo.getData().getWin_price());
                    binding.perKill.setText(matchDetailPojo.getData().getPer_kill() );
                    binding.entryFees.setText(matchDetailPojo.getData().getEntry_fee() /* + matchDetailPojo.getData().getEntry_fee_coin() + " c"*/);
                    binding.matchType.setText(matchDetailPojo.getData().getType());
                    binding.version.setText(matchDetailPojo.getData().getVersion());
                    binding.mapType.setText(matchDetailPojo.getData().getMap());

                    binding.matchDetail.setText("Detail : \n" + matchDetailPojo.getData().getDetail());

                    matchRule = matchDetailPojo.getData().getRules();

                    String[] time = matchDetailPojo.getData().getMatch_time().split(" ");

                    String timeh12Constants = Constants.timeFormat(time[1]);

                    binding.time.setText("time: " + time[0] + " AT: " + timeh12Constants);

                    if (openFrom.equals("onGoing")) {
                        if (isjoin == 1) {
                            showIdPasswordDialog(matchDetailPojo.getData().getDetail());
                        } else {
                            Log.d(TAG,"0");
                        }
                    }
                    editmatchid = matchDetailPojo.getData().getId();

                    if (matchDetailPojo.getData().getTotal_spot().equals(matchDetailPojo.getData().getCount())) {
                        binding.btnJoin.setEnabled(false);
                        binding.btnJoin.setClickable(false);
                    } else {
                        binding.btnJoin.setEnabled(true);
                        binding.btnJoin.setClickable(true);
                    }

                    if (!matchDetailPojo.getData().getUsers().isEmpty()) {
                        usersArrayList.clear();
                        usersArrayList.addAll(matchDetailPojo.getData().getUsers());
                        adapter = new ParticipantsAdapter();
                        binding.rvPlayer.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    usersArrayList.clear();

                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
            }
        }, false);
    }

    public class ParticipantsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MatchDetailActivity.this).inflate(R.layout.adapter_participants, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            final MatchDetailPojo.DataBean.UsersBean usersBean = usersArrayList.get(i);

            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.participantsname.setText(usersBean.getPubg_name());

            if(storeUserData.getString(Constants.USER_ID).equals(usersBean.getUser_id())){
                viewHolder.imgEditName.setVisibility(View.VISIBLE);
            }else{
                viewHolder.imgEditName.setVisibility(View.GONE);
            }

            viewHolder.participantsid.setText(+(i+1)+".");

            viewHolder.imgEditName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        showEditDialog(usersBean.getId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return usersArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView participantsname;
            TextView participantsid;
            ImageView imgEditName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                participantsname = itemView.findViewById(R.id.participants_name);
                participantsid = itemView.findViewById(R.id.participants_id);
                imgEditName = itemView.findViewById(R.id.imgEditName);
            }
        }
    }

    private void showEditDialog(final String myid) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.editname_dialog, null);
        dialogBuilder.setView(view);


        final EditText edtpubgname = view.findViewById(R.id.edt_pubg_name);

        TextView btnok = view.findViewById(R.id.btn_ok);
        TextView btncancel = view.findViewById(R.id.btn_cancel);

        final AlertDialog dialog = dialogBuilder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                callEditNameAPI(edtpubgname.getText().toString().trim(),myid);
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callEditNameAPI(String edtName,String myid) {
       customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();

        map.put(com.prisminfoways.ultimate.helper.Constants.EDIT_PUBG_NAME, edtName);
        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_MATCH_ID, editmatchid);
        map.put(com.prisminfoways.ultimate.helper.Constants.ID, myid);

        new AddShow().handleCall(MatchDetailActivity.this, add.Native.com.admodule.Constants.TAG_GET_CHANGE_PUBGNAME, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "callEditNameAPI onLoaded: " + o.toString());



                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {

                        CustomLoader.showErrorDialog(MatchDetailActivity.this, jsonObject.getString("msg"));
                        callMatchDetail("edit");
                    }
                     else {
                        CustomLoader.showErrorDialog(MatchDetailActivity.this, jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "callEditNameAPI onFailed: " + o.toString());
            }
        }, false);
    }

    private void showDialog(final String match_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MatchDetailActivity.this);
        View view = getLayoutInflater().inflate(R.layout.join_dialog, null);
        builder.setView(view);

        TextView cancelBtn = view.findViewById(R.id.btn_cancel);
        TextView joinBtn = view.findViewById(R.id.btn_join);

        final EditText paubgname1 = view.findViewById(R.id.edt_pubg_name1);
        final EditText paubgname2 = view.findViewById(R.id.edt_pubg_name2);
        final EditText paubgname3 = view.findViewById(R.id.edt_pubg_name3);
        final EditText paubgname4 = view.findViewById(R.id.edt_pubg_name4);

        TextView totalCoins = view.findViewById(R.id.totalcoins);
        TextView totalRs = view.findViewById(R.id.totalRs);

        RadioGroup rgType = view.findViewById(R.id.rg_type);
        final RadioButton rbCoin = view.findViewById(R.id.rb_coin);
        final RadioButton rbRupee = view.findViewById(R.id.rb_rs);



        paubgname1.setText(storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.PUBG_NAME));

        totalCoins.setText("Coins (" + storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.TOTAL_COINS) + ")");
        totalRs.setText("Rupees (" + storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.TOTAL_AVAILABLE_RUPEE) + ")");

        final AlertDialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        amount = "rupees";

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

                if (paubgname1.getText().toString().trim().isEmpty()) {
                    CustomLoader.showErrorDialog(MatchDetailActivity.this, "Please Enter your Pubg Name");
                } else {
                    dialog.dismiss();

                    storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.PUBG_NAME, paubgname1.getText().toString());

                    callJoinMatchApi(paubgname1.getText().toString(),paubgname2.getText().toString(),paubgname3.getText().toString(),paubgname4.getText().toString(), match_id, amount);
                }

            }
        });


        dialog.show();

    }

    private void callJoinMatchApi(String pubgname,String pubgname2,String pubgname3,String pubgname4, String matchid, String amount) {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();


        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_PUBG_NAME, pubgname);
        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_PUBG_NAME2, pubgname2);
        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_PUBG_NAME3, pubgname3);
        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_PUBG_NAME4, pubgname4);

        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_MATCH_ID, matchid);
        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_AMOUNT, amount);

        new AddShow().handleCall(MatchDetailActivity.this, add.Native.com.admodule.Constants.TAG_JOIN_MATCH, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {

                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        callMatchDetail(onCreatestr);
                        getBalance();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(MatchDetailActivity.this, JoinSuccessActivity.class)
                                        .putExtra("match_detail", matchdetail)
                                        .putExtra("is_match", "Match Joined Successfully!"));
                                finish();
                            }
                        }, 1000);

                    } else if (jsonObject.getString("status").equals("3")) {

                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MatchDetailActivity.this);
                        dialogBuilder.setTitle("PBGZone");

                        dialogBuilder.setMessage(jsonObject.getString("msg"));

                        dialogBuilder.setPositiveButton("Add Money", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                startActivity(new Intent(MatchDetailActivity.this, AddMoneyActivity.class));
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
                        CustomLoader.showErrorDialog(MatchDetailActivity.this, jsonObject.getString("msg"));
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
        if (storeUserData.getBoolean("is_joined")) {
            binding.userId.setVisibility(View.VISIBLE);
            binding.btnJoin.setText("Join +");
            storeUserData.setBoolean("is_joined", false);
        }
    }

    public void showGeneralDialog(String s) {

        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(MatchDetailActivity.this);
        alertDialog.setMessage(s);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MatchDetailActivity.this, JoinSuccessActivity.class).putExtra("match_detail", matchdetail).putExtra("is_match", "Match Joined Successfully!"));
                    }
                }, 1000);
                dialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        final androidx.appcompat.app.AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }

    private void getBalance() {

        customLoader.showLoader();


        new AddShow().handleCall(MatchDetailActivity.this, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();

                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject responseObj = jsonObject.getJSONObject("data");

                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_COINS, responseObj.getString("total_coins"));
                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.TOTAL_RUPEE, responseObj.getString("total_rupee"));
                        storeUserData.setString(Constants.TOTAL_AVAILABLE_RUPEE, responseObj.getString("total_available_rupee"));

                        binding.totalCoins.setText(responseObj.getString("total_coins"));
                        binding.totalRs.setText("₹" + responseObj.getString("total_rupee"));

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

    private void openPubgApp(String packageName) {

        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.tencent.ig"));
            startActivity(intent);
        }
    }


}
