var table = $("#dataResTable").DataTable({
    'data': dataResData,
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
        { 'data': "tableName", 'title': '表名称', 'className': 'row-tName',
            'render': function (data, type, row, meta) {
                return "<a href='javascript:void(0);' onclick='setDataResInfo()'>"+row.tableName+"</span>";
            }
        },
        { 'data': "fieldName", 'title': '字段名称', 'className': 'row-fName'},
        { 'data': "rule", 'title': '规则操作符', 'className': 'row-rule'},
        { 'data': "fieldVal", 'title': '字段值', 'className': 'row-fVal'},
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

function setDataResInfo(){
    window.location.href = "dataResInfo.html";
}

$("#addBtn").on('click',function(){
    window.location.href = "addDataRes.html";
})