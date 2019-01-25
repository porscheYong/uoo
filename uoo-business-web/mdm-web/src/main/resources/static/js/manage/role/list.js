var roleId = getQueryString('roleId');
var roleName = getQueryString('roleName');
var roleCode = getQueryString('roleCode');
var lChBox =document.getElementById("lowerCheckBox"); 
var roleFullName = "";
var roleTable;
var permList = [];
var isCheck = 0;


// 获取组织完整路径
function getRoleExtInfo () {
    var pathArry = parent.nodeArr;
    var pathStr = '';
    if (pathArry && pathArry.length > 0) {
        for (var i = pathArry.length - 1; i >= 0; i--) {
            var node = pathArry[i].node;
            if (pathArry[i].current) {
                pathStr +=  '<span class="breadcrumb-item"><a href="javascript:void(0);">' + node.name + '</a></span>';
            } else {
                pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+node.id+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
            }
            roleFullName += node.name + ' / '; 
        }
        roleFullName = roleFullName.toString().substring(0,roleFullName.toString().length-2);
        $('.breadcrumb').html(pathStr);
    }
}

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
            }, function (err) {
            })
        }
    });
}
    

function setRoleInfo(id){
    window.location.href = "roleInfo.html?roleId="+roleId+"&id="+id+"&roleCode="+roleCode+"&roleFullName="+roleFullName;
}

$("#addBtn").on('click',function(){
    window.location.href = "add.html?roleId="+roleId+"&roleCode="+roleCode;
})

getRoleExtInfo();
getRolePerm();
initRoleTable(isCheck,"");
