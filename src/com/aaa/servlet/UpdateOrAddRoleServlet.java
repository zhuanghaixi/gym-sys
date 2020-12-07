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

@WebServlet(name = "UpdateOrAddRoleServlet")
public class UpdateOrAddRoleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回参数
        ResponseDto responseDto = new ResponseDto();

        RoleService roleService = new RoleServiceImpl();
        Integer id = IntegerUtils.ToInteger(request.getParameter("id"));
        String roleName = request.getParameter("roleName");
        String description = request.getParameter("description");
        Integer status = IntegerUtils.ToInteger(request.getParameter("status"));
        try {
            int len = roleService.updateOrAddRole(id,roleName,description,status);
            responseDto.setMessage("请求成功");
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
