var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgFullName = getQueryString('orgFullName');
var hType = getQueryString('hType');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');
var tabPage = getQueryString('tabPage');
var acctId = getQueryString('acctId');
var orgTreeName = getQueryString('orgTreeName');
var curOrgId = getQueryString('curOrgId');
var curOrgTreeId = getQueryString('curOrgTreeId');
var addToEditFlag = getQueryString('addToEditFlag'); //判断是从新增账号页面跳转过来的

var slaveAcctCount; //从账号数量
var personnelId;
var orgTable;
var orgNum = 0;
var slaveOrgIdList = [];
// var psw;
var roleList = [];      //需要上传的角色列表
var userRoleList = [];      //用户已有角色列表
var formValidate;
var acctInfoList = [];
var toastr = window.top.toastr;
var cerTypeList = window.top.dictionaryData.certType();
var statusCdList = window.top.dictionaryData.statusCd();
var relTypeName = window.top.relTypeName;
var relTypeVal = "";
var loading = parent.loading;

loading.screenMaskEnable('container');

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

//获取人员头像
function getPsnImage(){
  $http.get('/psnImage/getPsnImage', {
      personnelId: personnelId
  }, function (data) {
      if(data != null){
          imgUrl =  "data:image/png;base64," + data.image;
          $("#psnImg").attr("src",imgUrl);
      }
  }, function (err) {
      loading.screenMaskDisable('container');
  })
}

