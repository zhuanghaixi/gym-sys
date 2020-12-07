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

@WebServlet(name = "AddNewsServlet")
public class AddNewsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NewsService newsService = new NewsServiceImpl();
        String newsTitle1 = request.getParameter("newsTitle1");
        Integer staffId = IntegerUtils.ToInteger(request.getParameter("staffId"));
        Integer status1 = IntegerUtils.ToInteger(request.getParameter("status1"));
        String createdTime1 = request.getParameter("createdTime1");
        String newsEndTime1 = request.getParameter("newsEndTime1");
        String newsContent1 = request.getParameter("newsContent1");
        ResponseDto responseDto = new ResponseDto();
        try {
            int len = newsService.addNewsInfo(status1,newsTitle1,staffId,createdTime1,newsEndTime1,newsContent1);
            responseDto.setData(len);
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            responseDto.setMessage("成功");
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
