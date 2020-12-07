package com.aaa.service.impl;

import com.aaa.dao.RechargeRuleDao;
import com.aaa.dao.impl.RechargeRuleDaoImpl;
import com.aaa.entity.RechargeRule;
import com.aaa.service.RechargeRuleService;
import com.aaa.util.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RechargeRuleServiceImpl implements RechargeRuleService {
    private RechargeRuleDao rechargeRuleDao = new RechargeRuleDaoImpl();

    @Override
    public int update(int id, String name, Double coefficient, String createdTime, String endTime, int status) throws Exception {

        //参数校验
        if (StringUtils.isBlank(name)) {
            throw new BusinessException("规则名字不能为空");
        }
        if (coefficient == null) {
            throw new BusinessException("规则不能为空");
        }
        RechargeRule rechargeRule = new RechargeRule();
        rechargeRule.setId(id);
        rechargeRule.setName(name);
        rechargeRule.setCoefficient(coefficient);
        rechargeRule.setCreatedTime(createdTime);
        rechargeRule.setEndTime(endTime);
        rechargeRule.setStatus(status);
        int len = 0;
        if (id == 0){
            len = rechargeRuleDao.add(rechargeRule);
        }else {
             len = rechargeRuleDao.update(rechargeRule);
        }
        return len;
    }

    @Override
    public int delete(int id) {

        return rechargeRuleDao.delete(id);
    }

    @Override
    public Map<String, Object> findAllList(String searchName, String status, Integer pageNumber, Integer pageSize) throws Exception {
        //传入的参数
        Map<String, Object> map = new HashMap<>();
        map.put("searchName", searchName);
        map.put("status", status);
        map.put("pageNumber", (pageNumber - 1) * pageSize);
        map.put("pageSize", pageSize);
        List<RechargeRule> rechargeRuleList = rechargeRuleDao.findAllList(map);
        int count = rechargeRuleDao.findAllListCount(map);
        //返回参会苏
        Map<String,Object> result = new HashMap<>();
        result.put("list",rechargeRuleList);
        result.put("count",count);
        return result;
    }

}
