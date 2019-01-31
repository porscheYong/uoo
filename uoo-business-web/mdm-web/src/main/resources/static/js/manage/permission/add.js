var funcResSelectedList = [];   //已选择表格的功能数据
var menuResSelectedList = [];   //已选择表格的菜单数据
var elemResSelectedList = [];   //已选择表格的元素数据
var locationList = [];
var locationCode;
var locId = 0;

var funcResSelectedTable;   //功能资源
var funcResInfoTable;

var menuResSelectedTable;   //菜单资源
var menuResInfoTable;

var elemResSelectedTable;   //元素资源
var elemResInfoTable;

var funcData;
var menuData;
var elemData;
var query;
var toastr = window.top.toastr;

//获取功能资源数据
function getFuncRes(keyWord){
    $http.get('/system/sysFunction/listAll', {
        pageSize : 10000,
        pageNo : 1,
        keyWord : keyWord
    }, function (data) {
        funcData = data.records;
        initFuncTable(data.records);
    }, function (err) {
    });
}

//获取菜单资源数据
function getMenuRes(keyWord){
    $http.get('/system/sysMenu/listPage', {
        pageSize : 1000,
        pageNo : 1,
        keyWord : keyWord
    }, function (data) {
        menuData = data.records;
        initMenuTable(data.records);
    }, function (err) {
    });
}

//获取元素资源数据
function getElemRes(keyWord){
    $http.get('/system/SysElement/list', {
        pageSize : 1000,
        pageNo : 1,
        keyWord : keyWord
    }, function (data) {
        elemData = data.records;
        initElemTable(data.records);
    }, function (err) {
    });
}

//功能资源
function initFuncTable(results){
    funcResInfoTable = $("#funcResInfoTable").DataTable({
        'data': results,
        'destroy':true,
        'searching': true,
        'autoWidth': true,
        'ordering': false,
        'paging': true,
        'info': false,
        "lengthChange": false,
        "scrollY": "190px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '选择', 'className': 'row-select' ,
                'render': function (data, type, row, meta) {
                    return "<input type='checkbox' id='func_"+row.funcId+"' name='checkbox'><label for='func_"+row.funcId+"' class='ui-checkbox'></label>";
                }
            },
            { 'data': "funcName", 'title': '功能名称', 'className': 'row-fName'},
            { 'data': "funcCode", 'title': '功能编码', 'className': 'row-fCode'}
        ],
        "iDisplayLength":5,
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '',   
            'zeroRecords': '没有数据',  
            'paginate': {  
                'first':      '首页',  
                'last':       '尾页',  
                'next':       '下一页',  
                'previous':   '上一页'  
            },   
            'infoEmpty': '没有数据'
        }
    });

    inputClick("funcResInfoTable");
    $("#funcResInfoTable_filter").parent().prev().css("display","none");
    $(".dataTables_filter input").attr("placeholder","搜索资源");
}

//初始化功能已选表格
function initFuncSelectedTable(selectedList){
    funcResSelectedTable = $("#funcResSelectedTable").DataTable({
        'data': selectedList,
        'destroy':true,
        'searching': false,
        'autoWidth': true,
        'ordering': false,
        'paging': true,
        'info': false,
        "lengthChange": false,
        "scrollY": "190px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '选择', 'className': 'row-select' ,
                'render': function (data, type, row, meta) {
                    return "<input type='checkbox' id='funcS_"+row.funcId+"' name='checkbox'><label for='funcS_"+row.funcId+"' class='ui-checkbox'></label>";
                }
            },
            { 'data': "funcName", 'title': '功能名称', 'className': 'row-fName'},
            { 'data': "funcCode", 'title': '功能编码', 'className': 'row-fCode'}
        ],
        "iDisplayLength":5,
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
            'infoEmpty': '没有数据'
        }
    });

    inputClick("funcResSelectedTable");
}

//添加功能资源
function addFuncRes(){
    var codeList = [];      //要添加至已选择列表的功能数据code
    var resList = getSelectRow(funcResInfoTable);
    if(funcResSelectedList.length != 0){
        for(var j=0;j<funcResSelectedList.length;j++){
            codeList.push(funcResSelectedList[j].funcCode);
        }
    }

    for(var i=0;i<resList.length;i++){
        if(codeList.indexOf(resList[i].funcCode) == -1){
            funcResSelectedList.unshift(resList[i]);
        }
    }
    initFuncSelectedTable(funcResSelectedList);
    initFuncTable(funcData);
    inputClick("funcResInfoTable");
    inputClick("funcResSelectedTable");
}

//移除功能资源
function delFuncRes(){
    var saveData = [];
    var codeList = [];
    var allData = funcResSelectedTable.data();
    var delResList = getSelectRow(funcResSelectedTable);

    for(var i=0;i<delResList.length;i++){
        codeList.push(delResList[i].funcCode);
    }

    for(var j=0;j<allData.length;j++){
        if(codeList.indexOf(allData[j].funcCode) == -1){
            saveData.push(allData[j]);
        }
    }
    funcResSelectedList = saveData;
    initFuncSelectedTable(saveData);
    inputClick("funcResSelectedTable");
    // console.log(funcResSelectedList);
}

