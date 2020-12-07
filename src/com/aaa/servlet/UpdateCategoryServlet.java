package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.CategoryService;
import com.aaa.service.impl.CategoryServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateCategoryServlet")
public class UpdateCategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回参数

        ResponseDto responseDto = new ResponseDto();

        Integer categoryId = IntegerUtils.ToInteger(request.getParameter("categoryId"));
        String name = request.getParameter("name");
        String momo = request.getParameter("momo");
        Integer status= IntegerUtils.ToInteger(request.getParameter("status"));

        CategoryService categoryService = new CategoryServiceImpl();
        try {
            int len = categoryService.updateCategoryByCategoryId(categoryId, name, momo, status);
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
