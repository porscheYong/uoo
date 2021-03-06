var toastr = window.top.toastr;
var orgId = getQueryString('id');
var orgRootId = getQueryString('orgRootId');
var tabPage = getQueryString('tabPage');
//var orgRootId = 1;
var personnelId = getQueryString('personnelId');
var orgTreeId = getQueryString('orgTreeId');
var orgTreeName = getQueryString('orgTreeName');
var orgName = getQueryString('name');
var addOrg = getQueryString('addOrg');
var personalData={},genderData,certTypeData,nationData,pliticalStatusData,marriageData,orgInfo={},
    userFormValidate,jobFormValidate,eduFormValidate,familyFormValidate,orgFormValidate;
var toastr = window.top.toastr;
var psnImageId;
var imgUrl = "";
// lulu ui select插件
// seajs.use('../../../static/vendors/lulu/js/common/ui/Select', function () {
//   $('select').selectMatch();
// })

//字典数据
var certTypeData = window.top.dictionaryData.certType();
var nationData = window.top.dictionaryData.nation();
var pliticalStatusData = window.top.dictionaryData.pliticalStatus();
var marriageData = window.top.dictionaryData.marriage();
var memRelationData = window.top.dictionaryData.memRelation();
var refTypeData = window.top.dictionaryData.refType();

personalData.certTypeData = certTypeData;
personalData.nationData = nationData;
personalData.pliticalStatusData = pliticalStatusData;
personalData.marriageData = marriageData;
personalData.memRelation = memRelationData;
personalData.refTypeData = refTypeData;

function getUserAccount(){
    $http.get('/user/getUserList', {personnelId:personnelId}, function (data) {
        personalData.userList=data;
        initUserList();
    }, function (err) {

    })

}
function getOrgTreeList () {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        personalData.orgTreeList=data;
    }, function (err) {

    })
}

function getYesNo () {
    $http.get('/tbDictionaryItem/getList/YES_NO', {}, function (data) {
        personalData.yesNo=data;
    }, function (err) {

    })
}
function getEduInfo(){
	$http.get('/edu/getTbEduPage', {personnelId:personnelId,pageSize:11111111,pageNo:1}, function (data) {
		personalData.eduInfo=data;
        initEduInfo();
    }, function (err) {
    	
    })
}
function getJobInfo(){
	$http.get('/psnjob/getTbPsnjobPage', {personnelId:personnelId,pageSize:11111111,pageNo:1}, function (data) {
		personalData.jobInfo=data;
        initJobInfo();
    }, function (err) {

    })
    
}

function getFamilyInfo(){
	$http.get('/family/getTbFamilyPage', {personnelId:personnelId,pageSize:11111111,pageNo:1}, function (data) {
		personalData.familyInfo=data;
        initFamilyInfo();
    }, function (err) {

    })
     

}

// SCHOOL_TYPE
function getSchoolType () {
    $http.get('/tbDictionaryItem/getList/SCHOOL_TYPE', {}, function (data) {
        personalData.schoolType=data;
    }, function (err) {

    })
}
// 获取性别字典数据
function getGender () {
    $http.get('/tbDictionaryItem/getList/GENDER', {}, function (data) {
        genderData=data;
        personalData.genderData=genderData;
    }, function (err) {

    })
}

// 获取人员信息
function getOrgPersonnerList () {
    $http.get('/personnel/getFormPersonnel', {
        orgId: orgId,
        orgTreeId: orgTreeId,
        personnelId: personnelId
    }, function (data) {
        personalData.personalData=data;
        if(data.psonOrgVoList!=null){
            personalData.orgInfo=data.psonOrgVoList.records;
        }else{
            personalData.orgInfo={};
        }

        initUser();
        initOrgInfo();
    }, function (err) {

    })
}

//获取人员头像
function getPsnImage(){
    $http.get('/psnImage/getPsnImage', {
        personnelId: personnelId
    }, function (data) {
        if(data != null){
            imgUrl =  "data:image/png;base64," + data.image;
            $('#psnImg').attr("src",imgUrl);
            $('#psnimg2').attr("src",imgUrl);
            $('#psnimg1').attr("src",imgUrl);
        }
    }, function (err) {

    })
}

