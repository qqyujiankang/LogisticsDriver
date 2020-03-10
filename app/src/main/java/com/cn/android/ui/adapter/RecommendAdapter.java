package com.cn.android.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.RecommendBean;

public class RecommendAdapter extends BaseQuickAdapter<RecommendBean, BaseViewHolder> {
    private Context context;

    public RecommendAdapter(Context context) {
        super(R.layout.item_recommend);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendBean item) {
        helper.setText(R.id.address_title1,item.getAddressTitle1());
        helper.setText(R.id.address_content1,item.getAddressContent1());
        helper.setText(R.id.address_title2,item.getAddressTitle2());
        helper.setText(R.id.address_content2,item.getAddressContent2());
        helper.setText(R.id.address_msg,"货物信息："+item.getAddressMsg());
    }
}
