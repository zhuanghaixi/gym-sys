package com.aaa.dao;

import com.aaa.entity.Role;
import com.aaa.entity.TreeMenu;

import java.util.List;

/**
 * 角色
 **/
public interface RoleDao {
    /**
     * 获取所有的角色
     * @return
     */
    List<Role> getAllRole();

    /**
     * 获取所有的角色信息(列表)
     * @return
     */
    List<Role> getAllRoleInfo(Integer pageNumber, Integer pageSize, String searchName);

    /**
     * 获取所有的角色信息条数
     * @return
     */
    int getAllRoleInfoCount(String searchName);

    /**
     * 角色修改
     * @param role
     * @return
     */
    int updateRole(Role role);

    /**
     * 增加角色
     * @param role
     * @return
     */
    int addRole(Role role);

    /**
     * 删除 但是不是物理删除
     * @param id
     * @return
     */
    int delRole(Integer id, Integer status);

    /**
     * 获取整个权限树
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
