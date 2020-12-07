package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.RoleService;
import com.aaa.service.impl.RoleServiceImpl;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;

@WebServlet(name = "UpdateMenuListServlet")
public class UpdateMenuListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RoleService roleService = new RoleServiceImpl();
        Integer roleId = StringUtils.isBlank(request.getParameter("roleId")) ? 0 : Integer.parseInt(request.getParameter("roleId"));
        String resources = request.getParameter("getNodeList");
        String[ ]  strings = null;
        String t = "[ ]";
        if (!t.equals(resources)){
            strings = resources.substring(1, resources.length() -1 ).split(",");
        }
        Integer [ ] intarray = null;
        if (strings != null && strings.length >0){
            intarray = new Integer[strings.length];
            for (int i=0; i < strings.length;i++  ){
                intarray[i] = Integer.parseInt(strings[i]);
            }
        }
        try {
            int res = roleService.updateMenuList(roleId,intarray);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            responseDto.setData(res > 0 ? 1:0 );
            responseDto.setMessage("成功");
            response.getWriter().print(new Gson().toJson(responseDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
