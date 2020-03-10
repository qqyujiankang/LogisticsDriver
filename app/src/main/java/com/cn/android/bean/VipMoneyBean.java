package com.cn.android.bean;

public class VipMoneyBean {

    /**
     * id : 1
     * monthVipMoney : 29.99
     * yearVipMoney : 112.22
     * status : 1
     */

    private String id;
    private double monthVipMoney;
    private double yearVipMoney;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMonthVipMoney() {
        return monthVipMoney;
    }

    public void setMonthVipMoney(double monthVipMoney) {
        this.monthVipMoney = monthVipMoney;
    }

    public double getYearVipMoney() {
        return yearVipMoney;
    }

    public void setYearVipMoney(double yearVipMoney) {
        this.yearVipMoney = yearVipMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
