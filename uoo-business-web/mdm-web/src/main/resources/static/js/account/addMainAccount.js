var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgFullName = getQueryString('orgFullName');
var hType = getQueryString('hType');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');
var tabPage = getQueryString('tabPage');

var acctId = getQueryString('acctId');
var statusCd = getQueryString('statusCd');
var personnelId = getQueryString('personnelId');
var opBtn = getQueryString('opBtn');  // 0是编辑  1是新增
var orgTable;
var editOrgList = [];
var addOrgList = [];
var flag = 0;
var psw;
var roleList = [];      //需要上传的角色列表
var userRoleList = [];      //用户已有角色列表
var formValidate;
var toastr = window.top.toastr;
var pswTip = "1、登录密码必须包含数字、大写字母、小写字母、特殊符号中的3种，且长度大于8位"+
              "2、登录密码中连续的或相同的数据或字母（包括倒叙）不能超过3位"+
              "如：fz12348，abcd12格式不正确；ACabc123、Ab111aaa正确"+
              "3、登录密码中不能包含员工账号、中文标点符号以及反斜杠\ "+
              "4、键盘中连续按键不能超过3位；如：qweruio1，asdfuio1格式不正确；qeruio12、asdjkl12格式正确";

$('#invalidDate').val(''),
$('#effectDate').val(''),
$('#statusCd').get(0).selectedIndex=0; //判断状态，默认生效
$('#cerType').get(0).selectedIndex=0;  //判断证件类型,默认身份证

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
  var addAcctForm = $('#addAcctForm');
  formValidate = new Validate(addAcctForm);
  formValidate.immediate();
  //formValidate.isAllPass();
  // addAcctForm.find(':input').each(function () {
  //     $(this).hover(function () {
  //         formValidate.isPass($(this));
  //     });
  // });
});

function getUser(acctId) {           //查看并编辑主账号
    $http.get('/user/getUser', {   //http://192.168.58.112:18000/user/getUser
        acctId: acctId,
        userType: "1"
    }, function (data) {
        personnelId = data.personnelId;
        for(var i=0;i<data.acctOrgVoPage.records.length;i++){
          editOrgList.push({"orgId":data.acctOrgVoPage.records[i].orgId,"fullName":data.acctOrgVoPage.records[i].fullName});
        }
        $('#main-title').html("主账号信息");
        $('#addBtnDiv').css("display","none");
        $('#acctInfo').css("display","block");
        $('#acctEditButton').css("display","inline-block");
        initOrgTable(data.acctOrgVoPage.records);
        initAcctInfoCheck(data);
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
      $('#main-title').html("新增主账号");
      $("#addBtn").css("display","none");
      $("#acctInfo").css("display","none");
      $('#delAcct').css("display","none");
      $('#editAcctPanel').css("display","block");
      $('#editBtnDiv').css("display","none");
      initAddUserInfo(data);
      addAcctAutoSelectOrg();
      initSubOrgTable(data.slaveAcctOrgVoPage);
    }else{                      //编辑
      $('#main-title').html('主账号信息');
      $("#addBtn").css("display","none");
      $("#addBtnWFright").css("display","block");
      $('#addBtnDiv').css("display","none");
      $('#acctInfo').css("display","block");
      $('#acctEditButton').css("display","inline-block");
      opBtn = 0;
      acctId = data.tbAcct.acctId;
      for(var i=0;i<data.acctOrgVoPage.records.length;i++){
        editOrgList.push({"orgId":data.acctOrgVoPage.records[i].orgId,"fullName":data.acctOrgVoPage.records[i].fullName});
      }
      initOrgTable(data.acctOrgVoPage.records);
      initAcctInfoCheck(data);
      initEditUserInfo(data);
      initSubOrgTable(data.slaveAcctOrgVoPage.records);
    }
  }, function (err) {
    console.log(err)
  })
}

