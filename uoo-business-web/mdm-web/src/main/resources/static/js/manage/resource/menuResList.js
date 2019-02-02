var levelList = [];
var table;
var query;
var loading = parent.loading;

//初始化菜单资源
function initMenuResTable(keyWord){
    table = $("#menuResTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        'info': true,
        "scrollY": "395px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-num' ,
                'render': function (data, type, row, meta) {
                    return meta.row + 1 + meta.settings._iDisplayStart;
                }
            },
            { 'data': "menuName", 'title': '菜单名称', 'className': 'row-mName',
                'render': function (data, type, row, meta) {
                    return "<a href='javascript:void(0);' onclick='setMenuResInfo("+row.menuId+")'>"+row.menuName+"</span>";
                }
            },
            { 'data': "menuCode", 'title': '菜单编码', 'className': 'row-mNum'},
            { 'data': "menuLevel", 'title': '菜单级别', 'className': 'row-mLevel',
                'render': function (data, type, row, meta) {
                    if(levelList.indexOf(row.menuLevel) == -1){
                        levelList.push(row.menuLevel);
                    }
                    return row.menuLevel;
                }
            },
            { 'data': "statusCd", 'title': '状态', 'className': 'row-state' ,
                'render': function (data, type, row, meta) {
                    if (row.statusCd == '1000') {
                        return '有效';
                    } else {
                        return '无效';
                    }
                }
            }
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
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.keyWord = keyWord;
            $http.get('/system/sysMenu/listPage', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
                loading.screenMaskDisable('LAY_app_body');
            }, function (err) {
                loading.screenMaskDisable('LAY_app_body');
            })
        }
    });
}

// 搜索菜单资源
function search () {
    loading.screenMaskEnable('LAY_app_body');
    query = $('.ui-input-search').val();
    initMenuResTable(query);
}

function setMenuResInfo(menuId){
    window.location.href = "menuResInfo.html?id="+menuId+"&levelCount="+levelList.length;
}

$("#addBtn").on('click',function(){
    window.location.href = "addMenuRes.html?levelCount="+levelList.length;
})

initMenuResTable("");