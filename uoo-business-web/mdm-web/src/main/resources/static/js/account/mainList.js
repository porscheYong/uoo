var orgId = getQueryString('id');
var orgName = getQueryString('name');
var fullOrgName;

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
    fullOrgName = pathStr;
    $('.breadcrumb').html(pathStr);
}

function getUserList (orgId) {                    //获取账号列表
    $http.get('http://134.96.253.221:11100/orgPersonRel/getUserOrgRelPage', {
        orgId: orgId,
        pageSize:2000,
        pageNo:1
    }, function (data) {
        initMainTable(data.records) //data.records
    }, function (err) {
        console.log(err)
    })
}

function initMainTable(results){
    var table = $("#mainTable").DataTable({
        'data': results,
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
                    return '<a href="addMainAccount.html?acctId='+ row.accId +'&userType='+row.type+'&statusCd='+row.statusCd+'&title=查看主账号&opBtn=0">'+ row.psnName +'</a>'
                }else{
                    return '<a href="addSubAccount.html?acctId='+ row.accId +'&userType='+row.type+'&statusCd='+row.statusCd+'&title=查看从账号&opBtn=0">'+ row.psnName +'</a>'
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
        'dom': '<"top"f>t<"bottom"ipl>'
    });
}

$('#orgName').html(orgName);
getOrgExtInfo();
getUserList(orgId);

// $('#addBtn').on('click', function () {
//     var url = 'addMainAccount.html?fullorgname=' + fullOrgName + '&name=' + orgName;
//     $(this).attr('href', url);
// })