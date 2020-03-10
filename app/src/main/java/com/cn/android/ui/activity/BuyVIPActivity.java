package com.cn.android.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.cn.android.R;
import com.cn.android.bean.VipMoneyBean;
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
import com.hjq.toast.ToastUtils;
import com.hjq.widget.layout.SettingBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyVIPActivity extends MyActivity implements PublicInterfaceView {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.buy_vip_1)
    TextView buyVip1;
    @BindView(R.id.linear_vip1)
    LinearLayout linearVip1;
    @BindView(R.id.buy_vip_2)
    TextView buyVip2;
    @BindView(R.id.linear_vip2)
    LinearLayout linearVip2;
    @BindView(R.id.wx)
    SettingBar wx;
    @BindView(R.id.zfb)
    SettingBar zfb;
    @BindView(R.id.ye)
    SettingBar ye;
    @BindView(R.id.linear_show_buy)
    LinearLayout linearShowBuy;
    @BindView(R.id.but_buy)
    AppCompatButton butBuy;
    @BindView(R.id.m_money)
    TextView mMoney;
    @BindView(R.id.y_money)
    TextView yMoney;
    private int buyType = 0;
    private boolean buyClick = false;
    private int buyMorY = 0;
    private PublicInterfaceePresenetr presenetr;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_vip;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);
    }

    @Override
    protected void initData() {
        showLoading();
        presenetr.getPostRequest(this, ServerUrl.selectVipMoney, Constant.selectVipMoney);
    }

    @OnClick({R.id.buy_vip_1, R.id.buy_vip_2, R.id.wx, R.id.zfb,R.id.ye, R.id.but_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buy_vip_1:
                buyMorY=1;
                buyClick = true;
                buyVip1.setText("￥"+bean.getMonthVipMoney());
                linearVip1.setVisibility(View.VISIBLE);
                linearVip2.setVisibility(View.GONE);
                linearShowBuy.setVisibility(View.VISIBLE);
                butBuy.setVisibility(View.VISIBLE);
                break;
            case R.id.buy_vip_2:
                buyMorY=2;
                buyClick = true;
                buyVip2.setText("￥"+bean.getYearVipMoney());
                linearVip1.setVisibility(View.GONE);
                linearVip2.setVisibility(View.VISIBLE);
                linearShowBuy.setVisibility(View.VISIBLE);
                butBuy.setVisibility(View.VISIBLE);
                break;
            case R.id.wx:
                buyType = 0;
                wx.setRightIcon(R.mipmap.vip_select);
                zfb.setRightIcon(R.mipmap.vip_un_select);
                ye.setRightIcon(R.mipmap.vip_un_select);
                break;
            case R.id.zfb:
                buyType = 1;
                wx.setRightIcon(R.mipmap.vip_un_select);
                zfb.setRightIcon(R.mipmap.vip_select);
                ye.setRightIcon(R.mipmap.vip_un_select);
                break;
            case R.id.ye:
                buyType = 2;
                wx.setRightIcon(R.mipmap.vip_un_select);
                zfb.setRightIcon(R.mipmap.vip_un_select);
                ye.setRightIcon(R.mipmap.vip_select);
                break;
            case R.id.but_buy:
                switch (buyType) {
                    case 0:
                        ToastUtils.show("微信购买");
                        break;
                    case 1:
                        ToastUtils.show("支付宝购买");
                        break;
                    case 2:
                        double money=0;
                        switch (buyMorY) {
                            case  1:
                                money=bean.getMonthVipMoney();
                                break;
                            case  2:
                                money=bean.getYearVipMoney();
                                break;
                        }
                        showLoading();
                        new PayUtils().infoPay(this,1,UserBean().getId(),3,money,
                                "",0,2,2,2)
                                .setPayListener(new PayUtils.OnPayListener() {
                                    @Override
                                    public void paySuccess(int payType) {
                                        showComplete();
                                        BuyVIPActivity.this.finish();
                                        toast("余额支付Success");
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (buyClick) {
                buyClick = false;
                buyVip1.setText("立即购买");
                buyVip2.setText("立即购买");
                linearVip1.setVisibility(View.VISIBLE);
                linearVip2.setVisibility(View.VISIBLE);
                linearShowBuy.setVisibility(View.GONE);
                butBuy.setVisibility(View.GONE);
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectVipMoney:
                return paramsMap;
        }
        return null;
    }

   private VipMoneyBean bean;
    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.selectVipMoney:
                bean = GsonUtils.getPerson(data, VipMoneyBean.class);
                mMoney.setText("￥"+bean.getMonthVipMoney());
                yMoney.setText("￥"+bean.getYearVipMoney());
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }
}
