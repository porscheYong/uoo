var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var orgRelTypeList = [];
var orgTypeList = [];
var formValidate;
var loading = parent.loading;

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = parent.nodeArr;
    console.log(pathArry)
    var pathStr = '';
    for (var i = pathArry.length - 1; i >= 0; i--) {
        if (i === 0) {
            pathStr +=  '<span class="breadcrumb-item"><a href="javascript:viod(0);">' + pathArry[i] + '</a></span>';
        } else {
            pathStr += '<span class="breadcrumb-item"><a href="javascript:viod(0);">' + pathArry[i] + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
        }
    }
    $('.breadcrumb').html(pathStr);
}
$('.orgName').html(orgName);
getOrgExtInfo();

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
    $http.get('http://134.96.253.221:11100/org/getOrgTree', {
        orgTreeId: '1'
    }, function (data) {
        $('#orgTreeName').val(data.orgTreeName).focus();
        getProperty(data.userTypeList);
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
    $http.get('http://134.96.253.221:11500/tbDictionaryItem/getList/PROPERTY', {}, function (data) {
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
        copyList.push({id: id});
    }
    var orgTreeName = $('#orgTreeName').val();
    var orgTreeType = $('#orgTreeType option:selected') .val();
    var sort = $('#sort').val();
    var userType = $('#userType option:selected') .val();

    $http.post('/orgTree/addOrgTree', JSON.stringify({
        orgTreeName: orgTreeName,
        orgTreeType: orgTreeType,
        orgRelTypeList: orgRelType,
        sort: sort,
        userTypeId: userType,
        orgTypeList: orgType,
        tarOrgTreeId: copyList
    }), function () {
        // parent.changeNodeName(orgId, orgName);
        // window.location.replace("list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName));
        loading.screenMaskDisable('container');
    }, function (err) {

    })
}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + "&name=" + row.orgName;
    window.location.href = url;
}

getOrgTree(1);
getOrgTreeType();
getProperty();
