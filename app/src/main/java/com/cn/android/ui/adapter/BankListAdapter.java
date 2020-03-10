package com.cn.android.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.BankBean;
import com.cn.android.utils.HideStringUtil;

public class BankListAdapter extends BaseQuickAdapter<BankBean, BaseViewHolder> {
    private Context context;

    public BankListAdapter(Context context) {
        super(R.layout.item_my_bank);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BankBean item) {
        helper.setText(R.id.bank_name,item.getBankname());
        helper.setText(R.id.bank_num, HideStringUtil.hideCardNo(item.getBankno()));
    }
}
