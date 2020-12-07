package com.aaa.dao;

import com.aaa.entity.Goods;



import java.util.List;
import java.util.Map;

public interface GoodsDao {
    List<Goods> getAllGoodsInfo(Integer pageNumber, Integer pageSize, String searchgoodsid, String searchName);


    /**
     *查询所有商品的信息总条数
     * @return
     */
    int getAllGoodsInfoCount(String searchgoodsid, String searchName);

    /**修改商品
     *
     * @param goods
     * @return
     */
    int updateGoods(Goods goods);

    /**
     * 删除商品
     * @param goodsid
     * @param status
     * @return
     */
    int delGoods(Integer goodsid, Integer status);

  int addGoods(Goods goods);
    /**
     * 获取最后一个
     * @return
     */
    String getLastGoodsId();
}
