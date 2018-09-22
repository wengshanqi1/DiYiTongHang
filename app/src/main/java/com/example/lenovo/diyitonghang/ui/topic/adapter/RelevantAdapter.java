package com.example.lenovo.diyitonghang.ui.topic.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.data.Data;

import java.util.List;

public class RelevantAdapter extends BaseQuickAdapter<Data, BaseViewHolder> {
    public RelevantAdapter(int layoutResId, @Nullable List<Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Data item) {
        helper.setText(R.id.adapter_right_title, item.title);
        helper.setText(R.id.textView14, item.publishTime);
        // 加载网络图片
        Glide.with(mContext).load(item.getImageListThumb().get(0)).into((ImageView) helper.getView(R.id.adapter_right_image));
    }
}
