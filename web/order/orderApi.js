/*
 * 定义URl
**/
var goodsListUrl = "/GetGoodsAllServlet";
var userListUrl = "/GetCardInfoServlet";
/*
 * 初始化
**/
$(function () {
    /**
     * 给topUserName 赋值
     */
    $("#topUserName").html(window.localStorage.getItem("userName"));
    goodsManage.initList();
})

var goodsManage = {};
/**
 * 加载商品列表
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
        pageSize: 14, //默认每页加载行数
        pageList: [12, 25, 50, 100], //每页可选择记录数
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
            title: "商品缩写",

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
    'click .addTab': function (e, value, row, index) {
        goodsManage.addTab(row);
    }
}
/*
 * 给bootstrapTable增加按钮
**/
goodsManage.buttonOption = function (value, row, index) {
    var returnButton = [];
    returnButton.push('<button class="btn btn-info addTab">添加到购物车</button>');
    return returnButton.join('');
}
/*
 * 搜索
**/
goodsManage.search = function () {
    //bootstrapTable 刷新
    $("#goodsList").bootstrapTable('refresh');
}


/**
 * 添加到购物车
 */
goodsManage.addTab = function (row) {
    var tr = $("<tr></tr>");
    var td1 = $("<td class='goodsId'>" + row.goodsid + "</td>");
    var td2 = $("<td>" + row.name + "</td>");
    var td3 = $("<td>" + row.stock + "</td>");
    var td4 = $("<td  class='price'>" + row.price+ "</td>");
    var td5 = $("<td><button onclick='jian(this)' >-</button><input readonly='readonly' type=\"text\"id='number' value='1'  class='number' '" +
        "onkeyup=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\\D/g,'')}\"  \n" +
        "onafterpaste=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\\D/g,'')}\" />" +
        "<button onclick='jia(this)' >+</button></td>");
    var td6 = $("<td class='number1' id='number1'>"+row.price+"</td>");
    var delb = $("<td>"+"<button class='btn btn-danger' onclick='delcommodity(this)'>删除 </button>"+"</td>");
    tr.append(td1);
    tr.append(td2);
    tr.append(td3);
    tr.append(td4);
    tr.append(td5);
    tr.append(td6);
    tr.append(delb);
    $("#Tab").append(tr);
    CurrentTime();
    jiesuan();
    zongjia();
    /**
     * 当前时间
     * @type {Date}
     */
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    var strDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    $("#consumptionTime").val(currentdate);
};
/**
 * 删除订单
 * @param obj
 */
function delcommodity(obj) {
    CurrentTime();
    $(obj).parent().parent().remove();
    jiesuan();
    zongjia();
}
/**
 * 商品数量加减
 * @param obj
 */
function jia(obj) {
    $(obj).prev().val(parseInt($(obj).prev().val()) + 1);
    jiesuan();
    var a = parseInt($(obj).prev().val());
    var b =parseInt($(obj).parent().parent().find("td[class='price']").text());
    var c =a*b;
    danjia(obj,c);
    zongjia();
}
function jian(obj) {
    if ($(obj).next().val() > 1) {
        $(obj).next().val(parseInt($(obj).next().val()) - 1);
    } else {
        $("#number").val(1);
    }
    jiesuan();
    var a = parseInt($(obj).next().val());
    var b =parseInt($(obj).parent().parent().find("td[class='price']").text());
    var c =a*b;
    danjia(obj,c);
    zongjia();
}

/**
 * 单个商品总价
 */
function danjia(obj,c) {
    $(obj).parent().parent().find("td[class='number1']").text(c);
}

/**
 * 商品总价
 */
function  zongjia() {
    var $obj = $("#Tab").parent().find("td[class='number1']");
    var sum = 0;
    for (var i = 0; i < $obj.length; i++) {
        sum += parseInt($obj.eq(i).text());
    }
    $("#rental").val(sum);
    $("#aggregateScore").val(sum);
    $("#zheghourental").val(sum);

}
function ok() {
    toastr['success']("结算成功");
    setTimeout("window.location.reload();",1000);
}

