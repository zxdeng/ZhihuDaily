package com.poison.httplibrary;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16 0016.
 */
public class GsonRequest<T> extends Request<T>{

    private Listener<T> mListener;
    private Class<T> mClass;
    private Gson mGson = new Gson();
    private TypeToken<T> mTypeToken;
    private Map<String,String> mParams;
    private Map<String,String> mHeaderParams;

    public GsonRequest(int method, Map<String, String> params, String url, Class<T> clazz, Listener<T> listener,
                       ErrorListener errorListener) {
        super(method, url, errorListener);
        mClass = clazz;
        mListener = listener;
        mParams = params;
    }


    public GsonRequest(int method, Map<String, String> params, String url, TypeToken<T> typeToken, Listener<T> listener,
                       ErrorListener errorListener) {
        super(method, url, errorListener);
        mTypeToken = typeToken;
        mListener = listener;
        mParams = params;
    }

    //get
    public GsonRequest(String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        this(Method.GET, null, url, clazz, listener, errorListener);
    }

    public GsonRequest(String url, TypeToken<T> typeToken, Listener<T> listener, ErrorListener errorListener) {

        this(Method.GET, null, url, typeToken, listener, errorListener);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String str = new String(response.data,"utf-8");
            String jsonString = new String(str.getBytes(), HttpHeaderParser.parseCharset(response.headers));
            if (mTypeToken == null)
                return Response.success(mGson.fromJson(jsonString, mClass),
                        HttpHeaderParser.parseCacheHeaders(response));//用Gson解析返回Java对象
            else
                return (Response<T>) Response.success(mGson.fromJson(jsonString, mTypeToken.getType()),
                        HttpHeaderParser.parseCacheHeaders(response));//通过构造TypeToken让Gson解析成自定义的对象类型

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
