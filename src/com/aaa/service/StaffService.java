package com.aaa.service;

import java.util.Map;

public interface StaffService {
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
    Map<String,Object> getAllStaffInfo(Integer pageNumber, Integer pageSize, String searchStuno, String searchName)throws Exception;

    /**
     * 修改员工信息
     * @param staffId
     * @param staffName
     * @param phone
     * @param idCard
     * @param status
     * @param roleId
     * @param momo
     * @param address
     * @return
     */
    int updateStaffByStaffId(Integer staffId, String staffName, String phone, String idCard, Integer status, Integer roleId, String momo, String address, String createdTime) throws Exception;

    /**
     * 删除员工
     * 虽然 我们叫做删除 但是实际上只是修改状态不能删除
     * @param staffId
     * @return
     */
    int delStaff(Integer staffId, Integer status) throws Exception;

}
