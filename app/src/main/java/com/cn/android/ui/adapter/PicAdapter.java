package com.cn.android.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.hjq.image.ImageLoader;


public class PicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    Context context;

    public PicAdapter(Context context) {
        super(R.layout.item_img);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.iv_photo_image);
        if (getData().size() - 1 != helper.getLayoutPosition()) {
            ImageLoader.with(context).load(item).into(imageView);
        } else {
                ImageLoader.with(context).load(R.drawable.add_imag).into(imageView);
        }
    }

}
