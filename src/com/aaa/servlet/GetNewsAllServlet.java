package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.NewsService;
import com.aaa.service.StaffService;
import com.aaa.service.impl.NewsServiceImpl;
import com.aaa.service.impl.StaffServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetNewsAllServlet")
public class GetNewsAllServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NewsService newsService = new NewsServiceImpl();
        Integer pageNumber = IntegerUtils.ToInteger(request.getParameter("pageNumber"));
        Integer pageSize = IntegerUtils.ToInteger(request.getParameter("pageSize"));
        String searchTitle = request.getParameter("searchTitle");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        try {
            //返回参数
            ResponseDto responseDto = new ResponseDto();
            responseDto.setData(newsService.getAllNewsInfo(pageNumber,pageSize,searchTitle,startTime,endTime));
            responseDto.setMessage("请求成功");
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
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
