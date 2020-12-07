package com.aaa.service.impl;

import com.aaa.dao.NewsDao;
import com.aaa.dao.impl.NewsDaoImpl;
import com.aaa.entity.News;
import com.aaa.service.NewsService;
import com.aaa.util.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsServiceImpl implements NewsService {
    private NewsDao newsDao = new NewsDaoImpl();

    @Override
    public Map<String, Object> getAllNewsInfo(Integer pageNumber, Integer pageSize, String searchTitle, String startTime, String endTime) throws Exception {
        if (pageNumber == null || pageNumber == 0) {
            throw new BusinessException("当前页数不能为空");
        }
        if (pageSize == null || pageSize == 0) {
            throw new BusinessException("每页条数不能为空");
        }
        pageNumber = (pageNumber - 1) * pageSize;
        List<News> list = newsDao.getAllNewsInfo(pageNumber, pageSize, searchTitle,startTime,endTime);
        int count = newsDao.getAllNewsInfoCount(searchTitle,startTime,endTime);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        return map;
    }

    @Override
    public int updateNewsById(Integer newsId, Integer status, String newsTitle, String newsContent) throws Exception {
        if (newsId == null) {
            throw new BusinessException("id不能为空");
        }
        News news = new News();
        news.setId(newsId);
        news.setStatus(status);
        news.setTitle(newsTitle);
        news.setContent(newsContent);
        return newsDao.updateNews(news);
    }

    @Override
    public int delNews(Integer id, Integer status) throws Exception {
        if (id == null || id == 0) {
            throw new BusinessException("id不能为空");
        }
        return newsDao.delNews(id, status);
    }

    @Override
    public int addNewsInfo(Integer status, String newsTitle, Integer staffId, String createdTime, String newsEndTime, String newsContent) throws Exception {
        News news = new News();
        if(StringUtils.isBlank(newsTitle) || StringUtils.isBlank(createdTime) || StringUtils.isBlank(newsEndTime) ||
                StringUtils.isBlank(newsContent) || staffId == 0 ){
            throw new BusinessException("不能为空");
        }
        news.setStatus(status);
        news.setTitle(newsTitle);
        news.setStaffId(staffId);
        news.setCreatedTime(createdTime);
        news.setEndTime(newsEndTime);
        news.setContent(newsContent);
        return newsDao.addNews(news);
    }

}
