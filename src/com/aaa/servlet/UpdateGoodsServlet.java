package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.GoodsService;
import com.aaa.service.impl.GoodsServiceImpl;

import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateGoodsServlet")
public class UpdateGoodsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回参数

        ResponseDto responseDto = new ResponseDto();

        Integer goodsid = IntegerUtils.ToInteger(request.getParameter("goodsid"));
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        Integer status= IntegerUtils.ToInteger(request.getParameter("status"));
        Integer stock = IntegerUtils.ToInteger(request.getParameter("stock"));
        Integer unitId= IntegerUtils.ToInteger(request.getParameter("unitId"));
        Integer price = IntegerUtils.ToInteger(request.getParameter("price"));
        Integer categoryId = IntegerUtils.ToInteger(request.getParameter("categoryId"));
        GoodsService goodsService = new GoodsServiceImpl();
        try {
            int len = goodsService.updateGoodsByGoodsId(goodsid, name, code, status, stock, unitId, price, categoryId);
            if (len > 0) {
                responseDto.setStatus(ResponseDto.SUCCESS_CODE);
                responseDto.setMessage("修改成功");
                responseDto.setData(len);
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
