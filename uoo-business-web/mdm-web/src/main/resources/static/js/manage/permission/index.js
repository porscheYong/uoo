var table = $("#permTable").DataTable({
    'data': permData,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'initComplete': function (settings, json) {

    },
    "scrollY": "375px",
    'scrollCollapse': true,
    'columns': [
        { 'data': null, 'title': '序号', 'className': 'row-num' ,
            'render': function (data, type, row, meta) {
                return meta.row + 1 + meta.settings._iDisplayStart;
            }
        },
        { 'data': "permName", 'title': '权限名称', 'className': 'row-pName',
            'render': function (data, type, row, meta) {
                return "<a href='javascript:void(0);' onclick='setPermInfo()'>"+row.permName+"</span>";
            }
        },
        { 'data': "area", 'title': '管理区域', 'className': 'row-area'},
        { 'data': "permCode", 'title': '权限编码', 'className': 'row-pCode'},
        { 'data': "statusCd", 'title': '状态', 'className': 'row-statusCd'}
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

function setPermInfo(){
    window.location.href = "permInfo.html";
}

$("#addBtn").on('click',function(){
    window.location.href = "addPerm.html";
})