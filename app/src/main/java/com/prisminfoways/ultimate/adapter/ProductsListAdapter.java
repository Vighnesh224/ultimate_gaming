package com.prisminfoways.ultimate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.ProductsDetailsActivity;
import com.prisminfoways.ultimate.pojo.ProductListPojo;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;
    private List<ProductListPojo.DataBean> data = new ArrayList<>();

    public ProductsListAdapter(Activity activity, List<ProductListPojo.DataBean> data) {
        this.context = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View gamelistview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_list_layout, viewGroup, false);
        return new MyViewHolder(gamelistview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder holder = (MyViewHolder) viewHolder;

        holder.txtProductName.setText(data.get(i).getTitle());
        holder.txtPrice.setText(data.get(i).getPrice());
        holder.txtDiscountPrice.setText(data.get(i).getDiscount());

        Glide.with(context).load(data.get(i).getImage()).into(holder.imgProduct);

        holder.crdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductsDetailsActivity.class);
                intent.putExtra("title",data.get(i).getTitle());
                intent.putExtra("brand",data.get(i).getBrand());
                intent.putExtra("image",data.get(i).getImage());
                intent.putExtra("price",data.get(i).getPrice());
                intent.putExtra("discount",data.get(i).getDiscount());
                intent.putExtra("description",data.get(i).getDescription());
                intent.putExtra("url",data.get(i).getUrl());


                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView txtProductName;
        private TextView txtPrice;
        private TextView txtDiscountPrice;
        private CardView crdView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            txtDiscountPrice = (TextView)itemView.findViewById(R.id.txtDiscountPrice);
            crdView = (CardView) itemView.findViewById(R.id.crdView);
        }

    }
}
