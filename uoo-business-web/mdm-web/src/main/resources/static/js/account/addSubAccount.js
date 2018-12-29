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
var isEdit;
var acctOrgRelId = 0;
var slaveOrgList = [];
var acctExtId = null;
var isChecked = 0;
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

function getUserInfo(){         //新增从账号
    $http.get('/user/getPsnUser', {    
        personnelId: personnelId,
        userType: "2"
    }, function (data) {
        initOrgTable("");
        initUserInfo(data);
        getSysSelect();
    }, function (data) {
        
    })
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

function initUserInfo(results){   //新增时初始化信息
    $('#psnName').val(results.psnName);
    $('#psnNum').val(results.psnCode);
    $('#mobile').val(results.mobilePhone);
    $('#email').val(results.eamil);
    $('#cerNo').val(results.certNo);
    window.localStorage.setItem('userRoleList',JSON.stringify(''));

    if(fullName != null){
        initOrgTable([{"orgTreeName":orgTreeName,"fullName":fullName}]);
        $('#addText').text('更换归属组织');
    } 
    setDate();

    for(var i=0;i<cerTypeList.length;i++){
        if(results.certType === cerTypeList[i].itemValue){
          $("#cerType").append("<option value='" + cerTypeList[i].itemValue + "' selected>" + cerTypeList[i].itemCnname +"</option>");
        }
        $("#extCerType").append("<option value='" + cerTypeList[i].itemValue + "'>" + cerTypeList[i].itemCnname +"</option>");     
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $("#cerType").selectMatch();
        $("#extCerType").selectMatch();
    });
}

function addTbSlaveAcct(){      //从账号新增
    if(!formValidate.isAllPass())
        return;
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
        "slaveAcctType": slaveAcctType.toString(),
        "statusCd": subStatusCd.toString(),
        "tbAcctExt": tbAcctExt,
        "userType": "2"
    };

    $.ajax({
        url: '/slaveAcct/addTbSlaveAcct',
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
          toastr.error('新增失败');
        }
      });
}

function btnSubmit(){       //提交
    if(acctOrgRelId != 0){
        addTbSlaveAcct();
    }else{
        toastr.error("组织不能为空");
    }
}

function getSysSelect(){   //获取应用系统下拉列表
    $http.get('/permission/businessSystem/listBusinessSystem/'+orgTreeId, 
    {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = i === 0? 'selected' : '';
            option += "<option value='" + data[i].businessSystemId + "' " + select + ">" + data[i].systemName +"</option>";
        }
        resourceObjId = data[0].businessSystemId;   
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
    if(hType == "th"){      //返回主账号编辑页面
        url = "editMainAccount.html?orgTreeId=" + orgTreeId + "&hType="+ toMainType +"&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&acctId=" + mainAcctId;   
    }else if(hType == "mh"){       //返回主界面
        url = "list.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;      
    }else if(hType == "ah"){       //返回新增界面
        url = "add.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;      
    }else{
        url = "/inaction/user/edit.html?orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName) + "&id=" + orgId + 
                                      "&personnelId=" + personnelId + "&orgRootId=" + orgRootId + "&tabPage=" + tabPage;
    }
    window.location.href = url;
}

$('#statusCd').get(0).selectedIndex=0;
noSelectUserInfo();
getUserInfo();



