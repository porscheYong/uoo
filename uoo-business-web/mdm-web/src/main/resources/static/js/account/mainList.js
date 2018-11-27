var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgTreeId = getQueryString('orgTreeId');

// 获取组织完整路径
function getOrgExtInfo() {
    var pathArry = parent.nodeArr;
    //console.log(pathArry)
    var pathStr = '';
    for (var i = pathArry.length - 1; i >= 0; i--) {
        if (i === 0) {
            pathStr +=  '<span class="breadcrumb-item"><a href="javascript:viod(0);">' + pathArry[i] + '</a></span>';
        } else {
            pathStr += '<span class="breadcrumb-item"><a href="javascript:viod(0);">' + pathArry[i] + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
        }
    }
    $('.breadcrumb').html(pathStr);
}

// function getUserList (orgId) {                    //获取账号列表
//     $http.get('http://134.96.253.221:11100/orgPersonRel/getUserOrgRelPage', {
//         orgTreeId: '1',
//         orgId: orgId,
//         pageSize:2000,
//         pageNo:1
//     }, function (data) {
//         initMainTable(data.records) //data.records
//     }, function (err) {
//         console.log(err)
//     })
// }

// function initMainTable(results){
//     var table = $("#mainTable").DataTable({
//         'data': results,
//         'searching': true,
//         'autoWidth': false,
//         'ordering': true,
//         'info': true,
//         // 'lengthChange':false,
//         'initComplete': function (settings, json) {
//             console.log(settings, json)
//         },
//         "scrollY": "375px",
//         'columns': [
//             { 'data': "psnName", 'title': '人员姓名', 'className': 'row-psnName' ,
//             'render': function (data, type, row, meta) {
//                 if(row.typeName == '主账号'){
//                     return '<a href="addMainAccount.html?orgName=' + orgName +'&orgId=' + orgId + '&acctId='+ row.accId + '&statusCd='+row.statusCd+'&opBtn=0&hType=mh">'+ row.psnName +'</a>'
//                 }else{
//                     return '<a href="addSubAccount.html?orgName=' + orgName +'&orgId=' + orgId + '&acctId='+ row.accId +'&statusCd='+row.statusCd+'&opBtn=0&hType=mh">'+ row.psnName +'</a>'
//                 } 
//               }
//             },
//             { 'data': "typeName", 'title': '用户类型', 'className': 'row-typeName' },
//             { 'data': "acct", 'title': '用户名', 'className': 'row-acc' },
//             { 'data': "orgName", 'title': '归属组织', 'className': 'row-org' },
//             { 'data': "certNo", 'title': '证件号码', 'className': 'row-certNo' },
//             { 'data': "statusCd", 'title': '状态', 'className': 'row-statusCd' ,
//             'render': function (data, type, row, meta) {
//                 if(row.statusCd == 1000){
//                     return '生效';
//                 }else{
//                     return '失效';
//                 }
//             }
//             }
//         ],
//         'language': {
//             'emptyTable': '没有数据',  
//             'loadingRecords': '加载中...',  
//             'processing': '查询中...',  
//             'search': '搜索：',  
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
//         "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
//         'pagingType': 'simple_numbers',
//         'dom': '<"top"f>t<"bottom"ipl>'
//     });
// }


function initMainTable(){
    var table = $("#mainTable").DataTable({
        'searching': true,
        'autoWidth': false,
        'ordering': true,
        'info': true,
        // 'lengthChange':false,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        "scrollY": "375px",
        'columns': [
            { 'data': "psnName", 'title': '人员姓名', 'className': 'row-psnName' ,
            'render': function (data, type, row, meta) {
                if(row.typeName == '主账号'){
                    return '<a href="addMainAccount.html?orgTreeId=' + orgTreeId + '&orgName=' + orgName +'&orgId=' + orgId + '&acctId='+ row.accId + '&statusCd='+row.statusCd+'&opBtn=0&hType=mh">'+ row.psnName +'</a>'
                }else{
                    return '<a href="addSubAccount.html?orgTreeId=' + orgTreeId + '&orgName=' + orgName +'&orgId=' + orgId + '&acctId='+ row.accId +'&statusCd='+row.statusCd+'&opBtn=0&hType=mh">'+ row.psnName +'</a>'
                } 
              }
            },
            { 'data': "typeName", 'title': '用户类型', 'className': 'row-typeName' },
            { 'data': "acct", 'title': '用户名', 'className': 'row-acc' },
            { 'data': "orgName", 'title': '归属组织', 'className': 'row-org' },
            { 'data': "certNo", 'title': '证件号码', 'className': 'row-certNo' },
            { 'data': "statusCd", 'title': '状态', 'className': 'row-statusCd' ,
            'render': function (data, type, row, meta) {
                if(row.statusCd == 1000){
                    return '生效';
                }else{
                    return '失效';
                }
            }
            }
        ],
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '搜索：',  
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
            param.orgTreeId = orgTreeId;
            param.orgId = orgId;
            $http.get('http://134.96.253.221:11100/orgPersonRel/getUserOrgRelPage', param, function (result) {
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

$('#orgName').html(orgName);
getOrgExtInfo();
// getUserList(orgId);
initMainTable();

$('#addBtn').on('click', function () {
    var url = 'add.html?&orgName=' + orgName +'&orgId=' + orgId + '&orgTreeId=' + orgTreeId;
    $(this).attr('href', url);
})