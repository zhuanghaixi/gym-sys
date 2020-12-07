package com.aaa.entity;

import java.util.List;


public class RoleRights {
    private int id;
    private String roleName;
    private int grade;
    private String description;
    private int status;

    private List<Menu> menuList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "RoleRights{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", grade=" + grade +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", menuList=" + menuList +
                '}';
    }
}
