var toastr = window.top.toastr;
var cloNameData;
var ruleOperatorData = window.top.dictionaryData.ruleOperator();

function backToList(){
    window.location.href = "dataResList.html";
}

//创建数据资源
function addDataRule(){
    $http.post('/system/sysDataRule/addDataRule', JSON.stringify({   
        tabId : $("#tabName").val(),
        colId : $("#colName").val(),
        ruleOperatorId : $("#ruleOperator").val(),
        colValue : $("#colValue").val(),
        statusCd : $("#statusCd").val()
    }), function (message) {
        //新增
        backToList();
        toastr.success("新增成功！");
    }, function (err) {
        // toastr.error("新增失败！");
    })
}

//获取表名称列表
function getTab(){
    $http.get('/system/sysDataRule/getTab', {
    }, function (data) {
        initTabName(data);
        getTabColumn(data[0].tabId);
    }, function (err) {
        toastr.error("获取信息失败！");
    } )
}

//初始化表名称列表
function initTabName(result){
    for(var i=0;i<result.length;i++){
        $("#tabName").append("<option value='" + result[i].tabId + "'>" + result[i].tabName +"</option>");
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#tabName').selectMatch();
    })
}

//初始化字段名称列表
function initColName(result){
    var selected = "";
    $("#colName").empty();
    for(var i=0;i<result.length;i++){
        if(i==0){
            selected = "selected";
        }
        $("#colName").append("<option value='" + result[i].colId + "'"+selected+">" + result[i].colName +"</option>");
        selected = "";
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#colName').selectMatch();
    })
}

//初始化规则操作符
function initRuleOprator(result){
    for(var i=0;i<result.length;i++){
        $("#ruleOperator").append("<option value='" + result[i].itemValue + "'>" + result[i].itemCnname +"</option>");
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#ruleOperator').selectMatch();
    })
}

//获取字段名称列表
function getTabColumn(tabId){ 
    $http.get('/system/sysDataRule/getTabColumn', {
        tabId : tabId
    }, function (data) {
        initColName(data);
    }, function (err) {
        toastr.error("获取信息失败！");
    } )
}

$('#tabName').unbind('change').bind('change', function (event) {
    var tabId = event.target.options[event.target.options.selectedIndex].value;
    getTabColumn(tabId);
})

getTab();
initRuleOprator(ruleOperatorData);