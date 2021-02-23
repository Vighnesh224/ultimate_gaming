package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.adapter.ProductsListAdapter;
import com.prisminfoways.ultimate.pojo.ProductListPojo;
import com.wang.avi.CustomLoader;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;

public class BuyProductsActivity extends AppCompatActivity {



    private ProductsListAdapter productsListAdapter;
    private RecyclerView recycleproductslist;

    CustomLoader customLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_products);

        customLoader = new CustomLoader(this, false);
        ImageView imgBack;
        imgBack = findViewById(R.id.imgBack);

        recycleproductslist = findViewById(R.id.recycle_products_list);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });







        getProductsList();

    }

    private void getProductsList() {

        if(customLoader !=null){
            customLoader.showLoader();
        }

        new AddShow().handleCall(BuyProductsActivity.this, Constants.TAG_PRODUCTS_LIST, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.e("Gamelist","onLoading" + o.toString());
                try{

                    ProductListPojo gameListPojo = new Gson().fromJson(o.toString(),ProductListPojo.class);

                    if(gameListPojo !=null){
                        if(gameListPojo.getStatus().equals("1")){

                            recycleproductslist.setLayoutManager(new GridLayoutManager(BuyProductsActivity.this, 2));
                            productsListAdapter = new ProductsListAdapter(BuyProductsActivity.this,gameListPojo.getData());
                            recycleproductslist.setAdapter(productsListAdapter);
                        } else {
                            Log.d("TAG","0");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d("Gamelist","onFailed"+o.toString());

            }
        },false);

    }
}