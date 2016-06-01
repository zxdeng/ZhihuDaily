package com.poison.zhihu;

import android.app.Application;

import com.poison.zhihu.utils.HttpUtils;

/**
 * Description：
 * Created by poison on 2016/6/1 0001.
 */
public class App extends Application {

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtils.init(this);
        app = this;
    }

    public static App getInstance(){
        return app;
    }
}
