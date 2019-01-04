var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var refCode = getQueryString('refCode');
var areaCodeId = ''; //区号ID
var locationList = [];
var orgTypeList = [];
var expandovalueVoList = []; //划小扩展字段
var positionList = [];
var orgPostList = [];
var checkNode;
var selectUser = [];
var formValidate;
var loading = parent.loading;
var toastr = window.top.toastr;
var nodeArr = parent.nodeArr;
var editSmallField = false;
var selectStandardNode = false; //是否选中标准节点
var selectRevenueCenter = false; //是否选中收入中心
var U5Node = false; //是否存在U5节点

//字典数据
var scaleData = window.top.dictionaryData.scale();
var cityVillageData = window.top.dictionaryData.cityVillage();
var orgPostLevelData = window.top.dictionaryData.orgPostLevel();
var statusCdData = window.top.dictionaryData.statusCd();
var nodeTypeData = window.top.dictionaryData.nodeType();
var areaTypeData = window.top.dictionaryData.areaType();
var countTypeData = window.top.dictionaryData.countType();
var contractTypeData = window.top.dictionaryData.contractType();

$('.orgName').html(orgName);
parent.getOrgExtInfo();

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {
    $('select').selectMatch();
});

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var orgAddForm = $('#orgAddForm');
    formValidate = new Validate(orgAddForm);
    formValidate.immediate();
    orgAddForm.find(':input').each(function () {
        $(this).hover(function () {
            formValidate.isPass($(this));
        });
    });
    formValidate.isAllPass();
});

