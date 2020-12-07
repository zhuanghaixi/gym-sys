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

@WebServlet(name = "DelUserServlet")
public class DelUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = IntegerUtils.ToInteger(request.getParameter("userId"));
        Integer status = IntegerUtils.ToInteger(request.getParameter("status"));
        UserService staffService = new UserServiceImpl();
        //返回参数
        ResponseDto responseDto = new ResponseDto();
        try {
            int len = staffService.delUser(userId,status);
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
