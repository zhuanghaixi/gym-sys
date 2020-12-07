package com.aaa.dao.impl;

import com.aaa.dao.OrderDao;
import com.aaa.entity.Order;
import com.aaa.util.BaseDao;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    BaseDao baseDao=new BaseDao();
    @Override
    public List<Order> getAllOrder(Integer pageNumber, Integer pageSize,String searchOrderId,String searchCardId) {
        String sql="select * FROM `order` ORDER BY orderId DESC";
        sql+=" limit ?,?";
        Object [ ] params ={pageNumber,pageSize};
        List<Order> order=baseDao.queryList(sql,params, Order.class);
        return order;
    }

    @Override
    public int getAllOrdercount(String searchOrderId, String searchCardId) {
        String sql = "select count(1) len from `order` where 1=1 ";
        if (StringUtils.isNotBlank(searchOrderId)) {
            sql += " and orderId = " + searchOrderId;
        }
        if (StringUtils.isNotBlank(searchCardId)) {
            searchCardId = "%" + searchCardId + "%";
            sql += " and cardId like '" + searchCardId + "'";
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
    public int addOrder(Order order) {
        String sql="insert into `order` (orderId,cardId,cardType,price,pay,credit,momo,createdTime) values (?,?,?,?,?,?,?,?)";
        Object[] params={order.getOrderId(),order.getCardId(),order.getCardType(),order.getPrice(),order.getPay(),order.getCredit(),order.getMomo(),order.getCreatedTime()};
        int a=baseDao.executeUpdate(sql,params);
        return a;
    }

    @Override
    public int updateCardAmount(Integer id, Double pay) {
        String sql ="update card set amount = amount-"+pay+" ,credit=credit+"+pay+" WHERE cardId=?";
        String sn=Integer.toString(id);
        String[] s = {sn};
        int b= baseDao.executeUpdate(sql,s);
        return b;
    }

    @Override
    public Map getGoodsNumForCode(String goodsid) {
        String sql = "select * from goods where goodsid = ?";
        String[] s = {goodsid};
        List<Map> list = baseDao.executeQuery(sql,s);
        if(list!=null&&list.size()>0){

            return list.get(0);
        }
        return null;
    }

    @Override
    public int reduceMargin(String code, String num) {
        String sql = "update goods set stock = stock-"+num+"  WHERE goodsid = ?";
        String[] s= {code};
        return baseDao.executeUpdate(sql,s);
    }
}
