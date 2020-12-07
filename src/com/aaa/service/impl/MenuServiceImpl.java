package com.aaa.service.impl;

import com.aaa.dao.MenuDao;
import com.aaa.dao.impl.MenuDaoImpl;
import com.aaa.entity.Menu;
import com.aaa.service.MenuService;
import com.aaa.util.BusinessException;

import java.util.List;

public class MenuServiceImpl implements MenuService {
    private MenuDao menuDao = new MenuDaoImpl();
    @Override
    public List<Menu> getMenuList(Integer roleId) throws Exception {
        if (roleId == null  || roleId == 0){
            throw new BusinessException("角色Id不能为空");
        }
        return menuDao.getMenuList(roleId);
    }
}
