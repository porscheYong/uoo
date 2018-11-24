var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var checkNode;
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
$('#orgName').focus();

//TODO select源码修改，使用方式修改
// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function (Select) {
    // $('#locId').selectMatch();
    // $('#statusCd').selectMatch();
    // $('select').each(function () {
    //     new Select($(this));
    // });
})

// // lulu ui select插件
// seajs.use('/vendors/lulu/js/common/ui/Select', function () {
//     $('select').selectMatch();
// })

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var orgAddForm = $('#orgAddForm');
    formValidate = new Validate(orgAddForm);
    formValidate.immediate();
    orgAddForm.find(':input').each(function () {
        $(this).hover(function () {
            formValidate.isPass($(this));
        });
    })
})

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
    $('#orgTypeList').tagsInput();
    $('#positionList').tagsInput();
    $('#postList').tagsInput();
}

$('#orgTypeList_tagsinput').on('click', function() {
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
            orgTypeList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
})

// 获取规模字典数据
function getScale () {
    $http.get('http://134.96.253.221:11500/tbDictionaryItem/getList/SCALE', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#orgScale').append(option);
        // $('#orgScale').selectMatch();
    }, function (err) {
        console.log(err)
    })
}
// 获取城乡字典数据
function getCityVillage () {
    $http.get('http://134.96.253.221:11500/tbDictionaryItem/getList/CITY_VILLAGE', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#cityTown').append(option);
        // $('#cityTown').selectMatch();
    }, function (err) {
        console.log(err)
    })
}
// 获取组织最高岗位级别字典数据
function getOrgPostLevel () {
    $http.get('http://134.96.253.221:11500/tbDictionaryItem/getList/ORG_POST_LEVEL', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#orgPositionLevel').append(option);
        // $('#orgPositionLevel').selectMatch();
    }, function (err)  {
        console.log(err)
    })
}
// 获取状态数据
function getStatusCd () {
    var statusArry = [{"itemValue":"1000","itemCnname":"有效"},
        {"itemValue":"1100","itemCnname":"无效"}];
    var option = '<option></option>';
    for (var i = 0; i < statusArry.length; i++) {
        option += "<option value='" + statusArry[i].itemValue + "'>" + statusArry[i].itemCnname +"</option>";
    }
    $('#statusCd').append(option);
    // $('#statusCd').selectMatch();
}

// 更新组织信息
function addOrg () {
    if (!formValidate.isAllPass())
        return;
    loading.screenMaskEnable('container');
    var orgType = [];
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
    var locId = $('#locId option:selected') .val();
    var orgPositionLevel = $('#orgPositionLevel option:selected') .val();
    var officePhone = $('#officePhone').val();
    var statusCd = $('#statusCd option:selected') .val();
    var sort = $('#sort').val();
    var address = $('#address').val();
    var orgContent = $('#orgContent').val();
    var orgDesc = $('#orgDesc').val();
    $http.post('http://134.96.253.221:11100/org/addOrg', JSON.stringify({
        orgRootId: '1',
        supOrgId: orgId,
        orgName: orgName,
        orgScale: orgScale,
        shortName: shortName,
        orgNameEn: orgNameEn,
        cityTown: cityTown,
        // createDate: createDate,
        locId: locId,
        orgPositionLevel: orgPositionLevel,
        officePhone: officePhone,
        statusCd: statusCd,
        sort: sort,
        address: address,
        orgTypeList: orgType,
        orgContent: orgContent,
        orgDesc: orgDesc
    }), function (data) {
        parent.addNodeById(orgId, data);
        parent.openTreeById(orgId, data.id);
        window.location.replace("list.html?id=" + data.id + '&pid=' + data.pid + "&name=" + encodeURI(data.name));
        loading.screenMaskDisable('container');
    }, function (err) {
        console.log(err);
    })
}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + "&name=" + row.orgName;
    window.location.href = url;
}
getScale();
getCityVillage();
getOrgPostLevel();
getStatusCd();
