var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var mainAcctId = getQueryString('mainAcctId');
var hType = getQueryString('hType');
var toMainType = getQueryString('toMainType');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');
// var hostId = getQueryString('acctOrgRelId');
var fullName = getQueryString('fullName');
var tabPage = getQueryString('tabPage');
var acctId = getQueryString('acctId');
var statusCd = getQueryString('statusCd');
var personnelId = getQueryString('personnelId');
var orgTreeName = getQueryString('orgTreeName');
var curOrgId = getQueryString('curOrgId');
var curOrgTreeId = getQueryString('curOrgTreeId');
var curSlaveOrgTreeId = getQueryString('curSlaveOrgTreeId');
var curSlaveOrgTreeName = getQueryString('curSlaveOrgTreeName');
var relType;
var extCerTypeName;
var extFlag = 0;

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
var acctTypeList = window.top.dictionaryData.acctType();
var statusCdList = window.top.dictionaryData.statusCd();
var relTypeName = window.top.relTypeName;

var loading = parent.loading;
loading.screenMaskEnable('container');
// if(hostId != null){
//     acctOrgRelId = hostId;
// }

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

function getSubUser(acctId) {       //查看并编辑从账号          
    $http.get('/user/getUser', {  
        acctId: acctId,
        userType: "2",
    }, function (data) {
        if(data.tbAcctExt != null){
            acctExtId = data.tbAcctExt.acctExtId;
        }
        $('#acctInfo').css("display","block");
        personnelId = data.personnelId;
        acctOrgRelId = data.acctOrgVoList[0].orgId;
        slaveAcctId = data.tbSlaveAcct.slaveAcctId;
        relType = data.acctOrgVoList[0].relType;
        setAcctInfoTables(data.acctOrgVoList[0]);

        initSubAcctInfoCheck(data);
        initSubInfo(data);
        getSysSelect();
    }, function (err) {
        loading.screenMaskDisable('container');
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

function noSelectUserInfo(){     //控制人员信息不可选
    $("#psnName").attr("disabled","disabled");
    $("#psnNum").attr("disabled","disabled");
    $("#mobile").attr("disabled","disabled");
    $("#email").attr("disabled","disabled");
    $("#cerType").attr("disabled","disabled");
    $("#cerNo").attr("disabled","disabled");
 }

function initSubInfo(results){  //编辑时初始化信息
    $('#psnName').val(results.psnName);
    $('#psnNum').val(results.psnCode);
    $('#mobile').val(results.mobilePhone);
    $('#email').val(results.email);
    $('#cerNo').val(results.certNo);
    $('#acct').val(results.tbSlaveAcct.slaveAcct);
    // $('#defaultPsw').val(results.tbSlaveAcct.password);
    setDate(results.tbSlaveAcct.enableDate,results.tbSlaveAcct.disableDate);

    $('#role').addTag(results.tbRolesList);

    for(var i=0;i<statusCdList.length;i++){
        if(results.tbSlaveAcct.statusCd === statusCdList[i].itemValue){
            $("#statusCd").append("<option value='" + statusCdList[i].itemValue + "' selected>" + statusCdList[i].itemCnname +"</option>");
        }else{
            $("#statusCd").append("<option value='" + statusCdList[i].itemValue + "'>" + statusCdList[i].itemCnname +"</option>");
        }
    }

    for(var i=0;i<cerTypeList.length;i++){
        if(results.certType === cerTypeList[i].itemValue){
          $("#cerType").append("<option value='" + cerTypeList[i].itemValue + "' selected>" + cerTypeList[i].itemCnname +"</option>");
        }    
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $("#cerType").selectMatch();
        $("#statusCd").selectMatch();
    });

    resourceObjId = results.tbSlaveAcct.resourceObjId;
    psw = results.tbSlaveAcct.password;

    initExtInfo(results);
}

//初始化扩展信息
function initExtInfo(results){
    $("#extCerType").empty();
    for(var i=0;i<cerTypeList.length;i++){
        var select = "";
        if(results.tbAcctExt != null && results.tbAcctExt.certType === cerTypeList[i].itemValue){
            extCerTypeName = cerTypeList[i].itemCnname;
            select = "selected";
        }
        $("#extCerType").append("<option value='" + cerTypeList[i].itemValue + "' " + select + ">" + cerTypeList[i].itemCnname +"</option>"); 
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $("#extCerType").selectMatch();
    });

    //扩展信息
    if(results.tbAcctExt != null){
        extFlag = 1;
        $("#extInfoDiv").css("display","block"); 

        $('#extCerNo').val(results.tbAcctExt.certNo);
        $('#extMobile').val(results.tbAcctExt.contactWay);
        $('#extName').val(results.tbAcctExt.name);
        $('#extEmail').val(results.tbAcctExt.workEmail);
        $('#extWeChatID').val(results.tbAcctExt.openid);

        isNull("#extCerNumLable",results.tbAcctExt.certNo);
        isNull("#extMobileLable",results.tbAcctExt.contactWay);
        isNull("#extNameLable",results.tbAcctExt.name);
        isNull("#extEmailLable",results.tbAcctExt.workEmail);
        isNull("#extCerTypeLable",extCerTypeName);
        isNull("#extWeChatIDLable",results.tbAcctExt.openid);
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
    isNull("#saleCodeLable",results.crossTran);
    setAcctHref(results.acct);

    for(var i=0;i<cerTypeList.length;i++){
        if(results.certType === cerTypeList[i].itemValue){
            $("#cerNoTxt").text(cerTypeList[i].itemCnname+"号码：");
            break;
        }
    }

    for(var i=0;i<acctTypeList.length;i++){
        if(results.slaveAcctType === acctTypeList[i].itemValue){
            $("#slaveTypeTxt").text("从账号类型：" + acctTypeList[i].itemCnname);
            $("#accType").append("<option value='" + acctTypeList[i].itemValue + "' selected>" + acctTypeList[i].itemCnname +"</option>");
        }else{
            $("#accType").append("<option value='" + acctTypeList[i].itemValue + "'>" + acctTypeList[i].itemCnname +"</option>");
        }
    }

    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $("#accType").selectMatch();
    });

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

//设置主账号点击跳转
function setAcctHref(account){
    // $http.get('/user/getUser', {   
    //     acctId: mainAcctId,
    //     userType: "1"
    // }, function (data) {
        $("#acctLink").text(account);
        if(hType == "mh"){
            $("#acctLink").attr("href","editMainAccount.html?curOrgId="+curOrgId+"&curOrgTreeId="+curOrgTreeId+"&orgTreeId=" + orgTreeId + 
                        "&hType="+hType+"&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&acctId=" + mainAcctId);
        }else{
            $("#acctLink").attr("href","editMainAccount.html?curOrgId="+curOrgId+"&curOrgTreeId="+curOrgTreeId+"&orgTreeId=" + orgTreeId + 
                        "&hType="+toMainType+"&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&acctId=" + mainAcctId);
        }
    // }, function (err) {
    //     loading.screenMaskDisable('container');
    // })
}

function updateTbSlaveAcct(){       //更新从账号信息
    loading.screenMaskEnable('container');
    if(!formValidate.isAllPass()){
        loading.screenMaskDisable('container');
        $("#acctInfo").css("display","none");
        $("#editAcctPanel").css("display","block");
        $('#acctEditButton').css("display","none");
        return;
    }

    var slaveAcctType = $('#accType').val();
    var subStatusCd = $('#statusCd').val();
    // var certType = $('#extCerType').val();
    // var tbAcctExt = hasExtInfo(certType);

    if(roleList.length == 0){
        roleList = userRoleList;
    }

    var editFormSlaveAcctVo = {
        // "acctOrgRelId": acctOrgRelId,
        "disableDate": $('#invalidDate').val(),
        "enableDate": $('#effectDate').val(),
        // "password": $('#defaultPsw').val(),
        "personnelId": parseInt(personnelId),
        "resourceObjId": resourceObjId,
        "rolesList": roleList,
        "slaveAcct": $('#acct').val(),
        "slaveAcctId": slaveAcctId,
        "slaveAcctType": slaveAcctType.toString(),
        "statusCd": subStatusCd.toString(),
        // "tbAcctExt": tbAcctExt,
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
                submitToSuccess();
            }else{
                loading.screenMaskDisable('container');
                toastr.error(data.message);
            }
        },
        error:function(err){
            loading.screenMaskDisable('container');
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
        loading.screenMaskEnable('container');
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
                    loading.screenMaskDisable('container');
                    toastr.error(data.message);
                }
            },
            error:function(err){
                loading.screenMaskDisable('container');
                toastr.error('删除失败');
            }
          });
      }, function(){
    }); 
}

