var id = getQueryString('id');
var toastr = window.top.toastr;
var logTable;

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
    initUpdateTable();
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

//初始化更新记录列表
function initUpdateTable(){
    logTable = $("#logTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': true,
        'ordering': true,
        'lSort': true,
        'info': true,
        "scrollY": "390px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '订单号', 'className': 'row-num', 
                'render': function (data, type, row, meta) {
                    return "<span style='cursor:pointer;' title='"+row.batchNumber+"'>"+row.batchNumber+"</span>";
                }     
            },
            { 'data': "operateType", 'title': '操作说明', 'className': 'row-explain'},
            { 'data': "userName", 'title': '操作人', 'className': 'row-psn' },
            { 'data': "userOrgName", 'title': '操作人组织', 'className': 'row-org' },
            { 'data': "userAccout", 'title': '操作账号', 'className': 'row-acct' },
            { 'data': null, 'title': '时间', 'className': 'row-date' ,
                'render': function (data, type, row, meta) {
                    return parent.formatDateTime(row.createDate);
                }     
            },
            { 'data': null, 'title': '状态', 'className': 'row-state', 
                'render': function (data, type, row, meta) {
                    if(row.statusCd == 1000){
                        return "有效";
                    }else{
                        return "失效";
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
            param.recordId = id;
            param.tableName = "SYS_FILE";
            $http.get('/public/modifyHistory/listByRecord', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
                // loading.screenMaskDisable('container');
            }, function (err) {
                loading.screenMaskDisable('container');
            })
        }
    });
}

// datatable应用于tab切换出现表头错位
$('#myTabs a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
})

getResInfo();