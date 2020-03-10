package com.cn.android.bean;

public class AppUserBean {
    private String id;

    /**
     * 1司机端 2客户端
     *
     * @mbg.generated
     */
    private int type;

    /**
     * 上级id   空为主账号 否则为辅助账号
     *
     * @mbg.generated
     */
    private String pid;

    /**
     * 是否VIP账号登陆  1是 2否
     *
     * @mbg.generated
     */
    private int isVip;

    /**
     * 手机号
     *
     * @mbg.generated
     */
    private String userphone;

    /**
     * 密码
     *
     * @mbg.generated
     */
    private String password;

    /**
     * 身份证正面
     *
     * @mbg.generated
     */
    private String idcardFront;

    /**
     * 身份证反面
     *
     * @mbg.generated
     */
    private String idcardBack;

    /**
     * 本人照片
     *
     * @mbg.generated
     */
    private String photoAm;

    /**
     * 驾驶证照片
     *
     * @mbg.generated
     */
    private String driveImg;

    /**
     * 行驶证照片
     *
     * @mbg.generated
     */
    private String runImg;

    /**
     * 头像
     *
     * @mbg.generated
     */
    private String headImg;

    /**
     * 昵称
     *
     * @mbg.generated
     */
    private String nickname;

    /**
     * 余额
     *
     * @mbg.generated
     */
    private String umoney;

    /**
     * 会员等级 0普通用户 1月租式会员 2VIP会员
     *
     * @mbg.generated
     */
    private int garde;

    /**
     * 会员开始时间
     *
     * @mbg.generated
     */
    private String stime;

    /**
     * 会员结束时间
     *
     * @mbg.generated
     */
    private String etime;

    /**
     * 注册时间
     *
     * @mbg.generated
     */
    private String ctime;

    /**
     * 1有效 2无效
     *
     * @mbg.generated
     */
    private int status;

    /**
     * 0待审核 1通过 2不通过
     *
     * @mbg.generated
     */
    private int isReal;

    /**
     * 注册步骤 0为注册 1为司机身份认证 2为车型选择 3为添加银行卡 4为选择线路
     *
     * @mbg.generated
     */
    private int isRegister;

    /**
     * 微信号
     *
     * @mbg.generated
     */
    private String wechatno;

    /**
     * 姓名
     *
     * @mbg.generated
     */
    private String realname;

    /**
     * 设备id
     *
     * @mbg.generated
     */
    private String deviceid;

    /**
     * 判断微信登陆的openid
     *
     * @mbg.generated
     */
    private String wechatOpenid;

    /**
     * 判断qq登陆的openid
     *
     * @mbg.generated
     */
    private String qqOpenid;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return null==id?"":id;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdcardFront() {
        return idcardFront;
    }

    public void setIdcardFront(String idcardFront) {
        this.idcardFront = idcardFront;
    }

    public String getIdcardBack() {
        return idcardBack;
    }

    public void setIdcardBack(String idcardBack) {
        this.idcardBack = idcardBack;
    }

    public String getPhotoAm() {
        return photoAm;
    }

    public void setPhotoAm(String photoAm) {
        this.photoAm = photoAm;
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

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUmoney() {
        return umoney;
    }

    public void setUmoney(String umoney) {
        this.umoney = umoney;
    }

    public int getGarde() {
        return garde;
    }

    public void setGarde(int garde) {
        this.garde = garde;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsReal() {
        return isReal;
    }

    public void setIsReal(int isReal) {
        this.isReal = isReal;
    }

    public int getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(int isRegister) {
        this.isRegister = isRegister;
    }

    public String getWechatno() {
        return wechatno;
    }

    public void setWechatno(String wechatno) {
        this.wechatno = wechatno;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getWechatOpenid() {
        return wechatOpenid;
    }

    public void setWechatOpenid(String wechatOpenid) {
        this.wechatOpenid = wechatOpenid;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }
}
