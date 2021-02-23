package com.prisminfoways.ultimate.activity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.databinding.ActivityUpdateBinding;
import com.prisminfoways.ultimate.helper.Constants;
import com.prisminfoways.ultimate.helper.StoreUserData;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding binding;
    StoreUserData storeUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update);

        storeUserData = new StoreUserData(this);

        binding.updateMessage.setText(storeUserData.getString(Constants.UPDATE_MESSAGE));

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(storeUserData.getString(Constants.APP_LINK))));
            }
        });

        if (storeUserData.getString(Constants.SKIP).equals("1")) {
            binding.btnSkip.setVisibility(View.VISIBLE);
        } else {
            binding.btnSkip.setVisibility(View.GONE);
        }

        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                finish();
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(storeUserData.getString(Constants.APP_LINK))));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
