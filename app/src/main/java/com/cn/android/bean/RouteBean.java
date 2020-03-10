package com.cn.android.bean;

public class RouteBean {

    /**
     * scity : 西安市
     * sarea : 雁塔区
     * ecity : 上海市
     * earea : 浦东新区
     * is_back : 1
     * sort : 1
     */

    private String scity;
    private String sarea;
    private String ecity;
    private String earea;
    private int is_back=2;
    private int sort;
    private String id="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScity() {
        return scity;
    }

    public void setScity(String scity) {
        this.scity = scity;
    }

    public String getSarea() {
        return sarea;
    }

    public void setSarea(String sarea) {
        this.sarea = sarea;
    }

    public String getEcity() {
        return ecity;
    }

    public void setEcity(String ecity) {
        this.ecity = ecity;
    }

    public String getEarea() {
        return earea;
    }

    public void setEarea(String earea) {
        this.earea = earea;
    }

    public int getIs_back() {
        return is_back;
    }

    public void setIs_back(int is_back) {
        this.is_back = is_back;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
