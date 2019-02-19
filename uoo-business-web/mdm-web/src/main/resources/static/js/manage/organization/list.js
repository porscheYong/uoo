var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var checked = 0; //是否搜索下级
var table;
var query,
    delayTime = 500;

function initOrgTable (isSearchlower, search) {
    table = $("#orgTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        "scrollY": "395px",
        'scrollCollapse': true,
        'columns': [
            { 'data': "orgName", 'title': '组织名称', 'className': 'row-org',
                'render': function (data, type, row, meta) {
                    return '<a href="info.html?id=' + row.orgId + '&pid=' + orgId + '&name=' + encodeURI(row.orgName) + '">'+ row.orgName +'</a>'
                }
            },
            { 'data': "orgCode", 'title': '组织编码', 'className': 'row-code'},
            { 'data': "regionName", 'title': '管理区域', 'className': 'row-region' },
            { 'data': "positions", 'title': '组织职位', 'className': 'row-position'},
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
            }
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
            param.search = search;
            param.id = orgId;
            $http.get('/sysOrganization/getOrgRelPage', param, function (result) {
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
function search () {
    query = $('.ui-input-search').val();
    clearTimeout(this.timer);
    // 添加的延时
    this.timer = setTimeout(function(){
        initOrgTable(checked, query);
    }, delayTime);
}

//勾选显示下级组织人员
function showLower() {
    sortFlag = 0;
    checked = $('#isShowLower').is(':checked')? 1: 0;
    if(isIE8 && checked == 1){
        $(".ui-checkbox").css("background-position","0 -40px");
    }else if(isIE8 && checked == 0){
        $(".ui-checkbox").css("background-position","0px 0px");
    }
    initOrgTable(checked, query);
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
    $http.get('/sysOrganization/getOrgPositionList', {
        id: orgId
    }, function (data) {
        console.log(data)
        var positionList = [];
        var positionStr= '';
        if (data.length > 0) {
            positionList = data[0].positionName.split('&');
        }
        for (var i = 0; i < positionList.length; i++) {
            positionStr += '<span class="uoo-tag">'+ positionList[i] +'</span>';
        }
        $('.org-ext').html(positionStr);
    }, function (err) {

    })
}

$('#orgName').html(orgName);
// 显示组织路径
parent.getOrgExtInfo();
getOrgExt();
initOrgTable(0);
