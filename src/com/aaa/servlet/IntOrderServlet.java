package com.aaa.servlet;

import com.aaa.entity.Order;
import com.aaa.entity.ResponseDto;
import com.aaa.service.OrderService;
import com.aaa.service.impl.OrderServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "IntOrderServlet")
public class IntOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResponseDto responseDto =new ResponseDto();
        String orderId =request.getParameter("orderNumber");
        String cardType =request.getParameter("cardType");
        String amount = request.getParameter("amount");
        Integer credit = IntegerUtils.ToInteger(request.getParameter("credit"));//消费积分
        String momo =request.getParameter("momo");
        String createdTime = request.getParameter("createdTime");
        String code =request.getParameter("code");
        String num = request.getParameter("num");
        Double pay = Double.parseDouble(request.getParameter("pay"));
        Integer cardId = IntegerUtils.ToInteger(request.getParameter("cardId"));
        String[] codeArray=code.split(",");
        String[] numArray=num.split(",");
        Order order=new Order();
        order.setOrderId(orderId);
        order.setCardId(cardId);
        order.setPrice(pay);
        order.setPay(pay);
        order.setCardType(cardType);
        order.setCredit(credit);
        order.setMomo(momo);
        order.setCreatedTime(createdTime);
        OrderService orderService=new OrderServiceImpl();
        for (int i = 1; i <codeArray.length; i++) {
            Map map = orderService.getGoodsNumForCode(codeArray[i]);
            int n = Integer.parseInt(map.get("stock").toString());
            int n2=Integer.parseInt(numArray[i]);

            if(n<n2){
                responseDto.setStatus(3);
                response.getWriter().print(new Gson().toJson(responseDto));
                return ;
            }
        }
        int n=0;
        for (int i = 1; i <codeArray.length ; i++) {
            n = orderService.reduceMargin(codeArray[i],numArray[i]);
        }
        int  a=orderService.addOrder(order);
        int  b=orderService.updateCardAmount(cardId,pay);

        if (n>0&&a>0&&b>0){
            responseDto.setStatus(a);
            response.getWriter().print(new Gson().toJson(responseDto));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request, response);
    }
}
