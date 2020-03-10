package com.cn.android.bean;

public class AppAccountInfo {

    private String id;

    /**
     * 支付方式：1支付宝 2微信 3余额
     *
     * @mbg.generated
     */
    private Integer payType;

    /**
     * 支付金额
     *
     * @mbg.generated
     */
    private double payMoney;

    /**
     * 类别:1议价 2提现 3单票查看订单详情 4接单付定金 5购买vip
     *
     * @mbg.generated
     */
    private Integer type;

    /**
     * 支付流水号
     *
     * @mbg.generated
     */
    private String shopOrder;

    /**
     * 订单id
     *
     * @mbg.generated
     */
    private String orderid;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private String userid;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private String ctime;

    /**
     * 0提现审核中 1有效（提现通过） 2无效  3提现不通过
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     * 提现银行账号
     *
     * @mbg.generated
     */
    private String accountNo;

    /**
     * 提现银行名称
     *
     * @mbg.generated
     */
    private String accountName;

    /**
     * 备注
     *
     * @mbg.generated
     */
    private String reamrk;

    /**
     * 议价时输入的议价金额
     *
     * @mbg.generated
     */
    private double bargainprice;

    /**
     * 接单时：是否有物流责任险  1是 2否
     *
     * @mbg.generated
     */
    private Integer isDuty;

    /**
     * 接单时：是否有放空险  1是 2否
     *
     * @mbg.generated
     */
    private Integer isEmpty;

    /**
     * 接单时：是否有待时险  1是 2否
     *
     * @mbg.generated
     */
    private Integer isTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getShopOrder() {
        return shopOrder;
    }

    public void setShopOrder(String shopOrder) {
        this.shopOrder = shopOrder;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getReamrk() {
        return reamrk;
    }

    public void setReamrk(String reamrk) {
        this.reamrk = reamrk;
    }

    public double getBargainprice() {
        return bargainprice;
    }

    public void setBargainprice(double bargainprice) {
        this.bargainprice = bargainprice;
    }

    public Integer getIsDuty() {
        return isDuty;
    }

    public void setIsDuty(Integer isDuty) {
        this.isDuty = isDuty;
    }

    public Integer getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Integer isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Integer getIsTime() {
        return isTime;
    }

    public void setIsTime(Integer isTime) {
        this.isTime = isTime;
    }

}
