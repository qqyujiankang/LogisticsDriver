package com.cn.android.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.cn.android.R;
import com.cn.android.bean.AppUserBean;
import com.cn.android.bean.CityAreaBean;
import com.cn.android.bean.RouteBean;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.RadioButtonGroupHelper;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.dialog.AddressDialog;
import com.cn.android.utils.L;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.layout.SettingBar;

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
public final class AddRouteActivity extends MyActivity implements PublicInterfaceView, CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.route1_1)
    SettingBar route11;
    @BindView(R.id.route1_2)
    SettingBar route12;
    @BindView(R.id.route2_1)
    SettingBar route21;
    @BindView(R.id.route2_2)
    SettingBar route22;
    @BindView(R.id.route3_1)
    SettingBar route31;
    @BindView(R.id.route3_2)
    SettingBar route32;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.cb_route1)
    AppCompatCheckBox cbRoute1;
    @BindView(R.id.cb_route2)
    AppCompatCheckBox cbRoute2;
    @BindView(R.id.cb_route3)
    AppCompatCheckBox cbRoute3;
    private List<RouteBean> list=new ArrayList<>();
    private List<RouteBean> dataList=new ArrayList<>();
    private PublicInterfaceePresenetr presenetr;
    private List<CityAreaBean>areaList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_route;
    }

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected void initView() {
        presenetr=new PublicInterfaceePresenetr(this);
    }

    @Override
    protected void initData() {
        list.add(new RouteBean());
        list.add(new RouteBean());
        list.add(new RouteBean());
        cbRoute1.setOnCheckedChangeListener(this);
        cbRoute2.setOnCheckedChangeListener(this);
        cbRoute3.setOnCheckedChangeListener(this);
        showLoading("加载区域信息");
        presenetr.getPostRequest(this,ServerUrl.selectCityArea,Constant.selectCityArea);
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        dataList.clear();
        for (int i = 0; i <list.size() ; i++) {
            if(null!=list.get(i).getSarea()){
                dataList.add(list.get(i));
            }
        }
        showLoading();
        presenetr.getPostTokenRequest(this, ServerUrl.addLineInfo, Constant.addLineInfo);
    }

    @OnClick({R.id.route1_1, R.id.route1_2, R.id.route2_1, R.id.route2_2, R.id.route3_1, R.id.route3_2})
    public void onViewClicked(View view) {
        getRoute(view.getId());
    }

    private void getRoute(int id) {
        new AddressDialog.Builder(this,areaList)
                .setTitle("选择地区")
                .setListener(new AddressDialog.OnListener() {

                    @Override
                    public void onSelected(BaseDialog dialog, String city, String area) {
                        String address = city +"-"+ area;
                        switch (id) {
                            case R.id.route1_1:
                                route11.setRightText(address);
                                list.get(0).setScity(city);
                                list.get(0).setSarea(area);
                                list.get(0).setSort(1);
                                break;
                            case R.id.route1_2:
                                route12.setRightText(address);
                                list.get(0).setEcity(city);
                                list.get(0).setEarea(area);
                                list.get(0).setSort(1);
                                break;
                            case R.id.route2_1:
                                route21.setRightText(address);
                                list.get(1).setScity(city);
                                list.get(1).setSarea(area);
                                list.get(1).setSort(2);
                                break;
                            case R.id.route2_2:
                                route22.setRightText(address);
                                list.get(1).setEcity(city);
                                list.get(1).setEarea(area);
                                list.get(1).setSort(2);
                                break;
                            case R.id.route3_1:
                                route31.setRightText(address);
                                list.get(2).setScity(city);
                                list.get(2).setSarea(area);
                                list.get(2).setSort(3);
                                break;
                            case R.id.route3_2:
                                route32.setRightText(address);
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
            case  Constant.addLineInfo:
                paramsMap.put("userid",UserBean().getId());
                paramsMap.put("data",new Gson().toJson(dataList));
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case  Constant.addLineInfo:
                startActivityFinish(HomeActivity.class);
                break;
            case  Constant.selectCityArea:
                areaList= GsonUtils.getPersons(data,CityAreaBean.class);
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
        toast(error);
    }
}