package com.aaa.service;


import java.util.Map;

public interface RechargeRuleService {


    /**
     * 修改规则
     * @param id
     * @param name
     * @param coefficient
     * @param createdTime
     * @param endTime
     * @param status
     * @return
     */
    int update(int id, String name, Double coefficient, String createdTime, String endTime, int status) throws Exception;

    /**
     * 删除规则
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 分页查询所有规则
     * @param searchName
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Map<String,Object> findAllList(String searchName, String status, Integer pageNumber, Integer pageSize) throws Exception;
}
