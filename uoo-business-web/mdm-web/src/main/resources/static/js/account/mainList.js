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

function getMainAccountList (orgId) {                    //获取主账号列表
    $http.get('', {
        orgId: orgId,
        orgRootId: '1'
    }, function (data) {
        initMainTable(data.records) //data.records
    }, function (err) {
        console.log(err)
    })
}

function initMainTable(){
    var table = $("#mainTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'info': false,
        // 'lengthChange':false,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        "scrollY": "375px",
        'columns': [
            { 'data': "account", 'title': '账号', 'className': 'row-account' ,
            'render': function (data, type, row, meta) {
                return '<a href="list.html?account='+ row.account +'" onclick="">'+ row.account +'</a>'
              }
            },
            { 'data': "name", 'title': '人员姓名', 'className': 'row-name' },
            { 'data': "org", 'title': '归属组织', 'className': 'row-org' },
            { 'data': "state", 'title': '状态', 'className': 'row-state' }
            // { 
            //   'data': "userRoleName",
            //   'title': '系统角色',
            //   'className': 'user-role'
            //   // 'render': function (data, type, row, meta) {
            //   //       console.log(data, type, row, meta)
            //   //   }
            //  }
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
        'dom': '<"top"f>t<"bottom"ipl>'
    });
}

$('#orgName').html(orgName);
getOrgExtInfo();
initMainTable();

$('#addBtn').on('click', function () {
    var url = 'addMainAccount.html?fullorgname=' + fullOrgName + '&name=' + orgName;
    $(this).attr('href', url);
})