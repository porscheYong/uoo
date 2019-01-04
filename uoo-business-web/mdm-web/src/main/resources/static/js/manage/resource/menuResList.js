var table = $("#menuResTable").DataTable({
    'data': menuResData,
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
        { 'data': "menuName", 'title': '菜单名称', 'className': 'row-mName',
            'render': function (data, type, row, meta) {
                return "<a href='javascript:void(0);' onclick='setMenuResInfo()'>"+row.menuName+"</span>";
            }
        },
        { 'data': "menuNum", 'title': '菜单编码', 'className': 'row-mNum'},
        { 'data': "menuLevel", 'title': '菜单级别', 'className': 'row-mLevel'},
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

function setMenuResInfo(){
    window.location.href = "menuResInfo.html";
}

$("#addBtn").on('click',function(){
    window.location.href = "addMenuRes.html";
})