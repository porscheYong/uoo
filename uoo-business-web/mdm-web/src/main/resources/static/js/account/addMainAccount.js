var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgFullName = getQueryString('orgFullName');
var hType = getQueryString('hType');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');
var orgTreeName = getQueryString('orgTreeName');

var acctId = getQueryString('acctId');
var personnelId = getQueryString('personnelId');
var orgTable;
var addOrgList = [];
var roleList = [];      //需要上传的角色列表
var userRoleList = [];      //用户已有角色列表
var formValidate;
var orgNum = 0;
var toastr = window.top.toastr;
var cerTypeList = window.top.dictionaryData.certType();
var statusCdList = window.top.dictionaryData.statusCd();

window.localStorage.setItem('userRoleList',JSON.stringify(''));

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
  var addAcctForm = $('#addAcctForm');
  formValidate = new Validate(addAcctForm);
  formValidate.immediate();
  addAcctForm.find(':input').each(function () {
    $(this).bind({
        paste : function(){
            formValidate.isPass($(this));
            $(this).removeClass('error');
        }
    });
  });
});

// lulu ui tips插件
seajs.use('/vendors/lulu/js/common/ui/Tips', function () {
  $('#defaultPsw').tips({
      align: 'right'
  });
});

function getAcctUser(personnelId){     //获取人员信息(新增)
  $http.get('/user/getPsnUser', {    
    personnelId: personnelId,
    userType: "1"
  }, function (data) {
      //新增
      initAddUserInfo(data);
      addAcctAutoSelectOrg(orgId,orgFullName);
  }, function (err) {

  })
}

function noSelectUserInfo(){     //控制人员信息不可选
   $("#psnName").attr("disabled","disabled");
   $("#psnNum").attr("disabled","disabled");
   $("#mobile").attr("disabled","disabled");
   $("#email").attr("disabled","disabled");
   $("#cerType").attr("disabled","disabled");
   $("#cerNo").attr("disabled","disabled");
}

