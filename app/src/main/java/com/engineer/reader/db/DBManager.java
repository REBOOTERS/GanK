package com.engineer.reader.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.engineer.reader.App;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 */
public class DBManager {
    private static final String TAG = "DBManager";
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DBManager() {
        dbHelper = new DBHelper(App.getContext());
    }


    /**
     * 插入缓存
     *
     * @param url  地址
     * @param data json数据
     */
    public synchronized void insertData(String url, String data) {


        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.URL, url);
        values.put(DBHelper.DATA, data);
        values.put(DBHelper.TIME, getCurrentTime());
        db.replace(DBHelper.CACHE, null, values);
        db.close();
    }

    /**
     * 缓存是否过期
     *
     * @param url
     * @return
     */
    public boolean isOverTime(String url) {
        String saveTime = "";
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.CACHE + " WHERE URL = ?";
        Cursor mCursor = db.rawQuery(sql, new String[]{url});
        while (mCursor.moveToNext()) {
            saveTime = mCursor.getString(mCursor.getColumnIndex(DBHelper.TIME));
        }
        String currTime = getCurrentTime();

        int day = daysBetween(saveTime, currTime);

        return day > 1;

    }




    /**
     * 计算两个时间戳之间相隔天数
     *
     * @param smdate
     * @param bdate
     * @return
     */
    private int daysBetween(String smdate, String bdate) {
        Log.e(TAG, "daysBetween: add in  time: " + smdate);
        Log.e(TAG, "daysBetween: current time: " + bdate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;

        try {
            cal.setTime(sdf.parse(smdate));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            time2 = cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    private String getCurrentTime() {
        Date mDate = new Date(System.currentTimeMillis());
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mSimpleDateFormat.format(mDate);
    }


    /**
     * 根据url获取缓存数据
     *
     * @param url 地址
     * @return 数据
     */
    public synchronized String getCache(String url) {
        String result = "";
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.CACHE + " WHERE URL = ?", new String[]{url});
        while (cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex(DBHelper.DATA));
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 删除缓存
     * @param url
     */
    public void delCache(String url) {
        db=dbHelper.getWritableDatabase();
        db.delete(DBHelper.CACHE, "URL=?", new String[]{url});
    }

}
