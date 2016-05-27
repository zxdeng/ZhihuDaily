package com.poison.zhihu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description：所有fragment的基类
 * Created by poison on 2016/5/27 0027.
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext = null;
    protected View mRootView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater,container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initData();
    }

    /**
     * 在此方法中加载数据
     */
    protected abstract void initData();

    /**
     * 在此方法中初始化rootview
     * @param inflater
     * @param container
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    /**
     * 不带参数跳转
     */
    protected void openActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivity(intent);
    }

    /**
     * 带参数跳转
     */
    protected void openActivity(Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
