var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var pName = parent.getNodeName(pid);
var parentOrgList = [{id: orgId, name: pName}];
var locationList = [];
var orgPostList = [];
var loading = parent.loading;
var toastr = parent.parent.toastr;
//字典数据
var statusCdData = window.top.dictionaryData.statusCd();

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
    $http.get('/sysOrganization/getOrg', {
        id: orgId
    }, function (data) {
        $('#orgName').html(data.orgName);
        $('#orgCode').html(data.orgCode);
        $('#sort').html(data.sort);
        getStatusCd(data.statusCd);

        locationList.push({id: data.regionNbr, name: data.regionName});
        orgPostList = data.sysPositionVos;
        $('#parentOrg').addTag(parentOrgList);
        $('#location').addTag(locationList);
        $('#post').addTag(orgPostList);
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

$('.orgName').html(orgName);
// 显示组织路径
parent.getOrgExtInfo();
// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
    $('#parentOrg').tagsInput();
    $('#location').tagsInput();
    $('#post').tagsInput();
}
getOrg(orgId);
getOrgRel(orgId);
