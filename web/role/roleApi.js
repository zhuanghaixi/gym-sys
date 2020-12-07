/*
 * 定义URL
**/
var getAllRoleInfoUrl = "/GetAllRoleInfoServlet";
var UpdateOrAddRoleUrl = "/UpdateOrAddRoleServlet";
var DelRoleServletUrl = "/DelRoleServlet";
var initRightsURL = "/GetRoleListByIdServlet";
var updateRoleRole = "/UpdateMenuListServlet";
/*
 * 初始化
**/
$(function () {
    roleManage.initList();
})


var roleManage = {};

/*
 * 加载table
**/
roleManage.initList = function () {
    $("#roleList").bootstrapTable({
        url: getAllRoleInfoUrl, //请求路径
        method: 'post', //请求方式(*)
        contentType: 'application/x-www-form-urlencoded', //使用from表单方式提交(*)
        toolbar: '#toolbar', //工具按钮的容器
        striped: true, //是否启用隔行变色
        cache: false, //使用是否缓存 默认为true,所以一般情况下需要设置一下为false (*)
        pagination: true, //是否显示分页(*)
        sortable: false, //使用启用排序
        sortOrder: 'desc', //排序方式
        queryParams: roleManage.queryParams, //传递参数(*)
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
            field: 'roleName',
            title: "职位名称"
        }, {
            field: 'description',
            title: "职位描述"
        }, {
            field: 'status',
            title: "职位状态",
            formatter: function (value) {
                switch (value) {
                    case 1:
                        return "<span class='label label-info'>启用</span>";
                        break;
                    case 0:
                        return "<span class='label label-danger'>禁用</span>";
                        break;
                }
            }
        }, {
            field: 'operation',
            events: buttonOperateEvent,
            title: '操作',
            formatter: function (value, row, index) {
                return roleManage.buttonOption(value, row, index);
            }
        }
        ]
    });
}

/*
 * 传递参数
**/
roleManage.queryParams = function (params) {
    return {
        "pageNumber": params.pageNumber, //当前页数
        "pageSize": params.pageSize, //每页条数
        "searchName": $("#searchName").val()
    }
}
/*
 * 按钮源 (按钮的操作事件)
**/
window.buttonOperateEvent = {
    'click .updateStaff': function (e, value, row, index) {
        //row 这一行的数据
        roleManage.update(row);
    },
    'click .delStaff': function (e, value, row, index) {
        roleManage.del(row);
    },
    'click .updateResource': function (e, value, row, index) {
        roleManage.updateResource(row);
    }
}

/*
 * 给bootstrapTable增加按钮
**/
roleManage.buttonOption = function (value, row, index) {
    var returnButton = [];
    returnButton.push('<button class="btn btn-info updateStaff">修改</button>');
    returnButton.push('<button class="btn btn-danger delStaff">更改状态</button>');
    returnButton.push('<button class="btn btn-warning updateResource">权限分配</button>');
    return returnButton.join('');
}

/*
 * 权限分配
**/
var roleId = "";
roleManage.updateResource = function (row) {
    $("#treeModal").modal('show');
    roleId = row.id;
    $.ajax({
        url: initRightsURL,
        type: 'post',
        data: {
            'id': row.id
        },
        dataType: 'json',
        success: function (result) {
            if (result.status == 1 && result.data != null) {
                $('#tree1').treeview({
                    levels: 1,
                    expandIcon: 'glyphicon glyphicon-chevron-right',
                    collapseIcon: 'glyphicon glyphicon-chevron-down',
                    selectedBackColor: false,
                    selectedColor: '#337AB7',
                    showCheckbox: true,
                    multiSelect: true,
                    data: result.data,
                    onNodeChecked: function (event, node) { //选中节点
                        var selectNodes = getChildNodeIdArr(node); //获取所有子节点
                        if (selectNodes) { //子节点不为空，则选中所有子节点
                            $('#tree1').treeview('checkNode', [selectNodes, {
                                silent: true
                            }]);
                        }
                        $("#tree1").treeview("getNode", node.parentId);
                        setParentNodeCheck(node);
                    },
                    onNodeUnchecked: function (event, node) {//取消选中节点
                        var selectNodes = getChildNodeIdArr(node); //获取所有子节点
                        if (selectNodes) { //子节点不为空，则取消选中所有子节点
                            $('#tree1').treeview('uncheckNode', [selectNodes, {
                                silent: true
                            }]);
                        }
                    }
                });
            }
        }
    })
}

