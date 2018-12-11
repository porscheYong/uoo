var orgId = getQueryString('id');
// var orgRootId = getQueryString('orgRootId');
var orgRootId = 1;
var orgTreeId = 1;
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
        orgTreeId: orgTreeId,
        personnelId: personnelId
    }, function (data) {
        console.log(data)
    }, function (err) {
        console.log(err)
    })
}

function cancel () {
  var url = "list.html?id=" + orgId + "&orgRootId=" + orgRootId;
  window.location.href = url;
}

getOrgPersonnerList();