function noSelectUserInfo(){     //控制人员信息不可选
   $("#psnTel").attr("disabled","disabled");
   $("#psnNumTel").attr("disabled","disabled");
   $("#mobileTel").attr("disabled","disabled");
   $("#emailTel").attr("disabled","disabled");
   $("#cerType").attr("disabled","disabled");
   $("#cerNoTel").attr("disabled","disabled");
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
          if(row.fullName != null){
            // if(row.fullName.search('->') != -1){
            //   var s = row.fullName.replace(/->/g,'/');
            //   return s.substring(0,s.length-1);
            // }else{
              return row.fullName;
            }else{
              return "";
          }
        }
      },
      {'data': "orgId", 'title': '操作', 'className': 'row-delete' ,
      'render': function (data, type, row, meta) {
        return "<a class='Icon IconDel' href='javascript:void(0);' id='delOrgBtn' onclick='deleteOrg("+ num + ")'></a>";
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
            return '<a href="addSubAccount.html?orgTreeId=' + orgTreeId + '&toMainType=' + hType +'&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +'&hType=th&mainAcctId='+ acctId +
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
  // var roldId = '';
  $('#psnTel').val(results.psnName);
  $('#psnNumTel').val(results.psnNbr);
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

  $('#roleTel').addTag(results.tbRolesList);
}

function initAcctInfoCheck(results){     //初始化用户信息(编辑时查看面板)

  isNull("#psnNameLable",results.psnName);
  isNull("#mobileLable",results.mobilePhone);
  isNull("#emailLable",results.eamil);
  isNull("#acctLable",results.tbAcct.acct);
  isNull("#psnNumLable",results.psnNbr);
  isNull("#cerNoLable",results.certNo);
  isNull("#effectDateLable",results.tbAcct.enableDate);
  isNull("#invalidDateLable",results.tbAcct.disableDate);

  for(var i = 0; i <results.tbRolesList.length; i++){
    $("#nameAndRole").append($("<span class='roleTag'>"+results.tbRolesList[i].roleName+"</span>"));
  }
  userRoleList = results.tbRolesList;
  
  window.localStorage.setItem('userRoleList',JSON.stringify(userRoleList));
}

function initAddUserInfo(results){    //初始化用户信息(新增)
  $('#psnTel').val(results.psnName);
  $('#psnNumTel').val(results.psnCode);
  $('#mobileTel').val(results.mobilePhone);
  $('#emailTel').val(results.eamil);
  $('#cerNoTel').val(results.certNo);
  $('#acctTel').val(results.psnCode);
}

function addTbAcct(){         //新增
  if(!formValidate.isAllPass())
    return;
  if(roleList.length == 0){
    roleList = userRoleList;
  }
  if(addOrgList.length == 0){
    toastr.warning('组织不能为空!');
  }else{
    var editFormAcctVo = {
      "acct": $('#acctTel').val(),
      "acctOrgVoList": addOrgList,
      "disableDate": $('#invalidDate').val(),
      "enableDate": $('#effectDate').val(),
      "password": $('#defaultPswTel').val(),
      "personnelId": personnelId,
      "statusCd": "1000", 
      "tbRolesList":roleList,
      "userType": "1"
    };
    console.log(editFormAcctVo);
  
    $.ajax({
      url: '/acct/addTbAcct',
      type: 'POST',
      contentType: "application/json",
      data: JSON.stringify(editFormAcctVo),
      dataType:"JSON",
      success: function (data) { //返回json结果
        if(data.state === 1000){
          toastr.success(data.message);
          submitSuccess();
        }else{
          toastr.error(data.message);
        }
      },
      error:function(err){
        console.log(err);
        toastr.error('新增失败');
      }
    });
  }
}

function updateAcct(){      //编辑主账号
    if(!formValidate.isAllPass())
        return;
    var statusCd;
    if(roleList.length == 0){
      roleList = userRoleList;
    }
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
      "tbRolesList":roleList,
      "userType": "1"
    };
    console.log(editFormAcctVo);

    $.ajax({
      url: '/acct/updateAcct',
      type: 'PUT',
      contentType: "application/json",
      data: JSON.stringify(editFormAcctVo),
      dataType:"JSON",
      success: function (data) { //返回json结果
        if(data.state === 1000){
          toastr.success(data.message);
          submitSuccess();
        }else{
          toastr.error(data.message);
        }
      },
      error:function(err){
        console.log(err);
        toastr.error('编辑失败');
      }
    });
}

function deleteTbAcct(){    //删除主账号
  parent.layer.confirm('此操作将删除该用户, 是否继续?', {
    icon: 0,
    title: '提示',
    btn: ['确定','取消']
}, function(index, layero){
    parent.layer.close(index);
    $.ajax({
      url: '/acct/deleteTbAcct?&acctId='+parseInt(acctId),
      type: 'DELETE',
      contentType: "application/json",
      dataType:"json",
      success: function (data) { //返回json结果
        console.log(data);
        toastr.success(data.message);
        submitSuccess();
      },
      error:function(err){
        console.log(err);
        toastr.error('删除失败！');
      }
    });
  }, function(){

  });
}

// function isDelete(){    //询问是否删除账号
//   var r=confirm("是否删除主账号");
//   if(r == true){
//     deleteTbAcct();   //确定，删除
//   }
// }

