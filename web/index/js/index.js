var initmenu_url = "/GetMeunListServlet";
var openFrame = new Array();
var checkPage = new Array();
$(function(){
/**
     * 给topUserName 赋值
     */
    $("#topUserName").html(window.localStorage.getItem("userName"));
 accordion();
    $("#menu").on("click",action);
    openFrame.push("firstPage");
    $('#firstPage').contextMenu('topFirstMenu',{
        bindings:{
            't_others': function(t){
                var flgValue=$(t).data('flag');
                var initLen = checkPage.length;
                for(var j=0;j<initLen;j++){
                    for(var k=0;k<checkPage.length;k++){
                        if(checkPage[k]!=flgValue && checkPage[k] != "firstPage"){
                            dmRemoveFrame(checkPage[k]);
                        }
                    }
                }
            },
            't_all': function(t){
                var flgValue=$(t).data('flag');
                var initLen = checkPage.length;
                for(var j=0;j<initLen;j++){
                    for(var k=0;k<checkPage.length;k++){
                        if(checkPage[k] != "firstPage"){
                            dmRemoveFrame(checkPage[k]);
                        }
                    }
                }
            },
            't_refresh': function(t){
                var flgValue=$(t).data('flag');
                var iframe = $("#" + flgValue);
                var iframeDom = iframe[0];
                iframeDom.contentWindow.location.reload(true);
            }
        }
    });
});
//折叠效果
function accordion(){
    $.ajax({
        url : initmenu_url,
        data : {
            // "staffId" : window.localStorage.getItem("dm_user")
            "roleId": window.localStorage.getItem("roleId")
        },
        cache : false,
        async : false,
        type : 'POST',
        dataType : 'json',
        success : function(result) {
            /**
             * 一级菜单
             */
            var _data=result.data;
            for(var i=0;i<_data.length;i++){
                /**
                 * 二级子菜单
                 */
                var List=_data[i].menuList;
                var li = $("<li></li>");
                var divLink=$('<div class="link"></div>');

                var iconfont = $('<i class="tipso iconfont /*'+_data[i].icon+'*/ " data-tipso="'+_data[i].resourceName+'">');
                var listName=$('<span class="list-name">'+_data[i].resourceName+'</span>');
                var iconBottom=$('<span class="iconfont icon-bottom"></span>');
                var submenu=$('<ul class="submenu"> </ul>');

                divLink.append(iconfont);
                divLink.append(listName);
                divLink.append(iconBottom);

                li.append(divLink);

                iconfont.on("click",function(){
                    action();
                });

                divLink.on("click",function(){
                    var $next=$(this).next();
                    $("#accordion .submenu").not($next).slideUp(); //只显示点击的当前二级菜单
                    $("#accordion .link").not($(this)).removeClass("open");
                    $next.slideToggle(); //当前二级菜单收缩与伸展
                    $(this).toggleClass('open');  //控制三角按钮转动
                });
                if(List != null && List != "" && List != "undefined"){
                    for(var j=0; j<List.length;j++){
                        var listli = $('<li data-src="'+ List[j].url +'" data-flag="'+ List[j].identity +'"><a href="#">'+List[j].resourceName+'</a></li>');
                        submenu.append(listli);

                        //点击二级菜单
                        listli.on('click',function(){
                            var listTitleSrc=$(this).data('src');
                            var listTitleFlag=$(this).data('flag');
                            var listTitleCon=$(this).children('a').html();
                            $('.list-title').removeClass('active');
                            $('.list-title').children(".toptitleright").css("display","none");
                            var listLen= $('.list-title').length;
                            if(listLen <16){
                                var listIframe=$('<iframe id="'+listTitleFlag+'" class="if-con" src="'+ listTitleSrc +'" ></iframe>')

                                var listTitle=$('<div id="top-'+listTitleFlag+'" class="list-title active" data-src="'+listTitleSrc +'" data-flag="'+listTitleFlag +'"></div>');

                                var listTitleSpan=$('<span> '+ listTitleCon +' </span>');
                                var listTitleIcon=$('<i class="iconfont icon-delete"></i>');

                                //tab样式
                                var listTitleSpan=$('<div class="toptitleleft"><span> '+ listTitleCon +' </span></div>');

                                var listTitleIcon=$('<div class="toptitleright"></div>');
                                var listTitleIconDel=$('<div class="toptitleico"><i class="iconfont icon-delete"></i></div>');
                                var listTitleIconRefresh=$('<div class="toptitleico"><i class="iconfont icon-shuaxin-"></i></div>');

                                listTitleIcon.append(listTitleIconDel);
                                listTitleIcon.append(listTitleIconRefresh);
                                openFrame.push(listTitleFlag);

                                //tab样式end
                                //页面刷新
                                listTitleIconRefresh.on('click',function(){
                                    var flgValue=$(this).parents(".list-title").data('flag');
                                    var iframe = $("#" + flgValue);

                                    var iframeDom = iframe[0];
                                    iframeDom.contentWindow.location.reload(true);

                                });

                                $(listTitle).contextMenu('topTabMenu',{
                                    bindings:{
                                        't_self': function(t){
                                            var flgValue=$(t).data('flag');
                                            dmRemoveFrame(flgValue);
                                        },
                                        't_others': function(t){
                                            var flgValue=$(t).data('flag');
                                            var initLen = checkPage.length;
                                            for(var j=0;j<initLen;j++){
                                                for(var k=0;k<checkPage.length;k++){
                                                    if(checkPage[k]!=flgValue && checkPage[k] != "firstPage"){
                                                        dmRemoveFrame(checkPage[k]);
                                                    }
                                                }
                                            }
                                        },
                                        't_all': function(t){
                                            var flgValue=$(t).data('flag');
                                            var initLen = checkPage.length;
                                            for(var j=0;j<initLen;j++){
                                                for(var k=0;k<checkPage.length;k++){
                                                    if(checkPage[k] != "firstPage"){
                                                        dmRemoveFrame(checkPage[k]);
                                                    }
                                                }
                                            }
                                        },
                                        't_refresh': function(t){
                                            var flgValue=$(t).data('flag');
                                            var iframe = $("#" + flgValue);
                                            var iframeDom = iframe[0];
                                            iframeDom.contentWindow.location.reload(true);
                                        }
                                    }
                                });
                                //点击头部的 × 删除
                                listTitleIconDel.on('click',function(){
                                    var parentdiv = $(this).parent();
                                    var flgValue=$(this).parents(".list-title").data('flag');
                                    dmRemoveFrame(flgValue);
                                });
                                //点击首页
                                $('#firstPage').on('click',function(){
                                    $(this).addClass('active').siblings().removeClass('active');
                                    var firstSrc=$(this).data('src');
                                    $('.if-con').css("display","none");
                                    $("iframe[src='"+ firstSrc +"']").css("display","block");

                                });
                                //点击切换
                                listTitle.on('click',function(){
                                    $(this).addClass('active').siblings().removeClass('active');
                                    var swiftActive= $(this).data('src');
                                    $('iframe').css("display","none");
                                    $('.if-con[src="'+ swiftActive +'"]').show();
                                    $(this).addClass('active').siblings().children(".toptitleright").css("display","none");
                                    $(this).children(".toptitleright").css("display","inline-block");
                                    if(swiftActive == '/bookingManage/html/bookingManage.html'){
                                        var iframe = $("iframe[src='"+ swiftActive +"']",window.parent.document)[0];
                                        iframe.contentWindow.location.reload(true);
                                    }
                                });

                                listTitle.append(listTitleSpan);
                                listTitle.append(listTitleIcon);

                                var FlagBoo = isItemId(listTitleFlag);
                                if(FlagBoo==1){
                                    $("#topCon").append(listTitle);
                                    var showIframSrc=$(this).data('src')
                                    $('#siderbar').after(listIframe);
                                    $('.if-con').css("display","none");
                                    $("iframe[src='"+ showIframSrc +"']").css("display","block");
                                    checkPage.push(listTitleFlag);
                                }else{
                                    var listTitleActive = $("#topCon .list-title[data-flag='"+listTitleFlag+"']");
                                    var listTitleActiveSrc = $("#topCon .list-title[data-flag='"+listTitleFlag+"']").data('src');
                                    listTitleActive.addClass('active');
                                    listTitleActive.children(".toptitleright").css("display","inline-block");
                                    listTitleActive.siblings().removeClass('active');
                                    $('.if-con').css("display","none");
                                    $('iframe[src="'+ listTitleActiveSrc +'"]').css("display","block");
                                }
                            }else{
                            	$.MsgBox.Alert("消息提示", "您打开的面板太多，为提高系统运行速度，请先关闭一些！");
                            }

                        });
                    }
                }
                li.append(submenu);
                $("#accordion").append(li);
                $('.tipso').tipso({
                    useTitle: false,
                    position: 'right'
                });
            }
        }
    });
}

