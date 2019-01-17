var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
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

//字典数据
var scaleData = window.top.dictionaryData.scale();
var cityVillageData = window.top.dictionaryData.cityVillage();
var orgPostLevelData = window.top.dictionaryData.orgPostLevel();
var statusCdData = window.top.dictionaryData.statusCd();
// var nodeTypeData = window.top.dictionaryData.nodeType();
// var areaTypeData = window.top.dictionaryData.areaType();
// var countTypeData = window.top.dictionaryData.countType();
// var contractTypeData = window.top.dictionaryData.contractType();
var nodeArr = parent.nodeArr;
$('.orgName').html(orgName);
// 显示组织路径
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
    $('#regionId').tagsInput();
}

//自动填写组织简称
function autoFillShortName () {
    $('#shortName').val($('#orgName').val());
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
        content: 'contactDialog.html?id=' + orgId,
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
        content: 'typeDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#orgTypeList').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            parent.layer.close(index);
            orgTypeList = checkNode;
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
        content: 'positionDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#positionList').importTags(checkNode);
            // $('.ui-tips-error').css('display', 'none');
            parent.layer.close(index);
            positionList = checkNode;
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
        content: 'postDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#postList').importTags(checkNode);
            parent.layer.close(index);
            //TODO 防止选中标签显示错误提示
            // $('.ui-tips-error').css('display', 'none');
            orgPostList = checkNode;
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
        content: 'locationDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#locationList').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            locationList = checkNode;
            parent.layer.close(index);
            getAreaId(checkNode[0].id);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//根据电信管理区域ID获取区号
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
        option += "<option value='" + statusCdData[i].itemValue + "' " + select +">" + statusCdData[i].itemCnname +"</option>";
    }
    $('#statusCd').append(option);
    // $('#statusCd').selectMatch();
}

// // 获取组织节点类型字典数据
// function getNodeType () {
//     var option = '<option></option>';
//     for (var i = 0; i < nodeTypeData.length; i++) {
//         option += "<option value='" + nodeTypeData[i].itemValue + "'>" + nodeTypeData[i].itemCnname +"</option>";
//     }
//     $('#nodeTypes').append(option);
//     // $('#nodeTypes').selectMatch();
//     formSelects.render('nodeTypes');
//     formSelects.on('nodeTypes', function(id, vals, val, isAdd, isDisabled){
//         var data = formSelects.value('countType');
//         formSelects.render('countType', {
//             init: data,
//             template: function(name, value, selected, disabled){
//                 if (isAdd && val.value == 'A1' && value.value == 'B2') {
//                     value.disabled = true;
//                 }
//                 else if (!isAdd && val.value == 'A1' && value.value == 'B2') {
//                     value.disabled = false;
//                 }
//                 //例如: 反转字符串
//                 //return name.split('').reverse().join('');
//                 return value.name;        //返回一个html结构, 用于显示选项
//             }
//         });
//     }, true);
// }
//
// // 获取区域级别字典数据
// function getAreaType () {
//     var option = '<option></option>';
//     for (var i = 0; i < areaTypeData.length; i++) {
//         option += "<option value='" + areaTypeData[i].itemValue + "'>" + areaTypeData[i].itemCnname +"</option>";
//     }
//     $('#areaType').append(option);
//     $('#areaType').selectMatch();
// }
//
// // 获取统计属性字典数据
// function getCountType () {
//     var option = '<option></option>';
//     for (var i = 0; i < countTypeData.length; i++) {
//         option += "<option value='" + countTypeData[i].itemValue + "'>" + countTypeData[i].itemCnname +"</option>";
//     }
//     $('#countType').append(option);
//     // $('#countType').selectMatch();
//     formSelects.render('countType');
// }
//
// // 获取承包类型字典数据
// function getContractType () {
//     if (U5Node) {
//         for (var i = 0; i < contractTypeData.length; i++) {
//             if (contractTypeData[i].itemValue.split('-')[0] == 'U5') {
//                 contractTypeData.splice(i, 1)
//             }
//         }
//     }
//     var option = '<option></option>';
//     for (var i = 0; i < contractTypeData.length; i++) {
//         option += "<option value='" + contractTypeData[i].itemValue + "'>" + contractTypeData[i].itemCnname +"</option>";
//     }
//     $('#contractType').append(option);
//     // $('#contractType').selectMatch();
//     formSelects.render('contractType');
// }

