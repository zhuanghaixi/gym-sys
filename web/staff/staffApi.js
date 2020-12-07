
var staffListUrl = "/GetStaffAllServlet";
var updateStaffUrl = "/UpdateStaffServlet";
var delStaffServletUrl = "/DelStaffServlet";
var initRoleUrl = "/GetStaffAllRoleServlet";
var staffManage = "/AddStaffServlet";
/*
 * 初始化
**/
$(function () {
    staffManage.initList();
    staffManage.initRole();
})
var staffManage = {};
/*
 * 加载table
**/
staffManage.initList = function () {
    $("#staffList").bootstrapTable({
        url: staffListUrl, //请求路径
        method: 'post', //请求方式(*)
        contentType: 'application/x-www-form-urlencoded', //使用from表单方式提交(*)
        toolbar: '#toolbar', //工具按钮的容器
        striped: true, //是否启用隔行变色
        cache: false, //使用是否缓存 默认为true,所以一般情况下需要设置一下为false (*)
        pagination: true, //是否显示分页(*)
        sortable: false, //使用启用排序
        sortOrder: 'desc', //排序方式
        queryParams: staffManage.queryParams, //传递参数(*)
        queryParamsType: '',
        sidePagination: 'server', // 分页方式有两种 1.client 客户端分页  2.server分页
        pageNumber: 1, //初始化页数为第一页
        pageSize: 5, //默认每页加载行数
        pageList: [10, 25, 50, 100], //每页可选择记录数
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
            field: 'staffId',
            title: "员工编号"
        }, {
            field: 'staffName',
            title: "员工姓名"
        }, {
            field: 'idCard',
            title: "员工年龄",
            formatter: function (value) {
                var res = "-";
                if (value != "" && value != null) {
                    res = value.substr(6, 4) + "/" + value.substr(10, 2) + "/" + value.substr(12, 2);
                    res = staffManage.getNow(res + " 00:00:00");
                }
                return res;
            }
        }, {
            field: 'phone',
            title: "员工手机号"
        }, {
            field: 'idCard',
            title: "员工身份证"
        }, {
            field: 'address',
            title: "员工地址"
        }, {
            field: 'status',
            title: "状态",
            formatter: function (value) {
                switch (value) {
                    case 1 :
                        return "<span class='label label-info'>在职</span>";
                    case 2 :
                        return "<span class='label label-danger'>离职</span>";
                }
            }
        }, {
            field: 'roleName',
            title: "角色"
        }, {
            field: 'momo',
            title: "备注"
        }, {
            field: 'createdTime',
            title: "创建时间"
        }, {
            field: 'operation',
            events: buttonOperateEvent,
            title: '操作',
            formatter: function (value, row, index) {
                return staffManage.buttonOption(value, row, index);
            }
        }
        ]
    });
}
/*
 * 传递参数
**/
staffManage.queryParams = function (params) {
    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize, //每页条数
        "searchStaffId": $("#searchStaffId").val(),
        "searchName": $("#searchName").val()
    }
}
/*
 * 按钮源 (按钮的操作事件)
**/
window.buttonOperateEvent = {
    'click .updateStaff': function (e, value, row, index) {
        //row 这一行的数据
        staffManage.update(row);
    },
    'click .delStaff': function (e, value, row, index) {
        staffManage.del(row);
    }
}
/*
 * 给bootstrapTable增加按钮
**/
staffManage.buttonOption = function (value, row, index) {
    var returnButton = [];
    returnButton.push('<button class="btn btn-info updateStaff">修改</button>');
    returnButton.push('<button class="btn btn-danger delStaff">启用/禁用</button>');
    return returnButton.join('');
}
/*
 * 搜索
**/
staffManage.search = function () {
    //bootstrapTable 刷新
    $("#staffList").bootstrapTable('refresh');
}
/*
 * 修改
**/
staffManage.update = function (row) {
    $("#staffId").val(row.staffId);
    $("#staffName").val(row.staffName);
    $("#phone").val(row.phone);
    $("#idCard").val(row.idCard);
    $("#status").val(row.status);
    $("#roleId").val(row.roleId);
    $("#momo").val(row.momo);
    $("#address").val(row.address);
    $("#myModal").modal('show');
}
/*
 *  删除
**/
staffManage.del = function (row) {
    /**
     * 一般情况下删除要加confirm
     */
    Modal.confirm({
        msg: "确认当前操作"
    }).on(function (e) {
        if (e) {
            $.ajax({
                url: delStaffServletUrl,
                type: 'post',
                data: {
                    "staffId": row.staffId,
                    "status": row.status
                },
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.status == 1) {
                        toastr['success']("删除成功");
                        $("#staffList").bootstrapTable('refresh');
                    } else {
                        toastr['error']("删除失败");
                    }
                }
            })
        }
    })
}
/**
 * 添加
 */
