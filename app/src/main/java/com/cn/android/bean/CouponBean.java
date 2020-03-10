package com.cn.android.bean;

public class CouponBean {
    /**
     * id : 4
     * type : 1
     * money : 14
     * etime : 2019-09-08
     * ctime : 2019-09-08 12:12:12
     * isUse : 3
     * status : 1
     * userid : 89B903A82F21B1DEDCB31973C46856DB
     * useMoney : 55
     * content : 是可见的富士康附件是雷锋精神疗法就开上了飞机的数量发
     */

    private String id;
    private int type;
    private double money;
    private String etime;
    private String ctime;
    private int isUse;
    private int status;
    private String userid;
    private double useMoney;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public double getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(double useMoney) {
        this.useMoney = useMoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
