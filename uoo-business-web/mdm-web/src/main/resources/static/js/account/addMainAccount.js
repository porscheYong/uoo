var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var hType = getQueryString('hType');
var orgTreeId = getQueryString('orgTreeId');

var acctId = getQueryString('acctId');
var statusCd = getQueryString('statusCd');
var personnelId = getQueryString('personnelId');
var title = getQueryString('title');
var opBtn = getQueryString('opBtn');  // 0是编辑  1是新增
var orgTable;
var editOrgList = [];
var flag = 0;
var psw;
var roleList = [];


$('#invalidDate').val(''),
$('#effectDate').val(''),
$('#statusCd').get(0).selectedIndex=0; //判断状态，默认生效
$('#cerType').get(0).selectedIndex=0;  //判断证件类型,默认身份证


function getUser(acctId) {           //查看并编辑主账号
    $http.get('/user/getUser', {   //http://192.168.58.112:18000/user/getUser
        acctId: acctId,
        userType: "1"
    }, function (data) {
        personnelId = data.personnelId;
        for(var i=0;i<data.acctOrgVoPage.records.length;i++){
          editOrgList.push({"orgId":data.acctOrgVoPage.records[i].orgId,"fullName":data.acctOrgVoPage.records[i].fullName});
        }
        $('#main-title').html("编辑主账号");
        noSelectUserInfo();
        initOrgTable(data.acctOrgVoPage.records);
        initEditUserInfo(data);
        initSubOrgTable(data.slaveAcctOrgVoPage.records);
    }, function (err) {
        console.log(err)
    })
}

function getAcctUser(personnelId){     //获取主账号信息(编辑或者新增)
  $http.get('/user/getPsnUser', {    //'http://'+baseUrl+'/user/getPsnUser'
    personnelId: personnelId,
    userType: "1"
  }, function (data) {
    if(data.tbAcct == null){    //新增
      initAddUserInfo(data);
      initOrgTable(data.acctOrgVoPage);
      initSubOrgTable(data.slaveAcctOrgVoPage);
      $('#main-title').html("新增主账号");
      $('#delAcct').css("display","none");
      noSelectUserInfo();
      console.log('no user');
    }else{                      //编辑
      $('#main-title').html('编辑主账号');
      noSelectUserInfo();
      opBtn = 0;
      acctId = data.tbAcct.acctId;
      for(var i=0;i<data.acctOrgVoPage.records.length;i++){
        editOrgList.push({"orgId":data.acctOrgVoPage.records[i].orgId,"fullName":data.acctOrgVoPage.records[i].fullName});
      }

      initOrgTable(data.acctOrgVoPage.records);
      initEditUserInfo(data);
      initSubOrgTable(data.slaveAcctOrgVoPage.records);
    }
  }, function (err) {
    console.log(err)
  })
}

