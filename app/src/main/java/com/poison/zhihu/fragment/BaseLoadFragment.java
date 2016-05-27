package com.poison.zhihu.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poison.loadpagerlibrary.LoadPager;

/**
 * Description：需要联网加载数据的页面基类，统一处理状态
 * Created by poison on 2016/5/27 0027.
 */
public abstract class BaseLoadFragment extends BaseFragment {
    protected LoadPager mLoadPager = null;

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }
}
