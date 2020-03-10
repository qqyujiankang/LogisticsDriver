package com.cn.android.bean;

public class ListModelBean {

    /**
     * id : 1
     * name : 飞翼
     * ctime : 2019-12-26 12:12:12
     * status : 1
     */

    private String id;
    private String name;
    private String ctime;
    private int status;
    private boolean select;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
