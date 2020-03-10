package com.cn.android.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.cn.android.R;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.network.Constant;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.PayUtils;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
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
public final class BargainingActivity extends MyActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.bargaining)
    EditText bargaining;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.wx)
    SettingBar wx;
    @BindView(R.id.zfb)
    SettingBar zfb;
    @BindView(R.id.ye)
    SettingBar ye;
    @BindView(R.id.linear_show_buy)
    LinearLayout linearShowBuy;
    @BindView(R.id.but_pay)
    AppCompatButton butPay;
    private String orderid = "";
    private String bargainprice = "";
    private double uMoney = 0.0;
    private int payType = 1;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bargaining;
    }

    @Override
    protected void initView() {
        orderid = getIntent().getStringExtra("orderid");
        bargainprice = getIntent().getStringExtra("bargainprice");
    }

    @Override
    protected void initData() {
        if (UserBean().getGarde() == 0) {
            linearShowBuy.setVisibility(View.VISIBLE);
        } else {
            linearShowBuy.setVisibility(View.GONE);
        }
        if (UserBean().getGarde() == 0) {
            uMoney = Double.valueOf(bargainprice);
        } else {
            uMoney = 0;
        }
        money.setText(bargainprice);
    }

    @OnClick({R.id.wx, R.id.zfb, R.id.ye, R.id.but_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.but_pay:
                switch (payType) {
                    case 1:
                        ToastUtils.show("支付宝支付");
                        break;
                    case 2:
                        ToastUtils.show("微信支付");
                        break;
                    case 3:
                        showLoading();
                        new PayUtils().infoPay(getActivity(), 2, UserBean().getId(), payType, uMoney,
                                orderid, Double.valueOf(bargaining.getText().toString()), 2, 2, 2)
                                .setPayListener(new PayUtils.OnPayListener() {
                                    @Override
                                    public void paySuccess(int payType) {
                                        showComplete();
                                        BargainingActivity.this.finish();
                                    }

                                    @Override
                                    public void payError(int payType) {
                                        showComplete();
                                        toast("余额支付Error");
                                    }
                                });
                        break;
                }
                break;
        }
    }

}