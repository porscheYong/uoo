// var fullOrgName = getQueryString('fullorgname');
var acctId = getQueryString('acctId');
var userType = getQueryString('userType');
var statusCd = getQueryString('statusCd');
var personnelId = getQueryString('personnelId');
var title = getQueryString('title');
var opBtn = getQueryString('opBtn');
var isEdit;
var acctOrgVoPage;
var tbRolesList;

// console.log(acctId+'--'+userType+'--'+statusCd+'--'+personnelId+'--'+title+'--'+opBtn);

$('#main-title').html(title);

$('#cerType').get(0).selectedIndex=1;  //判断证件类型


function getUser(acctId,userType) {           //查看主账号时相关操作 
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

function getAcctUser(personnelId,userType){     //获取主账号信息(编辑或者新增)
  $http.get('http://192.168.58.112:9092/user/getPsnUser', {  
    personnelId: personnelId,
    userType: userType
  }, function (data) {
    if(data.tbAcct.acctId == null){
      isEdit = 0;
      console.log('no user');
    }else{
      $('#main-title').html('编辑主账号');

      if(data.tbAcct.statusCd == '1000'){                //判断状态
        $('#statusCd').get(0).selectedIndex=1;
      }
      isEdit = 1;
      initOrgTable(data.acctOrgVoPage.records);
      initEditUserInfo(data);
      initSubOrgTable(data.slaveAcctOrgVoPage.records);
    }
  }, function (err) {
    console.log(err)
  })
}

function initOrgTable(results){         //主账号组织数据
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

function initSubOrgTable(results){    //从账号组织数据
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

function initUserInfo(results){         //初始化用户信息(查看)

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

function initEditUserInfo(results){
  var roldId = '';
  acctOrgVoPage = results.acctOrgVoPage;
  tbRolesList = results.tbRolesList;
  personnelId = results.tbAcct.personnelId;
  console.log(acctOrgVoPage);
  console.log(tbRolesList);

  // if(results.tbAcct.statusCd == '1000'){                //判断状态
  //$('#statusCd').val('生效');
  // }else{
  //   $('#statusCd').get(0).selectedIndex=2;
  // }

  $('#psnTel').val(results.psnName);
  $('#psnNumTel').val(results.psnCode);
  $('#mobileTel').val(results.mobilePhone);
  $('#emailTel').val(results.eamil);
  $('#cerNoTel').val(results.certNo);
  $('#acctTel').val(results.tbAcct.acct);
  
  $('#defaultPswTel').val(results.tbAcct.password);
  $('#effectDate').val(results.tbAcct.enableDate);
  $('#invalidDate').val(results.tbAcct.disableDate);

  for(var i = 0; i <results.tbRolesList.length; i++){
    roldId = roldId + "、" + results.tbRolesList[i].roleId;
  }
  $('#roleTel').val(roldId.substring(1,roldId.length));
}

function isNullVal(s,r){
  if(r==null){
    $(s).val('null');
  }else{
    $(s).val(r);
  }
}

function addTbAcct(){

}

function updateAcct(){
  var statusCd;
  if($('#statusCd').val() == '生效'){
    statusCd = '1000';
  }else{
    statusCd = '1100';
  }

  var editFormAcctVo = {
    "acct": $('#acctTel').val(),
    "acctOrgVoList": [
      {
        "acctHostId": acctOrgVoPage.records[0].acctHostId,
        "acctId": acctOrgVoPage.records[0].acctId,
        "fullName": acctOrgVoPage.records[0].fullName,
        "id": acctOrgVoPage.records[0].id,
        "orgId": acctOrgVoPage.records[0].orgId,
        "pageNo": acctOrgVoPage.records[0].pageNo,
        "pageSize": acctOrgVoPage.records[0].pageSize
      }
    ],
    "disableDate": $('#invalidDate').val(),
    "enableDate": $('#effectDate').val(),
    "password": $('#defaultPswTel').val(),
    "personnelId": personnelId,
    "statusCd": statusCd, 
    "tbRolesList": [
      {
        "roleId": parseInt($('#roleTel').val()),
        "roleName": ""
      }
    ],
    "userType": "1"
  };
  console.log(editFormAcctVo);

  $.ajax({
    url: 'http://192.168.58.112:9092/acct/updateAcct',
    type: 'PUT',
    contentType: "application/json",
    data: JSON.stringify(editFormAcctVo),
    dataType:"JSON",
    success: function (data) { //返回json结果
      console.log(data);
    },
    error:function(err){
      console.log(err);
    }
  });
}

function acctSubmit(){   //提交事件
  if($('#acctTel').val()!='' && $('#statusCd').val()!='' && $('#roleTel').val()!='' && $('#defaultPswTel').val()!=''){
    if(isEdit == 0){
      addTbAcct();
    }else if(isEdit == 1){
      updateAcct();
    }
  }else{
    alert('必填部分不能为空');
  }
}

  laydate.render({
    elem: '#effectDate' //指定元素
  }); 

  laydate.render({
    elem: '#invalidDate' //指定元素
  }); 

  if(opBtn==0){
    $('#opBtn').css("display","none");
    $('.fright').css("display","none");
    $('#default_psw').css("display","none");
    $('input').attr("disabled","false");
    $('select').attr("disabled","false");

    if(statusCd == '1000'){                //判断状态
      $('#statusCd').get(0).selectedIndex=1;
    }else{
      $('#statusCd').get(0).selectedIndex=2;
    }
    getUser(acctId,userType);
    
  }else{
    getAcctUser(personnelId,userType);
  }


