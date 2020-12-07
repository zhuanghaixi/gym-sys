package com.aaa.dao.impl;

import com.aaa.dao.MenuDao;
import com.aaa.entity.Menu;
import com.aaa.util.BaseDao;
import org.junit.Test;

import java.util.List;


public class MenuDaoImpl implements MenuDao {
    private BaseDao baseDao = new BaseDao();

    @Override
    public List<Menu> getMenuList(Integer roleId) {
        //查询一级菜单
        String sql = "select " +
                "r.id id,r.resource_name resourceName,r.url url,r.icon icon,r.pid pid,r.sort sort,r.identity identity" +
                      " from " +
                "resource_role rr,resource r " +
                      " where " +
                "rr.resource_id = r.id and rr.role_id = ? and r.pid = 0 and r.status = 1 ORDER BY r.sort asc";
        Object[] params = {roleId};
        List<Menu> lists = baseDao.queryList(sql,params,Menu.class);
        for (int i = 0; i < lists.size(); i++) {
            Menu row = lists.get(i);
            //查询二级菜单
            String sql1 = "select " +
                    "r.id id,r.resource_name resourceName,r.url url,r.icon icon,r.pid pid,r.sort sort,r.identity identity" +
                    " from " +
                    "resource_role rr,resource r " +
                    " where " +
                    "rr.resource_id = r.id and rr.role_id = ? and r.pid = ? ORDER BY r.sort asc";
            Object[] params1 = {roleId,row.getId()};
            List<Menu> lists1 = baseDao.queryList(sql1,params1,Menu.class);
            row.setMenuList(lists1);
        }
        return lists;
    }
    @Test
    public void test(){
        getMenuList(1);
    }
}
