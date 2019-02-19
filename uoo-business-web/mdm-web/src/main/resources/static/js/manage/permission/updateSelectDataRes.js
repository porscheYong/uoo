var gCount = 0;  //分组计数
var cCount = 0;   //条件计数
var tabData;
var allIdList = [];
var relationList = [{"rela":"并且","relaVal":"and"},{"rela":"或者","relaVal":"or"}];
var ruleOperatorData = window.top.dictionaryData.ruleOperator();
var loading = parent.loading;

//获取字段名称列表
function getTabColumn(tabId,conId,colId){ 
    $http.get('/system/sysDataRule/getTabColumn', {
        tabId : tabId
    }, function (data) {
        initColName(data,conId,colId);
    }, function (err) {
        toastr.error("获取信息失败！");
    } )
}

//初始化表名称列表
function initTabName(result,conId,tabId,dataRuleId){
    for(var i=0;i<result.length;i++){
        var select = result[i].tabId == tabId? 'selected' : '';
        $("#tabName_"+conId).append("<option conId='"+dataRuleId+"' value='" + result[i].tabId + "' " + select + ">" + result[i].tabName +"</option>");
    }
}

//初始化字段名称列表
function initColName(result,conId,colId){
    $("#colName_"+conId).empty();
    if(result.length == 0){
        $("#colName_"+conId).append("<option selected>无字段</option>");
        $("#colValue_"+conId).attr("disabled","disabled");
    }else{
        $("#colValue_"+conId).removeAttr("disabled");
    }
    for(var i=0;i<result.length;i++){
        var select = result[i].colId == colId? 'selected' : '';
        $("#colName_"+conId).append("<option value='" + result[i].colId + "'"+select+">" + result[i].colName +"</option>");
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $("#colName_"+conId).selectMatch();
    })
}

//初始化规则操作符
function initRuleOprator(result,conId,val){
    for(var i=0;i<result.length;i++){
        var select = result[i].itemValue == val? 'selected' : '';
        $("#ruleOperator_"+conId).append("<option value='" + result[i].itemValue + "' " + select + ">" + result[i].itemCnname +"</option>");
    }
}

// 初始化业务组织列表
function initBusinessList (orgTreeId) {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        var option = '';
        for (var i = -1; i < data.length; i++) {
            var select = "";
            if(i == -1){
                option += "<option value='0' " + select + ">全部</option>";
            }else{
                select = data[i].orgTreeId == orgTreeId? 'selected' : '';
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

//初始化字段值
function initColVal(conId,value){
    $("#colValue_"+conId).val(value);
}

//初始化组间关系
function initRelation(andOr,groupId){
    for(var i=0;i<relationList.length;i++){
        var select = relationList[i].relaVal == andOr? 'selected' : '';
        $("#relation_"+groupId).append("<option value='" + relationList[i].relaVal + "' " + select + ">" + relationList[i].rela +"</option>");
    }

    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $("#relation_"+groupId).selectMatch();
    });
}

//绑定表名称输入框点击事件
function bindInput(conId){
    $('#tabName_'+conId).unbind('change').bind('change', function (event) {
        var tabId = event.target.options[event.target.options.selectedIndex].value;
        getTabColumn(tabId,conId,-1);
    })
}

//添加分组
function addDataGroup(flag){
    var n = -1;
    var groupHtml = "<div class='dataGroup' id='group_"+gCount+"' groupId='-1'>"+
                        "<div id='conDiv_"+gCount+"' class='conDiv'>"+
                        "</div>"+
                        "<div class='row' style='margin-bottom: 10px;margin-top:1%;'>"+
                            "<div class='col-md-2 col-sm-2 col-xs-2' style='float:right;'>"+
                                "<button class='BtnDelGroup' onclick='delDataGroup("+gCount+")'>删除分组</button>"+
                            "</div>"+
                            "<div class='col-md-2 col-sm-2 col-xs-2' style='float:right;'>"+
                                "<button class='BtnAddRela' onclick='addDataCondition("+gCount+","+n+","+n+","+n+","+n+")'>添加条件</button>"+
                            "</div>"+
                            "<div class='col-md-1 col-sm-1 col-xs-1' style='width:15%;float: right;'>"+
                                "<select class='col-md-9 col-sm-9 col-xs-9' id='relation_"+gCount+"' style='width:100%;'>"+
                                // "<option value='and'>并且</option><option value='or'>或者</option>"+
                                "</select>"+
                            "</div>"+
                        "</div>"+
                    "</div>";
    $("#tab_content5").append(groupHtml);
    
    allIdList.push({"gId":gCount,"cId":[]});
    if(flag == 1){
        initRelation("",gCount);
        addDataCondition(gCount,-1,-1,-1,-1);
    }

    gCount++;
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
function addDataCondition(groupId,tabId,opratorVal,dataRuleId,colId){
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

    initTabName(tabData,cCount,tabId,dataRuleId);
    initRuleOprator(ruleOperatorData,cCount,opratorVal);
    if(tabId != -1){
        getTabColumn(tabId,cCount,colId);
    }else{
        getTabColumn(tabData[0].tabId,cCount,colId);
    }
    bindInput(cCount);

    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#tabName_'+cCount).selectMatch();
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

//初始化已选的数据资源分组
function initDataSelectedGroups(selectedList,tData){
    var dataList = [];
    var c = 0;
    tabData = tData;
    console.log(selectedList);
    if(selectedList.length != 0){
        initBusinessList(selectedList[0].orgTreeId);
    }else{
        initBusinessList(0);
    }
    
    for(var i=0;i<selectedList.length;i++){
        var ruleList = [];
        addDataGroup(0);
        $("#group_"+i).attr("groupId",selectedList[i].dataRuleGroupId);
        initRelation(selectedList[i].andOr,i);
        for(var j=0;j<selectedList[i].dataRules.length;j++){
            ruleList.push({"cId":cCount,"ruleId":selectedList[i].dataRules[j].dataRuleId});
            addDataCondition(i , selectedList[i].dataRules[j].tabId , selectedList[i].dataRules[j].ruleOperator , selectedList[i].dataRules[j].dataRuleId , selectedList[i].dataRules[j].colId);
            initColVal(c,selectedList[i].dataRules[j].colValue);
            seajs.use('/vendors/lulu/js/common/ui/Select', function () {
                $('#tabName_'+j).selectMatch();
                $("#ruleOperator_"+j).selectMatch();
            });
            c++;
        }
        dataList.push({"gId":i,"con":ruleList,"groundId":selectedList[i].dataRuleGroupId});
    }
}




