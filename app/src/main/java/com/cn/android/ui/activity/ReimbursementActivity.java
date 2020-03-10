package com.cn.android.ui.activity;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cn.android.R;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
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
public final class ReimbursementActivity extends MyActivity implements
        PublicInterfaceView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.cost1)
    TextView cost1;
    @BindView(R.id.cost2)
    EditText cost2;
    @BindView(R.id.cost3)
    EditText cost3;
    @BindView(R.id.cost4)
    EditText cost4;
    @BindView(R.id.cost5)
    EditText cost5;
    @BindView(R.id.cost6)
    EditText cost6;
    @BindView(R.id.cost_sum)
    TextView costSum;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private PublicInterfaceePresenetr presenetr;
    private String orderid = "";
    private String freightprice = "";
    private double freight = 0;
    private double cost_1;
    private double cost_2;
    private double cost_3;
    private double cost_4;
    private double cost_5;
    private double cost_6;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reimbursement;
    }

    @Override
    protected void initView() {
        orderid = getIntent().getStringExtra("orderid");
        freightprice = getIntent().getStringExtra("freightprice");
        presenetr = new PublicInterfaceePresenetr(this);
    }

    private void costSum(CharSequence s) {
        if (s.toString().equals(".")) {
            return;
        }
        if (cost1.getText().toString() == "") {
            cost_1 = Double.valueOf("0");
        } else {

            cost_1 = Double.valueOf(cost1.getText().toString());
        }
        if (cost2.getText().toString().trim().equals("")) {
            cost_2 = Double.valueOf("0");
        } else {

            cost_2 = Double.valueOf(cost2.getText().toString());
        }
        if (cost3.getText().toString().trim().equals("")) {
            cost_3 = Double.valueOf("0");
        } else {
            cost_3 = Double.valueOf(cost3.getText().toString());
        }
        if (cost4.getText().toString().trim().equals("")) {
            cost_4 = Double.valueOf("0");
        } else {
            cost_4 = Double.valueOf(cost4.getText().toString());
        }
        if (cost5.getText().toString().trim().equals("")) {
            cost_5 = Double.valueOf("0");
        } else {
            cost_5 = Double.valueOf(cost5.getText().toString());
        }
        if (cost6.getText().toString().trim().equals("")) {
            cost_6 = Double.valueOf("0");
        } else {
            cost_6 = Double.valueOf(cost6.getText().toString());
        }
        costSum.setText((cost_1 + cost_2 + cost_3 + cost_4 + cost_5+cost_6) + "");
    }

    @Override
    protected void initData() {
        freight = Double.valueOf(freightprice);
        cost1.setText(freightprice);
        costSum.setText(freightprice);
        cost2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                costSum(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        cost3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                costSum(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        cost4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                costSum(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        cost5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                costSum(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        cost6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                costSum(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.addApplyAccount:
                paramsMap.put("id", orderid);
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("freightmoney", cost_1);
                paramsMap.put("outDoormoney", cost_2);
                paramsMap.put("expressmoney", cost_3);
                paramsMap.put("entrymoney", cost_4);
                paramsMap.put("outmoney", cost_5);
                paramsMap.put("othermoney", cost_6);
                paramsMap.put("totalmoney", costSum.getText().toString().trim());
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.addApplyAccount:
                new MessageDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("您的费用结算会在48小时内处理完成，请您不要着急耐心等候。")
                        .setCancel("")
                        .setCanceledOnTouchOutside(false)
                        .setConfirm("确认").setListener(new MessageDialog.OnListener() {
                    @Override
                    public void onConfirm(BaseDialog dialog) {
                        ReimbursementActivity.this.finish();
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {

                    }
                }).show();
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        presenetr.getPostTokenRequest(getActivity(), ServerUrl.addApplyAccount, Constant.addApplyAccount);
    }
}