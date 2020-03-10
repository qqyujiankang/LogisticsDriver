package com.cn.android.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cn.android.R;
import com.cn.android.bean.AppUserBean;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.InputTextHelper;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.utils.GetDeviceId;
import com.cn.android.utils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.widget.view.CountdownView;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 注册界面
 */
public final class RegisterActivity extends MyActivity implements PublicInterfaceView {

    @BindView(R.id.et_register_phone)
    EditText mPhoneView;
    @BindView(R.id.cv_register_countdown)
    CountdownView mCountdownView;
    @BindView(R.id.et_register_code)
    EditText mCodeView;
    @BindView(R.id.et_register_password)
    EditText mPasswordView;
    @BindView(R.id.btn_register_commit)
    Button mCommitView;
    PublicInterfaceePresenetr presenetr;
    AppUserBean driverBean = new AppUserBean();
    private String registrationID,deviceid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        registrationID = JPushInterface.getRegistrationID(getActivity());
        deviceid= GetDeviceId.getDeviceId(this);
        presenetr = new PublicInterfaceePresenetr(this);
        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mCodeView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .setListener(new InputTextHelper.OnInputTextListener() {

                    @Override
                    public boolean onInputChange(InputTextHelper helper) {
                        return mPhoneView.getText().toString().length() == 11 &&
                                mPasswordView.getText().toString().length() >= 6 &&
                                mCodeView.getText().toString().length() == 6;
                    }
                })
                .build();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected ImmersionBar statusBarConfig() {
        // 不要把整个布局顶上去
        return super.statusBarConfig().keyboardEnable(true);
    }

    @OnClick({R.id.cv_register_countdown, R.id.btn_register_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_register_countdown:
                // 获取验证码
                if (mPhoneView.getText().toString().length() != 11) {
                    // 重置验证码倒计时控件
                    mCountdownView.resetState();
                    toast(R.string.common_phone_input_error);
                } else {
                    showLoading();
                    presenetr.getPostRequest(this, ServerUrl.sendSms, Constant.sendSms);
                    toast(R.string.common_code_send_hint);
                }
                break;
            case R.id.btn_register_commit:
                showLoading();
                presenetr.getPostRequest(this, ServerUrl.registerDriver, Constant.registerDriver);
                break;
            default:
                break;
        }
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.registerDriver:
                paramsMap.put("userphone", mPhoneView.getText().toString());
                paramsMap.put("password", mPasswordView.getText().toString());
                paramsMap.put("smscode", mCodeView.getText().toString());
                paramsMap.put("jiguangid", registrationID);
                paramsMap.put("deviceid", deviceid);
                return paramsMap;
            case Constant.sendSms:
                paramsMap.put("loginName", mPhoneView.getText().toString());
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.registerDriver:
                driverBean = GsonUtils.getPerson(data, AppUserBean.class);
                SPUtils.putString("AppUser",data);
                SPUtils.putString("token", driverBean.getId());
                //注册步骤 0为注册 1为司机身份认证 2为车型选择 3为添加银行卡 4为选择线路
                switch (driverBean.getIsRegister()) {
                    case 0:
                        Intent intent = new Intent(this, DriverAuthenticationActivity.class);
                        intent.putExtra("step", 0);
                        startActivityFinish(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(this, DriverAuthenticationActivity.class);
                        intent2.putExtra("step", 1);
                        startActivityFinish(intent2);
                        break;
                    case 2:
                        startActivityFinish(AddBankActivity.class);
                        break;
                    case 3:
                        startActivityFinish(AddRouteActivity.class);
                        break;
                }
                break;
            case Constant.sendSms:
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
        switch (tag) {
            case Constant.registerDriver:
                break;
            case Constant.sendSms:
                mCountdownView.resetState();
                break;
        }
        toast(error);
    }
}