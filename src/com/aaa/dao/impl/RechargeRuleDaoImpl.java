package com.aaa.dao.impl;

import com.aaa.dao.RechargeRuleDao;
import com.aaa.entity.RechargeRule;
import com.aaa.util.BaseDao;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class RechargeRuleDaoImpl implements RechargeRuleDao {
    private BaseDao baseDao = new BaseDao();

    @Override
    public int add(RechargeRule rechargerule) {
        String sql = "insert into rechargerule (name,coefficient,createdTime,endTime,status) values " +
                "(?,?,?,?,?) ";
        Object[] params = {rechargerule.getName(), rechargerule.getCoefficient(), rechargerule.getCreatedTime(),
                rechargerule.getEndTime(), rechargerule.getStatus()};
        return baseDao.executeUpdate(sql, params);
    }

    @Override
    public int update(RechargeRule rechargerule) {
        String sql = "update rechargerule set name = ?,coefficient=?,endTime=?,status = ? where id = ?";
        Object[] params = {rechargerule.getName(), rechargerule.getCoefficient(), rechargerule.getEndTime(),rechargerule.getStatus(),
                rechargerule.getId()};
        return baseDao.executeUpdate(sql, params);
    }

    @Override
    public int delete(int id) {
        String sql1 = "select status from rechargerule where id = ?";
        Object[] params1 = {id};
        List<Map<String, Object>> mapList = baseDao.executeQuery(sql1, params1);
        int len = 0;
        if (mapList != null && mapList.size() > 0) {
            Integer status = Integer.parseInt(mapList.get(0).get("status") + "");
            status = status == 1 ? 0 : 1;
            String sql = "update rechargerule set status = ? where id = ?";
            Object[] params = {status, id};
            len = baseDao.executeUpdate(sql, params);
        }
        return len;
    }

    @Override
    public RechargeRule findById(int id) {
        return null;
    }

    @Override
    public List<RechargeRule> findAllList(Map<String, Object> param) {
        String sql = "select * from rechargerule where 1=1 ";
        if (param.get("searchName") != null && StringUtils.isNotBlank(param.get("searchName") + "")) {
            String searchName = "'%" + param.get("searchName") + "" + "%'";
            sql += " and name like " + searchName;
        }
        if (param.get("status") != null && StringUtils.isNotBlank(param.get("status") + "")) {
            Integer status = Integer.parseInt(param.get("status") + "");
            sql += " and status = " + status;
        }
        sql += " limit ?,?";
        Integer pageNumber = Integer.parseInt(param.get("pageNumber") + "");
        Integer pageSize = Integer.parseInt(param.get("pageSize") + "");
        Object[] params = {pageNumber, pageSize};
        List<RechargeRule> rechargeRuleList = baseDao.queryList(sql, params, RechargeRule.class);
        return rechargeRuleList;
    }

    @Override
    public int findAllListCount(Map<String, Object> param) {
        String sql = "select count(1) len from rechargerule where 1=1 ";
        if (param.get("searcheName") != null && StringUtils.isNotBlank(param.get("searcheName") + "")) {
            String searcheName = "'%" + param.get("searcheName") + "" + "%'";
            sql += " and name like " + searcheName;
        }
        if (param.get("searchStatus") != null && StringUtils.isNotBlank(param.get("searchStatus") + "")) {
            Integer status = Integer.parseInt(param.get("searchStatus") + "");
        }
        List<Map<String, Object>> mapList = baseDao.executeQuery(sql, null);
        if (mapList.get(0) != null && mapList.get(0).get("len") != null) {
            return Integer.parseInt(mapList.get(0).get("len") + "");
        }
        return 0;
    }
}
