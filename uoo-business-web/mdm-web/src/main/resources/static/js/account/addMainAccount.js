var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgFullName = getQueryString('orgFullName');
var hType = getQueryString('hType');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');
var orgTreeName = getQueryString('orgTreeName');
var curOrgId = getQueryString('curOrgId');
var curOrgTreeId = getQueryString('curOrgTreeId');

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
var relTypeName = window.top.relTypeName;
var loading = parent.loading;

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
// seajs.use('/vendors/lulu/js/common/ui/Tips', function () {
//   $('#defaultPsw').tips({
//       align: 'right'
//   });
// });

function getAcctUser(personnelId){     //获取人员信息(新增)
  $http.get('/user/getPsnUser', {    
    personnelId: personnelId,
    userType: "1"
  }, function (data) {
      //新增
      initAddUserInfo(data);
      addAcctAutoSelectOrg(orgId,orgFullName,orgName,"99");
  }, function (err) {
      loading.screenMaskDisable('container');
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

//展示主账号组织信息
function setAcctInfoTables(){
    var acctHtml = ""; 
    var relType;
    $("#acctOrgDiv").empty();
    for(var i=0;i<addOrgList.length;i++){
      relType = "";
      for(var j=0;j<relTypeName.length;j++){
          if(addOrgList[i].relType == relTypeName[j].itemValue){
            relType = relTypeName[j].itemCnname;
            break;
          }
      }
      acctHtml += "<div class='curDiv' style='padding:10px 0;margin-left:2%;'>"+
                      "<span class='pngDot'></span>"+
                      "<span class='Name Gray3' style='margin-left:1.5%;' id='orgTreeName_"+i+"'>"+addOrgList[i].orgTreeName+"</span>"+
                      "<span class='Tag' style='cursor:pointer;' title='"+addOrgList[i].fullName+"' id='orgName_"+i+"'>"+addOrgList[i].orgName+"</span>"+
                      // "<span id='editBtn_"+i+"' title='组织编辑' onclick='' class='icon icon-edit'></span>"+
                      "<span class='Tag' style='cursor:pointer;' id='relTypeName_"+i+"'>"+relType+"</span>"+
                      "<span class='fright FunctionBtn' style='float:right;margin-right:-1.2%;'>"+
                          "<a class='BtnDel' style='cursor:pointer;' onclick='deleteOrg("+i+")' id='delBtn_"+i+"'><span></span>删除组织关系</a></span></div>";
  }
  $("#acctOrgDiv").append(acctHtml);
}

function initAddUserInfo(results){    //初始化用户信息(新增)
  $('#psnName').val(results.psnName);
  $('#psnNum').val(results.psnCode);
  $('#mobile').val(results.mobilePhone);
  $('#email').val(results.email);
  $('#cerNo').val(results.certNo);
  $('#acct').val(results.psnCode);
  setDate();

  for(var i=0;i<statusCdList.length;i++){
      var selected = i===0 ? "selected":"";
      $("#statusCd").append("<option value='" + statusCdList[i].itemValue + "'"+selected+">" + statusCdList[i].itemCnname +"</option>");
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
  loading.screenMaskEnable('container');
  if(!formValidate.isAllPass()){
    loading.screenMaskDisable('container');
    return;
  }
    
  if(roleList.length == 0){
    roleList = userRoleList;
  }
  var editFormAcctVo = {
    "acct": $('#acct').val(),
    "acctOrgVoList": addOrgList,
    "disableDate": $('#invalidDate').val(),
    "enableDate": $('#effectDate').val(),
    "password": "4A@12345",
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
        // loading.screenMaskDisable('container');
        toastr.success(data.message);
        submitSuccess(personnelId);
      }else{
        loading.screenMaskDisable('container');
        toastr.error(data.message);
      }
    },
    error:function(err){
      loading.screenMaskDisable('container');
      toastr.error('新增失败');
    }
  });
}

function addAcctAutoSelectOrg(orgId,orgFullName,orgName,relType){    //新增主账号时自动选择组织
    orgNum++;
    addOrgList.push({'orgId':orgId,'fullName':orgFullName,'orgTreeId':orgTreeId,'orgTreeName':orgTreeName,'orgName':orgName,'relType':relType});
    setAcctInfoTables();
}

//组织选择
function openOrgDialog() {
  var flag = 0;
  parent.layer.open({
      type: 2,
      title: '选择组织',
      shadeClose: true,
      shade: 0.8,
      area: ['40%', '90%'],
      maxmin: true,
      content: '/inaction/account/orgDialog.html?orgTreeId='+orgTreeId+'&relType=99',
      btn: ['确认', '取消'],
      yes: function(index, layero){
          //获取layer iframe对象
          var iframeWin = parent.window[layero.find('iframe')[0].name];
          var orgIdSelect = iframeWin.orgIdSelect;
          var orgFullName = iframeWin.orgFullName;
          var relTypeVal = iframeWin.relTypeVal;
          var orgName = iframeWin.orgName;
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
              addAcctAutoSelectOrg(orgIdSelect,orgFullName,orgName,relTypeVal);
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
function deleteOrg(num){
    // if(orgNum == 1){
    //   toastr.error("无法删除所有组织");
    // }else{
    orgNum--;
    addOrgList.splice(num,1);
    // }
    setAcctInfoTables();
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
      content: '/inaction/account/roleDialog.html',
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

//提交成功
function submitSuccess(personnelId){       
  var url = "";
  $http.get('/user/getPsnUser', {    
      personnelId: personnelId,
      userType: "1"
    }, function (data) {
      url = "editMainAccount.html?acctId="+ data.tbAcct.acctId +"&orgFullName=" + encodeURI(orgFullName) + "&orgTreeId=" + orgTreeId + 
              "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&hType=mh" + "&orgTreeName="+encodeURI(orgTreeName)+
              "&curOrgId="+curOrgId+"&curOrgTreeId="+curOrgTreeId;
      loading.screenMaskDisable('container');
      window.location.href = url;
    }, function (err) {
      loading.screenMaskDisable('container');
    })
}

getAcctUser(personnelId);
noSelectUserInfo();