//动画样式
function action(){
    var width=$("#siderbar").width();
    if(width>100){
        //  $("#accordion .link").removeClass("open");
        $("#siderbar").addClass("width-set");
        $(".siderbar span").addClass("opacity-set");
        $(".submenu li").addClass("show-set");
//        $("#accordion .link").addClass("show-set");
        $("#accordion .link").addClass("width-set");
        $('.if-con').css({"left":"60px","width":"calc(100% - 80px)"});

        $("#accordion .link .iconfont").addClass("tipso");
        $('.tipso').tipso('hide');
    }else{
        $(".submenu").css("display","none");
        $("#siderbar").removeClass("width-set");
        $(".submenu li").removeClass("show-set");
        $("#accordion .link").removeClass("show-set");
        $("#accordion .link").removeClass("width-set");
        setTimeout(function(){
            $("span").removeClass("opacity-set");
        },200);
        $('.if-con').css({"left":"200px","width":"calc(100% - 200px)"});
    }
}


function isItemId(isId) {
    if (checkPage.length == 0) {
        return 1;
    } else {
        var flag = 1;
        for (var i = 0; i < checkPage.length; i++) {
            if (checkPage[i] == isId) {
                flag = 0;
                break;
            }
        }
        return flag;
    }
}

/**
 * 方法:Array.remove(dx) 通过遍历,重构数组
 * 功能:删除数组元素.
 * 参数:dx删除元素的下标.
 */

