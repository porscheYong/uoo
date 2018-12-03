var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');

function initOrgTable () {
    var table = $("#orgTable").DataTable({
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        "scrollY": "375px",
        'columns': [
            { 'data': "orgName", 'title': '部门', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return '<a href="javascript:void(0);" onclick="window.parent.openTreeById('+orgId+','+row.orgId+')">'+ row.orgName +'</a>'
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
            param.orgTreeId = '1';
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
}

function initOrgPersonnelTable (results) {
    var table = $("#personnelTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        "scrollY": "375px",
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no' },
            { 'data': "psnName", 'title': '姓名', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return '<a href="/inaction/user/edit.html?id='+ row.orgId +'&orgTreeId=1' + '&name=' + row.orgName + '&personnelId=' + row.personnelId + '">'+ row.psnName +'</a>'
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
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.orgTreeId = '1';
            param.orgId = orgId;
            $http.get('/orgPersonRel/getPerOrgRelPage', param, function (result) {
                var returnData = {};
                returnData.recordsTotal = result.total;
                returnData.recordsFiltered = result.total;
                returnData.data = result.records;
                callback(returnData);
            }, function (err) {

            })
        }
    });
}

$('#orgName').html(orgName);
// 显示组织路径
parent.getOrgExtInfo();
initOrgTable();
initOrgPersonnelTable();

$('#orgName').on('click', function () {
    var url = 'info.html?id=' + orgId + '&pid=' + pid + '&name=' + encodeURI(orgName);
    window.location.href = url;
});
$('#editBtn').on('click', function () {
    var url = 'edit.html?id=' + orgId + '&pid=' + pid + '&name=' + encodeURI(orgName);
    $(this).attr('href', url);
});
$('#searchBtn').on('click', function () {
    var url = 'search.html?id=' + orgId  + '&pid=' + pid + '&name=' + encodeURI(orgName);
   // $(this).attr('href', url);
   window.location.href = url;
    return false;
});
