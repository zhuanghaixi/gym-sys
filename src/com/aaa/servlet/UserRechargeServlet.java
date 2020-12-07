package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.CardService;
import com.aaa.service.impl.CardServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserRechargeServlet")
public class UserRechargeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userCardId = request.getParameter("userCardId");
        String credit = request.getParameter("credit");
        String amount = request.getParameter("amount");
        String cardAmount = request.getParameter("cardAmount");
        String rechargeRule = request.getParameter("rechargeRule");
        String staffId = request.getParameter("staffId");
        String momo = request.getParameter("momo");
        String sendAmount = request.getParameter("sendAmount");
        String userCredit = request.getParameter("userCredit");
        CardService cardService = new CardServiceImpl();
        cardService.userRecharge(userCardId,credit,amount,cardAmount,rechargeRule,staffId,momo,sendAmount,userCredit);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(ResponseDto.SUCCESS_CODE);
        response.getWriter().print(new Gson().toJson(responseDto));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
