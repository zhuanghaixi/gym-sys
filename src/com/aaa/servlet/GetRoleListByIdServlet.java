package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.entity.TreeMenu;
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
import java.util.List;

@WebServlet(name = "GetRoleListByIdServlet")
public class GetRoleListByIdServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RoleService roleService = new RoleServiceImpl();
        Integer roleId = IntegerUtils.ToInteger(request.getParameter("id"));
        try {
            List<TreeMenu> list = roleService.getMenuList(roleId);
            Gson gson = new Gson();
            //返回参数
            ResponseDto responseDto = new ResponseDto();
            responseDto.setMessage("请求成功");
            responseDto.setData(list);
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            response.getWriter().print(gson.toJson(responseDto));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
