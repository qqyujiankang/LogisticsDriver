package com.cn.android.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cn.android.R;
import com.cn.android.bean.LocalImageInfo;
import com.cn.android.bean.LogisticsMsgBean;
import com.cn.android.bean.RecommendBean;
import com.cn.android.common.MyLazyFragment;
import com.cn.android.ui.activity.DriveRouteActivity;
import com.cn.android.ui.activity.HomeActivity;
import com.cn.android.ui.adapter.LogisticsAdapter;
import com.cn.android.ui.adapter.RecommendAdapter;
import com.cn.android.utils.L;
import com.cn.android.utils.ScreenUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.image.ImageLoader;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目炫酷效果示例
 */
public final class FragmentLeft2 extends MyLazyFragment<HomeActivity> implements BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.banner)
    XBanner mBanner;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.logistics_information_title)
    TextView logisticsInformationTitle;
    @BindView(R.id.li_recyclerView)
    RecyclerView liRecyclerView;
    @BindView(R.id.logistics_information_layout)
    CardView logisticsInformationLayout;
    @BindView(R.id.recommend_title)
    TextView recommendTitle;
    @BindView(R.id.recommend_more)
    TextView recommendMore;
    @BindView(R.id.recommend_RecyclerView)
    RecyclerView recommendRecyclerView;
    private LogisticsAdapter logisticsAdapter;
    private RecommendAdapter recommendAdapter;
    private List<LogisticsMsgBean> logisticsMsgBeans = new ArrayList<>();
    private List<RecommendBean> recommendBeans = new ArrayList<>();

    public static FragmentLeft2 newInstance() {
        return new FragmentLeft2();
    }

    @Override
    protected int getTitleId() {
        return R.id.title_bar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_left2;
    }

    @Override
    protected void initView() {
        L.e("123", "FragmentLeft initView");
        ImmersionBar.setTitleBar(getAttachActivity(), titleBar);
        logisticsAdapter = new LogisticsAdapter(getActivity());
        liRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        liRecyclerView.setAdapter(logisticsAdapter);
        recommendAdapter = new RecommendAdapter(getActivity());
        recommendRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recommendRecyclerView.setAdapter(recommendAdapter);
        logisticsAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initData() {
        L.e("123", "FragmentLeft initData");
        List<SimpleBannerInfo> list = new ArrayList<>();
        list.add(new LocalImageInfo("http://g.hiphotos.baidu.com/image/pic/item/b17eca8065380cd78775def0ab44ad3459828147.jpg"));
        list.add(new LocalImageInfo("http://f.hiphotos.baidu.com/image/pic/item/a08b87d6277f9e2faa2048151530e924b899f392.jpg"));
        list.add(new LocalImageInfo("http://b.hiphotos.baidu.com/image/pic/item/03087bf40ad162d923621d011bdfa9ec8a13cd1b.jpg"));
        list.add(new LocalImageInfo("http://e.hiphotos.baidu.com/image/pic/item/b7fd5266d0160924d76acf06de0735fae6cd345b.jpg"));
        list.add(new LocalImageInfo("http://a.hiphotos.baidu.com/image/pic/item/c83d70cf3bc79f3d785ce62db0a1cd11728b2969.jpg"));
        list.add(new LocalImageInfo("http://f.hiphotos.baidu.com/image/pic/item/fcfaaf51f3deb48fd146bfc3fa1f3a292df578fb.jpg"));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(getActivity()) / 2);
        mBanner.setLayoutParams(layoutParams);
        mBanner.setBannerData(R.layout.item_banner_image, list);
        mBanner.setAutoPlayAble(true);
        //加载广告图片
        mBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //此处适用Fresco加载图片，可自行替换自己的图片加载框架
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                LocalImageInfo listBean = ((LocalImageInfo) model);
                ImageLoader.with(getActivity()).load(listBean.getXBannerUrl()).into(imageView);
            }
        });
        logisticsMsgBeans.add(new LogisticsMsgBean("https://c-ssl.duitang.com/uploads/item/201306/27/20130627163146_eMjUf.thumb.700_0.jpeg",
                "运输中",
                "1吨",
                "2件",
                "3方",
                "113.61378",
                "34.770148"));
        logisticsMsgBeans.add(new LogisticsMsgBean("https://c-ssl.duitang.com/uploads/item/201704/10/20170410073535_HXVfJ.thumb.700_0.jpeg",
                "已完成",
                "2吨",
                "4件",
                "5方",
                "108.941158",
                "34.311161"));
        logisticsAdapter.setNewData(logisticsMsgBeans);
        recommendBeans.add(new RecommendBean(
                "陕西省西安市未央区",
                "印象城8楼***号",
                "陕西省西安市灞桥区",
                "印象城8楼***号",
                "2吨/3件/6方"));
        recommendBeans.add(new RecommendBean(
                "陕西省西安市雁塔区",
                "印象城8楼***号",
                "陕西省西安市碑林区",
                "印象城8楼***号",
                "2吨/3件/6方"));
        recommendBeans.add(new RecommendBean(
                "陕西省西安市雁塔区",
                "印象城8楼***号",
                "陕西省西安市碑林区",
                "印象城8楼***号",
                "2吨/3件/6方"));
        recommendBeans.add(new RecommendBean(
                "陕西省西安市雁塔区",
                "印象城8楼***号",
                "陕西省西安市碑林区",
                "印象城8楼***号",
                "2吨/3件/6方"));
        recommendAdapter.setNewData(recommendBeans);
    }

    @Override
    public void onResume() {
        super.onResume();
        L.e("123", "FragmentLeft onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.e("123", "FragmentLeft onRestart");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            L.e("123", "TestFragmentA onHiddenChanged ! hidden");
        } else {
            L.e("123", "TestFragmentA onHiddenChanged hidden");
        }
    }

    @OnClick({R.id.recommend_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recommend_more:

                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if(adapter==logisticsAdapter){
            startActivity(DriveRouteActivity.class);
        }
    }
}