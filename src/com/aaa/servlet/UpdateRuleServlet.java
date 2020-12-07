package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.RechargeRuleService;
import com.aaa.service.impl.RechargeRuleServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateRuleServlet")
public class UpdateRuleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RechargeRuleService rechargeRuleService = new RechargeRuleServiceImpl();
        int id = IntegerUtils.ToInteger(request.getParameter("id"));
        String name = request.getParameter("name");
        Double coefficient = Double.parseDouble(request.getParameter("coefficient"));
        String createdTime = request.getParameter("createdTime");
        String endTime = request.getParameter("endTime");
        int status = IntegerUtils.ToInteger(request.getParameter("status"));
        //参数返回
        ResponseDto responseDto = new ResponseDto();
        try {
            int len = rechargeRuleService.update(id,name,coefficient,createdTime,endTime,status);
            responseDto.setData(len);
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
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
