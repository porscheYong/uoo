var table = $("#orgTable").DataTable({
    'data': results,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'info': false,
    'lengthChange':false,
    'initComplete': function (settings, json) {
        console.log(settings, json)
    },
    "scrollY": "105px",
    'columns': [
        { 'data': "number", 'title': '序号', 'className': 'row-account' },
        { 'data': "orgtree", 'title': '组织树名称', 'className': 'row-name' },
        { 'data': "org", 'title': '组织名称', 'className': 'row-org' }
        // { 
        //   'data': "userRoleName",
        //   'title': '系统角色',
        //   'className': 'user-role'
        //   // 'render': function (data, type, row, meta) {
        //   //       console.log(data, type, row, meta)
        //   //   }
        //  }
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
        'info': '总_TOTAL_人',  
        'infoEmpty': '没有数据'
    },
    'pagingType': 'simple_numbers',
    'dom': '<"top"f>t<"bottom"ipl>'
});