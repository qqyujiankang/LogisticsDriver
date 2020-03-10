package com.cn.android.ui.activity;


import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cn.android.R;
import com.cn.android.bean.ListModelBean;
import com.cn.android.common.MyActivity;
import com.cn.android.network.Constant;
import com.cn.android.network.GsonUtils;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.FileOperationPresenetr;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.FileOperationView;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.adapter.CarTypeAdapter;
import com.cn.android.utils.KeyboardUtil;
import com.hjq.bar.TitleBar;
import com.hjq.image.ImageLoader;
import com.hjq.toast.ToastUtils;

import java.io.File;
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
public final class DriverAuthenticationActivity extends MyActivity implements
        BaseQuickAdapter.OnItemClickListener, PublicInterfaceView, FileOperationView {

    @BindView(R.id.iv_driver_progress1)
    ImageView ivDriverProgress1;
    @BindView(R.id.tv_driver_progress1)
    TextView tvDriverProgress1;
    @BindView(R.id.iv_driver_progress2)
    ImageView ivDriverProgress2;
    @BindView(R.id.tv_driver_progress2)
    TextView tvDriverProgress2;
    @BindView(R.id.idCard1)
    ImageView idCard1;
    @BindView(R.id.idCard2)
    ImageView idCard2;
    @BindView(R.id.portrait)
    ImageView portrait;
    @BindView(R.id.drivingLicense1)
    ImageView drivingLicense1;
    @BindView(R.id.drivingLicense2)
    ImageView drivingLicense2;
    @BindView(R.id.linear_step1)
    LinearLayout linearStep1;
    @BindView(R.id.linear_step2)
    LinearLayout linearStep2;
    @BindView(R.id.btn_authe_next)
    AppCompatButton btnAutheNext;
    @BindView(R.id.car_type_recyclerView)
    RecyclerView carTypeRecyclerView;
    @BindView(R.id.carNumber)
    EditText carNumber;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;

    private int step = 0;
    private KeyboardUtil keyboardUtil;
    private CarTypeAdapter adapter;
    private List<ListModelBean> type;
    private PublicInterfaceePresenetr postPresenetr;
    private FileOperationPresenetr filePresenetr;
    private int uploadFileType = 0;
    private String idcard_front = "";
    private String idcard_back = "";
    private String photo_am = "";
    private String drive_img = "";
    private String run_img = "";
    private String carModel= "",carNo="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_driver_authentication;
    }

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected void initView() {
        step = getIntent().getIntExtra("step", 0);
        showLayout(step);
        postPresenetr = new PublicInterfaceePresenetr(this);
        filePresenetr = new FileOperationPresenetr(this);
        showLoading();
        postPresenetr.getPostRequest(this,ServerUrl.selectCarModel,Constant.selectCarModel);
    }

    @Override
    protected void initData() {
        adapter = new CarTypeAdapter(this);
        carTypeRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        carTypeRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        carNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (keyboardUtil == null) {
                    keyboardUtil = new KeyboardUtil(DriverAuthenticationActivity.this, carNumber);
                    keyboardUtil.hideSoftInputMethod();
                    keyboardUtil.showKeyboard();
                } else {
                    keyboardUtil.showKeyboard();
                }
                return false;
            }
        });
    }

    private void showLayout(int step) {
        switch (step) {
            case 0:
                ivDriverProgress1.setImageResource(R.mipmap.da_icon1_1);
                tvDriverProgress1.setTextColor(getResources().getColor(R.color.colorAccent));
                ivDriverProgress2.setImageResource(R.mipmap.da_icon2_1);
                tvDriverProgress2.setTextColor(getResources().getColor(R.color.textColor));
                linearStep1.setVisibility(View.VISIBLE);
                linearStep2.setVisibility(View.GONE);
                break;
            case 1:
                ivDriverProgress1.setImageResource(R.mipmap.da_icon1_2);
                tvDriverProgress1.setTextColor(getResources().getColor(R.color.textColor));
                ivDriverProgress2.setImageResource(R.mipmap.da_icon2_2);
                tvDriverProgress2.setTextColor(getResources().getColor(R.color.colorAccent));
                linearStep1.setVisibility(View.GONE);
                linearStep2.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        for (int i = 0; i < type.size(); i++) {
            if (i == position) {
                type.get(i).setSelect(true);

            } else {
                type.get(i).setSelect(false);
            }
        }
        carModel=type.get(position).getName();
        adapter.replaceData(type);
    }

    @OnClick({R.id.idCard1, R.id.idCard2, R.id.portrait, R.id.drivingLicense1, R.id.drivingLicense2, R.id.linear_step1, R.id.btn_authe_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.idCard1:
                selectPhoto(idCard1);
                break;
            case R.id.idCard2:
                selectPhoto(idCard2);
                break;
            case R.id.portrait:
                selectPhoto(portrait);
                break;
            case R.id.drivingLicense1:
                selectPhoto(drivingLicense1);
                break;
            case R.id.drivingLicense2:
                selectPhoto(drivingLicense2);
                break;
            case R.id.linear_step1:
                break;
            case R.id.btn_authe_next:
                switch (step) {
                    case 0:
                        if(idcard_front.equals("")||idcard_back.equals("")||photo_am.equals("")||drive_img.equals("")||run_img.equals("")){
                            toast("请上传对应照片");
                            return;
                        }
                        postPresenetr.getPostTokenRequest(this, ServerUrl.certificationDriver, Constant.certificationDriver);
                        break;
                    case 1:
                        if (TextUtils.isEmpty(carNumber.getText())) {
                            ToastUtils.show("请输入车牌号");
                            return;
                        }
                        carNo=carNumber.getText().toString().trim();
                        showLoading();
                        postPresenetr.getPostTokenRequest(this, ServerUrl.addCarInfo, Constant.addCarInfo);
                        break;
                }
                break;
        }
    }

    private void selectPhoto(ImageView imageView) {
        PhotoActivity.start(this, new PhotoActivity.OnPhotoSelectListener() {

            @Override
            public void onSelect(List<String> data) {
                uploadFileType = imageView.getId();
                showLoading();
                progressBar.setVisibility(View.VISIBLE);
                filePresenetr.uploadSingleFileRequest(DriverAuthenticationActivity.this, "file", new File(data.get(0)), ServerUrl.upload, Constant.upload);
                ImageLoader.with(DriverAuthenticationActivity.this)
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
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.certificationDriver:
                paramsMap.put("idcard_front", idcard_front);
                paramsMap.put("idcard_back", idcard_back);
                paramsMap.put("photo_am", photo_am);
                paramsMap.put("drive_img", drive_img);
                paramsMap.put("run_img", run_img);
                paramsMap.put("userid", UserBean().getId());
                return paramsMap;
            case Constant.addCarInfo:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("car_model", carModel);
                paramsMap.put("car_no", carNo);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.certificationDriver:
                step = 1;
                showLayout(step);
                break;
            case Constant.addCarInfo:
                startActivityFinish(AddBankActivity.class);
                break;
            case Constant.selectCarModel:
                type= GsonUtils.getPersons(data, ListModelBean.class);
                adapter.setNewData(type);
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }

    @Override
    public void FileOperationSuccess(Object data, int tag) {
        showComplete();
        progressBar.setVisibility(View.GONE);
        switch (uploadFileType) {
            case R.id.idCard1:
                idcard_front = (String) data;
                break;
            case R.id.idCard2:
                idcard_back = (String) data;
                break;
            case R.id.portrait:
                photo_am = (String) data;
                break;
            case R.id.drivingLicense1:
                drive_img = (String) data;
                break;
            case R.id.drivingLicense2:
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

}