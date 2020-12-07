package com.aaa.service;

import com.aaa.entity.CardType;

import java.util.List;
import java.util.Map;

public interface CardService {
    /**
     * 获取所有的卡类型
     * @return
     */
    List<CardType> getAllCardTypeInfo();
    /**
     * 获取近一年的每个月的充值总额
     * @return
     */
    List<Map<String,Object>> getDataByNearYear();
    /**
     * 查询会员信息
     * @param pageNumber
     * @param pageSize
     * @throws Exception
     */
    Map<String,Object> getAllCardInfo(Integer pageNumber, Integer pageSize, String searchCardId)throws Exception;
    /**
     * 修改
     */
    int updateCardByCardId(Integer cardId, Integer userId, Double amount, Integer creadit, Integer status) throws Exception;
    /**
     * 删除
     */
    int delCard(Integer cardId, Integer status) throws Exception;

    /**
     * 用户充值
     * @param userCardId
     * @param credit
     * @param amount
     * @param cardAmount
     * @param rechargeRule
     * @param staffId
     * @param momo
     * @return
     */
    int userRecharge(String userCardId, String credit, String amount, String cardAmount, String rechargeRule, String staffId, String momo, String sendAmount, String userCredit);


    /**
     * 获取所有的卡信息
     *
     * @return
     */
    Map<String, Object> getAllCardInfo(Integer pageNumber, Integer pageSize);

}
