/*
 * 定义URl
**/
var cardListUrl = "/GetCardInfoServlet";
var updateCardUrl = "/UpdateCardServlet";
var delCardServletUrl = "/DelCardServlet";

/*
 * 初始化
**/
$(function () {
    cardManage.initList();
})
var cardManage = {};
/*
 * 加载table
**/
cardManage.initList = function () {
    $("#cardList").bootstrapTable({
        url: cardListUrl, //请求路径
        method: 'post', //请求方式(*)
        contentType: 'application/x-www-form-urlencoded', //使用from表单方式提交(*)
        toolbar: '#toolbar', //工具按钮的容器
        striped: true, //是否启用隔行变色
        cache: false, //使用是否缓存 默认为true,所以一般情况下需要设置一下为false (*)
        pagination: true, //是否显示分页(*)
        sortable: false, //使用启用排序
        sortOrder: 'desc', //排序方式
        queryParams: cardManage.queryParams, //传递参数(*)
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
            field: 'cardId',
            title: "会员卡Id"
        }, {
            field: 'userId',
            title: "会员编号"
        }, {
            field: 'userName',
            title: "会员姓名"
        }, {
            field: 'amount',
            title: "会员卡内余额"
        }, {
            field: 'credit',
            title: "会员卡积分"
        },{
            field: 'levelName',
            title: "会员卡等级"
        }, {
            field: 'status',
            title: "会员卡状态",
            formatter: function (value) {
                switch (value) {
                    case 0 :
                        return "<span class='label label-info'>正常</span>";
                    case 1 :
                        return "<span class='label label-danger'>挂失</span>";
                }
            }
        }, {
            field: 'operation',
            events: buttonOperateEvent,
            title: '操作',
            formatter: function (value, row, index) {
                return cardManage.buttonOption(value, row, index);
            }
        }
        ]
    });
}
/*
 * 传递参数
**/
cardManage.queryParams = function (params) {

    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize, //每页条数
        "searchCardId": $("#searchCardId").val()
    }
}
/*
 * 按钮源 (按钮的操作事件)
**/
window.buttonOperateEvent = {
    'click .updateCard': function (e, value, row, index) {
        //row 这一行的数据
        cardManage.update(row);
    },
    'click .delCard': function (e, value, row, index) {
        cardManage.del(row);
    }
}
/*
 * 给bootstrapTable增加按钮
**/
cardManage.buttonOption = function (value, row, index) {
    var returnButton = [];
    returnButton.push('<button class="btn btn-info updateCard">修改</button>');
    returnButton.push('<button class="btn btn-danger delCard">删除</button>');
    return returnButton.join('');
}
/*

 * 搜索
**/
cardManage.search = function () {
    //bootstrapTable 刷新
    $("#cardList").bootstrapTable('refresh');
}
/**
 * 修改
 * @param row
 */
cardManage.update = function (row) {
    $("#cardId").val(row.cardId);
    $("#userId").val(row.userId);
    $("#userName").val(row.userName);
    $("#amount").val(row.amount);
    $("#credit").val(row.credit);
    $("#status").val(row.status);
    $("#myModal").modal('show');
}
/*
 *  删除
**/
cardManage.del = function (row) {
    /**
     * 一般情况下删除要加confirm
     */
    Modal.confirm({
        msg: "确认当前操作"
    }).on(function (e) {
        if (e) {
            $.ajax({
                url: delCardServletUrl,
                type: 'post',
                data: {
                    "cardId": row.cardId,
                    "status": row.status
                },
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.status == 1) {
                        toastr['success']("删除成功");
                        $("#cardList").bootstrapTable('refresh');
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
    $("#cardForm").data('bootstrapValidator').resetForm();
    $("#cardForm")[0].reset();
})
/*
 * 确认按钮
**/
cardManage.operate = function () {
    var bootstrapValidator = $("#cardForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            url: updateCardUrl,
            type: 'post',
            data: {
                "cardId": $("#cardId").val(),
                "userId": $("#userId").val(),
                "amount": $("#amount").val(),
                "credit": $("#credit").val(),
                "status": $("#status").val()
            },
            dataType: 'json',
            success: function (result) {
                if (result.status > 0) {
                    toastr['success']("操作成功");
                    $("#cardList").bootstrapTable('refresh');
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
$("#cardForm").bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        cardId: {
            validators: {
                notEmpty: {
                    message: "会员卡Id不能为空"
                }
            }
        }
    }
});
//
// cardManage.add = function (row) {
//     $("#myModal").modal('show');
//
// }