function initUser(){
    psnImageId=personalData.personalData.image;
    $('#userEditButton').show();
    //预编译模板
    var userTemplate = Handlebars.compile($("#userTemplate").html());
    //var baseInfoTemplate = Handlebars.compile($("#baseInfoTemplate").html());
    //匹配json内容
    var userHtml = userTemplate(personalData);
    // var baseHtml = baseInfoTemplate(personalData);
    //输入模板
    $('#userInfo').html(userHtml);
    // $('#baseInfo').html(baseHtml);
    getPsnImage();
}
function initUserList(){
    $('#userEditButton').show();
    //预编译模板
    //var userTemplate = Handlebars.compile($("#userTemplate").html());
    var baseInfoTemplate = Handlebars.compile($("#baseInfoTemplate").html());
    //匹配json内容
    //var userHtml = userTemplate(personalData);
    var baseHtml = baseInfoTemplate(personalData);
    //输入模板
    //$('#userInfo').html(userHtml);
    $('#baseInfo').html(baseHtml);
    getPsnImage();
}
function initOrgInfo(){
    //预编译模板
    var orgInfoTemplate1 = Handlebars.compile($("#orgInfoTemplate1").html());
    var orgInfoTemplate2 = Handlebars.compile($("#orgInfoTemplate2").html());
    //匹配json内容
    var h1 = orgInfoTemplate1(personalData);
    var h2 = orgInfoTemplate2(personalData);
    //输入模板
    $('#orgInfoTable1').html(h1);
    $('#orgInfoTable2').html(h2);
}
function initJobInfo(){
    //预编译模板
    var jobInfoTemplate1 = Handlebars.compile($("#jobInfoTemplate1").html());
    var jobInfoTemplate2 = Handlebars.compile($("#jobInfoTemplate2").html());
    //匹配json内容
    var h1 = jobInfoTemplate1(personalData);
    var h2 = jobInfoTemplate2(personalData);
    //输入模板
    $('#jobInfoTable1').html(h1);
    $('#jobInfoTable2').html(h2);
}
function initEduInfo(){
    //预编译模板
    var eduInfoTemplate1 = Handlebars.compile($("#eduInfoTemplate1").html());
    var eduInfoTemplate2 = Handlebars.compile($("#eduInfoTemplate2").html());
    //匹配json内容
    var h1 = eduInfoTemplate1(personalData);
    var h2 = eduInfoTemplate2(personalData);
    //输入模板
    $('#eduInfoTable1').html(h1);
    $('#eduInfoTable2').html(h2);
}
function initFamilyInfo(){
    //预编译模板
    var familyInfoTemplate1 = Handlebars.compile($("#familyInfoTemplate1").html());
    var familyInfoTemplate2 = Handlebars.compile($("#familyInfoTemplate2").html());
    //匹配json内容
    var h1 = familyInfoTemplate1(personalData);
    var h2 = familyInfoTemplate2(personalData);
    //输入模板
    $('#familyInfoTable1').html(h1);
    $('#familyInfoTable2').html(h2);
}
function  editUser() {
    $('#userEditButton').hide();
    //预编译模板
    var userTemplate = Handlebars.compile($("#userEditTemplate").html());
    //匹配json内容
    var userHtml = userTemplate(personalData);
    //输入模板
    $('#userInfo').html(userHtml);
    //电话
    if (personalData && personalData.personalData && personalData.personalData.tbMobileVoList.length > 0) {
        $('#mobile').val(personalData.personalData.tbMobileVoList[0].content);
        $('#mobile').attr('contactid', personalData.personalData.tbMobileVoList[0].contactId);
    }
    //邮箱
    if (personalData && personalData.personalData && personalData.personalData.tbEamilVoList.length > 0) {
        $('#email').val(personalData.personalData.tbEamilVoList[0].content);
        $('#email').attr('contactid', personalData.personalData.tbEamilVoList[0].contactId);
    }
    laydate.render({
        elem:  'input[isTime="yes"]'
    });

    $('.icon-del').on('click', function () {
        $(this).parent().parent().remove();
    });
    $("#choseFileImg").change( function() {
        addPsonImg();
    });
    seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
        userFormValidate = new Validate($('#userEditForm'));
        userFormValidate.immediate();
        /* $('#userEditForm').find(':input').each(function () {
             $(this).hover(function () {
                 userFormValidate.isPass($(this));
             });
         });*/
    });
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('select').selectMatch();
      })
    getPsnImage();
}
//邮箱自动填充
function autoFillMail() {
    var phoneNo = $('#mobile').val();
    var emailNo = $('#email').val();
    var reg = /^1[34578]\d{9}$/;
    if (!emailNo && reg.test(phoneNo)) {
        $('#email').val(phoneNo + '@189.cn');
        $('#email').focus();
    }
}
function editOrgInfo(){

    //预编译模板
    var t = Handlebars.compile($("#orgInfoEditTemplate").html());
    //匹配json内容
    var h = t(personalData);
    //输入模板
    $('#orgInfoTable1').html(h);
}
function editJobInfo(){

    //预编译模板
    var t = Handlebars.compile($("#jobInfoEditTemplate").html());
    //匹配json内容
    var h = t(personalData);
    //输入模板
    $('#jobInfoTable1').html(h);
}
function editEduInfo(){

    //预编译模板
    var t = Handlebars.compile($("#eduInfoEditTemplate").html());
    //匹配json内容
    var h = t(personalData);
    //输入模板
    $('#eduInfoTable1').html(h);
}
function editFamilyInfo(){

    //预编译模板
    var t = Handlebars.compile($("#familyInfoEditTemplate").html());
    //匹配json内容
    var h = t(personalData);
    //输入模板
    $('#familyInfoTable1').html(h);
}
//归属组织信息编辑
function openOrgEdit () {
    //预编译模板
    var orgTemplate = Handlebars.compile($("#orgTemplate").html());
    //匹配json内容
    var orgEditHtml = orgTemplate(personalData);
    //输入模板
    $('#orgInfoTable1').html(orgEditHtml);
    seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
        orgFormValidate = new Validate($('#orgEditForm'));
        orgFormValidate.immediate();
        /*$('#jobEditForm').find(':input').each(function () {
            $(this).hover(function () {
                jobFormValidate.isPass($(this));
            });
        });*/
    });

    if(addOrg=='1'){
        //从人员新增那里过来的老铁直接赋值一些数据
        if(personalData.currentEditOrgInfo==null ||personalData.currentEditOrgInfo.orgPersonId==null){
            $('#orgTreeId').val(orgTreeId);
            $('#orgFullName').val(orgName);
            $('#orgFullName').attr('keyId',orgId);
        }
    }
}
function openOrgEditByEdit (i) {
    personalData.currentEditOrgInfo=personalData.orgInfo[i];
    openOrgEdit();
}
function openJobEdit () {
    //预编译模板
    var orgTemplate = Handlebars.compile($("#jobTemplate").html());
    //匹配json内容
    var orgEditHtml = orgTemplate(personalData);
    //输入模板
    $('#jobInfoTable1').html(orgEditHtml);
    laydate.render({
        elem:  '#endTime'
    });
    laydate.render({
        elem:  '#beginTime'
    });

    seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
        jobFormValidate = new Validate($('#jobEditForm'));
        jobFormValidate.immediate();
        /*$('#jobEditForm').find(':input').each(function () {
            $(this).hover(function () {
                jobFormValidate.isPass($(this));
            });
        });*/
    });
}
function openJobEditByEdit (i) {
    personalData.currentEditJobInfo=personalData.jobInfo.records[i];
    openJobEdit();
}
function openEduEdit () {
    //预编译模板
    var orgTemplate = Handlebars.compile($("#eduTemplate").html());
    //匹配json内容
    var orgEditHtml = orgTemplate(personalData);
    //输入模板
    $('#eduInfoTable1').html(orgEditHtml);
    laydate.render({
        elem:  '#enddate'
    });
    laydate.render({
        elem:  '#begindate'
    });
    seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
        eduFormValidate = new Validate($('#eduEditForm'));
        eduFormValidate.immediate();
        /*$('#jobEditForm').find(':input').each(function () {
            $(this).hover(function () {
                jobFormValidate.isPass($(this));
            });
        });*/
    });
}
function openEduEditByEdit (i) {
    personalData.currentEditEduInfo=personalData.eduInfo.records[i];
    openEduEdit();
}
function openFamilyEdit () {
    //预编译模板
    var orgTemplate = Handlebars.compile($("#familyTemplate").html());
    //匹配json内容
    var orgEditHtml = orgTemplate(personalData);
    //输入模板
    $('#familyInfoTable1').html(orgEditHtml);
    seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
        familyFormValidate = new Validate($('#familyEditForm'));
        familyFormValidate.immediate();
        /*$('#jobEditForm').find(':input').each(function () {
            $(this).hover(function () {
                jobFormValidate.isPass($(this));
            });
        });*/
    });
}
function openFamilyEditByEdit (i) {
    personalData.currentEditFamilyInfo=personalData.familyInfo.records[i];
    openFamilyEdit();
}
function openChoseImg(){
    $('#choseFileImg').click();
}

