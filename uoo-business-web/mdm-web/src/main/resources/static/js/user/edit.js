var orgId = getQueryString('id');
// var orgRootId = getQueryString('orgRootId');
var orgRootId = 1;
// var personnelId = getQueryString('personnelId');
var personnelId = 127;
var orgName = getQueryString('name');
// lulu ui select插件
// seajs.use('../../../static/vendors/lulu/js/common/ui/Select', function () {
//   $('select').selectMatch();
// })

// 获取人员信息
function getOrgPersonnerList () {
    $http.get('/personnel/getFormPersonnel', {
        orgId: orgId,
        orgRootId: orgRootId,
        personnelId: personnelId
    }, function (data) {
        console.log(data)
    }, function (err) {
        console.log(err)
    })
}

// 获取人员信息
function getOrgPersonnerList () {
    $http.get('/personnel/getFormPersonnel', {
        orgId: orgId,
        orgRootId: orgRootId,
        personnelId: personnelId
    }, function (data) {
        console.log(data)
        //预编译模板
        var userTemplate = Handlebars.compile($("#userTemplate").html());
        //匹配json内容
        var userHtml = userTemplate();
        //输入模板
        $('#userInfo').html(userHtml);
    }, function (err) {
        console.log(err)
    })
}

function  editUser() {
    //预编译模板
    var userTemplate = Handlebars.compile($("#userEditTemplate").html());
    //匹配json内容
        var userHtml = userTemplate();
    //输入模板
    $('#userInfo').html(userHtml);
}

function cancelUserEdit () {
    var userInfoTemplate = Handlebars.compile($("#userInfoTemplate").html());
    var userInfoHtml = userInfoTemplate();
    $('#userInfo').html(userInfoHtml);
}

getOrgPersonnerList();