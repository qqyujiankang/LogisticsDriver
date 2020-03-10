package com.cn.android.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.android.R;
import com.cn.android.bean.BankBean;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.adapter.BankListAdapter;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class MybankListActivity extends MyActivity implements PublicInterfaceView {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private BankListAdapter adapter;
    private PublicInterfaceePresenetr presenetr;
    private List<BankBean> beans = new ArrayList<>();

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
        titleBar.setTitle("绑定银行卡");
        presenetr = new PublicInterfaceePresenetr(this);
        smartRefresh.setEnableRefresh(false);
        smartRefresh.setEnableLoadMore(false);
    }

    @Override
    protected void onResume() {
        showLoading();
        presenetr.getPostRequest(this, ServerUrl.selectBankList, Constant.selectBankList);
        super.onResume();
    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BankListAdapter(this);
        View view = LayoutInflater.from(this).inflate(R.layout.list_foot_but, recyclerView, false);
        AppCompatButton button = view.findViewById(R.id.button);
        button.setText("添加银行卡");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MybankListActivity.this, AddBankActivity.class).putExtra("myBank", 1));
            }
        });
        adapter.addFooterView(view);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectBankList:
                paramsMap.put("userid", UserBean().getId());
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.selectBankList:
                beans = GsonUtils.getPersons(data, BankBean.class);
                adapter.replaceData(beans);
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }

}