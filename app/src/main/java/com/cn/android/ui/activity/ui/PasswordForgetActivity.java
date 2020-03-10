package com.cn.android.ui.activity.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.cn.android.R;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.InputTextHelper;
import com.cn.android.network.Constant;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.hjq.bar.TitleBar;
import com.hjq.widget.view.ClearEditText;
import com.hjq.widget.view.CountdownView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/02/27
 * desc   : 忘记密码
 */
public final class PasswordForgetActivity extends MyActivity implements PublicInterfaceView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.m_codeView)
    ClearEditText mCodeView;
    @BindView(R.id.phone)
    ClearEditText phone;
    @BindView(R.id.cv_register_countdown)
    CountdownView mCountdownView;
    @BindView(R.id.button)
    AppCompatButton button;
    @BindView(R.id.pass)
    ClearEditText pass;
    private PublicInterfaceePresenetr presenetr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_forget;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);
        InputTextHelper.with(this)
                .addView(phone)
                .addView(mCodeView)
                .addView(pass)
                .setMain(button)
                .setListener(new InputTextHelper.OnInputTextListener() {

                    @Override
                    public boolean onInputChange(InputTextHelper helper) {
                        return phone.getText().toString().length() == 11 && mCodeView.getText().toString().length() == 4
                                && pass.getText().toString().length() < 6;
                    }
                })
                .build();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.cv_register_countdown, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_register_countdown:
                // 获取验证码
                if (phone.getText().toString().length() != 11) {
                    // 重置验证码倒计时控件
                    mCountdownView.resetState();
                    toast(R.string.common_phone_input_error);
                } else {
                    showLoading();
                    presenetr.getPostRequest(this, ServerUrl.sendSms, Constant.sendSms);
                    toast(R.string.common_code_send_hint);
                }
                break;
            case R.id.button:
                showLoading();
                presenetr.getPostTokenRequest(this, ServerUrl.forgetPassword, Constant.forgetPassword);
                break;
        }
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.forgetPassword:
                paramsMap.put("type", 1);
                paramsMap.put("userphone", phone.getText().toString());
                paramsMap.put("password", pass.getText().toString());
                paramsMap.put("smsCode", mCodeView.getText().toString());
                return paramsMap;
            case Constant.sendSms:
                paramsMap.put("loginName", phone.getText().toString());
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        switch (tag) {
            case Constant.forgetPassword:
                this.finish();
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
        mCountdownView.resetState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}