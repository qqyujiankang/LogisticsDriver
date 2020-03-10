package com.cn.android.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.AppAccountInfo;

public class CustomerAdapter extends BaseQuickAdapter<AppAccountInfo, BaseViewHolder> {
    private Context context;

    public CustomerAdapter(Context context) {
        super(R.layout.item_customer);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AppAccountInfo item) {
        helper.setText(R.id.time,item.getCtime());
        helper.setText(R.id.money,item.getPayMoney()+"");
    }
}
