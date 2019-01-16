var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var orgFlag = ~~getQueryString('orgFlag');
var checked = 0,
    query,
    delayTime = 500;

function initOrgUserTable (isSearchlower,search) {
    $("#userTable").DataTable({
        'searching': false,
        'destroy': true,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        "scrollY": "375px",
        'scrollCollapse': true,
        'columns': [
            { 'data': "userName", 'title': '用户姓名', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return "<a href='edit.html?id=" + row.orgCode + "&userId=" + row.userId +
                        "&name="+ encodeURI(orgName) + "'>" + row.userName + "</a>";
                }
            },
            { 'data': "accout", 'title': '账号', 'className': 'row-accout' },
            { 'data': "userPositions", 'title': '平台职位', 'className': 'cert-no' },
            { 'data': "orgName", 'title': '归属组织', 'className': '' }
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
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.isSearchlower = isSearchlower;//是否显示下级组织人员
            param.search = search;
            param.id = orgId;
            $http.get('/sysOrganization/getOrgUserPage', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                callback(returnData);
            })
        }
    });

    var loading = parent.loading;
    loading.screenMaskDisable('container');
}

function initPositionUserTable (isSearchlower,search) {
    $("#userTable").DataTable({
        'searching': false,
        'destroy': true,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        "scrollY": "375px",
        'scrollCollapse': true,
        'columns': [
            { 'data': "userName", 'title': '用户姓名', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return "<a href='edit.html?id=" + row.orgCode + "&userId=" + row.userId +
                        "&name="+ encodeURI(orgName) + "'>" + row.userName + "</a>";
                }
            },
            { 'data': "accout", 'title': '账号', 'className': 'row-accout' },
            { 'data': "userPositions", 'title': '平台职位', 'className': 'cert-no' },
            { 'data': "orgName", 'title': '归属组织', 'className': '' }
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
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.isSearchlower = isSearchlower;//是否显示下级组织人员
            param.search = search;
            param.id = orgId;
            $http.get('/sysOrganization/getPositionUserPage', param, function (result) {
                var returnData = {};
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                callback(returnData);
            })
        }
    });

    var loading = parent.loading;
    loading.screenMaskDisable('container');
}

// 搜索用户
function search () {
    query = $('.ui-input-search').val();
    clearTimeout(this.timer);
    // 添加的延时
    this.timer = setTimeout(function(){
        if (orgFlag)
            initOrgUserTable(checked, query);
        else
            initPositionUserTable(checked, query);
    }, delayTime);
}

//勾选显示下级组织用户
function showLower() {
    sortFlag = 0;
    checked = $('#isShowLower').is(':checked')? 1: 0;
    if(isIE8 && checked == 1){
        $(".ui-checkbox").css("background-position","0 -40px");
    }else if(isIE8 && checked == 0){
        $(".ui-checkbox").css("background-position","0px 0px");
    }
    if (orgFlag)
        initOrgUserTable(checked, query);
    else
        initPositionUserTable(checked, query);
}

//创建组织
function addUser() {
    var url = 'add.html?id=' + orgId + '&pid=' + pid + '&name=' + encodeURI(orgName);
    window.location.href = url;
}


$('#orgName').html(orgName);
// 显示组织路径
parent.getOrgExtInfo();
if (orgFlag)
    initOrgUserTable(0);
else
    initPositionUserTable(0);
