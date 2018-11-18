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
            { 'data': "name", 'title': '人员姓名', 'className': 'row-name' ,
            'render': function (data, type, row, meta) {
                if(row.type == '主账号'){
                    return '<a href="addMainAccount.html?account='+ row.acc +'&title=查看主账号&opBtn=0">'+ row.name +'</a>'
                }else{
                    return '<a href="addSubAccount.html?account='+ row.acc +'&title=查看从账号&opBtn=0">'+ row.name +'</a>'
                } 
              }
            },
            { 'data': "type", 'title': '用户类型', 'className': 'row-type' },
            { 'data': "acc", 'title': '用户名', 'className': 'row-acc' },
            { 'data': "org", 'title': '归属组织', 'className': 'row-org' },
            { 'data': "mobile", 'title': '联系电话', 'className': 'row-mobile' },
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

// $('#addBtn').on('click', function () {
//     var url = 'addMainAccount.html?fullorgname=' + fullOrgName + '&name=' + orgName;
//     $(this).attr('href', url);
// })