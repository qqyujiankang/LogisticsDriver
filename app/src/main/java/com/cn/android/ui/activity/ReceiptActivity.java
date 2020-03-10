package com.cn.android.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cn.android.R;
import com.cn.android.common.MyActivity;
import com.cn.android.network.Constant;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.FileOperationPresenetr;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.FileOperationView;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.cn.android.ui.activity.ui.ImageActivity;
import com.cn.android.ui.adapter.TakePictureAdapter;
import com.hjq.bar.TitleBar;

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
 * desc   : 可进行拷贝的副本
 */
public final class ReceiptActivity extends MyActivity implements
        PublicInterfaceView, FileOperationView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.take_picture1)
    LinearLayout takePicture1;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.take_picture2)
    LinearLayout takePicture2;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String orderid = "";
    private PublicInterfaceePresenetr presenetr;
    private FileOperationPresenetr filePresenetr;
    private TakePictureAdapter adapter1, adapter2;
    private ArrayList<String> files1=new ArrayList<>();
    private ArrayList<String> files2=new ArrayList<>();
    private String receiptfile = "";
    private String invoicefile = "";
    private int viewId;

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receipt;
    }

    @Override
    protected void initView() {
        orderid = getIntent().getStringExtra("orderid");
        presenetr = new PublicInterfaceePresenetr(this);
        filePresenetr = new FileOperationPresenetr(this);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        adapter1 = new TakePictureAdapter(this);
        adapter2 = new TakePictureAdapter(this);
        adapter1.setOnItemClickListener(this);
        adapter2.setOnItemClickListener(this);
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.take_picture1, R.id.take_picture2, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.take_picture1:
                takePic(R.id.take_picture1);
                break;
            case R.id.take_picture2:
                takePic(R.id.take_picture2);
                break;
            case R.id.btn_commit:
                showLoading();
                presenetr.getPostTokenRequest(getActivity(), ServerUrl.addReceiptfileImgs, Constant.addReceiptfileImgs);
                break;
        }
    }

    private void takePic(int view) {
        viewId=view;
        PhotoActivity.start(getActivity(), 1, new PhotoActivity.OnPhotoSelectListener() {
            @Override
            public void onSelect(List<String> data) {
                switch (view) {
                    case  R.id.take_picture1:
                        files1.addAll(data);
                        adapter1.replaceData(files1);
                        break;
                    case  R.id.take_picture2:
                        files2.addAll(data);
                        adapter2.replaceData(files2);
                        break;
                }
                showLoading();
                progressBar.setVisibility(View.VISIBLE);
                filePresenetr.uploadSingleFileRequest(ReceiptActivity.this, "file", new File(data.get(0)), ServerUrl.upload, Constant.upload);
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
        switch (viewId) {
            case  R.id.take_picture1:
                receiptfile += data + ",";
                break;
            case  R.id.take_picture2:
                invoicefile += data + ",";
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
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.addReceiptfileImgs:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("id", orderid);
                paramsMap.put("receiptfile", receiptfile = receiptfile.substring(0, receiptfile.length() - 1));
                paramsMap.put("invoicefile", invoicefile = invoicefile.substring(0, invoicefile.length() - 1));
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.addReceiptfileImgs:
                this.finish();
                break;
        }
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(adapter==adapter1) {
            ImageActivity.start(this, files1, position);
        }else if(adapter==adapter2){
            ImageActivity.start(this, files2, position);
        }
    }
}