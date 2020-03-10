package com.cn.android.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.cn.android.R;
import com.cn.android.bean.AppUserBean;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.InputTextHelper;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.other.IntentKey;
import com.cn.android.other.KeyboardWatcher;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.activity.ui.PasswordForgetActivity;
import com.cn.android.utils.GetDeviceId;
import com.cn.android.utils.L;
import com.cn.android.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 登录界面
 */
public final class LoginActivity extends MyActivity
        implements PublicInterfaceView,
        KeyboardWatcher.SoftKeyboardStateListener {

    @BindView(R.id.iv_login_logo)
    ImageView mLogoView;
    @BindView(R.id.ll_login_body)
    LinearLayout mBodyLayout;
    @BindView(R.id.et_login_phone)
    EditText mPhoneView;
    @BindView(R.id.et_login_password)
    EditText mPasswordView;
    @BindView(R.id.btn_login_commit)
    Button mCommitView;
    @BindView(R.id.v_login_blank)
    View mBlankView;

    PublicInterfaceePresenetr presenetr;
    AppUserBean driverBean = new AppUserBean();

    /**
     * logo 缩放比例
     */
    private final float mLogoScale = 0.8f;
    /**
     * 动画时间
     */
    private final int mAnimTime = 300;
    private String registrationID,deviceid;

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);
        registrationID = JPushInterface.getRegistrationID(getActivity());
        deviceid= GetDeviceId.getDeviceId(this);
        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .setListener(new InputTextHelper.OnInputTextListener() {

                    @Override
                    public boolean onInputChange(InputTextHelper helper) {
                        return mPhoneView.getText().toString().length() == 11 &&
                                mPasswordView.getText().toString().length() >= 6;
                    }
                })
                .build();

        post(new Runnable() {

            @Override
            public void run() {
                // 因为在小屏幕手机上面，因为计算规则的因素会导致动画效果特别夸张，所以不在小屏幕手机上面展示这个动画效果
                if (mBlankView.getHeight() > mBodyLayout.getHeight()) {
                    // 只有空白区域的高度大于登录框区域的高度才展示动画
                    KeyboardWatcher.with(LoginActivity.this)
                            .setListener(LoginActivity.this);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPhoneView.setText("15339256798");
        mPasswordView.setText("123456");
    }

    @Override
    public void onRightClick(View v) {
        // 跳转到注册界面
        startActivityForResult(RegisterActivity.class, new ActivityCallback() {

            @Override
            public void onActivityResult(int resultCode, @Nullable Intent data) {
                // 如果已经注册成功，就执行登录操作
                if (resultCode == RESULT_OK && data != null) {
                    mPhoneView.setText(data.getStringExtra(IntentKey.PHONE));
                    mPasswordView.setText(data.getStringExtra(IntentKey.PASSWORD));
                    onClick(mCommitView);
                }
            }
        });
    }

    @OnClick({R.id.tv_login_forget, R.id.tv_login_register, R.id.btn_login_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_forget:
                startActivity(PasswordForgetActivity.class);
                break;
            case R.id.tv_login_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.btn_login_commit:
                if (mPhoneView.getText().toString().length() != 11) {
                    toast(R.string.common_phone_input_error);
                } else {
                    showLoading();
                    presenetr.getPostRequest(this, ServerUrl.login, Constant.login);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 友盟登录回调
    }


    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int[] location = new int[2];
        // 获取这个 View 在屏幕中的坐标（左上角）
        mBodyLayout.getLocationOnScreen(location);
        //int x = location[0];
        int y = location[1];
        int bottom = screenHeight - (y + mBodyLayout.getHeight());
        if (keyboardHeight > bottom) {
            // 执行位移动画
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", 0, -(keyboardHeight - bottom));
            objectAnimator.setDuration(mAnimTime);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.start();

            // 执行缩小动画
            mLogoView.setPivotX(mLogoView.getWidth() / 2f);
            mLogoView.setPivotY(mLogoView.getHeight());
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", 1.0f, mLogoScale);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", 1.0f, mLogoScale);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", 0.0f, -(keyboardHeight - bottom));
            animatorSet.play(translationY).with(scaleX).with(scaleY);
            animatorSet.setDuration(mAnimTime);
            animatorSet.start();
        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        // 执行位移动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", mBodyLayout.getTranslationY(), 0);
        objectAnimator.setDuration(mAnimTime);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

        if (mLogoView.getTranslationY() == 0) {
            return;
        }
        // 执行放大动画
        mLogoView.setPivotX(mLogoView.getWidth() / 2f);
        mLogoView.setPivotY(mLogoView.getHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", mLogoScale, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", mLogoScale, 1.0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", mLogoView.getTranslationY(), 0);
        animatorSet.play(translationY).with(scaleX).with(scaleY);
        animatorSet.setDuration(mAnimTime);
        animatorSet.start();
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.login:
                paramsMap.put("userphone", mPhoneView.getText().toString());
                paramsMap.put("password", mPasswordView.getText().toString());
                paramsMap.put("jiguangid", registrationID);
                paramsMap.put("deviceid", deviceid);
                paramsMap.put("type", "1");
                paramsMap.put("is_vip", "0");
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.login:
                driverBean = GsonUtils.getPerson(data, AppUserBean.class);
                SPUtils.putString("AppUser", data);
                SPUtils.putString("token", driverBean.getId());
                //注册步骤 0为司机身份认证 1为车型选择 2为添加银行卡 3为选择线路
                switch (driverBean.getIsRegister()) {
                    case 0:
                        Intent intent = new Intent(this, DriverAuthenticationActivity.class);
                        intent.putExtra("step", 0);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(this, DriverAuthenticationActivity.class);
                        intent2.putExtra("step", 1);
                        startActivity(intent2);
                        break;
                    case 2:
                        startActivity(AddBankActivity.class);
                        break;
                    case 3:
                        startActivity(AddRouteActivity.class);
                        break;
                    case 4:
                        startActivityFinish(HomeActivity.class);
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
        L.e("123", error);
        toast(error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_login_qq, R.id.iv_login_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_qq:
                break;
            case R.id.iv_login_wx:

                break;
        }
    }
}