package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.RoleService;
import com.aaa.service.impl.RoleServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DelRoleServlet")
public class DelRoleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回参数
        ResponseDto responseDto = new ResponseDto();
        Integer id = IntegerUtils.ToInteger(request.getParameter("id"));
        Integer status = IntegerUtils.ToInteger(request.getParameter("status"));

        RoleService roleService = new RoleServiceImpl();
        try {
            int len = roleService.delRole(id, status);
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
