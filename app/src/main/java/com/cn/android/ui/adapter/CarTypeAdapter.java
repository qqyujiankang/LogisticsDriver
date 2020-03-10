package com.cn.android.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.android.R;
import com.cn.android.bean.ListModelBean;

public class CarTypeAdapter extends BaseQuickAdapter<ListModelBean, BaseViewHolder> {
    private Context context;

    public CarTypeAdapter(Context context) {
        super(R.layout.item_car_type);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ListModelBean item) {
        helper.setText(R.id.car_type,item.getName());
        if(item.isSelect()){
            helper.setBackgroundRes(R.id.car_type,R.mipmap.car_type_on);
        }else{
            helper.setBackgroundRes(R.id.car_type,R.mipmap.car_type_off);
        }
    }
}