function noSelectUserInfo(){     //控制人员信息不可选
   $("#psnTel").attr("disabled",false);
   $("#psnNumTel").attr("disabled",false);
   $("#mobileTel").attr("disabled",false);
   $("#emailTel").attr("disabled",false);
   $("#cerType").attr("disabled",false);
   $("#cerNoTel").attr("disabled",false);
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
        { 'data': "id", 'title': '序号', 'className': 'row-number' },
        { 'data': "slaveAcct", 'title': '账号名', 'className': 'row-acc' ,
        'render': function (data, type, row, meta) {
            return '<a href="addSubAccount.html?orgTreeId=' + orgTreeId + '&toMainType=' + hType +'&orgName=' + orgName + '&orgId=' + orgId +'&hType=th&mainAcctId='+ acctId +
                                  '&acctId='+ row.slaveAcctId + '&statusCd='+ row.statusCd +'&opBtn=0">'+ row.slaveAcct +'</a>'
        }
      },
        { 'data': "slaveAcctType", 'title': '账号类型', 'className': 'row-acctype' },
        { 'data': "orgTreeName", 'title': '组织树', 'className': 'row-orgtree' },
        { 'data': "fullName", 'title': '归属组织', 'className': 'row-org' ,
        'render': function (data, type, row, meta) {
          if(row.fullName.search('->') != -1){
            var s = row.fullName.replace(/->/g,'/');
            return s.substring(0,s.length-1);
          }else{
            return row.fullName;
          }
        }
      },
        { 'data': "statusCd", 'title': '状态', 'className': 'row-state' ,
        'render': function (data, type, row, meta) {
          return "生效";
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

function initEditUserInfo(results){     //初始化用户信息(编辑)
  var roldId = '';
  $('#psnTel').val(results.psnName);
  $('#psnNumTel').val(results.psnCode);
  $('#mobileTel').val(results.mobilePhone);
  $('#emailTel').val(results.eamil);
  $('#cerNoTel').val(results.certNo);
  $('#acctTel').val(results.tbAcct.acct);
  $('#statusCd').get(0).selectedIndex=0;
  $('#defaultPswTel').val(results.tbAcct.password);
  $('#effectDate').val(results.tbAcct.enableDate);
  $('#invalidDate').val(results.tbAcct.disableDate);

  psw = results.tbAcct.password;

  isEnableStatus(results.tbAcct.statusCd);  //判断状态

  for(var i = 0; i <results.tbRolesList.length; i++){
    roldId = roldId + "、" + results.tbRolesList[i].roleId;
  }
  $('#roleTel').val(roldId.substring(1,roldId.length));
  // $('#orgTypeList').addTag(results.tbRolesList);
  // console.log(results.tbRolesList);
}

function initAddUserInfo(results){    //初始化用户信息(新增)
  $('#psnTel').val(results.psnName);
  $('#psnNumTel').val(results.psnCode);
  $('#mobileTel').val(results.mobilePhone);
  $('#emailTel').val(results.eamil);
  $('#cerNoTel').val(results.certNo);
}

function addTbAcct(){         //新增
  if(addOrgList.length == 0){
    alert("组织不能为空");
  }else{
    var editFormAcctVo = {
      "acct": $('#acctTel').val(),
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
        alert('添加成功');
        submitSuccess();
      },
      error:function(err){
        console.log(err);
        alert('添加失败');
      }
    });
  }
}

function updateAcct(){      //编辑主账号
    var statusCd;
    if($('#statusCd').get(0).selectedIndex == 0){
      statusCd = "1000";
    }else{
      statusCd = "1100";
    }
    var editFormAcctVo = {
      "acct": $('#acctTel').val(),
      "acctId": acctId,
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
        alert('编辑成功');
        submitSuccess();
      },
      error:function(err){
        console.log(err);
        alert('编辑失败');
      }
    });
}

function deleteTbAcct(){    //删除主账号
  $.ajax({
    url: '/acct/deleteTbAcct?&acctId='+parseInt(acctId),
    type: 'DELETE',
    contentType: "application/json",
    async:false,
    dataType:"json",
    success: function (data) { //返回json结果
      console.log(data);
      alert('删除成功');
      submitSuccess();
    },
    error:function(err){
      console.log(err);
      alert('删除失败');
    }
  });
}

function isDelete(){    //询问是否删除账号
  var r=confirm("是否删除主账号");
  if(r == true){
    deleteTbAcct();   //确定，删除
  }
}

function removeAcctOrg(orgId){   //编辑时删除组织
    $.ajax({
      url: '/acct/removeAcctOrg?personnelId='+personnelId+'&acctId='+acctId+'&orgId='+parseInt(orgId),
      type: 'DELETE',
      async:false,
      contentType: "application/json",
      dataType:"json",
      success: function (data) { //返回json结果
        console.log(data);
        alert('删除成功');
      },
      error:function(err){
        console.log(err);
        alert('删除失败');
      }
    });
}

function addAcctOrg(orgId){ //编辑时新增组织
  var tbAccountOrgRel={
    "acctId": acctId,
    "orgId": parseInt(orgId)
  };
  $.ajax({
    url: '/acct/addAcctOrg',
    type: 'POST',
    async:false,
    contentType: "application/json",
    data: JSON.stringify(tbAccountOrgRel),
    dataType:"JSON",
    success: function (data) { //返回json结果
      console.log(data);
    },
    error:function(err){
      console.log(err);
      alert('新增失败');
    }
  });
}

