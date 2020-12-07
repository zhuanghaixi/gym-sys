package com.aaa.dao.impl;

import com.aaa.dao.StaffDao;
import com.aaa.entity.Role;
import com.aaa.entity.Staff;
import com.aaa.util.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class StaffDaoImpl implements StaffDao {
    private BaseDao baseDao = new BaseDao();

    @Override
    public Map<String, Object> login(String loginName, String loginPwd) {
        String sql = "select * from staff where staffid = ? and password = ? and status = 1 or status = 2";
        Object[] params = {loginName, loginPwd};
        List<Map<String, Object>> lists = baseDao.executeQuery(sql, params);
        if (lists != null && lists.size() > 0) {
            return lists.get(0);
        }
        return null;
    }

    @Override
    public List<Staff> getAllStaffInfo(Integer pageNumber, Integer pageSize, String searchStuno, String searchName) {
        String sql = "select s.*,r.roleName roleName from staff s,role r where s.roleId = r.id ";
        if (StringUtils.isNotBlank(searchStuno)) {
            sql += " and staffId = " + searchStuno;
        }
        if (StringUtils.isNotBlank(searchName)) {
            searchName = "%" + searchName + "%";
            sql += " and staffName like '" + searchName + "'";
        }
        sql += " limit ?,?";
        Object[] params = {pageNumber, pageSize};
        List<Staff> staff = baseDao.queryList(sql, params, Staff.class);
        return staff;
    }

    @Override
    public int getAllStaffInfoCount(String searchStuno, String searchName) {
        String sql = "select count(1) len from staff where 1=1 ";
        if (StringUtils.isNotBlank(searchStuno)) {
            sql += " and staffId = " + searchStuno;
        }
        if (StringUtils.isNotBlank(searchName)) {
            searchName = "%" + searchName + "%";
            sql += " and staffName like '" + searchName + "'";
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
    public int updateStudent(Staff staff) {
        String sql = "update staff set staffName = ?,phone = ?,idCard = ?,address = ?,status = ?,roleId = ?,momo = ? where staffId = ?";
        Object[] params = {staff.getStaffName(), staff.getPhone(), staff.getIdCard(), staff.getAddress(), staff.getStatus()
                , staff.getRoleId(), staff.getMomo(), staff.getStaffId()};

        return baseDao.executeUpdate(sql, params);
    }

    @Override
    public int delStaff(Integer staffId, Integer status) {
        status = status == 1 ? 2 : 1;
        String sql = "update staff set status = ? where staffId = " + staffId;
        Object[] params = {status};
        int len = baseDao.executeUpdate(sql, params);
        return len;
    }

    @Override
    public int addStaff(Staff staff) {
        String sql = "insert into   staff (staffId,staffName,phone,idCard,address,status,roleId,momo,createdTime,password) values (?,?,?,?,?,?,?,?,?,123123)";
        Object[] params = {staff.getStaffId(),staff.getStaffName(),staff.getPhone(),staff.getIdCard(),staff.getAddress(),staff.getStatus(),staff.getRoleId(),staff.getMomo(),staff.getCreatedTime()};
        return baseDao.executeUpdate(sql, params);
    }

    @Override
    public Integer getLastStaffId() {
        String sql = "select staffId from staff order by id desc limit 1";
        List<Map<String, Object>> maps = baseDao.executeQuery(sql, null);
        if (maps != null && maps.size() > 0) {
            String staffId = maps.get(0).get("staffId") + "";
            return Integer.parseInt(staffId);
        }
        return null;
    }



}
