package com.aaa.entity;

import com.aaa.util.DateUtils;

import java.util.Date;

/**
 * 会员
 **/
public class Staff {
    /**
     * id
     */
    private Integer id;

    /**
     * 员工id
     */
    private Integer staffId;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 员工电话
     */
    private String phone;

    /**
     * 员工身份证号码
     */
    private String idCard;

    /**
     * 员工地址
     */
    private String address;

    /**
     * 入职日期
     */
    private String createdTime;

    /**
     * 员工状态(1.在职 2.离职 3.黑名单)
     */
    private Integer status;

    /**
     * roleid
     */
    private Integer roleId;
    /**
     * 角色名字
     */
    private String roleName;

    /**
     * 备注
     */
    private String momo;

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", staffId=" + staffId +
                ", staffName='" + staffName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", idCard='" + idCard + '\'' +
                ", address='" + address + '\'' +
                ", createdTime=" + createdTime +
                ", status=" + status +
                ", roleId=" + roleId +
                ", momo='" + momo + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getMomo() {
        return momo;
    }

    public void setMomo(String momo) {
        this.momo = momo;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getRoleName() {
        return roleName;
    }

}
