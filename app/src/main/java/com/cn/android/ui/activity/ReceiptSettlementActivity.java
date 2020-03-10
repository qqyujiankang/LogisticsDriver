package com.cn.android.ui.activity;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.android.R;
import com.cn.android.bean.AppAccountInfo;
import com.cn.android.bean.MessageBean;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.adapter.ReceiptAdapter;
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
public final class ReceiptSettlementActivity extends MyActivity implements PublicInterfaceView  ,
        OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private ReceiptAdapter adapter;
    private int type = 0;
    private PublicInterfaceePresenetr presenetr;
    private List<MessageBean> list=new ArrayList<>();
    private List<MessageBean> showList = new ArrayList<>();

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
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                titleBar.setTitle("通知");
                break;
            case 1:
                titleBar.setTitle("我的会员");
                break;
            case 2:
                titleBar.setTitle("订单回执");
                break;
            case 3:
                titleBar.setTitle("订单结算");
                break;
        }
        presenetr=new PublicInterfaceePresenetr(this);
        showLoading();
        getData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReceiptAdapter(this);
        recyclerView.setAdapter(adapter);
        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setOnLoadMoreListener(this);
    }

    private void getData() {
        presenetr.getPostRequest(this, ServerUrl.selectMessageList, Constant.selectMessageList);
    }

    @Override
    protected void initData() {
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case  Constant.selectMessageList:
                paramsMap.put("userid",UserBean().getId());
                paramsMap.put("page",page);
                paramsMap.put("rows",row);
                paramsMap.put("type",type+1);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case  Constant.selectMessageList:
                if(isUpRefresh){
                    showList.clear();
                    if (data.equals("null")) {
                        showEmpty();
                        return;
                    }
                }
                smartRefresh.closeHeaderOrFooter();
                list= GsonUtils.getPersons(data, MessageBean.class);
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