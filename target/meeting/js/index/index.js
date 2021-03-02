var selected = [];
//获取当前网址，如：
var curWwwPath=window.document.location.href;
//获取主机地址之后的目录如：/Tmall/index.jsp
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
//获取主机地址，如：//localhost:8080
var localhostPaht=curWwwPath.substring(0,pos);
//获取带"/"的项目名，如：/Tmall
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

$(function () {

    //加载选择框
    $.ajax({
        dataType: "json",
        async: false,
        type: 'post',
        url: projectName+'/meeting/findselect',
        data: {},
        success: function (data) {
            $.each(data.type, function (index, temp) {
                $("#select1").append("<option value =" + temp + ">" + temp + "</option>")
            })
            $("#select1").append()
        }
    });

    //加载列表
    $('#grid').bootstrapTable({
        url: projectName+"/meeting/findAll",                      //请求后台的URL（*）
        method: 'post',                      //请求方式（*）
        //得到查询的参数
        queryParams: function (params) {
            //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            var temp = {
                rows: params.limit,                         //页面大小 limit为原来pageSize上的值
                page: (params.offset / params.limit) + 1,   //页码 pageList
                sort: params.sort,      //排序列名
                sortOrder: params.order, //排位命令（desc，asc）
                persontype: $("#select1").val(),//refresh无法覆盖这个方法只能放上来
                customercompanyname: $("#input1").val(),
                customername: $("#input2").val(),
                tablenum: $("#input3").val()
            };
            return temp;
        },
        //toolbar: '#toolbar',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 25,                     //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: true,                  //是否显示所有的列（选择显示的列）
        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表

        columns: [
            {
                checkbox: true,
            },
            {
                title: '序号',
                align: "center",
                width: 40,
                formatter: function (value, row, index) {
                    //获取每页显示的数量
                    var pageSize = $('#grid').bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber = $('#grid').bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            {
                field: 'id',
                title: 'id',
                visible: false
            }, {
                field: 'persontype',
                title: '人员类型',
                sortable: true
            }, {
                field: 'customertype',
                title: '客户类型',
                sortable: true
            }, {
                field: 'customercompanyname',
                title: '客户单位名称',
                sortable: true,

            }, {
                field: 'customername',
                title: '人员姓名',

            }, {
                field: 'position',
                title: '职务'
            }, {
                field: 'stay',
                title: '是否住宿',
                sortable: true
            }, {
                field: 'roomtype',
                title: '房间类型'
            }, {
                field: 'roomnum',
                title: '房间号'
            }, {
                field: 'tablenum',
                title: '用餐桌号'
            }, {
                field: 'memo',
                title: '备注',
                width: 120,
                align: 'center',


            },],
        onLoadSuccess: function () {

        },
        onLoadError: function () {

        },
        onDblClickRow: function (row, $element) {

        },
        toolbar: '#toolbar',

    });
});

function subm() {
    var excelFile = $("#upload").val();

    if (excelFile == '') {

        //这个好看点
        Ewin.confirm({message: "请选择一个文件"}).on(function (e) {
        });

        //这个丑点
        /* bootbox.alert({
             message: "请选择一个文件!",
             backdrop: true
         });*/
        return;
    } else if (excelFile.indexOf('.xls') == -1 || excelFile.indexOf('.xlsx') != -1) {
        Ewin.confirm({message: "请上传正确的excel,后缀名为xls!"}).on(function (e) {
        });
        return;
    } else {
        $.ajax({
            type: 'post',
            async: false,
            url:projectName+ '/meeting/upload',
            cache: false,//不从缓存读取
            processData: false,//默认都是字符串
            contentType: false,//默认的话文件格式就是application/x-www-form-urlencoded
            data: new FormData($("#import_form")[0]),
            success: function (data) {
                $('#myModal').modal('hide');//关闭模态框
                Ewin.confirm({message: "执行成功，共影响" + data + "条数据"}).on(function (e) {
                    if (e) {
                         $('#grid').bootstrapTable('refresh')
                    }
                });

            }
        });
    }
}

function notnull() {
    //验证人员
    var n = $("#customername").val();
    var reg = /\S/;
    var boolean1 = reg.test(n);
    if (boolean1) {
        $("#customername").css("border", "");
    } else {
        $("#customername").css("border", "red 1px solid");
    }
    return boolean1
}

function notnull2() {
    //验证人员
    var n = $("#customername2").val();
    var reg = /\S/;
    var boolean1 = reg.test(n);
    if (boolean1) {
        $("#customername").css("border", "");
    } else {
        $("#customername").css("border", "red 1px solid");
    }
    return boolean1
}

function add() {
    var a = notnull();
    if (a) {
        $.ajax({
            dataType: "text",
            async: false,
            type: 'post',
            url:projectName+ '/meeting/add',
            data: $("#add_form").serialize(),
            success: function (data) {
                $('#myModal2').modal('hide');//关闭模态框
                Ewin.confirm({message: data}).on(function (e) {
                    if (e) {
                         $('#grid').bootstrapTable('refresh')
                    }
                });
            }
        });
    } else {
        Ewin.confirm({message: "标红的必填项不能为空"}).on(function (e) {
        });
    }

}

function del() {
    selected = $('#grid').bootstrapTable('getSelections');
    if (selected.length != 0) {
        var search = [];

        $.each(selected, function (i, item) {
            search.push(item.id)
        })

        Ewin.confirm({message: "是否确定删除"}).on(function (e) {
            if (e) {
                $.ajax({
                    async: false,
                    type: 'post',
                    url:projectName+ '/meeting/del',
                    traditional: true,//传递数组必须加这个不然会变成ids[]:aaa ids[]:aaa这样
                    data: {ids: search},
                    success: function (data) {
                        Ewin.confirm({message: data}).on(function (e) {
                            if (e) {
                                selected = [];//页面刷新应该没了 不过以防万一制空下
                                 $('#grid').bootstrapTable('refresh')
                            }
                        });
                    }
                });
            } else {
                return;
            }
        });


    } else {
        Ewin.confirm({message: "至少选择一行数据才可删除"}).on(function (e) {
        });
    }
}

//修改回显
function updlist() {

    selected = $('#grid').bootstrapTable('getSelections');
    if (selected.length != 0) {
        var id = '';
        $.each(selected, function (i, item) {
            id = item.id;
        })
        $.ajax({
            async: false,
            type: 'post',
            url:projectName+ '/meeting/updlist',
            data: {id: id},
            success: function (data) {

                //动态调用模态框
                $("#myModal3").modal({
                    backdrop: "static",
                    keyboard: false
                });
                $("#persontype2").val(data.persontype);
                $("#customertype2").val(data.customertype);
                $("#customercompanyname2").val(data.customercompanyname);
                $("#customername2").val(data.customername);
                $("#position2").val(data.position);
                $("#stay2").val(data.stay);
                $("#roomnum2").val(data.roomnum);
                $("#roomtype2").val(data.roomtype);
                $("#tablenum2").val(data.tablenum);
                $("#memo2").val(data.memo);
                $("#id2").val(id)

                selected = [];
            }
        });


    } else {
        Ewin.confirm({message: "请选择要修改的数据（多行选择将会默认修改第一行）"}).on(function (e) {
        });
    }

}

//修改提交
function upd() {
    var a = notnull2();
    if (a) {
        $.ajax({
            dataType: "text",
            async: false,
            type: 'post',
            url: projectName+'/meeting/upd',
            data: $("#upd_form").serialize(),
            success: function (data) {
                $('#myModal3').modal('hide');//关闭模态框
                Ewin.confirm({message: data}).on(function (e) {
                    if (e) {
                         $('#grid').bootstrapTable('refresh')
                    }
                });
            }
        });
    } else {
        Ewin.confirm({message: "标红的必填项不能为空"}).on(function (e) {
        });
    }

}


function t_search() {
    $('#grid').bootstrapTable('refresh')
}

function clearlist() {
    Ewin.confirm({message: "确定要清空吗"}).on(function (e) {
        if(e){
            $.ajax({
                dataType: "text",
                async: false,
                type: 'post',
                url: projectName+'/meeting/clear',
                data: {},
                success: function (data) {
                    Ewin.confirm({message: data}).on(function (e) {
                        if(e){
                            $('#grid').bootstrapTable('refresh')
                        }
                    });
                }
            });
        }
    });
}


//座位查询
function sitsearch() {
    window.open(projectName+"/meeting/search")
}

//作为表
function sitform() {
    window.open(projectName+"/meeting/getsit")
}



