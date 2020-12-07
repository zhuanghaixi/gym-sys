package com.aaa.service.impl;

import com.aaa.dao.OrderDao;
import com.aaa.dao.impl.OrderDaoImpl;
import com.aaa.entity.Order;
import com.aaa.service.OrderService;
import com.aaa.util.BusinessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao=new OrderDaoImpl();
    @Override
    public Map<String, Object> getAllOrder(Integer pageNumber, Integer pageSize,String  searchOrderId,String searchCardId) throws Exception{
        if (pageNumber == null || pageNumber == 0) {
            throw new BusinessException("当前页数不能为空");
        }
        if (pageSize == null || pageSize == 0) {
            throw new BusinessException("每页条数不能为空");
        }
        pageNumber = (pageNumber - 1) * pageSize;
        List<Order> order=orderDao.getAllOrder(pageNumber,pageSize,searchOrderId,searchCardId);
        int count = orderDao.getAllOrdercount(searchOrderId,searchCardId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", order);
        map.put("count", count);
        return map;
//        int len=0;
//        len=list.get(0).getOrderId();
     }

    @Override
    public int addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    @Override
    public int updateCardAmount(Integer id, Double pay) {
        return orderDao.updateCardAmount(id,pay);
    }

    @Override
    public Map getGoodsNumForCode(String code) {
        return orderDao.getGoodsNumForCode(code);
    }

    @Override
    public int reduceMargin(String code, String num) {
        return orderDao.reduceMargin(code,num);
    }
}
