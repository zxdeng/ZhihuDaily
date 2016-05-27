package com.poison.httplibrary;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class HttpUtils {
    private static VolleyManager mVolleyManager = null;
//    private static Context mContext = null;
    public static void init(Context context){
//        mContext = context;
//        Glide.get(context).register(GlideUrl.class,InputStream.class,new OkHttpUrlLoader.Factory(new OkHttpClient()));
        mVolleyManager.initVolleyManager(context);
        mVolleyManager = VolleyManager.getInstance();
    }

    /**
     * 根据url显示图片
     * @param context context
     * @param url url地址
     * @param imageView ImageView控件
     */
    public static void loadImg(Context context,String url, ImageView imageView){
//        Glide.with(context).load(url).into(imageView);
    }

    /**
     * 根据url显示图片
     * @param context context
     * @param url url地址
     * @param errorImg 加载错误的图片
     * @param loadImg 加载中图片
     * @param imageView ImageView控件
     */
    public static void loadImg(Context context,String url,int loadImg,int errorImg, ImageView imageView){
//        Glide.with(context).load(url).error(errorImg).placeholder(loadImg).into(imageView);
    }

    /**
     * 通过get方式获取数据
     * @param tag 标记
     * @param url 链接
     * @param callback 回调函数
     * @return StringRequest
     */
    public static StringRequest stringRequestWithGet(Object tag, String url,final NetWorkCallback<String> callback){
       return mVolleyManager.strRequest(tag,url,callback);
    }

    /**
     * 通过post方式获取数据
     * @param tag 标记
     * @param url 链接
     * @param params 传入的参数
     * @param callback 回调函数
     * @return StringRequest
     */
    public static StringRequest stringRequestWithPost(Object tag, String url, Map<String, String> params, NetWorkCallback callback){
        return mVolleyManager.strPostRequest(tag, url, params, callback);
    }

    /**
     * 通过get方式获取数据
     * @param tag 标记
     * @param url 链接
     * @param clazz 类型
     * @param callback 回调函数
     * @return GsonRequest<T>
     */
    public static <T>  GsonRequest<T> gsonRequestWithGet(Object tag, String url, Class<T> clazz,NetWorkCallback callback){
        return mVolleyManager.gsonGetRequest(tag, url, clazz, callback);
    }

    /**
     * 通过post方式获取数据
     * @param tag 标记
     * @param params 传入的参数
     * @param url 链接
     * @param clazz 类型
     * @param callback 回调函数
     * @return GsonRequest<T>
     */
    public static <T> GsonRequest<T> gsonRequestWithPost(Object tag, Map<String, String> params, String url, Class<T> clazz, final NetWorkCallback callback){
        return mVolleyManager.gsonPostRequest(tag, params, url, clazz, callback);
    }

    /**
     * 通过get方式获取数据
     * @param tag 标记
     * @param url 链接
     * @param typeToken 列表类型
     * @param callback 回调函数
     * @return GsonRequest<T>
     */
    public static <T> GsonRequest<T> gsonRequestByTypeToKenWithGet(Object tag, String url, TypeToken typeToken, final NetWorkCallback callback){
        return mVolleyManager.gsonGetRequestByTypeToKen(tag, url, typeToken, callback);
    }

    /**
     * 通过post方式获取数据
     * @param tag 标记
     * @param params 传入的参数
     * @param url 链接
     * @param typeToken 列表类型
     * @param callback 回调函数
     * @return GsonRequest<T>
     */
    public static <T> GsonRequest<T> gsonRequestByTypeToKenWithPost(Object tag, Map<String, String> params, String url, TypeToken typeToken, final NetWorkCallback callback){
        return mVolleyManager.gsonPostRequestByTypeToKen(tag, params, url, typeToken, callback);
    }
}
