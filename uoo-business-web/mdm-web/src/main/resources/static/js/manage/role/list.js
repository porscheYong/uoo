var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var roleId = getQueryString('roleId');
var roleName = getQueryString('roleName');
var roleCode = getQueryString('roleCode');
var lChBox =document.getElementById("lowerCheckBox"); 
// var roleFullName = "";
var roleTable;
var permList = [];
var isCheck = 0;
var query;
var loading = parent.loading;


//获取当前角色的权限
function getRolePerm(){
    $http.get('/system/sysRole/get/'+roleId, {
    }, function (data) {
        $("#roleName").html(data.roleName);
        $("#perms").empty();
        if(data.permissionNames){
            permList = data.permissionNames.split(',');
        }
        for(var i=0;i<permList.length;i++){ 
            $("#perms").append("<span class='perTab'>"+permList[i]+"</span>");
        }
    }, function (err) {
        // loading.screenMaskDisable('container');
    });
}

//初始化角色表格
function initRoleTable(isCheck,keyWord){
    roleTable = $("#roleTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': true,
        'ordering': true,
        'lSort': true,
        'info': true,
        "scrollY": "375px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-num' ,
                'render': function (data, type, row, meta) {
                    return meta.row + 1 + meta.settings._iDisplayStart;
                }
            },
            { 'data': "roleName", 'title': '角色名称', 'className': 'row-role',
                'render': function (data, type, row, meta) {
                    return "<a href='javascript:void(0);' onclick='setRoleInfo("+row.roleId+")'>"+row.roleName+"</span>";
                }
            },
            { 'data': "permissionNames", 'title': '所含权限', 'className': 'row-perm' ,
                'render': function (data, type, row, meta) {
                    return "<span title='"+row.permissionNames+"' style='cursor:pointer;'>"+row.permissionNames+"</span>";
                }
            },
            { 'data': "commonRegionName", 'title': '管理区域', 'className': 'row-area' }
        ],
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '检索:',  
            'lengthMenu': ' _MENU_ ',  
            'zeroRecords': '没有数据',  
            'paginate': {  
                'first':      '首页',  
                'last':       '尾页',  
                'next':       '下一页',  
                'previous':   '上一页'  
            },  
            'info': '总_TOTAL_个',  
            'infoEmpty': '没有数据'
        },
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.parentRoleCode = roleCode;
            param.includChild = isCheck;
            param.keyWord = keyWord;
            $http.get('/system/sysRole/listPage', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
                loading.screenMaskDisable('container');
            }, function (err) {
                loading.screenMaskDisable('container');
            })
        }
    });
}

// 搜索角色
function search () {
    loading.screenMaskEnable('container');
    query = $('.ui-input-search').val();
    initRoleTable(isCheck, query);
    // loading.screenMaskDisable('container');
}

function boxClick(){            //点击复选框
    if(lChBox.checked == true){
        isCheck = 1;
        if(isIE8){
            $(".ui-checkbox").css("background-position","0px -40px");
        }
    }else{
        isCheck = 0;
        if(isIE8){
            $(".ui-checkbox").css("background-position","0px 0px");
        }
    }
    // initPosTable(isCheck,'');
}

function boxClick(){            //点击复选框
    sortFlag = 0;
    if(lChBox.checked == true){
        isCheck = 1;
        if(isIE8){
            $(".ui-checkbox").css("background-position","0px -40px");
        }
    }else{
        isCheck = 0;
        if(isIE8){
            $(".ui-checkbox").css("background-position","0px 0px");
        }
    }
}
    
function setRoleInfo(id){
    window.location.href = "roleInfo.html?roleId="+roleId+"&id="+id+"&roleCode="+roleCode;
}

$("#roleName").on("click",function(){
    window.location.href = "roleInfo.html?roleId="+roleId+"&id="+roleId+"&roleCode="+roleCode;
});

$("#infoBtn").on('click',function(){
    window.location.href = "roleInfo.html?roleId="+roleId+"&id="+roleId+"&roleCode="+roleCode;
})

$("#addBtn").on('click',function(){
    window.location.href = "add.html?roleId="+roleId+"&roleCode="+roleCode;
})

$('.breadcrumb').html(parent.getRoleExtInfo(1));
getRolePerm();
initRoleTable(isCheck,"");
