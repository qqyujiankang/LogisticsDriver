package com.cn.android.ui.activity;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cn.android.R;
import com.cn.android.bean.AppUserBean;
import com.cn.android.bean.ListModelBean;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.PopupWindowHelper;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.adapter.BankAdapter;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class AddBankActivity extends MyActivity implements
        PublicInterfaceView,
        BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.bankName)
    TextView bankName;
    @BindView(R.id.bankNumber)
    EditText bankNumber;
    @BindView(R.id.btn_bank_next)
    AppCompatButton btnBankNext;
    private PopupWindowHelper popupHelper;
    private RecyclerView recyclerView;
    private BankAdapter adapter;
    private List<ListModelBean> list;
    private PublicInterfaceePresenetr presenetr;
    private String myBankId = "", myBankName = "";
    private int myBank = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bank;
    }

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected void initView() {
        myBank = getIntent().getIntExtra("myBank", 0);
        if(myBank==1){
            btnBankNext.setText("提交");
        }
        presenetr = new PublicInterfaceePresenetr(this);
        popupHelper = new PopupWindowHelper(this, R.layout.popup_list);
        recyclerView = popupHelper.getPopupView().findViewById(R.id.popup_recyclerView);
        adapter = new BankAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        showLoading();
        presenetr.getPostRequest(this, ServerUrl.selectBankType, Constant.selectBankType);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        bankName.setText(list.get(position).getName());
        myBankName = list.get(position).getName();
        popupHelper.dismiss();
    }

    @OnClick({R.id.bankName, R.id.btn_bank_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bankName:
                popupHelper.show(bankName);
                break;
            case R.id.btn_bank_next:
                if (TextUtils.isEmpty(account.getText())) {
                    ToastUtils.show("请输入账户名");
                    return;
                }
                if (TextUtils.isEmpty(bankNumber.getText())) {
                    ToastUtils.show("请输入银行卡号");
                    return;
                }
                myBankId = bankNumber.getText().toString().trim();
                showLoading();
                if(myBank==0){
                    presenetr.getPostTokenRequest(this, ServerUrl.addBankInfo, Constant.addBankInfo);
                }else{
                    presenetr.getPostTokenRequest(this, ServerUrl.saveBankInfo, Constant.addBankInfo);
                }

                break;
        }
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectBankType:
                return paramsMap;
            case Constant.addBankInfo:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("username", account.getText().toString().trim());
                paramsMap.put("bankno", myBankId);
                paramsMap.put("bankname", myBankName);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.selectBankType:
                list = GsonUtils.getPersons(data, ListModelBean.class);
                adapter.replaceData(list);
                break;
            case Constant.addBankInfo:
                switch (myBank) {
                    case 0:
                        startActivityFinish(AddRouteActivity.class);
                        break;
                    case 1:
                        this.finish();
                        break;
                }
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }
}