package com.cn.android.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cn.android.R;
import com.cn.android.bean.BankBean;
import com.cn.android.bean.ListModelBean;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.helper.PopupWindowHelper;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.adapter.BankAdapter;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.widget.layout.SettingBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawActivity extends MyActivity implements
        PublicInterfaceView ,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.bank_list)
    SettingBar bankList;
    @BindView(R.id.user_money)
    TextView userMoney;
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.but_buy)
    AppCompatButton butBuy;
    private PopupWindowHelper popupHelper;
    private RecyclerView recyclerView;
    private BankAdapter adapter;
    private List<BankBean> beans=new ArrayList<>();
    private List<ListModelBean> list=new ArrayList<>();
    private PublicInterfaceePresenetr presenetr;
    private String myBankId = "", myBankName = "";

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);
        popupHelper = new PopupWindowHelper(this, R.layout.popup_list);
        recyclerView = popupHelper.getPopupView().findViewById(R.id.popup_recyclerView);
        adapter = new BankAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        showLoading();
        presenetr.getPostRequest(this, ServerUrl.selectBankList, Constant.selectBankList);
    }

    @Override
    protected void initData() {
        userMoney.setText("可提现金额"+UserBean().getUmoney()+"元");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        bankList.setRightText(beans.get(position).getBankname());
        myBankName = beans.get(position).getBankname();
        myBankId= beans.get(position).getBankno();
        popupHelper.dismiss();
    }

    @OnClick({R.id.bank_list, R.id.but_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bank_list:
                popupHelper.show(bankList);
                break;
            case R.id.but_buy:
                if(bankList.getRightText().equals("选择银行")){
                    toast("请选择提现银行");
                    return;
                }
                if(TextUtils.isEmpty(money.getText())){
                toast("请输入提现金额");
                return;
            }
                showLoading();
                presenetr.getPostTokenRequest(this, ServerUrl.withdrawalByUserid, Constant.withdrawalByUserid);
                break;
        }
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case  Constant.selectBankList:
                paramsMap.put("userid",UserBean().getId());
                return paramsMap;
            case Constant.withdrawalByUserid:
                paramsMap.put("userid",UserBean().getId());
                paramsMap.put("money",money.getText().toString().trim());
                paramsMap.put("bankname",myBankName);
                paramsMap.put("bankno",myBankId);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case  Constant.selectBankList:
                beans= GsonUtils.getPersons(data,BankBean.class);
                List<ListModelBean> list=new ArrayList<>();
                for (int i = 0; i <beans.size() ; i++) {
                    ListModelBean bean=new ListModelBean();
                    bean.setName(beans.get(i).getBankname());
                    bean.setId(beans.get(i).getBankno());
                    list.add(bean);
                }
                adapter.replaceData(list);

                break;
            case Constant.withdrawalByUserid:
                this.finish();
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }
}
