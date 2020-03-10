package com.cn.android.ui.fragment;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cn.android.R;
import com.cn.android.bean.CouponBean;
import com.cn.android.bean.MiddleBean;
import com.cn.android.common.MyLazyFragment;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.activity.ui.CopyActivity;
import com.cn.android.ui.adapter.CouponAdapter;
import com.cn.android.ui.adapter.MyOrderAdapter;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class CouponBFragment extends MyLazyFragment<CopyActivity> implements PublicInterfaceView,
        OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private CouponAdapter adapter;
    private List<CouponBean> list = new ArrayList<>();
    private List<CouponBean> showList = new ArrayList<>();
    private PublicInterfaceePresenetr presenetr;

    public static CouponAFragment newInstance() {
        return new CouponAFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CouponAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setOnLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        showLoading();
        presenetr.getPostRequest(getActivity(), ServerUrl.selectCouponList, Constant.selectCouponList);
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectCouponList:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("is_use", 2);
                paramsMap.put("page", page);
                paramsMap.put("rows", row);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        if(isUpRefresh){
            showList.clear();
            if (data.equals("null")) {
                showEmpty();
                return;
            }
        }
        smartRefresh.closeHeaderOrFooter();
        list= GsonUtils.getPersons(data, CouponBean.class);
        showList.addAll(list);
        adapter.replaceData(showList);
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }

    private int page = 1, row = 10;
    private boolean isUpRefresh = true;

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isUpRefresh = false;
        page = page + 1;
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isUpRefresh = true;
        page = 1;
        initData();
    }
}