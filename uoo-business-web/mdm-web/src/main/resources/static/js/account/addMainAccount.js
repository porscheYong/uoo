// var fullOrgName = getQueryString('fullorgname');
var acctId = getQueryString('acctId');
var userType = getQueryString('userType');
var statusCd = getQueryString('statusCd');
var title = getQueryString('title');
var opBtn = getQueryString('opBtn');

$('#main-title').html(title);
$('#cerType').get(0).selectedIndex=1;  //判断证件类型

if(statusCd == '1000'){                //判断状态
  $('#statusCd').get(0).selectedIndex=1;
}else{
  $('#statusCd').get(0).selectedIndex=2;
}

if(opBtn==0){
    $('#opBtn').css("display","none");
    $('.fright').css("display","none");
    $('#default_psw').css("display","none");
    $('input').attr("disabled","false");
    $('select').attr("disabled","false");
    getUser(acctId,userType);
}

function getUser(acctId,userType) {                   
    $http.get('http://192.168.58.112:9092/user/getUser', {   //http://192.168.58.112:18000/user/getUser
        acctId: acctId,
        userType: userType
    }, function (data) {
        initOrgTable(data.acctOrgVoPage.records);
        initUserInfo(data);
        initSubOrgTable(data.slaveAcctOrgVoPage.records);
        // console.log(data.slaveAcctOrgVoPage.records.length);
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
        { 'data': "orgId", 'title': '组织树', 'className': 'row-orgTree' ,
        'render': function (data, type, row, meta) {
          return '标准组织树';
      }
      },
        { 'data': "fullName", 'title': '组织名称', 'className': 'row-fullName' ,
        'render': function (data, type, row, meta) {
          var s = row.fullName.replace(/->/g,'/');
          return s.substring(0,s.length-1);
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
        'infoEmpty': '没有数据'
    }
  });
}

function initSubOrgTable(results){
  var table = $("#subInfoTable").DataTable({
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
        { 'data': "number", 'title': '序号', 'className': 'row-number' },
        { 'data': "acc", 'title': '账号名', 'className': 'row-acc' },
        { 'data': "acctype", 'title': '账号类型', 'className': 'row-acctype' },
        { 'data': "orgtree", 'title': '组织树', 'className': 'row-orgtree' },
        { 'data': "org", 'title': '归属组织', 'className': 'row-org' },
        { 'data': "state", 'title': '状态', 'className': 'row-state' }
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

function initUserInfo(results){
    // console.log(results.psnName+'---');
    // console.log(results.acctOrgVoPage.current);

    isNullVal('#psnTel',results.psnName);
    isNullVal('#psnNumTel',results.psnCode);
    isNullVal('#mobileTel',results.mobilePhone);
    isNullVal('#emailTel',results.eamil);
    isNullVal('#cerNoTel',results.certNo);
    isNullVal('#acctTel',results.tbAcct.acct);
    $('#roleTel').val('null');
    isNullVal('#effectDate',results.tbAcct.enableDate);
    isNullVal('#invalidDate',results.tbAcct.disableDate);  
}

function isNullVal(s,r){
  if(r==null){
    $(s).val('null');
  }else{
    $(s).val(r);
  }
}

laydate.render({
  elem: '#effectDate' //指定元素
}); 

laydate.render({
  elem: '#invalidDate' //指定元素
}); 

