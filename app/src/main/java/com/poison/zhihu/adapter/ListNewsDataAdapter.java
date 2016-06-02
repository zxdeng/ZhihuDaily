package com.poison.zhihu.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.poison.zhihu.R;
import com.poison.zhihu.data.SubNewsData;
import com.poison.zhihu.utils.HttpUtils;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.MultiItemCommonAdapter;
import com.zhy.base.adapter.recyclerview.MultiItemTypeSupport;

import java.util.List;

/**
 * Description：
 * Created by poison on 2016/6/1 0001.
 */
public class ListNewsDataAdapter extends MultiItemCommonAdapter<SubNewsData.StoriesBean> {

    public ListNewsDataAdapter(Context context, List<SubNewsData.StoriesBean> datas) {
        super(context, datas, new MultiItemTypeSupport<SubNewsData.StoriesBean>(){
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == SubNewsData.ITEM_NO_PIC){
                    return R.layout.list_news_item_no_pic;
                }else {
                    return R.layout.list_news_item;
                }
            }

            @Override
            public int getItemViewType(int position, SubNewsData.StoriesBean storiesBean) {
                if (storiesBean.getImages()==null){
                    return SubNewsData.ITEM_NO_PIC;
                }else {
                    return SubNewsData.ITEM_ONE_PIC;
                }
            }
        });
    }

    @Override
    public void convert(ViewHolder holder, SubNewsData.StoriesBean storiesBean) {
        switch (holder.getLayoutId()){
            case R.layout.list_news_item:
                holder.setText(R.id.list_news_item_title,storiesBean.getTitle());
                ImageView imageView = holder.getView(R.id.list_news_item_pic);
                HttpUtils.loadImg(mContext,storiesBean.getImages().get(0),imageView);
                break;
            case R.layout.list_news_item_no_pic:
                holder.setText(R.id.list_news_item_no_pic_title,storiesBean.getTitle());
                break;
        }
    }
}
