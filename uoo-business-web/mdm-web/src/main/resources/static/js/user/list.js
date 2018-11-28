var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var table;

$('#orgName').html(orgName);

// function getOrgPersonnerList () {
//     $http.get('/orgPersonRel/getPerOrgRelPage', {
//         orgId: orgId,
//         orgTreeId: orgTreeId
//     }, function (data) {
//         initOrgPersonnelTable(data.records)
//     }, function (err) {
//         console.log(err)
//     })
// }

function initOrgPersonnelTable (results) {
    var num = 1;
    table = $("#personnelTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        "scrollY": "375px",
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
            { 'data': "doubleName", 'title': '重名称谓', 'className': 'row-mobile' },
            { 'data': "certNo", 'title': '员工工号', 'className': 'cert-no' },
            { 'data': "postName", 'title': '职位名称', 'className': 'post-name' },
            { 'data': "orgName", 'title': '所属组织', 'className': 'org-name' },
            { 'data': "statusCd", 'title': '状态', 'className': 'status-code' }
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
            param.orgTreeId = '1';
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
    var loading = parent.loading;
    loading.screenMaskDisable('container');
}

initOrgPersonnelTable();

// $('#editBtn').on('click', function () {
//     var url = 'edit.html?id=' + orgId;
//     $(this).attr('href', url);
// })
$('#addBtn').on('click', function () {
   var url = "add.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName);
   $(this).attr('href', url);
})