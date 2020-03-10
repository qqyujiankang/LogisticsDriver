package com.cn.android.bean;

public class MessageBean {

    /**
     * id : werwer
     * type : 1
     * userid : 89B903A82F21B1DEDCB31973C46856DB
     * title : 士大夫十分
     * content : 四点零分介绍了附件
     * week : 星期日
     * ctime : 2019-08-10 12:12:12
     * status : 1
     */

    private String id;
    private int type;
    private String userid;
    private String title;
    private String content;
    private String week;
    private String ctime;
    private int status;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
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
