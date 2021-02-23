package com.prisminfoways.ultimate.helper;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
    private Constants() {

    }


    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHONE = "phone";
    public static final String CODE = "code";
    public static final String MATCH_ID = "match_id";
    public static final String PAGE = "page";
    public static final String JOIN_PUBG_NAME = "pubg_name";
    public static final String JOIN_PUBG_NAME2 = "pubg_name2";
    public static final String JOIN_PUBG_NAME3 = "pubg_name3";
    public static final String JOIN_PUBG_NAME4 = "pubg_name4";

    public static final String JOIN_MATCH_ID = "match_id";
    public static final String JOIN_LUCKYDRAW_ID = "lucky_draw_id";
    public static final String JOIN_AMOUNT = "join_type";
    public static final String OLD_PASSWORD = "old_password";
    public static final String NEW_PASSWORD = "new_password";
    public static final String AMOUNT = "amount";
    public static final String CUSTOMER_ID = "cust_id";
    public static final String GAME_ID = "game_id";
    public static final String ID = "id";

    public static final String EDIT_PUBG_NAME = "pubgname";


    //** VARIABLES
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String USER_NAME = "user_name";
    public static final String TOKEN = "token";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_ID = "user_id";
    public static final String TOTAL_COINS = "total_coins";
    public static final String TOTAL_RUPEE = "total_rupee";
    public static final String TOTAL_AVAILABLE_RUPEE = "total_available_rupee";

    public static final String REDEEM_AMOUNT = "amount";
    public static final String REDEEM_DETAIL = "detail";
    public static final String PUBG_NAME = "pubgName";
    public static final String TELEGRAM_LINK = "telegram";
    public static final String ABOUT_US = "about_us";
    public static final String CUSTOMER_SUPPORT = "custom_support";
    public static final String PRIVACY_POLICY = "privacy_policy";
    public static final String TERMS_CONDITION = "terms_condition";
    public static final String FAQ = "faq";
    public static final String WEBSITE = "website";
    public static final String HOW_IT_WORK = "how_it_works";
    public static final String DATE = "date";
    public static final String INFO_MSG = "info_msg";
    public static final String UPDATE_APP_VERSION = "play_store_appversion";
    public static final String UPDATE_MESSAGE = "update_message";
    public static final String SKIP = "skip";
    public static final String APP_LINK = "play_store_applink";
    public static final String CURRENT_VERSION_NAME = "current_version_name";
    public static final String CURRENT_VERSION_CODE = "current_version_code";

    //Add Money
    public static final String CHECKSUMHASH = "CHECKSUMHASH";
    public static final String ORDER_ID = "order_id";
    public static final String PAYT_STATUS = "payt_stauts";
    public static final String MID = "mid";
    public static final String INDUSTRY_TYPE_ID = "industry_type_id";
    public static final String PAYTM_WEBSITE = "website";
    public static final String CALLBACK_URL = "callback_url";
    public static final String CHANNEL_ID = "channel_id";
    public static final String HOW_TO_JOIN_ROOM = "how_to_join_room";

    public static final String IS_ACTION = "IS_ACTION";

    public static final String AD_IMP = "AD_IMP";
    public static final String AD_CLICK = "AD_CLICK";
    public static final String AD_BANNER_CLICK = "AD_BANNER_CLICK";
    public static final String AD_WEB_CLICK = "AD_WEB_CLICK";
    public static final String AD_CLICK_MSG = "AD_CLICK_MSG";
    public static final String IS_CLICK = "AD_CLICK_MSG";

    public static final String NEW_TIMER_START = "NewTimerStart";
    public static final String WATCH_VIDEO_COUNT = "watch_video_count";
    public static final String WATCH_VIDEO_TITLE = "watch_video_title";
    public static final String WATCH_VIDEO_COIN = "watch_video_coin";
    public static final int TOTAL_WATCH_VIDEO_COUNT = 10;

    public static String timeFormat(String time) {

        String date = null;

        try {
            SimpleDateFormat simpleDateFormat24 = new SimpleDateFormat("HH:mm");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
            final Date dateObj = simpleDateFormat24.parse(time);
            Log.d("Format time", "timeFormat: " + dateObj);
            Log.d("Format time", "timeFormat: " + simpleDateFormat.format(dateObj));
            date = simpleDateFormat.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
