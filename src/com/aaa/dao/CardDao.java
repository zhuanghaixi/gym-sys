package com.aaa.dao;

import com.aaa.entity.Card;
import com.aaa.entity.CardType;
import com.aaa.entity.RechargeRecord;

import java.util.List;
import java.util.Map;

public interface CardDao {
   /**
     * 分页查询所有会员卡的信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Card> getAllCardInfo(Integer pageNumber, Integer pageSize, String searchCardId);

    /**
     *查询所有会员卡的信息总条数
     * @return
     */
    int getAllCardInfoCount(String searchCardId);
    /**
     * 修改会员卡信息
     */
    int updateCard(Card card);

    /**
     * 删除会员卡信息
     * @param
     * @return
     */
    int delCard(Integer cardId, Integer status);

    /**
     * 增加会员卡
     * @param
     * @return
     */
    int addCard(Card card);
    /**
     * 获取最后一个id
     * @return
     */
    Integer getLastCardId();

    /**
     * 获取所有的卡信息
     * @return
     */
    List<Card> getAllCardInfo();

    /**
     * 获取所有的卡类型
     * @return
     */
    List<CardType> getAllCardTypeInfo();
 /**
  * 增加充值记录
  * @param rechargeRecord
  * @return
  */
 int addRechargeRecord(RechargeRecord rechargeRecord);

 /**
  * 获取最后一个cardid
  * @return
  */
 String lastCardId();
 /**
  * 获取近一年的每个月的充值总额
  * @return
  */
 List<Map<String,Object>> getDataByNearYear();

 /**
  * 获取所有的卡信息
  * @return
  */
 List<Card> getAllCardInfo1(Integer pageNumber, Integer pageSize);
 /**
  * 获取所有的卡信息
  * @return
  */
 int getAllCardInfoCount1();
}
