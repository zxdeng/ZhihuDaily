package com.poison.zhihu.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poison.loadpagerlibrary.LoadPager;

import java.util.List;

/**
 * Description：需要联网加载数据的页面基类，统一处理状态
 * Created by poison on 2016/5/27 0027.
 */
public abstract class BaseLoadFragment extends BaseFragment {
    private LoadPager mLoadPager = null;

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(final LayoutInflater inflater, final ViewGroup container) {
        if (mLoadPager == null) {
            mLoadPager = new LoadPager(getActivity()) {
                @Override
                public View createSuccessView() {
                    return BaseLoadFragment.this.createSuccessView();
                }

                @Override
                protected void load() {
                    BaseLoadFragment.this.loadData();
                }
            };
        }
        return mLoadPager;
    }

    protected abstract void loadData();

    protected abstract View createSuccessView();

    /**
     * 根据String类型的结果判断显示的页面
     *
     * @param resultData
     */
    protected void checkData(String resultData) {
        if (resultData == null) {
            mLoadPager.showPage(LoadPager.STATE_ERROR);
        } else if (resultData.isEmpty()) {
            mLoadPager.showPage(LoadPager.STATE_EMPTY);
        } else {
            mLoadPager.showPage(LoadPager.STATE_SUCCESS);
        }

    }

    /**
     * 显示错误页面
     */
    protected void showErrorView(){
        mLoadPager.showPage(LoadPager.STATE_ERROR);
    }

    /**
     * 根据List类型的结果判断显示的页面
     *
     * @param resultData
     */
    protected void checkData(List resultData) {
        if (resultData == null) {
            mLoadPager.showPage(LoadPager.STATE_ERROR);
        } else if (resultData.size() == 0) {
            mLoadPager.showPage(LoadPager.STATE_EMPTY);
        } else {
            mLoadPager.showPage(LoadPager.STATE_SUCCESS);
        }
    }

    protected void show(){
        if (mLoadPager!= null){
            mLoadPager.show();
        }
    }
}
