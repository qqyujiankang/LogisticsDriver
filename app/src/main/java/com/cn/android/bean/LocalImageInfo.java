package com.cn.android.bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

/**
 * author: xiaohaibin.
 * time: 2018/12/3
 * mail:xhb_199409@163.com
 * github:https://github.com/xiaohaibin
 * describe: 本地资源图片
 */
public class LocalImageInfo extends SimpleBannerInfo {

    private String bannerRes;

    public LocalImageInfo(String bannerRes) {
        this.bannerRes = bannerRes;
    }

    @Override
    public String getXBannerUrl() {
        return bannerRes;
    }
}
