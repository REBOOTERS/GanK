package com.engineer.reader.http;

import android.util.Log;

import com.engineer.reader.App;
import com.engineer.reader.beans.GanHuo;
import com.engineer.reader.beans.GanHuoShell;
import com.engineer.reader.db.DBManager;
import com.engineer.reader.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 管理所有请求类
 */
public class RequestManager {
    private static final String ERROR = "error";
    private static final String RESULTS = "results";
//    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .addInterceptor(new LoggingInterceptor())
//            .build();


    public static void getGankData(Object tag, final String url, final String type, final String pageCount, final boolean isCache, final HttpListener listener) {
        final DBManager dbManager = new DBManager();

        if (isCache) {
            //读取缓存数据
            String data = dbManager.getData(url);
            if (!"".equals(data)) {
                //解析json数据并返回成功回调
                Object o = null;
                Gson mGson = new Gson();
                List<GanHuo> mGanHuos = mGson.fromJson(data, new TypeToken<List<GanHuo>>() {
                }.getType());
                listener.onSuccess(mGanHuos);
            }
        }

        if (!NetworkUtils.isConnectedByState(App.getContext())) {
            listener.onFailure(5, "网络开小差了！！");
            return;
        }
        if (!NetworkUtils.isNetAvailable(App.getContext())) {
            listener.onFailure(5, "当前网络不可用！！");
            return;
        }


        GankService mGankService = GenServiceUtil.createService(GankService.class);
        retrofit2.Call<GanHuoShell> mCall = mGankService.getlist(type, "30", pageCount);
        mCall.enqueue(new retrofit2.Callback<GanHuoShell>() {
            @Override
            public void onResponse(retrofit2.Call<GanHuoShell> call, retrofit2.Response<GanHuoShell> response) {
                GanHuoShell mGanHuoShell = response.body();
                Log.e("onResponse", "------> " + mGanHuoShell.getResults().size());
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

    /**
     * 请求响应日志信息，方便debug
     */
    public static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Logger.i(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Logger.i(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}