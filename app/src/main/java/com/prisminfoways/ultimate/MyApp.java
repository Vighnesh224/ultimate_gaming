package com.prisminfoways.ultimate;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.onesignal.OneSignal;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
