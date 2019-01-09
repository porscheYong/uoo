var id = getQueryString('id');
var toastr = window.top.toastr;

function getResInfo(){  
    $http.get('/system/SysElement/get', {  
        id : id
    }, function (data) {
        initElemInfo(data);
    }, function (err) {
        toastr.error("获取信息失败！");
    })
}  

 //初始化元素资源信息
 function initElemInfo(result){     
    isNull("elementName",result.elementName);
    isNull("elementCode",result.elementCode);
    isNull("elementType",result.elementType);
    isNull("elementDesc",result.elementDesc);
    isNull("urlAddr",result.urlAddr);
    initMenuName(parent.menuList,result.menuCode);
}

//判断时候为null
function isNull(el,str){
    if(str != null){
        $("#"+el).val(str);
    }
}

//初始化菜单select
function initMenuName(result,currentMenu){
    var selected = "";
    for(var i=0;i<result.length;i++){
        if(currentMenu == result[i].menuCode){
            selected = "selected";
        }else{
            selected = "";
        }
        $("#menuName").append("<option value='" + result[i].menuCode + "'"+selected+">" + result[i].menuName +"</option>");
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#menuName').selectMatch();
    });
}

//更新元素资源信息
function updateRes(){
    $http.post('/system/SysElement/update', JSON.stringify({  
        elementName : $("#elementName").val(),
        elementCode : $("#elementCode").val(),
        elementType : $("#elementType").val(),
        elementDesc : $("#elementDesc").val(),
        menuCode : $("#menuName").val(),
        urlAddr : $("#urlAddr").val(),
        statusCd : $("#statusCd").val(),
        elementId : id
    }), function (message) {
        backToList();
        toastr.success("保存成功！");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}

//删除元素资源信息
function deleteRes(){
    parent.layer.confirm('是否删除该元素资源？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
            $http.post('/system/SysElement/delete', JSON.stringify({  
                elementId : id
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
    window.location.href = "elemResList.html";
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