Array.prototype.remove = function(dx) {
    if (isNaN(dx) || dx > this.length) {
        return false;
    }
    for (var i = 0, n = 0; i < this.length; i++) {
        if (this[i] != this[dx]) {
            this[n++] = this[i];
        }
    }
    this.length -= 1;
}


function dmRemoveFrame(flgValue){
    var prevSrc=$("#"+flgValue).parent().prev().data('src');
    var curTitle = $('.top-con').children('.list-title[data-flag="'+flgValue+'"]');
    curTitle.remove();
    $('.top-con').children('.list-title:last-child').addClass('active');
    for(var k=0;k<checkPage.length;k++){
        if(checkPage[k]==flgValue){
            checkPage.remove(k);
            $("#"+flgValue).remove();
            break ;
        }
    }
    var prevFrame = checkPage[checkPage.length-1];
    $('.top-con').children('.list-title:last-child').children(".toptitleright").css("display","inline-block");
    $("#"+prevFrame).show();

    if(checkPage.length <=1){
        $("#firstPage").addClass('active').siblings().removeClass('active');
        var swiftActive= $("#firstPage").data('src');
        $('iframe').css("display","none");
        $('.if-con[src="'+ swiftActive +'"]').show();
    }

    var isIE = !-[1];
    if (isIE) {
        CollectGarbage();
    }
}


function logout(){
    // $.ajax({
    //     url : php_request_url + "/user/app_sign_out",
    //     cache : false,
    //     async : false,
    //     type : "GET",
    //     dataType : 'json',
    //     success : function(result) {
    //         if(result.status == 1) {
    //
    //         }
    //     }
    // });
    window.localStorage.clear();
    // window.location.href="../login.html";
    window.location.href="../../login.html";
}

