/*
 * 定义URL
**/
var getAllRechargeRuleUrl = "/GetAllRechargeRuleServlet";
var cardListUrl = "/GetCardInfoServlet";
var UserRechargeUrl = "/UserRechargeServlet";

/*
 * 初始化
**/
$(function () {
    userRecharge.initRechargeDate();
    userRecharge.initRechargeId();
    userRecharge.initRechargeRule();
})

var userRecharge = {};
var cardUser ={};
/*
 * 搜索
**/
cardUser.search= function () {
    //bootstrapTable 刷新
    $("#userList").bootstrapTable('refresh');
}
/*
 * 初始化充值日期
**/
userRecharge.initRechargeDate = function () {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth();
    var day = date.getDate();
    var hour = date.getHours();
    var min = date.getMinutes();
    var sec = date.getSeconds();
    $("#rechargeDate").text(year + "-" + (month + 1) + "-" + day + " " + hour + ":" + min + ":" + sec);
}

/*
 * 初始化充值单号
 *  获取时间戳当做单号 保持单号的唯一性
**/
userRecharge.initRechargeId = function () {
    var timestamp = new Date().getTime();
    $("#rechargeId").text(timestamp);
}

/*
 * 初始化充值规则
**/
userRecharge.initRechargeRule = function () {
    $.ajax({
        url: getAllRechargeRuleUrl,
        type: 'post',
        data: {
            "pageNumber": 1,
            "pageSize": 100000000
        },
        dataType: 'json',
        success: function (result) {
            if (result.status > 0) {
                var list = result.data.list;
                for (var i = 0; i < list.length; i++) {
                    var opt = $("<option data-amount ='" + list[i].amount + "' data-coff = '" + list[i].coefficient + "' value='" + list[i].id + "'>" + list[i].name + "</option>");
                    $("#rechargeRule").append(opt);
                }
            }
        }
    })
}

/*
 

 * 初始化充值金额和赠送金额
**/
var coff;
var qiChong;
userRecharge.initAmount = function () {
    coff = $("#rechargeRule option:selected").data('coefficient');
    qiChong = $("#rechargeRule option:selected").data('amount');
}
userRecharge.setAmount = function () {
    if (coff >= 0 && qiChong <= $("#amount").val()) {
        var amount = $("#amount").val();
        amount = amount * coff;
        $("#sendAmount").val(amount);
    } else {
        $("#sendAmount").val(0);
    }
    $("#total").text(Number($("#sendAmount").val()) + Number($("#amount").val()));
    $("#credit").text(Number($("#amount").val()));
}

/*
 * 加载用户列表
**/
userRecharge.searchUserList = function () {
    $("#myModal").modal('show');
    userRecharge.initList();
}

/*
 * 加载table
**/
userRecharge.initList = function () {
    $("#userList").bootstrapTable({
        url: cardListUrl, //请求路径
        method: 'post', //请求方式(*)
        contentType: 'application/x-www-form-urlencoded', //使用from表单方式提交(*)
        toolbar: '#toolbar', //工具按钮的容器
        striped: true, //是否启用隔行变色
        cache: false, //使用是否缓存 默认为true,所以一般情况下需要设置一下为false (*)
        pagination: true, //是否显示分页(*)
        sortable: false, //使用启用排序
        sortOrder: 'desc', //排序方式
        queryParams: userRecharge.queryParams, //传递参数(*)
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
            field: 'userId',
            title: "会员编号"
        }, {
            field: 'userName',
            title: "会员姓名"
        }, {
            field: 'cardId',
            title: "会员卡号"
        }, {
            field: 'levelName',
            title: "等级"
        }, {
            field: 'credit',
            title: "积分"
        }, {
            field: 'amount',
            title: "余额"
        }, {
            field: 'status',
            title: "状态",
            formatter: function (value) {
                switch (value) {
                    case 0 :
                        return "<span class='label label-info'>正常</span>";
                    case 1 :
                        return "<span class='label label-danger'>挂失</span>";
                }
            }
        }
        ]
    });
}
/*
 * 传递参数
**/
userRecharge.queryParams = function (params) {
    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize, //每页条数
        "inp": $("#searchCardId").val()
    }
}
/*
 * 双击表格加载 指定区域的数据
**/
$("#myModal").on('dbl-click-row.bs.table', function (e, row) {
    $("#userCardId").text(row.cardId);
    $("#userName").text(row.userName);
    $("#cardType").text(row.levelName);
    $("#cardAmount").text(row.amount);
    $("#userCredit").text(row.credit);
    $("#inp").val(row.cardId);
    $("#myModal").modal('hide');
})
/*
 * 用户充值
**/
userRecharge.userRechargeOk = function () {
    var userCardId = $("#userCardId").text();
    var sendAmount = $("#sendAmount").val();
    var credit = $("#credit").text();
    var amount = $("#amount").val();
    var cardAmount = $("#cardAmount").text();
    var rechargeRule = $("#rechargeRule").val();
    var staffId = window.localStorage.getItem("userId");
    var momo = $("#momo").val();
    var userCredit = $("#userCredit").text();
    $.ajax({
        url: UserRechargeUrl,
        type: 'post',
        data: {
            "userCardId":userCardId,
            "credit":credit,
            "amount":amount,
            "cardAmount":cardAmount,
            "rechargeRule":rechargeRule,
            "staffId":staffId,
            "momo":momo,
            "sendAmount":sendAmount,
            "userCredit":userCredit
        },
        dataType: 'json',
        success: function () {
            toastr['success']("充值成功");
            $("#form").reset();
            setTimeout("window.location.reload();",1000);
        }
    })
}