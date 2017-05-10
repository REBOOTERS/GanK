package com.engineer.reader.http;

import com.engineer.reader.beans.GanHuoShell;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by co-mall on 2017/5/10.
 */

public interface GankService {

    @GET("{type}/{pageSize}/{page}")
    Call<GanHuoShell> getlist(@Path("type") String type,
                              @Path("pageSize") String pageSize,
                              @Path("page") String page);

    @GET("{type}/{pageSize}/{page}")
    Observable<GanHuoShell> getData(@Path("type") String type,
                                    @Path("pageSize") String pageSize,
                                    @Path("page") String page);


}
