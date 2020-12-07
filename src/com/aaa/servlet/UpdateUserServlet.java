package com.aaa.servlet;

import com.aaa.entity.ResponseDto;
import com.aaa.service.UserService;
import com.aaa.service.impl.UserServiceImpl;
import com.aaa.util.IntegerUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回参数
        ResponseDto responseDto = new ResponseDto();
        Integer id = Integer.parseInt(StringUtils.isBlank(request.getParameter("id")) ? "0" : request.getParameter("id") );
        Integer userId = IntegerUtils.ToInteger(request.getParameter("userId"));
        Integer cardId = IntegerUtils.ToInteger(request.getParameter("cardId"));
        String userName = request.getParameter("userName");
        String phone = request.getParameter("phone");
        Integer status = IntegerUtils.ToInteger(request.getParameter("status"));
        String idCard = request.getParameter("idCard");
        Integer sex = IntegerUtils.ToInteger(request.getParameter("sex"));
        String address = request.getParameter("address");
        String birthday = request.getParameter("birthday");
        String momo = request.getParameter("momo");
        UserService userService = new UserServiceImpl();
        try {
            int len = userService.updateUserByUserId(userId,cardId, userName, phone, status,idCard,birthday,sex, address,momo);
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
