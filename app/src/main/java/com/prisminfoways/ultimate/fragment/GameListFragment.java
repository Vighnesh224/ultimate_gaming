package com.prisminfoways.ultimate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.prisminfoways.ultimate.R;
import com.prisminfoways.ultimate.activity.NotificationActivity;
import com.prisminfoways.ultimate.adapter.GameListAdapter;
import com.prisminfoways.ultimate.pojo.GameListPojo;
import com.prisminfoways.ultimate.pojo.NotificationPojo;
import com.wang.avi.CustomLoader;

import java.util.HashMap;

import add.Native.com.admodule.AddShow;
import add.Native.com.admodule.Constants;
import add.Native.com.admodule.ErrorListner;
import io.supercharge.shimmerlayout.ShimmerLayout;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameListFragment extends Fragment {

    private GameListAdapter gameListAdapter;
    private RecyclerView recyclerView;

    private TextView txtMessage;
    private TextView txtnotification;
    private TextView createddate;

    CustomLoader customLoader;

    private ShimmerLayout shimmerLayoutView;
    private CoordinatorLayout llCordinate;

    public GameListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycle_game_list);

        ImageView imgNotification;
        LinearLayout llNotification;
        customLoader = new CustomLoader(getActivity(), false);

        shimmerLayoutView = view.findViewById(R.id.shimmer_layout);
        llCordinate = view.findViewById(R.id.llCordinate);

        llNotification = view.findViewById(R.id.llNotification);

        imgNotification = view.findViewById(R.id.imgNotification);
        txtMessage = view.findViewById(R.id.txtMessage);
        txtnotification = view.findViewById(R.id.txt_notification);
        createddate = view.findViewById(R.id.created_date);

        shimmerLayoutView.startShimmerAnimation();

        getGameList();

        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutmanager);

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                getActivity().startActivity(intent);
            }
        });

        callNotificationApi();

        llNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NotificationActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private void getGameList() {
        if(customLoader !=null){
            Log.d(TAG,"loader");
        }

        new AddShow().handleCall(getActivity(), Constants.TAG_GAME_LIST, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {
                customLoader.dismissLoader();
                Log.e("Gamelist","onLoading" + o.toString());
                try{

                    GameListPojo gameListPojo = new Gson().fromJson(o.toString(),GameListPojo.class);

                    if(gameListPojo !=null){
                        if(gameListPojo.getStatus().equals("1")){
                            shimmerLayoutView.stopShimmerAnimation();
                            llCordinate.setVisibility(View.VISIBLE);
                            shimmerLayoutView.setVisibility(View.GONE);

                            txtMessage.setVisibility(View.GONE);
                            gameListAdapter = new GameListAdapter(getActivity(),gameListPojo.getData());
                            recyclerView.setAdapter(gameListAdapter);
                        }else{
                            txtMessage.setVisibility(View.VISIBLE);
                            shimmerLayoutView.setVisibility(View.GONE);
                        }
                    }else{
                       txtMessage.setVisibility(View.VISIBLE);
                        shimmerLayoutView.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    txtMessage.setVisibility(View.VISIBLE);
                    shimmerLayoutView.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object o) {
                customLoader.dismissLoader();
                Log.d("Gamelist","onFailed"+o.toString());
                txtMessage.setVisibility(View.VISIBLE);
                shimmerLayoutView.setVisibility(View.GONE);
            }
        },false);
    }

    private void callNotificationApi() {

        new AddShow().handleCall(getActivity(), Constants.TAG_NOTIFICATION, new HashMap<String, String>(), new ErrorListner() {
            @Override
            public void onLoaded(Object o) {

                Log.d(TAG, "onLoaded: " + o.toString());

                NotificationPojo notificationPojo = new Gson().fromJson(o.toString(), NotificationPojo.class);

                if (notificationPojo.getStatus().equals("1")) {
                    if (!notificationPojo.getData().isEmpty()) {
                        txtnotification.setText(notificationPojo.getData().get(0).getTitle());
                        createddate.setText(notificationPojo.getData().get(0).getCreated_date());
                    } else {
                        Log.d(TAG, "onLoaded: No Data Available");
                    }
                } else {
                    Log.d(TAG,"0");
                }
            }

            @Override
            public void onFailed(Object o) {
                Log.d(TAG, "onFailed: " + o.toString());
            }
        }, false);
    }
}
