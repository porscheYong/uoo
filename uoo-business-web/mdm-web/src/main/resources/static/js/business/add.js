var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var orgTypeTreeName = getQueryString('treeName');
var orgRelTypeList = [];
var orgTypeList = [];
var orgCopyList = [];
var tarOrgTreeId = '';
var formValidate;
var loading = parent.loading;
var toastr = window.top.toastr;

$('#orgTypeTreeName').html(orgTypeTreeName);

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {

})

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var businessAddForm = $('#businessAdd');
    formValidate = new Validate(businessAddForm);
    formValidate.immediate();
    businessAddForm.find(':input').each(function () {
        $(this).hover(function () {
            formValidate.isPass($(this));
        });
    });
    formValidate.isAllPass();
});

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
  $('#orgRelType').tagsInput({unique: true});
  $('#orgType').tagsInput({unique: true});
  $('#copyTree').tagsInput();
}

//组织关系类型选择
function openRelTypeDialog() {
    parent.layer.open({
        type: 2,
        title: '组织关系类型',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'relTypeDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgRelType').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            orgRelTypeList = checkNode;
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
        content: 'typeDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgType').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            orgTypeList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//拷贝组织节点
function openCopyDialog() {
    parent.layer.open({
        type: 2,
        title: '拷贝组织节点',
        shadeClose: true,
        shade: 0.8,
        area: ['70%', '85%'],
        maxmin: true,
        content: 'copyDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            // checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            checkNode = iframeWin.getCheckdNodes();
            $('#copyTree').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            tarOrgTreeId = iframeWin.targetId;
            orgCopyList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}


// 获取组织树类型数据
function getOrgTreeType () {
    $http.get('/tbDictionaryItem/getList/ORG_TREE_TYPE', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = i === 0? 'selected' : '';
            option += "<option value='" + data[i].itemValue + "' " + select + ">" + data[i].itemCnname +"</option>";
        }
        $('#orgTreeType').append(option);
        $('#orgTreeType').selectMatch();
    }, function (err) {

    })
}

// 获取用工性质
function getProperty () {
    $http.get('/tbDictionaryItem/getList/PROPERTY', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
          option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#userType').append(option);
        $('#userType').selectMatch();
    }, function (err) {

    })
}

// 新增业务组织树
function addOrgTree () {
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
    var url = "list.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName);
    window.location.href = url;
}

getOrgTreeType();
// getProperty();
