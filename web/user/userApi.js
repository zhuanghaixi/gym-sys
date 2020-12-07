/*
 * 
 * @date 2019/11/20
 * 定义URl
**/
var userListUrl = "/GetUserAllServlet";
var updateUserUrl = "/UpdateUserServlet";
var delUserServletUrl = "/DelUserServlet";
//var initRoleUrl = "/GetUserAllRoleServlet";

/*
 * 初始化
**/
$(function () {
    userManage.initList();
    //userManage.initRole();
})
var userManage = {};
/*
 * 加载table
**/
userManage.initList = function () {
    $("#userList").bootstrapTable({
        url: userListUrl, //请求路径
        method: 'post', //请求方式(*)
        contentType: 'application/x-www-form-urlencoded', //使用from表单方式提交(*)
        toolbar: '#toolbar', //工具按钮的容器
        striped: true, //是否启用隔行变色
        cache: false, //使用是否缓存 默认为true,所以一般情况下需要设置一下为false (*)
        pagination: true, //是否显示分页(*)
        sortable: false, //使用启用排序
        sortOrder: 'desc', //排序方式
        queryParams: userManage.queryParams, //传递参数(*)
        queryParamsType: '',
        sidePagination: 'server', // 分页方式有两种 1.client 客户端分页  2.server分页
        pageNumber: 1, //初始化页数为第一页
        pageSize: 10, //默认每页加载行数
        pageList: [25, 50, 100], //每页可选择记录数
        strictSearch: true,
        showColumns: false, // 是否显示所有的列
        showRefresh: false, // 是否显示刷新按钮
        minimumCountColumns: 2, // 最少允许的列数
        clickToSelect: true, // 是否启用点击选中行
        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        showToggle: false, // 是否显示详细视图和列表视图的切换按钮
        cardView: false, // 是否显示详细视图
        detailView: false, // 是否显示父子表
        smartDisplay: false,
        onClickRow: function (e, row, element) {
            $(".success").removeClass("success");
            $(row).addClass("success");
        },
        responseHandler: function (result) {
            if (result != null) {
                return {
                    'total': result.data.count, //总条数
                    'rows': result.data.list //所有的数据
                };
            }
            return {
                'total': 0, //总条数
                'rows': [] //所有的数据
            }
        },
        //列表显示
        columns: [{
            field: 'id',
            title: "编号",
            visible: false
        }, {
            field: 'userId',
            title: "会员编号"
        },{
            field: 'cardId',
            title: "会员卡Id"
        },  {
            field: 'userName',
            title: "会员姓名"
        }, {
            field: 'idCard',
            title: "会员年龄",
            formatter: function (value) {
                var res = "-";
                if (value != "" && value != null) {
                    res = value.substr(6, 4) + "/" + value.substr(10, 2) + "/" + value.substr(12, 2);
                    res = userManage.getNow(res + " 00:00:00");
                }
                return res;
            }
        }, {
            field: 'phone',
            title: "会员手机号"
        }, {
            field: 'idCard',
            title: "会员身份证"
        }, {
                field: 'birthday',
                title: "会员生日"
        }, {
            field: 'address',
            title: "会员地址"
        }, {
            field: 'status',
            title: "会员状态",
            formatter: function (value) {
                switch (value) {
                    case 1 :
                        return "<span class='label label-info'>启用</span>";
                    case 2 :
                        return "<span class='label label-danger'>未启用</span>";
                }
            }
        }, {
            field: 'sex',
            title: "会员性别",
            formatter: function (value) {
                switch (value) {
                    case 1 :
                        return "男";
                    case 2 :
                        return "女";
                }
            }
        }, {
            field: 'createdTime',
            title: "创建时间",
        },  {
            field: 'momo',
            title: "备注",
        }, {
            field: 'operation',
            events: buttonOperateEvent,
            title: '操作',
            formatter: function (value, row, index) {
                return userManage.buttonOption(value, row, index);
            }
        }
        ]
    });
}
/*
 * 传递参数
**/
userManage.queryParams = function (params) {

    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize, //每页条数
        "searchUserId": $("#searchUserId").val(),
        "searchName": $("#searchName").val()
    }
}
/*
 * 按钮源 (按钮的操作事件)
**/
window.buttonOperateEvent = {
    'click .updateUser': function (e, value, row, index) {
        //row 这一行的数据
        userManage.update(row);
    },
    'click .delUser': function (e, value, row, index) {
        userManage.del(row);
    }
}
/*
 * 给bootstrapTable增加按钮
**/
userManage.buttonOption = function (value, row, index) {
    var returnButton = [];
    returnButton.push('<button class="btn btn-info updateUser">修改</button>');
    returnButton.push('<button class="btn btn-danger delUser">启用/禁用</button>');
    return returnButton.join('');
}
/*

 * 搜索
**/
userManage.search = function () {
    //bootstrapTable 刷新
    $("#userList").bootstrapTable('refresh');
}
/**
 * 修改
 * @param row
 */
userManage.update = function (row) {
    $("#userId").val(row.userId);
    $("#cardId").val(row.cardId);
    $("#userName").val(row.userName);
    $("#phone").val(row.phone);
    $("#status").val(row.status);
    $("#idCard").val(row.idCard);
    $("#birthday").val(row.birthday);
    $("#sex").val(row.sex);
    $("#address").val(row.address);
    // $("#createdTime").val(row.createdTime);
    $("#momo").val(row.momo);
    $("#myModal").modal('show');
}
/*
 *  删除
**/
userManage.del = function (row) {
    /**
     * 一般情况下删除要加confirm
     */
    Modal.confirm({
        msg: "确认当前操作"
    }).on(function (e) {
        if (e) {
            $.ajax({
                url: delUserServletUrl,
                type: 'post',
                data: {
                    "userId": row.userId,
                    "status":row.status
                },
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.status == 1) {
                        toastr['success']("删除成功");
                        $("#userList").bootstrapTable('refresh');
                    } else {
                        toastr['error']("删除失败");
                    }
                }
            })
        }
    })
}
/*
 * 关闭模态框
**/
$("#myModal").on('hide.bs.modal', function () {
    //移除上次的校验配置
    $("#userForm").data('bootstrapValidator').resetForm();
    $("#userForm")[0].reset();
})
/*
 * 确认按钮
**/
userManage.operate = function () {
    var bootstrapValidator = $("#userForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            url: updateUserUrl,
            type: 'post',
            data: {
                "userId": $("#userId").val(),
                "cardId":$("#cardId").val(),
                "userName": $("#userName").val(),
                "phone": $("#phone").val(),
                "idCard": $("#idCard").val(),
                "birthday":$("#birthday").val(),
                "status": $("#status").val(),
                "sex":$("#sex").val(),
                "address": $("#address").val(),
                "momo":$("#momo").val()
            },
            dataType: 'json',
            success: function (result) {
                if (result.status > 0) {
                    toastr['success']("操作成功");
                    $("#userList").bootstrapTable('refresh');
                    $("#myModal").modal('hide');
                } else {
                    toastr['error']("操作失败");
                }
            }
        })
    }
}

/*
 * 验证
**/
$("#userForm").bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        userName: {
            validators: {
                notEmpty: {
                    message: "会员姓名不能为空"
                }
            }
        }
    }
});
/*
 * 计算两个时间相差的年份
**/
userManage.getNow = function (startTime) {
    //结束时间
    var date2 = new Date();
    //时间差的毫秒数
    var date3 = date2.getTime() - new Date(startTime).getTime();
    //计算出相差天数
    var days = Math.floor(date3 / (24 * 3600 * 1000));
    return parseInt(days / 365);
}

