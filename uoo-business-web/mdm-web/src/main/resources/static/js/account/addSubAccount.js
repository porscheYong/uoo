var acctId = getQueryString('acctId');
var userType = getQueryString('userType');
var statusCd = getQueryString('statusCd');
var title = getQueryString('title');
var opBtn = getQueryString('opBtn');
var personnelId = getQueryString('personnelId');
var table;
var slaveTable;
var isEdit;
var acctHostId = 0;
var slaveOrgList = [];
var slaveAcctId;
var acctExtId = null;
var isChecked = 0;

$('#sub-title').html(title);
$('#cerType').get(0).selectedIndex=0;  //判断证件类型
$('#accTypeTel').get(0).selectedIndex=0;  //判断账号类型
initAcctOrgTable("");

laydate.render({
    elem: '#effectDate' //指定元素
  }); 

laydate.render({
    elem: '#invalidDate' //指定元素
  }); 

if(statusCd == "1000"){                //判断状态
    $('#statusCd').get(0).selectedIndex=0;
  }else{
    $('#statusCd').get(0).selectedIndex=1;
  }


function getSubUser(acctId,userType) {       //查看并编辑从账号            
    $http.get('/user/getUser', {   //http://192.168.58.112:18000/user/getUser
        acctId: acctId,
        userType: userType
    }, function (data) {
        if(data.tbAcctExt != null){
            acctExtId = data.tbAcctExt.acctExtId;
        }
        personnelId = data.personnelId;
        acctHostId = data.tbSlaveAcct.acctHostId;
        slaveAcctId = data.tbSlaveAcct.slaveAcctId;
        initOrgTable(data.acctOrgVoList);
        initSubInfo(data);
    }, function (err) {
        console.log(err)
    })
}

function getUserInfo(){         //新增从账号
    $http.get('/user/getPsnUser', {    
        personnelId: personnelId,
        userType: userType
    }, function (data) {
        initUserInfo(data);
        initOrgTable("");
    }, function (message) {
        alert(message);
        window.history.back();
        console.log(err)
    })
}

function getAcctOrg(){          //获取从账号可选组织列表(添加组织)
    $http.get('/user/getAcctOrgByPsnId', {    
        personnelId: personnelId,
        orgTreeId: "1"
      }, function (data) {
        for(var i=0;i<data.length;i++){
            slaveOrgList.push(data[i].fullName);
        }
        slaveTable.destroy();
        console.log(data);
        initAcctOrgTable(data);
      }, function (err) {
        console.log('error');
        console.log(err)
      })
}

