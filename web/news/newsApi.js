/* 

 * 定义URl
**/
var newsListUrl = "/GetNewsAllServlet";
var updateNewsUrl = "/UpdateNewsServlet";
var addNewsUrl = "/AddNewsServlet";
var delNewsServletUrl = "/DelNewsServlet";

/* 

 * 初始化
**/
$(function () {
    newsManage.initList();
})

var newsManage = {};
/*
 * 加载table
**/
newsManage.initList = function () {
    $("#newsList").bootstrapTable({
        url: newsListUrl, //请求路径
        method: 'post', //请求方式(*)
        contentType: 'application/x-www-form-urlencoded', //使用from表单方式提交(*)
        toolbar: '#toolbar', //工具按钮的容器
        striped: true, //是否启用隔行变色
        cache: false, //使用是否缓存 默认为true,所以一般情况下需要设置一下为false (*)
        pagination: true, //是否显示分页(*)
        sortable: false, //使用启用排序
        sortOrder: 'desc', //排序方式
        queryParams: newsManage.queryParams, //传递参数(*)
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
            field: 'title',
            title: "新闻/公告标题"
        }, {
            field: 'content',
            title: "新闻/公告内容"
        }, {
            field: 'status',
            title: "状态",
            formatter: function (value) {
                switch (value) {
                    case 1 :
                        return "<span class='label label-info'>启用</span>";
                    case 2 :
                        return "<span class='label label-danger'>禁用</span>";
                }
            }
        }, {
            field: 'staffName',
            title: "员工"
        }, {
            field: 'createdTime',
            title: "创建时间",
            formatter:function (value) {
                return value.substr(0,value.length -2)
            }
        }, {
            field: 'endTime',
            title: "结束时间",
            formatter:function (value) {
                return value.substr(0,value.length -2)
            }
        }, {
            field: 'operation',
            events: buttonOperateEvent,
            title: '操作',
            formatter: function (value, row, index) {
                return newsManage.buttonOption(value, row, index);
            }
        }
        ]
    });
}

/*
 * 传递参数
**/
newsManage.queryParams = function (params) {
    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize, //每页条数
        "searchTitle": $("#searchTitle").val(),
        "startTime": $("#startTime").val(),
        "endTime": $("#endTime").val()
    }
}
/*
 * 按钮源 (按钮的操作事件)
**/
window.buttonOperateEvent = {
    'click .updateNews': function (e, value, row, index) {
        //row 这一行的数据
        newsManage.update(row);
    },
    'click .delnews': function (e, value, row, index) {
        newsManage.del(row);
    }
}

/*
 * 给bootstrapTable增加按钮
**/
newsManage.buttonOption = function (value, row, index) {
    var returnButton = [];
    returnButton.push('<button class="btn btn-info updateNews">修改</button>');
    returnButton.push('<button class="btn btn-danger delnews">启用/禁用</button>');
    return returnButton.join('');
}

/*
 * 搜索
**/
newsManage.search = function () {
    //bootstrapTable 刷新
    $("#newsList").bootstrapTable('refresh');
}

/*
 * 修改
**/
newsManage.update = function (row) {
    $("#id").val(row.id);
    $("#newsTitle").val(row.title);
    $("#createdTime").val(row.createdTime);
    $("#staffName").val(row.staffName);
    $("#status").val(row.status);
    $("#newsEndTime").val(row.endTime);
    $("#newsContent").val(row.content);
    $("#myModal").modal('show');
}

/*
 *  删除
**/
newsManage.del = function (row) {
    /**
     * 一般情况下删除要加confirm
     */
    Modal.confirm({
        msg: "确认当前操作"
    }).on(function (e) {
        if (e) {
            $.ajax({
                url: delNewsServletUrl,
                type: 'post',
                data: {
                    "id": row.id,
                    "status":row.status
                },
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.status == 1) {
                        toastr['success']("删除成功");
                        $("#newsList").bootstrapTable('refresh');
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
    $("#newsForm").data('bootstrapValidator').resetForm();
    $("#newsForm")[0].reset();
})


/*
 * 确认按钮
**/
newsManage.operate = function () {
    var bootstrapValidator = $("#newsForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            url: updateNewsUrl,
            type: 'post',
            data: {
                "id": $("#id").val(),
                "status": $("#status").val(),
                "newsTitle": $("#newsTitle").val(),
                "newsTContent": $("#newsContent").val()
            },
            dataType: 'json',
            success: function (result) {
                if (result.status > 0) {
                    toastr['success']("操作成功");
                    $("#newsList").bootstrapTable('refresh');
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
$("#newsForm").bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        newsTitle: {
            validators: {
                notEmpty: {
                    message: "新闻/公告标题不能为空"
                }
            }
        },
        newsContent: {
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
        newsEndTime: {
            validators: {
                notEmpty: {
                    message: "结束时间不能为空"
                }
            }
        }
    }
});
/*
 * 
 * @date 2019/11/20
 * 计算两个时间相差的年份
**/
newsManage.getNow = function (startTime) {
    //结束时间
    var date2 = new Date();
    //时间差的毫秒数
    var date3 = date2.getTime() - new Date(startTime).getTime();
    //计算出相差天数
    var days = Math.floor(date3 / (24 * 3600 * 1000));
    return parseInt(days / 365);
}

/*
 * 新增新闻
**/
newsManage.add = function () {
    var userName = window.localStorage.getItem("userName");
    var userId = window.localStorage.getItem("userId");
    $("#staffName1").val(userName);
    $("#staffId").val(userId);
    $("#myModal1").modal('show');
}
/*
 * 第二个模态框 确认按钮
**/
newsManage.operate1 = function () {
    var bootstrapValidator = $("#newsForm1").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            url: addNewsUrl,
            type: 'post',
            data: {
                "newsTitle1": $("#newsTitle1").val(),
                "staffId": $("#staffId").val(),
                "status1": $("#status1").val(),
                "createdTime1": $("#createdTime1").val(),
                "newsEndTime1": $("#newsEndTime1").val(),
                "newsContent1": $("#newsContent1").val()
            },
            dataType: 'json',
            success: function (result) {
                if (result.status > 0) {
                    toastr['success']("操作成功");
                    $("#newsList").bootstrapTable('refresh');
                    $("#myModal1").modal('hide');
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
$("#newsForm1").bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        newsTitle: {
            validators: {
                notEmpty: {
                    message: "新闻/公告标题不能为空"
                }
            }
        },
        newsContent: {
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
        newsEndTime: {
            validators: {
                notEmpty: {
                    message: "结束时间不能为空"
                }
            }
        }
    }
});
/*
 * 关闭模态框
**/
$("#myModal1").on('hide.bs.modal', function () {
    //移除上次的校验配置
    $("#newsForm1").data('bootstrapValidator').resetForm();
    $("#newsForm1")[0].reset();
})