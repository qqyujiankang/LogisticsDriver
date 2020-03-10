package com.cn.android.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cn.android.R;
import com.cn.android.bean.MiddleBean;
import com.cn.android.bean.PickTimeBean;
import com.cn.android.common.MyLazyFragment;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.activity.CourierNumberActivity;
import com.cn.android.ui.activity.HomeActivity;
import com.cn.android.ui.activity.LoadingGoodsActivity;
import com.cn.android.ui.activity.MyOrderDetailsActivity;
import com.cn.android.ui.activity.ReceiptActivity;
import com.cn.android.ui.activity.ReimbursementActivity;
import com.cn.android.ui.activity.WaitingTimeActivity;
import com.cn.android.ui.adapter.MyOrderAdapter;
import com.cn.android.ui.dialog.MenuDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseActivity;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.dialog.base.BaseDialogFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目自定义控件展示
 */
public final class FragmentMiddle extends MyLazyFragment<HomeActivity> implements PublicInterfaceView,
        BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemClickListener, BaseActivity.ActivityCallback,
        OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private MyOrderAdapter adapter;
    private List<MiddleBean> list = new ArrayList<>();
    private List<MiddleBean> showList = new ArrayList<>();
    private List<PickTimeBean> timeList = new ArrayList<>();
    private BaseDialog dialog;
    private PublicInterfaceePresenetr presenetr;
    private int orderPposition = 0, pickTimePposition = 0;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    public static FragmentMiddle newInstance() {
        return new FragmentMiddle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        ImmersionBar.setTitleBar(getAttachActivity(), titleBar);
        presenetr = new PublicInterfaceePresenetr(this);
        titleBar.setLeftIcon(null);
        titleBar.setTitle("我的订单");
        adapter = new MyOrderAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);
        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {
        showLoading();
        presenetr.getPostRequest(getActivity(), ServerUrl.selectOrderListByUserid, Constant.selectOrderListByUserid);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(getActivity(), MyOrderDetailsActivity.class).putExtra("orderid", showList.get(position).getId()));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.next_step:
                orderPposition = position;
                butNextStep(position);
                break;
            case R.id.kefu:
                showDialog(position);
                break;
        }
    }

    private void showDialog(int position) {
        BaseDialogFragment.Builder builder = new BaseDialogFragment.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_call_phone, new FrameLayout(getActivity()), false);
        view.findViewById(R.id.phone1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callPhone(showList.get(position).getUser_tel());
            }
        });
        view.findViewById(R.id.phone2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callPhone(showList.get(position).getUser_phone());
            }
        });
        builder.setContentView(view);
        dialog = builder.create();
        dialog.show();
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    private int page = 1, row = 10;
    private boolean isUpRefresh = true;

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectOrderListByUserid:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("page", page);
                paramsMap.put("rows", row);
                return paramsMap;
            case Constant.selectPickTime:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("id", showList.get(orderPposition).getId());
                return paramsMap;
            case Constant.addPickTime:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("id", showList.get(orderPposition).getId());
                paramsMap.put("time", timeList.get(pickTimePposition).getTime());
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.selectOrderListByUserid:
                if(isUpRefresh){
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
            case Constant.selectPickTime:
                timeList = GsonUtils.getPersons(data, PickTimeBean.class);
                List<String> showTime = new ArrayList<>();
                for (int i = 0; i < timeList.size(); i++) {
                    showTime.add(timeList.get(i).getStime() + " - " + timeList.get(i).getEtime());
                }
                new MenuDialog.Builder(getActivity())
                        .setList(showTime)
                        .setListener(new MenuDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int position, Object o) {
                                showLoading();
                                pickTimePposition = position;
                                presenetr.getPostTokenRequest(getActivity(), ServerUrl.addPickTime, Constant.addPickTime);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {

                            }
                        }).show();
                break;
            case Constant.addPickTime:
                initData();
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }


    private void butNextStep(int position) {
        switch (showList.get(position).getStatus()) {
            case 0://0选择提货时间
                showLoading();
                presenetr.getPostRequest(getActivity(), ServerUrl.selectPickTime, Constant.selectPickTime);
                break;
            case 1://1申请待时(上传开始照片)
                startActivityForResult(new Intent(getActivity(), WaitingTimeActivity.class).putExtra("orderid",
                        showList.get(position).getId()).putExtra("state", 0), this);
                break;
            case 2://2装车拍照
                startActivityForResult(new Intent(getActivity(), LoadingGoodsActivity.class).putExtra("orderid", showList.get(position).getId()), this);
                break;
            case 3://3申请待时(上传结束照片)
                startActivityForResult(new Intent(getActivity(), WaitingTimeActivity.class).putExtra("orderid",
                        showList.get(position).getId()).putExtra("state", 1), this);
                break;
            case 4://4上传回单
                startActivityForResult(new Intent(getActivity(), ReceiptActivity.class).putExtra("orderid", showList.get(position).getId()), this);
                break;
            case 5://5报销费用
                startActivityForResult(new Intent(getActivity(), ReimbursementActivity.class).putExtra("orderid",
                        showList.get(position).getId()).putExtra("freightprice", showList.get(position).getFreightprice()), this);
                break;
            case 6://6邮寄单号填写
                startActivityForResult(new Intent(getActivity(), CourierNumberActivity.class).putExtra("orderid", showList.get(position).getId()), this);
                break;
            case 7://7已完成待入账
                break;
            case 8://8已完成已入账
                break;
        }
    }

    @Override
    public void onActivityResult(int resultCode, @Nullable Intent data) {
        initData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isUpRefresh = false;
        page = page + 1;
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isUpRefresh = true;
        page = 1;
        initData();
    }
}