package com.poison.httplibrary;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by Poison on 2016/3/17 0017.
 * volley的管理类，使用单例模式，使用时在项目启动时初始化
 */
public class VolleyManager {
    private static VolleyManager mVolleyManager = null;
    private static  RequestQueue mRequestQueue = null;

    private VolleyManager(Context context) {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(context,new OkHttp3Stack(new OkHttpClient()));
    }

    public static VolleyManager getInstance(){
        return mVolleyManager;
    }

    public static synchronized void initVolleyManager(Context context){
        if (mVolleyManager == null){
            mVolleyManager = new VolleyManager(context);
        }
    }
//    public static synchronized VolleyManager newInstance(Context context) {
//        if (mVolleyManager == null) {
//            mVolleyManager = new VolleyManager(context);
//        }
//        return mVolleyManager;
//    }

    private <T> Request<T> add(Request<T> request) {
        return mRequestQueue.add(request);//添加请求到队列
    }

    /**
     * @param tag 标记
     * @param url 链接
     * @param callback 回调接口
     * @return 返回一个request实例
     */
    public StringRequest strRequest(Object tag, String url,final NetWorkCallback<String> callback) {
        Listener<String> listener = new Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        };
        ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        };
        MyStringRequest request = new MyStringRequest(url, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
//        return  this.StrPostRequest(tag,url,null,callback);
    }

    public StringRequest strPostRequest(Object tag, String url, final Map<String, String> params, final NetWorkCallback<String> callback){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        };
        MyStringRequest request = new MyStringRequest(url,params, listener, errorListener);
        request.setTag(tag);
        add(request);
        return  request;
    }

    /**
     * Get
     */
    public <T> GsonRequest<T> gsonGetRequest(Object tag, String url, Class<T> clazz,final NetWorkCallback<T> callback) {
        Response.Listener<T> listener = new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                callback.onSuccess(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        };
        GsonRequest<T> request = new GsonRequest<T>(url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * Post
     */
    public <T> GsonRequest<T> gsonPostRequest(Object tag, Map<String, String> params, String url, Class<T> clazz, final NetWorkCallback<T> callback) {
        Response.Listener<T> listener = new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                callback.onSuccess(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        };
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * 根据TypeToken返回数据
     */
    public <T> GsonRequest<T> gsonGetRequestByTypeToKen(Object tag, String url, TypeToken typeToken, final NetWorkCallback callback) {
        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                callback.onSuccess(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        };
        GsonRequest<T> request = new GsonRequest<T>(url, typeToken, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    public <T> GsonRequest<T> gsonPostRequestByTypeToKen(Object tag, Map<String, String> params, String url, TypeToken typeToken, final NetWorkCallback callback) {
        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                callback.onSuccess(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        };
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, typeToken, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * 根据tag取消请求
     */
    public void cancel(Object tag) {
        if (mRequestQueue != null) mRequestQueue.cancelAll(tag);
    }


}
