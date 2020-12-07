package com.aaa.entity;

/**
 * 角色
**/
public class Role {
    /**
     * id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色等级
     */
    private Integer grade;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色状态(0启用 1禁用)
     */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