function getUser(acctId) {           //查看并编辑主账号
    var date = new Date();
    $http.get('/user/getUser', {   
        acctId: acctId,
        userType: "1"
    }, function (data) {
        personnelId = data.personnelId;
        orgNum = data.acctOrgVoPage.records.length;
        slaveAcctCount = data.slaveAcctOrgVoPage.records.length;
        initAcctInfo(data);
        initAcctInfoCheck(data);
        initEditUserInfo(data);
        setAcctInfoTables();
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

//初始化主从账号信息
function initAcctInfo(results){
   var acct = results.acctOrgVoPage.records;
   var slave = results.slaveAcctOrgVoPage.records;
   for(var k=0;k<acct.length;k++){
      if(acct[k].orgId == orgId){
          addToEditFlag = 0;
          break;
      }
   }
   acctInfoList = [];
   for(var i=0;i<acct.length;i++){
      if(acct[i].orgTreeId == orgTreeId || orgTreeId == 1){
          if(acct[i].orgTreeId == curOrgTreeId && acct[i].orgId == curOrgId){ //当前选择的组织放到数组最前面（高亮）
              acctInfoList.unshift({"acct":acct[i],"slaveAcct":[]});
          }else{
              acctInfoList.push({"acct":acct[i],"slaveAcct":[]});
          }
      }
   }
   for(var i=0;i<acctInfoList.length;i++){
      for(var j=0;j<slave.length;j++){
         if(acctInfoList[i].acct.orgTreeId == slave[j].orgTreeId && acctInfoList[i].acct.orgId == slave[j].orgId){
            acctInfoList[i].slaveAcct.push(slave[j]);
         }
      }
   }
  //  console.log(acctInfoList);
}

//展示主从账号信息
function setAcctInfoTables(){
    var acctHtml = ""; 
    var relType;
    var currentId;
    $("#acctOrgDiv").empty();
    for(var i=0;i<acctInfoList.length;i++){
        relType = "";
        for(var j=0;j<relTypeName.length;j++){
            if(acctInfoList[i].acct.relType == relTypeName[j].itemValue){
              relType = relTypeName[j].itemCnname;
              break;
            }
        }
        acctHtml += "<div id='activeDiv_"+i+"' style='margin-top:2%;margin-left:1.3%;width:100%;'>"+
                      "<div class='curDiv' style='padding:10px 0;'>"+
                        "<span class='pngDot'></span>"+
                        "<span class='Name Gray3' style='margin-left:1.5%;' id='orgTreeName_"+i+"'>"+acctInfoList[i].acct.orgTreeName+"</span>"+
                        "<span class='Tag' style='cursor:pointer;' title='"+acctInfoList[i].acct.fullName+"' id='orgName_"+i+"'>"+acctInfoList[i].acct.orgName+"</span>"+
                        "<span class='Tag' style='cursor:pointer;' id='relTypeName_"+i+"'>"+relType+"</span>"+
                        "<span id='editBtn_"+i+"' title='组织编辑' onclick='openEditOrgDialog("+acctInfoList[i].acct.relType+","+acctInfoList[i].acct.acctOrgRelId+","+acctInfoList[i].acct.orgTreeId+")' class='icon icon-edit'></span>"+
                        "<div class='rightDiv' style='float:right;margin-right:1.5%;'>"+
                            "<span class='fright FunctionBtn' style='float:none;'>"+
                                "<a class='BtnDel' style='cursor:pointer;' onclick='deleteOrg("+acctInfoList[i].acct.orgId+","+acctInfoList[i].acct.orgTreeId+")' id='delBtn_"+i+"'><span></span>删除组织关系</a></span>"+
                            "<a class='button-add' onclick='addSlaveBtnClick("+acctInfoList[i].acct.orgId+","+i+","+acctInfoList[i].acct.orgTreeId+","+acctInfoList[i].acct.relType+")'><span class='fa fa-plus-circle icon-add'></span>创建从账号</a></div></div>"+ 
                    "<div id='table-container_"+i+"' style='width: 100%; font-size: 14px; overflow: hidden;'>"+
                        "<table id='orgTable_"+i+"' class='stripe' width='100%'></table></div></div>"; 
        if(acctInfoList[i].acct.orgTreeId == curOrgTreeId && acctInfoList[i].acct.orgId == curOrgId){
            currentId = i;
        }
    }
    $("#acctOrgDiv").append(acctHtml);
    // $("#orgTreeName_"+currentId).attr("class","currentOrgLable");
    $("#activeDiv_"+currentId).attr("class","ActiveArea fleft WpFull");
    $("#orgName_"+currentId).attr("class","Tag BgBlue");
    $("#relTypeName_"+currentId).attr("class","Tag BgBlue");

    for(var i=0;i<acctInfoList.length;i++){
        if(acctInfoList[i].slaveAcct.length != 0){
            $("#delBtn_"+i).css("display","none");
            $("#table-container_"+i).css("padding","10px 10px 30px 10px");
            $("#orgTable_"+i).DataTable({
              'data': acctInfoList[i].slaveAcct,
              'destroy':true,
              'searching': false,
              'autoWidth': false,
              'ordering': true,
              'paging': false,
              'info': false,
              "scrollY": "375px",
              'scrollCollapse': true,
              'columns': [
                  { 'data': null, 'title': '序号', 'className': 'row-number' ,
                      'render': function (data, type, row, meta) {
                          return meta.row + 1 + meta.settings._iDisplayStart;
                      }
                  },
                  { 'data': "slaveAcct", 'title': '从账号', 'className': 'row-acc' ,
                  'render': function (data, type, row, meta) {
                      return '<a title="'+ row.slaveAcct +'" href="editSubAccount.html?curOrgId='+curOrgId+'&curOrgTreeId='+curOrgTreeId+
                              '&orgTreeId=' + orgTreeId + '&curSlaveOrgTreeId='+row.orgTreeId+'&toMainType=' + hType +'&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +
                              '&curSlaveOrgTreeName='+encodeURI(row.orgTreeName)+'&hType=th&mainAcctId='+acctId+'&acctId='+row.slaveAcctId+'&statusCd='+row.statusCd+'">'+row.slaveAcct+'</a>';
                  }
                },
                  { 'data': "slaveAcctType", 'title': '类型', 'className': 'row-acctype' },
                  { 'data': "systemName", 'title': '系统', 'className': 'row-system'},
                  { 'data': "statusCd", 'title': '状态', 'className': 'row-state' ,
                    'render': function (data, type, row, meta) {
                      return "生效";
                    }
                  },
                  {'data': "orgId", 'title': '操作', 'className': 'row-delete' ,
                    'render': function (data, type, row, meta) {
                        return "<a class='Icon IconDel' href='javascript:void(0);' id='delOrgBtn' title='删除' onclick='deleteTbSubAcct("+row.slaveAcctId+")'></a>"; 
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
        }else{
            // $("#editBtn_"+i).attr("class","delBtn");
            // $("#editBtn_"+i).attr("onclick","deleteOrg("+acctInfoList[i].acct.orgId+","+acctInfoList[i].acct.orgTreeId+")");
            // $("#editBtn_"+i).text("删除组织关系");
            $("#editBtn_"+i).css("display","none");
        }
    }
    loading.screenMaskDisable('container');
}

function deleteTbSubAcct(slaveAcctId){     //删除从账号
  parent.layer.confirm('此操作将删除从账号, 是否继续?', {
      icon: 0,
      title: '提示',
      btn: ['确定','取消']
  }, function(index, layero){
      parent.layer.close(index);
      $.ajax({
          url: '/slaveAcct/delTbSlaveAcct?&slaveAcctId='+parseInt(slaveAcctId),
          type: 'DELETE',
          contentType: "application/json",
          dataType:"json",
          success: function (data) { //返回json结果
              if(data.state === 1000){
                  toastr.success(data.message);
                  refreshTb(acctId);
              }else{
                  toastr.error(data.message);
              }
          },
          error:function(err){
            toastr.error('删除失败');
          }
        });
    }, function(){
  }); 
}

function initEditUserInfo(results){     //初始化用户信息(编辑)
  var slaveOrg =  results.slaveAcctOrgVoPage.records;
  $('#psnName').val(results.psnName);
  $('#psnNum').val(results.psnNbr);
  $('#mobile').val(results.mobilePhone);
  $('#email').val(results.eamil);
  $('#cerNo').val(results.certNo);
  $('#acct').val(results.tbAcct.acct);
  // $('#defaultPsw').val(results.tbAcct.password);
  setDate(results.tbAcct.enableDate,results.tbAcct.disableDate);

  // psw = results.tbAcct.password;

  $('#role').addTag(results.tbRolesList);

  for(var i=0;i<statusCdList.length;i++){
    if(results.tbAcct.statusCd === statusCdList.itemValue){
        $("#statusCd").append("<option value='" + statusCdList[i].itemValue + "' selected>" + statusCdList[i].itemCnname +"</option>");
    }else{
        $("#statusCd").append("<option value='" + statusCdList[i].itemValue + "'>" + statusCdList[i].itemCnname +"</option>");
    }
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

  for(var i = 0;i < slaveOrg.length;i++){
      slaveOrgIdList.push(slaveOrg[i].orgId);
  }
}

function initAcctInfoCheck(results){     //初始化用户信息(查看)

  getPsnImage();
  
  isNull("#psnNameLable",results.psnName);
  isNull("#mobileLable",results.mobilePhone);
  isNull("#emailLable",results.eamil);
  isNull("#acctLable",results.tbAcct.acct);
  isNull("#psnNumLable",results.psnNbr);
  isNull("#cerNoLable",results.certNo);
  isNull("#effectDateLable",results.tbAcct.enableDate);
  isNull("#invalidDateLable",results.tbAcct.disableDate);
  
  for(var i=0;i<cerTypeList.length;i++){
    if(results.certType === cerTypeList[i].itemValue){
      $("#cerNoTxt").text(cerTypeList[i].itemCnname+"号码：");
      break;
    }
  }

  for(var i = 0; i <results.tbRolesList.length; i++){
    if(i != 0 && i%3 == 0){
      $("#roleTab").append($("<br><span class='roleTag'>"+results.tbRolesList[i].roleName+"</span>"));
    }else if(i != 0 && i%3 != 0){
      $("#roleTab").append($("<span class='roleTag'>"+results.tbRolesList[i].roleName+"</span>"));
    }else{
      $("#roleTab").append($("<span class='roleTag'>"+results.tbRolesList[i].roleName+"</span>"));
    }
  }
  userRoleList = results.tbRolesList;
  
  window.localStorage.setItem('userRoleList',JSON.stringify(userRoleList));
}

function updateAcct(){      //编辑主账号
    if(!formValidate.isAllPass())
        return;

    if(roleList.length == 0){
      roleList = userRoleList;
    }

    var editFormAcctVo = {
      "acct": $('#acct').val(),
      "acctId": acctId,
      "disableDate": $('#invalidDate').val(),
      "enableDate": $('#effectDate').val(),
      "password": "4A@12345",
      "personnelId": personnelId,
      "statusCd": $("#statusCd").val(), 
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
  parent.layer.confirm('此操作将删除主账号, 是否继续?', {
    icon: 0,
    title: '提示',
    btn: ['确定','取消']
}, function(index, layero){
    parent.layer.close(index);
    if(slaveAcctCount == 0){
      $.ajax({
        url: '/acct/deleteTbAcct?&acctId='+parseInt(acctId),
        type: 'DELETE',
        contentType: "application/json",
        dataType:"json",
        success: function (data) { //返回json结果
          if(data.state === 1000){
            toastr.success(data.message);
            deleteSuccess();
          }else{
            toastr.error(data.message);
          }
        },
        error:function(err){
          toastr.error('删除失败！');
        }
      });
    }else{
      toastr.error('主账号存在关联的从账号，删除主账号失败！');
    }
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

function addAcctOrg(orgId,orgTreeId,relTypeVal){ //编辑时新增组织
  var tbAccountOrgRel={
    "acctId": acctId,
    "orgId": parseInt(orgId),
    "orgTreeId": parseInt(orgTreeId),
    "relType": relTypeVal
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

//编辑时修改组织
function updateAcctOrg(orgId,orgTreeId,relTypeVal,acctOrgRelId){ 
    $http.put('/acct/updateAcctOrg', JSON.stringify({  
      acctId : acctId,
      acctOrgRelId : acctOrgRelId,
      orgId : orgId,
      orgTreeId : orgTreeId,
      relType : relTypeVal
    }), function (message) {
        refreshTb(acctId);
        toastr.success("保存成功！");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}

function refreshTb(acctId) {           //新增组织后刷新组织表格
  var date = new Date();
  $http.get('/user/getUser', {   
      acctId: acctId,
      userType: "1",
      _:date.getTime()
  }, function (data) {
      slaveAcctCount = data.slaveAcctOrgVoPage.records.length;
      initAcctInfo(data);
      setAcctInfoTables();
  }, function (err) {

  })
}

function addSlaveBtnClick(acctOrgRelId,id,slaveOrgTreeId,relTypeVal){      //点击新增从账号
    var sFullName = $("#orgName_"+id).attr("title");
    var sOrgName = $("#orgName_"+id).text();
    var treeName = $("#orgTreeName_"+id).text();
    var url = 'addSubAccount.html?curOrgId='+curOrgId+'&curOrgTreeId='+curOrgTreeId+'&orgTreeId='+orgTreeId+'&hType=th&personnelId='+personnelId + '&slaveOrgTreeId=' + slaveOrgTreeId +
                      '&mainAcctId='+ acctId +'&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +'&toMainType=' + hType + '&curSlaveOrgName='+sOrgName+
                      '&fullName=' + encodeURI(sFullName) + '&acctOrgRelId=' + acctOrgRelId + '&orgTreeName=' + encodeURI(treeName)+'&relTypeVal='+relTypeVal;
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

function isNull(s,r){    //判断是否为null
    if(r == null){
      $(s).text("");
    }else{
      $(s).text(r);
    }
}

//删除组织
function deleteOrg(orgId,orgTreeId){
    if(orgNum == 1){
      toastr.error("无法删除所有组织");
    }else{
        parent.layer.confirm('是否删除该组织？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
          removeAcctOrg(orgId,orgTreeId);
          parent.layer.close(index);
        }, function(){
      
        });
    }
}

function cancel() {   //取消按钮
  var url = '';
  if(hType != "uh" && hType != "null"){  //返回list.html
    url = "list.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;
  }else{
    url = "/inaction/user/edit.html?orgTreeName="+encodeURI(orgTreeName)+"&orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName) + "&id=" + orgId + 
    "&personnelId=" + personnelId + "&orgRootId=" + orgRootId + "&tabPage=" + tabPage;
  }
  window.location.href = url;
}   

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
  $('#role').tagsInput();
}

//角色选择
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
          $('#role',window.parent.document).importTags(checkNode);
          $('.ui-tips-error').css('display', 'none');
          window.localStorage.setItem('userRoleList',JSON.stringify(checkRole));
          roleList = checkRole;
          parent.layer.close(index);
      },
      btn2: function(index, layero){},
      cancel: function(){}
  });
}

//组织选择(新增)
function openOrgDialog() {
  parent.layer.open({
      type: 2,
      title: '选择组织',
      shadeClose: true,
      shade: 0.8,
      area: ['40%', '80%'],
      maxmin: true,
      content: '/inaction/account/orgDialog.html?orgTreeId='+orgTreeId+'&relType=30'+'&addToEditFlag='+addToEditFlag,
      btn: ['确认', '取消'],
      yes: function(index, layero){
          //获取layer iframe对象
          var iframeWin = parent.window[layero.find('iframe')[0].name];
          var orgIdSelect = iframeWin.orgIdSelect;
          var orgTreeId = iframeWin.orgTreeId;
          var relTypeVal = iframeWin.relTypeVal;
          parent.layer.close(index);
          addAcctOrg(orgIdSelect,orgTreeId,relTypeVal);
      },
      btn2: function(index, layero){},
      cancel: function(){}
  });
}

//组织选择(编辑)
function openEditOrgDialog(val,acctOrgRelId,orgTreeId) {
  parent.layer.open({
      type: 2,
      title: '选择组织',
      shadeClose: true,
      shade: 0.8,
      area: ['40%', '80%'],
      maxmin: true,
      content: '/inaction/account/orgDialog.html?orgTreeId='+orgTreeId+'&relType='+val,
      btn: ['确认', '取消'],
      yes: function(index, layero){
          //获取layer iframe对象
          var iframeWin = parent.window[layero.find('iframe')[0].name];
          var orgIdSelect = iframeWin.orgIdSelect;
          var orgTreeId = iframeWin.orgTreeId;
          var relTypeVal = iframeWin.relTypeVal;
          parent.layer.close(index);
          updateAcctOrg(orgIdSelect,orgTreeId,relTypeVal,acctOrgRelId);
      },
      btn2: function(index, layero){},
      cancel: function(){}
  });
}

//提交成功
function submitSuccess(){     
    var url = "editMainAccount.html?curOrgId="+curOrgId+"&curOrgTreeId="+curOrgTreeId+"&acctId="+ acctId +"&orgFullName=" + encodeURI(orgFullName) + "&orgTreeId=" + orgTreeId + 
                  "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&hType="+ hType + "&orgTreeName="+encodeURI(orgTreeName);
    window.location.href = url;
}

//删除成功
function deleteSuccess(){
    var url = "list.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;
    window.location.href = url;
}

// $("#defaultPsw").focus(function (){    //默认密码输入框获得焦点
//   if($("#defaultPsw").attr("type") == "password"){
//     $("#defaultPsw").val('');
//     $("#defaultPsw").attr("type","text");
//   }
// })

// $("#defaultPsw").blur(function (){     //默认密码输入框失去焦点
//   if($("#defaultPsw").val() == ''){
//     $("#defaultPsw").val(psw);
//     $("#defaultPsw").attr("type","password");
//     formValidate.isAllPass($('#defaultPsw'));
//   }
// })

getUser(acctId);
noSelectUserInfo();






