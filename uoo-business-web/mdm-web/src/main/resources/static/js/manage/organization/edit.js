var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var pidName = getQueryString('pidName');
var flag = getQueryString('flag');
// var pName = parent.getNodeName(pid);
// var orgList = [{id: orgId, name: orgName}];
var orgList = [];
var locationList = [];
var postList = [];
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
            $('#parentOrg').importTags(checkNode, {unique: true});
            orgList = checkNode;
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
            postList = checkNode;
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
    $('#statusCd').selectMatch();
}

// 获取组织基础信息
function getOrg (orgId) {
    $http.get('/sysOrganization/getOrg', {
        id: orgId
    }, function (data) {
        orgCode = data.orgCode;
        $('#orgName').val(data.orgName).focus();
        $('#orgCode').val(data.orgCode);
        $('#sort').val(data.sort);
        getStatusCd(data.statusCd);

        if(data.parentOrgCode != null){
            orgList.push({id: data.parentOrgCode, name: data.parentOrgName});
        }
        
        locationList.push({id: data.regionNbr, name: data.regionName});
        postList = data.sysPositionVos;
        $('#parentOrg').addTag(orgList);
        if(data.regionNbr != null && data.regionName != null){
            $('#location').addTag(locationList);
        }
        $('#post').addTag(postList);
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
    var parOrg = "";
    var orgName = $('#orgName').val();
    var orgCode = $('#orgCode').val();
    var sort = $('#sort').val();
    var statusCd = $('#statusCd option:selected') .val();
    //组织职位
    var sysPositionVos = [];
    for (var i = 0; i < postList.length; i++) {
        var posCode = postList[i].positionCode || postList[i].id;
        sysPositionVos.push({positionCode: posCode});
    }

    if(orgList.length != 0){
        parOrg = orgList[0].id;
    }

    $http.post('/sysOrganization/updateOrg', JSON.stringify({
        orgId: orgId,
        orgName: orgName,
        orgCode: orgCode,
        parentOrgCode: parOrg,
        regionNbr: locationList[0].id,
        sysPositionVos: sysPositionVos,
        sort: sort,
        statusCd: statusCd
    }), function () {
        parent.changeNodeName(orgId, orgName);
        if(flag == 1){
            window.location.replace("list.html?id=" + pid + '&pid=' + pid + "&name=" + encodeURI(pidName));
        }else{
            window.location.replace("list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName));
        }
        loading.screenMaskDisable('container');
        toastr.success('更新成功！');
    }, function (err) {
        loading.screenMaskDisable('container');
    })
}

// 删除组织
function deleteOrg () {
    parent.layer.confirm('此操作将删除该组织, 是否继续?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index){
        parent.layer.close(index);
        loading.screenMaskEnable('container');
        $http.get('/sysOrganization/deleteOrg', {
            id: orgId
        }, function () {
            parent.deleteNode(orgId);
            parent.selectNodeById(pid);
            loading.screenMaskDisable('container');
            toastr.success('删除成功！');
        }, function () {
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
    $('#parentOrg').tagsInput();
    $('#location').tagsInput();
    $('#post').tagsInput();
};

getOrg(orgId);