//获取组织全称
function getOrgFullName() {
    parent.layer.open({
        type: 2,
        title: '选中组织类别',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'orgNameDialog.html?orgTreeId='+$('#orgTreeId').val()+'&orgRootId='+orgTreeId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgFullName').val(checkNode.name);
            $('#orgFullName').attr("keyId",checkNode.id);
            orgFullName = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}
function getOrgId() {
    parent.layer.open({
        type: 2,
        title: '选中组织类别',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'orgNameDialog.html?orgTreeId='+orgTreeId+'&orgRootId='+orgRootId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgId').val(checkNode.id);
            //orgFullName = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}
function getJobOrgId() {
    parent.layer.open({
        type: 2,
        title: '选中组织类别',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'jobsTreeNameDialog.html?orgTreeId='+orgTreeId+'&orgRootId='+orgRootId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgId').attr('keyId',checkNode.id);
            $('#orgId').val(checkNode.name);
            //orgFullName = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}
function getPostName () {
    parent.layer.open({
        type: 2,
        title: '组织职位',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: '/inaction/organization/postDialog.html?orgTreeId='+orgTreeId+'&orgRootId='+orgTreeId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#postId').val(checkNode[0].postName);
            $('#postId').attr('keyId',checkNode[0].postId);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

function cancelOrgEdit(){
    personalData.currentEditOrgInfo={};
    initOrgInfo();
}
function cancelJobEdit(){
    personalData.currentEditJobInfo={};
    initJobInfo();
}
function cancelEduEdit(){
    personalData.currentEditEduInfo={};
    initEduInfo();
}
function cancelFamilyEdit(){
    personalData.currentEditFamilyInfo={};
    initFamilyInfo();
}
function cancelUserEdit () {
    initUser();
}
function addPsonOrg(){
    if(!orgFormValidate.isAllPass()){
        return;
    }
    var isUpdate=$('#orgPersonId').val().length>0;
    var psonOrgArr=new Array();
    var psonOrg={
        orgTreeId:$('#orgTreeId').val(),
        orgName:$('#orgFullName').val(),
        doubleName:$('#doubleName').val(),
        property:$('#property').val(),
        postId:$('#postId').attr('keyId'),
        property:$('#property').val(),
        personnelId:personnelId,
        orgId:$('#orgFullName').attr('keyId'),
        orgPersonId:$('#orgPersonId').val(),
    };
    psonOrgArr[0]=psonOrg;
    $http.post((isUpdate?'/orgPersonRel/updateOrgPsn':'/orgPersonRel/addOrgPsn2'),(isUpdate?JSON.stringify(psonOrg):JSON.stringify(psonOrgArr)),function(data){
    	toastr.success('操作成功');
        personalData.currentEditOrgInfo={};
        getOrgPersonnerList();
    });
}
function addPsonJob(){
    if(!jobFormValidate.isAllPass()){
        return;
    }
    var psnjobId=$('#psnjobId').val();
    var psonJob={
        orgId:$('#orgId').attr('keyId'),
        beginTime:$('#beginTime').val(),
        endTime:$('#endTime').val(),
        personnelId:personnelId,
        psnjobId:psnjobId
    };
    if(psnjobId.length>0){
    	$http.put((psnjobId.length>0?'/psnjob/updateTbPsnjob':'/psnjob/saveTbPsnjob'),
        		(JSON.stringify(psonJob)),
        		function(data){
        	toastr.success('操作成功');
            personalData.currentEditJobInfo={};
            getJobInfo();
        });
    }else{
    	$http.post((psnjobId.length>0?'/psnjob/updateTbPsnjob':'/psnjob/saveTbPsnjob'),
        		(JSON.stringify(psonJob)),
        		function(data){
        	toastr.success('操作成功');
            personalData.currentEditJobInfo={};
            getJobInfo();
        });
    }
    
}
function addPsonEdu(){
    if(!eduFormValidate.isAllPass()){
        return;
    }
    var eduId=$('#eduId').val();
    var obj={
        school:$('#school').val(),
        schoolType:$('#schoolType').val(),
        major:$('#major').val(),
        majortype:$('#majortype').val(),
        education:$('#education').val(),
        edusystem:$('#edusystem').val(),
        degree:$('#degree').val(),
        firstEducation:$('#firstEducation').val(),
        lastEducation:$('#lastEducation').val(),
        lastDegree:$('#lastDegree').val(),
        certifcode:$('#certifcode').val(),
        isFullTimeHighEdu:$('#isFullTimeHighEdu').val(),
        isInServiceHighEdu:$('#isInServiceHighEdu').val(),
        begindate:$('#begindate').val(),
        enddate:$('#enddate').val(),
        personnelId:personnelId,
        eduId:eduId
    };
    if(eduId.length>0){
    	$http.put('/edu/updateTbEdu',JSON.stringify(obj),function(data){
    		toastr.success('操作成功');
            personalData.currentEditEduInfo={};
            getEduInfo();
    	});
    }else{
    	$http.post('/edu/saveTbEdu',JSON.stringify(obj),function(data){
    		toastr.success('操作成功');
            personalData.currentEditEduInfo={};
            getEduInfo();
    	});
    }
}
function addFamily(){
    if (!familyFormValidate.isAllPass())
        return;
    var familyId=$('#familyId').val();

    var obj={
        memRelation:$('#memRelation').val(),
        memName:$('#memName').val(),
        relaEmail:$('#relaEmail').val(),
        relaPhone:$('#relaPhone').val(),
        relaAddr:$('#relaAddr').val(),
        personnelId:personnelId,
        familyId:familyId
    };
    $http.post((familyId.length>0?'/family/updateTbFamily':'/family/saveTbFamily'),JSON.stringify(obj),function(data){
    	toastr.success('操作成功');
        personalData.currentEditFamilyInfo={};
        getFamilyInfo();
    });
     
}
function addPsonImg(){
    var isIE=!!window.ActiveXObject;
    var isIE8910=isIE&&document.documentMode<11;
    
    if(isIE8910){
        xmlHttp = new ActiveXObject("MSXML2.XMLHTTP");
        xmlHttp.open("POST",this.value, false);
        xmlHttp.send("");
        xml_dom = new ActiveXObject("MSXML2.DOMDocument");
        tmpNode = xml_dom.createElement("tmpNode");
        tmpNode.dataType = "bin.base64";
        tmpNode.nodeTypedValue = xmlHttp.responseBody;
        imgData = "data:image/"+ "bmp" +";base64," + tmpNode.text.replace(/\n/g,"");
        $("#psnImg").attr('src', imgData);
        convertToFile(imgData);
    }else{
        var $file = $('#choseFileImg');
        var fileObj = $file[0];
        if(fileObj.value!=""){
            var dataURL;
            var fr = new FileReader;
            var $img = $("#psnImg");
            fr.readAsDataURL(fileObj.files[0]);
            fr.onload=function(){
                dataURL = fr.result;
                // console.log(dataURL);
                $img.attr('src', dataURL);
                setTimeout(convertToFile(dataURL),"500");
            } 
        }
    }
}

function convertToFile(base64Codes){
    var formData = new FormData();

    // formData.append("psnImageId", $('#psnImageId').val());
    formData.append("multipartFile",convertBase64UrlToBlob(base64Codes));	
    $.ajax({			//提交表单，异步上传图片
        url : "/psnImage/uploadImg",  //上传图片调用的服务
        type : "POST",
        data : formData,
        // dataType:"json",
        processData : false,         // 告诉jQuery不要去处理发送的数据
        contentType : false,        // 告诉jQuery不要去设置Content-Type请求头
        success:function(data){
            toastr.success(data.message);
            psnImageId = data.data.psnImageId;
        },
        error:function(data){

        }
    });
}

function convertBase64UrlToBlob(urlData){
    var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte
    var ab = new ArrayBuffer(bytes.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < bytes.length; i++) {
        ia[i] = bytes.charCodeAt(i);
    }
    return new Blob( [ab] , {type : 'image/png'});
}


function updatePersonnel(){
    if (!userFormValidate.isAllPass())
        return;
    var psnName=$('#psnName').val();
    var gender=$('#gender').val();
    var certType=$('#certType').val();
    var certNo=$('#certNo').val();
    var nationality=$('#nationality').val();
    var nation=$('#nation').val();
    var toWorkTime=$('#toWorkTime').val();
    var marriage=$('#marriage').val();
    var pliticalStatus=$('#pliticalStatus').val();
    var updates={};
    updates.personnelId=personalData.personalData.personnelId;
    updates.gender=gender;
    updates.psnName=psnName;
    updates.certType=certType;
    updates.certNo=certNo;
    updates.nationality=nationality;
    updates.nation=nation;
    updates.toWorkTime=toWorkTime;
    updates.marriage=marriage;
    updates.pliticalStatus=pliticalStatus;
    updates.image=psnImageId;
    updates.address=$('#address').val();
    
    var tbMobileVoList =new Array();
    $('#tbMobileVoList').find(':input').each(function(index){
        var firstFlag = index == 0? 1: 0;
        var obj={};
        if($(this).attr('contactid')!=null&&typeof($(this).attr('contactid')) != "undefined"&&$(this).attr('contactid').length>0){
            obj.contactId=$(this).attr('contactid');
        }
        if($(this).val()==null || typeof($(this).val()) == "undefined"||$(this).val().length<=0){
        }else{
            obj.content=$(this).val();
            obj.personnelId=personalData.personalData.personnelId;
            obj.contactType=1;
            obj.firstFlag = firstFlag;
            tbMobileVoList.push(obj);
        }

    }) ;
    var tbEamilVoList =new Array();
    $('#tbEamilVoList').find(':input').each(function(index){
        var firstFlag = index == 0? 1: 0;
        var obj={};
        if($(this).attr('contactid')!=null&&typeof($(this).attr('contactid')) != "undefined"&&$(this).attr('contactid').length>0){
            obj.contactId=$(this).attr('contactid');
        }
        if($(this).val()==null || typeof($(this).val()) == "undefined"||$(this).val().length<=0){
        }else{
            obj.content=$(this).val();
            obj.personnelId=personalData.personalData.personnelId;
            obj.contactType=2;
            obj.firstFlag = firstFlag;
            tbEamilVoList.push(obj);
        }

    }) ;

    updates.tbMobileVoList=tbMobileVoList;
    updates.tbEamilVoList=tbEamilVoList;

    updates.image = psnImageId;
    $http.put('/personnel/updatePersonnel',JSON.stringify(updates),function(data){
    	toastr.success('操作成功');
        getOrgPersonnerList();
    });
     
}

// 点击电话新增btn
function addMobile (e) {
    $("<div class='mobile-item' style='width: 100%; position: relative; display: inline-block;'><input class='ui-input col-md-8 col-sm-8 col-xs-12 col-md-offset-4 col-sm-offset-4'><div class='fright del'><a class='icon-del' href='javascript:void(0)'><span class='fa fa-minus-circle'></span></a></div><div class='fright default-set'><a href='javascript:void(0)' onclick='setDefault(this)'>设为默认</a></div></div>").insertBefore($(e).parent());
    $('.icon-del').on('click', function () {
        $(this).parent().parent().remove();
    });
}
// 点击邮箱新增btn
function addEmail (e) {
    $("<div class='email-item' style='width: 100%; position: relative; display: inline-block;'><input class='ui-input col-md-8 col-sm-8 col-xs-12 col-md-offset-4 col-sm-offset-4'><div class='fright del'><a class='icon-del' href='javascript:void(0)'><span class='fa fa-minus-circle'></span></a></div><div class='fright default-set'><a href='javascript:void(0)' onclick='setDefaultEmail(this)'>设为默认</a></div></div>").insertBefore($(e).parent())
    $('.icon-del').on('click', function () {
        $(this).parent().parent().remove();
    });
}
// 设置默认电话
function setDefault (e) {
    var temp1 = $('#mobile').val();
    var temp2 = $(e).parent().siblings('input').val();
    if (temp1 && temp2) {
        $('#mobile').val(temp2);
        $(e).parent().siblings('input').val(temp1);
    }
}
// 设置默认邮箱
function setDefaultEmail (e) {
    var temp1 = $('#email').val();
    var temp2 = $(e).parent().siblings('input').val();
    if (temp1 && temp2) {
        $('#email').val(temp2);
        $(e).parent().siblings('input').val(temp1);
    }
}

function deleteJob(id){
    parent.layer.confirm('确定删除?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        $http.delet('/psnjob/delTbPsnjob?psnjobId='+id,{},function(data){
        	toastr.success('操作成功');
            getJobInfo();
        });
    }, function(){

    });
}
function deleteImg(){
    parent.layer.confirm('确定删除?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        /*$.ajax({
            url:'/psnjob/delTbPsnjob?psnjobId='+id,
            type:'DELETE',
            dataType:'json',
            success:function(data){
                if(data.state==1000){
                    parent.layer.confirm('操作成功', {
                        icon: 0,
                        title: '提示',
                        btn: ['确定' ]
                    }, function(index, layero){
                        parent.layer.close(index);

                    }, function(){

                    });
                    getJobInfo();
                }else{
                    parent.layer.confirm('操作失败,'+data.message, {
                        icon: 0,
                        title: '提示',
                        btn: ['确定' ]
                    }, function(index, layero){
                        parent.layer.close(index);

                    }, function(){

                    });
                }
            }
        });*/
    }, function(){

    });
}
function deleteEdu(id){
    parent.layer.confirm('确定删除?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        $http.delet('/edu/delTbEdu',{'eduId':id},function(data){
        	toastr.success('操作成功');
        	getEduInfo();
        });
    }, function(){

    });

}
function deleteFamily(id){
    parent.layer.confirm('确定删除?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        $http.delet('/family/delTbFamily?familyId='+id,{},function(data){
        	toastr.success('操作成功');
        	getFamilyInfo();
        });
    }, function(){

    });


}
$(document).ready(function(){
    Handlebars.registerHelper('eq', function(v1, v2, opts) {
        if(v1 == v2){
            return opts.fn(this);
        }
        else
            return opts.inverse(this);
    });
    Handlebars.registerHelper('format', function (date__,options) {
        Date.prototype.Format = function(fmt) {
            var o = {
                "M+" : this.getMonth()+1, //月份
                "d+" : this.getDate(), //日
                "h+" : this.getHours(), //小时
                "m+" : this.getMinutes(), //分
                "s+" : this.getSeconds(), //秒
                "q+" : Math.floor((this.getMonth()+3)/3), //季度
                "S" : this.getMilliseconds() //毫秒
            };
            if(/(y+)/.test(fmt))
                fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)
                if(new RegExp("("+ k +")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            return fmt;
        }
        if(date__==''||date__==null){
            return "";
        }
        var str = new Date(date__).Format("yyyy-MM-dd");
        return str;
    });
    Handlebars.registerHelper('seq', function (index,options) {

        return index+1;
    });
    if(tabPage=='acct'){
        $('#personnel').removeClass('active');
        $('#user').addClass('active');
        $('#psnTabLI').removeClass('active');
        $('#userTabLI').addClass('active');
    }

    if(!isNum(orgId)){
        orgId=0;
    }
    if(!isNum(orgTreeId)){
        orgTreeId=0;
    }

    getYesNo();
    getOrgTreeList();
    getSchoolType();
    getGender();
    getJobInfo();
    getEduInfo();
    getFamilyInfo();
    getOrgPersonnerList();
    getUserAccount();
    getPsnImage();

});
function gotoPrev(){
    if (orgId == '88888888') {
        parent.openTreeById('', '88888888')
    }
    else {
        var url = "/inaction/user/list.html?";
        url += "id=" + orgId;
        url += "&orgTreeId=" + orgTreeId;
        url += "&orgTreeName=" + orgTreeName;
        url += "&name=" + orgName;
        location.href = url;
    }
}
function gotoAccout(i){
	var mainAcctId=0;
	for(var j=0;j<personalData.userList.records.length;j++){
		if(personalData.userList.records[j].type==1){
			mainAcctId=personalData.userList.records[j].acctId;
			break;
		}
	}
    var userAcc=personalData.userList.records[i];
    var url="";
    if(userAcc.type==1){
        url+="/inaction/account/editMainAccount.html";
        url+="?";
        url+="curOrgTreeId="+parent.getCurrentOrgTreeId()+"&";
        url+="curOrgId="+parent.getCurrentOrgId()+"&";
    }else{
        url+="/inaction/account/editSubAccount.html";
        url+="?";
        url+="curOrgTreeId="+parent.getCurrentOrgTreeId()+"&";
        url+="curOrgId="+parent.getCurrentOrgId()+"&";
        url+="curSlaveOrgTreeId="+userAcc.curSlaveOrgTreeId+"&";
        url+="curSlaveOrgTreeName="+userAcc.curSlaveOrgTreeName+"&";
        url+="mainAcctId="+mainAcctId+"&";
    }
    
    url+="orgId="+orgId+"&";
    url+="orgRootId="+orgRootId+"&";
    url+="personnelId="+personnelId +"&";
    url+="orgTreeId="+orgTreeId+"&";
    url+="orgName="+encodeURI(orgName)+"&";
    url+="opBtn="+0+"&";
    url+="hType="+"uh"+"&";
    url+="acctId="+userAcc.acctId+"&";
    url+="statusCd="+userAcc.statusCd+"&";
    url+="tabPage="+"acct"+"&";
    url+="orgTreeName="+orgTreeName+"&";
    
    window.location.href=url;
}
function autoWriteForm(){
    var certNo=$('#certNo').val();
    if(validCardByCard(certNo)){
        $('#nationality').val(getNationalityByCard(certNo));
        $('#gender').val(getGenderByCard(certNo)?1:2);
    }
}

function isNum(n){
    var re = /^[0-9]+.?[0-9]*$/;
    return re.test(n);
}
function showMore(){
	$('#showMore').hide();
	$('#jobTitleDiv').show();
	$('#jobInfoTable1').show();
	$('#eduTitleDiv').show();
	$('#eduInfoTable1').show();
	$('#familyTitleDiv').show();
	$('#familyInfoTable1').show();
	$('#showLess').show();
}
function showLess(){
	$('#showLess').hide();
	$('#jobTitleDiv').hide();
	$('#jobInfoTable1').hide();
	$('#eduTitleDiv').hide();
	$('#eduInfoTable1').hide();
	$('#familyTitleDiv').hide();
	$('#familyInfoTable1').hide();
	$('#showMore').show();
}