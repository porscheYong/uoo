var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var locationList;
var orgTypeList;
var positionList;
var orgPostList;
var checkNode;
var selectUser = [];
var formValidate;
var loading = parent.loading;
var toastr = parent.parent.toastr;

$('.orgName').html(orgName);
// 显示组织路径
parent.getOrgExtInfo();

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
  $('#locationList').tagsInput();
  $('#orgTypeList').tagsInput();
  $('#positionList').tagsInput();
  $('#postList').tagsInput();
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

// 组织关系信息初始化
function initOrgRelTable (results) {
    var table = $("#orgRelTable").DataTable({
        'data': results,
        'searching': false,
        'ordering': false,
        'autoWidth': false,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-name' },
            { 'data': "orgName",
              'title': '组织名称',
              'className': 'row-sex',
              // 'render': function (data) {
              //   return data[0].orgTypeName
              // }
            },
            { 'data': "orgBizName", 'title': '组织称谓', 'className': 'user-account' },
            { 'data': "refName", 'title': '关系类型', 'className': 'user-account' },
            { 'data': "supOrgName", 'title': '上级组织', 'className': 'user-type' },
            { 'data': "createDate", 'title': '添加时间', 'className': 'role-type' }
        ],
        'language': {
            'emptyTable': '没有数据',
            'loadingRecords': '加载中...',
            'processing': '查询中...',
            'search': '检索:',
            'lengthMenu': ' _MENU_ ',
            'zeroRecords': '没有数据',
            'paginate': {
                'first':      '首页',
                'last':       '尾页',
                'next':       '下一页',
                'previous':   '上一页'
            },
            'info': '总_TOTAL_人',
            'infoEmpty': '没有数据'
        },
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom">',
        'drawCallback': function(){
            this.api().column(0).nodes().each(function(cell, i) {
        　　　　cell.innerHTML =  i + 1;
        　　});
         }
    });
}

// 获取规模字典数据
function getScale (orgScale) {
    $http.get('/tbDictionaryItem/getList/SCALE', {}, function (data) {
        var value = '';
        for (var i = 0; i < data.length; i++) {
            if (orgScale === data[i].itemValue) {
                value = data[i].itemCnname
            }
        }
        $('#orgScale').html(value);
    }, function (err) {

    })
}

// 获取城乡字典数据
function getCityVillage (cityTown) {
    $http.get('/tbDictionaryItem/getList/CITY_VILLAGE', {}, function (data) {
        var value = '';
        for (var i = 0; i < data.length; i++) {
            if (cityTown === data[i].itemValue) {
                value = data[i].itemCnname
            }
        }
        $('#cityTown').html(value);
    }, function (err) {

    })
}

// 获取组织最高岗位级别字典数据
function getOrgPostLevel (orgPositionLevel) {
    $http.get('/tbDictionaryItem/getList/ORG_POST_LEVEL', {}, function (data) {
        var value = '';
        for (var i = 0; i < data.length; i++) {
            if (orgPositionLevel === data[i].itemValue) {
                value = data[i].itemCnname
            }
        }
        $('#orgPositionLevel').html(value);
    }, function (err) {

    })
}

// 获取状态数据
function getStatusCd (statusCd) {
    $http.get('/tbDictionaryItem/getList/STATUS_CD', {}, function (data) {
        var value = '';
        for (var i = 0; i < data.length; i++) {
            if (statusCd === data[i].itemValue) {
                value = data[i].itemCnname
            }
        }
        $('#statusCd').html(value);
    }, function (err) {

    })
}

// 获取组织基础信息
function getOrg (orgId) {
    $http.get('/org/getOrg', {
        orgTreeId: '1',
        orgId: orgId
    }, function (data) {
        $('#orgName').html(data.orgName);
        $('#orgCode').html(data.orgCode);
        $('#shortName').html(data.shortName);
        $('#fullName').html(data.fullName);
        $('#orgMartCode ').html(data.orgMartCode);
        $('#createDate ').html(data.createDate);
        $('#orgNameEn').html(data.orgNameEn);
        $('#officePhone').html(data.officePhone);
        $('#sort').html(data.sort);
        $('#address').html(data.address);
        if (data.orgContent) {
            var content = "<p>"+ data.orgContent + "</p>";
            $('#orgContent').html(content);
        }
        $('#orgDesc').html(data.orgDesc);

        getScale(data.orgScale);
        getCityVillage(data.cityTown);
        getOrgPostLevel(data.orgPositionLevel);
        getStatusCd(data.statusCd);
        locationList = data.politicalLocationList;
        orgTypeList = data.orgTypeList;
        positionList = data.positionList;
        orgPostList = data.postList;
        $('#locationList').addTag(locationList);
        $('#orgTypeList').addTag(orgTypeList);
        $('#positionList').addTag(positionList);
        $('#postList').addTag(orgPostList);
    }, function (err) {

    })
}

// 获取组织关系信息
function getOrgRel (orgId) {
    $http.get('/orgRel/getOrgRelTypePage', {
        orgTreeId: '1',
        orgId: orgId,
        pageSize: 10,
        pageNo: 1
    }, function (data) {
        initOrgRelTable(data.records);
    }, function (err) {

    })
}

getOrg(orgId);
getOrgRel(orgId);
