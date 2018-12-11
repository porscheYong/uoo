function table_init1(){
    var table = {
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'paging': false,
        'info': false,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        "scrollY": "375px",
        'columns': [
            { 'data': "code", 'title': '编码', 'className': 'row-code' },
            { 'data': "classification", 'title': '分类', 'className': 'row-class' },
            { 'data': "visibility", 'title': '可见性', 'className': 'row-visibility' },
            { 'data': "number", 'title': '排序号', 'className': 'row-number' },
            { 'data': "date", 'title': '添加时间', 'className': 'row-date' }
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
    };
    return table;
}

function table_init2(){
    var table = {
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'paging': false,
        'info': false,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        "scrollY": "375px",
        'columns': [
            { 'data': "code", 'title': '编码', 'className': 'row-code' },
            { 'data': "classification", 'title': '字典名称', 'className': 'row-class' },
            { 'data': "visibility", 'title': '可见性', 'className': 'row-visibility' },
            { 'data': "number", 'title': '排序号', 'className': 'row-number' },
            { 'data': "date", 'title': '添加时间', 'className': 'row-date' }
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
    };
    return table;
}
