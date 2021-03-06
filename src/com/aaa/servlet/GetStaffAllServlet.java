package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.StaffService;
import com.aaa.service.impl.StaffServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** 
**/
@WebServlet(name = "GetStaffAllServlet")
public class GetStaffAllServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StaffService staffService = new StaffServiceImpl();
        Integer pageNumber = IntegerUtils.ToInteger(request.getParameter("pageNumber"));
        Integer pageSize = IntegerUtils.ToInteger(request.getParameter("pageSize"));
        String searchStaffId = request.getParameter("searchStaffId");
        String searchName = request.getParameter("searchName");
        try {
            //返回参数
            ResponseDto responseDto = new ResponseDto();
            responseDto.setData(staffService.getAllStaffInfo(pageNumber,pageSize,searchStaffId,searchName));
            responseDto.setMessage("请求成功");
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
