package com.aaa.service.impl;

import com.aaa.dao.CardDao;
import com.aaa.dao.UserDao;
import com.aaa.dao.impl.CardDaoImpl;
import com.aaa.dao.impl.UserDaoImpl;
import com.aaa.entity.Card;
import com.aaa.entity.RechargeRecord;
import com.aaa.entity.User;
import com.aaa.service.UserService;
import com.aaa.util.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    private CardDao cardDao = new CardDaoImpl();
    @Override
    public Map<String, Object> getAllUserInfo(Integer pageNumber, Integer pageSize, String searchUserId, String searchName) throws Exception {
        if (pageNumber == null || pageNumber == 0) {
            throw new BusinessException("当前页数不能为空");
        }
        if (pageSize == null || pageSize == 0) {
            throw new BusinessException("每页条数不能为空");
        }
        pageNumber = (pageNumber - 1) * pageSize;
        List<User> list = userDao.getAllUserInfo(pageNumber, pageSize, searchUserId, searchName);
        int count = userDao.getAllUserInfoCount(searchUserId, searchName);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        return map;
    }

    @Override

    public int updateUserByUserId( Integer userId,Integer cardId, String userName, String phone, Integer status, String idCard,String birthday, Integer sex, String address,String momo) throws Exception {
        User user = new User();
        user.setCardId(cardId);
        user.setUserName(userName);
        user.setPhone(phone);
        user.setStatus(status);
        user.setIdCard(idCard);
        user.setSex(sex);
        user.setAddress(address);
        user.setBirthday(birthday);
        user.setUserId(userId);
        user.setMomo(momo);
        return  userDao.updateUser(user);

    }

    @Override
    public int addUserInfo(String userName, String userPhone, String userLevel, String userStatus, String staffId, String staffName,String birthday, String amount, String idno, String userSex, String area, String address, String momo) throws Exception {
        /**
         * 1.user
         * 2.card
         * 3.rechargeRecord
         */
        //设置user
        User user = new User();
        String newUserId = userDao.lastUserId();
        if (StringUtils.isBlank(newUserId)){
            throw new BusinessException("用户id为空");
        }
        user.setUserId(Integer.parseInt(newUserId) + 1);
        user.setUserName(userName);
        user.setPhone(userPhone);
        user.setIdCard(idno);
        user.setStatus(Integer.parseInt(userStatus));
        user.setBirthday(birthday);
        user.setSex(Integer.parseInt(userSex));
        user.setAddress(address);
        user.setArea(area);
        user.setCreatedTime(new Date());
        user.setMomo(momo);
        //设置卡
        Card card = new Card();
        String cardId = cardDao.lastCardId();
        if (StringUtils.isBlank(cardId)){
            throw new BusinessException("卡id为空");
        }
        card.setCardId(Integer.parseInt(cardId) + 1);
        user.setCardId(Integer.parseInt(cardId) + 1);
        card.setUserId(Integer.parseInt(newUserId) + 1);
        card.setUserName(userName);
        card.setAmount(Double.parseDouble(amount));
        card.setCredit(Integer.parseInt(amount));
        card.setCreatedTime(new Date());
        card.setStatus(0);
        /**
         * 充值记录
         *  这里是新开卡  新开卡就意味着 什么都是新的
         */
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setCardId(card.getCardId());
        rechargeRecord.setRechargeamount(Double.parseDouble(amount));
        rechargeRecord.setBeforeamount(0.00);
        rechargeRecord.setAfteramount(Double.parseDouble(amount));
        rechargeRecord.setRuleid(0);
        rechargeRecord.setCreatedtime(new Date());
        rechargeRecord.setStaffid(Integer.parseInt(staffId));
        rechargeRecord.setMomo(staffName + "开卡("+momo+")");
        int len = userDao.addUser(user);
        len = cardDao.addCard(card);
        len = cardDao.addRechargeRecord(rechargeRecord);
        return 1;
    }


    @Override
    public int delUser(Integer userId, Integer status) throws Exception {
        if (userId == null || userId == 0) {
            throw new BusinessException("会员id不能为空");
        }
        return userDao.delUser(userId,status);
    }
}
