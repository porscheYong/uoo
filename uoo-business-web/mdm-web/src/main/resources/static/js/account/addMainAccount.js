
// var baseUrl = '134.96.253.221:18000';
// var baseUrl = '192.168.58.112:18000';
var acctId = getQueryString('acctId');
var userType = getQueryString('userType');
var statusCd = getQueryString('statusCd');
var personnelId = getQueryString('personnelId');
var title = getQueryString('title');
var opBtn = getQueryString('opBtn');
var isEdit;     //add=0   edit=1
var isStatus;
var isAcctId;
var acctOrgVoPage;
var tbRolesList;
var orgTable;
var editOrgList = [];
var flag = 0;

// console.log(acctId+'--'+userType+'--'+statusCd+'--'+personnelId+'--'+title+'--'+opBtn);

$('#main-title').html(title);
$('#invalidDate').val(''),
$('#effectDate').val(''),

$('#cerType').get(0).selectedIndex=1;  //判断证件类型


function getUser(acctId,userType) {           //查看主账号
    $http.get('/user/getUser', {   //http://192.168.58.112:18000/user/getUser
        acctId: acctId,
        userType: userType
    }, function (data) {
        initOrgTable(data.acctOrgVoPage.records);
        initUserInfo(data);
        initSubOrgTable(data.slaveAcctOrgVoPage.records);
    }, function (err) {
        console.log(err)
    })
}

function getAcctUser(personnelId,userType){     //获取主账号信息(编辑或者新增)
  $http.get('/user/getPsnUser', {    //'http://'+baseUrl+'/user/getPsnUser'
    personnelId: personnelId,
    userType: userType
  }, function (data) {
    if(data.tbAcct == null){    //新增
      isEdit = 0;
      initAddUserInfo(data);
      initOrgTable(data.acctOrgVoPage);
      initSubOrgTable(data.slaveAcctOrgVoPage);
      console.log('no user');
    }else{                      //编辑
      $('#main-title').html('编辑主账号');
      isStatus = data.tbAcct.statusCd;
      isAcctId = data.tbAcct.acctId;
      isEdit = 1;
      initOrgTable(data.acctOrgVoPage.records);
      initEditUserInfo(data);
      initSubOrgTable(data.slaveAcctOrgVoPage.records);
    }
  }, function (err) {
    console.log(err)
  })
}