function initOrgTable(results){         //主账号组织数据表格
    var num =1;
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
        { 'data': "id", 'title': '序号', 'className': 'row-id' ,
        'render': function (data, type, row, meta) {
          return num++;
      }
      },
        { 'data': "orgTreeName", 'title': '组织树', 'className': 'row-orgTree'},
        { 'data': "fullName", 'title': '组织名称', 'className': 'row-fullName' ,
        'render': function (data, type, row, meta) {
          if(row.fullName != null){
              return row.fullName;
            }else{
              return "";
          }
        }
      },
      {'data': "orgId", 'title': '操作', 'className': 'row-delete' ,
      'render': function (data, type, row, meta) {
              return "<a class='Icon IconDel' href='javascript:void(0);' id='delOrgBtn' title='删除' onclick='deleteOrg("+num+","+row.orgId+")'></a>"; 
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

function initAddUserInfo(results){    //初始化用户信息(新增)
  $('#psnName').val(results.psnName);
  $('#psnNum').val(results.psnCode);
  $('#mobile').val(results.mobilePhone);
  $('#email').val(results.eamil);
  $('#cerNo').val(results.certNo);
  $('#acct').val(results.psnCode);
  setDate();

  for(var i=0;i<statusCdList.length;i++){
      $("#statusCd").append("<option value='" + statusCdList[i].itemValue + "'>" + statusCdList[i].itemCnname +"</option>");
  }

  for(var i=0;i<cerTypeList.length;i++){
    if(results.certType === cerTypeList[i].itemValue){
      $("#cerType").append("<option value='" + cerTypeList[i].itemValue + "' selected>" + cerTypeList[i].itemCnname +"</option>");
      break;
    }
  }
  seajs.use('/vendors/lulu/js/common/ui/Select', function () {
    $('#cerType').selectMatch();
    $("#statusCd").selectMatch();
  });
}

function addTbAcct(){         //新增
  if(!formValidate.isAllPass())
    return;
  if(roleList.length == 0){
    roleList = userRoleList;
  }
  var editFormAcctVo = {
    "acct": $('#acct').val(),
    "acctOrgVoList": addOrgList,
    "disableDate": $('#invalidDate').val(),
    "enableDate": $('#effectDate').val(),
    "password": $('#defaultPsw').val(),
    "personnelId": personnelId,
    "statusCd": $("#statusCd").val(), 
    "tbRolesList":roleList,
    "userType": "1"
  };

  $.ajax({
    url: '/acct/addTbAcct',
    type: 'POST',
    contentType: "application/json",
    data: JSON.stringify(editFormAcctVo),
    dataType:"JSON",
    success: function (data) { //返回json结果
      if(data.state === 1000){
        toastr.success(data.message);
        submitSuccess(personnelId);
      }else{
        toastr.error(data.message);
      }
    },
    error:function(err){
      toastr.error('新增失败');
    }
  });
}

function addAcctAutoSelectOrg(orgId,orgFullName){    //新增主账号时自动选择组织
    orgNum++;
    addOrgList.push({'orgId':orgId,'fullName':orgFullName,'orgTreeId':orgTreeId,'orgTreeName':orgTreeName});
    initOrgTable(addOrgList);
}

//组织选择
function openOrgDialog() {
  var flag = 0;
  parent.layer.open({
      type: 2,
      title: '选择组织',
      shadeClose: true,
      shade: 0.8,
      area: ['27%', '80%'],
      maxmin: true,
      content: 'orgDialog.html?orgTreeId='+orgTreeId,
      btn: ['确认', '取消'],
      yes: function(index, layero){
          //获取layer iframe对象
          var iframeWin = parent.window[layero.find('iframe')[0].name];
          var orgIdSelect = iframeWin.orgIdSelect;
          var orgFullName = iframeWin.orgFullName;
          // var orgTreeId = iframeWin.orgTreeId;
          // var businessName = iframeWin.businessName;
          parent.layer.close(index);

          for(var i=0;i<addOrgList.length;i++){
              if(orgIdSelect == addOrgList[i].orgId && orgTreeId == addOrgList[i].orgTreeId){
                  flag = 1;
                  break;
              }
          }
          if(flag == 1){
              toastr.error("已选择该组织");
          }else{
              addAcctAutoSelectOrg(orgIdSelect,orgFullName);
          }
      },
      btn2: function(index, layero){},
      cancel: function(){}
  });
}

function setDate(){    //设置时间

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

  laydate.render({
    elem: '#effectDate', //指定元素
    value: nowDate,
    done: function(value, date, endDate){
      if(value != ""){
        $("#effectDate").removeClass('error');
      }
    }
  }); 

  laydate.render({
    elem: '#invalidDate', //指定元素
    value: toDate,
    done: function(value, date, endDate){
      if(value != ""){
        $("#invalidDate").removeClass('error');
      }
    }
  }); 
}

//删除组织
function deleteOrg(num,orgId){
    if(orgNum == 1){
      toastr.error("无法删除所有组织");
    }else{
      orgNum--;
      addOrgList.splice(num-2,1);
    }
    initOrgTable(addOrgList);
}


function cancel() {   //取消按钮
  var url = "list.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + 
                      "&orgFullName=" + encodeURI(orgFullName);
  window.location.href = url;
}   

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
  $('#role').tagsInput();
}

// //角色选择
function openTypeDialog() {
  parent.layer.open({
      type: 2,
      title: '选择角色',
      shadeClose: true,
      shade: 0.8,
      area: ['70%', '85%'],
      maxmin: true,
      content: 'roleDialog.html',
      btn: ['确认', '取消'],
      yes: function(index, layero){
          //获取layer iframe对象
          var iframeWin = parent.window[layero.find('iframe')[0].name];
          var checkRole = iframeWin.checkRole;
          var checkNode = iframeWin.checkNode;
          $('#role').importTags(checkNode);
          $('.ui-tips-error').css('display', 'none');
          window.localStorage.setItem('userRoleList',JSON.stringify(checkRole));
          roleList = checkRole;
          parent.layer.close(index);
      },
      btn2: function(index, layero){},
      cancel: function(){}
  });
}


// function submitSuccess(){     //提交成功
//     var url = "";



//     url = "list.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;
//     window.location.href = url;
// }

//提交成功
function submitSuccess(personnelId){       
  var url = "";
  $http.get('/user/getPsnUser', {    
      personnelId: personnelId,
      userType: "1"
    }, function (data) {
      url = "editMainAccount.html?acctId="+ data.tbAcct.acctId +"&orgFullName=" + encodeURI(orgFullName) + "&orgTreeId=" + orgTreeId + 
              "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&hType=mh" + "&orgTreeName="+encodeURI(orgTreeName);
      window.location.href = url;
    }, function (err) {
  
    })
}

getAcctUser(personnelId);
noSelectUserInfo();






