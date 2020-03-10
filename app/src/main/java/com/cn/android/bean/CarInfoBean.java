package com.cn.android.bean;

public class CarInfoBean {

    /**
     * id : 2D7C6E929C01562C712E82FC94DD5B79
     * userid : 89B903A82F21B1DEDCB31973C46856DB
     * phone : 15339256798
     * driveImg : http://192.168.0.110:8010/upload/a16abe8a07be416398201d8ce9486086.png
     * runImg : http://192.168.0.110:8010/upload/ed7598015bc24be3afbd781603bc5e48.jpg
     * carModel : 箱车
     * carNo : 陕A605P6
     * ctime : 2019-12-27 14:56:01
     * status : 1
     */

    private String id;
    private String userid;
    private String phone;
    private String driveImg;
    private String runImg;
    private String carModel;
    private String carNo;
    private String ctime;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDriveImg() {
        return driveImg;
    }

    public void setDriveImg(String driveImg) {
        this.driveImg = driveImg;
    }

    public String getRunImg() {
        return runImg;
    }

    public void setRunImg(String runImg) {
        this.runImg = runImg;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