/**
 * 结算数据统计
 */
function jiesuan() {
    var $obj = $("#tab").parent().find("input[class='number']");
    var sum = 0;
    for (var i = 0; i < $obj.length; i++) {
        sum += parseInt($obj.eq(i).val());
    }
    $("#push").val(sum);

}



/**
 * 订单编号获取当前时间，后接两位随机数
 */
function CurrentTime()
{
    var now = new Date();
    var year = now.getFullYear(); //年
    var month = now.getMonth() + 1; //月
    var day = now.getDate(); //日
    var hh = now.getHours(); //时
    var mm = now.getMinutes(); //分
    var ss = now.getSeconds(); //秒
    var clock = year + "";
    if(month < 10)
        clock += "0";
    clock += month + "";
    if(day < 10)
        clock += "0";
    clock += day + "";
    if(hh < 10)
        clock += "0";
    clock += hh + "";
    if(mm < 10) clock += '0';
    clock += mm + "";
    if(ss < 10) clock += '0';
    clock += ss;
    for (var i = 0; i < 2; i++) //j位随机数，用以加在时间戳后面。
    {
        clock += Math.floor(Math.random() * 10);
    }
    $("#orderNumber").val(clock);
    /**
     * 给topUserName 赋值
     */
    $("#topUserName").html(window.localStorage.getItem("userName"));
}
goodsManage.find = function () {
    $("#myModal").modal('show');
    goodsManage.initList1();
}
/*
 * 加载table
**/
goodsManage.initList1 = function () {
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
        queryParams: goodsManage.queryParams1, //传递参数(*)
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
        }
        ]
    });
}
/*
 * 传递参数
**/
goodsManage.queryParams1 = function (params) {
    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize //每页条数
    }
}
/*
 * 双击表格加载 指定区域的数据
**/
$("#myModal").on('dbl-click-row.bs.table', function (e, row) {
    $("#userCardId").val(row.cardId);
    $("#userName").val(row.userName);
    $("#cardType").val(row.levelName);
    $("#cardAmount").val(row.amount);
    $("#userCredit").val(row.credit);
    $("#inp").val(row.cardId);
    $("#myModal").modal('hide');
})
/**
 * 订单结算
 */
goodsManage.Settlement=function () {
    //判断信息是否正确
    if ($("#userCardId").text().length > 2) {
        toastr['error']("请选择消费账户");
        alert("111");
        return 0;
    }
    var $codeval = $("#Tab").find(".goodsId");
    var $numval = $("#Tab").find(".number");
    var code = "";
    var num = "";
    if ($codeval.length < 1) {
        toastr['error']("请选择商品");
        return 0;
    }
    var total = parseInt($("#rental").val());
    var amount = parseInt($("#cardAmount").text());
    if (total > amount) {
        toastr['error']("账户余额不足");
        return 0;
    };
    for (var i = 0; i < $codeval.length; i++) {
        code = code + "," + $codeval.eq(i).html();
        num = num + "," + $numval.eq(i).val();

    };
    $.ajax({
        url: '/IntOrderServlet',
        data: {
            "code": code,
            "num": num,
            "cardId": $("#userCardId").val(),//卡号
            "orderNumber": $("#orderNumber").val(),//订单编号
            "cardType": $("#cardType").val(),//会员等级
            "amount": $("#cardAmount").val(),//余额
            "pay": $("#rental").val(),//消费金额
            "credit": $("#aggregateScore").val(),//消费积分
            "momo": $("#momo").val(),//备注
            "createdTime": $("#consumptionTime").val(),//消费时间
        },
        type: 'post',
        dataType: 'json',
        success: function (res) {
            console.log(res);
            if (res.status == 3) {
                toastr['error']("商品余量不足");
            } else {
                toastr['success']("购买成功");
                setTimeout("window.location.reload()",1000);
                // location.reload();//购买成功之后刷新界面
            }
        }
    })
    // toastr['success']("购买成功");
    // setTimeout("window.location.reload()",1000);
}