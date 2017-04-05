package com.engineer.reader.http;


/**
 * Created by rookie on 2016/7/13.
 */
public interface HttpListener {
    void onSuccess(Object result);

    void onFailure(int errorType, String message);
}
