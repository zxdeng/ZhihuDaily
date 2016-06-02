package com.poison.zhihu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.poison.httplibrary.NetWorkCallback;
import com.poison.zhihu.R;
import com.poison.zhihu.adapter.ListNewsDataAdapter;
import com.poison.zhihu.adapter.RecycleViewDivider;
import com.poison.zhihu.data.SubNewsData;
import com.poison.zhihu.utils.Constant;
import com.poison.zhihu.utils.HttpUtils;
import com.poison.zhihu.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：栏目详情页
 * Created by poison on 2016/6/1 0001.
 */
public class ListDataFragment extends BaseLoadFragment {
    private static final String TAG = "ListDataFragment";
    private int mId = 13;
    private SubNewsData mSubNewsData;
    private View mView;

    private ImageView mImageView;
    private TextView mDescTv;
    private LinearLayout mEditorLL;
    private RecyclerView mRecyclerView;
    private ListNewsDataAdapter mAdapter;

    private List<SubNewsData.StoriesBean> mListDatas;

    private SwipeRefreshLayout mRefreshLayout;

    private void assignViews() {
        mImageView = (ImageView) mView.findViewById(R.id.fg_list_data_image);
        mDescTv = (TextView) mView.findViewById(R.id.fg_list_data_desc);
        mEditorLL = (LinearLayout) mView.findViewById(R.id.fg_list_data_editors);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fg_list_data_recycle);
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.fg_list_data_srl);
    }


    @Override
    protected void loadData() {
//        mId = getArguments().getInt("id");
        HttpUtils.gsonRequestWithGet(TAG, Constant.THEMENEWS + mId, SubNewsData.class, new NetWorkCallback<SubNewsData>() {
            @Override
            public void onSuccess(SubNewsData responseObj) {
                mSubNewsData = responseObj;
                if (mSubNewsData.getStories() != null){
                    mListDatas = mSubNewsData.getStories();
                }else {
                    mListDatas = new ArrayList<SubNewsData.StoriesBean>();
                }
                checkData(responseObj.toString());
                Log.d(TAG, "responseObj:" + responseObj);
            }

            @Override
            public void onError(Exception e) {
                showErrorView();
            }
        });

    }

    @Override
    protected View createSuccessView() {
        mView = mInflater.inflate(R.layout.fragment_list_data, mContainer, false);
        assignViews();
        HttpUtils.loadImg(this, mSubNewsData.getImage(), mImageView);
        mDescTv.setText(mSubNewsData.getDescription());
        for (int i = 0; i < mSubNewsData.getEditors().size(); i++) {
            addEditor(mSubNewsData.getEditors().get(i));
        }
        mAdapter = new ListNewsDataAdapter(mContext,mListDatas);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext,LinearLayoutManager.VERTICAL,10,getResources().getColor(R.color.white)));
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;
            int lastVisibleItem = 0;
            LinearLayoutManager manager = (LinearLayoutManager)mRecyclerView.getLayoutManager();
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
//                    mRefreshLayout.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
//                    handler.sendEmptyMessageDelayed(0, 3000);
                    Toast.makeText(mContext, "加载", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = manager.findLastVisibleItemPosition();

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);
                updateData();
            }
        });
        return mView;
    }

    private void updateData() {
        HttpUtils.gsonRequestWithGet(TAG, Constant.THEMENEWS + mId, SubNewsData.class, new NetWorkCallback<SubNewsData>() {
            @Override
            public void onSuccess(SubNewsData responseObj) {
                mListDatas.clear();
                if (responseObj.getStories() != null){
                    mListDatas.addAll(responseObj.getStories());
                }
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
                Toast.makeText(mContext, "刷新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(mContext, "刷新失败啦，请稍后再试！", Toast.LENGTH_SHORT).show();
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void addEditor(SubNewsData.EditorsBean editorsBean) {
        CircleImageView circleImageView = (CircleImageView) mInflater.inflate(R.layout.editor_item, mEditorLL, false);
//        HttpUtils.loadImg(this,editorsBean.getAvatar(),circleImageView);
        Glide.with(this).load(editorsBean.getAvatar()).into(circleImageView);
        mEditorLL.addView(circleImageView);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }
}
