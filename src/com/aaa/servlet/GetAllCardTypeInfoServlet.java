package com.aaa.servlet;

import com.aaa.entity.CardType;
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
import java.util.List;

@WebServlet(name = "GetAllCardTypeInfoServlet")
public class GetAllCardTypeInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CardService cardService = new CardServiceImpl();
        List<CardType> cardTypeList = cardService.getAllCardTypeInfo();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(ResponseDto.SUCCESS_CODE);
        responseDto.setData(cardTypeList);
        response.getWriter().print(new Gson().toJson(responseDto));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
