var menuData;
var levelId;
var levelCount = getQueryString('levelCount');
var toastr = window.top.toastr;

function backToList(){
    window.location.href = "menuResList.html";
}

function getParentMenu(){
    $http.get('/system/sysMenu/listPage', {  
        pageNo : 1,
        pageSize : 100,
        keyWord : ""
    }, function (data) {
        menuData = data.records;
        initMenuLevel();
        initParentMenu(menuData,1);
    }, function (err) {
        toastr.error("获取信息失败！");
    })
}

function initParentMenu(result,level){
    $("#parentMenuName").empty();
    level -= 1;
    for(var i=0;i<result.length;i++){
        if(level != 0){
            if(result[i].menuLevel === level){
                $("#parentMenuName").append("<option value='" + result[i].menuCode + "'>" + result[i].menuName +"</option>");
            }
        }else{
            $("#parentMenuName").append("<option value='' selected>无上级菜单</option>");
            break;
        }
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#parentMenuName').selectMatch();
    })
}

function initMenuLevel(){
    for(var i=1;i<=levelCount;i++){
        $("#menuLevel").append("<option value='" + i + "'>" + i +"</option>");
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#menuLevel').selectMatch();
    })
}

//新增菜单资源
function addRes(){ 
    $http.post('/system/sysMenu/add', JSON.stringify({  
        menuName : $("#menuName").val(),
        menuCode : $("#menuCode").val(),
        menuLevel : $("#menuLevel").val(),
        parentMenuCode : $("#parentMenuName").val(),
        menuSort : $("#menuSort").val(),
        statusCd : $("#statusCd").val(),
        menuUrl : $("#menuUrl").val()
    }), function (message) {
        //新增
        backToList();
        toastr.success("新增成功！");
    }, function (err) {
        toastr.error("新增失败！");
    })
}

$('#menuLevel').unbind('change').bind('change', function (event) {
    levelId = event.target.options[event.target.options.selectedIndex].value;
    initParentMenu(menuData,levelId);
})

getParentMenu();