// 获取组织基础信息
function getOrg (orgId) {
    $http.get('/org/getOrg', {
        orgId: orgId
    }, function (data) {
        var orgAddForm = $('#orgAddForm');
        orgAddForm.find('input:not(#orgName)').each(function () {
            $(this).attr('disabled', 'disabled')
        });
        $('#orgName').val(data.orgName);
        $('#orgCode').val(data.orgCode);
        $('#shortName').val(data.shortName);
        $('#orgBizFullName').val(data.orgBizFullName);
        $('#orgBizFullName').attr('title', data.orgBizFullName);
        $('#orgNameEn').val(data.orgNameEn);
        orgMartCode = data.orgMartCode;
        laydate.render({
            elem: '#foundingTime',
            value: new Date(data.foundingTime)
        });
        $('#foundingTime').val(data.foundingTime);
        if (data.psonOrgVoList && data.psonOrgVoList.length > 0) {
            $('#psonOrgVoList').val(data.psonOrgVoList[0].psnName);
        }
        $('#officePhone').val(data.officePhone);
        $('#sort').val(data.sort);
        areaCodeId = data.areaCodeId;
        // render area code
        var option = '';
        if (data.areaCode)
            option = "<option value='" + areaCodeId + "'>" + data.areaCode +"</option>";
        $('#areaCode').append(option);
        $('#areaCode').selectMatch();
        formValidate.isPass($('#areaCode'));

        $('#address').val(data.address);
        $('#orgContent').val(data.orgContent);
        $('#orgDesc').val(data.orgDesc);
        getScale(data.orgScale);
        getCityVillage(data.cityTown);
        getOrgPostLevel(data.orgPositionLevel);
        getStatusCd(data.statusCd);
        locationList = data.politicalLocationList;
        orgTypeList = data.orgTypeList;
        positionList = data.positionList;
        orgPostList = data.postList;
        selectUser = data.psonOrgVoList;
        $('#locationList').importTags(locationList);
        $('#orgTypeList').importTags(orgTypeList);
        $('#positionList').importTags(positionList);
        $('#postList').importTags(orgPostList);
        expandovalueVoList = data.expandovalueVoList;
    }, function (err) {

    })
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
    // var nodeType = $('#nodeTypes option:selected') .val();
    // var nodeType = formSelects.value('nodeTypes');
    // var areaType = $('#areaType option:selected') .val();
    // var countType = $('#countType option:selected') .val();
    // var countType = formSelects.value('countType');
    // var contractType = $('#contractType option:selected') .val();
    // if (editSmallField) {
    //     expandovalueVoList = [
    //         // {columnName: 'nodeType', data: nodeType},
    //         {columnName: 'areaType', data: areaType},
    //         // {columnName: 'countType', data: countType},
    //         {columnName: 'contractType', data: contractType}
    //     ];
    //     for (var i = 0; i < nodeType.length; i++){
    //         expandovalueVoList.push({columnName: 'nodeType', data: nodeType[i].value})
    //     }
    //     for (var i = 0; i < countType.length; i++){
    //         expandovalueVoList.push({columnName: 'countType', data: countType[i].value})
    //     }
    // }
    $http.post('/org/addOrg', JSON.stringify({
        orgRootId: '1',
        orgTreeId: '1',
        orgId: orgId,
        supOrgId: orgId,
        orgName: orgName,
        shortName: shortName,
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
        window.location.replace("list.html?id=" + data.id + '&pid=' + data.pid + "&name=" + encodeURI(data.name));
        loading.screenMaskDisable('container');
        toastr.success('新增成功！');
    }, function (err) {

    })
}

// 添加搜索子节点
function  addTreeNode () {
    var loading = parent.loading;
    loading.screenMaskEnable('container');
    $http.post('/orgRel/addOrgRel', JSON.stringify({
        orgRootId: '1',
        orgTreeId: '1',
        supOrgId: orgId,
        orgId: selectNode.orgId
    }), function (data) {
        var newNode = {
            name: selectNode.orgName,
            id: selectNode.orgId
        };
        parent.addNodeById(orgId, newNode);
        parent.openTreeById(orgId, data.id);
        window.location.replace("list.html?id=" + selectNode.orgId + '&pid=' + orgId + "&name=" + encodeURI(selectNode.orgName));
        loading.screenMaskDisable('container');
        toastr.success('新增成功！');
    }, function () {
        loading.screenMaskDisable('container');
    })
}

function add() {
    if (selectNode && selectNode.orgId)
        addTreeNode();
    else
        addOrg();
}
// 取消
function cancel () {
    var url = "list.html?id=" + orgId + "&name=" + encodeURI(orgName);
    window.location.href = url;
}

getScale();
getCityVillage();
getOrgPostLevel();
getStatusCd('1000'); //默认选择生效


var engine, template, empty, selectNode, query;

template = Handlebars.compile($("#result-template").html());

engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name', 'orgName'),
    dupDetector: function(a, b) { return a.id_str === b.id_str; },
    remote: {
        url: '/org/getOrgPage?orgRootId=1&search=%QUERY',
        wildcard: '%QUERY',
        filter: function (response) {
            return response.data.records;
        }
    }
});

function engineWithDefaults(q, sync, async) {
    console.log(query, q, selectNode)
    if (q != query && selectNode && selectNode.orgId){
        // console.log(query, q)
        selectNode = {};
        $('#orgAddForm').find(':input').each(function () {
            $(this).attr('disabled', false)
        });
    }
    if (q === '') {
        async([]);
    }

    else {
        engine.search(q, sync, async);
    }
}

$('#orgName').typeahead({
    hint: $('.typeahead-hint'),
    menu: $('.typeahead-menu'),
    minLength: 0,
    highlight:true,
    classNames: {
        open: 'is-open',
        empty: 'is-empty',
        cursor: 'is-active',
        suggestion: 'Typeahead-suggestion',
        selectable: 'Typeahead-selectable'
    }
}, {
    source: engineWithDefaults,
    displayKey: 'orgName',
    templates: {
        suggestion: template
    }
})
    .on('typeahead:asyncrequest', function() {

    })
    .on('typeahead:asynccancel typeahead:asyncreceive', function() {

    });

// typeahead获取选中的节点
$('#orgName').bind('typeahead:select', function(ev, suggestion) {
    query = $('#orgName').val();
    selectNode = suggestion;
    getOrg(suggestion.orgId);
});

Handlebars.registerHelper('eq', function(v1, v2, opts) {
    if(v1 == v2){
        return opts.fn(this);
    }
    else
        return opts.inverse(this);
});
