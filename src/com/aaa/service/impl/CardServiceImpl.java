package com.aaa.service.impl;

import com.aaa.dao.CardDao;
import com.aaa.dao.impl.CardDaoImpl;
import com.aaa.entity.Card;
import com.aaa.entity.CardType;
import com.aaa.entity.RechargeRecord;
import com.aaa.service.CardService;
import com.aaa.util.BusinessException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardServiceImpl implements CardService {
    private CardDao cardDao = new CardDaoImpl();

    @Override
    public List<CardType> getAllCardTypeInfo() {

        return cardDao.getAllCardTypeInfo();
    }
    @Override
    public List<Map<String, Object>> getDataByNearYear() {
        return cardDao.getDataByNearYear();
    }
    @Override
    public Map<String, Object> getAllCardInfo(Integer pageNumber, Integer pageSize, String searchCardId) throws Exception {
        if (pageNumber == null || pageNumber == 0) {
            throw new BusinessException("当前页数不能为空");
        }
        if (pageSize == null || pageSize == 0) {
            throw new BusinessException("每页条数不能为空");
        }
        pageNumber = (pageNumber - 1) * pageSize;
        List<Card> list = cardDao.getAllCardInfo(pageNumber, pageSize, searchCardId);
        int count = cardDao.getAllCardInfoCount(searchCardId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        return map;
    }

    @Override
    public int updateCardByCardId(Integer cardId, Integer userId, Double amount, Integer credit, Integer status) throws Exception {

        Card card = new Card();
        card.setUserId(userId);
        card.setAmount(amount);
        card.setCredit(credit);
        card.setStatus(status);
        if (cardId == null || cardId == 0) {
            Integer lastCardId = cardDao.getLastCardId();
            card.setCardId(lastCardId + 1);
            return cardDao.addCard(card);
        }else{
            card.setCardId(cardId);
            return cardDao.updateCard(card);
        }
    }

    @Override
    public int delCard(Integer cardId, Integer status) throws Exception {
        if (cardId == null || cardId == 0) {
            throw new BusinessException("会员id不能为空");
        }
        return cardDao.delCard(cardId,status);
    }


    @Override
    public int userRecharge(String userCardId, String credit, String amount, String cardAmount, String rechargeRule, String staffId, String momo, String sendAmount, String userCredit) {
        Card card = new Card();
        card.setCardId(Integer.parseInt(userCardId));
        //余额
        Double ca = Double.parseDouble(cardAmount);
        //充值金额
        Double a = Double.parseDouble(amount);
        //赠送金额
        Double b = Double.parseDouble(sendAmount);
        card.setAmount(ca+a+b);
        card.setCredit(Integer.parseInt(credit) + Integer.parseInt(userCredit));
        card.setStatus(0);

        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setCardId(Integer.parseInt(userCardId));
        rechargeRecord.setRechargeamount(Double.parseDouble(amount));
        rechargeRecord.setAfteramount(ca + a);
        rechargeRecord.setBeforeamount(ca);
        rechargeRecord.setRuleid(Integer.parseInt(rechargeRule));
        rechargeRecord.setStaffid(Integer.parseInt(staffId));
        rechargeRecord.setMomo(momo);
        rechargeRecord.setCreatedtime(new Date());
        cardDao.updateCard(card);
        cardDao.addRechargeRecord(rechargeRecord);
        return 1;
    }

    @Override
    public Map<String, Object> getAllCardInfo(Integer pageNumber, Integer pageSize) {
        List<Card> list = cardDao.getAllCardInfo1(pageNumber,pageSize);
        int count = cardDao.getAllCardInfoCount1();
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("count",count);
        return map;
    }

}
