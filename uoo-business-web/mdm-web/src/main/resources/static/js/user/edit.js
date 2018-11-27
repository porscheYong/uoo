var orgId = getQueryString('id');
var orgRootId = getQueryString('orgRootId');
//var orgRootId = 1;
var personnelId = getQueryString('personnelId');
var orgTreeId = getQueryString('orgTreeId');
var orgName = getQueryString('name');
var personalData={},genderData,certTypeData,nationData,pliticalStatusData,marriageData,orgInfo={};
// lulu ui select插件
// seajs.use('../../../static/vendors/lulu/js/common/ui/Select', function () {
//   $('select').selectMatch();
// })
function getOrgTreeList () {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        personalData.orgTreeList=data;
    }, function (err) {
        console.log(err)
    })
}
function getEduInfo(){
	$.ajax({
		url:'/edu/getTbEduPage',
		data:{personnelId:personnelId,pageSize:11111111,pageNo:1},
		dataType:'json',
		type:'get',
		success:function(data){
			personalData.eduInfo=data.data;
			initEduInfo();
		}
	});
}
function getJobInfo(){
	$.ajax({
		url:'/psnjob/getTbPsnjobPage',
		data:{personnelId:personnelId,pageSize:11111111,pageNo:1},
		dataType:'json',
		type:'get',
		success:function(data){
			personalData.jobInfo=data;
			initJobInfo();
		}
	});
}
function getOrgInfo(){
	$http.get('/orgPersonRel/getPerOrgRelPage', {orgId:orgId,orgTreeId:orgTreeId,personnelId:personnelId,pageSize:11111111,pageNo:1},
	function (data) {
        console.log(data)
        orgInfo=data;
        personalData.orgInfo=orgInfo;
        initOrgInfo();
    }, function (err) {
        console.log(err)
    })
}
function getFamilyInfo(){
	$.ajax({
		url:'/family/getTbFamilyPage',
		data:{personnelId:personnelId,pageSize:11111111,pageNo:1},
		dataType:'json',
		type:'get',
		success:function(data){
			personalData.familyInfo=data.data;
			initFamilyInfo();
		},
		error:function(data){
			initFamilyInfo();
		}
	});
	 
}
 // 与本人关系
 function getMemRelation () {
     $http.get('/tbDictionaryItem/getList/MEM_RELATION', {}, function (data) {
         console.log(data)
         personalData.memRelation=data;
     }, function (err) {
         console.log(err)
     })
 }
 // SCHOOL_TYPE
 function getSchoolType () {
	 $http.get('/tbDictionaryItem/getList/SCHOOL_TYPE', {}, function (data) {
		 console.log(data)
		 personalData.schoolType=data;
	 }, function (err) {
		 console.log(err)
	 })
 }
 // 获取性别字典数据
 function getGender () {
	 $http.get('/tbDictionaryItem/getList/GENDER', {}, function (data) {
		 console.log(data)
		 genderData=data;
		 personalData.genderData=genderData;
	 }, function (err) {
		 console.log(err)
	 })
 }

 // 获取证件类型字典数据
 function getCertType () {
     $http.get('/tbDictionaryItem/getList/CERT_TYPE', {}, function (data) {
         console.log(data)
         certTypeData=data;
         personalData.certTypeData=certTypeData;
     }, function (err) {
         console.log(err)
     })
 }

 // 获取民族字典数据
 function getNation () {
     $http.get('/tbDictionaryItem/getList/NATION', {}, function (data) {
         console.log(data)
         nationData=data;
         personalData.nationData=nationData;
     }, function (err) {
         console.log(err)
     })
 }

 // 获取政治面貌字典数据
 function getPliticalStatus () {
     $http.get('/tbDictionaryItem/getList/PLITICAL_STATUS', {}, function (data) {
         console.log(data)
         pliticalStatusData=data;
         personalData.pliticalStatusData=pliticalStatusData;
     }, function (err) {
         console.log(err)
     })
 }

 // 获取婚姻状况字典数据
 function getMarriage () {
     $http.get('/tbDictionaryItem/getList/MARRIAGE', {}, function (data) {
         console.log(data)
         marriageData=data;
         personalData.marriageData=marriageData;
     }, function (err) {
         console.log(err)
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
    	console.log(personalData);
    	initUser();
    }, function (err) {
        console.log(err)
    })
}
function initUser(){
    //预编译模板
    var userTemplate = Handlebars.compile($("#userTemplate").html());
    var baseInfoTemplate = Handlebars.compile($("#baseInfoTemplate").html());
    //匹配json内容
    var userHtml = userTemplate(personalData);
    var baseHtml = baseInfoTemplate(personalData);
    //输入模板
    $('#userInfo').html(userHtml);
    $('#baseInfo').html(baseHtml);
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
	console.log('开始渲染教育信息');
	console.log(personalData);
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
    		mobileHtml+="<li>";
    		if(i==0){
    			mobileHtml+="<span class='Label'><span class='Red'>* </span>联系电话</span>";
    		}else{
    			mobileHtml+="<span class='Label'><span class='Red'> </span> </span>";
    			
    		}
    		mobileHtml+="<input name='mobiles' contactId='"+d.contactId+"' class='Col6' type='text' value='"+d.content+"'/>";
    		if(i==0){
    			mobileHtml+="<a id='' href='javascript:void(0)' onclick='addMobileInput()'><span class='fa fa-plus-circle icon-add' style='padding-right: 0; font-size: 23px;'></span></a>";
    		}
    		mobileHtml+="</li>";
    	}
    }else{
		mobileHtml+="<li>";
		mobileHtml+="<span class='Label'><span class='Red'>* </span>联系电话</span>";
		mobileHtml+="<input name='mobiles' contactId='' class='Col6' type='text' value=''/>";
		mobileHtml+="<a id='' href='javascript:void(0)' onclick='addMobileInput()'><span class='fa fa-plus-circle icon-add' style='padding-right: 0; font-size: 23px;'></span></a>";
		mobileHtml+="</li>";
    }
    $('#userEditUL0').append(mobileHtml);
    var emailHtml="";
    if(personalData.personalData.tbEamilVoList.length>0){
    	for(var i=0;i<personalData.personalData.tbEamilVoList.length;i++){
    		var d=personalData.personalData.tbEamilVoList[i];
    		emailHtml+="<li>";
    		if(i==0){
    			mobileHtml+="<span class='Label'><span class='Red'>* </span>邮箱</span>";
    		}else{
    			mobileHtml+="<span class='Label'><span class='Red'> </span> </span>";
    			
    		}
    		emailHtml+="<input name='emails' contactId='"+d.contactId+"' class='Col6' type='text' value='"+d.content+"'/>";
    		if(i==0){
    			emailHtml+="<a id='' href='javascript:void(0)' onclick='addEmailInput()'><span class='fa fa-plus-circle icon-add' style='padding-right: 0; font-size: 23px;'></span></a>";
    		}
    		emailHtml+="</li>";
    	}
    }else{
    	emailHtml+="<li>";
    	emailHtml+="<span class='Label'><span class='Red'>* </span>邮箱</span>";
    	emailHtml+="<input name='emails' contactId='' class='Col6' type='text' value=''/>";
    	emailHtml+="<a id='' href='javascript:void(0)' onclick='addEmailInput()'><span class='fa fa-plus-circle icon-add' style='padding-right: 0; font-size: 23px;'></span></a>";
    	emailHtml+="</li>";
    }
    $('#userEditUL1').append(emailHtml);
    laydate.render({
	    elem:  'input[isTime="yes"]'
	});
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
}
function openOrgEditByEdit (i) {
	personalData.currentEditOrgInfo=personalData.orgInfo.records[i];
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
}
function openFamilyEditByEdit (i) {
	personalData.currentEditFamilyInfo=personalData.familyInfo.records[i];
	openFamilyEdit();
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
        content: 'orgNameDialog.html?orgTreeId='+orgTreeId+'&orgRootId='+orgRootId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgFullName').val(checkNode.name);
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
function cancelOrgEdit(){
	  personalData=currentEditOrgInfo={};
	initOrgInfo();
}
function cancelJobEdit(){
	 personalData=currentEditJobInfo={};
	initJobInfo();
}
function cancelEduEdit(){
	 personalData=currentEditEduInfo={};
	initEduInfo();
}
function cancelFamilyEdit(){
	personalData.currentEditFamilyInfo={};
	initFamilyInfo();
}
function cancelUserEdit () {
    var userInfoTemplate = Handlebars.compile($("#userTemplate").html());
    var userInfoHtml = userInfoTemplate(personalData);
    $('#userInfo').html(userInfoHtml);
}
function addPsonOrg(){
	var psonOrgArr=new Array();
	var psonOrg={
			orgTreeId:$('#orgTreeId').val(),
			orgFullName:$('#orgFullName').val(),
			doubleName:$('#doubleName').val(),
			property:$('#property').val(),
			postId:$('#postId').val(),
			property:$('#property').val(),
			personId:personnelId,
			
	};
	psonOrgArr[0]=psonOrg;
	$.ajax({
		url:'/orgPersonRel/addOrgPsn',
		type:'post',
		data:JSON.stringify(psonOrgArr), 
		
		contentType:'application/json',
		dataType:'json',
		success:function(data){
			if(data.state==1000){
				alert('新修改成功');
				  personalData.currentEditOrgInfo={};
				getOrgInfo();
			}else{
				alert('修改失败，'+data.message);
			}
		}
	});
}
function addPsonJob(){
	var psnjobId=$('#psnjobId').val();
	var psonJob={
			orgId:$('#orgId').attr('keyId'),
			beginTime:$('#beginTime').val(),
			endTime:$('#endTime').val(),
			personnelId:personnelId,
			psnjobId:psnjobId
	};
	$.ajax({
		url:psnjobId.length>0?'/psnjob/updateTbPsnjob':'/psnjob/saveTbPsnjob',
		type:psnjobId.length>0?'put':'post',
		data:JSON.stringify(psonJob), 
		contentType:'application/json',
		dataType:'json',
		success:function(data){
			if(data.state==1000){
				alert('新修改成功');
				 personalData.currentEditJobInfo={};
				 
				getJobInfo();
			}else{
				alert('修改失败，'+data.message);
			}
		}
	});
}
function addPsonEdu(){
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
	$.ajax({
		url:eduId.length>0?'/edu/updateTbEdu':'/edu/saveTbEdu',
		type:eduId.length>0?'put':'post',
		data:JSON.stringify(obj), 
		contentType:'application/json',
		dataType:'json',
		success:function(data){
			if(data.state==1000){
				alert('新修改成功');
				 personalData.currentEditEduInfo={};
				getEduInfo();
			}else{
				alert('修改失败，'+data.message);
			}
		}
	});
}
function addFamily(){
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
	$.ajax({
		url:familyId.length>0?'/family/updateTbFamily':'/family/saveTbFamily',
		type:familyId.length>0?'post':'post',
		data:JSON.stringify(obj), 
		contentType:'application/json',
		dataType:'json',
		success:function(data){
			if(data.state==1000){
				alert('新修改成功');
				 personalData.currentEditFamilyInfo={};
				getFamilyInfo();
			}else{
				alert('修改失败，'+data.message);
			}
		}
	});
}
function updatePersonnel(){
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
	
	var tbMobileVoList =new Array();
	var mobiles=$("input[name='mobiles']").each(function(){
		var obj={};
		if($(this).attr('contactId')!=null&&$(this).attr('contactId').length>0){
			obj.contactId=$(this).attr('contactId');
		}
		obj.content=$(this).val();
		obj.personnelId=personalData.personalData.personnelId;
		obj.contactType=1;
		tbMobileVoList.push(obj);
	}) ;
	var tbEamilVoList =new Array();
	var emails=$("input[name='emails']").each(function(){
		var obj={};
		if($(this).attr('contactId')!=null&&$(this).attr('contactId').length>0){
			obj.contactId=$(this).attr('contactId');
		}
		obj.content=$(this).val();
		obj.personnelId=personalData.personalData.personnelId;
		obj.contactType=2;
		tbEamilVoList.push(obj);
	}) ;
	
	updates.tbMobileVoList=tbMobileVoList;
	updates.tbEamilVoList=tbEamilVoList;
	
	$.ajax({
		url:'/personnel/updatePersonnel',
		type:'put',
		data:JSON.stringify(updates), 
		
		contentType:'application/json',
		dataType:'json',
		success:function(data){
			console.log(JSON.stringify(updates));
			if(data.state==1000){
				alert('新修改成功');
				personalData.personalData.gender=gender;
				personalData.personalData.psnName=psnName;
				personalData.personalData.certType=certType;
				personalData.personalData.certNo=certNo;
				personalData.personalData.nationality=nationality;
				personalData.personalData.nation=nation;
				personalData.personalData.toWorkTime=toWorkTime;
				personalData.personalData.marriage=marriage;
				personalData.personalData.pliticalStatus=pliticalStatus;
				personalData.personalData.tbMobileVoList=tbMobileVoList;
				personalData.personalData.tbEamilVoList=tbEamilVoList;
				initUser();
			}else{
				alert('修改失败,'+data.message);
			}
		}
	});
}
function addEmailInput(){
	var mh="<li>";
	mh+="<span class='Label'></span>"
	 mh+="<input class='Col6' name='emails' type='text'  />";
	 mh+="</li>";
	 $('#userEditUL1').append(mh);
}
function addMobileInput(){
	var mh="<li>";
	mh+="<span class='Label'></span>"
	 mh+="<input class='Col6' name='mobiles' type='text'  />";
	 mh+="</li>";
	 $('#userEditUL0').append(mh);
}
function deleteJob(id){
	if(confirm('确定删除？')){
		$.ajax({
			url:'/psnjob/delTbPsnjob?psnjobId='+id,
			type:'DELETE',
			dataType:'json',
			success:function(data){
				if(data.state==1000){
					alert('操作成功');
					getJobInfo();
				}else{
					alert('操作失败,'+data.message);
				}
			}
		});
	}
	
}
function deleteEdu(id){
	if(confirm('确定删除？')){
		$.ajax({
			url:'/edu/delTbEdu?eduId='+id,
			type:'DELETE',
			dataType:'json',
			success:function(data){
				if(data.state==1000){
					alert('操作成功');
					getEduInfo();
				}else{
					alert('操作失败,'+data.message);
				}
			}
		});
	}
	
}
function deleteFamily(id){
	if(confirm('确定删除？')){
		$.ajax({ 
			url:'/family/delTbFamily?familyId='+id,
			type:'DELETE',
			dataType:'json',
			success:function(data){
				if(data.state==1000){
					alert('操作成功');
					getFamilyInfo();
				}else{
					alert('操作失败,'+data.message);
				}
			}
		});
	}
	
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
	getSchoolType();
	getMemRelation();
	getGender();
	getCertType();
	getNation();
	getPliticalStatus();
	getMarriage();
	getOrgInfo();
	getJobInfo();
	getEduInfo();
	getFamilyInfo();
	getOrgTreeList();
	getOrgPersonnerList();
	
	 
});
