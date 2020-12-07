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

@WebServlet(name = "DelNewsServlet")
public class DelNewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = IntegerUtils.ToInteger(request.getParameter("id"));
        Integer status = IntegerUtils.ToInteger(request.getParameter("status"));
        NewsService newsService =new NewsServiceImpl();
        //返回参数
        ResponseDto responseDto = new ResponseDto();
        try {
            int len = newsService.delNews(id,status);
            if (len > 0){
                responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            }else {
                responseDto.setStatus(ResponseDto.FAILURE_CODE);
            }
            response.getWriter().print(new Gson().toJson(responseDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