function acctSubmit(){   //提交事件
  if($('#acctTel').val()!='' && $('#statusCd').val()!='' && $('#roleTel').val()!='' && $('#defaultPswTel').val()!=''){
    if(opBtn == 1){
      addTbAcct();
    }else if(opBtn == 0){ //编辑
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

function isEnableStatus(statusCd){    //判断状态

  if(statusCd == "1000"){                
    $('#statusCd').get(0).selectedIndex=0;
  }else if(statusCd == "1100"){
    $('#statusCd').get(0).selectedIndex=1;
  }
}

//删除组织
function deleteOrg(id){
  if(opBtn == 1){    //新增
    addOrgList.splice(id-2,1);
    orgTable.destroy();
    initOrgTable(addOrgList);
  }else if(opBtn == 0){    //编辑
    if(editOrgList.length == 1){
      alert("无法删除所有组织");
    }else{
      //getTableContent();
      removeAcctOrg(editOrgList[id-2].orgId);
      editOrgList.splice(id-2,1);
      orgTable.destroy();
      initOrgTable(editOrgList);
    }
  }
}

function cancel() {   //取消按钮
  var url = '';
  if(hType == "mh"){  //返回mainList.html
    url = "mainList.html?orgTreeId=" + orgTreeId + "&orgName=" + orgName + "&orgId=" + orgId;
  }else if(hType == "ah"){  //返回add.html
    url = "add.html?orgTreeId=" + orgTreeId + "&orgName=" + orgName + "&orgId=" + orgId;
  }
  window.location.href = url;
}   

// // tags init
// if(typeof $.fn.tagsInput !== 'undefined'){
//   $('#orgTypeList').tagsInput();
// }

// // //角色选择
// function openTypeDialog() {
//   parent.layer.open({
//       type: 2,
//       title: '选中组织类别',
//       shadeClose: true,
//       shade: 0.8,
//       area: ['70%', '85%'],
//       maxmin: true,
//       content: 'roleDialog.html?id=' + orgId,
//       btn: ['确认', '取消'],
//       yes: function(index, layero){
//           //获取layer iframe对象
//           var iframeWin = parent.window[layero.find('iframe')[0].name];
//           var checkNode = iframeWin.checkNode;
//           parent.layer.close(index);
//           $('#orgTypeList').importTags(checkNode);
//           $('.ui-tips-error').css('display', 'none');
//           orgTypeList = checkNode;
//           console.log(checkNode);
//       },
//       btn2: function(index, layero){},
//       cancel: function(){}
//   });
// }


function submitSuccess(){     //提交成功
    var url = "mainList.html?orgTreeId=" + orgTreeId + "&orgName=" + orgName + "&orgId=" + orgId;
    window.location.href = url;
}

// $("#addSubAcctBtn").on('click', function () {    //添加从账号
//   var url = 'addSubAccount.html?orgTreeId=' + orgTreeId + '&hType=th&personnelId=' + personnelId + 
//                     '&opBtn=1&mainAcctId='+ acctId +'&orgName=' + orgName + '&orgId=' + orgId +'&toMainType=' + hType;
//   $(this).attr('href', url);
// })

$("#defaultPswTel").focus(function (){    //默认密码输入框获得焦点
  if($("#defaultPswTel").attr("type") == "password"){
    $("#defaultPswTel").val('');
    $("#defaultPswTel").attr("type","tel");
  }
})

$("#defaultPswTel").blur(function (){     //默认密码输入框失去焦点
  if($("#defaultPswTel").val() == ''){
    $("#defaultPswTel").val(psw);
    $("#defaultPswTel").attr("type","password");
  }
})


if(opBtn==0){     //查看并编辑主账号
  getUser(acctId);
}else{            //编辑或新增主账号
  getAcctUser(personnelId);
}





