var orgId = getQueryString('id');
var orgRootId = getQueryString('orgRootId');
var tabPage = getQueryString('tabPage');
var personnelId = getQueryString('personnelId');
var orgTreeId = getQueryString('orgTreeId');
var orgName = getQueryString('name');
var addOrg = getQueryString('addOrg');
var personalData={},genderData,certTypeData,nationData,pliticalStatusData,marriageData,orgInfo={},
    userFormValidate,orgFormValidate;
var toastr = window.top.toastr;
var psnImageId;
var imgUrl = "";

function getOrgTreeList () {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        personalData.orgTreeList=data;
    }, function (err) {

    })
}
function getRefType () {
    $http.get('/tbDictionaryItem/getList/REF_TYPE', {}, function (data) {
        personalData.refType=data;
    }, function (err) {

    })
}
function getYesNo () {
    $http.get('/tbDictionaryItem/getList/YES_NO', {}, function (data) {
        personalData.yesNo=data;
    }, function (err) {

    })
}

// 与本人关系
function getMemRelation () {
    $http.get('/tbDictionaryItem/getList/MEM_RELATION', {}, function (data) {
        personalData.memRelation=data;
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

// 获取证件类型字典数据
function getCertType () {
    $http.get('/tbDictionaryItem/getList/CERT_TYPE', {}, function (data) {
        certTypeData=data;
        personalData.certTypeData=certTypeData;
    }, function (err) {

    })
}

// 获取民族字典数据
function getNation () {
    $http.get('/tbDictionaryItem/getList/NATION', {}, function (data) {
        nationData=data;
        personalData.nationData=nationData;
    }, function (err) {

    })
}

// 获取政治面貌字典数据
function getPliticalStatus () {
    $http.get('/tbDictionaryItem/getList/PLITICAL_STATUS', {}, function (data) {
        pliticalStatusData=data;
        personalData.pliticalStatusData=pliticalStatusData;
    }, function (err) {

    })
}

// 获取婚姻状况字典数据
function getMarriage () {
    $http.get('/tbDictionaryItem/getList/MARRIAGE', {}, function (data) {
        marriageData=data;
        personalData.marriageData=marriageData;
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

function  editUser() {
    $('#userEditButton').hide();
    //预编译模板
    var userTemplate = Handlebars.compile($("#userEditTemplate").html());
    //匹配json内容
    var userHtml = userTemplate(personalData);
    //输入模板
    $('#userInfo').html(userHtml);

    //手动插入吧
    var mobileHtml="";
    if(personalData.personalData.tbMobileVoList.length>0){
        for(var i=0;i<personalData.personalData.tbMobileVoList.length;i++){
            var d=personalData.personalData.tbMobileVoList[i];
            if(d.content==null||d.content=='null'){
                personalData.personalData.tbMobileVoList[i].content='';
            }
        }
        for(var i=0;i<personalData.personalData.tbMobileVoList.length;i++){
            var d=personalData.personalData.tbMobileVoList[i];
            mobileHtml+="<li>";
            if(i==0){
                mobileHtml+="<span class='Label'><span class='Red'>* </span>联系电话</span>";
                mobileHtml+="<input name='mobiles' contactid='"+d.contactId+"' class='Col5 ui-input' required type='text' value='"+d.content+"'/>";
            }else{
                mobileHtml+="<span class='Label'><span class='Red'> </span> </span>";
                mobileHtml+="<input name='mobiles' contactid='"+d.contactId+"' class='Col5' type='text' value='"+d.content+"'/>";
                mobileHtml+="&nbsp;<a class='icon-del'><span class='fa fa-minus-circle '></span></a>";

            }
            if(i==0){
                mobileHtml+="&nbsp;<a id='' href='javascript:void(0)' onclick='addMobileInput()'><span class='fa fa-plus-circle icon-add' style='padding-right: 0; font-size: 23px;'></span></a>";
            }else{
                //mobileHtml+="&nbsp;<a id='' href='javascript:void(0)' onclick='addMobileInput()'><span class='fa fa-plus-circle icon-add' style='padding-right: 0; font-size: 23px;'></span></a>";

            }
            mobileHtml+="</li>";
        }
    }else{
        mobileHtml+="<li>";
        mobileHtml+="<span class='Label'><span class='Red'>* </span>联系电话</span>";
        mobileHtml+="<input name='mobiles' contactid='' class='Col5 ui-input' required type='text' value=''/>";
        mobileHtml+="&nbsp;<a id='' href='javascript:void(0)' onclick='addMobileInput()'><span class='fa fa-plus-circle icon-add' style='padding-right: 0; font-size: 23px;'></span></a>";
        mobileHtml+="</li>";
    }
    $('#userEditUL0').append(mobileHtml);
    var emailHtml="";
    if(personalData.personalData.tbEamilVoList.length>0){
        for(var i=0;i<personalData.personalData.tbEamilVoList.length;i++){
            var d=personalData.personalData.tbEamilVoList[i];
            if(d.content==null||d.content=='null'){
                personalData.personalData.tbEamilVoList[i].content='';
            }
        }
        for(var i=0;i<personalData.personalData.tbEamilVoList.length;i++){
            var d=personalData.personalData.tbEamilVoList[i];
            emailHtml+="<li>";
            if(i==0){
                emailHtml+="<span class='Label'><span class='Red'>* </span>邮箱</span>";
                emailHtml+="<input name='emails' contactid='"+d.contactId+"' class='Col5 ui-input' required type='text' value='"+d.content+"'/>";
            }else{
                emailHtml+="<span class='Label'><span class='Red'> </span> </span>";
                emailHtml+="<input name='emails' contactid='"+d.contactId+"' class='Col5' type='text' value='"+d.content+"'/>";
                emailHtml+="&nbsp;<a class='icon-del'><span class='fa fa-minus-circle '></span></a>";
            }
            if(i==0){
                emailHtml+="&nbsp;<a id='' href='javascript:void(0)' onclick='addEmailInput()'><span class='fa fa-plus-circle icon-add' style='padding-right: 0; font-size: 23px;'></span></a>";
            }
            emailHtml+="</li>";
        }
    }else{
        emailHtml+="<li>";
        emailHtml+="<span class='Label'><span class='Red'>* </span>邮箱</span>";
        emailHtml+="<input name='emails' contactid='' class='Col5 ui-input' required type='text' value=''/>";
        emailHtml+="&nbsp;<a id='' href='javascript:void(0)' onclick='addEmailInput()'><span class='fa fa-plus-circle icon-add' style='padding-right: 0; font-size: 23px;'></span></a>";
        emailHtml+="</li>";
    }
    $('#userEditUL1').append(emailHtml);
    laydate.render({
        elem:  'input[isTime="yes"]'
    });
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('select').selectMatch();
    })
    $('.icon-del').on('click', function () {
        $(this).parent().remove();
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
    getPsnImage();
}
function editOrgInfo(){

    //预编译模板
    var t = Handlebars.compile($("#orgInfoEditTemplate").html());
    //匹配json内容
    var h = t(personalData);
    //输入模板
    $('#orgInfoTable1').html(h);
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
    $.ajax({
        url:isUpdate?'/orgPersonRel/updateOrgPsn':'/orgPersonRel/addOrgPsn2',
        type:'post',
        data:isUpdate?JSON.stringify(psonOrg):JSON.stringify(psonOrgArr),
        contentType:'application/json',
        dataType:'json',
        success:function(data){
            if(data.state==1000){
            	toastr.success('操作成功');
                personalData.currentEditOrgInfo={};
                getOrgPersonnerList();
            }else{
            	toastr.error('操作失败,'+data.message);
            }
        }
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
            console.log(data);
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
    var tbMobileVoList =new Array();
    var mobiles=$("input[name='mobiles']").each(function(){
        var obj={};
        if($(this).attr('contactid')!=null&&typeof($(this).attr('contactid')) != "undefined"&&$(this).attr('contactid').length>0){
            obj.contactId=$(this).attr('contactid');
        }
        if($(this).val()==null || typeof($(this).val()) == "undefined"||$(this).val().length<=0){
        }else{
            obj.content=$(this).val();
            obj.personnelId=personalData.personalData.personnelId;
            obj.contactType=1;
            tbMobileVoList.push(obj);
        }

    }) ;
    var tbEamilVoList =new Array();
    var emails=$("input[name='emails']").each(function(){
        var obj={};
        if($(this).attr('contactid')!=null&&typeof($(this).attr('contactid')) != "undefined"&&$(this).attr('contactid').length>0){
            obj.contactId=$(this).attr('contactid');
        }
        if($(this).val()==null || typeof($(this).val()) == "undefined"||$(this).val().length<=0){
        }else{
            obj.content=$(this).val();
            obj.personnelId=personalData.personalData.personnelId;
            obj.contactType=2;
            tbEamilVoList.push(obj);
        }

    }) ;

    updates.tbMobileVoList=tbMobileVoList;
    updates.tbEamilVoList=tbEamilVoList;

    updates.image = psnImageId;

    $.ajax({
        url:'/personnel/updatePersonnel',
        type:'put',
        data:JSON.stringify(updates),

        contentType:'application/json',
        dataType:'json',
        success:function(data){
            if(data.state==1000){
            	toastr.success('操作成功');
                getOrgPersonnerList();
            }else{
            	toastr.error('操作失败,'+data.message);
            }
        }
    });
}
function addEmailInput(){
    var mh="<li>";
    mh+="<span class='Label'></span>"
    mh+="<input class='Col6' contactid='' name='emails' type='text'  />&nbsp;<a class='icon-del'><span class='fa fa-minus-circle '></span></a>";
    mh+="</li>";
    $('#userEditUL1').append(mh);
    $('.icon-del').on('click', function () {
        $(this).parent().remove();
    });
}
function addMobileInput(){
    var mh="<li class=''>";
    mh+="<span class='Label'></span>"
    //mh+=" <input required class='Col6'> <a class='icon-del'><span class='fa fa-minus-circle'></span></a>  ";

    mh+="<input class='Col6' type='text'  contactid='' name='mobiles' type='text' />&nbsp;<a class='icon-del'><span class='fa fa-minus-circle '></span></a> ";
    mh+="</li>";
    $('#userEditUL0').append(mh);
    $('.icon-del').on('click', function () {
        $(this).parent().remove();
    });
}
function deleteJob(id){
    parent.layer.confirm('确定删除?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        $.ajax({
            url:'/psnjob/delTbPsnjob?psnjobId='+id,
            type:'DELETE',
            dataType:'json',
            success:function(data){
                if(data.state==1000){
                	toastr.success('操作成功');
                    getJobInfo();
                }else{
                	toastr.error('操作失败,'+data.message);
                }
            }
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
    }

    if(!isNum(orgId)){
        orgId=0;
    }
    if(!isNum(orgTreeId)){
        orgTreeId=0;
    }

    getRefType();
    getYesNo();
    getOrgTreeList();
    getSchoolType();
    getMemRelation();
    getGender();
    getCertType();
    getNation();
    getPliticalStatus();
    getMarriage();
    getOrgPersonnerList();
    getPsnImage();

});

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

