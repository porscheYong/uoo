var id = getQueryString('id');
var roleId = getQueryString('roleId');
var roleFullName = getQueryString('roleFullName');
var roleCode = getQueryString('roleCode');
var locationList = [];
var parRoleList = [];
var permList = [];

//获取角色信息
function getRoleInfo(){
    $http.get('/system/sysRole/get/'+id, {
    }, function (data) {
        $("#roleNameTitle").html(data.roleName);
        initRoleInfo(data);
    }, function (err) {
        // loading.screenMaskDisable('container');
    });
}

//初始化角色信息
function initRoleInfo(result){
    $("#perms").empty();
    isNull("roleCodeLable",result.roleCode);
    isNull("parRoleLable",result.parentRoleName);
    isNull("areaLable",result.commonRegionName);
    isNull("remarkLable",result.notes);
    isNull("sortLable",result.sortNum);
    $("#stateLable").text("有效");
    if(result.permissionNames){
        permList = result.permissionNames.split(',');
    }
    for(var i=0;i<permList.length;i++){ 
        $("#perms").append("<span class='perTab'>"+permList[i]+"</span>");
    }

    isInputNull("roleName",result.roleName);
    isInputNull("roleCode",result.roleCode);
    isInputNull("sortNum",result.sortNum);
    isInputNull("notes",result.notes);
    $('#perm').addTag(permList);
    // if(result.commonRegionName){
    //     locationList = [{"id":result.regionNbr,"name":result.regionName}];
    //     $('#location').addTag(locationList);
    // }
    // if(result.parRole){
    //     parRoleList = [{"id":result.pPositionId,"name":result.pPositionName}];
    //     $('#parRole').addTag(parRoleList);
    // }
}

//判断返回值是否为null
function isNull(el,str){
    if(str != null){
        $("#"+el).text(str);
    }
}

//判断填入input中的值是否为null
function isInputNull(el,str){
    if(str != null){
        $("#"+el).val(str);
    }
}

function editInfo(){
    $("#roleInfo").css("display","none");
    $("#editInfo").css("display","block");
}

function cancel(){
    $("#roleInfo").css("display","block");
    $("#editInfo").css("display","none");
}

var logTable = $("#logTable").DataTable({
    'data': logData,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'initComplete': function (settings, json) {
        // console.log(settings, json)
    },
    "scrollY": "375px",
    'scrollCollapse': true,
    'columns': [
        { 'data': "num", 'title': '订单号', 'className': 'row-num' },
        { 'data': "explain", 'title': '操作说明', 'className': 'row-explain'},
        { 'data': "person", 'title': '操作人', 'className': 'row-psn' },
        { 'data': "org", 'title': '操作人组织', 'className': 'row-org' },
        { 'data': "acct", 'title': '操作账号', 'className': 'row-acct' },
        { 'data': "date", 'title': '时间', 'className': 'row-date' },
        { 'data': "state", 'title': '状态', 'className': 'row-state' }
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

// datatable应用于tab切换出现表头错位
$('#myTabs a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
})

//返回
function backToList(){
    window.location.href = "list.html?roleCode="+roleCode+"&roleId="+roleId;
}

$(".breadcrumb").html(roleFullName);
getRoleInfo();