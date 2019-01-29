var permId = getQueryString('permId');

var resSelectedList = [];
var cancelList = [];
var locationList = [];
var locationCode;
var rowTotal;
var rowTotalSelr;
var resSelectedTable;
var locId = 0;

//获取权限信息 
function getSysPermission(){
    $http.get('/system/sysPermission/get/'+permId, {
    }, function (data) {
        initPermInfo(data);
    }, function (err) {
        // loading.screenMaskDisable('container');
    });
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
}

//判断填入input中的值是否为null
function isInputNull(el,str){
    if(str != null){
        $("#"+el).val(str);
    }
}

var resInfoTable = $("#resInfoTable").DataTable({
    'data': resInfoData,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'paging': false,
    'info': false,
    "scrollY": "175px",
    'scrollCollapse': true,
    'columns': [
        { 'data': null, 'title': '选择', 'className': 'row-select' ,
            'render': function (data, type, row, meta) {
                rowTotal = meta.row+1;
                return "<input type='checkbox' id='sel_"+meta.row+"' name='checkbox' onclick=''><label for='sel_"+meta.row+"' class='ui-checkbox'></label>";
            }
        },
        { 'data': "funcName", 'title': '功能名称', 'className': 'row-fName'},
        { 'data': "funcCode", 'title': '功能编码', 'className': 'row-fCode'}
    ],
    'language': {
        'emptyTable': '没有数据',  
        'loadingRecords': '加载中...',  
        'processing': '查询中...',  
        'search': '检索:',  
        'lengthMenu': ' _MENU_ ',  
        'zeroRecords': '没有数据',  
        'infoEmpty': '没有数据'
    }
});

function initSelectedTable(selectedList){
    resSelectedTable = $("#resSelectedTable").DataTable({
        'data': selectedList,
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        'paging': false,
        'info': false,
        "scrollY": "175px",
        'scrollCollapse': true,
        'columns': [
            { 'data': "id", 'title': '选择', 'className': 'row-select' ,
                'render': function (data, type, row, meta) {
                    rowTotalSelr = meta.row+1;
                    return "<input type='checkbox' id='"+row.id+"' name='checkbox' onclick=''><label for='"+row.id+"' class='ui-checkbox'></label>";
                }
            },
            { 'data': "funcName", 'title': '功能名称', 'className': 'row-fName'},
            { 'data': "funcCode", 'title': '功能编码', 'className': 'row-fCode'}
        ],
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '检索:',  
            'lengthMenu': ' _MENU_ ',  
            'zeroRecords': '没有数据',  
            'infoEmpty': '没有数据'
        }
    });
}

function addRes(){
    var sel;
    for(var i=0;i<rowTotal;i++){
        sel = document.getElementById("sel_"+i);
        if(sel.checked == true){
            resSelectedList.push({"id":i,"funcName":resInfoTable.row(i).data().funcName,"funcCode":resInfoTable.row(i).data().funcCode});
        }
    }
    initSelectedTable(resSelectedList);
    resSelectedList = [];
}

function delRes(){
    var selr;
    var id;
    for(var i=0;i<rowTotalSelr;i++){
        id = resSelectedTable.row(i).data().id;
        selr = document.getElementById(id);
        if(selr.checked == true){
            resSelectedTable.row(i).remove().draw();
            cancelList.push(id);
        }
    }
    for(var i=0;i<cancelList.length;i++){
        var sel;
        sel = document.getElementById("sel_"+cancelList[i]);
        sel.checked = false;
    }
    cancelList = [];
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

function backToList(){
    window.location.href = "index.html";
}

getSysPermission();
initSelectedTable("");
$("#addBtn").html("添加 >>");
$("#delBtn").html("<< 移除");