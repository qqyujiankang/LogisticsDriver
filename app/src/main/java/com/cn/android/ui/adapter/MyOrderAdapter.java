package com.cn.android.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.MiddleBean;

public class MyOrderAdapter extends BaseQuickAdapter<MiddleBean, BaseViewHolder> {
    private Context context;

    public MyOrderAdapter(Context context) {
        super(R.layout.item_order);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MiddleBean item) {
        helper.setText(R.id.f_title,item.getSendgoodscity()+item.getSendgoodsarea());
        helper.setText(R.id.f_content,item.getSendgoodsaddress());
        helper.setText(R.id.t_title,item.getPickgoodscity()+item.getPickgoodsarea());
        helper.setText(R.id.t_content,item.getPickgoodsaddress());
        helper.setText(R.id.freight,"运价：￥"+item.getFreightprice());
        helper.setText(R.id.tv_wvs,"重量："+item.getGoodsweight()+"吨 件数："+item.getGoodsnumber()+"件 体积："+item.getGoodsvolume()+"方");
        if(null!=item.getCtime()){
            helper.setText(R.id.time,"预计装货时间："+item.getCtime());
        }else{
            helper.setGone(R.id.time,false);
        }
        helper.addOnClickListener(R.id.next_step);
        helper.addOnClickListener(R.id.kefu);
        TextView textView=helper.getView(R.id.next_step);
        textView.setEnabled(true);
        //0选择提货时间 1申请待时(上传开始照片) 2装车拍照 3申请待时(上传结束照片) 4上传回单 5报销费用 6邮寄单号填写 7已完成待入账 8已完成已入账
        switch (item.getStatus()) {
            case  0:
                textView.setText("选择提货时间");
                break;
            case  2:
                textView.setText("装车拍照");
                break;
            case  1:
                textView.setText("申请待时");
                break;
            case  3:
                textView.setText("结束待时");
                break;
            case  4:
                textView.setText("上传回单");
                break;
            case  5:
                textView.setText("报销费用");
                break;
            case  6:
                textView.setText("邮寄单号填写");
                break;
            case  7:
                textView.setText("已完成待入账");
                textView.setEnabled(false);
                break;
            case  8:
                textView.setText("已完成已入账");
                textView.setEnabled(false);
                break;
        }
    }
}
