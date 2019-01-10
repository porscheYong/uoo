var roleId = getQueryString('roleId');
var roleName = getQueryString('roleName');
var lChBox =document.getElementById("lowerCheckBox"); 

var table = $("#roleTable").DataTable({
    'data': results,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'initComplete': function (settings, json) {
        console.log(settings, json)
    },
    "scrollY": "375px",
    'scrollCollapse': true,
    'columns': [
        { 'data': "num", 'title': '序号', 'className': 'row-num' },
        { 'data': "role", 'title': '角色名称', 'className': 'row-role',
            'render': function (data, type, row, meta) {
                return "<a href='javascript:void(0);' onclick='setRoleInfo()'>"+row.role+"</span>";
            }
        },
        { 'data': null, 'title': '所含权限', 'className': 'row-perm' ,
            'render': function (data, type, row, meta) {
                return "<span class='perTab'>修改组织基础信息</span><span class='perTab'>查看组织</span>";
            }
        },
        { 'data': "area", 'title': '管理区域', 'className': 'row-area' }
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
    'dom': '<"top"f>t<"bottom"ipl>'
});

function setRoleInfo(){
    window.location.href = "roleInfo.html";
}

$("#addBtn").on('click',function(){
    window.location.href = "add.html";
})
