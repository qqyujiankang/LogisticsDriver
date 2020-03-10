package com.cn.android.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.CarTypeBean;
import com.cn.android.bean.ListModelBean;

public class BankAdapter extends BaseQuickAdapter<ListModelBean, BaseViewHolder> {
    private Context context;

    public BankAdapter(Context context) {
        super(R.layout.item_bank);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ListModelBean item) {
        helper.setText(R.id.bank_name,item.getName());
    }
}
