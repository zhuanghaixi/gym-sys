package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.UserService;
import com.aaa.service.impl.UserServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetUserAllServlet")
public class GetUserAllServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        Integer pageNumber = IntegerUtils.ToInteger(request.getParameter("pageNumber"));
        Integer pageSize = IntegerUtils.ToInteger(request.getParameter("pageSize"));
        String searchUserId = request.getParameter("searchUserId");
        String searchName = request.getParameter("searchName");
        try {
            //返回参数
            ResponseDto responseDto = new ResponseDto();
            responseDto.setData(userService.getAllUserInfo(pageNumber,pageSize,searchUserId,searchName));
            responseDto.setMessage("请求成功");
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            response.getWriter().print(new Gson().toJson(responseDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
