package com.prisminfoways.ultimate;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.System.*;

public class AppConstant {
    private AppConstant(){}
    static String dateformat = "yyyy-MM-dd HH:mm:ss";
    public static long getTimerDifference(Activity activity,String startTime,String endTime) {
        long timeDiff = 0;

        try {
            long systemCurrentTime = Calendar.getInstance().getTimeInMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateformat);
            Date mStartTime = simpleDateFormat.parse(endTime);
            long startTimeInMilli = mStartTime.getTime();

            timeDiff = startTimeInMilli - systemCurrentTime;
            Log.d("TimeDiff", "getTimerDifference: " + timeDiff);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeDiff;
    }

    public static String getTodayDate(){
        Date c = Calendar.getInstance().getTime();
        out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(c);
    }

    public static String getTimeLeftForamt(String time) {

        String timeleft = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);

        try {

            Date oldDate = dateFormat.parse(time);
            String str;
            SimpleDateFormat tmp = new SimpleDateFormat("d MMM yyyy");
            str = tmp.format(oldDate);

            Date date = new Date();
            String strDate = new SimpleDateFormat(dateformat).format(date);

            Date currentDate = dateFormat.parse(strDate);

            long different = oldDate.getTime() - currentDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            if (oldDate.before(currentDate)) {
                timeleft = str;
            } else if (oldDate.equals(currentDate)) {
                timeleft = str;
            } else {
                timeleft = elapsedDays + "d : " + elapsedHours + "h : " + elapsedMinutes + "m : " + elapsedSeconds + "s ";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeleft;
    }

    public static boolean appInstalledOrNot(Activity activity, String uri) {
        PackageManager pm = activity.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static int getInstallAppCount(Activity activity) {
        int numberOfNonSystemApps = 0;

        List<ApplicationInfo> appList = activity.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo info : appList) {
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                numberOfNonSystemApps++;
            }
        }
        return numberOfNonSystemApps;
    }



}
