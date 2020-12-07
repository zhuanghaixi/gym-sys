package com.aaa.entity;

import java.util.Date;

public class RechargeRecord {
    /**
     * id
     */
    private Integer id;

    /**
     * cardId
     */
    private Integer cardId;

    /**
     * 充值金额
     */
    private Double rechargeamount;

    /**
     * 充值后卡余额
     */
    private Double afteramount;

    /**
     * 充值前卡余额
     */
    private Double beforeamount;

    /**
     * 充值规则
     */
    private Integer ruleid;

    /**
     * 充值时间
     */
    private Date createdtime;

    /**
     * 操作员
     */
    private Integer staffid;

    /**
     * 备注
     */
    private String momo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Double getRechargeamount() {
        return rechargeamount;
    }

    public void setRechargeamount(Double rechargeamount) {
        this.rechargeamount = rechargeamount;
    }

    public Double getAfteramount() {
        return afteramount;
    }

    public void setAfteramount(Double afteramount) {
        this.afteramount = afteramount;
    }

    public Double getBeforeamount() {
        return beforeamount;
    }

    public void setBeforeamount(Double beforeamount) {
        this.beforeamount = beforeamount;
    }

    public Integer getRuleid() {
        return ruleid;
    }

    public void setRuleid(Integer ruleid) {
        this.ruleid = ruleid;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Integer getStaffid() {
        return staffid;
    }

    public void setStaffid(Integer staffid) {
        this.staffid = staffid;
    }

    public String getMomo() {
        return momo;
    }

    public void setMomo(String momo) {
        this.momo = momo;
    }
}
