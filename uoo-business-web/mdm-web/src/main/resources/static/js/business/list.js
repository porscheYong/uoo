var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var orgName = getQueryString('name');

function initOrgTable (results) {
    var table = $("#orgTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        "scrollY": "375px",
        'columns': [
            { 'data': "orgName", 'title': '部门', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return '<a href="list.html?id='+ row.orgId + '&orgTreeId=' + orgTreeId + '&name=' + row.orgName + '" onclick="parent.openTreeById('+orgId+','+row.orgId+')">'+ row.orgName +'</a>'
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
                console.log(err)
            })
        }
    });
}

function initOrgPersonnelTable (isSearchlower) {
    var table = $("#personnelTable").DataTable({
        'searching': false,
        'destroy': true,
        'autoWidth': false,
        'ordering': true,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        "scrollY": "375px",
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no' },
            { 'data': "psnName", 'title': '姓名', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return '<a href="/inaction/user/edit.html?id='+ row.orgId +'&orgTreeId=' + orgTreeId + '&name=' + row.orgName + '&personnelId=' + row.personnelId + '">'+ row.psnName +'</a>';
                }
            },
            { 'data': "mobile", 'title': '手机号码', 'className': 'row-mobile' },
            { 'data': "certNo", 'title': '员工工号', 'className': 'cert-no' },
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
            'info': '总_TOTAL_人',
            'infoEmpty': '没有数据'
        },
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>',
        'drawCallback': function(){
            this.api().column(0).nodes().each(function(cell, i) {
                cell.innerHTML =  i + 1;
            });
        },
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
                console.log(err)
            })
        }
    });
}

//勾选显示下级组织人员
function showLower() {
    var checked = $('#isShowLower').is(':checked')? 1: 0;
    initOrgPersonnelTable(checked);
}

$('#orgName').html(orgName);
parent.getOrgExtInfo();
initOrgTable();
initOrgPersonnelTable(0);

function orgEdit () {
    var url = 'orgEdit.html?id=' + orgId + '&orgTreeId=' + orgTreeId + '&pid=' + pid + '&name=' + encodeURI(orgName);
    $('#editBtn').attr('href', url);
}

function orgSearch () {
    var url = 'search.html?id=' + orgId + '&orgTreeId=' + orgTreeId + '&pid=' + pid + '&name=' + encodeURI(orgName);
    // $('#searchBtn').attr('href', url);
    window.location.href = url;
}

$('#orgName').on('click', function () {
    var url = 'orgInfo.html?id=' + orgId + '&orgTreeId=' + orgTreeId + '&pid=' + pid + '&name=' + encodeURI(orgName);
    window.location.href = url;
});