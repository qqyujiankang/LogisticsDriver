package com.cn.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.cn.android.ui.activity.ui.ImageActivity;
import com.cn.android.ui.adapter.PicAdapter;
import com.cn.android.ui.dialog.MessageDialog;
import com.cn.android.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.image.ImageLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
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
public final class LoadingGoodsActivity extends MyActivity implements PublicInterfaceView, FileOperationView, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.jian)
    ImageView jian;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.jia)
    ImageView jia;
    @BindView(R.id.btn_ok)
    AppCompatButton btnOk;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private PicAdapter adapter;
    private ArrayList<String> files = new ArrayList<>();
    private PublicInterfaceePresenetr presenetr;
    private FileOperationPresenetr filePresenetr;
    private int number = 1;
    private String orderid = "";
    private String imgUrls = "";

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading_goods;
    }

    @Override
    protected void initView() {
        orderid = getIntent().getStringExtra("orderid");
        presenetr = new PublicInterfaceePresenetr(this);
        filePresenetr = new FileOperationPresenetr(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new PicAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        files.add(new String());
        adapter.setNewData(files);
    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.jian, R.id.jia, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jian:
                if (number == 1) {
                    Toast.makeText(LoadingGoodsActivity.this, "数量不能小于1", Toast.LENGTH_SHORT).show();
                    return;
                }
                number--;
                num.setText(number + "");
                break;
            case R.id.jia:
                number++;
                num.setText(number + "");
                break;
            case R.id.btn_ok:
                presenetr.getPostTokenRequest(getActivity(), ServerUrl.addLoadingImgs, Constant.addLoadingImgs);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (files.size() != position + 1) {
            ImageActivity.start(this, files, position);
        } else {
            PhotoActivity.start(getActivity(), 9, new PhotoActivity.OnPhotoSelectListener() {
                @Override
                public void onSelect(List<String> data) {
                    files.addAll(0, data);
                    adapter.setNewData(files);
                    showLoading();
                    progressBar.setVisibility(View.VISIBLE);
                    filePresenetr.uploadSingleFileRequest(LoadingGoodsActivity.this, "file", new File(data.get(0)), ServerUrl.upload, Constant.upload);
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.addLoadingImgs:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("id", orderid);
                paramsMap.put("img_urls", imgUrls = imgUrls.substring(0, imgUrls.length() - 1));
                paramsMap.put("num", number);
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.addLoadingImgs:
                this.finish();
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
        imgUrls += data + ",";
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