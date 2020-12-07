package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.UserService;
import com.aaa.service.impl.UserServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddUserServlet")
public class AddUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        String userName = request.getParameter("userName");
        String userPhone = request.getParameter("userPhone");
        String userLevel = request.getParameter("userLevel");
        String userStatus = request.getParameter("userStatus");
        String staffId = request.getParameter("staffId");
        String staffName = request.getParameter("staffName");
        String birthday = request.getParameter("birthday");
        String amount = request.getParameter("amount");
        String idno = request.getParameter("idno");
        String userSex = request.getParameter("userSex");
        String area = request.getParameter("area");
        String address = request.getParameter("address");
        String momo = request.getParameter("momo");
        //返回参数
        ResponseDto responseDto = new ResponseDto();
        try {
            int len = userService.addUserInfo(userName,userPhone,userLevel,userStatus,staffId,staffName,birthday,amount,idno,userSex,area,address,momo);
            responseDto.setData(len);
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            responseDto.setMessage("登记成功");
            response.getWriter().print(new Gson().toJson(responseDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
