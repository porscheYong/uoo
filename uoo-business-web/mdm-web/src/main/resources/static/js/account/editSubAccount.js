var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var mainAcctId = getQueryString('mainAcctId');
var hType = getQueryString('hType');
var toMainType = getQueryString('toMainType');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');
var hostId = getQueryString('acctOrgRelId');
var fullName = getQueryString('fullName');
var tabPage = getQueryString('tabPage');
var acctId = getQueryString('acctId');
var statusCd = getQueryString('statusCd');
var personnelId = getQueryString('personnelId');
var orgTreeName = getQueryString('orgTreeName');

var table;
var slaveTable;
var acctOrgRelId = 0;
var slaveOrgList = [];
var slaveAcctId;
var acctExtId = null;
var isChecked = 0;
var psw;
var roleList = [];      //需要上传的角色列表
var userRoleList = [];      //用户已有角色列表
var toastr = window.top.toastr;
var resourceObjId = null;
var cerTypeList = window.top.dictionaryData.certType();

if(hostId != null){
    acctOrgRelId = hostId;
}

$('#accType').get(0).selectedIndex=0;  //判断账号类型

if(statusCd == "1000"){                //判断状态
    $('#statusCd').get(0).selectedIndex=0;
  }else{
    $('#statusCd').get(0).selectedIndex=1;
  }

  seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var addAcctForm = $('#addAcctForm');
    formValidate = new Validate(addAcctForm);
    formValidate.immediate();
  });

  // lulu ui tips插件
seajs.use('/vendors/lulu/js/common/ui/Tips', function () {
    $('#defaultPsw').tips({
        align: 'right'
    });
});
  
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

function getSubUser(acctId) {       //查看并编辑从账号    
    var date = new Date();        
    $http.get('/user/getUser', {  
        acctId: acctId,
        userType: "2",
        _:date.getTime()
    }, function (data) {
        if(data.tbAcctExt != null){
            acctExtId = data.tbAcctExt.acctExtId;
        }
        $('#acctInfo').css("display","block");
        personnelId = data.personnelId;
        acctOrgRelId = data.tbSlaveAcct.acctOrgRelId;
        slaveAcctId = data.tbSlaveAcct.slaveAcctId;
        initOrgTable(data.acctOrgVoList);
        initSubAcctInfoCheck(data);
        initSubInfo(data);
        getSysSelect();
    }, function (err) {

    })
}