function addMenum(listTitleFlag, listTitleSrc, listTitleCon) {
    $('.list-title').removeClass('active');
    $('.list-title').children(".toptitleright").css("display","none");
    var listIframe = $('<iframe id="' + listTitleFlag
        + '" class="if-con" src="' + listTitleSrc + '" ></iframe>')

    var listTitle = $('<div id="top-' + listTitleFlag
        + '" class="list-title active" data-src="/dianmeiweb/web/' + listTitleSrc
        + '" data-flag="' + listTitleFlag + '"></div>');

    var listTitleSpan = $('<span> ' + listTitleCon + ' </span>');
    // var listTitleIcon = $('<i class="iconfont icon-delete"></i>');

    // tab样式
    var listTitleSpan = $('<div class="toptitleleft"><span> ' + listTitleCon
        + ' </span></div>');

    var listTitleIcon = $('<div class="toptitleright"></div>');
    var listTitleIconDel = $('<div class="toptitleico"><i class="iconfont icon-delete"></i></div>');
    var listTitleIconRefresh = $('<div class="toptitleico"><i class="iconfont icon-shuaxin-"></i></div>');

    listTitleIcon.append(listTitleIconDel);
    listTitleIcon.append(listTitleIconRefresh);
    // listTitle.append(listTitleSpan);
    openFrame.push(listTitleFlag);

    listIframe.css("display", "block");

    // tab样式end
    // 页面刷新
    listTitleIconRefresh.on('click', function() {

        var iframe = $("#" + listTitleFlag);
        var iframeDom = iframe[0];
        $("#" + listTitleFlag).css("display", "block");
        iframeDom.contentWindow.location.reload(true);

    });
    console.log(listTitle);
    $(listTitle).contextMenu('topTabMenu', {
        bindings : {
            't_self' : function(t) {
                var flgValue = $(t).data('flag');
                dmRemoveFrame(flgValue);
            },
            't_others' : function(t) {
                var flgValue = $(t).data('flag');
                var initLen = checkPage.length;
                for (var j = 0; j < initLen; j++) {
                    for (var k = 0; k < checkPage.length; k++) {
                        if (checkPage[k] != flgValue
                            && checkPage[k] != "firstPage") {
                            dmRemoveFrame(checkPage[k]);
                        }
                    }
                }
            },
            't_all' : function(t) {
                var flgValue = $(t).data('flag');
                var initLen = checkPage.length;
                for (var j = 0; j < initLen; j++) {
                    for (var k = 0; k < checkPage.length; k++) {
                        if (checkPage[k] != "firstPage") {
                            dmRemoveFrame(checkPage[k]);
                        }
                    }
                }
            },
            't_refresh' : function(t) {
                var flgValue = $(t).data('flag');
                var iframe = $("#" + flgValue);
                var iframeDom = iframe[0];

                iframeDom.contentWindow.location.reload(true);
            }
        }
    });

    // 点击头部的 × 删除
    listTitleIconDel.on('click', function() {
        // $("#ifCon").attr("src",$(this).parent('.list-title').prev().data('src'));
        dmRemoveFrame(listTitleFlag);
    });


    listTitle.append(listTitleSpan);
    listTitle.append(listTitleIcon);

    var FlagBoo = isItemId(listTitleFlag);
    if (FlagBoo == 1) {
        $("#topCon").append(listTitle);
        var showIframSrc =$('#'+listTitleFlag).data('src')
        $('#siderbar').after(listIframe);
        $('.if-con').css("display", "none");
        $("iframe[src='" + showIframSrc + "']").css("display", "block");
        checkPage.push(listTitleFlag);
    } else {
        var listTitleActive = $("#topCon .list-title[data-flag='"
            + listTitleFlag + "']");
        var listTitleActiveSrc = $("#topCon .list-title[data-flag='"
            + listTitleFlag + "']").data('src');
        listTitleActive.addClass('active');
        listTitleActive.children(".toptitleright").css("display",
            "inline-block");
        listTitleActive.siblings().removeClass('active');
        $('.if-con').css("display", "none");
        $('iframe[src="' + listTitleActiveSrc + '"]').css("display", "block");
    }

    //点击切换
    listTitle.on('click',function(){

        $(this).addClass('active').siblings().removeClass('active');
//        			  	$('#ifCon').attr("src",$(this).data('src'));
        var swiftActive= $(this).data('src');
        $('iframe').css("display","none");
        $('.if-con[src="'+ swiftActive +'"]').show();
        $(this).addClass('active').siblings().children(".toptitleright").css("display","none");
        $(this).children(".toptitleright").css("display","inline-block");
    });

    $('iframe[src="' + listTitleSrc + '"]').css("display", "block");

}


