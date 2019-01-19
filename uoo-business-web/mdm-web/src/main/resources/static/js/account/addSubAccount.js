var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var mainAcctId = getQueryString('mainAcctId');
var hType = getQueryString('hType');
var toMainType = getQueryString('toMainType');
var orgTreeId = getQueryString('orgTreeId');
var slaveOrgTreeId = getQueryString('slaveOrgTreeId');
var orgRootId = getQueryString('orgRootId');
var acctOrgRelId = getQueryString('acctOrgRelId');
var fullName = getQueryString('fullName');
var tabPage = getQueryString('tabPage');
var acctId = getQueryString('acctId');
var statusCd = getQueryString('statusCd');
var personnelId = getQueryString('personnelId');
var orgTreeName = getQueryString('orgTreeName');
var curOrgId = getQueryString('curOrgId');
var curOrgTreeId = getQueryString('curOrgTreeId');
var curSlaveOrgName = getQueryString('curSlaveOrgName');
var relTypeVal = getQueryString('relTypeVal');

var table;
var slaveTable;
var isEdit;
// var acctOrgRelId = 0;
var slaveOrgList = [];
var acctExtId = null;
var isChecked = 0;
var roleList = [];      //需要上传的角色列表
var userRoleList = [];      //用户已有角色列表
var toastr = window.top.toastr;
var resourceObjId = null;
var cerTypeList = window.top.dictionaryData.certType();
var acctTypeList = window.top.dictionaryData.acctType();
var statusCdList = window.top.dictionaryData.statusCd();
var relTypeName = window.top.relTypeName;
// if(hostId != null){
//     acctOrgRelId = hostId;
// }
// $('#addText').text('新增归属组织');

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
//     $('#defaultPsw').tips({
//         align: 'right'
//     });
// });

