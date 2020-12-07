package com.aaa.service;

import com.aaa.entity.Role;
import com.aaa.entity.TreeMenu;

import java.util.List;
import java.util.Map;

/**
 * 角色
**/
public interface RoleService {
    /**
     * 获取所有的角色
     * @return
     */
    List<Role> getAllRole();
    /**
     * 获取所有的角色信息(列表)
     * @return
     */
    Map<String,Object> getAllRoleInfo(Integer pageNumber, Integer pageSize, String searchName) throws Exception;

    /**
     * 角色修改
     * @param id
     * @param roleName
     * @param description
     * @param status
     * @return
     * @throws Exception
     */
    int updateOrAddRole(Integer id, String roleName, String description, Integer status) throws Exception;


    /**
     * 删除 但是不是物理删除
     * @param id
     * @return
     */
    int delRole(Integer id, Integer status) throws Exception;

    /**
     * 根据角色获取整个权限树
     * @return
     */
    List<TreeMenu> getMenuList(int roleId) throws Exception;

    /**
     * 修改指定角色的权限
     * @param roleId
     * @param resources
     * @return
     */
    int updateMenuList(Integer roleId, Integer[] resources) throws Exception;
}