function initOrgTable(results){         //主账号组织数据表格
    var num =1;
    orgTable = $("#orgTable").DataTable({
    'data': results,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'paging': false,
    'info': false,
    'initComplete': function (settings, json) {
        console.log(settings, json)
    },
    "scrollY": "120px",
    'columns': [
        { 'data': "id", 'title': '序号', 'className': 'row-id' ,
        'render': function (data, type, row, meta) {
          return num++;
      }
      },
        { 'data': "orgId", 'title': '组织树', 'className': 'row-orgTree' ,
        'render': function (data, type, row, meta) {
          return '标准组织树';
      }
      },
        { 'data': "fullName", 'title': '组织名称', 'className': 'row-fullName' ,
        'render': function (data, type, row, meta) {
          if(row.fullName.search('->') != -1){
            var s = row.fullName.replace(/->/g,'/');
            return s.substring(0,s.length-1);
          }else{
            return row.fullName;
          }
      }
      },
      {'data': "orgId", 'title': '操作', 'className': 'row-delete' ,
      'render': function (data, type, row, meta) {
        return "<a href='javascript:void(0);'  onclick='deleteOrg("+ num + ")'  class='btn btn-default btn-xs'><i class='fa fa-arrow-down'></i> 删除</a>";
    }
    },
    { 'data': "orgId", 'title': 'orgId', 'className': 'row-orgId' }
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
  var subTable = $("#subInfoTable").DataTable({
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
    var roldId = '';
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
    if(roldId != ''){
      $('#roleTel').val(roldId.substring(1,roldId.length));
    } 
}

function initEditUserInfo(results){     //初始化用户信息(编辑)
  var roldId = '';
  acctOrgVoPage = results.acctOrgVoPage;
  tbRolesList = results.tbRolesList;
  personnelId = results.tbAcct.personnelId;

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

function initAddUserInfo(results){//初始化用户信息(新增)
  $('#psnTel').val(results.psnName);
  $('#psnNumTel').val(results.psnCode);
  $('#mobileTel').val(results.mobilePhone);
  $('#emailTel').val(results.eamil);
  $('#cerNoTel').val(results.certNo);
}

function isNullVal(s,r){
  if(r==null){
    $(s).val('null');
  }else{
    $(s).val(r);
  }
}

function addTbAcct(){     //新增
  var editFormAcctVo = {
    "acct": $('#acctTel').val(),
    // "acctId": isAcctId,
    "acctOrgVoList": addOrgList,
    "disableDate": $('#invalidDate').val(),
    "enableDate": $('#effectDate').val(),
    "password": $('#defaultPswTel').val(),
    "personnelId": personnelId,
    "statusCd": "1000", 
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
    url: '/acct/addTbAcct',
    type: 'POST',
    async:false,
    contentType: "application/json",
    data: JSON.stringify(editFormAcctVo),
    dataType:"JSON",
    success: function (data) { //返回json结果
      console.log(data);
      alert('保存成功');
    },
    error:function(err){
      console.log(err);
      alert('保存失败');
    }
  });
}

function updateAcct(){      //编辑主账号
    var statusCd;
    if($('#statusCd option:selected').text() == '生效'){
      statusCd = '1000';
    }else{
      statusCd = '1100';
    }
    var editFormAcctVo = {
      "acct": $('#acctTel').val(),
      "acctId": isAcctId,
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
      url: '/acct/updateAcct',
      type: 'PUT',
      async:false,
      contentType: "application/json",
      data: JSON.stringify(editFormAcctVo),
      dataType:"JSON",
      success: function (data) { //返回json结果
        console.log(data);
        alert('保存成功');
      },
      error:function(err){
        console.log(err);
        alert('保存失败');
      }
    });
}

// function removeAcctOrg(){   //编辑时删除组织
//   $.ajax({
//     url: '/acct/removeAcctOrg',
//     type: 'DELETE',
//     async:false,
//     contentType: "application/json",
//     data: {personnelId:personnelId,acctID:,orgId},
//     dataType:"JSON",
//     success: function (data) { //返回json结果
//       console.log(data);
//       alert('保存成功');
//     },
//     error:function(err){
//       console.log(err);
//       alert('保存失败');
//     }
//   });
// }

function acctSubmit(){   //提交事件
  if($('#acctTel').val()!='' && $('#statusCd').val()!='' && $('#roleTel').val()!='' && $('#defaultPswTel').val()!=''){
    if(isEdit == 0){
      addTbAcct();
      window.history.back();
    }else if(isEdit == 1){
      updateAcct();
      window.history.back();
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

function isEnableStatus(statusCd){    //判断状态

  if(statusCd == 1000){                
    $('#statusCd').get(0).selectedIndex=2;
    console.log('生效');
  }else{
    $('#statusCd').get(0).selectedIndex=1;
    console.log('失效');
  }
}

//删除组织
function deleteOrg(id){
  if(isEdit == 0){    //add
    addOrgList.splice(id-2,1);
    orgTable.destroy();
    initOrgTable(addOrgList);
  }else if(isEdit == 1){    //编辑

    editOrgList.splice(id-2,1);
    orgTable.destroy();
    initOrgTable(editOrgList);

  }
}


    

  if(opBtn==0){     //查看主账号
    $('#opBtn').css("display","none");
    $('.fright').css("display","none");
    $('#default_psw').css("display","none");
    $('input').attr("disabled","false");
    $('select').attr("disabled","false");

    isEnableStatus(statusCd);
    getUser(acctId,userType);

  }else{        //编辑或新增主账号
    getAcctUser(personnelId,userType);
  }


  function getTableContent(){
    if(isEdit == 1){
      if(flag == 0){
        var n = 2;
        var tr = $('tr');
        for(var i=0;i<tr.length-5;i++){
          editOrgList.push({'orgId':tr[n].cells[4].innerText,'fullName':tr[n].cells[2].innerText});
          n++;
        }
      }
      console.log(editOrgList);
      flag = 1;
    }
  }