function getUserInfo(){         //新增从账号
    $http.get('/user/getPsnUser', {    
        personnelId: personnelId,
        userType: "2"
    }, function (data) {
        // initOrgTable("");
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

// function getAcctOrg(){          //获取从账号可选组织列表(添加组织)
//     $http.get('/user/getAcctOrgByPsnId', {    
//         personnelId: personnelId,
//         resourceObjId: resourceObjId
//       }, function (data) {
//         slaveOrgList = [];
//         for(var i=0;i<data.length;i++){
//             slaveOrgList.push({"orgTreeName":data[i].orgTreeName,"fullName":data[i].fullName});
//         }
//         initAcctOrgTable(data);
//       }, function (err) {

//       })
// }

function noSelectUserInfo(){     //控制人员信息不可选
    $("#psnName").attr("disabled","disabled");
    $("#psnNum").attr("disabled","disabled");
    $("#mobile").attr("disabled","disabled");
    $("#email").attr("disabled","disabled");
    $("#cerType").attr("disabled","disabled");
    $("#cerNo").attr("disabled","disabled");
 }

// function initOrgTable(results){
//     table = $("#orgTable").DataTable({
//       'data': results,
//       'destroy':true,
//       'searching': false,
//       'autoWidth': false,
//       'ordering': true,
//       'paging': false,
//       'info': false,
//       "scrollY": "375px",
//       'scrollCollapse': true,
//       'columns': [
//           { 'data': "id", 'title': '序号', 'className': 'row-id' ,
//           'render': function (data, type, row, meta) {
//             return 1;
//         }
//         },
//           { 'data': "orgTreeName", 'title': '组织树', 'className': 'row-orgTree'},
//           { 'data': "fullName", 'title': '组织名称', 'className': 'row-fullName' ,
//           'render': function (data, type, row, meta) {
//             if(row.fullName != null){
//                 return '<span title="'+ row.fullName +'" style="cursor:pointer;">'+row.fullName+'</span>';
//               }else{
//                 return "";
//             }
//         }
//         },
//         {'data': "orgId", 'title': '操作', 'className': 'row-delete' ,
//             'render': function (data, type, row, meta) {
//                 return "<a class='Icon IconDel' href='javascript:void(0);' id='delOrgBtn' title='删除' onclick='deleteOrg()'></a>";
//             }
//         }
//       ],
//       'language': {
//           'emptyTable': '没有数据',  
//           'loadingRecords': '加载中...',  
//           'processing': '查询中...',  
//           'search': '检索:',  
//           'lengthMenu': ' _MENU_ ',  
//           'zeroRecords': '没有数据', 
//           'infoEmpty': '没有数据'
//       }
//     });
//   }

//   function initAcctOrgTable(results){
//     var num = 1;
//     slaveTable = $("#acctOrgTable").DataTable({
//       'data': results,
//       'destroy':true,
//       'searching': false,
//       'autoWidth': false,
//       'ordering': true,
//       'paging': false,
//       'info': false,
//       "scrollY": "240px",
//       'columns': [
//         { 'data': "id", 'title': '序号', 'className': 'row-t1' ,
//             'render': function (data, type, row, meta) {
//                 return num++;
//             }
//         },
//         { 'data': "orgTreeName", 'title': '组织树', 'className': 'row-t2'},
//         { 'data': "fullName", 'title': '可选组织', 'className': 'row-t3',
//         'render': function (data, type, row, meta) {
//             return "<a href='javascript:void(0);' title='"+row.fullName+"' onclick='saveSlaveOrg("+ num + ","+ row.acctOrgRelId + ")'>"+ row.fullName +'</a>'
//         }
//         }
//       ],
//       'language': {
//           'emptyTable': '没有数据',  
//           'loadingRecords': '加载中...',  
//           'processing': '查询中...',  
//           'search': '检索:',  
//           'lengthMenu': ' _MENU_ ',  
//           'zeroRecords': '没有数据', 
//           'infoEmpty': '没有数据'
//       }
//     });
//   }

function initUserInfo(results){   //新增时初始化信息
    $('#psnName').val(results.psnName);
    $('#psnNum').val(results.psnCode);
    $('#mobile').val(results.mobilePhone);
    $('#email').val(results.eamil);
    $('#cerNo').val(results.certNo);
    window.localStorage.setItem('userRoleList',JSON.stringify(''));

    if(fullName != null){

        setAcctInfoTables({"orgTreeName":orgTreeName,"orgName":curSlaveOrgName,"orgFullName":fullName,'relType':relTypeVal});
        $('#addText').text('更改归属组织');
    } 
    setDate();

    for(var i=0;i<statusCdList.length;i++){
        $("#statusCd").append("<option value='" + statusCdList[i].itemValue + "'>" + statusCdList[i].itemCnname +"</option>");
    }

    for(var i=0;i<acctTypeList.length;i++){
        $("#accType").append("<option value='" + acctTypeList[i].itemValue + "'>" + acctTypeList[i].itemCnname +"</option>");
    }

    for(var i=0;i<cerTypeList.length;i++){
        if(results.certType === cerTypeList[i].itemValue){
          $("#cerType").append("<option value='" + cerTypeList[i].itemValue + "' selected>" + cerTypeList[i].itemCnname +"</option>");
        }
        $("#extCerType").append("<option value='" + cerTypeList[i].itemValue + "'>" + cerTypeList[i].itemCnname +"</option>");     
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $("#cerType").selectMatch();
        $("#accType").selectMatch();
        $("#statusCd").selectMatch();
        $("#extCerType").selectMatch();
    });
}

function addTbSlaveAcct(){      //从账号新增
    if(!formValidate.isAllPass())
        return;
    var slaveAcctType = $('#accType').val();
    var subStatusCd = $('#statusCd').val();
    var certType = $('#extCerType').val();
    var tbAcctExt = hasExtInfo(certType);

    if(roleList.length == 0){
        roleList = userRoleList;
    }

    var editFormSlaveAcctVo = {
        // "acctOrgRelId": acctOrgRelId,buchuang
        "orgId":acctOrgRelId,
        "acctId":mainAcctId,
        "orgTreeId":slaveOrgTreeId,
        "disableDate": $('#invalidDate').val(),
        "enableDate": $('#effectDate').val(),
        // "password": $('#defaultPsw').val(),
        "personnelId": parseInt(personnelId),
        "resourceObjId": resourceObjId,
        "rolesList": roleList,
        "slaveAcct": $('#acct').val(),
        "slaveAcctType": slaveAcctType.toString(),
        "statusCd": subStatusCd.toString(),
        "tbAcctExt": tbAcctExt,
        "userType": "2",
        "relType" : relTypeVal
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
            submitToSuccess();
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
    $http.get('/permission/businessSystem/listBusinessSystem/'+slaveOrgTreeId, 
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

// function saveSlaveOrg(id,hostId){  //获取acctOrgRelId
//     acctOrgRelId = hostId;
//     $('#slaveOrgModal').modal('hide');
//     initOrgTable([{"orgTreeName":slaveOrgList[id-2].orgTreeName,"fullName":slaveOrgList[id-2].fullName}]);
//     $('#addText').text('更换归属组织');
// }

// function deleteOrg(){
//     initOrgTable({"orgTreeName":"","fullName":""});
//     acctOrgRelId = 0;
//     $('#addText').text('新增归属组织');
// }

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

//组织选择
function openOrgDialog() {
    parent.layer.open({
        type: 2,
        title: '选择组织',
        shadeClose: true,
        shade: 0.8,
        area: ['27%', '80%'],
        maxmin: true,
        content: '/inaction/account/orgDialog.html?orgTreeId='+slaveOrgTreeId+'&relType='+relTypeVal,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            // $('#addText').text('更换归属组织');
            slaveOrgList = [];
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            var orgFullName = iframeWin.orgFullName;
            var orgName = iframeWin.orgName;
            relTypeVal = iframeWin.relTypeVal;
            acctOrgRelId = iframeWin.orgIdSelect;
            setAcctInfoTables({"orgTreeName":orgTreeName,"orgName":orgName,"orgFullName":orgFullName,'relType':relTypeVal});
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
  }

  //显示从账号组织信息
function setAcctInfoTables(result){
    var acctHtml = ""; 
    var relType;
    $("#acctOrgDiv").empty();
    for(var j=0;j<relTypeName.length;j++){
        if(result.relType == relTypeName[j].itemValue){
            relType = relTypeName[j].itemCnname;
            break;
        }
    }
    acctHtml = "<div class='curDiv' style='padding:10px 20px;'>"+
                    "<span class='pngDot'></span>"+
                    "<span class='Name Gray3' style='margin-left:1.5%;' id='orgTreeName_'>"+result.orgTreeName+"</span>"+
                    "<span class='Tag BgBlue' style='cursor:pointer;' title='"+result.fullName+"' id='orgName_'>"+result.orgName+"</span>"+
                    "<span class='Tag BgBlue' style='cursor:pointer;' id='relTypeName_'>"+relType+"</span></div>";
                    // "<span id='editBtn_' title='组织编辑' onclick='' class='icon icon-edit'></span>"+
                    // "<span class='fright FunctionBtn' style='float:right;margin-right:3.5%;'>";
                        // "<a class='BtnDel_' style='cursor:pointer;' onclick='deleteOrg()' id='delBtn'><span></span>删除组织关系</a></span></div>";
  
  $("#acctOrgDiv").append(acctHtml);
}

function extInfoFade(){     //点击复选框
    var extCheckBox = $("#extCheckBox");
    if(extCheckBox.attr("value") == "0"){  
        extCheckBox.attr("value","1")
        // $("#extInfo").css("border-color","#00A4FF"); 
        // $("#extInfo").fadeIn();
        $("#extInfo").css("display","block"); 
        isChecked = 1;
        if(isIE8){
            $(".ui-checkbox").css("background-position","0px -40px");
        }
    }else{
        extCheckBox.attr("value","0")
        // $("#extInfo").css("border-color","#b6b6b6"); 
        // $("#extInfo").fadeOut();
        $("#extInfo").css("display","none"); 
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
        url = "editMainAccount.html?curOrgId="+curOrgId+"&curOrgTreeId="+curOrgTreeId+"&orgTreeId=" + orgTreeId + "&hType="+ toMainType +"&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&acctId=" + mainAcctId;   
    }else{
        url = "/inaction/user/edit.html?orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName) + "&id=" + orgId + 
                                      "&personnelId=" + personnelId + "&orgRootId=" + orgRootId + "&tabPage=" + tabPage;
    }
    window.location.href = url;
}

function submitToSuccess(){ //保存成功跳转编辑从账号页面
    $http.get('/user/getPsnUser', {    
        personnelId: personnelId,
        userType: "1"
      }, function (data) {
            var slaveAcctId;
            var url = "";
            for(var i=0;i<data.slaveAcctOrgVoPage.records.length;i++){
                if($('#acct').val() == data.slaveAcctOrgVoPage.records[i].slaveAcct){
                    slaveAcctId = data.slaveAcctOrgVoPage.records[i].slaveAcctId;
                }
            }
            url = 'editSubAccount.html?curOrgId='+curOrgId+'&curOrgTreeId='+curOrgTreeId+'&orgTreeId=' + orgTreeId + '&toMainType=' + toMainType +
                    '&curSlaveOrgTreeId='+slaveOrgTreeId+'&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +'&hType=th&mainAcctId='+ mainAcctId +
                    '&acctId='+ slaveAcctId;
            window.location.href = url;
      }, function (err) {
    
      })
}

noSelectUserInfo();
getUserInfo();



