package com.aaa.service.impl;

import com.aaa.dao.GoodsDao;
import com.aaa.dao.impl.GoodsDaoImpl;
import com.aaa.entity.Goods;
import com.aaa.service.GoodsService;
import com.aaa.util.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsServiceImpl implements GoodsService {
    private GoodsDao goodsDao = new GoodsDaoImpl();



    @Override
    public Map<String, Object> getAllGoodsInfo(Integer pageNumber, Integer pageSize, String searchgoodsid, String searchName) throws Exception {
        if (pageNumber == null || pageNumber == 0) {
            throw new BusinessException("当前页数不能为空");
        }
        if (pageSize == null || pageSize == 0) {
            throw new BusinessException("每页条数不能为空");
        }
        pageNumber = (pageNumber - 1) * pageSize;
        List<Goods> list = goodsDao.getAllGoodsInfo(pageNumber, pageSize, searchgoodsid, searchName);
        int count = goodsDao.getAllGoodsInfoCount(searchgoodsid, searchName);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        return map;
    }

    @Override
    public int updateGoodsByGoodsId(Integer goodsid, String name, String code, Integer status, Integer stock, Integer unitId, Integer price, Integer categoryId) throws Exception {
        int len = 0;
        Goods goods = new Goods();
        goods.setName(name);
        goods.setCode(code);
        goods.setStatus(status);
        goods.setStock(stock);
        goods.setUnitId(unitId);
        goods.setPrice(price);
        goods.setCategoryId(categoryId);

        if (goodsid != null && goodsid != 0) {
            goods.setGoodsid(goodsid);
            len = goodsDao.updateGoods(goods);

        } else {
            String lastno = goodsDao.getLastGoodsId();
            if (StringUtils.isBlank(lastno)) {
                throw new BusinessException("获取不到id");
            }
            goods.setGoodsid(Integer.parseInt(lastno) + 1);
            //    goods.setCreatedTime(new Date());
            len = goodsDao.addGoods(goods);

        }
        return len;


    }


    @Override
    public int delGoods(Integer goodsid, Integer status) throws Exception {
        if (goodsid == null || goodsid == 0) {
            throw new BusinessException("商品id不能为空");
        }
        return goodsDao.delGoods(goodsid, status);
    }
}



