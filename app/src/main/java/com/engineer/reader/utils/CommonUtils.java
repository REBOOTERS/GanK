package com.engineer.reader.utils;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class CommonUtils {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 判断地址是否来自微博
     *
     * @param url
     * @return
     */
    public static boolean isWeibo(String url) {
        if (url.startsWith("http://weibo.com")) {
            return true;
        } else {
            return false;
        }
    }

    public static String formatTime(String originTime) {
        String[] times = originTime.split("T");
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DATE);
        String date = times[0] + " ";
        String time = times[1].substring(0, 8);
        return date + time;
    }
}
