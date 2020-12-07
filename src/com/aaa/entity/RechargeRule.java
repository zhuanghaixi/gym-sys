package com.aaa.entity;

/**
 * 充值规则
 **/
public class RechargeRule {
    /**
     * id
     */
    private Integer id;

    /**
     * 充值规则名字
     */
    private String name;

    /**
     * 充值系数
     */
    private Double coefficient;

    /**
     * 开始时间
     */
    private String createdTime;

    /**
     * 结束时间
     */
    private String endTime;
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
