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

@WebServlet(name = "DelStaffServlet")
public class DelStaffServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer staffId = IntegerUtils.ToInteger(request.getParameter("staffId"));
        Integer status = IntegerUtils.ToInteger(request.getParameter("status"));
        StaffService staffService = new StaffServiceImpl();
        //返回参数,智慧工地，水务智慧,装配式管理，不同于OA系统
        ResponseDto responseDto = new ResponseDto();
        try {
            int len = staffService.delStaff(staffId,status);
            if (len > 0){
                responseDto.setStatus(ResponseDto.SUCCESS_CODE);
            }else {
                responseDto.setStatus(ResponseDto.FAILURE_CODE);
            }
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
