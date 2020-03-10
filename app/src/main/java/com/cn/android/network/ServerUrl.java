package com.cn.android.network;

import com.cn.android.utils.SPUtils;

/**
 * Created by PC-122 on 2017/12/21.
 */
public class ServerUrl {

    public static String defaultIp="http://192.168.0.111:8010";


    public static String LOGIN_API = defaultIp+"/app/login/";
    public static String USER_API = defaultIp+"/app/user/";
    public static String ORDER_API = defaultIp+"/app/order/";
    public static String PAY_API = defaultIp+"/pay/";

    /**登录*/
    public static String login=LOGIN_API+"login";
    /**退出*/
    public static String exit=LOGIN_API+"exit";
    /**发送短信*/
    public static String sendSms=LOGIN_API+"sendSms";
    /**注册*/
    public static String registerDriver=USER_API+"registerDriver";
    /**司机身份认证*/
    public static String certificationDriver=USER_API+"certificationDriver";
    /**车型选择*/
    public static String addCarInfo=USER_API+"addCarInfo";
    /**新银行选择列表*/
    public static String selectBankType=USER_API+"selectBankType";
    /**添加银行卡*/
    public static String addBankInfo=USER_API+"addBankInfo";
    /**线路选择  提交*/
    public static String addLineInfo=USER_API+"addLineInfo";
    /**上传图片*/
    public static String upload=USER_API+"upload";
    /**修改手机号*/
    public static String updatePhone=USER_API+"updatePhone";
    /**修改头像*/
    public static String updateHeadImg=USER_API+"updateHeadImg";
    /**修改昵称???哪里修改昵称*/
    public static String updateNickname=USER_API+"updateNickname";
    /**修改密码*/
    public static String updatePassword=USER_API+"updatePassword";
    /**忘记密码*/
    public static String forgetPassword=USER_API+"forgetPassword";
    /**查询个人信息*/
    public static String selectInfoByUserid=USER_API+"selectInfoByUserid";
    /**提现*/
    public static String withdrawalByUserid=USER_API+"withdrawalByUserid";
    /**银行卡列表*/
    public static String selectBankList=USER_API+"selectBankList";
    /**列表中的添加银行卡*/
    public static String saveBankInfo=USER_API+"saveBankInfo";
    /**车型选择列表*/
    public static String selectCarModel=USER_API+"selectCarModel";
    /**车辆信息列表*/
    public static String selectCarInfoList=USER_API+"selectCarInfoList";
    /**车辆信息列表*/
    public static String selectCityArea=USER_API+"selectCityArea";
    /**添加车辆信息*/
    public static String saveCarInfo=USER_API+"saveCarInfo";
    /**路线信息*/
    public static String selectLineInfoList=USER_API+"selectLineInfoList";
    /**修改路线信息*/
    public static String updateLineInfo=USER_API+"updateLineInfo";
    /**会员券列表*/
    public static String selectCouponList=USER_API+"selectCouponList";
    /**账单流水列表*/
    public static String selectAccountList=USER_API+"selectAccountList";
    /**消息中心*/
    public static String selectMessageType=USER_API+"selectMessageType";
    /**分类下的消息列表*/
    public static String selectMessageList=USER_API+"selectMessageList";
    /**vip会员金额*/
    public static String selectVipMoney=USER_API+"selectVipMoney";
    /**v配货大厅*/
    public static String selectOrderList=ORDER_API+"selectOrderList";
    public static String infopay=PAY_API+"infopay";
    /**订单详情===配货大厅*/
    public static String selectOrderById=ORDER_API+"selectOrderById";
    /**订单详情===我的订单*/
    public static String selectOrderDetialsById=ORDER_API+"selectOrderDetialsById";
    /**我的订单*/
    public static String selectOrderListByUserid=ORDER_API+"selectOrderListByUserid";
    public static String selectPickTime=ORDER_API+"selectPickTime";
    public static String addPickTime=ORDER_API+"addPickTime";
    public static String addLoadingImgs=ORDER_API+"addLoadingImgs";
    public static String addReceiptfileImgs=ORDER_API+"addReceiptfileImgs";
    public static String addApplyAccount=ORDER_API+"addApplyAccount";
    public static String addExpressno=ORDER_API+"addExpressno";
    public static String addStayInStart=ORDER_API+"addStayInStart";
    public static String addStayInEnd=ORDER_API+"addStayInEnd";
}
