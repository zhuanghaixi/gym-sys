package com.aaa.dao.impl;

import com.aaa.dao.CategoryDao;
import com.aaa.entity.Category;


import com.aaa.util.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;


public class CategoryDaoImpl implements CategoryDao {
    private BaseDao baseDao = new BaseDao();


    @Override
    public List<Category> getAllCategoryInfo(Integer pageNumber, Integer pageSize, String searchcategoryId, String searchName) {
        String sql = "select * from category where 1=1 ";
        if (StringUtils.isNotBlank(searchcategoryId)) {
            sql += " and categoryId = " + searchcategoryId;
        }
        if (StringUtils.isNotBlank(searchName)) {
            searchName = "%" + searchName + "%";
            sql += " and name like '" + searchName +  "'";
        }
        sql += " limit ?,?";
        Object[] params = {pageNumber, pageSize};
        List<Category> category = baseDao.queryList(sql, params, Category.class);
        return category;
    }

    @Override
    public int getAllCategoryInfoCount(String searchcategoryId, String searchName) {
        String sql = "select count(1) len from category where 1=1";
        if (StringUtils.isNotBlank(searchcategoryId)) {
            sql += " and categoryId= " + searchcategoryId;
        }
        if (StringUtils.isNotBlank(searchName)) {
            searchName = "%" + searchName + "%";
            sql += " and name like '" + searchName + "'";
        }
        List<Map<String, Object>> maps = baseDao.executeQuery(sql, null);
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = maps.get(0);
            Integer res = Integer.parseInt(map.get("len") + "");
            return res;
        }
        return 0;
    }

    @Override
    public int updateCategory(Category category) {
        String sql = "update category set name = ?,momo = ?,status = ? where categoryId = ?";
        Object[] params = {category.getName() ,category.getMomo(),category.getStatus(),category.getCategoryId()};
        return baseDao.executeUpdate(sql, params);
    }

    @Override
    public int delCategory(Integer categoryId, Integer status) {
        status = status == 1 ? 0 : 1;
        String sql1 = "update category set status = ? where categoryId = " + categoryId;
        Object[] params1 = {status};
        String sql2 = "update goods set status = ? where categoryId = " + categoryId;
        Object[] params2 = {status};
        int len = baseDao.executeUpdate(sql1, params1)+ baseDao.executeUpdate(sql2, params2);;
        return len;
    }


    @Override
    public String getLastCategoryId() {
        String sql = "select categoryId from category order by id desc limit 1";
        List<Map<String, Object>> maps = baseDao.executeQuery(sql, null);
        if (maps != null && maps.size() > 0) {
            String categoryId = maps.get(0).get("categoryId") + "";
            return categoryId;
        }
        return null;
    }
    @Test
    public void test() {
        System.out.println(getAllCategoryInfo(0, 10, "", ""));
    }
}
