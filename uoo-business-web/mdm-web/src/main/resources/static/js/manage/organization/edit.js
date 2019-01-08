var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var parentOrgList = [];
var locationList = [];
var orgPostList = [];
var formValidate;
var loading = parent.loading;
var toastr = window.top.toastr;
//字典数据
var statusCdData = window.top.dictionaryData.statusCd();

//上级组织选择
function openOrgDialog() {
    parent.layer.open({
        type: 2,
        title: '上级组织',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: '/inaction/modal/orgDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#superiorOrg').importTags(checkNode, {unique: true});
            superiorOrgList = checkNode;
            parent.layer.close(index);
        }
    });
}

//管理区域选择
function openLocationDialog() {
    parent.layer.open({
        type: 2,
        title: '管理区域',
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
            $('#location').importTags(checkNode, {unique: true});
            locationList = checkNode;
            parent.layer.close(index);
        }
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
        content: '/inaction/modal/postDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#post').importTags(checkNode);
            orgPostList = checkNode;
            parent.layer.close(index);
        }
    });
}

// 获取状态数据
function getStatusCd (statusCd) {
    var option = '';
    for (var i = 0; i < statusCdData.length; i++) {
        var select = statusCd === statusCdData[i].itemValue? 'selected' : '';
        option += "<option value='" + statusCdData[i].itemValue + "' " + select + ">" + statusCdData[i].itemCnname +"</option>";
    }
    $('#statusCd').append(option);
}

// 获取组织基础信息
function getOrg (orgId) {
    $http.get('/sysOrganization/getOrg', {
        id: orgId
    }, function (data) {
        $('#orgName').val(data.orgName).focus();
        $('#sort').val(data.sort);
        getStatusCd(data.statusCd);

        parentOrgList.push({id: pid, name: orgName});
        locationList.push({id: data.regionNbr, name: data.regionName});
        orgPostList = data.sysPositionVos;
        $('#parentOrg').addTag(parentOrgList);
        $('#location').addTag(locationList);
        $('#post').addTag(orgPostList);
        seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
            var orgEditForm = $('#orgEditForm');
            var formValidate = new Validate(orgEditForm);
            formValidate.isAllPass();
        });
    }, function (err) {

    })
}

// 更新组织信息
function updateOrg () {
    if (!formValidate.isAllPass())
        return;
    loading.screenMaskEnable('container');
    var userList = [];
    var location = [];
    var position = [];
    var post = [];
    var orgType = [];
    //联系人
    if (selectUser && selectUser.length > 0) {
        for (var i = 0; i < selectUser.length; i++) {
            userList.push({personnelId: selectUser[i].personnelId});
        }
    }
    //行政管理区域
    for (var i = 0; i < locationList.length; i++) {
        var locId = locationList[i].locId || locationList[i].id;
        location.push({locId: locId});
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
    var orgBizFullName = $('#orgBizFullName').val();
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
    var orgMart = ''; //传给后台的划小组织编码

    $http.post('/org/updateOrg', JSON.stringify({
        orgRootId: '1',
        orgTreeId: '1',
        orgId: orgId,
        supOrgId: pid,
        orgName: orgName,
        shortName: shortName,
        orgBizFullName: orgBizFullName,
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
        expandovalueVoList: expandovalueVoList,
        orgMartCode: orgMart
    }), function () {
        parent.changeNodeName(orgId, orgName);
        window.location.replace("list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName));
        loading.screenMaskDisable('container');
        toastr.success('更新成功！');
    }, function (err) {

    })
}

// 删除组织
function deleteOrg () {
    parent.layer.confirm('此操作将删除该组织, 是否继续?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        loading.screenMaskEnable('container');
        $http.get('/org/deleteOrg', {
            orgTreeId: '1',
            orgId: orgId,
            supOrgId: pid
        }, function () {
            parent.deleteNode(orgId);
            parent.selectRootNode();
            loading.screenMaskDisable('container');
            toastr.success('删除成功！');
        }, function (err) {
            loading.screenMaskDisable('container');
        })
    }, function(){

    });
}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName);
    window.location.href = url;
}

$('.orgName').html(orgName);
// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {
    $('select').selectMatch();
});

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var orgEditForm = $('#orgEditForm');
    formValidate = new Validate(orgEditForm);
    formValidate.immediate();
    orgEditForm.find(':input').each(function () {
        $(this).hover(function () {
            formValidate.isPass($(this));
        });
    });
});

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
    $('#superiorOrg').tagsInput();
    $('#location').tagsInput();
    $('#post').tagsInput();
};

getOrg(orgId);
