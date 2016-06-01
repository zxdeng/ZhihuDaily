package com.poison.zhihu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poison.httplibrary.NetWorkCallback;
import com.poison.zhihu.R;
import com.poison.zhihu.data.SubNewsData;
import com.poison.zhihu.utils.Constant;
import com.poison.zhihu.utils.HttpUtils;
import com.poison.zhihu.view.CircleImageView;

/**
 * Description：栏目详情页
 * Created by poison on 2016/6/1 0001.
 */
public class ListDataFragment extends BaseLoadFragment {
    private static final String TAG = "ListDataFragment";
    private int mId = 11;
    private SubNewsData mSubNewsData;
    private View mView;

    private ImageView mImageView;
    private TextView mDescTv;
    private LinearLayout mEditorLL;
    private RecyclerView mRecyclerView;

    private void assignViews() {
        mImageView = (ImageView) mView.findViewById(R.id.fg_list_data_image);
        mDescTv = (TextView) mView.findViewById(R.id.fg_list_data_desc);
        mEditorLL = (LinearLayout) mView.findViewById(R.id.fg_list_data_editors);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fg_list_data_recycle);
    }


    @Override
    protected void loadData() {
//        mId = getArguments().getInt("id");
        HttpUtils.gsonRequestWithGet(TAG, Constant.THEMENEWS + mId, SubNewsData.class, new NetWorkCallback<SubNewsData>() {
            @Override
            public void onSuccess(SubNewsData responseObj) {
                mSubNewsData = responseObj;
                checkData(responseObj.toString());
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
//        Glide.with(this).load(mSubNewsData.getImage()).centerCrop().into(mImageView);
        mDescTv.setText(mSubNewsData.getDescription());
        for (int i = 0; i < mSubNewsData.getEditors().size(); i++) {
            addEditor(mSubNewsData.getEditors().get(i));
        }
        return mView;
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
