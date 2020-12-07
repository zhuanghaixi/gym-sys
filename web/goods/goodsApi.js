/*
 * 
 * @date 2019/11/20
 * 定义URl
**/
var goodsListUrl = "/GetGoodsAllServlet";
var updateGoodsUrl = "/UpdateGoodsServlet";

var delGoodsServletUrl = "/DelGoodsServlet";
var initCategoryUrl = "/GetGoodsAllCategoryServlet";

/*
 * 初始化
**/
$(function () {
    goodsManage.initList();
    goodsManage.initCategory();
})
var goodsManage = {};
/*
 * 加载table
**/
goodsManage.initList = function () {
    $("#goodsList").bootstrapTable({
        url: goodsListUrl, //请求路径
        method: 'post', //请求方式(*)
        contentType: 'application/x-www-form-urlencoded', //使用from表单方式提交(*)
        toolbar: '#toolbar', //工具按钮的容器
        striped: true, //是否启用隔行变色
        cache: false, //使用是否缓存 默认为true,所以一般情况下需要设置一下为false (*)
        pagination: true, //是否显示分页(*)
        sortable: false, //使用启用排序
        sortOrder: 'desc', //排序方式
        queryParams: goodsManage.queryParams, //传递参数(*)
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
            field: 'goodsid',
            title: "商品编号"
        }, {
            field: 'name',
            title: "商品名称"
        }, {
            field: 'code',
            title: "简称",

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
        }, {
            field: 'stock',
            title: "库存"
        }, {
            field: 'unitId',
            title: "单位",
            formatter: function (value) {
                switch (value) {
                    case 1 :
                        return "<span>张</span>";
                    case 2 :
                        return "<span>条</span>";
                    case 3 :
                        return "<span>个</span>";
                    case 4 :
                        return "<span>只</span>";
                    case 5 :
                        return "<span>瓶</span>";
                    case 6 :
                        return "<span>根</span>";
                }
            }
        }, {
            field: 'price',
            title: "价格"
        }, {
            field: 'categoryId',
            title: "类别",
            formatter: function (value) {
                switch (value) {
                    case 1 :
                        return "运动服装";
                    case 2 :
                        return "健身补剂";
                    case 3 :
                        return "健身餐";
                    case 4 :
                        return "私教服务";
                    case 5 :
                        return "健身饮料";
                    case 6 :
                        return "医疗用品";
                }
            }
        },  {
            field: 'operation',
            events: buttonOperateEvent,
            title: '操作',
            formatter: function (value, row, index) {
                return goodsManage.buttonOption(value, row, index);
            }
        }
        ]
    });
}
/*
 * 传递参数
**/
goodsManage.queryParams = function (params) {
    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize, //每页条数
        "searchgoodsid": $("#searchgoodsid").val(),
        "searchName": $("#searchName").val()
    }
}
/*
 * 按钮源 (按钮的操作事件)
**/
window.buttonOperateEvent = {
    'click .updateGoods': function (e, value, row, index) {
        //row 这一行的数据
        goodsManage.update(row);
    },
    'click .delGoods': function (e, value, row, index) {
        goodsManage.del(row);
    }
}
/*
 * 给bootstrapTable增加按钮
**/
goodsManage.buttonOption = function (value, row, index) {
    var returnButton = [];
    returnButton.push('<button class="btn btn-info updateGoods">修改</button>');
    returnButton.push('<button class="btn btn-danger delGoods">上/下架</button>');
    return returnButton.join('');
}
/*
 * 搜索
**/
goodsManage.search = function () {
    //bootstrapTable 刷新
    $("#goodsList").bootstrapTable('refresh');
}
/*
 * 修改
**/
goodsManage.update = function (row) {
    $("#goodsid").val(row.goodsid);
    $("#name").val(row.name);
    $("#code").val(row.code);
    $("#status").val(row.status);
    $("#stock").val(row.stock);
    $("#unitId").val(row.unitId);
    $("#price").val(row.price);
    $("#categoryId").val(row.categoryId);
    $("#myModal").modal('show');
}
goodsManage.add = function (row) {
    $("#myModal").modal('show');

}
/*
 *  删除
**/
goodsManage.del = function (row) {
    /**
     * 一般情况下删除要加confirm
     */
    Modal.confirm({
        msg: "确认当前操作"
    }).on(function (e) {
        if (e) {
            $.ajax({
                url: delGoodsServletUrl,
                type: 'post',
                data: {
                    "goodsid": row.goodsid,
                    "status":row.status
                },
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.status == 1) {
                        toastr['success']("删除成功");
                        $("#goodsList").bootstrapTable('refresh');
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
    $("#goodsForm").data('bootstrapValidator').resetForm();
    $("#goodsForm")[0].reset();
})
/*
 * 确认按钮
**/
goodsManage.operate = function () {
    var bootstrapValidator = $("#goodsForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            url: updateGoodsUrl,
            type: 'post',
            data: {
                "goodsid": $("#goodsid").val(),
                "name": $("#name").val(),
                "code": $("#code").val(),
                "status": $("#status").val(),
                "stock": $("#stock").val(),
                "unitId": $("#unitId").val(),
                "price": $("#price").val(),
                "categoryId": $("#categoryId").val()

            },
            dataType: 'json',
            success: function (result) {
                if (result.status > 0) {
                    toastr['success']("操作成功");
                    $("#goodsList").bootstrapTable('refresh');
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
$("#goodsForm").bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        name: {
            validators: {
                notEmpty: {
                    message: "商品名称不能为空"
                }
            }
        }
    }
});
goodsManage.initCategory = function () {
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
