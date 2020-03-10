package com.cn.android.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cn.android.R;
import com.cn.android.common.MyLazyFragment;
import com.cn.android.ui.activity.AddBankActivity;
import com.cn.android.ui.activity.AddRouteActivity;
import com.cn.android.ui.activity.BuyVIPActivity;
import com.cn.android.ui.activity.CouponActivity;
import com.cn.android.ui.activity.CustomerActivity;
import com.cn.android.ui.activity.DriverAuthenticationActivity;
import com.cn.android.ui.activity.HomeActivity;
import com.cn.android.ui.activity.LoginActivity;
import com.cn.android.ui.activity.MembershipInterestsActivity;
import com.cn.android.ui.activity.MessageCenterActivity;
import com.cn.android.ui.activity.MybankListActivity;
import com.cn.android.ui.activity.PersonalSettingsActivity;
import com.cn.android.ui.activity.VehicleInfoActivity;
import com.cn.android.ui.activity.WithdrawActivity;
import com.cn.android.ui.activity.ui.ImageActivity;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.ApkUtils;
import com.cn.android.utils.SPUtils;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.image.ImageLoader;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.layout.SettingBar;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目框架使用示例
 */
public final class FragmentRight extends MyLazyFragment<HomeActivity> {

    @BindView(R.id.my_img)
    ImageView myImg;
    @BindView(R.id.my_id)
    TextView myId;
    @BindView(R.id.my_msg)
    ImageView myMsg;
    @BindView(R.id.my_money)
    TextView myMoney;
    @BindView(R.id.withdraw)
    TextView withdraw;
    @BindView(R.id.open_vip)
    TextView openVip;
    @BindView(R.id.menu6)
    SettingBar menu6;
    @BindView(R.id.bill_flow)
    ImageView billFlow;

    public static FragmentRight newInstance() {
        return new FragmentRight();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_right;
    }

    @Override
    protected void initView() {
    }


    @Override
    public void initData() {
        ImageLoader.with(getActivity()).load(UserBean().getHeadImg()).circle()
                .placeholder(getResources().getDrawable(R.drawable.ic_head_placeholder))
                .error(getResources().getDrawable(R.drawable.ic_head_placeholder))
                .into(myImg);
        myId.setText(UserBean().getUserphone());
        myMoney.setText(UserBean().getUmoney());
        menu6.setRightText(ApkUtils.getVersionName(getActivity()));
        switch (UserBean().getGarde()) {
            case 0:
                openVip.setVisibility(View.VISIBLE);
                break;
            case 1:
            case 2:
                openVip.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(isLogin()){
            ((HomeActivity) getActivity()).getData();
        }
    }

    @OnClick({R.id.my_img, R.id.bill_flow, R.id.my_msg, R.id.withdraw, R.id.open_vip, R.id.menu1,
            R.id.menu2, R.id.menu3, R.id.menu4, R.id.menu5, R.id.menu6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu2://设置
                startActivity(PersonalSettingsActivity.class);
                break;
        }
        if (!SPUtils.contains("AppUser")) {
            goLogin(0, "您的账户未登陆");
            return;
        }
        if (UserBean().getIsRegister() != 4) {
            goLogin(1, "您的账户信息尚未补全");
            return;
        }
        switch (view.getId()) {
            case R.id.my_img://头像
                ImageActivity.start(getActivity(), UserBean().getHeadImg());
                break;
            case R.id.bill_flow://账单流水
                startActivity(CustomerActivity.class);
                break;
            case R.id.my_msg://消息
                startActivity(MessageCenterActivity.class);
                break;
            case R.id.withdraw://提现
                startActivity(WithdrawActivity.class);
                break;
            case R.id.open_vip://开通会员
                startActivity(BuyVIPActivity.class);
                break;
            case R.id.menu1://车辆信息
                startActivity(VehicleInfoActivity.class);
                break;
            case R.id.menu3://银行卡绑定
                startActivity(MybankListActivity.class);
                break;
            case R.id.menu4://路线信息
                startActivity(MembershipInterestsActivity.class);
                break;
            case R.id.menu5://会员券
                startActivity(CouponActivity.class);
                break;
            case R.id.menu6://版本信息
                break;
        }
    }

    private void goLogin(int type, String content) {
        new MessageDialog.Builder(getActivity())
                .setTitle("温馨提示")
                .setMessage(content)
                .setConfirm(type == 0 ? "去登陆" : "去补全")
                .setAutoDismiss(true)
                .setListener(new MessageDialog.OnListener() {
                    @Override
                    public void onConfirm(BaseDialog dialog) {
                        dialog.dismiss();
                        switch (type) {
                            case 0:
                                startActivity(LoginActivity.class);
                                break;
                            case 1:
                                switch (UserBean().getIsRegister()) {
                                    case 0:
                                        Intent intent = new Intent(getActivity(), DriverAuthenticationActivity.class);
                                        intent.putExtra("step", 0);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        Intent intent2 = new Intent(getActivity(), DriverAuthenticationActivity.class);
                                        intent2.putExtra("step", 1);
                                        startActivity(intent2);
                                        break;
                                    case 2:
                                        startActivity(AddBankActivity.class);
                                        break;
                                    case 3:
                                        startActivity(AddRouteActivity.class);
                                        break;
                                }
                                break;
                        }
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}