function initOrgTable(results){
    table = $("#orgTable").DataTable({
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
          { 'data': "id", 'title': '序号', 'className': 'row-id' ,
          'render': function (data, type, row, meta) {
            return 1;
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
                return "<a href='javascript:void(0);'  onclick='deleteOrg()'  class='btn btn-default btn-xs'><i class='fa fa-arrow-down'></i> 删除</a>";
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
    var num = 0;
    slaveTable = $("#acctOrgTable").DataTable({
      'data': results,
      'searching': false,
      'autoWidth': false,
      'ordering': true,
      'paging': false,
      'info': false,
      'initComplete': function (settings, json) {
          console.log(settings, json)
      },
      "scrollY": "240px",
      'columns': [
          { 'data': "fullName", 'title': '可选组织', 'className': 'row-id',
          'render': function (data, type, row, meta) {
              num++;
              return "<a href='javascript:void(0);' onclick='saveSlaveOrg("+ num + ","+ row.acctHostId + ")'>"+ row.fullName +'</a>' //"+ row.fullName.toString() + ",
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
    var roldId ='';
    $('#psnNameTel').val(results.psnName);
    $('#psnNumTel').val(results.psnCode);
    $('#mobileTel').val(results.mobilePhone);
    $('#emailTel').val(results.eamil);
    $('#cerNoTel').val(results.certNo);
    $('#acctTel').val(results.tbSlaveAcct.slaveAcct);
    $('#defaultPswTel').val(results.tbSlaveAcct.password);
    $('#effectDate').val(results.tbSlaveAcct.enableDate);
    $('#invalidDate').val(results.tbSlaveAcct.disableDate);
    $('#statusCd').val("生效");
    for(var i = 0; i <results.tbRolesList.length; i++){
        roldId = roldId + "、" + results.tbRolesList[i].roleId;
    }
    if(roldId != ''){
        $('#roleTel').val(roldId.substring(1,roldId.length));
    } 

     //扩展信息
    if(results.tbAcctExt != null){
        $("#extCheckBox").attr("checked",true);
        $("#extInfo").css("border-color","#00A4FF"); 
        $("#extCheckBox").attr("value","1");
        isChecked = 1;

        $('#extCerNoTel').val(results.tbAcctExt.certNo);
        $('#extCerTel').get(0).selectedIndex = parseInt(results.tbAcctExt.certType) - 1;
        $('#extMobileTel').val(results.tbAcctExt.contactWay);
        $('#extNameTel').val(results.tbAcctExt.name);
        $('#extEmailTel').val(results.tbAcctExt.workEmail);
    }
}

function initUserInfo(results){   //新增时初始化信息
    $('#psnNameTel').val(results.psnName);
    $('#psnNumTel').val(results.psnCode);
    $('#mobileTel').val(results.mobilePhone);
    $('#emailTel').val(results.eamil);
    $('#cerNoTel').val(results.certNo);
}

function addTbSlaveAcct(){      //从账号新增
    var slaveAcctType = $('#accTypeTel').get(0).selectedIndex + 1;
    var resourceObjId = $('#systemTel').get(0).selectedIndex + 1;
    var subStatusCd = $('#statusCd').get(0).selectedIndex*100 + 1000;
    var certType = $('#extCerTel').get(0).selectedIndex + 1;
    var tbAcctExt = hasExtInfo(certType);

    var editFormSlaveAcctVo = {
        "acctHostId": acctHostId,
        "disableDate": $('#invalidDate').val(),
        "enableDate": $('#effectDate').val(),
        "password": $('#defaultPswTel').val(),
        "personnelId": parseInt(personnelId),
        "resourceObjId": resourceObjId,
        "rolesList": [
          {
            "roleId": parseInt($('#roleTel').val()),
            "roleName": ""
          }
        ],
        "slaveAcct": $('#acctTel').val(),
        "slaveAcctType": slaveAcctType.toString(),
        "statusCd": subStatusCd.toString(),
        "tbAcctExt": tbAcctExt,
        "userType": "2"
    };

    $.ajax({
        url: '/slaveAcct/addTbSlaveAcct',
        type: 'POST',
        async:false,
        contentType: "application/json",
        data: JSON.stringify(editFormSlaveAcctVo),
        dataType:"JSON",
        success: function (data) { //返回json结果
          console.log(data);
          alert('新增成功');
          window.history.back();
        },
        error:function(err){
          console.log(err);
          alert('新增失败');
        }
      });
}

function updateTbSlaveAcct(){       //更新从账号信息
    var slaveAcctType = $('#accTypeTel').get(0).selectedIndex + 1;
    var resourceObjId = $('#systemTel').get(0).selectedIndex + 1;
    var subStatusCd = $('#statusCd').get(0).selectedIndex*100 + 1000;
    var certType = $('#extCerTel').get(0).selectedIndex + 1;
    var tbAcctExt = hasExtInfo(certType);

    var editFormSlaveAcctVo = {
        "acctHostId": acctHostId,
        "disableDate": $('#invalidDate').val(),
        "enableDate": $('#effectDate').val(),
        "password": $('#defaultPswTel').val(),
        "personnelId": parseInt(personnelId),
        "resourceObjId": resourceObjId,
        "rolesList": [
          {
            "roleId": parseInt($('#roleTel').val()),
            "roleName": ""
          }
        ],
        "slaveAcct": $('#acctTel').val(),
        "slaveAcctId": slaveAcctId,
        "slaveAcctType": slaveAcctType.toString(),
        "statusCd": subStatusCd.toString(),
        "tbAcctExt": tbAcctExt,
        "userType": "2"
    };
    
    $.ajax({
        url: '/slaveAcct/updateTbSlaveAcct',
        type: 'POST',
        async:false,
        contentType: "application/json",
        data: JSON.stringify(editFormSlaveAcctVo),
        dataType:"JSON",
        success: function (data) { //返回json结果
          console.log(data);
          alert('保存成功');
          window.history.back();
        },
        error:function(err){
          console.log(err);
          alert('保存失败');
        }
      });
}

function deleteTbSubAcct(){     //删除从账号
    $.ajax({
        url: '/slaveAcct/delTbSlaveAcct?&slaveAcctId='+parseInt(acctId),
        type: 'DELETE',
        contentType: "application/json",
        async:false,
        dataType:"json",
        success: function (data) { //返回json结果
          console.log(data);
          alert('删除成功');
          window.history.back();
        },
        error:function(err){
          console.log(err);
          alert('删除失败');
        }
      });
}

function  hasExtInfo(certType){  //判断是否需要扩展信息
    var tbAcctExt;
    if(isChecked == 0){
        tbAcctExt = null;
    }else{
        tbAcctExt = {
            "acctExtId":acctExtId,
            "certNo": $('#extCerNoTel').val(),
            "certType": certType.toString(),
            "contactWay": $('#extMobileTel').val(),
            "name": $('#extNameTel').val(),
            "workEmail": $('#extEmailTel').val()
          };
    }
    return tbAcctExt;
}

function saveSlaveOrg(id,hostId){  //获取acctHostId
    acctHostId = hostId;
    $('#slaveOrgModal').modal('hide');
    table.destroy();
    initOrgTable([{"fullName":slaveOrgList[id-1]}]);
    $('#addText').text('更换');
}

function deleteOrg(){
    table.destroy();
    initOrgTable({"fullName":""});
    acctHostId = 0;
    $('#addText').text('添加');
}


function btnSubmit(){       //提交
    if($('#acctTel').val()!='' && $('#statusCd').val()!='' && $('#roleTel').val()!='' 
                               && $('#defaultPswTel').val()!='' && $('#effectDate').val()!='' 
                               && $('#invalidDate').val()!='' && acctHostId != 0){
        if(isChecked == 0 || $('#extNameTel').val()!= '' || $('#extEmailTel').val()!= '' || $('#extMobileTel').val()!= '' || $('#extCerNoTel').val()!= '' ){
            if(opBtn == 0){
                updateTbSlaveAcct();
            }else{
                addTbSlaveAcct();
            }
        }else{
            alert('扩展信息不能全为空');
        }
    }else if(acctHostId == 0){
        alert('组织不能为空');
    }else{
        alert('必填部分不能为空');
    }
}

function extInfoFade(){     //点击复选框
    var extCheckBox = $("#extCheckBox");
    if(extCheckBox.attr("value") == "0"){  
        extCheckBox.attr("value","1")
        $("#extInfo").css("border-color","#00A4FF"); 
        isChecked = 1;
    }else{
        extCheckBox.attr("value","0")
        $("#extInfo").css("border-color","#b6b6b6"); 
        isChecked = 0;
    }
    console.log(isChecked);
}

if(opBtn==0){
    $('#addText').text('更换');
    getSubUser(acctId,userType);
}else{      //新增
    $('.BtnDel').css("display","none");
    $('#statusCd').get(0).selectedIndex=0;
    getUserInfo();
}


