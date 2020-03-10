package com.cn.android.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;

import com.cn.android.R;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.layout.SettingBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class RechargeActivity extends MyActivity {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.wx)
    SettingBar wx;
    @BindView(R.id.zfb)
    SettingBar zfb;
    @BindView(R.id.but_buy)
    AppCompatButton butBuy;
    @BindView(R.id.money)
    EditText money;
    private int buyType = 0;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.wx, R.id.zfb, R.id.but_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wx:
                buyType = 0;
                wx.setRightIcon(R.mipmap.vip_select);
                zfb.setRightIcon(R.mipmap.vip_un_select);
                break;
            case R.id.zfb:
                buyType = 1;
                wx.setRightIcon(R.mipmap.vip_un_select);
                zfb.setRightIcon(R.mipmap.vip_select);
                break;
            case R.id.but_buy:
                if (TextUtils.isEmpty(money.getText())) {
                    toast("请输入金额");
                    return;
                }
                switch (buyType) {
                    case 0:
                        ToastUtils.show("微信充值:" + money.getText().toString());
                        break;
                    case 1:
                        ToastUtils.show("支付宝充值:" + money.getText().toString());
                        break;
                }
                break;
        }
    }

}
