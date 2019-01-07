var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var table;
var lastTime,
    minChars = 0, // 最小进行查询的数量
    delayTime = 500;

function initOrgTable (isSearchlower) {
    table = $("#orgTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        "scrollY": "395px",
        'scrollCollapse': true,
        'columns': [
            { 'data': "orgName", 'title': '部门', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return '<a href="info.html?id=' + row.orgId + '&pid=' + orgId + '&name=' + encodeURI(row.orgName) + '">'+ row.orgName +'</a>'
                }
            },
            { 'data': "orgTypeSplit",
                'title': '组织类别',
                'className': 'row-sex',
                // 'render': function (data) {
                //   return data[0].orgTypeName
                // }
            },
            { 'data': "orgCode", 'title': '编码', 'className': 'user-account' },
            { 'data': "locName", 'title': '行政管理区域', 'className': 'user-type' },
            { 'data': "statusCd", 'title': '状态', 'className': 'role-type',
                'render': function (data, type, row, meta) {
                    var statusStr = '';
                    if (row.statusCd == '1000') {
                        statusStr = '<span>有效</span>';
                    } else {
                        statusStr = '<span>无效</span>';
                    }
                    return statusStr
                }
            },
            { 'data': "orgId", 'title': '', 'className': 'row-orgId' }
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
            'info': '总_TOTAL_条',
            'infoEmpty': '没有数据'
        },
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.isSearchlower = isSearchlower;//是否显示多层下级组织
            param.orgTreeId = '1';
            param.orgId = orgId;
            $http.get('/org/getOrgRelPage', param, function (result) {
                var returnData = {};
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                callback(returnData);
            }, function (err) {

            })
        }
    });

    var loading = parent.loading;
    loading.screenMaskDisable('container');
}

// 搜索组织
function search (event) {
    console.log(event, event.timeStamp)
    lastTime = event.timeStamp;
    var query = $('.ui-input-search').val();

    if (minChars && query.length < minChars) {
        return
    }

    // 添加的延时
    setTimeout(function(){
        if (lastTime - event.timeStamp === 0) {
            initOrgTable(query);
            console.log(query)
        }
    }, delayTime)
}

//勾选显示下级组织人员
function showLower() {
    sortFlag = 0;
    var checked = $('#isShowLower').is(':checked')? 1: 0;
    if(isIE8 && checked == 1){
        $(".ui-checkbox").css("background-position","0 -40px");
    }else if(isIE8 && checked == 0){
        $(".ui-checkbox").css("background-position","0px 0px");
    }
    initOrgTable(checked);
}

//创建组织
function addOrg() {
    var url = 'add.html?id=' + orgId + '&pid=' + pid + '&name=' + encodeURI(orgName);
    window.location.href = url;
}

//查看组织详情
function getOrgInfo() {
    var url = 'info.html?id=' + orgId + '&pid=' + pid + '&name=' + encodeURI(orgName);
    window.location.href = url;
}

//编辑组织
function orgEdit() {
    var url = 'edit.html?id=' + orgId + '&pid=' + pid + '&name=' + encodeURI(orgName);
    window.location.href = url;
}

//获取组织扩展信息
function getOrgExt() {
    $http.get('/org/getOrgExtByOrgId', {
        orgTreeId: '1',
        orgId: orgId
    }, function (data) {
        var followOrgList = [];
        var orgTypeInfoList = [];
        var followOrgStr= '';
        var orgTypeInfoStr= '';
        if (data.FOLLOW_ORG)
            followOrgList = data.FOLLOW_ORG.split(',');
        if (data.ORG_TYPE_INFO)
            orgTypeInfoList = data.ORG_TYPE_INFO.split(',');
        for (var i = 0; i < followOrgList.length; i++) {
            followOrgStr += '<span class="uoo-tag">'+ followOrgList[i] +'</span>';
        }
        for (var i = 0; i < orgTypeInfoList.length; i++) {
            orgTypeInfoStr += '<span class="uoo-tag">'+ orgTypeInfoList[i] +'</span>';
        }
        $('#followOrg').html(followOrgStr);
        $('#orgTypeInfo').html(orgTypeInfoStr);
    }, function (err) {

    })
}

$('#orgName').html(orgName);
// 显示组织路径
parent.getOrgExtInfo();
getOrgExt();
initOrgTable(0);
