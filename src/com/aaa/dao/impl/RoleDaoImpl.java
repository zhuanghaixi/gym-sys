package com.aaa.dao.impl;

import com.aaa.dao.RoleDao;
import com.aaa.entity.Role;
import com.aaa.entity.TreeMenu;
import com.aaa.util.BaseDao;
import com.aaa.util.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class RoleDaoImpl implements RoleDao {
    private BaseDao baseDao = new BaseDao();
    @Override
    public List<Role> getAllRole() {
        String sql = "select * from role where status = 1";
        List<Role> roleList = baseDao.queryList(sql,null,Role.class);
        return roleList;
    }

    @Override
    public List<Role> getAllRoleInfo(Integer pageNumber,Integer pageSize,String searchName) {
        String sql = "select * from role";
        if (StringUtils.isNotBlank(searchName)){
            sql += " where roleName = '" + searchName.trim() + "'";
        }
        sql += " limit ?,?";
        Object[] params = {pageNumber,pageSize};
        List<Role> roleList = baseDao.queryList(sql,params,Role.class);
        return roleList;
    }

    @Override
    public int getAllRoleInfoCount(String searchName) {
        String sql = "select count(1) a from role";
        if (StringUtils.isNotBlank(searchName)){
            sql += " where roleName like '" +searchName + "'";
        }
        List<Map<String,Object> > roleList = baseDao.executeQuery(sql,null);
        if (roleList != null && roleList.size() > 0){
            return Integer.parseInt(roleList.get(0).get("a") + "");
        }

        return 0;
    }

    @Override
    public int updateRole(Role role) {
        String sql = "update role set roleName = ?,description = ?,status = ? where id =? ";
        Object[] params = {role.getRoleName(),role.getDescription(),role.getStatus(),role.getId()};
        int len = baseDao.executeUpdate(sql,params);
        return len;
    }

    @Override
    public int addRole(Role role) {
        String sql = "insert into role (roleName,description,status) values (?,?,?)";
        Object[] params = {role.getRoleName(),role.getDescription(),role.getStatus()};
        return baseDao.executeUpdate(sql,params);
    }

    @Override
    public int delRole(Integer id,Integer status) {
        status = status == 1 ? 0 : 1;
        String sql = "update role set status = ? where id =? ";
        Object[] params = {status,id};
        int len = baseDao.executeUpdate(sql,params);
        return len;
    }

    @Override
    public List<TreeMenu> getMenuList(int roleId) throws Exception {
        /**
         * 根据role获取根节点
         */
        String sql = "select rs.id nodeid,rs.resource_name text,rs.icon icon,rs.pid pid,rs.sort sort "
                + "from resource rs "
                + "where rs.pid = 0 and rs.status = 1 order by rs.id asc";
        List<TreeMenu> list = baseDao.queryList(sql, null, TreeMenu.class);
        /**
         * 获取 resource_role 数据
         */
        String res = "SELECT resource_id rId from resource_role where role_id = " + roleId;
        List<Map<String,Object>> resList = baseDao.executeQuery(res, null);
        /**
         * 获取子节点
         */
        for (int i = 0; i < list.size(); i++) {
            TreeMenu row = list.get(i);
            /**
             * 通过roleId查询 resource_role 看是否有此数据
             * 如果有则设置为true否则为false
             */
            if (resList.size() > 0) {
                for (int j = 0; j < resList.size(); j++) {
                    if (resList.get(j) != null && resList.get(j).get("rId") != null) {
                        if (row.getNodeid() == Integer.parseInt(resList.get(j).get("rId")+"")) {
                            row.setState("is");
                        }
                    }
                }
            } else {
                row.setState("");
            }
            String sqlSub = "select rs.id nodeid,rs.resource_name text,rs.icon icon,rs.pid pid,rs.sort sort "
                    + "from resource rs "
                    + "where rs.pid = ? and rs.status = 1 order by rs.id asc";
            Object[] paramsSub = {row.getNodeid()};
            List<TreeMenu> subList = baseDao.queryList(sqlSub, paramsSub, TreeMenu.class);
            for (int j = 0; j < subList.size(); j++) {
                TreeMenu rowSubList = subList.get(j);
                if (resList.size() == 0){
                    rowSubList.setState("");
                }
                for (int k = 0; k < resList.size(); k++) {
                    if (resList.get(k) != null && resList.get(k).get("rId") != null) {
                        if (rowSubList.getNodeid() == Integer.parseInt(resList.get(k).get("rId")+"")) {
                            rowSubList.setState("is");
                        }
                    } else {
                        rowSubList.setState("");
                    }
                }
            }
            row.setTreeMenuList(subList);
        }
        return list;
    }

    @Override
    public int updateMenuList(Integer roleId, Integer[] resources) throws BusinessException {
        //参数校验
        if (roleId == null || roleId == 0) {
            throw new BusinessException("角色Id不能为空!");
        }
        /**
         * 删除操作
         * 使用逻辑删除
         */
        String sql = "delete from resource_role where role_id = ?";
        Object[] params = {roleId};
        int del = baseDao.executeUpdate(sql, params);
        if (del >= 0 && resources.length > 0) {
            String inser = "insert into resource_role (resource_id,role_id) values (?,?)";
            for (int i = 0; i < resources.length; i++) {
                Object[] inserP = {resources[i], roleId};
                baseDao.executeUpdate(inser, inserP);
            }
        }
        return del;
    }
}
