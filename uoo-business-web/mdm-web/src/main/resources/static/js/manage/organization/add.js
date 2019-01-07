var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var superiorOrgList = [];
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
    superiorOrgList.push({id: orgId, pid: pid, name: orgName});
    $('#superiorOrg').tagsInput({unique: true});
    $('#superiorOrg').importTags(superiorOrgList, {unique: true});

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
        content: '/inaction/organization/postDialog.html',
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
    var orgType = [];
    var orgRelType = [];
    var copyList = [];
    // 组织关系类型类别
    for (var i = 0; i < orgRelTypeList.length; i++) {
        var id = orgRelTypeList[i].id;
        orgRelType.push({refCode: id});
    }
    //组织类别
    for (var i = 0; i < orgTypeList.length; i++) {
        var orgTypeId = orgTypeList[i].orgTypeId || orgTypeList[i].id;
        orgType.push({orgTypeId: orgTypeId});
    }
    //拷贝组织节点
    for (var i = 0; i < orgCopyList.length; i++) {
        var id = orgCopyList[i].id;
        var pid = orgCopyList[i].pid;
        var name = orgCopyList[i].name;
        var level = orgCopyList[i].level;
        var checked = orgCopyList[i].checked;
        copyList.push({
            id: id,
            pid: pid,
            name: name,
            level: level,
            checked: checked
        });
    }
    var orgTreeName = $('#orgTreeName').val();
    var orgTreeType = $('#orgTreeType option:selected') .val();
    var sort = $('#sort').val();
    // var userType = $('#userType option:selected') .val();

    $http.post('/orgTree/addOrgTree', JSON.stringify({
        orgTreeName: orgTreeName,
        orgTreeType: orgTreeType,
        orgRelTypeList: orgRelType,
        sort: sort,
        // userTypeId: userType,
        orgTypeList: orgType,
        treeNodeList: copyList,
        tarOrgTreeId: tarOrgTreeId
    }), function () {
        parent.initBusinessList();
        loading.screenMaskDisable('container');
        toastr.success('新增成功！');
    }, function (err) {
        loading.screenMaskDisable('container');
    })
}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName);
    window.location.href = url;
}

getStatusCd('1000'); //默认选择生效
