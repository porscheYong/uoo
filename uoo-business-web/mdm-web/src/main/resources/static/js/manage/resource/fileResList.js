var table = $("#fileResTable").DataTable({
    'data': fileResData,
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
        { 'data': "fileName", 'title': '文件名称', 'className': 'row-fName',
            'render': function (data, type, row, meta) {
                return "<a href='javascript:void(0);' onclick='setFileResInfo()'>"+row.fileName+"</span>";
            }
        },
        { 'data': "fileType", 'title': '文件类型', 'className': 'row-fType'},
        { 'data': "fileSize", 'title': '文件大小', 'className': 'row-fSize'},
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

function setFileResInfo(){
    window.location.href = "fileResInfo.html";
}

$("#addBtn").on('click',function(){
    window.location.href = "addFileRes.html";
})