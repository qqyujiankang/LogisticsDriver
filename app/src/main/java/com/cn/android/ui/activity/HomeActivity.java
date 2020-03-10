package com.cn.android.ui.activity;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.cn.android.R;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.common.MyLazyFragment;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.helper.DoubleClickHelper;
import com.cn.android.jpush.ExampleUtil;
import com.cn.android.network.Constant;
import com.cn.android.network.ServerUrl;
import com.cn.android.other.KeyboardWatcher;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.service.AudioService;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.ui.fragment.FragmentLeft;
import com.cn.android.ui.fragment.FragmentMiddle;
import com.cn.android.ui.fragment.FragmentRight;
import com.cn.android.utils.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.base.BaseFragmentAdapter;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.widget.layout.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 主页界面
 */
public final class HomeActivity extends MyActivity
        implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        KeyboardWatcher.SoftKeyboardStateListener,
        PublicInterfaceView {

    @BindView(R.id.vp_home_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;
    private PublicInterfaceePresenetr presenetr;
    private FragmentLeft left;
    private FragmentMiddle middle;
    private FragmentRight right;
    public static boolean isForeground = false;
    public AudioService.MyBinder mMyBinder;
    //“绑定”服务的intent
    Intent MediaServiceIntent;

    /**
     * ViewPager 适配器
     */
    private BaseFragmentAdapter<MyLazyFragment> mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        left = FragmentLeft.newInstance();
        middle = FragmentMiddle.newInstance();
        right = FragmentRight.newInstance();
        presenetr = new PublicInterfaceePresenetr(this);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setNoScroll(true);
        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        KeyboardWatcher.with(this)
                .setListener(this);
    }

    @Override
    protected void initData() {
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(left);
        mPagerAdapter.addFragment(middle);
        mPagerAdapter.addFragment(right);
        mViewPager.setAdapter(mPagerAdapter);
        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
        //够了绑定播放音乐的服务
        Intent MediaServiceIntent = new Intent(this, AudioService.class);
        bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (AudioService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.menu_left);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.home_middle);
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.home_right);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_left:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.home_middle:
                if (!SPUtils.contains("AppUser")) {
                    goLogin(0, "您的账户未登陆");
                    break;
                }
                if (UserBean().getIsRegister() != 4) {
                    goLogin(1, "您的账户信息尚未补全");
                    break;
                }
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.home_right:
                mViewPager.setCurrentItem(2);
                return true;
            default:
                break;
        }
        return false;
    }

    /**
     * {@link KeyboardWatcher.SoftKeyboardStateListener}
     */
    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        mBottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onSoftKeyboardClosed() {
        mBottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 回调当前 Fragment 的 onKeyDown 方法
        if (mPagerAdapter.getCurrentFragment().onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.getInstance().finishAllActivities();
                    // 销毁进程（请注意：调用此 API 可能导致当前 Activity onDestroy 方法无法正常回调）
                    // System.exit(0);
                }
            }, 300);
        } else {
            toast(R.string.home_exit_hint);
        }
    }

    public void getData() {
        presenetr.getPostRequest(this, ServerUrl.selectInfoByUserid, Constant.selectInfoByUserid);
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userid", UserBean().getId());
        return paramsMap;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        SPUtils.putString("AppUser", data);
        right.initData();
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {

    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        if (isLogin()) {
            getData();
        }
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
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
                                        Intent intent = new Intent(HomeActivity.this, DriverAuthenticationActivity.class);
                                        intent.putExtra("step", 0);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        Intent intent2 = new Intent(HomeActivity.this, DriverAuthenticationActivity.class);
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

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(updateTextEvent event) {
//        switch (event.getType()) {
//            case  0:
//                new MessageDialog.Builder(this)
//                        .setTitle("提示")
//                        .setMessage(event.getMsg())
//                        .setCancel("")
//                        .setListener(new MessageDialog.OnListener() {
//                            @Override
//                            public void onConfirm(BaseDialog dialog) {
//                                SPUtils.removeAll();
//                                startActivity(LoginActivity.class);
//                                ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
//                            }
//
//                            @Override
//                            public void onCancel(BaseDialog dialog) {
//
//                            }
//                        }).show();
//                break;
//        }
//    }
}