package com.prisminfoways.ultimate.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.pojo.RedeemOptionPojo;
import com.wang.avi.CustomLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.ErrorListner;

import static android.content.ContentValues.TAG;

public class RedeemOptionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;
    private List<RedeemOptionPojo.DataBean> data = new ArrayList<>();
    CustomLoader customLoader;

    public RedeemOptionListAdapter(Activity context, List<RedeemOptionPojo.DataBean> data) {
        this.context = context;
        this.data = data;

        customLoader = new CustomLoader(context, false);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View gamelistview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_redeem_option_list, viewGroup, false);
        return new MyViewHolder(gamelistview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder holder = (MyViewHolder) viewHolder;

        holder.title.setText(data.get(i).getPayment_method());
        holder.coins.setText("₹ "+data.get(i).getAmount());

        holder.subtitle.setText(data.get(i).getSub_title());

        holder.crdAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfoDialog(data.get(i).getPayment_method(),data.get(i).getPlaceholder(),data.get(i).getAmount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView coins;
        private TextView subtitle;
        private CardView crdAddMoney;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            coins = (TextView) itemView.findViewById(R.id.coins);
            subtitle = itemView.findViewById(R.id.subtitle);
            crdAddMoney = itemView.findViewById(R.id.crdAddMoney);
        }

    }

    private void showInfoDialog(final String payment_method, String placeholder, final String amount) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View view = context.getLayoutInflater().inflate(R.layout.redeem_dialog, null);
        dialogBuilder.setView(view);

        TextView redeemMethod = view.findViewById(R.id.redeemMethod);
        TextView btnok = view.findViewById(R.id.btn_ok);
        final EditText edtpubgname1 = view.findViewById(R.id.edt_pubg_name);

        final AlertDialog dialog = dialogBuilder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        redeemMethod.setText(payment_method);
        edtpubgname1.setHint(placeholder);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                callRedeemApi(edtpubgname1.getText().toString().trim()+"-"+payment_method,amount);

            }
        });

        dialog.show();
    }

    private void callRedeemApi(String paytmno, String amount) {

        customLoader.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.REDEEM_DETAIL, paytmno);
        map.put(Constants.REDEEM_AMOUNT, amount);

        new AddShow().handleCall(context, add.Native.com.admodule.Constants.TAG_MAKE_REQUEST, map, new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.d(TAG, "onLoaded: " + o.toString());

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());

                    CustomLoader.showErrorDialog(context, jsonObject.getString("msg"));
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
