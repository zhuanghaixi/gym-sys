package com.aaa.dao;

import com.aaa.entity.Order;
import com.aaa.entity.Staff;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    /**
     * 获取所有订单信息
     * @return
     */
    List<Order> getAllOrder(Integer pageNumber, Integer pageSize, String searchOrderId, String searchCardId);


    /**
     * 分页查询所有订单信息
     */

    int getAllOrdercount(String searchOrderId, String searchCardId);

    /**
     * 订单结算
     */
    int addOrder(Order order);
    /**
     *减少会员余额，添加会员积分
     */
    int updateCardAmount(Integer id, Double pay) ;

    /**
     *通过code来去商品数量
     */
    Map getGoodsNumForCode(String goodsid);

    /**
     *减少数量
     */
    int reduceMargin(String code, String num);
}
