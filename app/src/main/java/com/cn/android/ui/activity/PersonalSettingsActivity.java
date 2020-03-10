package com.cn.android.ui.activity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cn.android.R;
import com.cn.android.bean.AppUserBean;
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

public class PersonalSettingsActivity extends MyActivity implements PublicInterfaceView, FileOperationView {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.select_head_img)
    LinearLayout selectHeadImg;
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.account_security)
    TextView accountSecurity;
    @BindView(R.id.sign_out)
    TextView signOut;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private PublicInterfaceePresenetr presenetr;
    private FileOperationPresenetr filePresenetr;
    private String head_img = "";

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_settings;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);
        filePresenetr = new FileOperationPresenetr(this);
    }

    @Override
    protected void initData() {
        ImageLoader.with(getActivity()).load(UserBean().getHeadImg()).circle()
                .placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.ic_head_placeholder))
                .error(ContextCompat.getDrawable(getActivity(), R.drawable.ic_head_placeholder))
                .into(headImg);
    }

    @OnClick({R.id.select_head_img, R.id.head_img, R.id.account_security,R.id.logout, R.id.sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_head_img:
                PhotoActivity.start(this, new PhotoActivity.OnPhotoSelectListener() {
                    @Override
                    public void onSelect(List<String> data) {
                        ImageLoader.with(getActivity()).load(data.get(0)).circle()
                                .placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.ic_head_placeholder))
                                .error(ContextCompat.getDrawable(getActivity(), R.drawable.ic_head_placeholder))
                                .into(headImg);
                        showLoading();
                        progressBar.setVisibility(View.VISIBLE);
                        filePresenetr.uploadSingleFileRequest(PersonalSettingsActivity.this, "file", new File(data.get(0)), ServerUrl.upload, Constant.upload);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.head_img:
                ImageActivity.start(this,UserBean().getHeadImg());
                break;
            case R.id.account_security:
                startActivity(AccountSecurityActivity.class);
                break;
            case R.id.logout:
                new MessageDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确认注销当前账户吗?")
                        .setListener(new MessageDialog.OnListener() {
                            @Override
                            public void onConfirm(BaseDialog dialog) {
                                showLoading();
                                presenetr.getPostTokenRequest(PersonalSettingsActivity.this,ServerUrl.exit,Constant.exit);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {

                            }
                        }).show();
                break;
            case R.id.sign_out:
                new MessageDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确认退出应用?")
                        .setListener(new MessageDialog.OnListener() {
                            @Override
                            public void onConfirm(BaseDialog dialog) {
                                ActivityStackManager.getInstance().finishAllActivities();
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {

                            }
                        }).show();
                break;
        }
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> paramsMap = new HashMap<>();
        switch (tag) {
            case Constant.updateHeadImg:
                paramsMap.put("userid", UserBean().getId());
                paramsMap.put("head_img", head_img);
                return paramsMap;
            case Constant.exit:
                paramsMap.put("userid", UserBean().getId());
                return paramsMap;
        }
        return null;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        switch (tag) {
            case Constant.updateHeadImg:
                AppUserBean userBean=UserBean();
                userBean.setHeadImg(head_img);
                SaveUserBean(userBean);
                break;
            case Constant.exit:
                SPUtils.removeAll();
                startActivity(LoginActivity.class);
                ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
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
        head_img = (String) data;
        showLoading();
        presenetr.getPostTokenRequest(PersonalSettingsActivity.this, ServerUrl.updateHeadImg, Constant.updateHeadImg);
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