function removeAcctOrg(orgId){   //编辑时删除组织
    $.ajax({
      url: '/acct/removeAcctOrg?personnelId='+personnelId+'&acctId='+acctId+'&orgId='+parseInt(orgId),
      type: 'DELETE',
      contentType: "application/json",
      dataType:"json",
      success: function (data) { //返回json结果
        console.log(data);
        toastr.success(data.message);
      },
      error:function(err){
        console.log(err);
        toastr.error('删除失败');
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
    contentType: "application/json",
    data: JSON.stringify(tbAccountOrgRel),
    dataType:"JSON",
    success: function (data) { //返回json结果
      console.log(data);
      toastr.success(data.message);
    },
    error:function(err){
      console.log(err);
      toastr.error('新增失败');
    }
  });
}

function addAcctAutoSelectOrg(){    //新增主账号时自动选择组织
    addOrgList.push({'orgId':orgId,'fullName':orgFullName});
    initOrgTable(addOrgList);
}

function addBtnWClick(){    //新增时已有主账号时点击添加组织按钮事件
    var orgNa = [];
    for(var i = 0;i < editOrgList.length; i++){
        orgNa.push(editOrgList[i].orgId);
    }
    if(orgNa.indexOf(parseInt(orgId)) != -1){
        toastr.error("用户已在该组织下存在");
    }else{
        addAcctOrg(orgId);
        editOrgList.push({'orgId':orgId,'fullName':orgFullName});
        orgTable.destroy();
        initOrgTable(editOrgList); 
    }
}

function editAcctInfo(){    //切换到用户信息编辑面板
    $("#acctInfo").css("display","none");
    $("#editAcctPanel").css("display","block");
}

function backToAcctInfo(){  //返回用户信息查看面板
  $("#acctInfo").css("display","block");
  $("#editAcctPanel").css("display","none");

}

function acctSubmit(){   //提交事件
  //if($('#acctTel').val()!='' && $('#statusCd').val()!='' && $('#roleTel').val()!='' && $('#defaultPswTel').val()!=''){
    if(opBtn == 1){
      addTbAcct();
    }else if(opBtn == 0){ //编辑
      updateAcct();
    }
}

  laydate.render({
    elem: '#effectDate', //指定元素
    // format : 'yyyy-MM-dd',
    // /*value : new Date(),*/
    // done: function(value, date, endDate){
    //   formValidate.isAllPass($('#effectDate'))
    // }
  }); 

  laydate.render({
    elem: '#invalidDate', //指定元素
    // format : 'yyyy-MM-dd',
    // /*value : new Date(),*/
    // done: function(value, date, endDate){
    //    formValidate.isAllPass($('#invalidDate'))
    // }
  }); 

function isEnableStatus(statusCd){    //判断状态

  if(statusCd == "1000"){                
    $('#statusCd').get(0).selectedIndex=0;
  }else if(statusCd == "1100"){
    $('#statusCd').get(0).selectedIndex=1;
  }
}

function isNull(s,r){    //判断是否为null
    if(r == null){
      $(s).text("");
    }else{
      $(s).text(r);
    }
}

//删除组织
function deleteOrg(id){
  if(opBtn == 1){    //新增
    // addOrgList.splice(id-2,1);
    // orgTable.destroy();
    // initOrgTable(addOrgList);
    toastr.warning("无法删除所有组织");
  }else if(opBtn == 0){    //编辑
    if(editOrgList.length == 1){
      toastr.warning("无法删除所有组织");
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
    url = "mainList.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;
  }else if(hType == "ah"){  //返回add.html
    url = "add.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&orgFullName=" + encodeURI(orgFullName);
  }else if(hType == "uh"){
    url = "/inaction/user/edit.html?orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName) + "&id=" + orgId + 
    "&personnelId=" + personnelId + "&orgRootId=" + orgRootId + "&tabPage=" + tabPage;
  }
  window.location.href = url;
}   

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
  $('#roleTel').tagsInput();
}

// //角色选择
function openTypeDialog() {
  parent.layer.open({
      type: 2,
      title: '选择角色',
      shadeClose: true,
      shade: 0.8,
      area: ['50%', '65%'],
      maxmin: true,
      content: 'roleDialog.html',
      btn: ['确认', '取消'],
      yes: function(index, layero){
          //获取layer iframe对象
          var iframeWin = parent.window[layero.find('iframe')[0].name];
          var checkRole = iframeWin.checkRole;
          var checkNode = iframeWin.checkNode;
          parent.layer.close(index);
          $('#roleTel').importTags(checkNode);
          $('.ui-tips-error').css('display', 'none');
          window.localStorage.setItem('userRoleList',JSON.stringify(checkRole));
          roleList = checkRole;
          console.log(roleList);
      },
      btn2: function(index, layero){},
      cancel: function(){}
  });
}


function submitSuccess(){     //提交成功
    var url = '';
    if(hType != "uh"){
      url = "mainList.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;
    }else{
      url = "/inaction/user/edit.html?orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName) + "&id=" + orgId + 
                                      "&personnelId =" + personnelId + "&orgRootId =" + orgRootId + "&tabPage=" + tabPage;
    }
    window.location.href = url;
}

$("#defaultPswTel").focus(function (){    //默认密码输入框获得焦点
  if($("#defaultPswTel").attr("type") == "password"){
    $("#defaultPswTel").val('');
    $("#defaultPswTel").attr("type","text");
  }
})

$("#defaultPswTel").blur(function (){     //默认密码输入框失去焦点
  if($("#defaultPswTel").val() == ''){
    $("#defaultPswTel").val(psw);
    $("#defaultPswTel").attr("type","password");
    formValidate.isAllPass($('#defaultPswTel'));
  }
})

$('#defaultPswTel').click(function() {
  var elSearch = $('input[type=password]');
  if (elSearch.val() == '') {
      elSearch.errorTip(pswTip);
  }
});


if(opBtn==0){     //查看并编辑主账号
  getUser(acctId);
  noSelectUserInfo();
}else{            //编辑或新增主账号
  getAcctUser(personnelId);
  noSelectUserInfo();
}





