// loadingMask
// var loading = parent.loading;
// loading.screenMaskEnable('container');

var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgFullName = getQueryString('orgFullName');
var hType = getQueryString('hType');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');
var tabPage = getQueryString('tabPage');
var acctId = getQueryString('acctId');
var orgTreeName = getQueryString('orgTreeName');

var personnelId;
var orgTable;
var orgNum = 0;
var slaveOrgIdList = [];
var psw;
var roleList = [];      //需要上传的角色列表
var userRoleList = [];      //用户已有角色列表
var formValidate;
var treeNameList = [];
var toastr = window.top.toastr;

$('#statusCd').get(0).selectedIndex=0; //判断状态，默认生效
$('#cerType').get(0).selectedIndex=0;  //判断证件类型,默认身份证

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
  var addAcctForm = $('#addAcctForm');
  formValidate = new Validate(addAcctForm);
  formValidate.immediate();
});

// lulu ui tips插件
seajs.use('/vendors/lulu/js/common/ui/Tips', function () {
  $('#defaultPswTel').tips({
      align: 'right'
  });
});

//添加数组IndexOf方法
if (!Array.prototype.indexOf){
  Array.prototype.indexOf = function(elt /*, from*/){
    var len = this.length >>> 0;

    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;

    for (; from < len; from++){
      if (from in this && this[from] === elt)
        return from;
    }
    return -1;
  };
}

