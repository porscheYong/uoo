var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var locationList = [];
var orgTypeList = [];
var positionList = [];
var orgPostList = [];
var regionList = [];
var checkNode;
var selectUser = [];
var formValidate;
var loading = parent.loading;
var toastr = parent.parent.toastr;

$('.orgName').html(orgName);
// 显示组织路径
parent.getOrgExtInfo();

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {

})

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
    elem: '#createDate'
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
        content: 'typeDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgTypeList').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
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
            parent.layer.close(index);
            $('#positionList').importTags(checkNode);
            // $('.ui-tips-error').css('display', 'none');
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
            parent.layer.close(index);
            $('#postList').importTags(checkNode);
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
            parent.layer.close(index);
            $('#locationList').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            locationList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//电信管理区域选择
function openRegionDialog() {
    parent.layer.open({
        type: 2,
        title: '电信管理区域',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'regionDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#regionId').importTags(checkNode);
            regionList = checkNode;
            parent.layer.close(index);
            getAreaId(checkNode[0].id);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//根据电信管理区域ID获取区号
function getAreaId(regionId) {
    $http.get('/region/commonRegion/getCommonRegion/id='+ regionId, {}, function (data) {
        $('#areaCodeId').val(data.areaCode.areaCodeId);
    }, function (err) {
        console.log(err)
    })
}

// 获取规模字典数据
function getScale () {
    $http.get('/tbDictionaryItem/getList/SCALE', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#orgScale').append(option);
        $('#orgScale').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取城乡字典数据
function getCityVillage () {
    $http.get('/tbDictionaryItem/getList/CITY_VILLAGE', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#cityTown').append(option);
        $('#cityTown').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取组织最高岗位级别字典数据
function getOrgPostLevel () {
    $http.get('/tbDictionaryItem/getList/ORG_POST_LEVEL', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#orgPositionLevel').append(option);
        $('#orgPositionLevel').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取状态数据
function getStatusCd () {
    $http.get('/tbDictionaryItem/getList/STATUS_CD', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#statusCd').append(option);
        $('#statusCd').selectMatch();
    }, function (err) {
        console.log(err)
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
    var date = $('#createDate').val();
    var createDate;
    if (date) {
        createDate = new Date(date).getTime();
    }
    var orgPositionLevel = $('#orgPositionLevel option:selected') .val();
    var officePhone = $('#officePhone').val();
    var statusCd = $('#statusCd option:selected') .val();
    var sort = $('#sort').val();
    var areaCodeId = $('#areaCodeId').val();
    var address = $('#address').val();
    var orgContent = $('#orgContent').val();
    var orgDesc = $('#orgDesc').val();
    $http.post('/org/addOrg', JSON.stringify({
        orgRootId: '1',
        orgTreeId: '1',
        orgId: orgId,
        supOrgId: orgId,
        orgName: orgName,
        shortName: shortName,
        cityTown: cityTown,
        orgScale: orgScale,
        createDate: createDate,
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
        orgDesc: orgDesc
    }), function (data) {
        parent.addNodeById(orgId, data);
        parent.openTreeById(orgId, data.id);
        window.location.replace("list.html?id=" + data.id + '&pid=' + data.pid + "&name=" + encodeURI(data.name));
        loading.screenMaskDisable('container');
        toastr.success('新增成功！');
    }, function (err) {
        console.log(err);
    })
}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + "&name=" + orgName;
    window.location.href = url;
}

getScale();
getCityVillage();
getOrgPostLevel();
getStatusCd();

