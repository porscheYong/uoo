// var table = $("#orgTable").DataTable({
//     'data': results,
//     'searching': false,
//     'autoWidth': false,
//     'ordering': true,
//     'paging': false,
//     'info': false,
//     'initComplete': function (settings, json) {
//         console.log(settings, json)
//     },
//     "scrollY": "105px",
//     'columns': [
//         { 'data': "number", 'title': '序号', 'className': 'row-account' },
//         { 'data': "orgtree", 'title': '组织树名称', 'className': 'row-name' },
//         { 'data': "org", 'title': '组织名称', 'className': 'row-org' }
//         // { 
//         //   'data': "userRoleName",
//         //   'title': '系统角色',
//         //   'className': 'user-role'
//         //   // 'render': function (data, type, row, meta) {
//         //   //       console.log(data, type, row, meta)
//         //   //   }
//         //  }
//     ],
//     'language': {
//         'emptyTable': '没有数据',  
//         'loadingRecords': '加载中...',  
//         'processing': '查询中...',  
//         'search': '检索:',  
//         'lengthMenu': ' _MENU_ ',  
//         'zeroRecords': '没有数据', 
//         'infoEmpty': '没有数据'
//     }
// });

var table = $("#subInfoTable").DataTable({
    'data': results1,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'paging': false,
    'info': false,
    'initComplete': function (settings, json) {
        console.log(settings, json)
    },
    "scrollY": "105px",
    'columns': [
        { 'data': "number", 'title': '序号', 'className': 'row-number' },
        { 'data': "acc", 'title': '账号名', 'className': 'row-acc' },
        { 'data': "acctype", 'title': '账号类型', 'className': 'row-acctype' },
        { 'data': "orgtree", 'title': '组织树', 'className': 'row-orgtree' },
        { 'data': "org", 'title': '归属组织', 'className': 'row-org' },
        { 'data': "state", 'title': '状态', 'className': 'row-state' }
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
        'infoEmpty': '没有数据'
    }
});