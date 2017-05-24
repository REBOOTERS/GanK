package com.engineer.reader.db;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by co-mall on 2017/5/24.
 */

public class ImgCacheManager {

    public static String getCacheDir(Context mContext) {
        String file = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cache = mContext.getExternalCacheDir();
            if (!cache.exists()) {
                cache.mkdir();
            }
            file = cache.getAbsolutePath();
        }
        return file;
    }

    public static File createFile(Context mContext, String fileName) {
        String filename = getMd5(fileName);
        if (!fileName.endsWith(".jpg")) {
            filename = filename + ".gif";
        }else {
            filename = filename + ".jpg";
        }
        File file = new File(getCacheDir(mContext), filename);
        return file;
    }



    public static boolean isFileExist(Context mContext, String fileName) {
        String filename = getMd5(fileName);
        if (!fileName.endsWith(".jpg")) {
            filename = filename + ".gif";
        }else {
            filename = filename + ".jpg";
        }
        File file = new File(getCacheDir(mContext), filename);
        return file.exists();
    }


    /**
     * 用于获取一个String的md5值
     *
     * @param str
     * @return
     */
    public static String getMd5(String str) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }
}
