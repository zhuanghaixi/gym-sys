package com.aaa.dao.impl;

import com.aaa.dao.CardDao;
import com.aaa.entity.Card;
import com.aaa.entity.CardType;
import com.aaa.entity.RechargeRecord;
import com.aaa.util.BaseDao;
import com.sun.jmx.snmp.internal.SnmpOutgoingRequest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardDaoImpl implements CardDao {
    private BaseDao baseDao = new BaseDao();

    @Override
    public List<Card> getAllCardInfo(Integer pageNumber, Integer pageSize, String searchCardId) {
        String sql = "select * from card where 1=1 ";
        if (StringUtils.isNotBlank(searchCardId)) {
            sql += " and cardId = " + searchCardId;
        }
        sql += " limit ?,?";
        Object[] params = {pageNumber, pageSize};
        List<Card> card = baseDao.queryList(sql, params, Card.class);
        return card;
    }

    @Override
    public int getAllCardInfoCount(String searchCardId) {
        String sql = "select count(1) len from card where 1=1 ";
        if (StringUtils.isNotBlank(searchCardId)) {
            sql += " and cardId = " + searchCardId;
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
    public int updateCard(Card card) {
        String sql = "update card set amount =?,credit =?,status =? where cardId = ?";
        Object[] params = {card.getAmount(), card.getCredit(), card.getStatus(), card.getCardId()};
        return baseDao.executeUpdate(sql, params);
    }

    @Override
    public int delCard(Integer cardId, Integer status) {
        status = status == 0 ? 1 : 0;
        String sql = "update card set status = ? where cardId = " + cardId;
        Object[] params = {status};
        int len = baseDao.executeUpdate(sql, params);
        return len;
    }

    @Override
    public int addCard(Card card) {
        String sql = "insert into card (cardId,userId,userName,amount,credit,status,createdTime) values (?,?,?,?,?,?,?)";
        Object[] params = {card.getCardId(), card.getUserId(), card.getUserName(),card.getAmount(), card.getCredit(), card.getStatus(),card.getCreatedTime()};
        return baseDao.executeUpdate(sql, params);
    }

    @Override
    public Integer getLastCardId() {
        String sql = "select cardId from card order by id desc limit 1";
        List<Map<String, Object>> maps = baseDao.executeQuery(sql, null);
        if (maps != null && maps.size() > 0) {
            String cardId = maps.get(0).get("cardId") + "";
            System.out.println(cardId);
            return Integer.parseInt(cardId);
        }
        return null;
    }

    @Override
    public List<Card> getAllCardInfo() {
        String sql = "select * from card";
        List<Card> cardList = baseDao.queryList(sql, null, Card.class);
        if (cardList != null) {
            for (int i = 0; i < cardList.size(); i++) {
                Card card = cardList.get(i);
                sql = "select id,name from cardtype where rank < (select credit from card where cardId = ?) ORDER BY LEVEL desc limit 1";
                Object[] params = {card.getCardId()};
                List<Map<String, Object>> maps = baseDao.executeQuery(sql, params);
                if (maps != null && maps.size() > 0) {
                    int cardTypeId = Integer.parseInt(maps.get(0).get("id") + "");
                    String cardTypeName = maps.get(0).get("name") + "";
                    card.setLevel(cardTypeId);
                    card.setLevelName(cardTypeName);
                }
            }
        }
        return cardList;
    }

    @Override
    public List<CardType> getAllCardTypeInfo() {
        String sql = "select * from cardtype where status = 1";
        List<CardType> cardTypeList = baseDao.queryList(sql, null, CardType.class);
        return cardTypeList;
    }

    @Override
    public int addRechargeRecord(RechargeRecord rechargeRecord) {
        String sql = "insert into rechargerecord (cardId,rechargeAmount,afterAmount,beforeAmount,ruleId,createdTime,staffId,momo) VALUES (?,?,?,?,?,?,?,?)";
        Object[] params = {rechargeRecord.getCardId(), rechargeRecord.getRechargeamount(), rechargeRecord.getAfteramount(), rechargeRecord.getBeforeamount()
                , rechargeRecord.getRuleid(), rechargeRecord.getCreatedtime(), rechargeRecord.getStaffid(), rechargeRecord.getMomo()};
        return baseDao.executeUpdate(sql, params);
    }

    @Override
    public String lastCardId() {
        String sql = "select cardId from card order by id desc limit 1";
        List<Map<String, Object>> maps = baseDao.executeQuery(sql, null);
        if (maps != null && maps.size() > 0){
            return maps.get(0).get("cardId") + "";
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getDataByNearYear() {
        String sql = "select SUM(rechargeAmount) amount,SUBSTRING(createdTime FROM 6 FOR 2) month from rechargerecord GROUP BY SUBSTRING(createdTime FROM 6 FOR 2)";
        return baseDao.executeQuery(sql,null);
    }

    @Override
    public List<Card> getAllCardInfo1(Integer pageNumber, Integer pageSize) {
        String sql = "select * from card where status = 0 and cardId <> 200001 limit "+(pageNumber - 1) * pageSize + "," + pageSize;
        List<Card> cardList = baseDao.queryList(sql,null,Card.class);
        if (cardList != null){
            for (int i = 0; i < cardList.size(); i++) {
                Card card = cardList.get(i);
                sql = "select id,name from cardtype where rank < (select credit from card where cardId = ?) ORDER BY LEVEL desc limit 1";
                Object[] params = {card.getCardId()};
                List<Map<String,Object>> maps = baseDao.executeQuery(sql,params);
                if (maps != null && maps.size() > 0){
                    int cardTypeId = Integer.parseInt(maps.get(0).get("id") + "");
                    String cardTypeName = maps.get(0).get("name") + "";
                    card.setLevel(cardTypeId);
                    card.setLevelName(cardTypeName);
                }
                sql = "select userName from user where userId = ?";
                Object[] params1 = {card.getUserId()};
                maps = baseDao.executeQuery(sql,params1);
                if (maps != null && maps.size() > 0){
                    String userName = maps.get(0).get("userName") + "";
                    card.setUserName(userName);
                }
            }
        }
        return cardList;
    }

    @Override
    public int getAllCardInfoCount1() {
        String sql = "select count(1) len from card where status = 0 and cardId <> 200001";
        List<Map<String, Object>> newsList = baseDao.executeQuery(sql, null);
        if (newsList != null && newsList.size() > 0) {
            return Integer.parseInt(newsList.get(0).get("len") + "");
        }
        return 0;
    }

    @Test
    public void test() {
        System.out.println(getAllCardInfo(0, 10, ""));
    }
}
