package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.StaffService;
import com.aaa.service.impl.StaffServiceImpl;
import com.aaa.util.DateUtils;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "UpdateStaffServlet")
public class UpdateStaffServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回参数
        ResponseDto responseDto = new ResponseDto();
        StaffService staffService = new StaffServiceImpl();
        Integer staffId = IntegerUtils.ToInteger(request.getParameter("staffId"));
        String staffName = request.getParameter("staffName");
        String phone = request.getParameter("phone");
        String idCard = request.getParameter("idCard");
        Integer status = IntegerUtils.ToInteger(request.getParameter("status"));
        Integer roleId = IntegerUtils.ToInteger(request.getParameter("roleId"));
        String momo = request.getParameter("momo");
        String address = request.getParameter("address");
        String createdTime =request.getParameter("createdTime");
        try {
            int len = staffService.updateStaffByStaffId(staffId, staffName, phone, idCard, status, roleId, momo, address,createdTime);
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
