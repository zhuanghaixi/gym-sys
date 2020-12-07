package com.aaa.dao;

import com.aaa.entity.Menu;

import java.util.List;

/**

 * 菜单接口
**/ 
public interface MenuDao {

    /**
     * 根据角色ID 动态查询菜单
     * @param roleId
     * @return
     */
    List<Menu> getMenuList(Integer roleId);
}
