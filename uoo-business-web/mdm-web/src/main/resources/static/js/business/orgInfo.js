var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var standardFlag = ~~getQueryString('standardFlag');
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

//字典数据
var scaleData = window.top.dictionaryData.scale();
var cityVillageData = window.top.dictionaryData.cityVillage();
var orgPostLevelData = window.top.dictionaryData.orgPostLevel();
var statusCdData = window.top.dictionaryData.statusCd();

$('.orgName').html(orgName);
//不可编辑
if (standardFlag)
    $('#editBtn').remove();
// 显示组织路径
parent.getOrgExtInfo();

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
    $('#locationList').tagsInput();
    $('#orgTypeList').tagsInput();
    $('#positionList').tagsInput();
    $('#postList').tagsInput();
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
    var value = '';
    for (var i = 0; i < scaleData.length; i++) {
        if (orgScale === scaleData[i].itemValue) {
            value = scaleData[i].itemCnname
        }
    }
    $('#orgScale').html(value);
}

// 获取城乡字典数据
function getCityVillage (cityTown) {
    var value = '';
    for (var i = 0; i < cityVillageData.length; i++) {
        if (cityTown === cityVillageData[i].itemValue) {
            value = cityVillageData[i].itemCnname
        }
    }
    $('#cityTown').html(value);
}

// 获取组织最高岗位级别字典数据
function getOrgPostLevel (orgPositionLevel) {
    var value = '';
    for (var i = 0; i < orgPostLevelData.length; i++) {
        if (orgPositionLevel === orgPostLevelData[i].itemValue) {
            value = orgPostLevelData[i].itemCnname
        }
    }
    $('#orgPositionLevel').html(value);
}

// 获取状态数据
function getStatusCd (statusCd) {
    var value = '';
    for (var i = 0; i < statusCdData.length; i++) {
        if (statusCd === statusCdData[i].itemValue) {
            value = statusCdData[i].itemCnname
        }
    }
    $('#statusCd').html(value);
}

// 获取组织基础信息
function getOrg (orgId) {
    $http.get('/org/getOrg', {
        orgTreeId: orgTreeId,
        orgId: orgId
    }, function (data) {
        $('#orgName').html(data.orgName);
        $('#orgCode').html(data.orgCode);
        $('#shortName').html(data.shortName);
        $('#orgBizFullName').html(data.orgBizFullName);
        $('#orgMartCode ').html(data.orgMartCode);
        $('#foundingTime ').html(data.foundingTime);
        $('#orgNameEn').html(data.orgNameEn);
        $('#officePhone').html(data.officePhone);
        $('#sort').html(data.sort);
        $('#address').html(data.address);
        if (data.psonOrgVoList && data.psonOrgVoList.length > 0) {
            $('#psonOrgVoList').html(data.psonOrgVoList[0].psnName);
        }
        if (data.orgContent) {
            var content = "<p>"+ data.orgContent + "</p>";
            $('#orgContent').html(content);
        }
        $('#orgDesc').html(data.orgDesc);

        getScale(data.orgScale);
        getCityVillage(data.cityTown);
        getOrgPostLevel(data.orgPositionLevel);
        // getStatusCd(data.statusCd);
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
        orgTreeId: orgTreeId,
        orgId: orgId,
        pageSize: 10,
        pageNo: 1
    }, function (data) {
        initOrgRelTable(data.records);
    }, function (err) {

    })
}

function orgEdit () {
    var url = 'orgEdit.html?id=' + orgId + '&orgTreeId=' + orgTreeId + '&pid=' + pid + '&name=' + encodeURI(orgName);
    $('#editBtn').attr('href', url);
}

getOrg(orgId);
getOrgRel(orgId);
