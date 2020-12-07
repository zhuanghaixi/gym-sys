package com.aaa.service;

import java.util.Map;

public interface GoodsService {




    /**
     * 分页查询所有商品的信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Map<String,Object> getAllGoodsInfo(Integer pageNumber, Integer pageSize, String searchgoodsid, String searchName)throws Exception;

    /**
     * 修改商品信息
     * @param goodsid
     * @param name
     * @param code
     * @param status
     * @param stock
     * @param unitId
     * @param price
     * @param categoryId
     * @return
     * @throws Exception
     */
    int updateGoodsByGoodsId(Integer goodsid, String name, String code, Integer status, Integer stock, Integer unitId, Integer price, Integer categoryId) throws Exception;


    /**
     * 删除商品
     * @param goodsid
     * @param status
     * @return
     * @throws Exception
     */

    int delGoods(Integer goodsid, Integer status) throws Exception;
}