/**
 * 查看公告消息
 */
function lookNoticMessage() {
    addMenum('systemNoticeMessage','../../news/newsList.html','公告管理');
    $("#systemNoticeMessage").click();
}

/**
 * 更改密码
 */
function userSelfChangePassord() {
    addMenum('userSelfChangePassord','changePass/views/ChangePass.html','更改密码');
}


/**
 * 查询未读消息数
 */
function showNoReadMessageCount(){
    var  userLoginInfo = JSON.parse(window.localStorage.getItem("current_user"));
    if (userLoginInfo == null || typeof(userLoginInfo) == 'undefined') {
        return;
    };
    var roleArray = userLoginInfo.roles;
    var roleId = 0;
    if (roleArray&&roleArray.length>0) {
        roleId = roleArray[0].id;
    }
    var  storeId=userLoginInfo.storeId;
    if(storeId&&storeId>0){
        $.ajax({
            url : php_request_url + "/web/message/getNoReadMessageCount",
            data :{
                "companyId" : userLoginInfo.companyId,
                "storeId" : storeId,
                "rankId" : userLoginInfo.rankId,
                "roleId" : roleId
            },
            type : "get",
            dataType : "json",
            success : function(result) {
                if (result.status == 1) {
                    var messageCount=result.data.messageCount;
                    if(messageCount>0){
                        $("#sysMessgeCount").show();
                        $("#sysMessgeCount").html(messageCount);
                    }else{
                        $("#sysMessgeCount").hide();
                    }
                }
            }
        });
    }
}

