/*
 * 定义URl
**/
var ruleListUrl = "/GetAllRechargeRuleServlet";
var updateRuleUrl = "/UpdateRuleServlet";
var addruleUrl = "/AddruleServlet";
var delRuleServletUrl = "/DelRechargeRuleServlet";

/*
 * 初始化
**/
$(function () {
    ruleManage.initList();
})

var ruleManage = {};
/*
 * 加载table
**/
ruleManage.initList = function () {
    $("#ruleList").bootstrapTable({
        url: ruleListUrl, //请求路径
        method: 'post', //请求方式(*)
        contentType: 'application/x-www-form-urlencoded', //使用from表单方式提交(*)
        toolbar: '#toolbar', //工具按钮的容器
        striped: true, //是否启用隔行变色
        cache: false, //使用是否缓存 默认为true,所以一般情况下需要设置一下为false (*)
        pagination: true, //是否显示分页(*)
        sortable: false, //使用启用排序
        sortOrder: 'desc', //排序方式
        queryParams: ruleManage.queryParams, //传递参数(*)
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
            field: 'name',
            title: "规则名字"
        }, {
            field: 'coefficient',
            title: "充值系数"
        }, {
            field: 'status',
            title: "状态",
            formatter: function (value) {
                switch (value) {
                    case 1 :
                        return "<span class='label label-info'>启用</span>";
                    case 0 :
                        return "<span class='label label-danger'>禁用</span>";
                }
            }
        }, {
            field: 'createdTime',
            title: "创建时间"
        }, {
            field: 'endTime',
            title: "结束时间"
        }, {
            field: 'operation',
            events: buttonOperateEvent,
            title: '操作',
            formatter: function (value, row, index) {
                return ruleManage.buttonOption(value, row, index);
            }
        }
        ]
    });
}

/*
 * 传递参数
**/
ruleManage.queryParams = function (params) {
    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize, //每页条数
        "searchName": $("#searchName").val(),
        "searchStatus": $("#searchStatus").val()
    }
}
/*
 * 按钮源 (按钮的操作事件)
**/
window.buttonOperateEvent = {
    'click .updateRule': function (e, value, row, index) {
        //row 这一行的数据
        ruleManage.update(row);
    },
    'click .delRule': function (e, value, row, index) {
        ruleManage.del(row);
    }
}

/*
 * 给bootstrapTable增加按钮
**/
ruleManage.buttonOption = function (value, row, index) {
    var returnButton = [];
    returnButton.push('<button class="btn btn-info updateRule">修改</button>');
    returnButton.push('<button class="btn btn-danger delRule">启用/禁用</button>');
    return returnButton.join('');
}

/*
 * 搜索
**/
ruleManage.search = function () {
    //bootstrapTable 刷新
    $("#ruleList").bootstrapTable('refresh');
}

/*
 * 修改
**/
ruleManage.update = function (row) {
    $("#ruleId").val(row.id);
    $("#coff").val(row.coefficient);
    $("#ruleName").val(row.name);
    $("#createdTime").val(row.createdTime);
    $("#status").val(row.status);
    $("#endTime").val(row.endTime);
    $("#myModal").modal('show');
}

/*
 *  删除
**/
ruleManage.del = function (row) {
    /**
     * 一般情况下删除要加confirm
     */
    Modal.confirm({
        msg: "确认当前操作"
    }).on(function (e) {
        if (e) {
            $.ajax({
                url: delRuleServletUrl,
                type: 'post',
                data: {
                    "id": row.id,
                },
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.status == 1) {
                        toastr['success']("删除成功");
                        $("#ruleList").bootstrapTable('refresh');
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
    $("#ruleForm").data('bootstrapValidator').resetForm();
    $("#ruleForm")[0].reset();
})


/*
 * 确认按钮
**/
ruleManage.operate = function () {
    var bootstrapValidator = $("#ruleForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            url: updateRuleUrl,
            type: 'post',
            data: {
                "id": $("#ruleId").val(),
                "name": $("#ruleName").val(),
                "coefficient": $("#coff").val(),
                "createdTime": $("#createdTime").val(),
                "endTime": $("#endTime").val(),
                "status": $("#status").val()
            },
            dataType: 'json',
            success: function (result) {
                if (result.status > 0) {
                    toastr['success']("操作成功");
                    $("#ruleList").bootstrapTable('refresh');
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
$("#ruleForm").bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        ruleName: {
            validators: {
                notEmpty: {
                    message: "新闻/公告标题不能为空"
                }
            }
        },
        coff: {
            validators: {
                notEmpty: {
                    message: "新闻/公告内容不能为空"
                }
            }
        },
        createdTime: {
            validators: {
                notEmpty: {
                    message: "开始时间不能为空"
                }
            }
        },
        endTime: {
            validators: {
                notEmpty: {
                    message: "结束时间不能为空"
                }
            }
        }
    }
});
/*
 * 计算两个时间相差的年份
**/
ruleManage.getNow = function (startTime) {
    //结束时间
    var date2 = new Date();
    //时间差的毫秒数
    var date3 = date2.getTime() - new Date(startTime).getTime();
    //计算出相差天数
    var days = Math.floor(date3 / (24 * 3600 * 1000));
    return parseInt(days / 365);
}

/*
 * 新增充值规则
**/
ruleManage.add = function () {
    var date = new Date();
    $("#createdTime").val(date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate());
    $("#myModal").modal('show');


}
