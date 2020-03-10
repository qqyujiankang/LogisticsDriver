package com.cn.android.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.MessageBean;

public class ReceiptAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
    private Context context;

    public ReceiptAdapter(Context context) {
        super(R.layout.item_receipt);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.content, item.getContent());
        helper.setText(R.id.time, item.getTitle());
    }
}
