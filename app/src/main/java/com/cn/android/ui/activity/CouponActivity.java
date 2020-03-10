package com.cn.android.ui.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cn.android.R;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.network.Constant;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.ui.fragment.CouponAFragment;
import com.cn.android.ui.fragment.CouponBFragment;
import com.cn.android.ui.fragment.CouponCFragment;
import com.cn.android.utils.SPUtils;
import com.google.android.material.tabs.TabLayout;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.widget.layout.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 登录界面
 */
public final class CouponActivity extends MyActivity {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.mytab)
    TabLayout mytab;
    @BindView(R.id.mViewPager)
    NoScrollViewPager mViewPager;
    List<String> mTitle;
    List<Fragment> mFragment;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_middle;
    }

    @Override
    protected void initView() {
        titleBar.setTitle("会员券");
    }

    @Override
    protected void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("未使用");
        mTitle.add("已使用");
        mTitle.add("已失效");
        mFragment = new ArrayList<>();
        mFragment.add(new CouponAFragment());
        mFragment.add(new CouponBFragment());
        mFragment.add(new CouponCFragment());

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        mytab.setupWithViewPager(mViewPager);
    }

}