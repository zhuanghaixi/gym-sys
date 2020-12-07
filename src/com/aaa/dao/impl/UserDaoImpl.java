package com.aaa.dao.impl;

import com.aaa.entity.User;
import com.aaa.util.BaseDao;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class UserDaoImpl implements com.aaa.dao.UserDao {
    BaseDao baseDao = new BaseDao();

    @Override
    public List<User> getAllUserInfo(Integer pageNumber, Integer pageSize, String searchUserId, String searchName) {
        String sql = "select *  from user  where 1 = 1";
        if (StringUtils.isNotBlank(searchUserId)) {
            sql += " and userId = " + searchUserId;
        }
        if (StringUtils.isNotBlank(searchName)) {
            searchName = "%" + searchName + "%";
            sql += " and userName like '" + searchName + "'";
        }
        sql += " order by id desc limit ?,?";
        Object[] params = {pageNumber, pageSize};
        List<User> userList = baseDao.queryList(sql, params, User.class);
        return userList;
    }

    @Override
    public int getAllUserInfoCount(String searchUserId, String searchName) {
        String sql = "select count(1) len from user ";
        if (StringUtils.isNotBlank(searchUserId)) {
            sql += " where userId = " + searchUserId;
        }
        if (StringUtils.isNotBlank(searchName)) {
            searchName = "%" + searchName + "%";
            sql += " where userName like '" + searchName + "'";
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
    public int updateUser(User user) {
        String sql = "update user set cardId  = ?,userName = ?,phone = ?,status = ?,idCard = ?,birthday = ?,sex = ?,address = ?,area = ?,momo = ? where userId = ?";
        Object[] params = {user.getCardId(), user.getUserName(), user.getPhone(), user.getStatus(), user.getIdCard(), user.getBirthday(), user.getSex(), user.getAddress(), user.getArea(),user.getMomo(), user.getUserId()};
        return baseDao.executeUpdate(sql, params);
    }

    @Override
    public int delUser(Integer userId, Integer status) {
        status = status == 1 ? 0 : 1;
        String sql = "update user set status = ? where userId = " + userId;
        Object[] params = {status};
        int len = baseDao.executeUpdate(sql, params);
        return len;
    }

    @Override
    public int addUser(User user) {
        String sql = "insert into user (userId,cardId,userName,phone,status,idCard,birthday,sex,address,area,createdTime,momo)" +
                "values (?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUserId(),user.getCardId(), user.getUserName(), user.getPhone(), user.getStatus(), user.getIdCard(), user.getBirthday(), user.getSex(), user.getAddress(), user.getArea(),
                user.getCreatedTime(),user.getMomo()};
        int len = baseDao.executeUpdate(sql, params);
        return len;
    }

    @Override
    public String lastUserId() {
        String sql = "select userId from user order by id desc limit 1";
        List<Map<String, Object>> maps = baseDao.executeQuery(sql, null);
        if (maps != null && maps.size() > 0){
            return maps.get(0).get("userId") + "";
        }
        return null;
    }

}
