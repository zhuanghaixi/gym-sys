package com.aaa.service;

import java.util.Map;


public interface NewsService {
    /**
     * 分页查询所有新闻/公告的信息
     * @param pageNumber
     * @param pageSize
     * @param searchTitle
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    Map<String,Object> getAllNewsInfo(Integer pageNumber, Integer pageSize, String searchTitle, String startTime, String endTime)throws Exception;

    /**
     * 修改新闻/公告信息
     * @param newsId
     * @param status
     * @param newsTitle
     * @param newsContent
     * @return
     * @throws Exception
     */
    int updateNewsById(Integer newsId, Integer status, String newsTitle, String newsContent) throws Exception;

    /**
     * 删除新闻/公告
     * 虽然 我们叫做删除 但是实际上只是修改状态不能删除
     * @param id
     * @return
     */
    int delNews(Integer id, Integer status) throws Exception;

    /**
     * 增加新闻
     * @param status
     * @param newsTitle
     * @param staffId
     * @param createdTime
     * @param newsEndTime
     * @param newsContent
     * @return
     * @throws Exception
     */
    int addNewsInfo(Integer status, String newsTitle, Integer staffId, String createdTime, String newsEndTime, String newsContent) throws Exception;
}
