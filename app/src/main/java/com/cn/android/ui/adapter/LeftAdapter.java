package com.cn.android.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.MiddleBean;

public class LeftAdapter extends BaseQuickAdapter<MiddleBean, BaseViewHolder> {
    private Context context;

    public LeftAdapter(Context context) {
        super(R.layout.item_left);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MiddleBean item) {
        helper.setText(R.id.f_title,item.getPickgoodscity()+item.getPickgoodsarea());
        helper.setText(R.id.f_content,item.getPickgoodsaddress());
        helper.setText(R.id.t_title,item.getSendgoodscity()+item.getSendgoodsarea());
        helper.setText(R.id.t_content,item.getSendgoodsaddress());
        helper.setText(R.id.tv_wvs,"重量："+item.getGoodsweight()+"吨 件数："+item.getGoodsnumber()+"件 体积："+item.getGoodsvolume()+"方");
        helper.setText(R.id.time,"预计装货时间："+item.getCtime());
        helper.addOnClickListener(R.id.button);
    }
}
