/*
 * 定义URl
**/
var categoryListUrl = "/GetCategoryAllServlet";
var updateCategoryUrl = "/UpdateCategoryServlet";

var delCategoryServletUrl = "/DelCategoryServlet";

var initCategoryUrl = "/GetCategoryAllCategoryServlet";

/*
 * 初始化
**/
$(function () {
    categoryManage.initList();
    categoryManage.initCategory();
})
var categoryManage = {};
/*
 * 加载table
**/
categoryManage.initList = function () {
    $("#categoryList").bootstrapTable({
        url: categoryListUrl, //请求路径
        method: 'post', //请求方式(*)
        contentType: 'application/x-www-form-urlencoded', //使用from表单方式提交(*)
        toolbar: '#toolbar', //工具按钮的容器
        striped: true, //是否启用隔行变色
        cache: false, //使用是否缓存 默认为true,所以一般情况下需要设置一下为false (*)
        pagination: true, //是否显示分页(*)
        sortable: false, //使用启用排序
        sortOrder: 'desc', //排序方式
        queryParams: categoryManage.queryParams, //传递参数(*)
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
            field: 'categoryId',
            title: "类别号"
        }, {
            field: 'name',
            title: "类名"
        }, {
            field: 'momo',
            title: "备注"
        }, {
            field: 'status',
            title: "状态",
            formatter: function (value) {
                switch (value) {
                    case 0 :
                        return "<span class='label label-info'>在售</span>";
                    case 1 :
                        return "<span class='label label-danger'>下架</span>";
                }
            }

        },  {
            field: 'operation',
            events: buttonOperateEvent,
            title: '操作',
            formatter: function (value, row, index) {
                return categoryManage.buttonOption(value, row, index);
            }
        }
        ]
    });
}
/*
 * 传递参数
**/
categoryManage.queryParams = function (params) {
    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize, //每页条数
        "searchcategoryId": $("#searchcategoryId").val(),
        "searchName": $("#searchName").val()
    }
}
/*
 * 按钮源 (按钮的操作事件)
**/
window.buttonOperateEvent = {
    'click .updateCategory': function (e, value, row, index) {
        //row 这一行的数据
        categoryManage.update(row);
    },
    'click .delCategory': function (e, value, row, index) {
        categoryManage.del(row);
    }
}
/*
 * 给bootstrapTable增加按钮
**/
categoryManage.buttonOption = function (value, row, index) {
    var returnButton = [];
    returnButton.push('<button class="btn btn-success updateCategory">修改</button>');

    returnButton.push('<button class="btn btn-danger delCategory">一键上下架</button>');
    return returnButton.join('');
}
/*
 * 搜索
**/
categoryManage.search = function () {
    //bootstrapTable 刷新
    $("#categoryList").bootstrapTable('refresh');
}
/*
 * 修改
**/
categoryManage.update = function (row) {
    $("#categoryId").val(row.categoryId);
    $("#name").val(row.name);
    $("#momo").val(row.momo);
    $("#myModal").modal('show');
}
categoryManage.add = function (row) {
    $("#myModal").modal('show');

}

/*
 *  删除
**/
categoryManage.del = function (row) {
    /**
     * 一般情况下删除要加confirm
     */
    Modal.confirm({
        msg: "确认当前操作"
    }).on(function (e) {
        if (e) {
            $.ajax({
                url: delCategoryServletUrl,
                type: 'post',
                data: {
                    "categoryId": row.categoryId,
                    "status":row.status
                },
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.status == 1) {
                        toastr['success']("操作成功");
                        $("#categoryList").bootstrapTable('refresh');
                    } else {
                        toastr['error']("操作失败");
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
    $("#categoryForm").data('bootstrapValidator').resetForm();
    $("#categoryForm")[0].reset();
})
/*
 * 确认按钮
**/
categoryManage.operate = function () {
    var bootstrapValidator = $("#categoryForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            url: updateCategoryUrl,
            type: 'post',
            data: {
                "categoryId": $("#categoryId").val(),
                "name": $("#name").val(),
                "momo": $("#momo").val()


            },
            dataType: 'json',
            success: function (result) {
                if (result.status > 0) {
                    toastr['success']("操作成功");
                    $("#categoryList").bootstrapTable('refresh');
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
$("#categoryForm").bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        name: {
            validators: {
                notEmpty: {
                    message: "类别名不能为空"
                }
            }
        }
    }
});
categoryManage.initCategory = function () {
    $.ajax({
        url: initCategoryUrl,
        type: 'get',
        dataType: 'json',
        success: function (result) {
            console.log(result);
            if (result.status == 1) {
                var res = result.data;
                for (var i = 0; i < res.length; i++) {
                    var opt = $("<option value='" + res[i].id + "'>" + res[i].name + "</option>");
                    $("#categoryId").append(opt);
                }
            }
        }
    })
}
