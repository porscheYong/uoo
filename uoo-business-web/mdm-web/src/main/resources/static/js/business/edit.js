var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var orgTypeTreeName = getQueryString('treeName');
var orgRelTypeList = [];
var orgTypeList = [];
var formValidate;
var loading = parent.loading;
var toastr = window.top.toastr;

$('#orgTypeTreeName').html(orgTypeTreeName);

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {

})

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var businessEditForm = $('#businessEdit');
    formValidate = new Validate(businessEditForm);
    formValidate.immediate();
    businessEditForm.find(':input').each(function () {
        $(this).hover(function () {
            formValidate.isPass($(this));
        });
    });
})

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
  $('#orgRelType').tagsInput();
  $('#orgType').tagsInput();
}

// 获取组织树基础信息
function getOrgTree () {
    $http.get('/orgTree/getOrgTree', {
        orgTreeId: orgTreeId
    }, function (data) {
        $('#orgTreeName').val(data.orgTreeName).focus();
        // getProperty(data.userTypeList);
        $('#sort').val(data.sort);
        orgRelTypeList = data.orgRelTypeList;
        orgTypeList = data.orgTypeList;
        $('#orgRelType').addTag(orgRelTypeList);
        $('#orgType').addTag(orgTypeList);
    }, function (err) {
        console.log(err)
    })
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
        console.log(err)
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
        console.log(err)
    })
}

// 更新业务组织树
function updateOrgTree () {
    if (!formValidate.isAllPass())
        return;
    loading.screenMaskEnable('container');
    var orgType = [];
    var orgRelType = [];
    // 组织关系类型类别
    for (var i = 0; i < orgRelTypeList.length; i++) {
        var id = orgRelTypeList[i].refCode || orgRelTypeList[i].id;
        orgRelType.push({refCode: id});
    }
    //组织类别
    for (var i = 0; i < orgTypeList.length; i++) {
        var orgTypeId = orgTypeList[i].orgTypeId || orgTypeList[i].id;
        orgType.push({orgTypeId: orgTypeId});
    }
    var orgTreeName = $('#orgTreeName').val();
    var orgTreeType = $('#orgTreeType option:selected') .val();
    var sort = $('#sort').val();
    // var userType = $('#userType option:selected') .val();

    $http.post('/orgTree/updateOrgTree', JSON.stringify({
        orgTreeId: orgTreeId,
        orgTreeName: orgTreeName,
        orgTreeType: orgTreeType,
        orgRelTypeList: orgRelType,
        sort: sort,
        // userTypeId: userType,
        orgTypeList: orgType
    }), function () {
        parent.initBusinessList();
        loading.screenMaskDisable('container');
        toastr.success('更新成功！');
    }, function (err) {
        loading.screenMaskDisable('container');
    })
}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName);
    window.location.href = url;
}

getOrgTree();
getOrgTreeType();
// getProperty();
