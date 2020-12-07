package com.aaa.service.impl;

import com.aaa.dao.CategoryDao;

import com.aaa.dao.impl.CategoryDaoImpl;

import com.aaa.entity.Category;

import com.aaa.service.CategoryService;
import com.aaa.util.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public Map<String, Object> getAllCategoryInfo(Integer pageNumber, Integer pageSize, String searchcategoryId, String searchName) throws Exception {

        pageNumber = (pageNumber - 1) * pageSize;
        List<Category> list = categoryDao.getAllCategoryInfo(pageNumber, pageSize, searchcategoryId, searchName);
        int count = categoryDao.getAllCategoryInfoCount(searchcategoryId, searchName);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        return map;
    }

    @Override
    public int updateCategoryByCategoryId(Integer categoryId, String name, String momo, Integer status) throws Exception {
        int len = 0;
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName(name);
        category.setMomo(momo);
        category.setStatus(status);


        if (categoryId != null && categoryId != 0) {
            category.setCategoryId(categoryId);
            len = categoryDao.updateCategory(category);

        }
        return len;
    }

    @Override
    public int delCategory(Integer categoryId, Integer status) throws Exception {
        if (categoryId == null || categoryId == 0) {
            throw new BusinessException("类别id不能为空");
        }
        return categoryDao.delCategory(categoryId, status);
    }


}
