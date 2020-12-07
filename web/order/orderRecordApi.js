/*
 * 定义URl
**/
var orderRecordListUrl = "/GetOrderRecordListServlet";
/*
 * 初始化
**/
$(function () {
    orderRecordManage .initList();

})
var orderRecordManage  = {};
/*
 * 加载table
**/
orderRecordManage .initList = function () {
    $("#orderRecordList").bootstrapTable({
        url: orderRecordListUrl, //请求路径
        method: 'post', //请求方式(*)
        contentType: 'application/x-www-form-urlencoded', //使用from表单方式提交(*)
        toolbar: '#toolbar', //工具按钮的容器
        striped: true, //是否启用隔行变色
        cache: false, //使用是否缓存 默认为true,所以一般情况下需要设置一下为false (*)
        pagination: true, //是否显示分页(*)
        sortable: false, //使用启用排序
        sortOrder: 'desc', //排序方式
        queryParams: orderRecordManage.queryParams, //传递参数(*)
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
            field: 'orderId',
            title: "订单编号"
        }, {
            field: 'cardId',
            title: "会员卡号"
        }, {
            field: 'cardType',
            title: "会员类型"
        }, {
            field: 'price',
            title: "商品价格"
        }, {
            field: 'pay',
            title: "付款金额"
        }, {
            field: 'credit',
            title: "获得积分"
        }, {
            field: 'momo',
            title: "备注"
        }, {
            field: 'createdTime',
            title: "创建时间"
        }
        ]
    });
}
/*
 * 传递参数
**/
orderRecordManage.queryParams = function (params) {
    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize, //每页条数
        "searchOrderId": $("#searchOrderId").val(),
        "searchCardId": $("#searchCardId").val()
    }
}
/*
 * 按钮源 (按钮的操作事件)
**/
window.buttonOperateEvent = {
    'click .updateStaff': function (e, value, row, index) {
        //row 这一行的数据
        orderRecordManage.update(row);
    },
    'click .delStaff': function (e, value, row, index) {
        orderRecordManage.del(row);
    }
}


