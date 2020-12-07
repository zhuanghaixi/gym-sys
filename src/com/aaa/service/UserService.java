package com.aaa.service;

import java.util.Map;

public interface UserService {
    /**
     * 查询会员信息
     *
     * @param pageNumber
     * @param pageSize
     * @param searchUserId
     * @param searchName
     * @return
     * @throws Exception
     */
    Map<String, Object> getAllUserInfo(Integer pageNumber, Integer pageSize, String searchUserId, String searchName) throws Exception;

    /**
     * 修改
     */
    int updateUserByUserId(Integer userId, Integer cardId, String userName, String phone, Integer status, String idCard, String birthday, Integer sex, String address, String momo) throws Exception;

    /**
     * 会员登记
     */
    int addUserInfo(String userName, String userPhone, String userLevel, String userStatus, String staffId, String staffName, String birthday, String amount, String idno, String userSex, String area, String address, String momo) throws Exception;

    /**
     * 删除
     */
    int delUser(Integer userId, Integer status) throws Exception;
}
