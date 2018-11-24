Handlebars.registerHelper('eq' function(param1, param2, options) {
	  if(param1 == param2) {
	    return options.fn(this);
	  } else {
	    return options.inverse(this);
	  }
	});

var orgId = getQueryString('id');
var orgRootId = getQueryString('orgRootId');
 var personnelId = getQueryString('personnelId');
//var personnelId = 127;
var orgName = getQueryString('name');
// lulu ui select插件
// seajs.use('../../../static/vendors/lulu/js/common/ui/Select', function () {
//   $('select').selectMatch();
// })
// 获取人员信息
var resultData;
function getOrgPersonnerList () {
    $http.get('/personnel/getFormPersonnel', {
        orgId: orgId,
        //orgRootId: orgRootId,
        personnelId: personnelId
    }, function (data) {
    	console.log(data)
    	resultData=data;
        /*var personOrgTableTemplate = Handlebars.compile($("#personOrgTableTemplate").html());
        var personalJobTemplate = Handlebars.compile($("#personalJobTemplate").html());
        var personalEduTemplate = Handlebars.compile($("#personalEduTemplate").html());*/
        /*var personOrgTableHtml = personOrgTableTemplate(data);
        var personalJobHtml = personalJobTemplate(data);
        var personalEduHtml = personalEduTemplate(data);*/
       /* $('#personOrgTableDiv').html(personOrgTableHtml);
        $('#personalJobDiv').html(personalJobHtml);
        $('#personalEduDiv').html(personalEduHtml);*/
        /*console.log(data)
        //预编译模板
        var userTemplate = Handlebars.compile($("#userTemplate").html());
        //匹配json内容
        var userHtml = userTemplate();
        //输入模板
        $('#userInfo').html(userHtml);*/
    	showUserInfo();
    }, function (err) {
        console.log(err)
    })
}
function showUserInfo(){
	//预编译模板
	var userTemplate = Handlebars.compile($("#userTemplate").html());
    //匹配json内容
    var userHtml = userTemplate(resultData);
    //输入模板
    $('#userInfo').html(userHtml);
}
function  editUser() {
    //预编译模板
    var userTemplate = Handlebars.compile($("#userEditTemplate").html());
    //匹配json内容
    var userHtml = userTemplate(resultData);
    //输入模板
    $('#userInfo').html(userHtml);
}


function cancelUserEdit () {
    var userInfoTemplate = Handlebars.compile($("#userTemplate").html());
    var userInfoHtml = userInfoTemplate(resultData);
    $('#userInfo').html(userInfoHtml);
}

getOrgPersonnerList();

