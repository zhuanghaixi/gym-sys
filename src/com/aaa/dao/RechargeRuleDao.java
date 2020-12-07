package com.aaa.dao;

import com.aaa.entity.RechargeRule;

import java.util.List;
import java.util.Map;

/**

 * 充值规则
**/ 
public interface RechargeRuleDao {
    /**
     * 增加规则
     * @param rechargerule
     * @return
     */
    int add(RechargeRule rechargerule);

    /**
     * 修改规则
     * @param rechargerule
     * @return
     */
    int update(RechargeRule rechargerule);

    /**
     * 删除规则
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 根据id查询规则
     * @param id
     * @return
     */
    RechargeRule findById(int id);

    /**
     * 分页查询所有规则
     * @param param
     * @return
     */
    List<RechargeRule> findAllList(Map<String, Object> param);

    /**
     * 查询所有规则的条数
     * @param param
     * @return
     */
    int findAllListCount(Map<String, Object> param);
}
