package com.cn.android.network;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fjw on 2018/1/19.
 */

public class GetJsonDate {
    private int code;
    private String data;
    private int total;

    public int getJsonCode(String jsonData) {
        try {
            JSONObject obj = new JSONObject(jsonData);
            code = obj.optInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }
    public String getJsonData(String jsonData) {
        try {
            JSONObject obj = new JSONObject(jsonData);
            data = obj.optString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    public String getJsonMsg(String jsonData) {
        try {
            JSONObject obj = new JSONObject(jsonData);
            data = obj.optString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    public String getJsonRows(String jsonData) {
        try {
            JSONObject rows = new JSONObject(jsonData);
            data=rows.getString("rows");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    public int getJsonTotal(String jsonData) {
        try {
            JSONObject rows = new JSONObject(jsonData);
            total=rows.getInt("total");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return total;
    }
    public String getJsonRes(String jsonData) {
        try {
            JSONObject obj = new JSONObject(jsonData);
            data=obj.optString("res");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    public String getJsonVehicle(String jsonData) {
        try {
            JSONObject obj = new JSONObject(jsonData);
            data=obj.optString("idletruck");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    public String getJsonDriver(String jsonData) {
        try {
            JSONObject obj = new JSONObject(jsonData);
            data=obj.optString("alldriver");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}
