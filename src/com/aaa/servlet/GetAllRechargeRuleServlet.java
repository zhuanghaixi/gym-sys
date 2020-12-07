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
import java.util.Map;

@WebServlet(name = "GetAllRechargeRuleServlet")
public class GetAllRechargeRuleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回参数
        ResponseDto responseDto = new ResponseDto();
        String searchName = request.getParameter("searchName");
        String status = request.getParameter("searchStatus");
        Integer pageNumber = IntegerUtils.ToInteger(request.getParameter("pageNumber"));
        Integer pageSize = IntegerUtils.ToInteger(request.getParameter("pageSize"));
        RechargeRuleService rechargeRuleService = new RechargeRuleServiceImpl();
        try {
            Map<String, Object> map = rechargeRuleService.findAllList(searchName,status,pageNumber,pageSize);
            responseDto.setMessage("成功");
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            responseDto.setData(map);
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
