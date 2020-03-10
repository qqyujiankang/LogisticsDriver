package com.cn.android.bean;

import android.util.Log;

public class updateTextEvent {
    private int type;

    private String msg;

    public updateTextEvent(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
