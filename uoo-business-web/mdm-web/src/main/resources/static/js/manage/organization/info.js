var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var pidName = getQueryString('pidName');
var flag = getQueryString('flag');
var parentOrgList;
var logTable;
var locationList = [];
var orgPostList = [];
var loading = parent.loading;
var toastr = parent.parent.toastr;
//字典数据
var statusCdData = window.top.dictionaryData.statusCd();
if(pid != ""){
    parentOrgList = [{id: orgId, name: parent.getNodeName(pid)}];
}else{
    parentOrgList = [{id: orgId, name: "无上级组织"}];
}

// // 组织关系信息初始化
// function initOrgRelTable (results) {
//     var table = $("#orgRelTable").DataTable({
//         'data': results,
//         'searching': false,
//         'ordering': false,
//         'autoWidth': false,
//         'columns': [
//             { 'data': null, 'title': '序号', 'className': 'row-name' },
//             { 'data': "orgName",
//                 'title': '组织名称',
//                 'className': 'row-sex',
//                 // 'render': function (data) {
//                 //   return data[0].orgTypeName
//                 // }
//             },
//             { 'data': "orgBizName", 'title': '组织称谓', 'className': 'user-account' },
//             { 'data': "refName", 'title': '关系类型', 'className': 'user-account' },
//             { 'data': "supOrgName", 'title': '上级组织', 'className': 'user-type' },
//             { 'data': "createDate", 'title': '添加时间', 'className': 'role-type' }
//         ],
//         'language': {
//             'emptyTable': '没有数据',
//             'loadingRecords': '加载中...',
//             'processing': '查询中...',
//             'search': '检索:',
//             'lengthMenu': ' _MENU_ ',
//             'zeroRecords': '没有数据',
//             'paginate': {
//                 'first':      '首页',
//                 'last':       '尾页',
//                 'next':       '下一页',
//                 'previous':   '上一页'
//             },
//             'info': '总_TOTAL_人',
//             'infoEmpty': '没有数据'
//         },
//         'pagingType': 'simple_numbers',
//         'dom': '<"top"f>t<"bottom">',
//         'drawCallback': function(){
//             this.api().column(0).nodes().each(function(cell, i) {
//                 cell.innerHTML =  i + 1;
//             });
//         }
//     });
// }

//初始化更新记录表格
function initLogTable(orgId){
    logTable = $("#orgRelTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': true,
        'ordering': true,
        'lSort': true,
        'info': true,
        "scrollY": "390px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '订单号', 'className': 'row-num', 
                'render': function (data, type, row, meta) {
                    return "<span style='cursor:pointer;' title='"+row.batchNumber+"'>"+row.batchNumber+"</span>";
                }     
            },
            { 'data': "operateType", 'title': '操作说明', 'className': 'row-explain'},
            { 'data': "userName", 'title': '操作人', 'className': 'row-psn' },
            { 'data': null, 'title': '操作人组织', 'className': 'row-org',
                'render': function (data, type, row, meta) {
                    return "<span style='cursor:pointer;' title='"+row.userOrgName+"'>"+row.userOrgName+"</span>";
                }    
            },
            { 'data': "userAccout", 'title': '操作账号', 'className': 'row-acct' },
            { 'data': null, 'title': '时间', 'className': 'row-date' ,
                'render': function (data, type, row, meta) {
                    return formatDateTime(row.createDate);
                }     
            },
            { 'data': null, 'title': '状态', 'className': 'row-state', 
                'render': function (data, type, row, meta) {
                    if(row.statusCd == 1000){
                        return "有效";
                    }else{
                        return "失效";
                    } 
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
            'info': '总_TOTAL_个',  
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
            param.recordId = orgId;
            param.tableName = "SYS_ORGANIZATION";
            $http.get('/public/modifyHistory/listByRecord', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
                loading.screenMaskDisable('container');
            }, function (err) {
                loading.screenMaskDisable('container');
            })
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
        initLogTable(orgId);

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
        // initOrgRelTable(data.records);
    }, function (err) {

    })
}

//编辑组织
function orgEdit() {
    var url = 'edit.html?id=' + orgId + '&pid=' + pid + '&name=' + encodeURI(orgName) + '&pidName=' + encodeURI(pidName) + '&flag=' + flag;
    window.location.href = url;
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

// datatable应用于tab切换出现表头错位
$('#myTabs a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
})

getOrg(orgId);
// getOrgRel(orgId);
