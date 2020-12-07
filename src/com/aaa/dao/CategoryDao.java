package com.aaa.dao;

import com.aaa.entity.Category;



import java.util.List;

public interface CategoryDao {
    /**
     * 商品类别
     * @return
     */
    List<Category> getAllCategoryInfo(Integer pageNumber, Integer pageSize, String searchcategoryId, String searchName);
    /**
     *查询所有类别的信息总条数
     * @return
     */
    int getAllCategoryInfoCount(String searchcategoryId, String searchName);

    /**修改类别
     *
     * @param category
     * @return
     */
    int updateCategory(Category category);

    /**
     * 删除类别
     * @param categoryId
     * @param status
     * @return
     */
    int delCategory(Integer categoryId, Integer status);

    /**
     * 获取最后一个
     * @return
     */
    String getLastCategoryId();
}
