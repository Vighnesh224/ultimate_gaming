package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.prisminfoways.ultimate.R;

public class ProductsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        String title;
        String brand;
        String image;
        String price;
        String discount;
        String description;
        final String url;
        ImageView imgProduct;
        ImageView imgBack;
        TextView txtProductName;
        TextView txtProductDes;
        TextView txtPrice;
        TextView txtDiscountPrice;
        TextView txtProductAllDes;
        TextView txtBuy;
        imgBack = findViewById(R.id.imgBack);
        imgProduct = findViewById(R.id.imgProduct);
        txtProductName = findViewById(R.id.txtProductName);
        txtProductDes = findViewById(R.id.txtProductDes);
        txtPrice = findViewById(R.id.txtPrice);
        txtDiscountPrice = findViewById(R.id.txtDiscountPrice);
        txtProductAllDes = findViewById(R.id.txtProductAllDes);
        txtBuy = findViewById(R.id.txtBuy);


        title =  getIntent().getStringExtra("title");
        brand = getIntent().getStringExtra("brand");
        image = getIntent().getStringExtra("image");
        price = getIntent().getStringExtra("price");
        discount = getIntent().getStringExtra("discount");
        description = getIntent().getStringExtra("description");
        url = getIntent().getStringExtra("url");

        Glide.with(this).load(image).into(imgProduct);

        txtProductName.setText(title);
        txtProductDes.setText(brand);
        txtPrice.setText(price);
        txtDiscountPrice.setText(discount);
        txtProductAllDes.setText(description);

        txtBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}