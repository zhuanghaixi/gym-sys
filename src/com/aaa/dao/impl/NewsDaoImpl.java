package com.aaa.dao.impl;

import com.aaa.dao.NewsDao;
import com.aaa.dao.StaffDao;
import com.aaa.entity.News;
import com.aaa.entity.Staff;
import com.aaa.util.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class NewsDaoImpl implements NewsDao {
    private BaseDao baseDao = new BaseDao();


    @Override
    public List<News> getAllNewsInfo(Integer pageNumber, Integer pageSize, String searchTitle, String startTime, String endTime) {
        String sql = "select n.*,s.staffName staffName from news n left join staff s on n.staffId = s.id where 1=1 ";
        if (StringUtils.isNotBlank(searchTitle)) {
            searchTitle = "'%" + searchTitle + "%'";
            sql += " and n.title like " + searchTitle;
        }
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            sql += " and n.createdTime  between '" + startTime + "' and '" + endTime + "'";
        } else {
            if (StringUtils.isNotBlank(startTime)) {
                sql += " and n.createdTime > '" + startTime + "'";
            }
            if (StringUtils.isNotBlank(endTime)) {
                sql += " and n.createdTime < '" + endTime + "'";
            }
        }
        sql += " order by createdTime desc limit ?,?";
        Object[] params = {pageNumber, pageSize};
        List<News> newsList = baseDao.queryList(sql, params, News.class);
        return newsList;
    }

    @Override
    public int getAllNewsInfoCount(String searchTitle, String startTime, String endTime) {
        String sql = "select count(1) len from news where 1=1 ";
        if (StringUtils.isNotBlank(searchTitle)) {
            searchTitle = "'%" + searchTitle + "%'";
            sql += " and title like " + searchTitle;
        }
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            sql += " and  createdTime  between '" + startTime + "' and '" + endTime + "'";
        } else {
            if (StringUtils.isNotBlank(startTime)) {
                sql += "  and createdTime > '" + startTime + "'";
            }
            if (StringUtils.isNotBlank(endTime)) {
                sql += "  and createdTime < '" + endTime + "'";
            }
        }
        List<Map<String, Object>> newsList = baseDao.executeQuery(sql, null);
        if (newsList != null && newsList.size() > 0) {
            return Integer.parseInt(newsList.get(0).get("len") + "");
        }
        return 0;
    }

    @Override
    public int updateNews(News news) {
        String sql = "update news set status = ?,title = ?,content = ? where id = ?";
        Object[] params = {news.getStatus(),news.getTitle(),news.getContent(),news.getId()};
        return baseDao.executeUpdate(sql,params);
    }

    @Override
    public int delNews(Integer id, Integer status) {
        status = status == 1 ? 2 : 1;
        String sql = "update news set status = ? where id = " + id;
        Object[] params = {status};
        int len = baseDao.executeUpdate(sql, params);
        return len;
    }

    @Override
    public int addNews(News news) {
        String sql = "insert into news (title,staffId,status,createdTime,endTime,content) values (?,?,?,?,?,?)";
        Object[] params = {news.getTitle(),news.getStaffId(),news.getStatus(),news.getCreatedTime(),news.getEndTime(),news.getContent()};
        return baseDao.executeUpdate(sql,params);
    }

    @Override
    public String getLastNewsId() {
        String  sql = "select  staffId from staff order by id desc limit 1";
        List<Map<String, Object>> maps = baseDao.executeQuery(sql, null);
        if (maps != null && maps.size() > 0) {
            String staffId = maps.get(0).get("staffId") + "";
            return staffId;
        }
        return null;
    }

}
