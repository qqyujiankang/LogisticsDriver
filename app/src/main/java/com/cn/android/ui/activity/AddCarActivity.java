package com.cn.android.ui.activity;


import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cn.android.R;
import com.cn.android.bean.ListModelBean;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.PopupWindowHelper;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.FileOperationPresenetr;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.FileOperationView;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.adapter.BankAdapter;
import com.cn.android.utils.KeyboardUtil;
import com.hjq.bar.TitleBar;
import com.hjq.image.ImageLoader;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.view.ClearEditText;

import java.io.File;
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
public final class AddCarActivity extends MyActivity implements
        PublicInterfaceView, FileOperationView,
        BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.car_no)
    ClearEditText carNo;
    @BindView(R.id.car_model)
    TextView carModel;
    @BindView(R.id.car_phone)
    ClearEditText carPhone;
    @BindView(R.id.car_msg1)
    ImageView carMsg1;
    @BindView(R.id.car_msg2)
    ImageView carMsg2;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;
    private PopupWindowHelper popupHelper;
    private RecyclerView recyclerView;
    private BankAdapter adapter;
    private List<ListModelBean> list;
    private PublicInterfaceePresenetr presenetr;
    private FileOperationPresenetr filePresenetr;
    private String car_model = "", car_phone = "";
    private String driver_img, run_img;
    private KeyboardUtil keyboardUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_car;
    }

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);
        filePresenetr = new FileOperationPresenetr(this);
        popupHelper = new PopupWindowHelper(this, R.layout.popup_list);
        recyclerView = popupHelper.getPopupView().findViewById(R.id.popup_recyclerView);
        adapter = new BankAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        showLoading();
        presenetr.getPostRequest(this, ServerUrl.selectCarModel, Constant.selectCarModel);
    }

    @Override
    protected void initData() {
        carNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (keyboardUtil == null) {
                    keyboardUtil = new KeyboardUtil(AddCarActivity.this, carNo);
                    keyboardUtil.hideSoftInputMethod();
                    keyboardUtil.showKeyboard();
                } else {
                    keyboardUtil.showKeyboard();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyboardUtil.isShow()) {
                keyboardUtil.hideKeyboard();
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        carModel.setText(list.get(position).getName());
        car_model = list.get(position).getName();
        popupHelper.dismiss();
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.selectCarModel:
                return paramsMap;
            case Constant.saveCarInfo:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("car_model", car_model);
                paramsMap.put("car_no", carNo.getText().toString().trim());
                paramsMap.put("phone", carPhone.getText().toString().trim());
                paramsMap.put("driver_img", driver_img);
                paramsMap.put("run_img", run_img);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.selectCarModel:
                list = GsonUtils.getPersons(data, ListModelBean.class);
                adapter.replaceData(list);
                break;
            case Constant.saveCarInfo:
                this.finish();
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }

    @OnClick({R.id.car_model, R.id.car_msg1, R.id.car_msg2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.car_model:
                popupHelper.show(carModel);
                break;
            case R.id.car_msg1:
                selectPhoto(carMsg1);
                break;
            case R.id.car_msg2:
                selectPhoto(carMsg2);
                break;
        }
    }

    private int uploadFileType = 0;

    private void selectPhoto(ImageView imageView) {
        PhotoActivity.start(this, new PhotoActivity.OnPhotoSelectListener() {

            @Override
            public void onSelect(List<String> data) {
                uploadFileType = imageView.getId();
                showLoading();
                progressBar.setVisibility(View.VISIBLE);
                filePresenetr.uploadSingleFileRequest(AddCarActivity.this, "file", new File(data.get(0)), ServerUrl.upload, Constant.upload);
                ImageLoader.with(AddCarActivity.this)
                        .load(data.get(0))
                        .circle(20)
                        .into(imageView);
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    public void FileOperationSuccess(Object data, int tag) {
        showComplete();
        progressBar.setVisibility(View.GONE);
        switch (uploadFileType) {
            case R.id.car_msg1:
                driver_img = (String) data;
                break;
            case R.id.car_msg2:
                run_img = (String) data;
                break;
        }
    }

    @Override
    public void FileOperationProgress(float progress, int tag) {
        progressBar.setProgress((int) (100 * progress));
    }

    @Override
    public void FileOperationError(String error, int tag) {
        showComplete();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRightClick(View v) {
        if (TextUtils.isEmpty(carNo.getText())) {
            ToastUtils.show("请输入车牌号");
            return;
        }
        if (TextUtils.isEmpty(carPhone.getText())) {
            ToastUtils.show("请输入手机号");
            return;
        }
        showLoading();
        presenetr.getPostTokenRequest(this, ServerUrl.saveCarInfo, Constant.saveCarInfo);
    }
}