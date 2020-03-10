package com.cn.android.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.CarInfoBean;
import com.cn.android.bean.ListModelBean;
import com.cn.android.ui.activity.PhotoActivity;
import com.cn.android.ui.activity.ui.ImageActivity;
import com.hjq.image.ImageLoader;
import com.hjq.widget.layout.SettingBar;

public class CarInfoAdapter extends BaseQuickAdapter<CarInfoBean, BaseViewHolder> {
    private Context context;

    public CarInfoAdapter(Context context) {
        super(R.layout.item_car_info);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CarInfoBean item) {
        ((SettingBar)helper.getView(R.id.carNo)).setRightText(item.getCarNo());
        ((SettingBar)helper.getView(R.id.carModel)).setRightText(item.getCarModel());
        ((SettingBar)helper.getView(R.id.phone)).setRightText(item.getPhone());
        ImageView car_img1 = helper.getView(R.id.car_msg1);
        ImageView car_img2 = helper.getView(R.id.car_msg2);
        car_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageActivity.start(context,item.getRunImg());
            }
        });
        car_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageActivity.start(context,item.getDriveImg());
            }
        });
        ImageLoader.with(context).load(item.getRunImg()).circle(20).into(car_img1);
        ImageLoader.with(context).load(item.getDriveImg()).circle(20).into(car_img2);
    }
}
