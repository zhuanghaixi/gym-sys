package com.aaa.service;

import com.aaa.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    /**
     * 获取所有订单信息
     * @return
     */
    Map<String, Object> getAllOrder(Integer pageNumber, Integer pageSize, String searchOrderId, String searchCardId) throws Exception;


    /**
     * 订单结算
     */
    int addOrder(Order order);

    /**
     *减少会员余额，添加会员积分
     */
    int updateCardAmount(Integer id, Double pay);
    /**
     *通过code来去商品数量
     */
    Map getGoodsNumForCode(String code);

    /**
     *减少数量
     */
    int reduceMargin(String code, String num);
}
