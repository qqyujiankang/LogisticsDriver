package com.cn.android.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.cn.android.R;
import com.cn.android.bean.AppUserBean;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.network.Constant;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.hjq.widget.view.CountdownView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePhoneActivity extends MyActivity implements PublicInterfaceView {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.phone)
    SettingBar phone;
    @BindView(R.id.cv_register_countdown)
    CountdownView mCountdownView;
    @BindView(R.id.but_buy)
    AppCompatButton butBuy;
    @BindView(R.id.m_codeView)
    ClearEditText mCodeView;
    @BindView(R.id.new_phone)
    ClearEditText newPhone;
    private PublicInterfaceePresenetr presenetr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);
    }

    @Override
    protected void initData() {
        String phoneNum = UserBean().getUserphone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        phone.setRightText(phoneNum);
    }

    @OnClick({R.id.cv_register_countdown, R.id.but_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_register_countdown:
                // 获取验证码
                if (newPhone.getText().toString().length() != 11) {
                    // 重置验证码倒计时控件
                    mCountdownView.resetState();
                    toast(R.string.common_phone_input_error);
                } else {
                    showLoading();
                    presenetr.getPostRequest(this, ServerUrl.sendSms, Constant.sendSms);
                    toast(R.string.common_code_send_hint);
                }
                break;
            case R.id.but_buy:
                showLoading();
                presenetr.getPostTokenRequest(this, ServerUrl.updatePhone, Constant.updatePhone);
                break;
        }
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.updatePhone:
                paramsMap.put("type", 1);
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("userphone", newPhone.getText().toString());
                paramsMap.put("smscode", mCodeView.getText().toString());
                return paramsMap;
            case Constant.sendSms:
                paramsMap.put("loginName", newPhone.getText().toString());
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        switch (tag) {
            case Constant.updatePhone:
                AppUserBean userBean = UserBean();
                userBean.setUserphone(newPhone.getText().toString());
                SaveUserBean(userBean);
                this.finish();
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
        mCountdownView.resetState();
    }
}
