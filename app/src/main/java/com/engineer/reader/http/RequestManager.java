package com.engineer.reader.http;

import com.engineer.reader.App;
import com.engineer.reader.beans.GanHuo;
import com.engineer.reader.beans.GanHuoShell;
import com.engineer.reader.db.DBManager;
import com.engineer.reader.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 管理所有请求类
 */
public class RequestManager {


    public static void getGankData(Object tag, final String url, final String type, final String pageCount, final boolean isCache, final HttpListener listener) {
        final DBManager dbManager = new DBManager();

        if (isCache) {

            if (!dbManager.isOverTime(url)) {
                //读取缓存数据
                String data = dbManager.getCache(url);
                if (!"".equals(data)) {
                    //解析json数据并返回成功回调
                    Gson mGson = new Gson();
                    List<GanHuo> mGanHuos = mGson.fromJson(data, new TypeToken<List<GanHuo>>() {
                    }.getType());
                    listener.onSuccess(mGanHuos);
                    return;
                }
            } else {
                dbManager.delCache(url);
            }


        }

//        if (!NetworkUtils.isConnectedByState(App.getContext())) {
//            listener.onFailure(5, "网络开小差了！！");
//            return;
//        }
        if (!NetworkUtils.isNetAvailable(App.getContext())) {
            listener.onFailure(5, "当前网络不可用！！");
            return;
        }


        GankService mGankService = GenServiceUtil.createService(GankService.class);
        retrofit2.Call<GanHuoShell> mCall = mGankService.getlist(type, "10", pageCount);
        mCall.enqueue(new retrofit2.Callback<GanHuoShell>() {
            @Override
            public void onResponse(retrofit2.Call<GanHuoShell> call, retrofit2.Response<GanHuoShell> response) {
                GanHuoShell mGanHuoShell = response.body();
                //判断error字段是否存在，不存在返回失败信息并结束请求
                if (mGanHuoShell.isError() == true) {
                    listener.onFailure(0, "error key not exists!!");
                    return;
                }

                if (mGanHuoShell.getResults() == null) {
                    listener.onFailure(0, "results key not exists!!");
                    return;
                }

                List<GanHuo> mGanHuos = mGanHuoShell.getResults();


                //TODO 缓存
                if (isCache) {
                    //插入缓存数据库
                    Gson mGson = new Gson();
                    String results = mGson.toJson(mGanHuos);
                    dbManager.insertData(url, results);
                }

                listener.onSuccess(mGanHuos);


            }

            @Override
            public void onFailure(retrofit2.Call<GanHuoShell> call, Throwable t) {
                listener.onFailure(4, t.getLocalizedMessage());
            }
        });

    }


    /**
     * 根据tag取消请求
     *
     * @param tag 标签
     */
    public static void cancelRequest(Object tag) {
//        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
//            if (call.request().tag().equals(tag)) {
//                call.cancel();
//            }
//        }
//        for (Call call : okHttpClient.dispatcher().runningCalls()) {
//            if (call.request().tag().equals(tag)) {
//                call.cancel();
//            }
//        }
    }


}