var gCount = 0;  //分组计数
var cCount = 0;   //条件计数
var tabData;
var allIdList = [];
var ruleOperatorData = window.top.dictionaryData.ruleOperator();
var loading = parent.loading;

//获取表名称列表
function getTab(){
    $http.get('/system/sysDataRule/getTab', {
    }, function (data) {
        tabData = data;
        loading.screenMaskDisable('LAY_app_body');
    }, function (err) {
        toastr.error("获取信息失败！");
        loading.screenMaskDisable('LAY_app_body');
    } )
}

//获取字段名称列表
function getTabColumn(tabId,conId){ 
    $http.get('/system/sysDataRule/getTabColumn', {
        tabId : tabId
    }, function (data) {
        initColName(data,conId);
    }, function (err) {
        toastr.error("获取信息失败！");
    } )
}

//初始化表名称列表
function initTabName(result,conId){
    for(var i=0;i<result.length;i++){
        $("#tabName_"+conId).append("<option value='" + result[i].tabId + "'>" + result[i].tabName +"</option>");
    }
}

//初始化字段名称列表
function initColName(result,conId){
    var selected = "";
    $("#colName_"+conId).empty();
    if(result.length == 0){
        $("#colName_"+conId).append("<option selected>无字段</option>");
        $("#colValue_"+conId).attr("disabled","disabled");
    }else{
        $("#colValue_"+conId).removeAttr("disabled");
    }
    for(var i=0;i<result.length;i++){
        if(i==0){
            selected = "selected";
        }
        $("#colName_"+conId).append("<option value='" + result[i].colId + "'"+selected+">" + result[i].colName +"</option>");
        selected = "";
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $("#colName_"+conId).selectMatch();
    })
}

//初始化规则操作符
function initRuleOprator(result,conId){
    for(var i=0;i<result.length;i++){
        $("#ruleOperator_"+conId).append("<option value='" + result[i].itemValue + "'>" + result[i].itemCnname +"</option>");
    }
}

// 初始化业务组织列表
function initBusinessList () {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        var option = '';
        for (var i = -1; i < data.length; i++) {
            var select = i === -1? 'selected' : '';
            if(i == -1){
                option += "<option value='0' " + select + ">全部</option>";
            }else{
                option += "<option value='" + data[i].orgTreeId + "' " + select + ">" + data[i].orgTreeName +"</option>";
            }
        }
        $('#businessOrg').html(option);
        seajs.use('/vendors/lulu/js/common/ui/Select', function () {
            $('#businessOrg').selectMatch();
        });
    }, function (err) {
        // loading.screenMaskDisable('container');
    })
}

//绑定表名称输入框点击事件
function bindInput(conId){
    $('#tabName_'+conId).unbind('change').bind('change', function (event) {
        var tabId = event.target.options[event.target.options.selectedIndex].value;
        getTabColumn(tabId,conId);
    })
}

//添加分组
function addDataGroup(){
    var groupHtml = "<div class='dataGroup' id='group_"+gCount+"'>"+
                        "<div id='conDiv_"+gCount+"' class='conDiv'>"+
                            "<div class='dataCondition' id='con_"+cCount+"'>"+
                                "<div class='row' style='width: 100%;'>"+
                                    "<span class='pngDel' style='float:right;' title='删除条件' onclick='delDataCondition("+cCount+","+gCount+")'></span>"+
                                "</div>"+
                                "<div class='row' style='margin-bottom: 10px;'>"+
                                    "<div class='col-md-6 col-sm-6 col-xs-6'>"+
                                        "<div style='width: 100%'>"+
                                            "<label for='tabName_"+cCount+"' class='col-md-4 col-sm-4 col-xs-4' style='padding-right: 20px;line-height: 40px;text-align: right;'>表名称</label>"+
                                            "<select class='col-md-8 col-sm-8 col-xs-8' id='tabName_"+cCount+"'></select>"+
                                        "</div>"+
                                    "</div>"+
                                    "<div class='col-md-6 col-sm-6 col-xs-6'>"+
                                        "<div style='width: 100%'>"+
                                            "<label for='colName_"+cCount+"' class='col-md-4 col-sm-4 col-xs-4' style='padding-right: 20px;line-height: 40px;text-align: right;width: 24%;'>字段名称</label>"+
                                            "<select class='col-md-8 col-sm-8 col-xs-8' id='colName_"+cCount+"'></select>"+
                                        "</div>"+
                                    "</div>"+
                                "</div>"+
                                "<div class='row' style='margin-bottom: 10px;'>"+
                                    "<div class='col-md-6 col-sm-6 col-xs-6'>"+
                                        "<div style='width: 100%'>"+
                                            "<label for='ruleOperator_"+cCount+"' class='col-md-4 col-sm-4 col-xs-4' style='padding-right: 20px;line-height: 40px;text-align: right;width:27%;'>规则操作符</label>"+
                                            "<select class='col-md-8 col-sm-8 col-xs-8' id='ruleOperator_"+cCount+"'></select>"+
                                        "</div>"+
                                    "</div>"+
                                    "<div class='col-md-6 col-sm-6 col-xs-6'>"+
                                        "<div style='width: 100%'>"+
                                            "<label for='colValue_"+cCount+"' class='form-item-label-required col-md-4 col-sm-4 col-xs-4' style='padding-right: 20px;line-height: 40px;text-align: right;'>字段值</label>"+
                                            "<input id='colValue_"+cCount+"' style='padding:8px 8px' class='ui-input col-md-8 col-sm-8 col-xs-8' size='40' required autocomplete='off'>"+
                                        "</div>"+
                                    "</div>"+
                                "</div>"+
                            "</div>"+
                        "</div>"+
                        "<div class='row' style='margin-bottom: 10px;margin-top:1%;'>"+
                            "<div class='col-md-2 col-sm-2 col-xs-2' style='float:right;'>"+
                                "<button class='BtnDelGroup' onclick='delDataGroup("+gCount+")'>删除分组</button>"+
                            "</div>"+
                            "<div class='col-md-2 col-sm-2 col-xs-2' style='float:right;'>"+
                                "<button class='BtnAddRela' onclick='addDataCondition("+gCount+")'>添加条件</button>"+
                            "</div>"+
                            "<div class='col-md-1 col-sm-1 col-xs-1' style='width:15%;float: right;'>"+
                                "<select class='col-md-9 col-sm-9 col-xs-9' id='relation_"+gCount+"' style='width:100%;'>"+
                                "<option value='and'>并且</option><option value='or'>或者</option>"+
                                "</select>"+
                            "</div>"+
                        "</div>"+
                    "</div>";
    $("#tab_content5").append(groupHtml);

    allIdList.push({"gId":gCount,"cId":[cCount]});

    initTabName(tabData,cCount);
    initRuleOprator(ruleOperatorData,cCount);
    getTabColumn(tabData[0].tabId,cCount);
    bindInput(cCount);

    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#tabName_'+cCount).selectMatch();
        // $("#colName_"+cCount).selectMatch();
        $("#ruleOperator_"+cCount).selectMatch();
        $("#relation_"+gCount).selectMatch();
    });

    gCount++;
    cCount++;

}

