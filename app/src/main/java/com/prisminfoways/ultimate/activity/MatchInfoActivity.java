package com.prisminfoways.ultimate.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityMatchInfoBinding;
import com.prisminfoways.ultimate.helper.StoreUserData;

public class MatchInfoActivity extends AppCompatActivity {

    ActivityMatchInfoBinding binding;
    StoreUserData storeUserData;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_match_info);

        storeUserData = new StoreUserData(this);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        binding.txtMatchinfo.setText(Html.fromHtml(getIntent().getStringExtra("matchRule")));



    }
}