function getSysSelect(){   //获取应用系统下拉列表
    $http.get('/permission/businessSystem/listBusinessSystem/'+curSlaveOrgTreeId, 
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
                    $("#sysLable").text(data[i].systemName);
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
        })
        loading.screenMaskDisable('container');
    }, function (err) {
        loading.screenMaskDisable('container');
    })
}

// function  hasExtInfo(certType){  //判断是否需要扩展信息
//     var tbAcctExt;
//     if(isChecked == 0){
//         tbAcctExt = null;
//     }else{
//         tbAcctExt = {
//             "acctExtId":acctExtId,
//             "certNo": $('#extCerNo').val(),
//             "certType": certType.toString(),
//             "contactWay": $('#extMobile').val(),
//             "name": $('#extName').val(),
//             "workEmail": $('#extEmail').val()
//           };
//     }
//     return tbAcctExt;
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
        area: ['40%', '90%'],
        maxmin: true,
        content: '/inaction/account/orgDialog.html?orgTreeId='+curSlaveOrgTreeId+'&relType='+relType,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            slaveOrgList = [];
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            var orgId = iframeWin.orgIdSelect;
            var relTypeVal = iframeWin.relTypeVal;
            parent.layer.close(index);
            updateSlaveAcctOrg(orgId,relTypeVal);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
  }

  //更新从账号组织
