var updateGoodsUrl = "/UpdateGoodsServlet";
var goodsAddManage= {};

goodsAddManage.operate = function () {

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
                "categoryId": $("#categoryId").val(),
                "createdTime": $("#createdTime").val()
            },
            dataType: 'json',
            success: function (result) {
                if (result.status > 0) {
                    toastr['success']("操作成功");
                    setTimeout("window.location.reload();",1000);
                    $("#myModal").modal('hide');
                } else {
                    toastr['error']("操作失败");
                }
            }
        })
}
$("#goodsAddList").bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        name: {
            validators: {
                notEmpty: {
                    message: "商品名称为空"
                }
            }
        },
        code: {
            validators: {
                notEmpty: {
                    message: "商品简写不能为空"
                }
            }
        },
        stock: {
            validators: {
                notEmpty: {
                    message: "商品库存不能为空"
                }
            }
        },
        unitId: {
            validators: {
                notEmpty: {
                    message: "商品单位不能为空"
                }
            }
        }
    }
})
