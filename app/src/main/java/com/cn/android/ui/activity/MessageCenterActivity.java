package com.cn.android.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public final class MessageCenterActivity extends MyActivity  implements PublicInterfaceView {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.msg_content1)
    TextView msgContent1;
    @BindView(R.id.msg_content2)
    TextView msgContent2;
    @BindView(R.id.msg_content3)
    TextView msgContent3;
    @BindView(R.id.msg_content4)
    TextView msgContent4;
    private PublicInterfaceePresenetr presenetr;
    private List<MessageBean> infoList=new ArrayList<>();

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initView() {
        presenetr=new PublicInterfaceePresenetr(this);
        showLoading();
        presenetr.getPostRequest(this, ServerUrl.selectMessageType, Constant.selectMessageType);
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.linear_msg1, R.id.linear_msg2, R.id.linear_msg3, R.id.linear_msg4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_msg1:
                startActivity(new Intent(this,ReceiptSettlementActivity.class).putExtra("type",0));
                break;
            case R.id.linear_msg2:
                startActivity(new Intent(this,ReceiptSettlementActivity.class).putExtra("type",1));
                break;
            case R.id.linear_msg3:
                startActivity(new Intent(this,ReceiptSettlementActivity.class).putExtra("type",2));
                break;
            case R.id.linear_msg4:
                startActivity(new Intent(this,ReceiptSettlementActivity.class).putExtra("type",3));
                break;
        }
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case  Constant.selectMessageType:
                paramsMap.put("userid",UserBean().getId());
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case  Constant.selectMessageType:
                infoList= GsonUtils.getPersons(data, MessageBean.class);
                for (int i = 0; i <infoList.size() ; i++) {
                    switch (infoList.get(i).getType()) {
                        case  1:
                            msgContent1.setText(infoList.get(i).getContent());
                            break;
                        case  2:
                            msgContent2.setText(infoList.get(i).getContent());
                            break;
                        case  3:
                            msgContent3.setText(infoList.get(i).getContent());
                            break;
                        case  4:
                            msgContent4.setText(infoList.get(i).getContent());
                            break;
                    }
                }
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }

}