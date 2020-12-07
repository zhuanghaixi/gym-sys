
//定义
var loginManager = {};
loginManager.login = function () {
    $.ajax({
        url:'/LoginServlet',
        type:'post',
        data:{
            "loginName":$("#loginName").val(),
            "loginPwd":$("#loginPwd").val()
        },
        dataType:'json',
        success:function (result) {
            if(result != null){
                toastr['success']("登陆成功");
                window.location.href = "/index/views/main.html";
                /**
                 * localStorage 相当于前端的小型数据库 当登陆成功的时候 我们保存一个roleId 在index.js中去获取
                 */
                window.localStorage.setItem("roleId",result.roleId);
                window.localStorage.setItem("userName",result.staffName);
                window.localStorage.setItem("userId",result.id);
            }else {
                toastr['error']("登陆失败");
            }
        }
    })
}