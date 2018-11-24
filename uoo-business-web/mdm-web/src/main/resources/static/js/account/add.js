// var baseUrl = '134.96.253.221:18000';
// var baseUrl = '192.168.58.112:18000';
$('#userType').get(0).selectedIndex=1;
var table;

function check(){
    var keyWordTel = $("#keyWordTel").val();
    if(keyWordTel == ''){
        alert('请选择人员');
    }else{
        getPsn(keyWordTel);
        table.destroy();
    }
}

// if(userType == '主账号'){
//     window.location.href = 'addMainAccount.html?personnelId='+personnelId+'&userType=1&title=添加主账号&opBtn=1';
// }else if(userType == '从账号'){
//     window.location.href = 'addSubAccount.html?account=123&title=添加从账号&opBtn=1';
// }else{
//     alert('请选择用户类型');
// }

function getPsn(keyWord) {           //搜索人员
    $http.get('/personnel/getPsnBasicInfo', {   //http://192.168.58.112:18000/user/getUser
        keyWord: keyWord,
        pageNo: 1,
        pageSize: 200
    }, function (data) {
        initPsnInfoTable(data.records);
    }, function (err) {
        console.log(err)
    })
}

function initPsnInfoTable(results){         //主账号组织数据
    var userType = $("#userType").val();
    table = $("#psnInfoTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'info': false,
        'paging': false,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        "scrollY": "105px",
        'columns': [
            { 'data': "psnName", 'title': '姓名', 'className': 'row-psnName' ,
            'render': function (data, type, row, meta) {
            if(userType == '主账号'){
                return '<a href="addMainAccount.html?personnelId='+ row.personnelId +'&title=添加主账号&opBtn=1">'+ row.psnName +'</a>'
            }else if(userType == '从账号'){
                return '<a href="addSubAccount.html?personnelId='+ row.personnelId +'&userType=2&title=添加从账号&opBtn=1">'+ row.psnName +'</a>'
            } 
            }
        },
            { 'data': "content", 'title': '联系方式', 'className': 'row-content'},
            { 'data': "psnNbr", 'title': '人员编码', 'className': 'row-psnNbr'}
        ],
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '检索:',  
            'lengthMenu': ' _MENU_ ',  
            'zeroRecords': '没有数据', 
            'infoEmpty': '没有数据'
        }
    });
}

