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

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
    //默认显示上级组织
    parentOrgList.push({id: orgId, pid: pid, name: orgName});
    $('#parentOrg').tagsInput({unique: true});
    $('#parentOrg').importTags(parentOrgList, {unique: true});

    $('#location').tagsInput({unique: true});
    $('#post').tagsInput({unique: true});
}

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
            parentOrgList = checkNode;
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

// 新增平台组织
function addOrg () {
    if (!formValidate.isAllPass())
        return;
    loading.screenMaskEnable('container');

    var orgName = $('#orgName').val();
    var sort = $('#sort').val();
    var statusCd = $('#statusCd option:selected') .val();
    //组织职位
    var sysPositionVos = [];
    for (var i = 0; i < orgPostList.length; i++) {
        sysPositionVos.push({positionId: orgPostList[i].id});
    }

    $http.post('/sysOrganization/addOrg', JSON.stringify({
        orgName: orgName,
        parentOrgCode: parentOrgList[0].id,
        regionNbr: locationList[0].id,
        sysPositionVos: sysPositionVos,
        sort: sort,
        statusCd: statusCd
    }), function () {
        loading.screenMaskDisable('container');
        toastr.success('新增成功！');
    }, function () {
        loading.screenMaskDisable('container');
    })
}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName);
    window.location.href = url;
}

getStatusCd('1000'); //默认选择生效
