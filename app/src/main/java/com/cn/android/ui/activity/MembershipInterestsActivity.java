package com.cn.android.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.cn.android.R;
import com.cn.android.bean.CityAreaBean;
import com.cn.android.bean.LineInfoBean;
import com.cn.android.bean.RouteBean;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.dialog.AddressDialog;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.google.gson.Gson;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.dialog.base.BaseDialogFragment;
import com.hjq.widget.view.ClearEditText;
import com.hjq.widget.view.CountdownView;

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
 * desc   : 登录界面
 */
public final class MembershipInterestsActivity extends MyActivity implements PublicInterfaceView , CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.main_route1)
    TextView mainRoute1;
    @BindView(R.id.main_route2)
    TextView mainRoute2;
    @BindView(R.id.assist_route1)
    TextView assistRoute1;
    @BindView(R.id.assist_route2)
    TextView assistRoute2;
    @BindView(R.id.assist_route3)
    TextView assistRoute3;
    @BindView(R.id.assist_route4)
    TextView assistRoute4;
    @BindView(R.id.btn_ok)
    AppCompatButton btnOk;
    @BindView(R.id.cb_route1)
    AppCompatCheckBox cbRoute1;
    @BindView(R.id.cb_route2)
    AppCompatCheckBox cbRoute2;
    @BindView(R.id.cb_route3)
    AppCompatCheckBox cbRoute3;
    private PublicInterfaceePresenetr presenetr;
    private List<LineInfoBean> infoList;
    private List<RouteBean> list = new ArrayList<>();
    private List<CityAreaBean> areaList = new ArrayList<>();
    private List<RouteBean> dataList=new ArrayList<>();
    private String smscode="";

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_membership_interests;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);
    }

    @Override
    protected void initData() {
        list.add(new RouteBean());
        list.add(new RouteBean());
        list.add(new RouteBean());
        cbRoute1.setOnCheckedChangeListener(this);
        cbRoute2.setOnCheckedChangeListener(this);
        cbRoute3.setOnCheckedChangeListener(this);
        showLoading();
        presenetr.getPostRequest(this, ServerUrl.selectCityArea, Constant.selectCityArea);
        presenetr.getPostRequest(this, ServerUrl.selectLineInfoList, Constant.selectLineInfoList);
    }


    @OnClick({R.id.main_route1, R.id.main_route2, R.id.assist_route1, R.id.assist_route2, R.id.assist_route3, R.id.assist_route4, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_route1:
            case R.id.main_route2:
            case R.id.assist_route1:
            case R.id.assist_route2:
            case R.id.assist_route3:
            case R.id.assist_route4:
                getRoute(view.getId());
                break;
            case R.id.btn_ok:
                dataList.clear();
                for (int i = 0; i <list.size() ; i++) {
                    if(null!=list.get(i).getSarea()&&null!=list.get(i).getEarea()){
                        dataList.add(list.get(i));
                    }
                }
                showDialog();
                break;
        }
    }

    private void getRoute(int id) {
        new AddressDialog.Builder(this, areaList)
                .setTitle("选择地区")
                .setListener(new AddressDialog.OnListener() {

                    @Override
                    public void onSelected(BaseDialog dialog, String city, String area) {
                        String address = city + area;
                        switch (id) {
                            case R.id.main_route1:
                                mainRoute1.setText(address);
                                list.get(0).setScity(city);
                                list.get(0).setSarea(area);
                                list.get(0).setSort(1);
                                break;
                            case R.id.main_route2:
                                mainRoute2.setText(address);
                                list.get(0).setEcity(city);
                                list.get(0).setEarea(area);
                                list.get(0).setSort(1);
                                break;
                            case R.id.assist_route1:
                                assistRoute1.setText(address);
                                list.get(1).setScity(city);
                                list.get(1).setSarea(area);
                                list.get(1).setSort(2);
                                break;
                            case R.id.assist_route2:
                                assistRoute2.setText(address);
                                list.get(1).setEcity(city);
                                list.get(1).setEarea(area);
                                list.get(1).setSort(2);
                                break;
                            case R.id.assist_route3:
                                assistRoute3.setText(address);
                                list.get(2).setScity(city);
                                list.get(2).setSarea(area);
                                list.get(2).setSort(3);
                                break;
                            case R.id.assist_route4:
                                assistRoute4.setText(address);
                                list.get(2).setEcity(city);
                                list.get(2).setEarea(area);
                                list.get(2).setSort(3);
                                break;
                        }
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                    }
                })
                .show();
    }

    private void showDialog() {
        BaseDialogFragment.Builder builder = new BaseDialogFragment.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_membership_interests, new FrameLayout(this), false);
        TextView tv_phone = view.findViewById(R.id.tv_phone);
        ClearEditText cet = view.findViewById(R.id.code);
        CountdownView countdownView = view.findViewById(R.id.countdown);
        countdownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取验证码
                if (tv_phone.getText().toString().length() != 11) {
                    // 重置验证码倒计时控件
                    countdownView.resetState();
                    toast(R.string.common_phone_input_error);
                } else {
                    dataList.clear();
                    for (int i = 0; i <list.size() ; i++) {
                        if(null!=list.get(i).getSarea()){
                            dataList.add(list.get(i));
                        }
                    }
                    showLoading();
                    presenetr.getPostRequest(MembershipInterestsActivity.this, ServerUrl.sendSms, Constant.sendSms);
                    toast(R.string.common_code_send_hint);
                }
            }
        });
        AppCompatButton button = view.findViewById(R.id.btn_ok);
        tv_phone.setText("15339256798");
        builder.setContentView(view);
        BaseDialog dialog = builder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                smscode=cet.getText().toString().trim();
                if(smscode.equals("")){
                    toast("验证码不能为空");
                    return;
                }
                showLoading();
                presenetr.getPostTokenRequest(MembershipInterestsActivity.this, ServerUrl.updateLineInfo, Constant.updateLineInfo);
            }
        });
        dialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case  R.id.cb_route1:
                if(isChecked){
                    list.get(0).setIs_back(1);
                }else{
                    list.get(0).setIs_back(2);
                }
                break;
            case  R.id.cb_route2:
                if(isChecked){
                    list.get(1).setIs_back(1);
                }else{
                    list.get(1).setIs_back(2);
                }
                break;
            case  R.id.cb_route3:
                if(isChecked){
                    list.get(2).setIs_back(1);
                }else{
                    list.get(2).setIs_back(2);
                }
                break;
        }
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectLineInfoList:
                paramsMap.put("userid", UserBean().getId());
                return paramsMap;
            case Constant.updateLineInfo:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("data",new Gson().toJson(dataList));
                paramsMap.put("smscode",smscode);
                return paramsMap;
            case Constant.sendSms:
                paramsMap.put("loginName", UserBean().getUserphone());
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.selectLineInfoList:
                infoList = GsonUtils.getPersons(data, LineInfoBean.class);
                for (int i = 0; i < infoList.size(); i++) {
                    if (i == 0) {
                        mainRoute1.setText(infoList.get(i).getSaddress());
                        mainRoute2.setText(infoList.get(i).getEaddress());
                        setData(i);
                        switch (infoList.get(i).getIsBack()) {
                            case  1:
                                cbRoute1.setChecked(true);
                                break;
                            case  2:
                                cbRoute1.setChecked(false);
                                break;
                        }
                    } else if (i == 1) {
                        assistRoute1.setText(infoList.get(i).getSaddress());
                        assistRoute2.setText(infoList.get(i).getEaddress());
                        setData(i);
                        switch (infoList.get(i).getIsBack()) {
                            case  1:
                                cbRoute2.setChecked(true);
                                break;
                            case  2:
                                cbRoute2.setChecked(false);
                                break;
                        }
                    } else if (i == 2) {
                        assistRoute3.setText(infoList.get(i).getSaddress());
                        assistRoute4.setText(infoList.get(i).getEaddress());
                        setData(i);
                        switch (infoList.get(i).getIsBack()) {
                            case  1:
                                cbRoute3.setChecked(true);
                                break;
                            case  2:
                                cbRoute3.setChecked(false);
                                break;
                        }
                    }
                }
                break;
            case Constant.selectCityArea:
                areaList = GsonUtils.getPersons(data, CityAreaBean.class);
                break;
            case Constant.updateLineInfo:
                this.finish();
                break;
        }
    }

    private void setData(int i) {
        list.get(i).setScity(infoList.get(i).getScity());
        list.get(i).setSarea(infoList.get(i).getSarea());
        list.get(i).setEcity(infoList.get(i).getEcity());
        list.get(i).setEarea(infoList.get(i).getEarea());
        list.get(i).setIs_back(infoList.get(i).getIsBack());
        list.get(i).setSort(infoList.get(i).getSort());
        list.get(i).setId(infoList.get(i).getId());
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }
}