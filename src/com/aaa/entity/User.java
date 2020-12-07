package com.aaa.entity;

import com.aaa.util.DateUtils;

import java.util.Date;

public class User {
    /**
     * id
     */
    private Integer id;

    /**
     * 会员id
     */
    private Integer userId;

    /**
     * 会员卡id
     */
    private Integer cardId;

    /**
     * 会员姓名
     */
    private String userName;
    /**
     * 会员手机号
     */
    private String phone;

    /**
     * 会员状态(1.启用2.未启用)
     */
    private Integer status;
    /**
     * 会员身份证号码
     */
    private String idCard;

    /**
     * 会员出生日期
     */
    private String birthday;

    /**
     * 会员性别
     */
    private Integer sex;

    /**
     * 会员地址
     */
    private String area;;

    /**
     * 会员详细地址
     */
    private String address;

    /**
     * 创建时间
     */
    private  String createdTime;

    /**
     * 备注
     */
    private  String momo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = DateUtils.toFormat(createdTime);
    }

    public String getMomo() {
        return momo;
    }

    public void setMomo(String momo) {
        this.momo = momo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId=" + userId +
                ", cardId=" + cardId +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", idCard='" + idCard + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex=" + sex +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", momo='" + momo + '\'' +
                '}';
    }
}
