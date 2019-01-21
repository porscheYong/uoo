var id = getQueryString('id');
var toastr = window.top.toastr;

function getResInfo(){  
    $http.get('/system/sysFunction/get', {  
        id : id
    }, function (data) {
        initFuncInfo(data);
    }, function (err) {
        toastr.error("获取信息失败！");
    })
}

 //初始化功能资源信息
function initFuncInfo(result){     
    $("#funcName").val(result.funcName);
    $("#funcCode").val(result.funcCode);
    $("#funcApi").val(result.funcApi);
    $("#statusCd").val(result.statusCd);
}

//更新功能资源信息
function updateRes(){
    $http.post('/system/sysFunction/update', JSON.stringify({  
        funcName : $("#funcName").val(),
        funcCode : $("#funcCode").val(),
        funcApi : $("#funcApi").val(),
        statusCd : $("#statusCd").val(),
        funcId : id
    }), function (message) {
        backToList();
        toastr.success("保存成功！");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}

//删除功能资源信息
function deleteRes(){
    parent.layer.confirm('是否删除该功能资源？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
            $http.post('/system/sysFunction/delete', JSON.stringify({  
                funcId : id
            }), function (message) {
                parent.layer.close(index);
                backToList();
                toastr.success("删除成功！");
            }, function (err) {
                parent.layer.close(index);
                toastr.error("删除失败！");
            })
        }, function(){
      
        });
}

function backToList(){
    window.location.href = "funResList.html";
}

var logTable = $("#logTable").DataTable({
    'data': logData,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'initComplete': function (settings, json) {
        // console.log(settings, json)
    },
    "scrollY": "375px",
    'scrollCollapse': true,
    'columns': [
        { 'data': "num", 'title': '订单号', 'className': 'row-num' },
        { 'data': "explain", 'title': '操作说明', 'className': 'row-explain'},
        { 'data': "person", 'title': '操作人', 'className': 'row-psn' },
        { 'data': "org", 'title': '操作人组织', 'className': 'row-org' },
        { 'data': "acct", 'title': '操作账号', 'className': 'row-acct' },
        { 'data': "date", 'title': '时间', 'className': 'row-date' },
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

// datatable应用于tab切换出现表头错位
$('#myTabs a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
})

getResInfo();