//删除分组
function delDataGroup(groupId){
    var newList = [];
    $("#group_"+groupId).remove();

    for(var i=0;i<allIdList.length;i++){
        if(allIdList[i].gId != groupId){
            newList.push(allIdList[i]);
        }
    }
    allIdList = newList;
}   

//添加条件
function addDataCondition(groupId){
    var conHtml = "<div class='dataCondition' id='con_"+cCount+"'>"+
                        "<div class='row' style='width: 100%;'>"+
                            "<span class='pngDel' style='float:right;' title='删除条件' onclick='delDataCondition("+cCount+","+groupId+")'></span>"+
                        "</div>"+
                        "<div class='row' style='margin-bottom: 10px;'>"+
                            "<div class='col-md-6 col-sm-6 col-xs-6'>"+
                                "<div style='width: 100%'>"+
                                    "<label for='tabName_"+cCount+"' class='col-md-4 col-sm-4 col-xs-4' style='padding-right: 20px;line-height: 40px;text-align: right;'>表名称</label>"+
                                    "<select class='col-md-8 col-sm-8 col-xs-8' id='tabName_"+cCount+"'></select>"+
                                "</div>"+
                            "</div>"+
                            "<div class='col-md-6 col-sm-6 col-xs-6'>"+
                                "<div style='width: 100%'>"+
                                    "<label for='colName_"+cCount+"' class='col-md-4 col-sm-4 col-xs-4' style='padding-right: 20px;line-height: 40px;text-align: right;width: 24%;'>字段名称</label>"+
                                    "<select class='col-md-8 col-sm-8 col-xs-8' id='colName_"+cCount+"'></select>"+
                                "</div>"+
                            "</div>"+
                        "</div>"+
                        "<div class='row' style='margin-bottom: 10px;'>"+
                            "<div class='col-md-6 col-sm-6 col-xs-6'>"+
                                "<div style='width: 100%'>"+
                                    "<label for='ruleOperator_"+cCount+"' class='col-md-4 col-sm-4 col-xs-4' style='padding-right: 20px;line-height: 40px;text-align: right;width:27%;'>规则操作符</label>"+
                                    "<select class='col-md-8 col-sm-8 col-xs-8' id='ruleOperator_"+cCount+"'></select>"+
                                "</div>"+
                            "</div>"+
                            "<div class='col-md-6 col-sm-6 col-xs-6'>"+
                                "<div style='width: 100%'>"+
                                    "<label for='colValue_"+cCount+"' class='form-item-label-required col-md-4 col-sm-4 col-xs-4' style='padding-right: 20px;line-height: 40px;text-align: right;'>字段值</label>"+
                                    "<input id='colValue_"+cCount+"' style='padding:8px 8px' class='ui-input col-md-8 col-sm-8 col-xs-8' size='40' required autocomplete='off'>"+
                                "</div>"+
                            "</div>"+
                        "</div>"+
                    "</div>";
    $("#conDiv_"+groupId).append(conHtml);

    for(var i=0;i<allIdList.length;i++){
        if(allIdList[i].gId == groupId){
            allIdList[i].cId.push(cCount);
            break;
        }
    }

    initTabName(tabData,cCount);
    initRuleOprator(ruleOperatorData,cCount);
    getTabColumn(tabData[0].tabId,cCount);
    bindInput(cCount);

    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#tabName_'+cCount).selectMatch();
        // $("#colName_"+cCount).selectMatch();
        $("#ruleOperator_"+cCount).selectMatch();
    });

    cCount++;
}

//删除条件
function delDataCondition(conId,groupId){
    $("#con_"+conId).remove();
    if($("#group_"+groupId).children(".conDiv").children().length == 0){
        $("#group_"+groupId).remove();
    }

    for(var i=0;i<allIdList.length;i++){
        if(allIdList[i].gId == groupId){
            allIdList[i].cId.splice(allIdList[i].cId.indexOf(conId),1);
            break;
        }
    }
}

initBusinessList();
getTab();