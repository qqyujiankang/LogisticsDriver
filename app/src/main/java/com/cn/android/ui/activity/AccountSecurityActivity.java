package com.cn.android.ui.activity;

import android.os.Bundle;

import com.cn.android.R;
import com.cn.android.common.MyActivity;
import com.hjq.bar.TitleBar;
import com.hjq.widget.layout.SettingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountSecurityActivity extends MyActivity {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.phone)
    SettingBar phone;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_security;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String phoneNum=UserBean().getUserphone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        phone.setRightText(phoneNum);
    }

    @OnClick(R.id.phone)
    public void onViewClicked() {
        startActivityFinish(ChangePhoneActivity.class);
    }
}
