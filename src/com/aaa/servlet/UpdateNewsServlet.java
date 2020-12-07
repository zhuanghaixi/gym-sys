package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.NewsService;
import com.aaa.service.impl.NewsServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateNewsServlet")
public class UpdateNewsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NewsService newsService = new NewsServiceImpl();
        Integer newsId = IntegerUtils.ToInteger(request.getParameter("newsId"));
        Integer status = IntegerUtils.ToInteger(request.getParameter("status"));
        String newsTitle = request.getParameter("newsTitle");
        String newsTContent = request.getParameter("newsTContent");
        //返回参数
        ResponseDto responseDto = new ResponseDto();
        try {
            int len = newsService.updateNewsById(newsId,status,newsTitle,newsTContent);
            responseDto.setMessage("成功");
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            responseDto.setData(len);
            response.getWriter().print(new Gson().toJson(responseDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
