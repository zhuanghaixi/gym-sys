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

@WebServlet(name = "DelRechargeRuleServlet")
public class DelRechargeRuleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RechargeRuleService rechargeRuleService = new RechargeRuleServiceImpl();
        Integer id = IntegerUtils.ToInteger(request.getParameter("id"));
        int len = rechargeRuleService.delete(id);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(ResponseDto.SUCCESS_CODE);
        responseDto.setData(len);
        responseDto.setMessage("成功");
        response.getWriter().print(new Gson().toJson(responseDto));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
