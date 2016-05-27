package com.poison.httplibrary;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public interface NetWorkCallback<T> {
    void onSuccess(T responseObj);
    void onError(Exception e);
}
