package com.cn.android.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.CarTypeBean;
import com.cn.android.bean.LogisticsMsgBean;
import com.hjq.image.ImageLoader;

public class LogisticsAdapter extends BaseQuickAdapter<LogisticsMsgBean, BaseViewHolder> {
    private Context context;

    public LogisticsAdapter(Context context) {
        super(R.layout.item_logistics_msg);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LogisticsMsgBean item) {
        ImageView imageView=helper.getView(R.id.logistics_img);
        ImageLoader.with(context).circle(10).load(item.getLogisticsImg()).into(imageView);
        helper.setText(R.id.logistics_state,item.getLogisticsState());
        helper.setText(R.id.logistics_msg1,item.getLogisticsMsg1());
        helper.setText(R.id.logistics_msg2,item.getLogisticsMsg2());
        helper.setText(R.id.logistics_msg3,item.getLogisticsMsg3());
        helper.addOnClickListener(R.id.logistics_state);
    }
}
