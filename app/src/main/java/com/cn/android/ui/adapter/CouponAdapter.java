package com.cn.android.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import androidx.fragment.app.FragmentActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.CarTypeBean;
import com.cn.android.bean.CouponBean;
import com.cn.android.ui.dialog.MessageDialog;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.toast.ToastUtils;

public class CouponAdapter extends BaseQuickAdapter<CouponBean, BaseViewHolder> {
    private FragmentActivity context;

    public CouponAdapter(FragmentActivity context) {
        super(R.layout.item_coupon);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponBean item) {
        switch (item.getIsUse()) {
            case 1:
                helper.setBackgroundRes(R.id.linaer_coupon, R.mipmap.coupon_a);
                break;
            case 2:
                helper.setBackgroundRes(R.id.linaer_coupon, R.mipmap.coupon_b);
                break;
            case 3:
                helper.setBackgroundRes(R.id.linaer_coupon, R.mipmap.coupon_c);
                break;
        }
        switch (item.getType()) {
            case  1:
                helper.setText(R.id.coupon_name, "定金减免券");
                break;
        }
        helper.setText(R.id.coupon_time, item.getEtime());
        helper.setText(R.id.coupon_money, item.getMoney()+"");
        helper.setText(R.id.coupon_describe,"满￥"+item.getUseMoney()+"使用");
        helper.getView(R.id.instructions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MessageDialog.Builder(context)
                        // 标题可以不用填写
                        .setTitle("使用说明")
                        // 内容必须要填写
                        .setMessage(item.getContent())
                        // 确定按钮文本
                        .setConfirm(context.getString(R.string.common_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(null)
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .show();
            }
        });
    }
}