function updateSlaveAcctOrg(orgId,relTypeVal){
    loading.screenMaskEnable('container');
    $http.put('/acct/updateSlaveAcctOrg', JSON.stringify({  
        acctId : mainAcctId,
        orgId : orgId,
        orgTreeId : curSlaveOrgTreeId,
        relType : relTypeVal,
        slaveAcctId : slaveAcctId
    }), function (message) {
        reflashOrg();
        toastr.success("保存成功！");
    }, function (err) {
        loading.screenMaskDisable('container');
        // toastr.error("保存失败！");
    })
}

//刷新组织信息
function reflashOrg(){
    $http.get('/user/getUser', {  
        acctId: acctId,
        userType: "2",
    }, function (data) {
        relType = data.acctOrgVoList[0].relType;
        setAcctInfoTables(data.acctOrgVoList[0]);
        loading.screenMaskDisable('container');
    }, function (err) {
        loading.screenMaskDisable('container');
    })
}

//显示从账号组织信息
function setAcctInfoTables(result){
    var acctHtml = ""; 
    var relTypeN;
    $("#acctOrgDiv").empty();
    for(var j=0;j<relTypeName.length;j++){
        if(result.relType == relTypeName[j].itemValue){
          relTypeN = relTypeName[j].itemCnname;
          break;
        }
    }
    acctHtml = "<div class='curDiv' style='padding:10px 20px;'>"+
                    "<span class='pngDot'></span>"+
                    "<span class='Name Gray3' style='margin-left:1.5%;' id='orgTreeName_'>"+result.orgTreeName+"</span>"+
                    "<span class='Tag BgBlue' style='cursor:pointer;' title='"+result.fullName+"' id='orgName_'>"+result.orgName+"</span>"+
                    "<span class='Tag BgBlue' style='cursor:pointer;' id='relTypeName_'>"+relTypeN+"</span></div>";
                    // "<span id='editBtn_' title='组织编辑' onclick='' class='icon icon-edit'></span>"+
                    // "<span class='fright FunctionBtn' style='float:right;margin-right:3.5%;'>";
                        // "<a class='BtnDel_' style='cursor:pointer;' onclick='deleteOrg()' id='delBtn'><span></span>删除组织关系</a></span></div>";
  
  $("#acctOrgDiv").append(acctHtml);
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
        url = "editMainAccount.html?curOrgId="+curOrgId+"&curOrgTreeId="+curOrgTreeId+"&orgTreeId=" + orgTreeId + "&hType="+ toMainType +"&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&acctId=" + mainAcctId;   //跳转主账号编辑界面
    }else if(hType == "mh"){
        url = "list.html?orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId;       //跳转主界面
    }else{
        url = "/inaction/user/edit.html?orgTreeName="+encodeURI(orgTreeName)+"&orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName) + "&id=" + orgId + 
                                      "&personnelId=" + personnelId + "&orgRootId=" + orgRootId + "&tabPage=" + tabPage;
    }
    loading.screenMaskDisable('container');
    window.location.href = url;
}

