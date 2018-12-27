var table = $("#posTable").DataTable({
    'data': posData,
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
        { 'data': "position", 'title': '职位名称', 'className': 'row-pos',
            'render': function (data, type, row, meta) {
                return "<a href='javascript:void(0);' onclick='setPosInfo()'>"+row.position+"</span>";
            }
        },
        { 'data': null, 'title': '所含角色', 'className': 'row-role' ,
            'render': function (data, type, row, meta) {
                return "<span class='perTab'>标准树维护</span><span class='perTab'>管理人员</span>";
            }
        },
        { 'data': "area", 'title': '管理区域', 'className': 'row-area' },
        { 'data': "orgNum", 'title': '职位授予组织数', 'className': 'row-orgNum' },
        { 'data': "userNum", 'title': '职位授予用户数', 'className': 'row-userNum' }
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

function setPosInfo(){
    window.location.href = "posInfo.html";
}

$("#addBtn").on('click',function(){
    window.location.href = "add.html";
})
