var id = getQueryString('id');
var levelCount = getQueryString('levelCount');
var toastr = window.top.toastr;
var menuData;
var levelId;
var curParMenu; //当前上级菜单
var curMenuLevel; //当前菜单级别

//获取选择的菜单资源信息
function getResInfo(){
    $http.get('/system/sysMenu/get/'+id, {  
    }, function (data) {
        initMenuInfo(data);
    }, function (err) {
        toastr.error("获取信息失败！");
    })
}

//获取所有菜单资源信息
function getParentMenu(){
    $http.get('/system/sysMenu/listPage', {  
        pageNo : 1,
        pageSize : 100,
        keyWord : ""
    }, function (data) {
        menuData = data.records;
        getResInfo();
    }, function (err) {
        toastr.error("获取信息失败！");
    })
}

function initMenuInfo(result){
    $("#menuName").val(result.menuName);
    $("#menuCode").val(result.menuCode);
    $("#menuSort").val(result.menuSort);
    $("#menuUrl").val(result.menuUrl);
    curParMenu = result.parentMenuName;
    curMenuLevel = result.menuLevel;
    initParentMenu(menuData,curMenuLevel);
    initMenuLevel();
}

//初始化上级菜单
function initParentMenu(result,level){
    var selected = "";
    $("#parentMenuName").empty();
    level -= 1;
    for(var i=0;i<result.length;i++){
        if(level != 0){
            if(result[i].menuLevel === level){
                if(result[i].menuName == curParMenu){
                    selected = "selected";
                }
                $("#parentMenuName").append("<option value='" + result[i].menuCode + "'"+ selected +">" + result[i].menuName +"</option>");
            }
        }else{
            $("#parentMenuName").append("<option value='' selected>无上级菜单</option>");
            break;
        }
        selected = "";
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#parentMenuName').selectMatch();
    })
}

//初始化菜单级别
function initMenuLevel(){
    var selected = "";
    for(var i=1;i<=levelCount;i++){
        if(curMenuLevel === i){
            selected = "selected";
        }
        $("#menuLevel").append("<option value='" + i + "'"+selected+">" + i +"</option>");
        selected = "";
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#menuLevel').selectMatch();
    })
}

$('#menuLevel').unbind('change').bind('change', function (event) {
    levelId = event.target.options[event.target.options.selectedIndex].value;
    initParentMenu(menuData,levelId);
})

//更新
function updateRes(){
    $http.post('/system/update', JSON.stringify({   
        menuName : $("#menuName").val(),
        menuCode : $("#menuCode").val(),
        menuUrl : $("#menuUrl").val(),
        menuLevel : $("#menuLevel").val(),
        parentMenuCode : $("#parentMenuName").val(),
        statusCd : $("#statusCd").val(),
        menuSort : $("#menuSort").val(),
        menuId : id
    }), function (message) {
        backToList();
        toastr.success("保存成功！");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}

//删除
function deleteRes(){
    parent.layer.confirm('是否删除该菜单资源？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
            $http.post('/system/sysMenu/delete', JSON.stringify({  
                menuId : id
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

//返回
function backToList(){
    window.location.href = "menuResList.html";
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

getParentMenu();
// getResInfo();
