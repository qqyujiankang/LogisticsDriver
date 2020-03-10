package com.cn.android.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.hjq.image.ImageLoader;


public class TakePictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    Context context;

    public TakePictureAdapter(Context context) {
        super(R.layout.item_picture);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.picture);
        ImageLoader.with(context).load(item).into(imageView);
    }

}
