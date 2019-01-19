var resSelectedList = [];
var cancelList = [];
var rowTotal;
var rowTotalSelr;
var resSelectedTable;
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

initSelectedTable("");
$("#addBtn").html("添加 >>");
$("#delBtn").html("<< 移除");