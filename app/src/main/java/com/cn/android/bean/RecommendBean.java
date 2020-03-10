package com.cn.android.bean;

public class RecommendBean {
    private String addressTitle1;
    private String addressContent1;
    private String addressTitle2;
    private String addressContent2;
    private String addressMsg;

    public RecommendBean(String addressTitle1, String addressContent1, String addressTitle2, String addressContent2, String addressMsg) {
        this.addressTitle1 = addressTitle1;
        this.addressContent1 = addressContent1;
        this.addressTitle2 = addressTitle2;
        this.addressContent2 = addressContent2;
        this.addressMsg = addressMsg;
    }

    public String getAddressTitle1() {
        return addressTitle1;
    }

    public void setAddressTitle1(String addressTitle1) {
        this.addressTitle1 = addressTitle1;
    }

    public String getAddressContent1() {
        return addressContent1;
    }

    public void setAddressContent1(String addressContent1) {
        this.addressContent1 = addressContent1;
    }

    public String getAddressTitle2() {
        return addressTitle2;
    }

    public void setAddressTitle2(String addressTitle2) {
        this.addressTitle2 = addressTitle2;
    }

    public String getAddressContent2() {
        return addressContent2;
    }

    public void setAddressContent2(String addressContent2) {
        this.addressContent2 = addressContent2;
    }

    public String getAddressMsg() {
        return addressMsg;
    }

    public void setAddressMsg(String addressMsg) {
        this.addressMsg = addressMsg;
    }
}
