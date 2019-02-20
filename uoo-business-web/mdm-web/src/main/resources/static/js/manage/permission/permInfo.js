var permId = getQueryString('permId');

var funcResSelectedList = [];   //已选择表格的功能数据
var menuResSelectedList = [];   //已选择表格的菜单数据
var elemResSelectedList = [];   //已选择表格的元素数据
var fileResSelectedList = [];   //已选择表格的文件数据
var dataResSelectedList = [];   //已选择的数据资源数据
var locationList = [];
var locationCode;

var funcResSelectedTable;   //功能资源
var funcResInfoTable;

var menuResSelectedTable;   //菜单资源
var menuResInfoTable;

var elemResSelectedTable;   //元素资源
var elemResInfoTable;

var fileResSelectedTable;   //文件资源
var fileResInfoTable;

var funcData;
var menuData;
var elemData;
var fileData;
var locId = 0;
var toastr = window.top.toastr;
var loading = parent.loading;

loading.screenMaskEnable('LAY_app_body');

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var tab = $('#tab_content5');
    var infoDiv = $("#editInfo");
    formValidate = new Validate(tab);
    infoValidate = new Validate(infoDiv);
    formValidate.immediate();
    infoValidate.immediate();
    tab.find(':input').each(function () {
      $(this).bind({
          paste : function(){
              formValidate.isPass($(this));
              $(this).removeClass('error');
          }
      });
    });

    infoDiv.find(':input').each(function () {
        $(this).bind({
            paste : function(){
                infoValidate.isPass($(this));
                $(this).removeClass('error');
            }
        });
    });
  });

//获取权限信息 
function getSysPermission(){
    $http.get('/system/sysPermission/get/'+permId, {
    }, function (data) {
        initPermInfo(data);
        // loading.screenMaskDisable('LAY_app_body');
    }, function (err) {
        loading.screenMaskDisable('LAY_app_body');
    });
}

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
        loading.screenMaskDisable('LAY_app_body');
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
        loading.screenMaskDisable('LAY_app_body');
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
        loading.screenMaskDisable('LAY_app_body');
    });
}

//获取文件资源数据
function getFileRes(search){
    $http.get('/sysFile/getSysFilePage', {
        pageSize : 1000,
        pageNo : 1,
        search : search
    }, function (data) {
        fileData = data.records;
        initFileTable(data.records);
    }, function (err) {
        loading.screenMaskDisable('LAY_app_body');
    });
}

//获取表名称列表
function getTab(list){
    $http.get('/system/sysDataRule/getTab', {
    }, function (data) {
        initDataSelectedGroups(list,data);      
    }, function (err) {
        toastr.error("获取信息失败！");
        loading.screenMaskDisable('LAY_app_body');
    } )
}

//初始化权限信息
function initPermInfo(result){
    isInputNull("permissionName",result.permissionName);
    isInputNull("permissionCode",result.permissionCode);
    isInputNull("permissionDesc",result.permissionDesc);
    isInputNull("notes",result.notes);

    if(result.regionName){
        locationCode = result.regionNbr;
        locationList = [{"id":result.locId,"name":result.regionName}];
        $('#location').addTag(locationList);
    }

    funcResSelectedList = result.funcs;
    menuResSelectedList = result.menus;
    elemResSelectedList = result.elements;
    fileResSelectedList = result.files;
    dataResSelectedList = result.dataRuleGroups;
    initFuncSelectedTable(funcResSelectedList);
    initMenuSelectedTable(menuResSelectedList);
    initElemSelectedTable(elemResSelectedList);
    initFileSelectedTable(fileResSelectedList);
    getTab(dataResSelectedList);
    // loading.screenMaskDisable('LAY_app_body');
}

