package com.aaa.entity;

import java.util.List;

/**
 *
 * 菜单
 **/
public class Menu {
    private String id;
    private String resourceName;
    private String url;
    private String icon;
    private String pid;
    private String sort;
    private String identity;
    /**
     * 定义二级菜单
     */
    private List<Menu> menuList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", resourceName=" + resourceName + ", url=" + url + ", icon=" + icon + ", pid=" + pid
                + ", sort=" + sort + ", identity=" + identity + ", menuList=" + menuList + "]";
    }
}