// ************************************************************************
//菜单资源
function initMenuTable(results){
    menuResInfoTable = $("#menuResInfoTable").DataTable({
        'data': results,
        'destroy':true,
        'searching': true,
        'autoWidth': true,
        'ordering': false,
        'paging': true,
        'info': false,
        "lengthChange": false,
        "scrollY": "190px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '选择', 'className': 'row-select' ,
                'render': function (data, type, row, meta) {
                    return "<input type='checkbox' id='menu_"+row.menuId+"' name='checkbox'><label for='menu_"+row.menuId+"' class='ui-checkbox'></label>";
                }
            },
            { 'data': "menuName", 'title': '菜单名称', 'className': 'row-fName'},
            { 'data': "menuCode", 'title': '菜单编码', 'className': 'row-fCode'}
        ],
        "iDisplayLength":5,
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '',  
            'lengthMenu': ' _MENU_ ',  
            'zeroRecords': '没有数据',  
            'paginate': {  
                'first':      '首页',  
                'last':       '尾页',  
                'next':       '下一页',  
                'previous':   '上一页'  
            },   
            'infoEmpty': '没有数据'
        }
    });

    inputClick("menuResInfoTable");
    $("#menuResInfoTable_filter").parent().prev().css("display","none");
    $(".dataTables_filter input").attr("placeholder","搜索资源");
}

//初始化菜单已选表格
function initMenuSelectedTable(selectedList){
    menuResSelectedTable = $("#menuResSelectedTable").DataTable({
        'data': selectedList,
        'destroy':true,
        'searching': false,
        'autoWidth': true,
        'ordering': false,
        'paging': true,
        'info': false,
        "lengthChange": false,
        "scrollY": "190px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '选择', 'className': 'row-select' ,
                'render': function (data, type, row, meta) {
                    return "<input type='checkbox' id='menuS_"+row.menuId+"' name='checkbox'><label for='menuS_"+row.menuId+"' class='ui-checkbox'></label>";
                }
            },
            { 'data': "menuName", 'title': '功能名称', 'className': 'row-fName'},
            { 'data': "menuCode", 'title': '功能编码', 'className': 'row-fCode'}
        ],
        "iDisplayLength":5,
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
            'infoEmpty': '没有数据'
        }
    });

    inputClick("menuResSelectedTable");
}

//添加菜单资源
function addMenuRes(){
    var codeList = [];      //要添加至已选择列表的功能数据code
    var resList = getSelectRow(menuResInfoTable);
    if(menuResSelectedList.length != 0){
        for(var j=0;j<menuResSelectedList.length;j++){
            codeList.push(menuResSelectedList[j].menuCode);
        }
    }

    for(var i=0;i<resList.length;i++){
        if(codeList.indexOf(resList[i].menuCode) == -1){
            menuResSelectedList.unshift(resList[i]);
        }
    }
    initMenuSelectedTable(menuResSelectedList);
    initMenuTable(menuData);
    inputClick("menuResInfoTable");
    inputClick("menuResSelectedTable");
}

//移除菜单资源
function delMenuRes(){
    var saveData = [];
    var codeList = [];
    var allData = menuResSelectedTable.data();
    var delResList = getSelectRow(menuResSelectedTable);

    for(var i=0;i<delResList.length;i++){
        codeList.push(delResList[i].menuCode);
    }

    for(var j=0;j<allData.length;j++){
        if(codeList.indexOf(allData[j].menuCode) == -1){
            saveData.push(allData[j]);
        }
    }
    menuResSelectedList = saveData;
    initMenuSelectedTable(saveData);
    inputClick("menuResSelectedTable");
    // console.log(menuResSelectedList);
}


// ************************************************************************
//元素资源
function initElemTable(results){
    elemResInfoTable = $("#elemResInfoTable").DataTable({
        'data': results,
        'destroy':true,
        'searching': true,
        'autoWidth': true,
        'ordering': false,
        'paging': true,
        'info': false,
        "lengthChange": false,
        "scrollY": "190px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '选择', 'className': 'row-select' ,
                'render': function (data, type, row, meta) {
                    return "<input type='checkbox' id='elem_"+row.elementId+"' name='checkbox'><label for='elem_"+row.elementId+"' class='ui-checkbox'></label>";
                }
            },
            { 'data': "elementName", 'title': '元素名称', 'className': 'row-fName'},
            { 'data': "elementCode", 'title': '元素编码', 'className': 'row-fCode'}
        ],
        "iDisplayLength":5,
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '',  
            'lengthMenu': ' _MENU_ ',  
            'zeroRecords': '没有数据',  
            'paginate': {  
                'first':      '首页',  
                'last':       '尾页',  
                'next':       '下一页',  
                'previous':   '上一页'  
            },   
            'infoEmpty': '没有数据'
        }
    });

    inputClick("elemResInfoTable");
    $("#elemResInfoTable_filter").parent().prev().css("display","none");
    $(".dataTables_filter input").attr("placeholder","搜索资源");
}

