package com.aaa.service.impl;

import com.aaa.dao.StaffDao;
import com.aaa.dao.impl.StaffDaoImpl;
import com.aaa.entity.Staff;
import com.aaa.service.StaffService;
import com.aaa.util.BusinessException;
import com.aaa.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffServiceImpl implements StaffService {
    private StaffDao staffDao = new StaffDaoImpl();

    @Override
    public Map<String, Object> login(String loginName, String loginPwd) {
        return staffDao.login(loginName, loginPwd);
    }

    @Override
    public Map<String, Object> getAllStaffInfo(Integer pageNumber, Integer pageSize, String searchStuno, String searchName) throws Exception {
        if (pageNumber == null || pageNumber == 0) {
            throw new BusinessException("当前页数不能为空");
        }
        if (pageSize == null || pageSize == 0) {
            throw new BusinessException("每页条数不能为空");
        }
        pageNumber = (pageNumber - 1) * pageSize;
        List<Staff> list = staffDao.getAllStaffInfo(pageNumber, pageSize, searchStuno, searchName);
        int count = staffDao.getAllStaffInfoCount(searchStuno, searchName);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        return map;
    }

    @Override
    public int updateStaffByStaffId(Integer staffId, String staffName, String phone, String idCard, Integer status, Integer roleId, String momo, String address,String createdTime) throws Exception {
        Staff staff = new Staff();
        staff.setStaffName(staffName);
        staff.setPhone(phone);
        staff.setIdCard(idCard);
        staff.setStatus(status);
        staff.setRoleId(roleId);
        staff.setMomo(momo);
        staff.setAddress(address);

        if (staffId == 0 || staffId == null) {
            //增加操作
            //step1:先获取最后一个员工号
            Integer lastStaffId = staffDao.getLastStaffId();
            staff.setStaffId(lastStaffId + 1);
            //调用时间工具类: DateUtils.toFormat 来实现对时间的设置
            staff.setCreatedTime(DateUtils.toFormat(new Date()));
            return staffDao.addStaff(staff);
        } else {
            staff.setStaffId(staffId);
            return staffDao.updateStudent(staff);
        }


//        if (staffId == null || staffId == 0) {
//            throw new BusinessException("员工id不能为空");
//        }

    }

    @Override
    public int delStaff(Integer id,Integer status) throws Exception {
        if (id == null || id == 0) {
            throw new BusinessException("id不能为空");
        }
        return staffDao.delStaff(id,status);
    }

}
