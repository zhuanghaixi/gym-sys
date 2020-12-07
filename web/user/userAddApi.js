/*
 * 定义URL
**/
var getAllCardTypeInfoUrl = "/GetAllCardTypeInfoServlet";
var AddUserServletUrl = "/AddUserInfoServlet"; //增加关于会员和会员卡的信息

/*
 * 初始化
**/
$(function () {
    var userId = window.localStorage.getItem("userId");
    var userName = window.localStorage.getItem("userName");
    $("#staffId").val(userId);
    $("#staffName").val(userName);
    userManage.initCardType();
    userManage.changedLevel();
    userManage.checkAmount();
    userManage.initProvince();
})

var userManage = {};

/*
 * 加载所有的会员类型
**/
userManage.initCardType = function () {
    $.ajax({
        url: getAllCardTypeInfoUrl,
        type: 'post',
        dataType: 'json',
        success: function (result) {
            console.log(result.data);
            for (var i = 0; i < result.data.length-1; i++) {
                var opt = $("<option data-rank='"+ result.data[i].rank +"' value='" + result.data[i].id + "'>" + result.data[i].name + "</option>");
                $("#userLevel").append(opt);
            }
        }
    })
}
/*

 * 会员等级联动积分
**/
var amount = 0;
var currAmount = 0;
userManage.changedLevel = function () {
    $("#credit").val(0);
    $("#amount").val(0);
    $("#userLevel").change(function () {
        $("#credit").val($("#userLevel :selected").data("rank"));
        $("#amount").val($("#userLevel :selected").data("rank"));
        amount = $("#userLevel :selected").data("rank");
    });
}
/*

 * 对amount进行校验
 *
 * 暂时不用
**/
userManage.checkAmount = function () {
    $("#amount").blur(function () {
        if ($("#amount").val() < amount){
            toastr['error']("起充金额不能小于" + amount + "元");
            $("#amount").val(amount);
        }else {
            currAmount = $("#amount").val();
            $("#userLevel").val(6);
            $("#userLevel").change();
        }
    });
}

/*
 * 加载城市
**/
userManage.initProvince = function () {

}
/*
 * 会员登记
 *  1.user
 *  2.card
 *  3.rechargeRecord
**/
userManage.addUser = function () {
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            url: AddUserServletUrl,
            type: 'post',
            data: {
                "userName": $("#userName").val(),
                "userPhone": $("#userPhone").val(),
                "userLevel": $("#userLevel").val(),
                "userStatus": $("#userStatus").val(),
                "staffId": $("#staffId").val(),
                "staffName": $("#staffName").val(),
                "birthday": $("#birthday").val(),
                "amount": $("#amount").val(),
                "idno": $("#idno").val(),
                "userSex": $("#userSex").val(),
                "area": $("#pro").val() + $("#city").val(),
                "address": $("#address").val(),
                "momo": $("#momo").val()
            },
            dataType: 'json',
            success: function (result) {
                toastr['success']("操作成功");
                setTimeout("window.location.reload();",1000);
            }
        })
    }
}
/*
 * 验证信息
**/
$("#form").bootstrapValidator({
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
        },
        userPhone: {
            validators: {
                notEmpty: {
                    message: "会员电话不能为空"
                },
                regexp:{
                    regexp: /^1[0-9]{10}$/,
                    message: "电话号码不符合规则"
                }
            }
        },
        idno: {
            validators: {
                notEmpty: {
                    message: "身份证号不能为空"
                },
                regexp:{
                    regexp: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
                    message: "身份证号码不符合规则"
                }
            }
        }
    }
})
var cityList = new Array();
cityList['北京市'] = ['朝阳区', '东城区', '西城区', '海淀区', '宣武区', '丰台区', '怀柔', '延庆', '房山'];
cityList['上海市'] = ['宝山区', '长宁区', '丰贤区', '虹口区', '黄浦区', '青浦区', '南汇区', '徐汇区', '卢湾区'];
cityList['广州省'] = ['广州市', '惠州市', '汕头市', '珠海市', '佛山市', '中山市', '东莞市'];
cityList['深圳市'] = ['福田区', '罗湖区', '盐田区', '宝安区', '龙岗区', '南山区', '深圳周边'];
cityList['重庆市'] = ['俞中区', '南岸区', '江北区', '沙坪坝区', '九龙坡区', '渝北区', '大渡口区', '北碚区'];
cityList['天津市'] = ['和平区', '河西区', '南开区', '河北区', '河东区', '红桥区', '塘古区', '开发区'];
cityList['江苏省'] = ['南京市', '苏州市', '无锡市'];
cityList['浙江省'] = ['杭州市', '宁波市', '温州市'];
cityList['四川省'] = ['四川省', '成都市'];
cityList['海南省'] = ['海口市'];
cityList['福建省'] = ['福州市', '厦门市', '泉州市', '漳州市'];
cityList['山东省'] = ['济南市', '青岛市', '烟台市'];
cityList['江西省'] = ['江西省', '南昌市'];
cityList['广西省'] = ['柳州市', '南宁市'];
cityList['安徽省'] = ['安徽省', '合肥市'];
cityList['河北省'] = ['邯郸市', '石家庄市'];
cityList['河南省'] = ['郑州市', '洛阳市'];
cityList['湖北省'] = ['武汉市', '宜昌市'];
cityList['湖南省'] = ['湖南省', '长沙市'];
cityList['陕西省'] = ['陕西省', '西安市'];
cityList['山西省'] = ['山西省', '太原市'];
cityList['黑龙江省'] = ['黑龙江省', '哈尔滨市'];
cityList['其他'] = ['其他'];
//选择省份以后，在城市下拉框中添加对应的城市
function allProvince(){
    var pro = document.getElementById("pro");
    for (var i in cityList){
        pro.add(new Option(i));
    }
}
function changeCity(){
    // 获得省份框中的值
    var pros = document.getElementById("pro").value;
    var city = document.getElementById("city");
    // 将city 列表中的值清空,放置再选择省份后,出现城市乱增加的情况
    city.options.length=0;
    // 遍历
    for (var i in cityList){
        if (i==pros){
            for (var j in cityList[i]){
                // 将 Option标签添加到Select中
                city.add(new Option(cityList[i][j]),null);
            }
        }
    }
}

// 在页面加载时调用方法
window.onload = allProvince;