function isCommonItemId(isId) {
    if (parent.checkPage.length == 0) {
        return 1;
    } else {
        var flag = 1;
        for (var i = 0; i < parent.checkPage.length; i++) {
            if (parent.checkPage[i] == isId) {
                flag = 0;
                break;
            }
        }
        return flag;
    }
}
function dmRemoveFrame(flgValue){
    var firstIframe =window.parent.document;
    var prevSrc=$(firstIframe).find("#"+flgValue).parent().prev().data('src');
    var curTitle =$(firstIframe).find('.top-con').children('.list-title[data-flag="'+flgValue+'"]');
    curTitle.remove();
    $(firstIframe).find('.top-con').children('.list-title:last-child').addClass('active');
    for(var k=0;k<parent.checkPage.length;k++){
        if(parent.checkPage[k]==flgValue){
            parent.checkPage.remove(k);
            $("#"+flgValue).remove();
            break ;
        }
    }
    var prevFrame = parent.checkPage[parent.checkPage.length-1];
    $(firstIframe).find('.top-con').children('.list-title:last-child').children(".toptitleright").css("display","inline-block");
    $(firstIframe).find("#"+prevFrame).show();

    if(parent.checkPage.length <=1){
        $(firstIframe).find("#firstPage").addClass('active').siblings().removeClass('active');
        var swiftActive= $(firstIframe).find("#firstPage").data('src');
        $('iframe').css("display","none");
        $(firstIframe).find('.if-con[src="'+ swiftActive +'"]').show();
    }

    var isIE = !-[1];
    if (isIE) {
        CollectGarbage();
    }
}
function openMenum(listTitleFlag, listTitleSrc, listTitleCon) {
    var listTitle=$(".list-title.active" , parent.document);
    $(listTitle).removeClass("active");
    $(listTitle).find(".toptitleright").css("display","none");
    var listLen= $(listTitle).length;
    if(listLen <16){
        var listIframe=$('<iframe id="'+listTitleFlag+'" class="if-con" src="'+ listTitleSrc +'" ></iframe>')
        var listTitle=$('<div id="top-'+listTitleFlag+'" class="list-title active" data-src="'+listTitleSrc +'" data-flag="'+listTitleFlag +'"></div>');
        var listTitleSpan=$('<span> '+ listTitleCon +' </span>');
        var listTitleIcon=$('<i class="iconfont icon-delete"></i>');
        //tab样式
        var listTitleSpan=$('<div class="toptitleleft"><span> '+ listTitleCon +' </span></div>');

        var listTitleIcon=$('<div class="toptitleright"></div>');
        var listTitleIconDel=$('<div class="toptitleico"><i class="iconfont icon-delete"></i></div>');
        var listTitleIconRefresh=$('<div class="toptitleico"><i class="iconfont icon-shuaxin-"></i></div>');

        listTitleIcon.append(listTitleIconDel);
        listTitleIcon.append(listTitleIconRefresh);
        parent.openFrame.push(listTitleFlag);

        //tab样式end
        //页面刷新
        listTitleIconRefresh.on('click',function(){
            var flgValue=$(this).parents(".list-title").data('flag');
            var iframe = window.parent.document.getElementById(flgValue);
            var iframeDom = iframe[0];
            iframe.contentWindow.location.reload(true);
        });

        $(listTitle).contextMenu('topTabMenu',{
            bindings:{
                't_self': function(t){
                    var flgValue=$(t).data('flag');
                    dmRemoveFrame(flgValue);
                },
                't_others': function(t){
                    var flgValue=$(t).data('flag');
                    var initLen = parent.checkPage.length;
                    for(var j=0;j<initLen;j++){
                        for(var k=0;k<parent.checkPage.length;k++){
                            if(parent.checkPage[k]!=flgValue && parent.checkPage[k] != "firstPage"){
                                dmRemoveFrame(parent.checkPage[k]);
                            }
                        }
                    }
                },
                't_all': function(t){
                    var flgValue=$(t).data('flag');
                    var initLen = parent.checkPage.length;
                    for(var j=0;j<initLen;j++){
                        for(var k=0;k<parent.checkPage.length;k++){
                            if(parent.checkPage[k] != "firstPage"){
                                dmRemoveFrame(parent.checkPage[k]);
                            }
                        }
                    }
                },
                't_refresh': function(t){
                    var flgValue=$(t).data('flag');
                    var iframe = $("#" + flgValue);
                    var iframeDom = iframe[0];
                    iframeDom.contentWindow.location.reload(true);
                }
            }
        });


        //点击头部的   × 删除
        listTitleIconDel.on('click',function(){
            dmRemoveFrame(listTitleFlag);

        });
        var firstPage=window.parent.document.getElementById("firstPage");
        //点击首页
        $(firstPage).on('click',function(){
            $(this).addClass('active').siblings().removeClass('active');
            var firstSrc=$(this).data('src');
            var ifCon = window.parent.document.getElementsByClassName("if-con");
            $(ifCon).css("display","none");
            var firstIframe =  window.parent.document;
            $(firstIframe).find("iframe[src='"+ firstSrc +"']").css("display","block");
        });
        //点击切换
        listTitle.on('click',function(){
            $(this).addClass('active').siblings().removeClass('active');
            var swiftActive= $(this).data('src');
            var ifCon = window.parent.document.getElementsByClassName("if-con");
            $(ifCon).css("display","none");
            var firstIframe =  window.parent.document;
            $(firstIframe).find("iframe[src='"+ swiftActive +"']").css("display","block");
            $(this).addClass('active').siblings().children(".toptitleright").css("display","none");
            $(this).children(".toptitleright").css("display","inline-block");
        });

        listTitle.append(listTitleSpan);
        listTitle.append(listTitleIcon);

        var FlagBoo = isCommonItemId(listTitleFlag);
        if(FlagBoo==1){
            var topCon = window.parent.document.getElementById("topCon");
            $(topCon).append(listTitle);
            var siderBar = window.parent.document.getElementById("siderbar");
            $(siderBar).after(listIframe);
            var ifCon = window.parent.document.getElementsByClassName("if-con");
            $(ifCon).css("display","none");
            var parentIframe = window.parent.document.getElementById(listTitleFlag);
            $(parentIframe ).css("display","block");
            parent.checkPage.push(listTitleFlag);
        }else{
            var tempTop=window.parent.document.getElementById("top-"+listTitleFlag);
            $(tempTop).click();
            var flgValue=$(tempTop).data('flag');
            var iframe = window.parent.document.getElementById(flgValue);
            iframe.contentWindow.location.reload(true);
        }
    }else{
        $.MsgBox.Alert("消息提示", "您打开的面板太多，为提高系统运行速度，请先关闭一些！");
    }
};