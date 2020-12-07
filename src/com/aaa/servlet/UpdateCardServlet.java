package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.CardService;
import com.aaa.service.impl.CardServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateCardServlet")
public class UpdateCardServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回参数
        ResponseDto responseDto = new ResponseDto();
        Integer id = Integer.parseInt(StringUtils.isBlank(request.getParameter("id")) ? "0" : request.getParameter("id"));
        Integer cardId = IntegerUtils.ToInteger(request.getParameter("cardId"));
        Integer userId = IntegerUtils.ToInteger(request.getParameter("userId"));
        Double amount = Double.parseDouble(request.getParameter("amount"));
        Integer credit = IntegerUtils.ToInteger(request.getParameter("credit"));
        Integer status = IntegerUtils.ToInteger(request.getParameter("status"));
        CardService cardService = new CardServiceImpl();
        try {
            int len = cardService.updateCardByCardId(cardId, userId, amount, credit, status);
            if (len > 0) {
                responseDto.setStatus(ResponseDto.SUCCESS_CODE);
                responseDto.setMessage("修改成功");
                responseDto.setData(len);
            } else {
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
