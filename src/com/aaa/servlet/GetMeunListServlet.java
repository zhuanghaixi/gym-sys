package com.aaa.servlet;

import com.aaa.entity.Menu;
import com.aaa.entity.ResponseDto;
import com.aaa.service.MenuService;
import com.aaa.service.impl.MenuServiceImpl;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetMeunListServlet")
public class GetMeunListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MenuService menuService = new MenuServiceImpl();
        Integer roleId = Integer.parseInt(StringUtils.isBlank(request.getParameter("roleId")) ?
                                                                    "0" : request.getParameter("roleId"));
        try {
            List<Menu> lists = menuService.getMenuList(roleId);
            //返回参数
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            responseDto.setMessage("查询成功");
            responseDto.setData(lists);
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