//更新成功跳转从账号编辑页
function submitToSuccess(){//curSlaveOrgTreeId
    var url = 'editSubAccount.html?curSlaveOrgTreeId='+curSlaveOrgTreeId+'&curOrgId='+curOrgId+'&curOrgTreeId='+curOrgTreeId+'&orgTreeId=' + orgTreeId + '&toMainType=' + toMainType +
                    '&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +'&hType='+hType+'&mainAcctId='+ mainAcctId +'&acctId='+ acctId;
    loading.screenMaskDisable('container');
    window.location.href = url;
}

//扩展信息编辑按钮
function showEditDiv(){
    // $("#extInfoEdit").css("display","none");
    $("#extInfoDiv").css("display","none");
    $("#extInfo").css("display","block");
    if(extFlag == 1){
        $("#delExtInfo").css("display","inline-block");
    }
}

function backToInfo(){
    // $("#extInfoEdit").css("display","block");
    if(extFlag == 1){
        reflashExtInfo();
        $("#extInfoDiv").css("display","block");
        $("#extInfo").css("display","none");
    }else{
        $("#extInfoDiv").css("display","none");
        $("#extInfo").css("display","none");
        $('#extCerNo').val("");
        $('#extMobile').val("");
        $('#extName').val("");
        $('#extEmail').val("");
        $('#extWeChatID').val("");
    }
}

//保存扩展信息
function saveExtInfo(){
    if(extFlag == 1){
        updateTbAcctExt();
    }else{
        addTbAcctExt();
    }
}

//新增扩展信息
function addTbAcctExt(){
    $http.post('/slaveAcct/addTbAcctExt', JSON.stringify({  
        openid : $('#extWeChatID').val(),
        certNo : $('#extCerNo').val(),
        certType : $('#extCerType').val().toString(),
        contactWay : $('#extMobile').val(),
        name : $('#extName').val(),
        workEmail : $('#extEmail').val(),
        slaveAcctId : acctId
    }), function (message) {
        reflashExtInfo();
        backToInfo();
        toastr.success("保存成功");
    }, function (err) {
        
    })
}

//更新扩展信息
function updateTbAcctExt(){
    $http.post('/slaveAcct/updateTbAcctExt', JSON.stringify({  
        slaveAcctId : acctId,
        acctExtId : acctExtId,
        openid : $('#extWeChatID').val(),
        certNo : $('#extCerNo').val(),
        certType : $('#extCerType').val().toString(),
        contactWay : $('#extMobile').val(),
        name : $('#extName').val(),
        workEmail : $('#extEmail').val()
    }), function (data) {
        reflashExtInfo();
        backToInfo();
        toastr.success("保存成功");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}

//删除扩展信息
function delTbAcctExt(){
    parent.layer.confirm('此操作将删除扩展信息, 是否继续?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        $http.delet('/slaveAcct/delTbAcctExt',JSON.stringify({  
            slaveAcctId : acctId,
            acctExtId : acctExtId,
            certNo : $('#extCerNo').val(),
            certType : $('#extCerType').val().toString(),
            contactWay : $('#extMobile').val(),
            name : $('#extName').val(),
            workEmail : $('#extEmail').val()
        }),function(message){
            extFlag = 0;
            backToInfo();
            $("#delExtInfo").css("display","none");
            $('#extCerNo').val("");
            $('#extMobile').val("");
            $('#extName').val("");
            $('#extEmail').val("");
            toastr.success("删除成功");
        });
      }, function(){
    }); 
}

//刷新扩展信息
function reflashExtInfo() {                
    $http.get('/user/getUser', {  
        acctId: acctId,
        userType: "2",
    }, function (data) {
        if(data.tbAcctExt != null){
            acctExtId = data.tbAcctExt.acctExtId;
        }
        initExtInfo(data);
    }, function (err) {
        loading.screenMaskDisable('container');
    })
}

noSelectUserInfo();
getSubUser(acctId); 



