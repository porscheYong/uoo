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

var personnelId;
var orgTable;
var orgNum = 0;
var slaveOrgIdList = [];
var psw;
var roleList = [];      //需要上传的角色列表
var userRoleList = [];      //用户已有角色列表
var formValidate;
var acctInfoList = [];
var toastr = window.top.toastr;
var cerTypeList = window.top.dictionaryData.certType();
var statusCdList = window.top.dictionaryData.statusCd();

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
        initAcctInfo(data);
        initAcctInfoCheck(data);
        initEditUserInfo(data);
        // initOrgTable(data.acctOrgVoPage.records);
        // initSlaveOrgTable(data.slaveAcctOrgVoPage.records);
        setAcctInfoTables();
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

//初始化主从账号信息
function initAcctInfo(results){
   var acct = results.acctOrgVoPage.records;
   var slave = results.slaveAcctOrgVoPage.records;
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
   console.log(acctInfoList);
}

//展示主从账号信息
function setAcctInfoTables(){
    var acctHtml = ""; 
    var currentId;
    $("#acctOrgDiv").empty();
    for(var i=0;i<acctInfoList.length;i++){
        acctHtml += "<div style='margin-top:5%;margin-left:-3.3%;'>"+
                        "<span style='font-size:16px;font-weight:600;'>("+(i+1)+")归属组织信息</span>"+
                        "<span class='infoLable' id='orgTreeName_"+i+"'>"+acctInfoList[i].acct.orgTreeName+"</span>"+
                        "<span class='infoLable' title='"+acctInfoList[i].acct.fullName+"' id='orgName_"+i+"'>"+acctInfoList[i].acct.orgName+"</span>"+
                        "<span class='infoBtn' onclick='' id='editBtn_"+i+"'>修改归属组织信息</span>"+
                        "<span class='infoBtn' onclick='addSlaveBtnClick("+acctInfoList[i].acct.acctOrgRelId+","+i+","+acctInfoList[i].acct.orgTreeId+")'>创建从账号</span></div>"+ 
                    "<div id='table-container' style='width: 100%; font-size: 14px; overflow: hidden;margin-left:-3.3%;'>"+
                        "<table id='orgTable_"+i+"' class='stripe' width='100%'></table></div>"; 
        if(acctInfoList[i].acct.orgTreeId == curOrgTreeId && acctInfoList[i].acct.orgId == curOrgId){
            currentId = i;
        }
    }
    $("#acctOrgDiv").append(acctHtml);
    $("#orgTreeName_"+currentId).attr("class","currentOrgLable");
    $("#orgName_"+currentId).attr("class","currentOrgLable");

    for(var i=0;i<acctInfoList.length;i++){
        if(acctInfoList[i].slaveAcct.length != 0){
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
                              '&orgTreeId=' + orgTreeId + '&toMainType=' + hType +'&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +
                              '&hType=th&mainAcctId='+acctId+'&acctId='+row.slaveAcctId+'&statusCd='+row.statusCd+'">'+row.slaveAcct+'</a>';
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
            $("#editBtn_"+i).attr("class","delBtn");
            $("#editBtn_"+i).attr("onclick","deleteOrg("+acctInfoList[i].acct.orgId+","+acctInfoList[i].acct.orgTreeId+")");
            $("#editBtn_"+i).text("删除组织关系");
        }
    }
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
  $('#defaultPsw').val(results.tbAcct.password);
  setDate(results.tbAcct.enableDate,results.tbAcct.disableDate);

  psw = results.tbAcct.password;

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
      "password": $('#defaultPsw').val(),
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
        deleteSuccess();
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
      initAcctInfo(data);
      setAcctInfoTables();
  }, function (err) {

  })
}

function addSlaveBtnClick(acctOrgRelId,id,slaveOrgTreeId){      //点击新增从账号
    var sFullName = $("#orgName_"+id).attr("title");
    var treeName = $("#orgTreeName_"+id).text();
    var url = 'addSubAccount.html?curOrgId='+curOrgId+'&curOrgTreeId='+curOrgTreeId+'&orgTreeId='+orgTreeId+'&hType=th&personnelId='+personnelId + '&slaveOrgTreeId=' + slaveOrgTreeId +
                      '&mainAcctId='+ acctId +'&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +'&toMainType=' + hType +
                      '&fullName=' + encodeURI(sFullName) + '&acctOrgRelId=' + acctOrgRelId + '&orgTreeName=' + encodeURI(treeName);
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
      toastr.warning("无法删除所有组织");
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
  if(hType != "uh"){  //返回list.html
    url = "list.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;
  }else{
    url = "/inaction/user/edit.html?orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName) + "&id=" + orgId + 
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
      content: 'roleDialog.html',
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

//组织选择
function openOrgDialog() {
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
          var orgTreeId = iframeWin.orgTreeId;
          parent.layer.close(index);
          addAcctOrg(orgIdSelect,orgTreeId);
      },
      btn2: function(index, layero){},
      cancel: function(){}
  });
}

//提交成功
function submitSuccess(){     
    var url = "editMainAccount.html?acctId="+ acctId +"&orgFullName=" + encodeURI(orgFullName) + "&orgTreeId=" + orgTreeId + 
                  "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&hType=mh" + "&orgTreeName="+encodeURI(orgTreeName);
    window.location.href = url;
}

//删除成功
function deleteSuccess(){
    var url = "list.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;
    window.location.href = url;
}

$("#defaultPsw").focus(function (){    //默认密码输入框获得焦点
  if($("#defaultPsw").attr("type") == "password"){
    $("#defaultPsw").val('');
    $("#defaultPsw").attr("type","text");
  }
})

$("#defaultPsw").blur(function (){     //默认密码输入框失去焦点
  if($("#defaultPsw").val() == ''){
    $("#defaultPsw").val(psw);
    $("#defaultPsw").attr("type","password");
    formValidate.isAllPass($('#defaultPsw'));
  }
})

getUser(acctId);
noSelectUserInfo();