//初始化元素已选表格
function initElemSelectedTable(selectedList){
    elemResSelectedTable = $("#elemResSelectedTable").DataTable({
        'data': selectedList,
        'destroy':true,
        'searching': false,
        'autoWidth': true,
        'ordering': false,
        'paging': true,
        'info': false,
        "lengthChange": false,
        "scrollY": "190px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '选择', 'className': 'row-select' ,
                'render': function (data, type, row, meta) {
                    return "<input type='checkbox' id='elemS_"+row.elementId+"' name='checkbox'><label for='elemS_"+row.elementId+"' class='ui-checkbox'></label>";
                }
            },
            { 'data': "elementName", 'title': '元素名称', 'className': 'row-fName'},
            { 'data': "elementCode", 'title': '元素编码', 'className': 'row-fCode'}
        ],
        "iDisplayLength":5,
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
            'infoEmpty': '没有数据'
        }
    });

    inputClick("elemResSelectedTable");
}

//添加元素资源
function addElemRes(){
    var codeList = [];      //要添加至已选择列表的功能数据code
    var resList = getSelectRow(elemResInfoTable);
    if(elemResSelectedList.length != 0){
        for(var j=0;j<elemResSelectedList.length;j++){
            codeList.push(elemResSelectedList[j].elementCode);
        }
    }

    for(var i=0;i<resList.length;i++){
        if(codeList.indexOf(resList[i].elementCode) == -1){
            elemResSelectedList.unshift(resList[i]);
        }
    }
    initElemSelectedTable(elemResSelectedList);
    initElemTable(elemData);
    inputClick("elemResInfoTable");
    inputClick("elemResSelectedTable");
}

//移除元素资源
function delElemRes(){
    var saveData = [];
    var codeList = [];
    var allData = elemResSelectedTable.data();
    var delResList = getSelectRow(elemResSelectedTable);

    for(var i=0;i<delResList.length;i++){
        codeList.push(delResList[i].elementCode);
    }

    for(var j=0;j<allData.length;j++){
        if(codeList.indexOf(allData[j].elementCode) == -1){
            saveData.push(allData[j]);
        }
    }
    elemResSelectedList = saveData;
    initElemSelectedTable(saveData);
    inputClick("elemResSelectedTable");
}

//复选框点击事件
function inputClick(tableName){
    $('#'+tableName).delegate('tbody tr td input','click',function(){
        if ($(this).parent().parent().hasClass('selected') ) {
            $(this).parent().parent().removeClass('selected');
            $(this).removeAttr("checked");
        } else {
            $(this).parent().parent().addClass('selected');
            $(this).prop("checked",true);
        }
    })
}

//获取选中行数据
function getSelectRow (table) {
    return table.rows('.selected').data();
}

// 搜索资源
function search () {
    query = $('.ui-input-search').val();
    getFuncRes(query);
}

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
    $('#location').tagsInput();
  }

//管理区域选择
function openLocationDialog() {
    if(locationList.length != 0){
        locId = locationList[0].id;
    }
    parent.layer.open({
        type: 2,
        title: '管理区域',
        shadeClose: true,
        shade: 0.8,
        area: ['40%', '70%'],
        maxmin: true,
        content: '/inaction/manage/permission/locationDialog.html?locId='+locId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            var checkNode = iframeWin.checkNode;
            $('#location').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            if(checkNode.length != 0){
                locationList = checkNode;
                locationCode = checkNode[0].extParams.locCode;
                locId = checkNode[0].id;
            }
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//新增权限
function addPermission(){
    var funcRels = [];
    var menuRels = [];
    var elementRels = [];
    for(var i=0;i<funcResSelectedList.length;i++){
        funcRels.push({"funcCode":funcResSelectedList[i].funcCode});
    }
    for(var j=0;j<menuResSelectedList.length;j++){
        menuRels.push({"menuCode":menuResSelectedList[j].menuCode});
    }
    for(var k=0;k<elemResSelectedList.length;k++){
        elementRels.push({"elementCode":elemResSelectedList[k].elementCode});
    }
    $http.post('/system/sysPermission/add', JSON.stringify({  
        funcRels : funcRels,
        menuRels : menuRels,
        elementRels : elementRels,
        notes : $("#notes").val(),
        permissionName : $("#permissionName").val(),
        permissionCode : $("#permissionCode").val(),
        regionNbr : locationCode,
        statusCd : $("#statusCd").val(),
        permissionDesc : $("#permissionDesc").val()
    }), function (message) {
        backToList();
        toastr.success("创建成功！");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}

function backToList(){
    window.location.href = "index.html";
}

// datatable应用于tab切换出现表头错位
$('#myTabs a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
})

getFuncRes("");
getMenuRes("");
getElemRes("");
initFuncSelectedTable("");
initMenuSelectedTable("");
initElemSelectedTable("");
$("#addBtn").html("添加 >>");
$("#delBtn").html("<< 移除");
$("#addBtn2").html("添加 >>");
$("#delBtn2").html("<< 移除");
$("#addBtn3").html("添加 >>");
$("#delBtn3").html("<< 移除");