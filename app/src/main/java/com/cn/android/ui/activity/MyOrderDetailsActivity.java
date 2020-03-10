package com.cn.android.ui.activity;


import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.cn.android.R;
import com.cn.android.bean.OrderDetailsBean;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.PayUtils;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.dialog.base.BaseDialogFragment;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.layout.SettingBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class MyOrderDetailsActivity extends MyActivity implements PublicInterfaceView{

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.order_code)
    TextView orderCode;
    @BindView(R.id.send_good_scity_area)
    TextView sendGoodScityArea;
    @BindView(R.id.send_goods_address)
    TextView sendGoodsAddress;
    @BindView(R.id.send_goods_name)
    TextView sendGoodsName;
    @BindView(R.id.send_goods_phone)
    TextView sendGoodsPhone;
    @BindView(R.id.pick_good_scity_area)
    TextView pickGoodScityArea;
    @BindView(R.id.pick_goods_address)
    TextView pickGoodsAddress;
    @BindView(R.id.pick_goods_name)
    TextView pickGoodsName;
    @BindView(R.id.pick_goods_phone)
    TextView pickGoodsPhone;
    @BindView(R.id.goods_weight)
    TextView goodsWeight;
    @BindView(R.id.goods_number)
    TextView goodsNumber;
    @BindView(R.id.goods_volume)
    TextView goodsVolume;
    @BindView(R.id.demand)
    TextView demand;
    @BindView(R.id.freight_price)
    TextView freightPrice;
    @BindView(R.id.subsidie_sprice)
    TextView subsidieSprice;
    @BindView(R.id.dutycontent)
    TextView dutycontent;
    @BindView(R.id.emptycontent)
    TextView emptycontent;
    @BindView(R.id.timecontent)
    TextView timecontent;
    private PublicInterfaceePresenetr presenetr;
    private String orderid = "";
    private OrderDetailsBean detailsBean;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order_details;
    }

    @Override
    protected void initView() {
        orderid = getIntent().getStringExtra("orderid");
        presenetr = new PublicInterfaceePresenetr(this);
    }

    @Override
    protected void initData() {
        showLoading();
        presenetr.getPostRequest(getActivity(), ServerUrl.selectOrderDetialsById, Constant.selectOrderDetialsById);
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectOrderDetialsById:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("id", orderid);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        switch (tag) {
            case Constant.selectOrderDetialsById:
                detailsBean = GsonUtils.getPerson(data, OrderDetailsBean.class);
                orderCode.setText(detailsBean.getOrdercode());
                sendGoodScityArea.setText(detailsBean.getSendgoodscity() + detailsBean.getSendgoodsarea());
                sendGoodsAddress.setText(detailsBean.getSendgoodsaddress());
                sendGoodsName.setText(detailsBean.getSendgoodsname());
                sendGoodsPhone.setText(detailsBean.getSendgoodsphone());
                pickGoodScityArea.setText(detailsBean.getPickgoodscity() + detailsBean.getPickgoodsarea());
                pickGoodsAddress.setText(detailsBean.getPickgoodsaddress());
                pickGoodsName.setText(detailsBean.getPickgoodsname());
                pickGoodsPhone.setText(detailsBean.getPickgoodsphone());
                goodsWeight.setText("毛重:" + detailsBean.getGoodsweight() + "吨");
                goodsNumber.setText("件数:" + detailsBean.getGoodsnumber() + "件");
                goodsVolume.setText("体积:" + detailsBean.getGoodsvolume() + "方");
                demand.setText(detailsBean.getDemand());
                freightPrice.setText(detailsBean.getFreightprice());
                subsidieSprice.setText(detailsBean.getSubsidiesprice());
                dutycontent.setText(detailsBean.getDutycontent());
                emptycontent.setText(detailsBean.getEmptycontent());
                timecontent.setText(detailsBean.getTimecontent());
                break;
        }
        showComplete();
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }
}