var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var orgTreeName = getQueryString('orgTreeName');
var table;

$('#orgName').html(orgName);
parent.getOrgExtInfo();

function initOrgPersonnelTable (isSearchlower) {
    table = $("#personnelTable").DataTable({
        'searching': false,
        'destroy': true,
        'autoWidth': false,
        'ordering': true,
        // "scrollY": "375px",
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no'},
            { 'data': "psnName", 'title': '姓名', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return "<a href='edit.html?id=" + row.orgId + "&orgRootId=" + row.orgRootId + "&personnelId=" + row.personnelId + 
                                        "&name="+ orgName +"&orgTreeId="+orgTreeId+"'>" + row.psnName + "</a>";
                }
            },
            { 'data': "doubleName", 'title': '重名称谓', 'className': 'row-mobile' },
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
    var loading = parent.loading;
    loading.screenMaskDisable('container');
}

function initFreePersonnelTable () {
    var num = 1;
    table = $("#personnelTable").DataTable({
        'searching': false,
        'destroy': true,
        'autoWidth': false,
        'ordering': true,
        'columns': [
            { 'data': "psnName", 'title': '序号', 'className': 'row-no' ,
                'render': function (data, type, row, meta) {
                    return num++;
                }
            },
            { 'data': "psnName", 'title': '姓名', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return "<a href='edit.html?id=" + row.orgId + "&orgRootId=" + row.orgRootId + "&personnelId=" + row.personnelId +
                        "&name="+ orgName +"&orgTreeId="+orgTreeId+"'>" + row.psnName + "</a>";
                }
            },
            { 'data': "psnNbr", 'title': '员工工号', 'className': 'cert-no' },
            // { 'data': "postName", 'title': '职位名称', 'className': 'post-name' },
            // { 'data': "orgName", 'title': '所属组织', 'className': 'org-name' },
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
        // 'drawCallback': function(){
        //     this.api().column(0).nodes().each(function(cell, i) {
        //         cell.innerHTML =  i + 1;
        //     });
        // },
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            $http.get('/personnel/getFreePsnInfo', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
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

//勾选显示下级组织人员
function showLower() {
    var checked = $('#isShowLower').is(':checked');
    initOrgPersonnelTable(checked);
}

if (orgId == 'noSort') {
    $('#titleName').html('游离人员');
    $('#isShow').hide();
    initFreePersonnelTable();
}
else {
    $('#titleName').html('组织人员');
    $('#isShow').show();
    initOrgPersonnelTable(false);
}
// $('#editBtn').on('click', function () {
//     var url = 'edit.html?id=' + orgId;
//     $(this).attr('href', url);
// })
$('#addBtn').on('click', function () {
   var url = "add.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&orgTreeName=" + encodeURI(orgTreeName) + "&name=" + encodeURI(orgName);
   $(this).attr('href', url);
})