package com.cn.android.ui.activity;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.android.R;
import com.cn.android.bean.AppAccountInfo;
import com.cn.android.bean.MiddleBean;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.adapter.CustomerAdapter;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class CustomerActivity extends MyActivity implements PublicInterfaceView  ,
        OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private CustomerAdapter adapter;
    private PublicInterfaceePresenetr presenetr;
    private List<AppAccountInfo> list=new ArrayList<>();
    private List<AppAccountInfo> showList = new ArrayList<>();

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        showLoading();
        presenetr=new PublicInterfaceePresenetr(this);
        titleBar.setTitle("账单详情");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new CustomerAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setOnLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        presenetr.getPostRequest(this, ServerUrl.selectAccountList, Constant.selectAccountList);
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case  Constant.selectAccountList:
                paramsMap.put("userid",UserBean().getId());
                paramsMap.put("page",page);
                paramsMap.put("rows",row);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case  Constant.selectAccountList:
                if(isUpRefresh){
                    showList.clear();
                    if (data.equals("null")) {
                        showEmpty();
                        return;
                    }
                }
                smartRefresh.closeHeaderOrFooter();
                list= GsonUtils.getPersons(data, AppAccountInfo.class);
                showList.addAll(list);
                adapter.replaceData(showList);
                break;
        }
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
        getData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isUpRefresh = true;
        page = 1;
        getData();
    }

}