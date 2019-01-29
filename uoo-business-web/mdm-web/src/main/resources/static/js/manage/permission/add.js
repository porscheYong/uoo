var resSelectedList = [];
var cancelList = [];
var rowTotal;
var rowTotalSelr;
var resSelectedTable;
var resInfoTable;

function getFuncRes(){
    $http.get('/system/sysFunction/listAll', {
        pageSize : 10000,
        pageNo : 1,
        keyWord : ""
    }, function (data) {
        initFuncTable(data.records);
    }, function (err) {
        // loading.screenMaskDisable('container');
    });
}

// function initFuncTable(result){
//     resInfoTable = $("#resInfoTable").DataTable({
//         'data':result,
//         'destroy':true,
//         'paging': true,
//         'searching': false,
//         'autoWidth': true,
//         'ordering': true,
//         'lSort': true,
//         'info': false,
//         "scrollY": "190px",
//         'scrollCollapse': true,
//         'columns': [
//             { 'data': null, 'title': '选择', 'className': 'row-select' ,
//                 'render': function (data, type, row, meta) {
//                     rowTotal = meta.row+1;
//                     return "<input type='checkbox' id='sel_"+meta.row+"' name='checkbox' onclick=''><label for='sel_"+meta.row+"' class='ui-checkbox'></label>";
//                 }
//             },
//             { 'data': "funcName", 'title': '功能名称', 'className': 'row-fName'},
//             { 'data': "funcCode", 'title': '功能编码', 'className': 'row-fCode'}
//         ],
//         'language': {
//             'emptyTable': '没有数据',  
//             'loadingRecords': '加载中...',  
//             'processing': '查询中...',  
//             'search': '检索:',  
//             'lengthMenu': ' _MENU_ ',  
//             'zeroRecords': '没有数据',  
//             'paginate': {  
//                 'first':      '首页',  
//                 'last':       '尾页',  
//                 'next':       '下一页',  
//                 'previous':   '上一页'  
//             },   
//             'infoEmpty': '没有数据'
//         },
//         "iDisplayLength":5,
//         'pagingType': 'simple_numbers',
//         'dom': '<"top"f>t<"bottom"ipl>',
//         'serverSide': true,  //启用服务器端分页
//         'ajax': function (data, callback, settings) {
//             var param = {};
//             param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
//             param.pageNo = (data.start / data.length) + 1;//当前页码
//             param.keyWord = "";
//             $http.get('/system/sysFunction/listAll', param, function (result) {
//                 var returnData = {};
//                 // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
//                 returnData.recordsTotal = result.total;//返回数据全部记录
//                 returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
//                 returnData.data = result.records;//返回的数据列表
//                 //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
//                 //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
//                 callback(returnData);
//             }, function (err) {
//             })
//         }
//     });
// }

function initFuncTable(results){
    resInfoTable = $("#resInfoTable").DataTable({
        'data': results,
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': false,
        'paging': true,
        'info': false,
        "scrollY": "190px",
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
}


function initSelectedTable(selectedList){
    resSelectedTable = $("#resSelectedTable").DataTable({
        'data': selectedList,
        'destroy':true,
        'searching': false,
        'autoWidth': true,
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

function backToList(){
    window.location.href = "index.html";
}

// initFuncTable();
getFuncRes();
initSelectedTable("");
$("#addBtn").html("添加 >>");
$("#delBtn").html("<< 移除");