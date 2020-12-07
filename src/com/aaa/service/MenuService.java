package com.aaa.service;

import com.aaa.entity.Menu;

import java.util.List;

/** 
 * 菜单业务
**/ 
public interface MenuService {
    /**
     * 根据角色ID 动态查询菜单
     * @param roleId
     * @return
     */
    List<Menu> getMenuList(Integer roleId) throws Exception;
}