/*
 * 设置父节点
 * @param {Object} node
 */
function setParentNodeCheck(node) {
    var parentNode = $("#tree1").treeview("getNode", node.parentId);
    if (parentNode.nodes) {
        var checkedCount = 0;
        for (x in parentNode.nodes) {
            if (parentNode.nodes[x].state.checked) {
                checkedCount ++;
            } else {
                break;
            }
        }
        if (checkedCount === parentNode.nodes.length) {
            $("#tree1").treeview("checkNode", parentNode.nodeId);
            setParentNodeCheck(parentNode);
        }
    }
}

/*
 * 获取所有的字节点
**/
function getChildNodeIdArr(node) {
    var ts = [];
    if (node.nodes) {
        for (x in node.nodes) {
            ts.push(node.nodes[x].nodeId);
            if (node.nodes[x].nodes) {
                var getNodeDieDai = getChildNodeIdArr(node.nodes[x]);
                for (j in getNodeDieDai) {
                    ts.push(getNodeDieDai[j]);
                }
            }
        }
    } else {
        ts.push(node.nodeId);
    }
    return ts;
}

/*
 * 权限分配
**/
roleManage.setTreeList = function(){
    //获取所有选中的节点的id值
    var getNodes = $('#tree1').treeview('getChecked');
    var getNodeList = new Array();
    for (var i=0; i < getNodes.length; i++) {
        getNodeList[i] = getNodes[i].nodeid;
    }
    Modal.confirm({
        msg: "确认当前操作"
    }).on(function(e) {
        if(e) {
            $.ajax({
                url:updateRoleRole,
                type:'post',
                data:{
                    'roleId':roleId,
                    'getNodeList':JSON.stringify(getNodeList)
                },
                dataType:'json',
                success:function(result){
                    if(result != null){
                        $("#treeModal").modal('hide');
                        $("#rightManageList").bootstrapTable('refresh');
                        toastr['success']("设置成功");
                    }else{
                        toastr['error']("设置失败");
                    }
                }
            });
        }
    });
}

/*
 * 修改
**/
roleManage.update = function (row) {
    $("#myModal").modal('show');
    $("#id").val(row.id);
    $("#roleName").val(row.roleName);
    $("#description").val(row.description);
    $("#status").val(row.status);

}
/*
 * 新增
**/
roleManage.add = function () {
    $("#myModal").modal('show');
}

/*
 * 删除
**/
roleManage.del = function (row) {
    Modal.confirm({
        msg: "确认当前操作"
    }).on(function (e) {
        if (e) {
            $.ajax({
                url: DelRoleServletUrl,
                type: 'post',
                data: {
                    "id": row.id,
                    "status": row.status
                },
                dataType: 'json',
                success: function (result) {
                    if (result.status == 1 && result.data > 0) {
                        toastr['success']("删除成功");
                        $("#roleList").bootstrapTable('refresh');
                    } else {
                        toastr['error']("删除失败");
                    }
                }
            })
        }
    })
}

/*
 * 确认按钮
**/
roleManage.operate = function () {
    var bootstrapValidator = $("#roleForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            url: UpdateOrAddRoleUrl,
            type: 'post',
            data: {
                "id": $("#id").val(),
                "roleName": $("#roleName").val(),
                "description": $("#description").val(),
                "status": $("#status").val()
            },
            dataType: 'json',
            success: function (result) {
                if (result.status > 0) {
                    toastr['success']("操作成功");
                    $("#roleList").bootstrapTable('refresh');
                    $("#myModal").modal('hide');
                } else {
                    toastr['error']("操作失败");
                }
            }
        })
    }
}

/*
 * 搜索
**/
roleManage.search = function () {
    $("#roleList").bootstrapTable('refresh');
}


/*
 * 验证
**/
$("#roleForm").bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        roleName: {
            validators: {
                notEmpty: {
                    message: "职位名称不能为空"
                }
            }
        }
    }
});

/*
 * 关闭模态框
**/
$("#myModal").on('hide.bs.modal', function () {
    //移除上次的校验配置
    $("#roleForm").data('bootstrapValidator').resetForm();
    $("#roleForm")[0].reset();
})
