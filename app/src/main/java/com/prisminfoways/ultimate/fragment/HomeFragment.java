package com.prisminfoways.ultimate.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.ads.NativeAd;
import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.AddMoneyActivity;
import com.prisminfoways.ultimate.activity.JoinSuccessActivity;
import com.prisminfoways.ultimate.activity.MatchDetailActivity;
import com.prisminfoways.ultimate.activity.MatchInfoActivity;
import com.prisminfoways.ultimate.databinding.AdapterTodaymatchBinding;
import com.prisminfoways.ultimate.databinding.FragmentHomeBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.TodayMatchPojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;
import cn.iwgang.countdownview.CountdownView;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    ArrayList<Object> matchList;
    TodayMatchAdapter adapter;
    FragmentHomeBinding binding;
    Activity activity;
    StoreUserData storeUserData;
    CustomLoader customLoader;
    String matchdetail;
    String status = "status";

    private long mEndTime;
    private long mLeftTimeInMillis;
    private boolean mTimerRunning;

    private static final long START_TIME_IN_MILLIS = 300000;
    private CountDownTimer mCountDownTimer;

    public HomeFragment() {
        Log.d(TAG,"home");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
            if (activity != null) {
                getTodayMatchApi();
                getBalance();
            } else {
                Log.d(TAG,"not visible");
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        activity = getActivity();

        customLoader = new CustomLoader(activity, false);
        storeUserData = new StoreUserData(activity);

        getBalance();

        matchList = new ArrayList<>();
        binding.rvTodayMatch.setLayoutManager(new LinearLayoutManager(activity));


        getTodayMatchApi();


        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.measure(100, 100);
                binding.swipeRefresh.setRefreshing(true);
                getTodayMatchApi();
                getBalance();
            }
        });

        return binding.getRoot();
    }

    private void getTodayMatchApi() {


        final CustomLoader loader = new CustomLoader(getActivity(), false);
        binding.swipeRefresh.measure(100, 100);
        binding.swipeRefresh.setRefreshing(true);

        new AddShow().handleCall(activity, Constants.TAG_TODAY_MATCH, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                binding.swipeRefresh.setRefreshing(false);
                loader.dismissLoader();
                Log.d(TAG, "loaded1:" + o.toString());

                TodayMatchPojo todayMatchPojo = new Gson().fromJson(o.toString(), TodayMatchPojo.class);

                if (todayMatchPojo.getStatus().equals("1")) {
                    if (!todayMatchPojo.getData().isEmpty()) {
                        matchList.clear();
                        matchList.addAll(todayMatchPojo.getData());
                        adapter = new TodayMatchAdapter(activity, matchList);
                        binding.rvTodayMatch.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        matchList.clear();
                        Log.d(TAG, "onLoaded: No Data Available");
                    }
                }
            }

            @Override
            public void onFailed(Object o) {
                loader.dismissLoader();
                binding.swipeRefresh.setRefreshing(false);
                Log.d(TAG, "onFailed1 : " + o.toString());
            }
        }, false);
    }

    private void getBalance() {


        binding.swipeRefresh.setRefreshing(true);

        new AddShow().handleCall(activity, add.Native.com.admodule.Constants.TAG_BALANCE, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {

                binding.swipeRefresh.setRefreshing(false);

                Log.d(TAG, "onLoaded2: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString(status).equals("1")) {
                        JSONObject responseObj = jsonObject.getJSONObject("data");

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
                binding.swipeRefresh.setRefreshing(false);
                Log.d(TAG, "onFailed2 : " + o.toString());
            }
        }, false);
    }

    //Adapter

    public class TodayMatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final String TAG = "todayMatchAdapter";
        private Context context;
        private ArrayList<Object> matchLists;
        private static final int ITEM_VIEW = 1;
        private static final int TYPE_AD_FB = 2;
        CustomLoader customLoader;
        Activity activity;
        private String amount;
        StoreUserData storeUserData;

        public TodayMatchAdapter(Context context, ArrayList<Object> matchLists) {
            this.context = context;
            this.matchLists = matchLists;
            activity = (Activity) context;
            customLoader = new CustomLoader(activity, false);
            storeUserData = new StoreUserData(activity);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_todaymatch, viewGroup, false);
            return new TodayMatchAdapter.ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

            try {
                if (holder instanceof ItemViewHolder) {
                    ItemViewHolder viewHolder = (ItemViewHolder) holder;
                    final TodayMatchPojo.DataBean matchPojo = (TodayMatchPojo.DataBean) matchLists.get(position);

                    viewHolder.binding.warType.setText(matchPojo.getName());
                    viewHolder.binding.winPrice.setText(matchPojo.getWin_price() );
                    viewHolder.binding.matchType.setText(matchPojo.getType());
                    viewHolder.binding.entryFees.setText(matchPojo.getEntry_fee() /* + matchPojo.getEntry_fee_coin() + " c"*/);
                    viewHolder.binding.perKill.setText(matchPojo.getPer_kill() );
                    viewHolder.binding.version.setText(matchPojo.getVersion());
                    viewHolder.binding.mapType.setText(matchPojo.getMap());
                    viewHolder.binding.time.setText(matchPojo.getMatch_time());
                    viewHolder.binding.totalSpot.setText(matchPojo.getCount() + "/" + matchPojo.getTotal_spot());

                    String[] time = matchPojo.getMatch_time().split(" ");
                    Log.d(TAG, "onBindViewHolder: " + time);

                    timeFormat(time[0], time[1], viewHolder);

                    int spotleft = (matchPojo.getTotal_spot() - matchPojo.getCount());

                    if (matchPojo.getTotal_spot() != matchPojo.getCount()) {
                        viewHolder.binding.spotLeft.setText("Only " + spotleft + " Spot Left");
                    } else {
                        viewHolder.binding.spotLeft.setText("Contest full");
                    }



                    viewHolder.binding.info.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            startActivity(new Intent(activity, MatchInfoActivity.class));
                        }
                    });

                    if (matchPojo.getIs_joined() == 1) {
                        viewHolder.binding.btnJoin.setText("Joined");
                    } else {
                        viewHolder.binding.btnJoin.setText("Join");
                    }

                    if (matchPojo.getTotal_spot() == matchPojo.getCount()) {
                        viewHolder.binding.btnJoin.setEnabled(false);
                        viewHolder.binding.btnJoin.setClickable(false);
                    } else {
                        viewHolder.binding.btnJoin.setEnabled(true);
                        viewHolder.binding.btnJoin.setClickable(true);
                    }

                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(activity, MatchDetailActivity.class).putExtra("match_id", matchPojo.getId()).putExtra("is_join", matchPojo.getIs_joined()).putExtra("detail", matchPojo.getDetail())
                                    .putExtra("btn_join", "today_match").putExtra("openFrom", "home"));
                        }
                    });

                    viewHolder.binding.btnJoin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            showDialog(matchPojo.getId(), matchPojo.getIs_joined(), matchPojo);

                        }
                    });

                    viewHolder.binding.circleView.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
                        @Override
                        public void onProgressChanged(float value) {
                            Log.d(TAG, "Progress Changed: " + value);
                        }
                    });

                    viewHolder.binding.circleView.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                    viewHolder.binding.circleView.setRimColor(ContextCompat.getColor(getActivity(),R.color.txt_bg_email));
                    viewHolder.binding.circleView.setUnitColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                    viewHolder.binding.circleView.setBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                    viewHolder.binding.circleView.setBarWidth(10);
                    viewHolder.binding.circleView.setRimWidth(10);
                    viewHolder.binding.circleView.setInnerContourColor(ContextCompat.getColor(getActivity(),R.color.color_transparent));
                    viewHolder.binding.circleView.setUnitColor(ContextCompat.getColor(getActivity(),R.color.color_transparent));
                    viewHolder.binding.circleView.setInnerContourColor(ContextCompat.getColor(getActivity(),R.color.color_transparent));
                    viewHolder.binding.circleView.setInnerContourSize(0);
                    viewHolder.binding.circleView.setOuterContourSize(0);

                    viewHolder.binding.circleView.setSpinningBarLength(matchPojo.getCount());
                    viewHolder.binding.circleView.setText(matchPojo.getCount() + "/" + matchPojo.getTotal_spot());
                    viewHolder.binding.circleView.setTextMode(TextMode.TEXT); // show text while spinning
                    viewHolder.binding.circleView.setUnitVisible(false);
                    viewHolder.binding.circleView.setUnitVisible(false);
                    viewHolder.binding.circleView.setValueAnimated(matchPojo.getCount(), 500);
                    viewHolder.binding.circleView.setAutoTextSize(true);

                    String matchReminingTime = convertTimeIntoMili(matchPojo.getMatch_time(), matchPojo);

                    viewHolder.refreshTime(matchPojo.getEndTime() - System.currentTimeMillis());


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (matchLists.get(position) instanceof NativeAd) {
                return TYPE_AD_FB;
            } else {
                return ITEM_VIEW;
            }
        }

        @Override
        public int getItemCount() {
            return matchLists.size();
        }

        @Override
        public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
            int pos = holder.getAdapterPosition();

            TodayMatchPojo.DataBean itemInfo = (TodayMatchPojo.DataBean) matchList.get(pos);

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.refreshTime(itemInfo.getEndTime() - System.currentTimeMillis());
            super.onViewAttachedToWindow(holder);
        }



        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {


            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.getCvCountdownView().stop();
        }


        public class ItemViewHolder extends RecyclerView.ViewHolder {

            AdapterTodaymatchBinding binding;
            private CountdownView mCvCountdownView;



            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);

                binding = DataBindingUtil.bind(itemView);
                mCvCountdownView = (CountdownView) itemView.findViewById(R.id.cv_countdownView);
            }

            public void refreshTime(long leftTime) {
                if (leftTime > 0) {
                    mCvCountdownView.start(leftTime);
                } else {
                    mCvCountdownView.stop();
                    mCvCountdownView.allShowZero();
                }
            }

            public CountdownView getCvCountdownView() {
                return mCvCountdownView;
            }

        }

        private void showDialog(final String match_id, final int isJoin, final TodayMatchPojo.DataBean matchPojo) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = activity.getLayoutInflater().inflate(R.layout.join_dialog, null);
            builder.setView(view);

            TextView cancelBtn = view.findViewById(R.id.btn_cancel);
            TextView joinBtn = view.findViewById(R.id.btn_join);
            final EditText paubgname = view.findViewById(R.id.edt_pubg_name1);

            TextView totalCoins = view.findViewById(R.id.totalcoins);
            TextView totalRs = view.findViewById(R.id.totalRs);

            RadioGroup rgType = view.findViewById(R.id.rg_type);
            final RadioButton rbCoin = view.findViewById(R.id.rb_coin);
            final RadioButton rbRupee = view.findViewById(R.id.rb_rs);

            int isjoin = matchPojo.getIs_joined();


            paubgname.setText(storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.PUBG_NAME));

            totalCoins.setText("Coins (Balance : " + storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.TOTAL_COINS) + ")");
            totalRs.setText("Rupees (₹ : " + storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.TOTAL_RUPEE) + ")");

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

                    if (paubgname.getText().toString().trim().isEmpty()) {
                        CustomLoader.showErrorDialog(activity, "Please Enter your Pubg Name");
                    } else {
                        dialog.dismiss();
                        storeUserData.setString(com.prisminfoways.ultimate.helper.Constants.PUBG_NAME, paubgname.getText().toString());
                        callJoinMatchApi(paubgname.getText().toString(), match_id, amount, matchPojo);
                    }

                }
            });

            if (isjoin == 1) {
                CustomLoader.showErrorDialog(activity, "You have already join this match");
            } else {
                dialog.show();
            }
        }

        private void callJoinMatchApi(String pubgname, String matchid, String amount, final TodayMatchPojo.DataBean matchPojo) {

            customLoader.showLoader();

            HashMap<String, String> map = new HashMap<>();
            map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_PUBG_NAME, pubgname);
            map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_MATCH_ID, matchid);
            map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_AMOUNT, amount);

            new AddShow().handleCall(activity, Constants.TAG_JOIN_MATCH, map, new ErrorListner() {
                @Override
                public void onLoaded(Object o) {

                    customLoader.dismissLoader();
                    Log.d(TAG, "onLoaded3 : " + o.toString());

                    try {
                        JSONObject jsonObject = new JSONObject(o.toString());

                        if (jsonObject.getString(status).equals("1")) {

                            getTodayMatchApi();
                            getBalance();

                            startActivity(new Intent(activity, JoinSuccessActivity.class).putExtra("match_detail", matchPojo.getDetail()).putExtra("time", matchPojo.getMatch_time())
                                    .putExtra("is_match", "Match Joined Successfully!"));

                        } else if (jsonObject.getString(status).equals("3")) {

                            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                            dialogBuilder.setTitle("PBGZone");

                            dialogBuilder.setMessage(jsonObject.getString("msg"));

                            dialogBuilder.setPositiveButton("Add Money", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    startActivity(new Intent(activity, AddMoneyActivity.class));
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
                            CustomLoader.showErrorDialog(activity, jsonObject.getString("msg"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(Object o) {
                    customLoader.dismissLoader();
                    Log.d(TAG, "onFailed3 : " + o.toString());
                }
            }, false);
        }



        void showInfoDialog() {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            View view = activity.getLayoutInflater().inflate(R.layout.info_dialog, null);
            dialogBuilder.setView(view);

            TextView infotxt = view.findViewById(R.id.text_match_info);
            TextView btnok = view.findViewById(R.id.btn_ok);

            final AlertDialog dialog = dialogBuilder.create();

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            String infotext = storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.INFO_MSG);

            infotxt.setText(infotext);

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getTodayMatchApi();
        getBalance();
    }

    void timeFormat(String date, String time, TodayMatchAdapter.ItemViewHolder holder) {

        try {
            SimpleDateFormat simpleDateFormat24 = new SimpleDateFormat("HH:mm");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
            final Date dateObj = simpleDateFormat24.parse(time);
            Log.d("Format time", "timeFormat: " + dateObj);
            Log.d("Format time", "timeFormat: " + simpleDateFormat.format(dateObj));
            holder.binding.time.setText("Time: " + date + " AT: " + simpleDateFormat.format(dateObj));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void showGeneralDialog(String s, final TodayMatchPojo.DataBean matchPojo) {

        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(activity);
        alertDialog.setMessage(s);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(activity, JoinSuccessActivity.class).putExtra("match_detail", matchPojo.getDetail()).putExtra("time", matchPojo.getMatch_time())
                                .putExtra("is_match", "Match Joined Successfully!"));
                    }
                }, 1000);
                dialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        final androidx.appcompat.app.AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    void startTime(final long time, TodayMatchAdapter.ItemViewHolder holder) {

        mEndTime = System.currentTimeMillis() + mLeftTimeInMillis;

        mCountDownTimer = new CountDownTimer(mLeftTimeInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mLeftTimeInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mLeftTimeInMillis = time;
                mTimerRunning = false;
            }
        }.start();

        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) ((mLeftTimeInMillis / 1000) / 60);
        int second = (int) ((mLeftTimeInMillis / 1000) % 60);

        String timeFormated = String.format(Locale.getDefault(), "%02d:%02d", minutes, second);
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        mLeftTimeInMillis = preferences.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = preferences.getBoolean("timerRunning", false);

        updateCountDownText();

        if (mTimerRunning) {
            mEndTime = preferences.getLong("endTime", 0);
            mLeftTimeInMillis = mEndTime - System.currentTimeMillis();

            if (mLeftTimeInMillis < 0) {
                mLeftTimeInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
            } else {
                Log.d(TAG,"not running");
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("millisLeft", mLeftTimeInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    String convertTimeIntoMili(String time, TodayMatchPojo.DataBean matchPojo) {


        String matchreminingTime = null;
        long matchTimeTiMili = 0;

        long remainingTime = 0;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setLenient(false);

        Date curDate = new Date();
        long currentTimeIntoMili = curDate.getTime();
        Log.d("TimeIntoMillisecond", "currentTimeIntoMili: " + currentTimeIntoMili);

        Date oldDate = null;
        try {
            oldDate = formatter.parse(time);
            matchTimeTiMili = oldDate.getTime();

            remainingTime = matchTimeTiMili - currentTimeIntoMili;
            Log.d("TimeIntoMillisecond", "remainingTimeIntoMili: " + remainingTime);

            matchreminingTime = converMiliToHours(remainingTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        matchPojo.setCountDown(remainingTime);
        long endTime = System.currentTimeMillis() + matchPojo.getCountDown();
        matchPojo.setEndTime(endTime);

        return matchreminingTime;
    }

    private String converMiliToHours(long mili) {

        long second = mili / 1000;
        long minutes = second / 60;
        long hour = minutes / 60;

        second = second % 60;
        minutes = minutes % 60;
        hour = hour % 60;

        String secondD = String.valueOf(second);
        String minutesD = String.valueOf(minutes);
        String hourD = String.valueOf(hour);

        if (second < 10) {
            secondD = "0" + second;
        }
        if (minutes < 10) {
            minutesD = "0" + minutes;
        }
        if (hour < 10) {
            hourD = "0" + hour;
        }

        String output = hourD + ":" + minutesD + ":" + secondD;
        Log.d("Time", "converMiliToHours: " + output);

        return output;

    }

}
