var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgFullName = getQueryString('orgFullName');
var hType = getQueryString('hType');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');

var acctId = getQueryString('acctId');
var personnelId = getQueryString('personnelId');
var orgTable;
var addOrgList = [];
var roleList = [];      //需要上传的角色列表
var userRoleList = [];      //用户已有角色列表
var formValidate;
var toastr = window.top.toastr;

$('#statusCd').get(0).selectedIndex=0; //判断状态，默认生效
$('#cerType').get(0).selectedIndex=0;  //判断证件类型,默认身份证
window.localStorage.setItem('userRoleList',JSON.stringify(''));

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

function getAcctUser(personnelId){     //获取人员信息(新增)
  $http.get('/user/getPsnUser', {    
    personnelId: personnelId,
    userType: "1"
  }, function (data) {
      //新增
      initAddUserInfo(data);
      addAcctAutoSelectOrg();
      initSubOrgTable(data.slaveAcctOrgVoPage);
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
        { 'data': "orgId", 'title': '组织树', 'className': 'row-orgTree' ,
        'render': function (data, type, row, meta) {
          return '标准组织树';
      }
      },
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
              return "<a class='Icon IconDel' href='javascript:void(0);' id='delOrgBtn' title='删除' onclick='deleteOrg()'></a>";
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

function initSubOrgTable(results){    //从账号组织数据
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
            return '<a href="addSubAccount.html?orgTreeId=' + orgTreeId + '&toMainType=' + hType +'&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +'&hType=th&mainAcctId='+ acctId +
                                  '&acctId='+ row.slaveAcctId + '&statusCd='+ row.statusCd +'&opBtn=0">'+ row.slaveAcct +'</a>'
        }
      },
        { 'data': "slaveAcctType", 'title': '从账号类型', 'className': 'row-acctype' },
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

function initAddUserInfo(results){    //初始化用户信息(新增)
  $('#psnTel').val(results.psnName);
  $('#psnNumTel').val(results.psnCode);
  $('#mobileTel').val(results.mobilePhone);
  $('#emailTel').val(results.eamil);
  $('#cerNoTel').val(results.certNo);
  $('#acctTel').val(results.psnCode);
  setDate();
}

function addTbAcct(){         //新增
  if(!formValidate.isAllPass())
    return;
  if(roleList.length == 0){
    roleList = userRoleList;
  }
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
      toastr.error('新增失败');
    }
  });
}

function addAcctAutoSelectOrg(){    //新增主账号时自动选择组织
    addOrgList.push({'orgId':orgId,'fullName':orgFullName});
    initOrgTable(addOrgList);
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
    value: new Date(nowDate)
  }); 

  laydate.render({
    elem: '#invalidDate', //指定元素
    value: new Date(toDate)
  }); 
}

//删除组织
function deleteOrg(){
    toastr.error("无法删除");
}

function cancel() {   //取消按钮
  var url = "add.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&orgFullName=" + encodeURI(orgFullName);
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
      },
      btn2: function(index, layero){},
      cancel: function(){}
  });
}


function submitSuccess(){     //提交成功
    var url = "mainList.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;
    window.location.href = url;
}

getAcctUser(personnelId);
noSelectUserInfo();






