package com.aaa.entity;

import java.util.Date;

public class Card {
    /**
     * id
     */
    private Integer id;
    /**
     * 会员卡id
     */
    private Integer cardId;
    /**
     * 会员id
     */
    private Integer userId;
    /**
     * 会员姓名
     */
    private String userName;
    /**
     * 会员卡内金额
     */
    private Double amount;
    /**
     * 会员卡积分
     */
    private Integer credit;
    /**
     * 会员卡状态
     */
    private Integer status;
    private Integer level;

    private String levelName;
    private Date createdTime;


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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardId=" + cardId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", amount=" + amount +
                ", credit=" + credit +
                ", status=" + status +
                ", level=" + level +
                ", levelName='" + levelName + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
