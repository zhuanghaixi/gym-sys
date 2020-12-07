package com.aaa.servlet;

import com.aaa.entity.Order;
import com.aaa.entity.ResponseDto;
import com.aaa.service.OrderService;
import com.aaa.service.StaffService;
import com.aaa.service.impl.OrderServiceImpl;
import com.aaa.service.impl.StaffServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetOrderRecordListServlet")
public class GetOrderRecordListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        Integer pageNumber = IntegerUtils.ToInteger(request.getParameter("pageNumber"));
        Integer pageSize = IntegerUtils.ToInteger(request.getParameter("pageSize"));
        String searchOrderId = request.getParameter("searchOrderId");
        String searchCardId = request.getParameter("searchCardId");

        try {
            //返回参数
            ResponseDto responseDto = new ResponseDto();
            responseDto.setData(orderService.getAllOrder(pageNumber,pageSize,searchOrderId,searchCardId));
            responseDto.setMessage("请求成功");
            responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            response.getWriter().print(new Gson().toJson(responseDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request, response);
    }
}