staffManage.add = function () {
    $("#myModal").modal('show');

}
/*
 * 关闭模态框
**/
$("#myModal").on('hide.bs.modal', function () {
    //移除上次的校验配置
    $("#staffForm").data('bootstrapValidator').resetForm();
    $("#staffForm")[0].reset();
})
/*
 * 确认按钮
**/
staffManage.operate = function () {
    var bootstrapValidator = $("#staffForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            url: updateStaffUrl,
            type: 'post',
            data: {
                "staffId": $("#staffId").val(),
                "staffName": $("#staffName").val(),
                "phone": $("#phone").val(),
                "idCard": $("#idCard").val(),
                "status": $("#status").val(),
                "roleId": $("#roleId").val(),
                "momo": $("#momo").val(),
                "address": $("#address").val(),
                "createdTime": $("#createdTime").val()
            },
            dataType: 'json',
            success: function (result) {
                if (result.status > 0) {
                    toastr['success']("操作成功");
                    $("#staffList").bootstrapTable('refresh');
                    $("#myModal").modal('hide');
                } else {
                    toastr['error']("操作失败");
                }
            }
        })
    }
}

$("#myModal").on('hide.bs.modal', function () {
    //移除上次的校验配置
    $("#staffForm").data('bootstrapValidator').resetForm();
    $("#staffForm")[0].reset();
})

/**
 * 验证
 */
$("#staffForm").bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        staffName: {
            validators: {
                notEmpty: {
                    message: "员工姓名不能为空"
                }
            }
        },
        phone: {
            validators: {
                notEmpty: {
                    message: "员工手机号不能为空"
                },
                regexp:{
                    regexp: /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/,
                    message: "员工手机号不符合规则"
                }
            }
        },
        idCard: {
            validators: {
                notEmpty: {
                    message: "员工身份证不能为空"
                },
                regexp:{
                    regexp: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
                    message: "身份证号码不符合规则"
                }
            }
        },
        status: {
            validators: {
                notEmpty: {
                    message: "员工状态不能为空"
                }
            }
        }
    }
});
/*
 * 计算两个时间相差的年份
**/
staffManage.getNow = function (startTime) {
    //结束时间
    var date2 = new Date();
    //时间差的毫秒数
    var date3 = date2.getTime() - new Date(startTime).getTime();
    //计算出相差天数
    var days = Math.floor(date3 / (24 * 3600 * 1000));
    return parseInt(days/ 365);
}
/*
 * 加载员工角色
**/
staffManage.initRole = function () {
    $.ajax({
        url: initRoleUrl,
        type: 'get',
        dataType: 'json',
        success: function (result) {
            console.log(result);
            if (result.status == 1) {
                var res = result.data;
                for (var i = 0; i < res.length; i++) {
                    var opt = $("<option value='" + res[i].id + "'>" + res[i].roleName + "</option>");
                    $("#roleId").append(opt);
                }
            }
        }
    })
}