var orgId = getQueryString('id');
// var orgRootId = getQueryString('orgRootId');
var orgRootId = 1;
var personnelId = getQueryString('personnelId');
var orgName = getQueryString('name');
// lulu ui select插件
// seajs.use('../../../static/vendors/lulu/js/common/ui/Select', function () {
//   $('select').selectMatch();
// })

// // 获取性别字典数据
// function getGender () {
//     $http.get('tbDictionaryItem/getList/GENDER', {}, function (data) {
//         console.log(data)
//     }, function (err) {
//         console.log(err)
//     })
// }
//
// // 获取证件类型字典数据
// function getCertType () {
//     $http.get('tbDictionaryItem/getList/CERT_TYPE', {}, function (data) {
//         console.log(data)
//     }, function (err) {
//         console.log(err)
//     })
// }
//
// // 获取民族字典数据
// function getNation () {
//     $http.get('tbDictionaryItem/getList/NATION', {}, function (data) {
//         console.log(data)
//     }, function (err) {
//         console.log(err)
//     })
// }
//
// // 获取政治面貌字典数据
// function getPliticalStatus () {
//     $http.get('tbDictionaryItem/getList/PLITICAL_STATUS', {}, function (data) {
//         console.log(data)
//     }, function (err) {
//         console.log(err)
//     })
// }
//
// // 获取婚姻状况字典数据
// function getMarriage () {
//     $http.get('tbDictionaryItem/getList/MARRIAGE', {}, function (data) {
//         console.log(data)
//     }, function (err) {
//         console.log(err)
//     })
// }

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
        var userHtml = userTemplate(data);
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