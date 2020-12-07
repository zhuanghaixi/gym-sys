package com.aaa.service;

import com.aaa.entity.Category;


import java.util.List;
import java.util.Map;

public interface CategoryService {

    /**
     * 分页查询所有类别的信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Map<String,Object> getAllCategoryInfo(Integer pageNumber, Integer pageSize, String searchcategoryId, String searchName)throws Exception;

    /**
     * 修改类别信息
     * @param categoryId
     * @param name
     * @param momo
     * @param status
     * @return
     * @throws Exception
     */
    int updateCategoryByCategoryId(Integer categoryId, String name, String momo, Integer status) throws Exception;


    /**
     * 删除类别
     * @param categoryId
     * @param status
     * @return
     * @throws Exception
     */

    int delCategory(Integer categoryId, Integer status) throws Exception;

}