function setDate(eDate,bDate){    //设置时间
    var date,year,month,day,nowDate,toDate;
    if(eDate == null && bDate == null){
      date = new Date();
      year = date.getFullYear();
      month = date.getMonth() + 1;
      day = date.getDate();
      if (month < 10) {
          month = "0" + month;
      }
      if (day < 10) {
          day = "0" + day;
      }
      nowDate = year + "-" + month + "-" + day;
      toDate = (year+10) + "-" + month + "-" + day;
    }else{
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

function getAcctOrg(){          //获取从账号可选组织列表(添加组织)
    $http.get('/user/getAcctOrgByPsnId', {    
        personnelId: personnelId,
        resourceObjId: resourceObjId
      }, function (data) {
        slaveOrgList = [];
        for(var i=0;i<data.length;i++){
            slaveOrgList.push({"orgTreeName":data[i].orgTreeName,"fullName":data[i].fullName});
        }
        initAcctOrgTable(data);
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

function initOrgTable(results){
    table = $("#orgTable").DataTable({
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
            return 1;
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
                return "<a class='Icon IconDel' href='javascript:void(0);' id='delOrgBtn' title='删除' onclick='deleteOrg()'></a>";
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

  function initAcctOrgTable(results){
    var num = 1;
    slaveTable = $("#acctOrgTable").DataTable({
      'data': results,
      'destroy':true,
      'searching': false,
      'autoWidth': false,
      'ordering': true,
      'paging': false,
      'info': false,
      "scrollY": "240px",
      'columns': [
          { 'data': "id", 'title': '序号', 'className': 'row-t1' ,
            'render': function (data, type, row, meta) {
                return num++;
            }
          },
          { 'data': "orgTreeName", 'title': '组织树', 'className': 'row-t2'},
          { 'data': "fullName", 'title': '可选组织', 'className': 'row-t3',
          'render': function (data, type, row, meta) {
              return "<a href='javascript:void(0);' title='"+row.fullName+"' onclick='saveSlaveOrg("+ num + ","+ row.acctOrgRelId + ")'>"+ row.fullName +'</a>'
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

function initSubInfo(results){  //编辑时初始化信息
    $('#psnName').val(results.psnName);
    $('#psnNum').val(results.psnCode);
    $('#mobile').val(results.mobilePhone);
    $('#email').val(results.eamil);
    $('#cerNo').val(results.certNo);
    $('#acct').val(results.tbSlaveAcct.slaveAcct);
    $('#defaultPsw').val(results.tbSlaveAcct.password);
    setDate(results.tbSlaveAcct.enableDate,results.tbSlaveAcct.disableDate);
    $('#statusCd').val("生效");

    $('#role').addTag(results.tbRolesList);

    for(var i=0;i<cerTypeList.length;i++){
        var select = "";
        if(results.certType === cerTypeList[i].itemValue){
          $("#cerType").append("<option value='" + cerTypeList[i].itemValue + "' selected>" + cerTypeList[i].itemCnname +"</option>");
        }
        if(results.tbAcctExt != null && results.tbAcctExt.certType === cerTypeList[i].itemValue){
            select = "selected";
        }
        $("#extCerType").append("<option value='" + cerTypeList[i].itemValue + "' " + select + ">" + cerTypeList[i].itemCnname +"</option>");     
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $("#cerType").selectMatch();
        $("#extCerType").selectMatch();
    });

    resourceObjId = results.tbSlaveAcct.resourceObjId;
    psw = results.tbSlaveAcct.password;

     //扩展信息
    if(results.tbAcctExt != null){
        $("#extCheckBox").attr("checked",true);
        $("#extInfo").css("border-color","#00A4FF"); 
        $("#extCheckBox").attr("value","1");
        isChecked = 1;

        $('#extCerNo').val(results.tbAcctExt.certNo);
        $('#extCerType').get(0).selectedIndex = parseInt(results.tbAcctExt.certType) - 1;
        $('#extMobile').val(results.tbAcctExt.contactWay);
        $('#extName').val(results.tbAcctExt.name);
        $('#extEmail').val(results.tbAcctExt.workEmail);
    }
}

function initSubAcctInfoCheck(results){       //初始化从账号信息(查看)
    getPsnImage();
    isNull("#psnNameLable",results.psnName);
    isNull("#mobileLable",results.mobilePhone);
    isNull("#emailLable",results.eamil);
    isNull("#acctLable",results.tbSlaveAcct.slaveAcct);
    isNull("#psnNumLable",results.psnNbr);
    isNull("#cerNoLable",results.certNo);
    isNull("#effectDateLable",results.tbSlaveAcct.enableDate);
    isNull("#invalidDateLable",results.tbSlaveAcct.disableDate);

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

function updateTbSlaveAcct(){       //更新从账号信息
    if(!formValidate.isAllPass()){
        $("#acctInfo").css("display","none");
        $("#editAcctPanel").css("display","block");
        $('#acctEditButton').css("display","none");
        return;
    }
    var slaveAcctType = $('#accType').get(0).selectedIndex + 1;
    var subStatusCd = $('#statusCd').get(0).selectedIndex*100 + 1000;
    var certType = $('#extCerType').get(0).selectedIndex + 1;
    var tbAcctExt = hasExtInfo(certType);

    if(roleList.length == 0){
        roleList = userRoleList;
    }

    var editFormSlaveAcctVo = {
        "acctOrgRelId": acctOrgRelId,
        "disableDate": $('#invalidDate').val(),
        "enableDate": $('#effectDate').val(),
        "password": $('#defaultPsw').val(),
        "personnelId": parseInt(personnelId),
        "resourceObjId": resourceObjId,
        "rolesList": roleList,
        "slaveAcct": $('#acct').val(),
        "slaveAcctId": slaveAcctId,
        "slaveAcctType": slaveAcctType.toString(),
        "statusCd": subStatusCd.toString(),
        "tbAcctExt": tbAcctExt,
        "userType": "2"
    };
    
    $.ajax({
        url: '/slaveAcct/updateTbSlaveAcct',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(editFormSlaveAcctVo),
        dataType:"JSON",
        success: function (data) { //返回json结果
            if(data.state === 1000){
                toastr.success(data.message);
                submitToOther();
            }else{
                toastr.error(data.message);
            }
        },
        error:function(err){
          toastr.error('保存失败');
        }
      });
}

function deleteTbSubAcct(){     //删除从账号
    parent.layer.confirm('此操作将删除从账号, 是否继续?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        $.ajax({
            url: '/slaveAcct/delTbSlaveAcct?&slaveAcctId='+parseInt(acctId),
            type: 'DELETE',
            contentType: "application/json",
            dataType:"json",
            success: function (data) { //返回json结果
                if(data.state === 1000){
                    toastr.success(data.message);
                    submitToOther();
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

function getSysSelect(){   //获取应用系统下拉列表
    $http.get('/permission/businessSystem/listBusinessSystem/'+orgTreeId, 
    {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            if(resourceObjId == null){
                var select = i === 0? 'selected' : '';
                option += "<option value='" + data[i].businessSystemId + "' " + select + ">" + data[i].systemName +"</option>";
                resourceObjId = data[0].businessSystemId;   
            }else{
                if(resourceObjId == data[i].businessSystemId){
                    option += "<option value='" + data[i].businessSystemId + "' selected>" + data[i].systemName +"</option>";
                }else{
                    option += "<option value='" + data[i].businessSystemId + "'>" + data[i].systemName +"</option>";
                }
            }     
        }
        $('#system').append(option);
        seajs.use('/vendors/lulu/js/common/ui/Select', function () {
            $('#system').selectMatch();
        });
        $('#system').unbind('change').bind('change', function (event) {
            resourceObjId = event.target.options[event.target.options.selectedIndex].value;
            console.log(resourceObjId);
        })
    }, function (err) {
    })
}

function  hasExtInfo(certType){  //判断是否需要扩展信息
    var tbAcctExt;
    if(isChecked == 0){
        tbAcctExt = null;
    }else{
        tbAcctExt = {
            "acctExtId":acctExtId,
            "certNo": $('#extCerNo').val(),
            "certType": certType.toString(),
            "contactWay": $('#extMobile').val(),
            "name": $('#extName').val(),
            "workEmail": $('#extEmail').val()
          };
    }
    return tbAcctExt;
}

function saveSlaveOrg(id,hostId){  //获取acctOrgRelId
    acctOrgRelId = hostId;
    console.log(id);
    console.log(slaveOrgList);
    $('#slaveOrgModal').modal('hide');
    initOrgTable([{"orgTreeName":slaveOrgList[id-2].orgTreeName,"fullName":slaveOrgList[id-2].fullName}]);
    $('#addText').text('更换归属组织');
}

function deleteOrg(){
    initOrgTable({"orgTreeName":"","fullName":""});
    acctOrgRelId = 0;
    $('#addText').text('新增归属组织');
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


function btnSubmit(){       //提交
    if(acctOrgRelId != 0){
        updateTbSlaveAcct();
    }else{
        toastr.error("组织不能为空");
    }
}

function extInfoFade(){     //点击复选框
    var extCheckBox = $("#extCheckBox");
    if(extCheckBox.attr("value") == "0"){  
        extCheckBox.attr("value","1")
        $("#extInfo").css("border-color","#00A4FF"); 
        isChecked = 1;
        if(isIE8){
            $(".ui-checkbox").css("background-position","0px -40px");
        }
    }else{
        extCheckBox.attr("value","0")
        $("#extInfo").css("border-color","#b6b6b6"); 
        isChecked = 0;
        if(isIE8){
            $(".ui-checkbox").css("background-position","0px 0px");
        }
    }
}

function isNull(s,r){    //判断是否为null
    if(r == null){
      $(s).text("");
    }else{
      $(s).text(r);
    }
}

function submitToOther(){   //提交或者取消跳转
    var url = "";
    if(hType == "th"){
        url = "editMainAccount.html?orgTreeId=" + orgTreeId + "&hType="+ toMainType +"&opBtn=0&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&acctId=" + mainAcctId;   //跳转主账号编辑界面
    }else if(hType == "mh"){
        url = "list.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;       //跳转主界面
    }else if(hType == "ah"){
        url = "add.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;       //跳转添加界面
    }else{
        url = "/inaction/user/edit.html?orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName) + "&id=" + orgId + 
                                      "&personnelId=" + personnelId + "&orgRootId=" + orgRootId + "&tabPage=" + tabPage;
    }
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


$('#addText').text('更换归属组织');
noSelectUserInfo();
getSubUser(acctId); 



