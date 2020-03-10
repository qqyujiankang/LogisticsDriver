package com.cn.android.ui.fragment;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cn.android.R;
import com.cn.android.bean.AppAccountInfo;
import com.cn.android.bean.MiddleBean;
import com.cn.android.bean.Update;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyLazyFragment;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.activity.AddBankActivity;
import com.cn.android.ui.activity.AddRouteActivity;
import com.cn.android.ui.activity.BuyVIPActivity;
import com.cn.android.ui.activity.DriverAuthenticationActivity;
import com.cn.android.ui.activity.HomeActivity;
import com.cn.android.ui.activity.LoginActivity;
import com.cn.android.ui.activity.OrderDetailsActivity;
import com.cn.android.ui.adapter.LeftAdapter;
import com.cn.android.ui.dialog.MenuDialog;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.PayUtils;
import com.cn.android.utils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.dialog.base.BaseDialogFragment;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.layout.SettingBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目自定义控件展示
 */
public final class FragmentLeft extends MyLazyFragment<HomeActivity> implements PublicInterfaceView,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener,
        OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private LeftAdapter adapter;
    private List<MiddleBean> list = new ArrayList<>();
    private List<MiddleBean> showList = new ArrayList<>();
    private List<String> data = new ArrayList<>();
    private int payType = 1;
    private int orderPposition = 0;
    private String orderId = "";
    private PublicInterfaceePresenetr presenetr;

    public static FragmentLeft newInstance() {
        return new FragmentLeft();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_left;
    }

    @Override
    protected void initView() {
        ImmersionBar.setTitleBar(getAttachActivity(), titleBar);
        presenetr = new PublicInterfaceePresenetr(this);
        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setOnLoadMoreListener(this);
        getData();
    }

    private void getData() {
        showLoading();
        presenetr.getPostRequest(getActivity(), ServerUrl.selectOrderList, Constant.selectOrderList);
    }

    @Override
    public void initData() {
        data.add("单次购买");
        data.add("VIP购买");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LeftAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpData(Update event) {
        smartRefresh.autoRefresh();
        getData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        Intent outIntent = new Intent(Constant.exit_out);
//        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(outIntent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        orderPposition = position;
        switch (view.getId()) {
            case R.id.button:
                if (!SPUtils.contains("AppUser")) {
                    goLogin(0, "您的账户未登陆");
                    return;
                }
                if (UserBean().getIsRegister() != 4) {
                    goLogin(1, "您的账户信息尚未补全");
                    return;
                }
                if (UserBean().getIsReal() != 1) {
                    toast("资料尚未审核通过");
                    return;
                }
                orderId = showList.get(position).getId();
                if (UserBean().getGarde() != 0) {
                    startActivity(new Intent(getActivity(), OrderDetailsActivity.class).putExtra("orderid", orderId));
                } else {
                    if (showList.get(position).getMessageprice() == 0.0) {
                        startActivity(new Intent(getActivity(), OrderDetailsActivity.class).putExtra("orderid", orderId));
                    } else {
                        new MenuDialog.Builder(getActivity())
                                .setCancel(getString(R.string.common_cancel))
                                .setList(data)
                                .setListener(new MenuDialog.OnListener<String>() {

                                    @Override
                                    public void onSelected(BaseDialog dialog, int position, String string) {
                                        switch (position) {
                                            case 0:
                                                buyPay();
                                                break;
                                            case 1:
                                                startActivity(BuyVIPActivity.class);
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onCancel(BaseDialog dialog) {
                                    }
                                })
                                .show();
                    }
                }
                break;
        }
    }

    private void goLogin(int type, String content) {
        new MessageDialog.Builder(getActivity())
                .setTitle("温馨提示")
                .setMessage(content)
                .setConfirm(type == 0 ? "去登陆" : "去补全")
                .setAutoDismiss(true)
                .setListener(new MessageDialog.OnListener() {
                    @Override
                    public void onConfirm(BaseDialog dialog) {
                        dialog.dismiss();
                        switch (type) {
                            case 0:
                                startActivity(LoginActivity.class);
                                break;
                            case 1:
                                switch (UserBean().getIsRegister()) {
                                    case 0:
                                        Intent intent = new Intent(getActivity(), DriverAuthenticationActivity.class);
                                        intent.putExtra("step", 0);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        Intent intent2 = new Intent(getActivity(), DriverAuthenticationActivity.class);
                                        intent2.putExtra("step", 1);
                                        startActivity(intent2);
                                        break;
                                    case 2:
                                        startActivity(AddBankActivity.class);
                                        break;
                                    case 3:
                                        startActivity(AddRouteActivity.class);
                                        break;
                                }
                                break;
                        }
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void buyPay() {
        BaseDialogFragment.Builder builder = new BaseDialogFragment.Builder(getActivity());
        builder.setGravity(Gravity.BOTTOM);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pay, new FrameLayout(getActivity()), false);
        SettingBar wx = view.findViewById(R.id.wx);
        SettingBar zfb = view.findViewById(R.id.zfb);
        SettingBar ye = view.findViewById(R.id.ye);
        wx.setRightText(showList.get(orderPposition).getMessageprice() + "元");
        zfb.setRightText(showList.get(orderPposition).getMessageprice() + "元");
        ye.setRightText(showList.get(orderPposition).getMessageprice() + "元");
        zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 1;
                wx.setRightIcon(R.mipmap.vip_un_select);
                zfb.setRightIcon(R.mipmap.vip_select);
                ye.setRightIcon(R.mipmap.vip_un_select);
            }
        });
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 2;
                wx.setRightIcon(R.mipmap.vip_select);
                zfb.setRightIcon(R.mipmap.vip_un_select);
                ye.setRightIcon(R.mipmap.vip_un_select);
            }
        });
        ye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 3;
                wx.setRightIcon(R.mipmap.vip_un_select);
                zfb.setRightIcon(R.mipmap.vip_un_select);
                ye.setRightIcon(R.mipmap.vip_select);
            }
        });
        AppCompatButton button = view.findViewById(R.id.but_pay);
        builder.setContentView(view);
        BaseDialog myDialog = builder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                switch (payType) {
                    case 1:
                        ToastUtils.show("支付宝支付");
                        break;
                    case 2:
                        ToastUtils.show("微信支付");
                        break;
                    case 3:
                        showLoading();
                        new PayUtils().infoPay(getActivity(), 3, UserBean().getId(), payType, showList.get(orderPposition).getMessageprice(),
                                showList.get(orderPposition).getId(), 0, 2, 2, 2)
                                .setPayListener(new PayUtils.OnPayListener() {
                                    @Override
                                    public void paySuccess(int payType) {
                                        showComplete();
                                        getData();
                                        startActivity(new Intent(getActivity(), OrderDetailsActivity.class).putExtra("orderid", orderId));
                                    }

                                    @Override
                                    public void payError(int payType) {
                                        showComplete();
                                        toast("余额支付Error");
                                    }
                                });
                        break;
                }
            }
        });
        myDialog.show();
    }


    private int page = 1, row = 10;
    private boolean isUpRefresh = true;

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectOrderList:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("page", page);
                paramsMap.put("rows", row);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.selectOrderList:
                if (isUpRefresh) {
                    showList.clear();
                    if (data.equals("null")) {
                        showEmpty();
                        return;
                    }
                }
                smartRefresh.closeHeaderOrFooter();
                list = GsonUtils.getPersons(data, MiddleBean.class);
                showList.addAll(list);
                adapter.replaceData(showList);
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }

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