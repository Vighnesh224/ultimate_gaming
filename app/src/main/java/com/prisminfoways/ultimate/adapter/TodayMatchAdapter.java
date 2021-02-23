package com.prisminfoways.ultimate.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.NativeAd;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.MatchDetailActivity;
import com.prisminfoways.ultimate.databinding.AdapterTodaymatchBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;
import com.prisminfoways.ultimate.pojo.TodayMatchPojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

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
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        try {
            if (holder instanceof ItemViewHolder) {
                final ItemViewHolder viewHolder = (ItemViewHolder) holder;
                final TodayMatchPojo.DataBean matchPojo = (TodayMatchPojo.DataBean) matchLists.get(position);

                viewHolder.binding.warType.setText(matchPojo.getName());
                viewHolder.binding.winPrice.setText(matchPojo.getWin_price());
                viewHolder.binding.mapType.setText(matchPojo.getType());
                viewHolder.binding.entryFees.setText(matchPojo.getEntry_fee() + "/Rs" + matchPojo.getEntry_fee_coin() + "C");
                viewHolder.binding.perKill.setText(matchPojo.getPer_kill());
                viewHolder.binding.version.setText(matchPojo.getVersion());
                viewHolder.binding.mapType.setText(matchPojo.getMap());
                viewHolder.binding.time.setText(matchPojo.getMatch_time());
                viewHolder.binding.totalSpot.setText(matchPojo.getCount() + "/" + matchPojo.getTotal_spot());

                float spotleft = (float) matchPojo.getTotal_spot() - matchPojo.getCount();

                viewHolder.binding.spotLeft.setText("Only " + spotleft + " Spot Left");



                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, MatchDetailActivity.class).putExtra("match_id", matchPojo.getId()));
                    }
                });

                viewHolder.binding.btnJoin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showDialog(matchPojo.getId());
                    }
                });

                viewHolder.progressView.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
                    @Override
                    public void onProgressChanged(float value) {
                        Log.d(TAG, "Progress Changed: " + value);
                    }
                });

                viewHolder.progressView.setSpinningBarLength(matchPojo.getCount());
                viewHolder.progressView.setText(matchPojo.getCount() + "/" + matchPojo.getTotal_spot());
                viewHolder.progressView.setTextMode(TextMode.TEXT); // show text while spinning
                viewHolder.progressView.setUnitVisible(false);
                viewHolder.progressView.setUnitVisible(false);
                viewHolder.progressView.setValueAnimated(matchPojo.getTotal_spot(), 1500);
                viewHolder.progressView.setAutoTextSize(true);


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

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        AdapterTodaymatchBinding binding;
        CircleProgressView progressView;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
            progressView = itemView.findViewById(R.id.circleView);




        }
    }

    private void showDialog(final String match_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = activity.getLayoutInflater().inflate(R.layout.join_dialog, null);
        builder.setView(view);

        TextView cancelBtn = view.findViewById(R.id.btn_cancel);
        TextView joinBtn = view.findViewById(R.id.btn_join);

        TextView totalCoins = view.findViewById(R.id.totalcoins);
        TextView totalRs = view.findViewById(R.id.totalRs);

        final EditText paubgname = view.findViewById(R.id.edt_pubg_name1);

        RadioGroup rgType = view.findViewById(R.id.rg_type);
        final RadioButton rbCoin = view.findViewById(R.id.rb_coin);
        final RadioButton rbRupee = view.findViewById(R.id.rb_rs);

        totalCoins.setText("Coins (" + storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.TOTAL_COINS) + ")");
        totalRs.setText("Rupees (" + storeUserData.getString(com.prisminfoways.ultimate.helper.Constants.TOTAL_RUPEE) + ")");



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
                    Toast.makeText(context, "Please Enter your Pubg Name", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    callJoinMatchApi(paubgname.getText().toString(), match_id, amount);
                }

            }
        });

        dialog.show();
    }

    private void callJoinMatchApi(String pubgname, String matchid, String amount) {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_PUBG_NAME, pubgname);
        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_MATCH_ID, matchid);
        map.put(com.prisminfoways.ultimate.helper.Constants.JOIN_AMOUNT, amount);

        new AddShow().handleCall(activity, Constants.TAG_JOIN_MATCH, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("status").equals("1")) {
                        Toast.makeText(context, "" + jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "" + jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
}