//判断填入input中的值是否为null
function isInputNull(el,str){
    if(str != null){
        $("#"+el).val(str);
    }
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

// ************************************************************************
//文件资源
function initFileTable(results){
    fileResInfoTable = $("#fileResInfoTable").DataTable({
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
                    return "<input type='checkbox' id='file_"+row.fileId+"' name='checkbox'><label for='file_"+row.fileId+"' class='ui-checkbox'></label>";
                }
            },
            { 'data': "fileName", 'title': '文件名称', 'className': 'row-fName'},
            { 'data': "fileId", 'title': '文件编码', 'className': 'row-fCode'}
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

    inputClick("fileResInfoTable");
    $("#fileResInfoTable_filter").parent().prev().css("display","none");
    $(".dataTables_filter input").attr("placeholder","搜索资源");
}

//初始化元素已选表格
function initFileSelectedTable(selectedList){
    fileResSelectedTable = $("#fileResSelectedTable").DataTable({
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
                    return "<input type='checkbox' id='fileS_"+row.fileId+"' name='checkbox'><label for='fileS_"+row.fileId+"' class='ui-checkbox'></label>";
                }
            },
            { 'data': "fileName", 'title': '文件名称', 'className': 'row-fName'},
            { 'data': "fileId", 'title': '文件编码', 'className': 'row-fCode'}
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

    inputClick("fileResSelectedTable");
}

//添加文件资源
function addFileRes(){
    var codeList = [];      //要添加至已选择列表的功能数据code
    var resList = getSelectRow(fileResInfoTable);
    if(fileResSelectedList.length != 0){
        for(var j=0;j<fileResSelectedList.length;j++){
            codeList.push(fileResSelectedList[j].fileId);
        }
    }

    for(var i=0;i<resList.length;i++){
        if(codeList.indexOf(resList[i].fileId) == -1){
            fileResSelectedList.unshift(resList[i]);
        }
    }
    initFileSelectedTable(fileResSelectedList);
    initFileTable(fileData);
    inputClick("fileResInfoTable");
    inputClick("fileResSelectedTable");
}

//移除文件资源
function delFileRes(){
    var saveData = [];
    var codeList = [];
    var allData = fileResSelectedTable.data();
    var delResList = getSelectRow(fileResSelectedTable);

    for(var i=0;i<delResList.length;i++){
        codeList.push(delResList[i].fileId);
    }

    for(var j=0;j<allData.length;j++){
        if(codeList.indexOf(allData[j].fileId) == -1){
            saveData.push(allData[j]);
        }
    }
    fileResSelectedList = saveData;
    initFileSelectedTable(saveData);
    inputClick("fileResSelectedTable");
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

//获取数据资源的数据
function getDataRules(){
    var dataRuleGroupList = [];
    var orgTreeId = $("#businessOrg").val();

    for(var i=0;i<allIdList.length;i++){
        var groupId = 0;
        var dataRuleGroup;
        if($("#group_"+allIdList[i].gId).attr("groupId") != -1){
            groupId = $("#group_"+allIdList[i].gId).attr("groupId");
        }
        dataRuleGroup = {
            "andOr": $("#relation_"+allIdList[i].gId).val(),
            "dataRuleGroupId" : groupId,
            "dataRules": [],
            "orgTreeId": orgTreeId,
            "sort": i
        };
        dataRuleGroupList.push(dataRuleGroup);
    }   

    for(var j=0;j<dataRuleGroupList.length;j++){
        for(var k=0;k<allIdList[j].cId.length;k++){
            var cId = allIdList[j].cId[k];
            var dataRule;
            var colName;
            var dataRuleId = 0;
            if($("#colName_"+cId).find("option:selected").text() == "无字段"){
                colName = "";
            }else{
                colName = $("#colName_"+cId).find("option:selected").text();
            }

            if($("#tabName_"+cId).find("option:selected").attr("conId") != -1){
                dataRuleId = $("#tabName_"+cId).find("option:selected").attr("conId");
            }

            dataRule = {
                "andOr": "and", 
                "colName": colName,
                "colValue": $("#colValue_"+cId).val(),
                "dataRuleId": dataRuleId,
                "ruleOperator": $("#ruleOperator_"+cId).val(),
                "sort": k,
                "tabName": $("#tabName_"+cId).find("option:selected").text()
            };
            dataRuleGroupList[j].dataRules.push(dataRule);
        }
    }
    return dataRuleGroupList;
}

//更新权限
function updatePermission(){
    loading.screenMaskEnable('LAY_app_body');
    if(!formValidate.isAllPass()){
        loading.screenMaskDisable('LAY_app_body');
        return;
    }
    var funcRels = [];
    var menuRels = [];
    var elementRels = [];
    var fileRels = [];
    var dataRuleGroups = getDataRules();
    for(var i=0;i<funcResSelectedList.length;i++){
        funcRels.push({"funcCode":funcResSelectedList[i].funcCode,"relId":funcResSelectedList[i].relId});
    }
    for(var j=0;j<menuResSelectedList.length;j++){
        menuRels.push({"menuCode":menuResSelectedList[j].menuCode,"relId":menuResSelectedList[j].relId});
    }
    for(var k=0;k<elemResSelectedList.length;k++){
        elementRels.push({"elementCode":elemResSelectedList[k].elementCode,"relId":elemResSelectedList[k].relId});
    }
    for(var l=0;l<fileResSelectedList.length;l++){
        fileRels.push({"fileId":fileResSelectedList[l].fileId});
    }

    $http.post('/system/sysPermission/update', JSON.stringify({  
        funcRels : funcRels,
        menuRels : menuRels,
        elementRels : elementRels,
        fileRels : fileRels,
        dataRuleGroups : dataRuleGroups,
        notes : $("#notes").val(),
        permissionName : $("#permissionName").val(),
        permissionCode : $("#permissionCode").val(),
        regionNbr : locationCode,
        statusCd : $("#statusCd").val(),
        permissionDesc : $("#permissionDesc").val(),
        permissionId : permId
    }), function (message) {
        reflashPermInfo();
        toastr.success("保存成功！");
    }, function (err) {
        loading.screenMaskDisable('LAY_app_body');
        // toastr.error("保存失败！");
    })
}

//删除权限
function deletePermission(){
    parent.layer.confirm('是否删除该权限？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
            loading.screenMaskEnable('LAY_app_body');
            $http.get('/system/sysPermission/delete/'+permId, {  
            }, function (message) {
                loading.screenMaskDisable('LAY_app_body');
                backToList();
                parent.layer.close(index);
                toastr.success("删除成功！");
            }, function (err) {
                parent.layer.close(index);
                // toastr.error("删除失败！");
            })
        }, function(){
      
        });
}

function backToList(){
    window.location.href = "index.html";
}

//刷新权限页面
function reflashPermInfo(){
    // loading.screenMaskEnable('LAY_app_body');
    loading.screenMaskDisable('LAY_app_body');
    window.location.href = "permInfo.html?permId="+permId;
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
getFileRes("");
getSysPermission();
$("#addBtn").html("添加 >>");
$("#delBtn").html("<< 移除");
$("#addBtn2").html("添加 >>");
$("#delBtn2").html("<< 移除");
$("#addBtn3").html("添加 >>");
$("#delBtn3").html("<< 移除");
$("#addBtn4").html("添加 >>");
$("#delBtn4").html("<< 移除");