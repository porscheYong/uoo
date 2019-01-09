var id = getQueryString('id');
var toastr = window.top.toastr;

function getResInfo(){  
    $http.get('/sysFile/getSysFile', {  
        id : id
    }, function (data) {
        initFileInfo(data);
    }, function (err) {
        toastr.error("获取信息失败！");
    })
}  

//初始化文件资源
function initFileInfo(result){
    isNull("fileName",result.fileName);
    isNull("fileType",result.fileType);
    isNull("fileAddr",result.fileAddr);
    isNull("fileSize",result.fileSize);
    isNull("fileVersion",result.fileVersion);
    isNull("fileDesc",result.fileDesc);
}

//判断是否为null
function isNull(el,str){
    if(str != null){
        $("#"+el).val(str);
    }
}

//更新文件资源信息
function updateRes(){
    $http.post('/sysFile/updateSysFile', JSON.stringify({  
        fileName : $("#fileName").val(),
        fileType : $("#fileType").val(),
        fileAddr : $("#fileAddr").val(),
        fileSize : $("#fileSize").val(),
        fileVersion : $("#fileVersion").val(),
        fileDesc : $("#fileDesc").val(),
        statusCd : $("#statusCd").val(),
        fileId : id
    }), function (message) {
        backToList();
        toastr.success("保存成功！");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}

//删除文件资源信息
function deleteRes(){
    parent.layer.confirm('是否删除该文件资源？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
            $http.post('/sysFile/deleteSysFile', JSON.stringify({  
                fileId : id
            }), function (message) {
                backToList();
                toastr.success("删除成功！");
                parent.layer.close(index);
            }, function (err) {
                toastr.error("删除失败！");
                parent.layer.close(index);
            })
        }, function(){
      
        });
}

function backToList(){
    window.location.href = "fileResList.html";
}

var logTable = $("#logTable").DataTable({
    'data': logData,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'initComplete': function (settings, json) {
        console.log(settings, json)
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