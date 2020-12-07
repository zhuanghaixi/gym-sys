package com.aaa.dao.impl;

import com.aaa.dao.GoodsDao;

import com.aaa.entity.Goods;

import com.aaa.util.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class GoodsDaoImpl implements GoodsDao {
    private BaseDao baseDao = new BaseDao();



    @Override
    public List<Goods> getAllGoodsInfo(Integer pageNumber, Integer pageSize, String searchgoodsid, String searchName) {
        String sql = "select * from goods where 1=1 ";
        if (StringUtils.isNotBlank(searchgoodsid)) {
            sql += " and goodsid = " + searchgoodsid;
        }
        if (StringUtils.isNotBlank(searchName)) {
            searchName = "%" + searchName + "%";
            sql += " and name like '" + searchName +  "'";
        }
        sql += " limit ?,?";
        Object[] params = {pageNumber, pageSize};
        List<Goods> goods = baseDao.queryList(sql, params, Goods.class);
        return goods;
    }



    @Override
    public int getAllGoodsInfoCount(String searchgoodsid, String searchName) {
        String sql = "select count(1) len from goods where 1=1";
        if (StringUtils.isNotBlank(searchgoodsid)) {
            sql += " and goodsid= " + searchgoodsid;
        }
        if (StringUtils.isNotBlank(searchName)) {
            searchName = "%" + searchName + "%";
            sql += " and name like '" + searchName + "'";
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
    public int updateGoods(Goods goods) {

            String sql = "update goods set name = ?,code= ?,status = ?,stock = ?,unitId = ?,price = ?,categoryId = ? where goodsid = ?";
            Object[] params = {goods.getName(), goods.getCode(), goods.getStatus(), goods.getStock()
                    , goods.getUnitId(), goods.getPrice(), goods.getCategoryId(), goods.getGoodsid()};
            return baseDao.executeUpdate(sql, params);

        }



    @Override
    public int delGoods(Integer goodsid, Integer status) {
        status = status == 1 ? 0 : 1;
        String sql = "update goods set status = ? where goodsid = " + goodsid;
        Object[] params = {status};
        int len = baseDao.executeUpdate(sql, params);
        return len;
    }

    @Override
    public int addGoods(Goods goods) {
        String sql = "insert into goods (name,code,status,stock,unitId,price,categoryId,goodsid) values (?,?,?,?,?,?,?,?)";
        Object[] params = {goods.getName(), goods.getCode(), goods.getStatus(), goods.getStock()
                , goods.getUnitId(), goods.getPrice(), goods.getCategoryId(), goods.getGoodsid()};
        return baseDao.executeUpdate(sql, params);
    }


    @Override
    public String getLastGoodsId() {
        String sql = "select goodsid from goods order by id desc limit 1";
        List<Map<String, Object>> maps = baseDao.executeQuery(sql, null);
        if (maps != null && maps.size() > 0) {
            String goodsid = maps.get(0).get("goodsid") + "";
            return goodsid;
        }
        return null;
    }
    @Test
    public void test() {
        System.out.println(getAllGoodsInfo(0, 10, "", ""));
    }

}
