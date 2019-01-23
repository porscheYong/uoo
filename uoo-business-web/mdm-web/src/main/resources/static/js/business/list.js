var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var refCode = getQueryString('refCode');
var standardFlag = ~~getQueryString('standardFlag');
var table;
var personnelTable;

if (!window.addEventListener) {
    seajs.use('/vendors/lulu/js/common/ui/Radio');
    seajs.use('/vendors/lulu/js/common/ui/Checkbox');
}

function initOrgTable (results) {
    table = $("#orgTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        "scrollY": "395px",
        'scrollCollapse': true,
        'columns': [
            { 'data': "orgId", 'title': '组织标识', 'className': 'org-id' },
            { 'data': "orgName", 'title': '部门', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return '<a href="orgInfo.html?id=' + row.orgId + '&pid=' + orgId + '&ppid=' + pid + '&orgTreeId=' + orgTreeId + '&standardFlag=' + standardFlag + '&name=' + encodeURI(row.orgName) + '&pName=' + encodeURI(orgName)  +  '">'+ row.orgName +'</a>'
                }
            },
            { 'data': "orgTypeSplit",
                'title': '组织类别',
                'className': 'row-sex'
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
        'serverSide': true,  // 服务端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.orgTreeId = orgTreeId;
            param.orgId = orgId;
            $http.get('/org/getOrgRelPage', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
            }, function (err) {

            })
        }
    });
    
    var loading = parent.loading;
    loading.screenMaskDisable('container');
}

function initOrgPersonnelTable (isSearchlower) {
    personnelTable = $("#personnelTable").DataTable({
        'searching': false,
        'destroy': true,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        "scrollY": "395px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no',
              'render': function (data, type, row, meta) {
                return meta.row + 1 + meta.settings._iDisplayStart;
              }
            },
            { 'data': "psnName", 'title': '姓名', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return '<a href="/inaction/user/edit.html?id='+ row.orgId +'&orgTreeId=' + orgTreeId + '&name=' + row.orgName + '&personnelId=' + row.personnelId + '">'+ row.psnName +'</a>';
                }
            },
            { 'data': "mobile", 'title': '手机号码', 'className': 'row-mobile' },
            { 'data': "psnNbr", 'title': '员工工号', 'className': 'cert-no' },
            { 'data': "postName", 'title': '职位名称', 'className': 'post-name' },
            { 'data': "orgName", 'title': '所属组织', 'className': 'org-name' },
            { 'data': "statusCd", 'title': '状态', 'className': 'status-code',
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
            { 'data': "orgId", 'title': '', 'className': 'row-orgId'},
            { 'data': "personnelId", 'title': '', 'className': 'row-personnelId'}
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
        'serverSide': true,  // 服务端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.isSearchlower = isSearchlower;//是否显示下级组织人员
            param.orgTreeId = orgTreeId;
            param.orgId = orgId;
            $http.get('/orgPersonRel/getPerOrgRelPage', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
            }, function (err) {

            })
        }
    });
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
    initOrgPersonnelTable(checked);
}

//获取组织扩展信息
function getOrgExt() {
    $http.get('/org/getOrgExtByOrgId', {
        orgTreeId: orgTreeId,
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
//不可编辑
if (standardFlag)
    $('#editBtn').remove();
parent.getOrgExtInfo();
getOrgExt();
initOrgTable();
initOrgPersonnelTable(0);

function orgInfo() {
    var url = 'orgInfo.html?id=' + orgId + '&orgTreeId=' + orgTreeId + '&pid=' + pid + '&refCode=' + refCode + '&standardFlag=' + standardFlag + '&name=' + encodeURI(orgName);
    window.location.href = url;
}
function orgEdit () {
    var url = 'orgEdit.html?id=' + orgId + '&orgTreeId=' + orgTreeId + '&pid=' + pid + '&refCode=' + refCode + '&name=' + encodeURI(orgName);
    $('#editBtn').attr('href', url);
}

function orgSearch () {
    var url = 'orgAdd.html?id=' + orgId + '&orgTreeId=' + orgTreeId + '&pid=' + pid + '&refCode=' + refCode + '&name=' + encodeURI(orgName);
    // $('#searchBtn').attr('href', url);
    window.location.href = url;
}

// $('#orgName').on('click', function () {
//     var url = 'orgInfo.html?id=' + orgId + '&orgTreeId=' + orgTreeId + '&pid=' + pid + '&name=' + encodeURI(orgName);
//     window.location.href = url;
// });