laydate.render({
    elem: '#foundingTime'
});

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
    $('#locationList').tagsInput({unique: true});
    $('#orgTypeList').tagsInput({unique: true});
    $('#positionList').tagsInput();
    $('#postList').tagsInput();
    $('#regionId').tagsInput({unique: true});
}
// 父节点U5类型检查
function checkU5Org() {
    var nodeIdArr = [];
    for (var i = 0; i < nodeArr.length; i++) {
        var id = nodeArr[i].node.id;
        nodeIdArr.push(id);
    }
    $http.post('/tbExpandovalue/checkOrgU5Node', JSON.stringify(nodeIdArr), function (data) {
        if (parseInt(data))
            U5Node = true;
    }, function (err) {

    })
}
//营销组织树新增页面组织类别默认选中营销类别
function getFullOrgTypeTree() {
    if (refCode == '0401') {
        $http.get('/orgType/getFullOrgTypeTree', {
            orgId: orgId
        }, function (data) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].extField1 == 'N11') {
                    orgTypeList.push(data[i]);
                    $('#orgTypeList').importTags(orgTypeList, {unique: true});
                    var smallTemplate = Handlebars.compile($("#smallTemplate").html());
                    var smallHtml = smallTemplate();
                    $('#small').html(smallHtml);
                    getNodeType();
                    getAreaType();
                    getCountType();
                    getContractType();
                    editSmallField = true;
                    return;
                }
            }
        }, function (err) {

        })
    }
}
//自动填写组织简称
function autoFillShortName () {
    $('#shortName').val($('#orgName').val());
    $('#orgBizName').val($('#orgName').val());
}
//联系人选择
function openContactDialog() {
    parent.layer.open({
        type: 2,
        title: '联系人',
        shadeClose: true,
        shade: 0.8,
        area: ['70%', '85%'],
        maxmin: true,
        content: '/inaction/organization/contactDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            var selectObj = iframeWin.getSelectUser();
            if (selectObj.length > 0) {
                selectUser = selectObj;
                $('#psonOrgVoList').val(selectUser[0].psnName);
            }
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//组织类别选择
function openTypeDialog() {
    parent.layer.open({
        type: 2,
        title: '选中组织类别',
        shadeClose: true,
        shade: 0.8,
        area: ['70%', '85%'],
        maxmin: true,
        content: '/inaction/organization/typeDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#orgTypeList').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            orgTypeList = checkNode;
            parent.layer.close(index);
            //选择组织类别为营销组织类型
            if (orgTypeList.length == 0 && editSmallField) {
                editSmallField = false;
            }
            else {
                for (var i = 0; i < orgTypeList.length; i++) {
                    var isSmallFieldExit = false;
                    if (refCode == '0401' &&
                        ((orgTypeList[i].orgTypeCode && orgTypeList[i].orgTypeCode.substr(0, 3) == 'N11') ||
                         (orgTypeList[i].extField1 && orgTypeList[i].extField1.substr(0, 3) == 'N11')
                        )) {
                        if (!editSmallField) {
                            var smallTemplate = Handlebars.compile($("#smallTemplate").html());
                            var smallHtml = smallTemplate();
                            $('#small').html(smallHtml);
                            editSmallField = true;
                            getNodeType();
                            getAreaType();
                            getCountType();
                            getContractType();
                            return
                        }
                        else {
                            isSmallFieldExit = true;
                            return
                        }
                    }
                    if (i == orgTypeList.length - 1 && !isSmallFieldExit )
                        editSmallField = false;
                }
            }
            if (!editSmallField) {
                $('#small').html('');
                selectStandardNode = false;
                selectRevenueCenter = false;
            }
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//组织岗位选择
function openPositionDialog() {
    parent.layer.open({
        type: 2,
        title: '选中岗位职位',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: '/inaction/organization/positionDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#positionList').importTags(checkNode);
            // $('.ui-tips-error').css('display', 'none');
            positionList = checkNode;
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//组织职位选择
function openPostDialog() {
    parent.layer.open({
        type: 2,
        title: '组织职位',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: '/inaction/organization/postDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#postList').importTags(checkNode);
            //TODO 防止选中标签显示错误提示
            // $('.ui-tips-error').css('display', 'none');
            orgPostList = checkNode;
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//行政管理区域选择
function openLocationDialog() {
    parent.layer.open({
        type: 2,
        title: '行政管理区域',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: '/inaction/organization/locationDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#locationList').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            locationList = checkNode;
            getAreaId(checkNode[0].id);
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//根据行政管理区域ID获取区号
function getAreaId(regionId) {
    $http.get('/region/areaCode/getAreaCodeByPollocId/id='+ regionId, {}, function (data) {
        if (data[0])
            areaCodeId = data[0].areaCodeId;
        var option = '';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].areaCodeId + "'>" + data[i].areaCode +"</option>";
        }
        $('#areaCode').html(option);
        $('#areaCode').selectMatch();
        formValidate.isPass($('#areaCode'));
    }, function (err) {

    })
}
//根据拉下框获取当前选中的区号ID
function getAreaCodeId() {
    areaCodeId = $(this).children('option:selected').val();
}

// 获取规模字典数据
function getScale () {
    var option = '<option></option>';
    for (var i = 0; i < scaleData.length; i++) {
        option += "<option value='" + scaleData[i].itemValue + "'>" + scaleData[i].itemCnname +"</option>";
    }
    $('#orgScale').append(option);
    // $('#orgScale').selectMatch();
}

// 获取城乡字典数据
function getCityVillage () {
    var option = '<option></option>';
    for (var i = 0; i < cityVillageData.length; i++) {
        option += "<option value='" + cityVillageData[i].itemValue + "'>" + cityVillageData[i].itemCnname +"</option>";
    }
    $('#cityTown').append(option);
    // $('#cityTown').selectMatch();
}

// 获取组织最高岗位级别字典数据
function getOrgPostLevel () {
    var option = '<option></option>';
    for (var i = 0; i < orgPostLevelData.length; i++) {
        option += "<option value='" + orgPostLevelData[i].itemValue + "'>" + orgPostLevelData[i].itemCnname +"</option>";
    }
    $('#orgPositionLevel').append(option);
    // $('#orgPositionLevel').selectMatch();
}

// 获取状态数据
function getStatusCd (statusCd) {
    var option = '';
    for (var i = 0; i < statusCdData.length; i++) {
        var select = statusCd === statusCdData[i].itemValue? 'selected' : '';
        option += "<option value='" + statusCdData[i].itemValue + "' " + select + ">" + statusCdData[i].itemCnname +"</option>";
    }
    $('#statusCd').append(option);
    // $('#statusCd').selectMatch();
}

// 获取组织节点类型字典数据
function getNodeType () {
    var option = '<option></option>';
    for (var i = 0; i < nodeTypeData.length; i++) {
        option += "<option value='" + nodeTypeData[i].itemValue + "'>" + nodeTypeData[i].itemCnname +"</option>";
    }
    $('#nodeTypes').append(option);
    // $('#nodeType').selectMatch();
    formSelects.render('nodeTypes');
    formSelects.on('nodeTypes', function(id, vals, val, isAdd, isDisabled){
        if (isAdd && val.value == 'A1') {
            selectStandardNode = true;
            formSelects.oDisabled('countType', {value: 'B2'});
            formSelects.addOne('countType', {value: 'B4', name: '收入中心'});
        }
        if (isAdd && val.value == 'A3') {
            selectRevenueCenter = true;
        }
        if (!isAdd && val.value == 'A1') {
            selectStandardNode = false;
            formSelects.oUnDisabled('countType', {value: 'B2'});
        }
        if (!isAdd && val.value == 'A3') {
            selectRevenueCenter = false;
        }
    }, true);
}

// 获取区域级别字典数据
function getAreaType () {
    var option = '<option></option>';
    for (var i = 0; i < areaTypeData.length; i++) {
        option += "<option value='" + areaTypeData[i].itemValue + "'>" + areaTypeData[i].itemCnname +"</option>";
    }
    $('#areaType').append(option);
    $('#areaType').selectMatch();
}

// 获取统计属性字典数据
function getCountType () {
    var option = '<option></option>';
    for (var i = 0; i < countTypeData.length; i++) {
        option += "<option value='" + countTypeData[i].itemValue + "'>" + countTypeData[i].itemCnname +"</option>";
    }
    $('#countType').append(option);
    // $('#countType').selectMatch();
    formSelects.render('countType');
    formSelects.on('countType', function(id, vals, val, isAdd, isDisabled){
        if (selectRevenueCenter && !selectStandardNode) {
            if (isAdd && val.value == 'B2') {
                formSelects.delOne('countType', {value: 'B4'});
            }
            if (isAdd && val.value == 'B4') {
                formSelects.delOne('countType', {value: 'B2'});
            }
        }

    }, true);
}

// 获取承包类型字典数据
function getContractType () {
    if (U5Node) {
        for (var i = 0; i < contractTypeData.length; i++) {
            if (contractTypeData[i].itemValue.split('-')[0] == 'U5') {
                contractTypeData.splice(i, 1)
            }
        }
    }
    var option = '<option></option>';
    for (var i = 0; i < contractTypeData.length; i++) {
        option += "<option value='" + contractTypeData[i].itemValue + "'>" + contractTypeData[i].itemCnname +"</option>";
    }
    $('#contractType').append(option);
    $('#contractType').selectMatch();
}

// 添加子节点
function addOrg () {
    if (!formValidate.isAllPass())
        return;
    loading.screenMaskEnable('container');
    var userList = [];
    var location = [];
    var position = [];
    var post = [];
    var orgType = [];
    //联系人
    for (var i = 0; i < selectUser.length; i++) {
        userList.push({personnelId: selectUser[i].personnelId});
    }
    //行政管理区域
    for (var i = 0; i < locationList.length; i++) {
        location.push({locId: locationList[i].id});
    }
    //组织岗位
    for (var i = 0; i < positionList.length; i++) {
        position.push({positionId: positionList[i].positionId});
    }
    //组织职位
    for (var i = 0; i < orgPostList.length; i++) {
        post.push({postId: orgPostList[i].postId});
    }
    //组织类别
    for (var i = 0; i < orgTypeList.length; i++) {
        var orgTypeId = orgTypeList[i].orgTypeId || orgTypeList[i].id;
        orgType.push({orgTypeId: orgTypeId});
    }
    var orgName = $('#orgName').val();
    var orgScale = $('#orgScale option:selected') .val();
    var shortName = $('#shortName').val();
    var orgBizName = $('#orgBizName').val();
    var orgNameEn = $('#orgNameEn').val();
    var cityTown = $('#cityTown option:selected') .val();
    var foundDate = $('#foundingTime').val();
    // var foundDate;
    // if (date) {
    //     foundDate = new Date(date).getTime();
    // }
    var orgPositionLevel = $('#orgPositionLevel option:selected') .val();
    var officePhone = $('#officePhone').val();
    var statusCd = $('#statusCd option:selected') .val();
    var sort = $('#sort').val();
    var address = $('#address').val();
    var orgContent = $('#orgContent').val();
    var orgDesc = $('#orgDesc').val();
    //划小扩展字段
    // var nodeType = $('#nodeType option:selected') .val();
    var nodeType = formSelects.value('nodeTypes');
    var areaType = $('#areaType option:selected') .val();
    // var countType = $('#countType option:selected') .val();
    var countType = formSelects.value('countType');
    var contractType = $('#contractType option:selected') .val();
    if (editSmallField) {
        if (areaType)
            expandovalueVoList.push({columnName: 'areaType', data: areaType});
        if (contractType)
            expandovalueVoList.push({columnName: 'contractType', data: contractType})
        for (var i = 0; i < nodeType.length; i++){
            expandovalueVoList.push({columnName: 'nodeType', data: nodeType[i].value})
        }
        for (var i = 0; i < countType.length; i++){
            expandovalueVoList.push({columnName: 'countType', data: countType[i].value})
        }
    }
    $http.post('/org/addOrg', JSON.stringify({
        orgRootId: '1',
        orgTreeId: orgTreeId,
        orgId: orgId,
        supOrgId: orgId,
        orgName: orgName,
        shortName: shortName,
        orgBizName: orgBizName,
        cityTown: cityTown,
        orgScale: orgScale,
        foundingTime: foundDate,
        psonOrgVoList: userList,
        orgNameEn: orgNameEn,
        orgPositionLevel: orgPositionLevel,
        officePhone: officePhone,
        statusCd: statusCd,
        sort: sort,
        areaCodeId: areaCodeId,
        address: address,
        politicalLocationList: location,
        orgTypeList: orgType,
        positionList: position,
        postList: post,
        orgContent: orgContent,
        orgDesc: orgDesc,
        expandovalueVoList: expandovalueVoList
    }), function (data) {
        parent.addNodeById(orgId, data);
        parent.openTreeById(orgId, data.id);
        window.location.replace("list.html?id=" + data.id + '&orgTreeId=' + orgTreeId + "&refCode=" + refCode + '&pid=' + data.pid + "&name=" + encodeURI(data.name));
        loading.screenMaskDisable('container');
        toastr.success('新增成功！');
    }, function (err) {

    })
}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName);
    window.location.href = url;
}

getScale();
getCityVillage();
getOrgPostLevel();
getStatusCd('1000'); //默认选择生效
checkU5Org();
getFullOrgTypeTree();

