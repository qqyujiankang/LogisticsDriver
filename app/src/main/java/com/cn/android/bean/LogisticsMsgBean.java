package com.cn.android.bean;

public class LogisticsMsgBean {
    private String logisticsImg;
    private String logisticsState;
    private String logisticsMsg1;
    private String logisticsMsg2;
    private String logisticsMsg3;
    private String longitude;//经度
    private String latitude;//纬度

    public LogisticsMsgBean(String logisticsImg, String logisticsState, String logisticsMsg1, String logisticsMsg2, String logisticsMsg3, String longitude, String latitude) {
        this.logisticsImg = logisticsImg;
        this.logisticsState = logisticsState;
        this.logisticsMsg1 = logisticsMsg1;
        this.logisticsMsg2 = logisticsMsg2;
        this.logisticsMsg3 = logisticsMsg3;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLogisticsImg() {
        return logisticsImg;
    }

    public void setLogisticsImg(String logisticsImg) {
        this.logisticsImg = logisticsImg;
    }

    public String getLogisticsState() {
        return logisticsState;
    }

    public void setLogisticsState(String logisticsState) {
        this.logisticsState = logisticsState;
    }

    public String getLogisticsMsg1() {
        return logisticsMsg1;
    }

    public void setLogisticsMsg1(String logisticsMsg1) {
        this.logisticsMsg1 = logisticsMsg1;
    }

    public String getLogisticsMsg2() {
        return logisticsMsg2;
    }

    public void setLogisticsMsg2(String logisticsMsg2) {
        this.logisticsMsg2 = logisticsMsg2;
    }

    public String getLogisticsMsg3() {
        return logisticsMsg3;
    }

    public void setLogisticsMsg3(String logisticsMsg3) {
        this.logisticsMsg3 = logisticsMsg3;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
