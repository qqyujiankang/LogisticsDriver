package com.cn.android.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cn.android.R;
import com.cn.android.bean.updateTextEvent;
import com.cn.android.common.MyActivity;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.network.Constant;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.FileOperationPresenetr;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.FileOperationView;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.image.ImageLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public final class WaitingTimeActivity extends MyActivity implements
        PublicInterfaceView, FileOperationView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.take_picture1)
    LinearLayout takePicture1;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_wait_time)
    TextView tvWaitTime;
    @BindView(R.id.img_show)
    ImageView imgShow;
    private String orderid = "";
    private int state = 0;
    private PublicInterfaceePresenetr presenetr;
    private FileOperationPresenetr filePresenetr;
    private String img_url;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_waiting_time;
    }

    @Override
    protected void initView() {
        orderid = getIntent().getStringExtra("orderid");
        state = getIntent().getIntExtra("state", 0);
        presenetr = new PublicInterfaceePresenetr(this);
        filePresenetr = new FileOperationPresenetr(this);
    }

    @Override
    protected void initData() {
        switch (state) {
            case 0:
                tvWaitTime.setText("开始点到达照片");
                btnCommit.setText("申请待时(上传开始照片)");
                break;
            case 1:
                tvWaitTime.setText("结束点到达照片");
                btnCommit.setText("申请待时(上传结束照片)");
                break;
        }
    }

    @OnClick({R.id.take_picture1, R.id.img_show, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.take_picture1:
            case R.id.img_show:
                takePic();
                break;
            case R.id.btn_commit:
                showLoading();
                switch (state) {
                    case 0:
                        presenetr.getPostTokenRequest(getActivity(), ServerUrl.addStayInStart, Constant.addStayIn);
                        break;
                    case 1:
                        presenetr.getPostTokenRequest(getActivity(), ServerUrl.addStayInEnd, Constant.addStayIn);
                        break;
                }
                break;
        }
    }

    private void takePic() {
        PhotoActivity.start(getActivity(), 1, new PhotoActivity.OnPhotoSelectListener() {
            @Override
            public void onSelect(List<String> data) {
                showLoading();
                takePicture1.setVisibility(View.GONE);
                imgShow.setVisibility(View.VISIBLE);
                ImageLoader.with(WaitingTimeActivity.this).load(data.get(0)).into(imgShow);
                progressBar.setVisibility(View.VISIBLE);
                filePresenetr.uploadSingleFileRequest(WaitingTimeActivity.this, "file", new File(data.get(0)), ServerUrl.upload, Constant.upload);
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
        img_url = (String) data;
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
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.addStayIn:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("id", orderid);
                paramsMap.put("img_url", img_url);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.addStayIn:
                this.finish();
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }

}