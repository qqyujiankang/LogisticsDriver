package com.cn.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.android.R;
import com.cn.android.bean.CarInfoBean;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.adapter.CarInfoAdapter;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VehicleInfoActivity extends MyActivity implements PublicInterfaceView {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.add_car)
    TextView addCar;
    private PublicInterfaceePresenetr presenetr;
    private List<CarInfoBean> carInfoList;
    private CarInfoAdapter adapter;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_vehicle_info;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);
        adapter = new CarInfoAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        showLoading();
        presenetr.getPostRequest(this, ServerUrl.selectCarInfoList, Constant.selectCarInfoList);
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectCarInfoList:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("page", 1);
                paramsMap.put("rows", 50);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.selectCarInfoList:
                carInfoList = GsonUtils.getPersons(data, CarInfoBean.class);
                adapter.replaceData(carInfoList);
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }


    @OnClick(R.id.add_car)
    public void onViewClicked() {
        startActivityFinish(AddCarActivity.class);
    }

}
