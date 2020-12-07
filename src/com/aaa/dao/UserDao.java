package com.aaa.dao;

import com.aaa.entity.User;

import java.util.List;

public interface UserDao {
    /**
     * 分页查询所有会员的信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<User> getAllUserInfo(Integer pageNumber, Integer pageSize, String searchUserId, String searchName);

    /**
     *查询所有会员的信息总条数
     * @return
     */
    int getAllUserInfoCount(String searchUserId, String searchName);
    /**
     * 修改会员
     */
    int updateUser(User user);

    /**
     * 删除会员
     * @param
     * @return
     */
    int delUser(Integer userId, Integer status);
    /**
     * 获取最后一个
     * @return
     */
    String lastUserId();

    /**
     * 会员登记
     * @param
     * @return
     */
    int addUser(User user);
}
