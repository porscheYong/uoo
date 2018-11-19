// var fullOrgName = getQueryString('fullorgname');
// var accId = getQueryString('accId');
// var userType = getQueryString('userType');
var title = getQueryString('title');
var opBtn = getQueryString('opBtn');

$('#main-title').html(title);

if(opBtn==0){
    $('#opBtn').css("display","none");
    $('.fright').css("display","none");
    $('#default_psw').css("display","none");
    $('input').attr("disabled","false");
    $('select').attr("disabled","false");
    //getUser(accId,userType);
}

function getUser(accId,userType) {                   
    $http.get('http://192.168.58.112:18000/user/getUser', {
        accId: accId,
        userType:userType
    }, function (data) {
        initOrgTable(data.records.acctOrgVoPage)
    }, function (err) {
        console.log(err)
    })
}

function initOrgTable(results){
  var table = $("#orgTable").DataTable({
    'data': results,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'paging': false,
    'info': false,
    'initComplete': function (settings, json) {
        console.log(settings, json)
    },
    "scrollY": "105px",
    'columns': [
        { 'data': "id", 'title': '序号', 'className': 'row-id' },
        { 标准组织树, 'title': '组织树名称', 'className': 'row-orgTree' },
        { 'data': "fullName", 'title': '组织名称', 'className': 'row-fullName' }
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

laydate.render({
  elem: '#effectDate' //指定元素
}); 

laydate.render({
  elem: '#invalidDate' //指定元素
}); 

