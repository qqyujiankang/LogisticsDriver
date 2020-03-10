package com.cn.android.ui.activity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.cn.android.R;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.helper.InputTextHelper;
import com.cn.android.network.Constant;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class CourierNumberActivity extends MyActivity implements
        PublicInterfaceView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.courier_number)
    EditText courierNumber;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private PublicInterfaceePresenetr presenetr;
    private String orderid = "";

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_courier_number;
    }

    @Override
    protected void initView() {
        orderid = getIntent().getStringExtra("orderid");
        presenetr = new PublicInterfaceePresenetr(this);
    }

    @Override
    protected void initData() {
        InputTextHelper.with(this)
                .addView(courierNumber)
                .setMain(btnCommit)
                .setListener(new InputTextHelper.OnInputTextListener() {

                    @Override
                    public boolean onInputChange(InputTextHelper helper) {
                        return courierNumber.getText().toString().length()!=0;
                    }
                })
                .build();
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.addExpressno:
                paramsMap.put("id", orderid);
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("expressno", UserBean().getId());
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.addExpressno:
                this.finish();
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        presenetr.getPostTokenRequest(getActivity(), ServerUrl.addExpressno, Constant.addExpressno);
    }

}