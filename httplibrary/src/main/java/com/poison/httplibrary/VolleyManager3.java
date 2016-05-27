package com.poison.httplibrary;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by Poison on 2016/3/17 0017.
 * volley的管理类，使用单例模式，没有二次封装
 */
public class VolleyManager3 {
    private static VolleyManager3 mVolleyManager3 = null;
    private static  RequestQueue mRequestQueue = null;

    public VolleyManager3(Context context) {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(context,new OkHttp3Stack(new OkHttpClient()));
    }

//    public static synchronized void initVolleyManager3(Context context){
//        if (mVolleyManager3 == null){
//            mVolleyManager3 = new VolleyManager3(context);
//        }
//    }
    public static synchronized VolleyManager3 newInstance(Context context) {
        if (mVolleyManager3 == null) {
            mVolleyManager3 = new VolleyManager3(context);
//            DebugLog.d("VolleyManager3-->"+mVolleyManager3+"==RequestQueue-->"+mRequestQueue);
        }
        return mVolleyManager3;
    }

    private <T> Request<T> add(Request<T> request) {
//        DebugLog.d("---");
        return mRequestQueue.add(request);//添加请求到队列
    }

    /**
     * @param tag
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public StringRequest StrRequest(Object tag, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        MyStringRequest request = new MyStringRequest(url, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    public StringRequest StrPostRequest(Object tag, String url, final Map<String, String> params, Response.Listener<String> listener,
                                        Response.ErrorListener errorListener){
        MyStringRequest request = new MyStringRequest(url,params, listener, errorListener);
        request.setTag(tag);
        add(request);
        return  request;
    }

    /**
     * Get
     *
     * @param tag
     * @param url
     * @param clazz
     * @param listener
     * @param errorListener
     * @param <T>
     * @return
     */
    public <T> GsonRequest<T> GsonGetRequest(Object tag, String url, Class<T> clazz, Response.Listener<T> listener,
                                             Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * Post
     *
     * @param tag
     * @param params
     * @param url
     * @param clazz
     * @param listener
     * @param errorListener
     * @param <T>
     * @return
     */
    public <T> GsonRequest<T> GsonPostRequest(Object tag, Map<String, String> params, String url, Class<T> clazz, Response.Listener<T> listener,
                                              Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * 根据TypeToken返回数据
     * @param tag
     * @param url
     * @param typeToken
     * @param listener
     * @param errorListener
     * @param <T>
     * @return
     */
    public <T> GsonRequest<T> GsonGetRequestByTypeToKen(Object tag, String url, TypeToken typeToken, Response.Listener<T> listener,
                                             Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(url, typeToken, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    public <T> GsonRequest<T> GsonPostRequestByTypeToKen(Object tag, Map<String, String> params, String url, TypeToken typeToken, Response.Listener<T> listener,
                                              Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, typeToken, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancel(Object tag) {
        if (mRequestQueue != null) mRequestQueue.cancelAll(tag);
    }


}
