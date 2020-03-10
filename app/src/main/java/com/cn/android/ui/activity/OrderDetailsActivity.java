package com.cn.android.ui.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.cn.android.R;
import com.cn.android.bean.MiddleBean;
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
import com.cn.android.utils.HideStringUtil;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class OrderDetailsActivity extends MyActivity implements PublicInterfaceView, View.OnClickListener {

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
    @BindView(R.id.message_price)
    AppCompatButton messagePrice;
    @BindView(R.id.subsidie_sprice)
    TextView subsidieSprice;
    @BindView(R.id.dutycontent)
    TextView dutycontent;
    @BindView(R.id.emptycontent)
    TextView emptycontent;
    @BindView(R.id.timecontent)
    TextView timecontent;
    @BindView(R.id.button)
    AppCompatButton button;
    private PublicInterfaceePresenetr presenetr;
    private String orderid = "";
    private OrderDetailsBean detailsBean;
    private int payType = 1;
    private int dutymoney = 2;
    private int emptymoney = 2;
    private int timemoney = 2;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        orderid = getIntent().getStringExtra("orderid");
        presenetr = new PublicInterfaceePresenetr(this);
    }

    @Override
    protected void initData() {
        showLoading();
        presenetr.getPostRequest(getActivity(), ServerUrl.selectOrderById, Constant.selectOrderById);
    }

    @OnClick({R.id.message_price, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message_price:
                startActivity(new Intent(this, BargainingActivity.class).putExtra("orderid", orderid).putExtra("bargainprice", detailsBean.getBargainprice()));
                break;
            case R.id.button:
                buyOrderPay();
                break;
        }
    }

    SettingBar zrx;
    SettingBar zfb;
    SettingBar wx;
    SettingBar ye;
    SettingBar dsx;
    SettingBar fkx;
    private void buyOrderPay() {
        BaseDialogFragment.Builder builder = new BaseDialogFragment.Builder(getActivity());
        builder.setGravity(Gravity.BOTTOM);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_order_pay, new FrameLayout(getActivity()), false);
        TextView money=view.findViewById(R.id.money);
        money.setText(detailsBean.getDepositprice());
        zrx = view.findViewById(R.id.zrx);
        fkx = view.findViewById(R.id.fkx);
        dsx = view.findViewById(R.id.dsx);
        wx = view.findViewById(R.id.wx);
        zfb = view.findViewById(R.id.zfb);
        ye = view.findViewById(R.id.ye);
        zrx.setRightText("￥" + detailsBean.getDutymoney());
        fkx.setRightText("￥" + detailsBean.getEmptymoney());
        dsx.setRightText("￥" + detailsBean.getTimemoney());
        zrx.setOnClickListener(this);
        fkx.setOnClickListener(this);
        dsx.setOnClickListener(this);
        zfb.setOnClickListener(this);
        wx.setOnClickListener(this);
        ye.setOnClickListener(this);
        AppCompatButton button = view.findViewById(R.id.but_pay);
        builder.setContentView(view);
        BaseDialog myDialog = builder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                switch (payType) {
                    case 1:
                        ToastUtils.show("支付宝支付");
                        break;
                    case 2:
                        ToastUtils.show("微信支付");
                        break;
                    case 3:
                        showLoading();
                        new PayUtils().infoPay(getActivity(),4,UserBean().getId(),payType,Double.valueOf(detailsBean.getDepositprice()),
                                detailsBean.getId(),0,dutymoney,emptymoney,timemoney)
                                .setPayListener(new PayUtils.OnPayListener() {
                                    @Override
                                    public void paySuccess(int payType) {
                                        showComplete();
                                       OrderDetailsActivity.this.finish();
                                    }

                                    @Override
                                    public void payError(int payType) {
                                        showComplete();
                                    }
                                });
                        break;
                }
            }
        });
        myDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zrx:
                switch (dutymoney) {
                    case  1:
                        dutymoney=2;
                        zrx.setLeftIcon(R.mipmap.vip_un_select);
                        zrx.setRightColor(getResources().getColor(R.color.text_hint));
                        break;
                    case  2:
                        dutymoney=1;
                        zrx.setLeftIcon(R.mipmap.vip_select);
                        zrx.setRightColor(getResources().getColor(R.color.text_red));
                        break;
                }
                break;
            case R.id.fkx:
                switch (emptymoney) {
                    case  1:
                        emptymoney=2;
                        fkx.setLeftIcon(R.mipmap.vip_un_select);
                        fkx.setRightColor(getResources().getColor(R.color.text_hint));
                        break;
                    case  2:
                        emptymoney=1;
                        fkx.setLeftIcon(R.mipmap.vip_select);
                        fkx.setRightColor(getResources().getColor(R.color.text_red));
                        break;
                }
                break;
            case R.id.dsx:
                switch (timemoney) {
                    case  1:
                        timemoney=2;
                        dsx.setLeftIcon(R.mipmap.vip_un_select);
                        dsx.setRightColor(getResources().getColor(R.color.text_hint));
                        break;
                    case  2:
                        timemoney=1;
                        dsx.setLeftIcon(R.mipmap.vip_select);
                        dsx.setRightColor(getResources().getColor(R.color.text_red));
                        break;
                }
                break;
            case R.id.zfb:
                payType = 1;
                wx.setRightIcon(R.mipmap.vip_un_select);
                zfb.setRightIcon(R.mipmap.vip_select);
                ye.setRightIcon(R.mipmap.vip_un_select);
                break;
            case R.id.wx:
                payType = 2;
                wx.setRightIcon(R.mipmap.vip_select);
                zfb.setRightIcon(R.mipmap.vip_un_select);
                ye.setRightIcon(R.mipmap.vip_un_select);
                break;
            case R.id.ye:
                payType = 3;
                wx.setRightIcon(R.mipmap.vip_un_select);
                zfb.setRightIcon(R.mipmap.vip_un_select);
                ye.setRightIcon(R.mipmap.vip_select);
                break;
        }
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectOrderById:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("orderid", orderid);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        switch (tag) {
            case Constant.selectOrderById:
                detailsBean = GsonUtils.getPerson(data, OrderDetailsBean.class);
                orderCode.setText(detailsBean.getOrdercode());
                sendGoodScityArea.setText(detailsBean.getSendgoodscity() + detailsBean.getSendgoodsarea());
                sendGoodsAddress.setText(HideStringUtil.hideAll(detailsBean.getSendgoodsaddress()));
                sendGoodsName.setText(HideStringUtil.hideName(detailsBean.getSendgoodsname()));
                sendGoodsPhone.setText(HideStringUtil.hidePhoneNo(detailsBean.getSendgoodsphone()));
                pickGoodScityArea.setText(detailsBean.getPickgoodscity() + detailsBean.getPickgoodsarea());
                pickGoodsAddress.setText(HideStringUtil.hideAll(detailsBean.getPickgoodsaddress()));
                pickGoodsName.setText(HideStringUtil.hideName(detailsBean.getPickgoodsname()));
                pickGoodsPhone.setText(HideStringUtil.hidePhoneNo(detailsBean.getPickgoodsphone()));
                goodsWeight.setText("毛重:" + detailsBean.getGoodsweight() + "吨");
                goodsNumber.setText("件数:" + detailsBean.getGoodsnumber() + "件");
                goodsVolume.setText("体积:" + detailsBean.getGoodsvolume() + "方");
                demand.setText(HideStringUtil.hideAll(detailsBean.getDemand()));
                freightPrice.setText(detailsBean.getFreightprice());
                subsidieSprice.setText(detailsBean.getSubsidiesprice());
                dutycontent.setText(detailsBean.getDutycontent());
                emptycontent.setText(detailsBean.getEmptycontent());
                timecontent.setText(detailsBean.getTimecontent());
                if (detailsBean.getIs_bargain().equals("1")) {
                    messagePrice.setVisibility(View.VISIBLE);
                } else {
                    messagePrice.setVisibility(View.GONE);
                }
                break;
        }
        showComplete();
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }
}