function getUser(acctId) {           //查看并编辑主账号
    var date = new Date();
    $http.get('/user/getUser', {   
        acctId: acctId,
        userType: "1",
        _:date.getTime()
    }, function (data) {
        personnelId = data.personnelId;
        orgNum = data.acctOrgVoPage.records.length;
        initAcctInfoCheck(data);
        initEditUserInfo(data);
        initOrgTable(data.acctOrgVoPage.records);
        initSlaveOrgTable(data.slaveAcctOrgVoPage.records);
    }, function (err) {

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
    orgTable = $("#orgTable").DataTable({
    'data': results,
    'destroy':true,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'paging': false,
    'info': false,
    "scrollY": "375px",
    'scrollCollapse': true,
    'columns': [
        { 'data': "id", 'title': '序号', 'className': 'row-id'},
        { 'data': "orgTreeName", 'title': '组织树', 'className': 'row-orgTree'},
        { 'data': "fullName", 'title': '组织名称', 'className': 'row-fullName' ,
        'render': function (data, type, row, meta) {
          if(row.fullName != null){
              return "<span class='spanPoint' title='"+row.fullName+"'>"+row.fullName+"</span>";
            }else{
              return "";
          }
        }
      },
      {'data': "orgId", 'title': '操作', 'className': 'row-delete' ,
      'render': function (data, type, row, meta) {
          // if(slaveOrgIdList.indexOf(row.orgId) != -1){
          //   return "<a class='Icon IconDel' href='javascript:void(0);' id='delOrgBtn' title='删除' onclick='deleteOrg("+ row.orgId + ")'></a>";
          // }else{
            treeNameList.push(row.orgTreeName);
            return "<a class='Icon IconAdd' href='javascript:void(0);' id='addSlaveBtn' title='创建从账号' onclick='addSlaveBtnClick(" + row.acctOrgRelId + "," + row.id + "," + row.orgTreeId +")'></a>"+
                    "<a class='Icon IconDel' href='javascript:void(0);' id='delOrgBtn' title='删除' onclick='deleteOrg("+ row.orgId + "," + row.orgTreeId + ")'></a>";
          //}
        }
    },
    { 'data': "orgId", 'title': 'orgId', 'className': 'row-orgId'}
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

function initSlaveOrgTable(results){    //从账号组织数据
  var subTable = $("#subInfoTable").DataTable({
    'data': results,
    'destroy':true,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'paging': false,
    'info': false,
    "scrollY": "375px",
    'scrollCollapse': true,
    'columns': [
        { 'data': "id", 'title': '序号', 'className': 'row-number' },
        { 'data': "slaveAcct", 'title': '账号名', 'className': 'row-acc' ,
        'render': function (data, type, row, meta) {
            return '<a title="'+ row.slaveAcct +'" href="editSubAccount.html?orgTreeId=' + orgTreeId + '&toMainType=' + hType +'&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +'&hType=th&mainAcctId='+ acctId +
                                  '&acctId='+ row.slaveAcctId + '&statusCd='+ row.statusCd +'">'+ row.slaveAcct +'</a>';
        }
      },
        { 'data': "slaveAcctType", 'title': '从账号类型', 'className': 'row-acctype' },
        { 'data': "orgTreeName", 'title': '组织树', 'className': 'row-orgtree' },
        { 'data': "systemName", 'title': '系统', 'className': 'row-system'
          // 'render': function (data, type, row, meta) {
          //     return '营销系统';
          // }
        },
        { 'data': "fullName", 'title': '归属组织', 'className': 'row-org' ,
        'render': function (data, type, row, meta) {
          if(row.fullName != null){
            return "<span class='spanPoint' title='"+row.fullName+"'>"+row.fullName+"</span>";
          }else{
            return "";
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
  var slaveOrg =  results.slaveAcctOrgVoPage.records;
  $('#psnTel').val(results.psnName);
  $('#psnNumTel').val(results.psnNbr);
  $('#mobileTel').val(results.mobilePhone);
  $('#emailTel').val(results.eamil);
  $('#cerNoTel').val(results.certNo);
  $('#acctTel').val(results.tbAcct.acct);
  $('#statusCd').get(0).selectedIndex=0;
  $('#defaultPswTel').val(results.tbAcct.password);
  setDate(results.tbAcct.enableDate,results.tbAcct.disableDate);

  psw = results.tbAcct.password;

  isEnableStatus(results.tbAcct.statusCd);  //判断状态

  $('#roleTel').addTag(results.tbRolesList);

  for(var i = 0;i < slaveOrg.length;i++){
      slaveOrgIdList.push(slaveOrg[i].orgId);
  }
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
        toastr.success(data.message);
        submitSuccess();
      },
      error:function(err){
        toastr.error('删除失败！');
      }
    });
  }, function(){

  });
}

function removeAcctOrg(orgId,orgTreeId){   //编辑时删除组织
    $.ajax({
      url: '/acct/removeAcctOrg?personnelId='+personnelId+'&acctId='+acctId+'&orgId='+parseInt(orgId)+'&orgTreeId='+orgTreeId,
      type: 'DELETE',
      contentType: "application/json",
      dataType:"json",
      success: function (data) { //返回json结果
        if(data.state == 1000){
          toastr.success(data.message);
          refreshTb(acctId);
          orgNum -= 1;
        }else{
          toastr.error(data.message);
        }
      },
      error:function(err){
        toastr.error('删除失败');
      }
    });
}

function addAcctOrg(orgId,orgTreeId){ //编辑时新增组织
  var tbAccountOrgRel={
    "acctId": acctId,
    "orgId": parseInt(orgId),
    "orgTreeId": parseInt(orgTreeId)
  };
  $.ajax({
    url: '/acct/addAcctOrg',
    type: 'POST',
    contentType: "application/json",
    data: JSON.stringify(tbAccountOrgRel),
    dataType:"JSON",
    success: function (data) { //返回json结果
      if(data.state == 1000){
        toastr.success(data.message);
        treeNameList = [];
        refreshTb(acctId);
        orgNum += 1;
      }else{
        toastr.error(data.message);
      }
    },
    error:function(err){
      toastr.error('新增失败');
    }
  });
}

function refreshTb(acctId) {           //新增组织后刷新组织表格
  var date = new Date();
  $http.get('/user/getUser', {   
      acctId: acctId,
      userType: "1",
      _:date.getTime()
  }, function (data) {
      initOrgTable(data.acctOrgVoPage.records);
      initSlaveOrgTable(data.slaveAcctOrgVoPage.records);
  }, function (err) {

  })
}

function addSlaveBtnClick(acctOrgRelId,id,orgTreeId){      //点击新增从账号
    var sFullName = orgTable.row(id-1).data().fullName;
    var url = 'addSubAccount.html?orgTreeId=' + orgTreeId + '&hType=th&personnelId=' + personnelId + '&orgTreeId=' + orgTreeId +
                      '&mainAcctId='+ acctId +'&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +'&toMainType=' + hType +
                      '&fullName=' + encodeURI(sFullName) + '&acctOrgRelId=' + acctOrgRelId + '&orgTreeName=' + treeNameList[id-1];
    window.location.href = url;
}

function editAcctInfo(){    //切换到用户信息编辑面板
    $("#acctInfo").css("display","none");
    $("#editAcctPanel").css("display","block");
    $('#acctEditButton').css("display","none");
}

function backToAcctInfo(){  //返回用户信息查看面板
  $("#acctInfo").css("display","block");
  $("#editAcctPanel").css("display","none");
  $('#acctEditButton').css("display","inline-block");
}

function setDate(eDate,bDate){    //设置时间

    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    var nowDate = year + "-" + month + "-" + day;
    var toDate = (year+10) + "-" + month + "-" + day;

    if(eDate != null || bDate != null){
        nowDate = eDate;
        toDate = bDate;
    }
    
    laydate.render({
      elem: '#effectDate', //指定元素
      value: nowDate
    }); 

    laydate.render({
      elem: '#invalidDate', //指定元素
      value: toDate
    }); 
}

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
function deleteOrg(orgId,orgTreeId){
    // var oId = slaveOrgIdList.indexOf(orgId);
    if(orgNum == 1){
      toastr.warning("无法删除所有组织");
    // }else if(oId != -1){        //如果该组织下有从账号，则提示
    //   parent.layer.confirm('组织下有从账号,删除组织将删除该从账号！', {
    //     icon: 0,
    //     title: '提示',
    //     btn: ['确定','取消']
    //   }, function(index, layero){
    //     removeAcctOrg(orgId,orgTreeId);
    //     slaveOrgIdList.splice(oId,1);
    //     parent.layer.close(index);
    //   }, function(){
    
    //   });
    }else{
      removeAcctOrg(orgId,orgTreeId);
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

//角色选择
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
          $('#roleTel').importTags(checkNode);
          $('#roleTel',window.parent.document).importTags(checkNode);
          $('.ui-tips-error').css('display', 'none');
          window.localStorage.setItem('userRoleList',JSON.stringify(checkRole));
          roleList = checkRole;
          parent.layer.close(index);
      },
      btn2: function(index, layero){},
      cancel: function(){}
  });
}

//组织选择
function openOrgDialog() {
  parent.layer.open({
      type: 2,
      title: '选择组织',
      shadeClose: true,
      shade: 0.8,
      area: ['27%', '80%'],
      maxmin: true,
      content: 'orgDialog.html',
      btn: ['确认', '取消'],
      yes: function(index, layero){
          //获取layer iframe对象
          var iframeWin = parent.window[layero.find('iframe')[0].name];
          var orgIdSelect = iframeWin.orgIdSelect;
          var orgTreeId = iframeWin.orgTreeId;
          parent.layer.close(index);
          addAcctOrg(orgIdSelect,orgTreeId);
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

getUser(acctId);
noSelectUserInfo();






