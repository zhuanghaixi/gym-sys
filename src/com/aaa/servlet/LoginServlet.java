package com.aaa.servlet;

import com.aaa.service.StaffService;
import com.aaa.service.impl.StaffServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 登陆
 **/
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String loginName = request.getParameter("loginName");
        String loginPwd = request.getParameter("loginPwd");
        StaffService staffService = new StaffServiceImpl();
        Map<String,Object> map = staffService.login(loginName,loginPwd);
        Gson gson = new Gson();
        response.getWriter().print(gson.toJson(map));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
