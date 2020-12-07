package com.aaa.dao;

import com.aaa.entity.News;

import java.util.List;
import java.util.Map;

/**

 * 新闻接口
 **/
public interface NewsDao {

    /**
     * 分页查询所有新闻的信息
     * @param pageNumber
     * @param pageSize
     * @param searchTitle
     * @param startTime
     * @param endTime
     * @return
     */
    List<News> getAllNewsInfo(Integer pageNumber, Integer pageSize, String searchTitle, String startTime, String endTime);

    /**
     *查询所有新闻的信息总条数
     * @return
     */
    int getAllNewsInfoCount(String searchTitle, String startTime, String endTime);
    /**
     * 修改新闻
     * @param News
     * @return
     */
    int updateNews(News News);

    /**
     * 删除新闻
     * @param id
     * @return
     */
    int delNews(Integer id, Integer status);

    /**
     * 增加新闻
     * @param News
     * @return
     */
    int addNews(News News);
    /**
     * 获取最后一个新闻Id
     * @return
     */
    String getLastNewsId();

}
