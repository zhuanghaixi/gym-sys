package com.aaa.service.impl;

import com.aaa.dao.RoleDao;
import com.aaa.dao.impl.RoleDaoImpl;
import com.aaa.entity.Role;
import com.aaa.entity.TreeMenu;
import com.aaa.service.RoleService;
import com.aaa.util.BusinessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao = new RoleDaoImpl();
    @Override
    public List<Role> getAllRole() {
        return roleDao.getAllRole();
    }
    @Override
    public Map<String, Object> getAllRoleInfo(Integer pageNumber, Integer pageSize, String searchName) throws Exception {
        //参数校验
        if (pageNumber == 0) {
            throw new BusinessException("当前页数不能为0");
        }
        if (pageSize == 0) {
            throw new BusinessException("每页条数不能为0");
        }
        Map<String, Object> map = new HashMap<>();
        pageNumber = (pageNumber - 1) * pageSize;
        List<Role> roleList = roleDao.getAllRoleInfo(pageNumber, pageSize, searchName);
        int count = roleDao.getAllRoleInfoCount(searchName);
        map.put("list", roleList);
        map.put("count", count);
        return map;
    }

    @Override
    public int updateOrAddRole(Integer id, String roleName, String description, Integer status) throws Exception {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setDescription(description);
        role.setStatus(status);
        int len = 0;
        if (id == null || id == 0) {
            len = roleDao.addRole(role);
        } else {
            role.setId(id);
            len = roleDao.updateRole(role);
        }
        return len;
    }

    @Override
    public int delRole(Integer id, Integer status) throws Exception {
        /**
         * 先查询这个角色对应的员工还有没有人
         * 如果有的话 是不允许删除的
         * 当不允许删除的时候  提示为什么不允许删除
         */
        return roleDao.delRole(id, status);
    }

    @Override
    public List<TreeMenu> getMenuList(int roleId) throws Exception {
        return roleDao.getMenuList(roleId);
    }

    @Override
    public int updateMenuList(Integer roleId, Integer[] resources) throws Exception {
        return roleDao.updateMenuList(roleId, resources);
    }

}
