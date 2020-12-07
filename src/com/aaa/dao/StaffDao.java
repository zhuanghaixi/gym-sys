package com.aaa.dao;

import com.aaa.entity.Staff;

import java.util.List;
import java.util.Map;


public interface StaffDao {

    /**
     * 根据账户密码登陆
     * @param loginName
     * @param loginPwd
     * @return
     */
    Map<String,Object> login(String loginName, String loginPwd);

    /**
     * 分页查询所有员工的信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Staff> getAllStaffInfo(Integer pageNumber, Integer pageSize, String searchStuno, String searchName);

    /**
     *查询所有员工的信息总条数
     * @return
     */
    int getAllStaffInfoCount(String searchStuno, String searchName);
    /**
     * 修改员工
     * @param staff
     * @return
     */
    int updateStudent(Staff staff);

    /**
     * 删除员工
     * @param staffId
     * @return
     */
    int delStaff(Integer staffId, Integer status);

    /**
     * 增加员工
     * @param staff
     * @return
     */
    int addStaff(Staff staff);
    /**
     * 获取最后一个
     * @return
     */
    Integer getLastStaffId();

}
